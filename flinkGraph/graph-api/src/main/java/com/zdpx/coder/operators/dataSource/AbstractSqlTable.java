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

package com.zdpx.coder.operators.dataSource;

import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.graph.InputPortObject;
import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.Column;
import com.zdpx.coder.utils.TemplateUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.TableDataStreamConverter;

/**
 *
 */
public abstract class AbstractSqlTable extends Operator {
    public static final String TEMPLATE =
            "CREATE TABLE ${tableName} ("
                    + "<#list columns as column>`${column.name}` ${column.type}<#sep>,"
                    + "</#list>"
                    + "<#if watermark??>, WATERMARK FOR `${watermark.column}` AS `${watermark.column}`<#if watermark.timeSpan??> - INTERVAL ' ${watermark.timeSpan} ' ${watermark.timeUnit}</#if></#if>"
                    + "<#if primary??>, PRIMARY KEY (`${primary}`) NOT ENFORCED</#if>"
                    + ")"
                    + "WITH ("
                    + "<#list parameters as key, value>"
                    + "'${key}' = '${value}'<#sep>,</#sep>"
                    + "</#list>)";

    public static final String OUTPUT_SQL =
            "CREATE VIEW ${tableName} AS "
            + "SELECT<#list columns as column>" +
            "<#if column.name??>`${column.name}`<#if column.outName?? && column.outName?length gt 1> AS `${column.outName}` </#if></#if><#sep>,</#sep></#list>"
            + "FROM ${inputTableName} ";

    public static final String INPUT_SQL =
            "INSERT INTO ${outputTableName} (<#list source as column>`${column.name}`<#sep>,</#sep></#list>) "
                    + "SELECT <#list tableInfo as column>`${column.name}`<#sep>, </#list> "
                    + "FROM ${inputTableName}";

