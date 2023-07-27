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
import java.util.concurrent.atomic.AtomicInteger;

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
                            + "SELECT<#if hints??> /*+ ${hints} */ </#if>" +
                            "<@e.fieldsProcess columns/> FROM ${inputTableName} "
                            + "${joinType?upper_case} JOIN ${anotherTableName} "
                            + "<#if systemTimeColumn??>FOR SYSTEM_TIME AS OF ${systemTimeColumn}</#if> "
                            + "<#if columnList??>ON <#list columnList as list>${list.onLeftColumn} = ${list.onRightColumn}<#sep> and </#sep></#list></#if>"
                            + "<#if where??> WHERE ${where}</#if> "
                    ,
                    Specifications.TEMPLATE_FILE);

    private InputPortObject<TableInfo> primaryInput;
    private InputPortObject<TableInfo> secondInput;
    private OutputPortObject<TableInfo> outputPort;

    private Map<String, List<Map<String, Object>>> inputColumn;

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
        String forSystemTime = (String) parameters.get(SYSTEM_TIME_COLUMN);
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
        dataModel.put(CONFIG, formatProcessing(parameters));
        if (forSystemTime != null && !forSystemTime.equals("")) {
            dataModel.put(
                    SYSTEM_TIME_COLUMN,
                    FieldFunction.insertTableName(primaryTableName, null, forSystemTime, true));
            dataModel.put("forSystemTime",forSystemTime);
        }
        if (where != null && !where.equals("")) {
            dataModel.put(WHERE, where);
        }
        // hints
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> hints =(List<Map<String, Object>>)parameters.get("hints");
        if(hints!=null){
            StringBuffer stringBuffer = new StringBuffer();
            hints.forEach(item->{
                stringBuffer.append(item.get("joinHints (").toString());

                @SuppressWarnings("unchecked")
                List<String> joinColumn =(List<String>)item.get("joinColumn");
                joinColumn.forEach(i->{
                    stringBuffer.append(i);
                    if(!i.equals(joinColumn.get(joinColumn.size()-1))){
                        stringBuffer.append(",");
                    }
                });
                stringBuffer.append(") ");
            });
            dataModel.put("hints",stringBuffer.toString());
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
     * systemTimeColumn需要主表的时间字段
     * 输出字段不属于左表或右表
     * on 的字段是否属于对应表
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);
        model.setTableName(map.get(TABLE_NAME).toString());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> config = (List<Map<String, Object>>)map.get(CONFIG);
        @SuppressWarnings("unchecked")
        List<Map<String, String>> columnList = (List<Map<String, String>>) map.get("columnList");

        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        List<Column> leftInputColumns = primaryInput.getConnection().getFromPort().getPseudoData().getColumns();
        List<Column> rightInputColumns = secondInput.getConnection().getFromPort().getPseudoData().getColumns();
        Map<String, List<String>> portInformation = new HashMap<>();
        List<String> edge = new ArrayList<>();

        boolean systemTimeColumn = true;
        boolean onLeft = true;
        boolean onRight = true;
        if(map.get("forSystemTime")!=null){
            String left = columnList.get(0).get("onLeftColumn").split("\\.")[1];
            String right = columnList.get(0).get("onRightColumn").split("\\.")[1];

            leftInputColumns.stream().filter(l->(map.get("forSystemTime").equals(l.getName())&&!l.getType().contains("TIME")))
                    .forEach(l->leftList.add("systemTimeColumn需要主表中的时间属性(如 TIMESTAMP(3)、TIMESTAMP_LTZ等),当前字段值： "+map.get("forSystemTime")+" ,类型： "+l.getType()));

            for(Map<String, Object> m : config){
                if(m.get(TABLE_NAME).equals(PRIMARY_INPUT)){
                    if(m.get(NAME).equals(map.get("forSystemTime"))){
                        systemTimeColumn=false;
                    }
                    if(left.contains(m.get(NAME).toString())){
                        onLeft=false;
                    }
                }else{
                    if(right.contains(m.get(NAME).toString())){
                        onRight=false;
                    }
                }
            }
            if(onLeft){
                leftList.add("columnList中的onLeftColumn需要主表中的字段,当前字段值： "+left);
            }
            if(onRight){
                rightList.add("columnList中的onRightColumn需要副表中的字段,当前字段值： "+right);
            }
        }
        if(systemTimeColumn){
            leftList.add("systemTimeColumn需要主表中的字段,当前字段值： "+map.get("forSystemTime"));
        }

        leftList.addAll(fieldNameCheck(PRIMARY_INPUT,leftInputColumns));
        rightList.addAll(fieldNameCheck(SECOND_INPUT,rightInputColumns));

        if(!leftList.isEmpty()||!rightList.isEmpty()){
            if(!leftList.isEmpty()){
                edge.add(getInputPorts().get(PRIMARY_INPUT).getConnection().getId());
                portInformation.put(PRIMARY_INPUT,leftList);
            }
            if(!rightList.isEmpty()){
                edge.add(getInputPorts().get(SECOND_INPUT).getConnection().getId());
                portInformation.put(SECOND_INPUT,rightList);
            }
            model.setColor(RED);
            model.setEdge(edge);
            model.setPortInformation(portInformation);
        }
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
        Map<String, List<Map<String, Object>>> inputColumn =getInputColumn(parameters);

        ffsPrimary.addAll(FieldFunction.analyzeParameters(primaryTableName, inputColumn.get(PRIMARY_INPUT), true,primaryColumns));
        ffsPrimary.addAll(FieldFunction.analyzeParameters(secondTableName, inputColumn.get(SECOND_INPUT), true,secondColumns));

        return ffsPrimary;
    }
    public Map<String , List<Map<String, Object>>> getInputColumn(Map<String, Object> parameters){
        Map<String , List<Map<String, Object>>> out = new HashMap<>();
        List<Map<String, Object>> primaryInput = new ArrayList<>();
        List<Map<String, Object>> secondInput = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) parameters.get(COLUMNS);
        for (Map<String, Object> column : columns) {
            if ((boolean) column.get(FLAG)) {
                switch (column.get("inputTable").toString()) {
                    case PRIMARY_INPUT:
                        primaryInput.add(column);
                        break;
                    case SECOND_INPUT:
                        secondInput.add(column);
                        break;
                }
            }
        }
        out.put(PRIMARY_INPUT,primaryInput);
        out.put(SECOND_INPUT,secondInput);
        inputColumn=out;
        return out;
    }

    //从连接桩中获取输入，验证columns中的参数是否符合需要
    public List<String> fieldNameCheck(String input,List<Column> columns){
        List<String> list = new ArrayList<>();
        for (Map<String, Object> m :inputColumn.get(input)){
            @SuppressWarnings("unchecked")
            List<String> list1 = (List<String>)m.get(PARAMETERS);
            for(String s : list1){
                for(int i=0;i<columns.size();i++){
                    if(s.equals(columns.get(i).getName())){
                        break;
                    }
                    if(i==columns.size()-1){
                        list.add("输入参数中不包含字段 "+s+"，请检查 "+input+" 连接桩的入参");
                    }
                }
            }
        }
        return list;
    }

}
