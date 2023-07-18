/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.zdpx.coder.operator.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.Column;
import com.zdpx.coder.operator.FieldFunction;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.OperatorUtil;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.NameHelper;
import com.zdpx.coder.utils.TemplateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * config中选中的输入，对columns中的parameters产生影响，再通过columns中的outPutName指定输出，所以config和outPutName没有直接联系
 */
@Slf4j
public class JoinOperator extends Operator {
    public static final String TEMPLATE =
            String.format(
                    "<#import \"%s\" as e>CREATE VIEW ${tableName} AS "
                            + "SELECT <@e.fieldsProcess columns/> "
                            + "FROM ${inputTableName} "
                            + "${joinType?upper_case} JOIN ${anotherTableName} "
                            + "<#if systemTimeColumn??>FOR SYSTEM_TIME AS OF ${systemTimeColumn}</#if> "
                            + "<#if columnList??>ON <#list columnList as list>${list.onLeftColumn} = ${list.onRightColumn}<#sep> and </#sep></#list></#if>"
                            + "<#if where??> WHERE ${where}</#if> "
                    ,
                    Specifications.TEMPLATE_FILE);

    private InputPortObject<TableInfo> primaryInput;
    private InputPortObject<TableInfo> secondInput;
    private OutputPortObject<TableInfo> outputPort;

    @Override
    protected void initialize() {
        primaryInput = registerInputObjectPort(PRIMARY_INPUT);
        secondInput = registerInputObjectPort(SECOND_INPUT);
        outputPort = registerOutputObjectPort(OUTPUT_0);
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return new HashMap<>();
    }

    @Override
    protected boolean applies() {
        return true;
    }

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        if (getOutputPorts().isEmpty() || this.nodeWrapper == null) {
            log.error("JoinOperator information err.");
            return null;
        }

        Map<String, Object> parameters = getFirstParameterMap();
        String joinType = (String) parameters.get(JOIN_TYPE);
        String forSystemTime = (String) parameters.get("systemTimeColumn");
        String where = (String) parameters.get(WHERE);

        @SuppressWarnings("unchecked")
        List<Map<String, String>> columnList = (List<Map<String, String>>) parameters.get("columnList");

        Object outputTableName = parameters.get(TABLE_NAME);
        if (outputTableName == null || outputTableName.equals("")) {
            outputTableName = NameHelper.generateVariableName("JoinOperator");
        }
        //算子预览功能
        String primaryTableName = "primaryTable";
        String secondTableName = "secondTable";
        List<Column> primaryColumns = new ArrayList<>();
        List<Column> secondColumns = new ArrayList<>();
        if (primaryInput.getConnection() != null) {
            primaryTableName = primaryInput.getOutputPseudoData().getName();
            secondTableName = secondInput.getOutputPseudoData().getName();
            primaryColumns = primaryInput.getConnection().getFromPort().getPseudoData().getColumns();
            secondColumns = secondInput.getConnection().getFromPort().getPseudoData().getColumns();
        }

        //根据config中的字段，确定columns中的字段属于哪张表
        List<FieldFunction> ffsPrimary = outPutFieldFunction(parameters, primaryTableName, secondTableName,primaryColumns,secondColumns );


        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put(TABLE_NAME, outputTableName);
        dataModel.put(INPUT_TABLE_NAME, primaryTableName);
        dataModel.put(ANOTHER_TABLE_NAME, secondTableName);
        dataModel.put(Operator.COLUMNS, ffsPrimary);
        dataModel.put(JOIN_TYPE, joinType);
        dataModel.put(ID, parameters.get(ID));
        if (forSystemTime != null && !forSystemTime.equals("")) {
            dataModel.put(
                    "systemTimeColumn",
                    FieldFunction.insertTableName(primaryTableName, null, forSystemTime, true));
        }
        if (where != null && !where.equals("")) {
            dataModel.put(WHERE, where);
        }


        //修改连接字段的字段名称
        for (Map<String, String> l : columnList) {
            setUpTableName(l, primaryTableName, "onLeftColumn");
            setUpTableName(l, secondTableName, "onRightColumn");
        }
        dataModel.put("columnList", columnList);
        return dataModel;
    }

    /**
     * 校验内容：
     * <p>
     * todo systemTimeColumn需要时间字段
     * todo 左表、右表连接字段需要属于对应的表
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);
        model.setTableName(map.get(TABLE_NAME).toString());

        this.getSchemaUtil().getGenerateResult().addCheckInformation(model);
    }

    @Override
    protected void execute(Map<String, Object> dataModel) {
        if (!dataModel.isEmpty()) {

            String sqlStr = TemplateUtils.format(this.getName(), dataModel, TEMPLATE);
            @SuppressWarnings("unchecked")
            List<FieldFunction> ffs = (List<FieldFunction>) dataModel.get(Operator.COLUMNS);
            registerUdfFunctions(ffs);

            List<Column> cls = Operator.getColumnFromFieldFunctions(ffs);
            generate(sqlStr);
            OperatorUtil.postTableOutput(outputPort, dataModel.get(TABLE_NAME).toString(), cls);
        }
    }


    public void setUpTableName(Map<String, String> l, String tableName, String column) {
        String onLeftColumn = FieldFunction.insertTableName(tableName, null, l.get(column), true);
        l.put(column, onLeftColumn);
    }


    public List<FieldFunction> outPutFieldFunction(Map<String, Object> parameters, String primaryTableName, String secondTableName,
                                                   List<Column> primaryColumns,List<Column> secondColumns) {

        List<FieldFunction> ffsPrimary = new ArrayList<>();

        List<Map<String, Object>> primaryInput = new ArrayList<>();
        List<Map<String, Object>> secondInput = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) parameters.get(COLUMNS);
        for (Map<String, Object> column : columns) {
            if ((boolean) column.get(FLAG)) {
                switch (column.get("inputTable").toString()) {
                    case "primaryInput":
                        primaryInput.add(column);
                        break;
                    case "secondInput":
                        secondInput.add(column);
                        break;
                }
            }
        }
        ffsPrimary.addAll(FieldFunction.analyzeParameters(primaryTableName, primaryInput, true,primaryColumns));
        ffsPrimary.addAll(FieldFunction.analyzeParameters(secondTableName, secondInput, true,secondColumns));

        return ffsPrimary;

    }

}