    protected abstract String getDefaultName();

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        Map<String, Object> dataModel = getDataModel();
        //任意数据源格式转换
        dataModel.put(PARAMETERS, formatConversion(dataModel));
        Object tableName = dataModel.get(TABLE_NAME);
        dataModel.put(CONFIG,formatProcessing(dataModel));
        if (tableName == null || tableName.equals("")) {
            dataModel.put(TABLE_NAME, generateTableName(this.getDefaultName()));
        }
        return dataModel;
    }

    /**
     * Source校验内容：
     * WATERMARK中的字段是否为事件字段
     * Sink校验内容：
     * WATERMARK中的字段是否为事件字段
     * 入参数量和表字段数量不匹配
     * 类型不匹配
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);
        model.setTableName(map.get(TABLE_NAME).toString());

        Map<String, List<String>> portInformation = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> edge = new ArrayList<>();

        boolean flag = this.getClass().getName().contains("Sink");

        String portName = OUTPUT_0;
        if(flag){
            portName=INPUT_0;
        }

        @SuppressWarnings("unchecked")
        Map<String,String> watermark = (Map<String,String>)map.get(WATERMARK);
        if(watermark!=null){
            @SuppressWarnings("unchecked")
            List<Map<String, String>> columns = (List<Map<String, String>>) map.get(COLUMNS);
            Map<String, String> type = columns.stream()
                    .filter(item -> item.get(NAME).equals(watermark.get(COLUMN))&&item.get(TYPE).contains("TIME"))
                    .findFirst().orElse(null);
            if(type==null){
                list.add("WATERMARK中指定的列不包含时间属性字段");
            }
        }

        if(!list.isEmpty()){
            model.setColor(RED);
            model.setEdge(edge);
            portInformation.put(portName,list);
            model.setPortInformation(portInformation);
        }
        this.getSceneCode().getGenerateResult().addCheckInformation(model);
    }

    //重复代码提取
    public void processLogic(OutputPortObject<TableInfo> outputPortObject, Map<String, Object> dataModel) {

        String tableName = dataModel.get(TABLE_NAME).toString();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>)dataModel.get(COLUMNS);
        //columns排序
        dataModel.put(COLUMNS,columns.stream().sorted(Comparator.comparing(item -> item.get(NAME).toString())).collect(Collectors.toList()));

        if (this.getClass().getName().contains("Sink")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> config = (List<Map<String, Object>>) dataModel.get(CONFIG);
            // config排序
            connectToSink(INPUT_0, dataModel, config.stream().sorted(Comparator.comparing(item -> item.get(NAME).toString())).collect(Collectors.toList()));

            String sqlStr = TemplateUtils.format(tableName, dataModel, TEMPLATE);
            this.getSceneCode().getGenerateResult().generate(sqlStr);
        }else{

            String inputName ="transTable";

            dataModel.put(TABLE_NAME,inputName);
            String sqlStr = TemplateUtils.format(inputName, dataModel, TEMPLATE);
            this.getSceneCode().getGenerateResult().generate(sqlStr);

            dataModel.put("inputTableName",inputName);
            dataModel.put(TABLE_NAME,tableName);
            String rename = TemplateUtils.format(tableName, dataModel, OUTPUT_SQL);
            this.getSceneCode().getGenerateResult().generate(rename);

            Map<String, Object> parameters = getParameterLists().get(0); // [ parameters , config ]
            final TableInfo ti = TableDataStreamConverter.getTableInfo(parameters);
            ti.setName(tableName);

            outputPortObject.setPseudoData(ti);
        }
    }

    //兼容任意类型的数据源
    protected Map<String, Object> formatConversion(Map<String, Object> dataModel) {
        @SuppressWarnings("unchecked")
        Map<String, Object> defineList = (Map<String, Object>) dataModel.get(PARAMETERS);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> other = (List<Map<String, Object>>) defineList.get(OTHER);
        if (other.size() > 0) {
            other.forEach(m->defineList.put(m.get(KEY).toString(), m.get(VALUES)));
        }
        defineList.remove(OTHER);
        return defineList;
    }

    //配置连接到sink的input语句
    public void connectToSink(String input_0, Map<String, Object> dataModel, List<Map<String, Object>> input) {

        //算子预览的特殊处理
        String tableName = TABLE_NAME_DEFAULT;
        @SuppressWarnings("unchecked")
        InputPortObject<TableInfo> in = (InputPortObject<TableInfo>) getInputPorts().get(input_0);
        if (in.getConnection() != null) {
            tableName = in.getOutputPseudoData().getName();
        }

        for(Map<String, Object> map : input){
            Object outName = map.get(OUT_NAME);
            if(outName!=null&&!outName.equals("")){
                map.put(NAME,outName);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put(OUTPUT_TABLE_NAME, dataModel.get(TABLE_NAME));
        data.put(INPUT_TABLE_NAME, tableName);
        data.put(SOURCE, dataModel.get(COLUMNS));
        data.put(TABLE_INFO, input);

        String insertSqlStr = TemplateUtils.format("insert", data, INPUT_SQL);
        this.getSceneCode().getGenerateResult().generate(insertSqlStr);

    }

    protected Map<String, Object> getDataModel() {

        List<Map<String, Object>> parameterLists = getParameterLists();

        @SuppressWarnings("unchecked")
        Map<String, Object> psFirst = (Map<String, Object>)parameterLists.get(0).get(SERVICE);

        Map<String, Object> result = new HashMap<>();
        result.put(PARAMETERS, new HashMap<String, Object>());

        if (psFirst.get(TABLE_NAME) != null) {
            result.put(TABLE_NAME, psFirst.get(TABLE_NAME));
        }
        Object primary = psFirst.get(PRIMARY);
        if (primary != null && !primary.equals("")) {
            result.put(PRIMARY, primary);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> watermark = (Map<String, Object>) psFirst.get(WATERMARK);
        if (watermark != null && watermark.get(COLUMN) != null && !watermark.get(COLUMN).equals("")) {
            if ((Integer) watermark.get(TIME_SPAN) == 0) {
                watermark.remove(TIME_SPAN);
            }
            result.put(WATERMARK, watermark);
        }

        psFirst.remove(PRIMARY);
        psFirst.remove(WATERMARK);
        psFirst.remove(TABLE_NAME);

        @SuppressWarnings("unchecked")
        HashMap<String, Object> ps = (HashMap<String, Object>) result.get(PARAMETERS);
        ps.putAll(psFirst);
        @SuppressWarnings("unchecked")
        Map<String,List<Map<String, Object>>> outPut = (Map<String,List<Map<String, Object>>>) parameterLists.get(0).get(OUTPUT);

        result.put(COLUMNS,outPut.get(COLUMNS)==null? outPut.get(SOURCE):outPut.get(COLUMNS));

        if (parameterLists.size() > 1) {//文件中存在config
            result.putAll(parameterLists.get(1));
        }
        return result;
    }

    //当没有指定tableName时，随机生成表名
    protected String generateTableName(String tableName) {
        return tableName + "_" + this.getId().substring(this.getId().lastIndexOf('-') + 1);
    }

    @Override
    protected void handleParameters(String parameters) {
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return new HashMap<>();
    }

    @Override
    protected boolean applies() {
        return true;
    }
}
