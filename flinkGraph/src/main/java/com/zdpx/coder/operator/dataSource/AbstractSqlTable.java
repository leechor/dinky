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

package com.zdpx.coder.operator.dataSource;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.utils.TemplateUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.TableDataStreamConverter;

/** */
public abstract class AbstractSqlTable extends Operator {
    public static final String TEMPLATE =
            "CREATE TABLE ${tableName} ("
                    + "<#list columns as column>`${column.name}` ${column.type}<#sep>,"
                    + "</#list>"
                    + "<#if watermark??>, WATERMARK FOR `${watermark.column}` AS `${watermark.column}`<#if watermark.timeSpan??> - INTERVAL ' ${watermark.timeSpan} ' ${watermark.timeUnit}</#if></#if>"
                    + "<#if primary??>, PRIMARY KEY (${primary}) NOT ENFORCED</#if>"
                    + ")"
                    + "WITH ("
                    + "<#list parameters as key, value>"
                    + "'${(key == \"tableName\")?then(\"table-name\", key)}' = '${value}'<#sep>, "
                    + "</#list>)";


    public static final String INPUT_SQL =
            "INSERT INTO ${outPutTableName} (<#list columns as column>`${column.name}`<#sep>,</#sep></#list>) "
                    + "SELECT <#list tableInfo as column>`${column.name}`<#sep>, </#list> "
                    + "FROM ${inPutTableName}";
    protected TableInfo tableInfo;

    protected static final String PARAMETERS = "parameters";

    protected static final String INPUT_0 = "input_0";
    protected static final String OUTPUT_0 = "output_0";
    protected static final String FLAG = "flag";
    protected static final String COLUMNS = "columns";



    //重复代码提取  source:false  sink:true
    public void processLogic(String tableName, boolean flag, OutputPortObject<TableInfo> outputPortObject){

        Map<String, Object> dataModel = getDataModel(tableName);

        //任意数据源格式转换
        dataModel.put(PARAMETERS,formatConversion(dataModel));
        dataModel.put("tableName",generateTableName(dataModel.get("tableName").toString()));

        if(flag){ //sink
            List<Map<String, Object>> input = formatProcessingSink(dataModel);
            connectToSink(INPUT_0,dataModel,input);
        }else{ //source
            //删除没有勾选的字段
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> columns = (List<Map<String, Object>>) dataModel.get(COLUMNS);
            columns.removeIf(s -> !(boolean) s.get(FLAG));
            Map<String, Object> parameters = getParameterLists().get(0); // [ parameters , config ]
            final TableInfo ti = TableDataStreamConverter.getTableInfo(parameters);

            if(ti.getName()==null){
                ti.setName(dataModel.get("tableName").toString());
            }else{
                ti.setName(generateTableName(ti.getName()));
            }

            outputPortObject.setPseudoData(ti);
        }

        String sqlStr = TemplateUtils.format(tableName, dataModel, TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

    }

    //兼容任意类型的数据源
    protected Map<String, Object> formatConversion(Map<String, Object> dataModel){
        @SuppressWarnings("unchecked")
        Map<String, Object> defineList = (Map<String, Object>) dataModel.get("parameters");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> other = (List<Map<String, Object>>) defineList.get("other");
        if(other.size()>0){
            for(Map<String, Object> m:other){
                defineList.put(m.get("key").toString(),m.get("values"));
            }
        }
        defineList.remove("other");
        return defineList;
    }

    //数据汇内部格式处理，包括输入的获取和勾选字段的设置
    public List<Map<String, Object>> formatProcessingSink(Map<String, Object> dataModel){
        //从config中获取输入
        @SuppressWarnings("unchecked")
        List<Map<String, List<Map<String,Object>>>> config = (List<Map<String, List<Map<String,Object>>>>) dataModel.get("config");
        List<Map<String, Object>> input = new ArrayList<>();
        for(Map<String, List<Map<String,Object>>> map:config){
            for(Map.Entry<String, List<Map<String,Object>>> m2:map.entrySet()){
                List<Map<String, Object>> value = m2.getValue();
                for(Map<String, Object> l:value){
                    if((boolean)l.get("flag")){
                        input.add(l);
                    }
                }
            }
        }
        //删除没有勾选的字段
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> output = (List<Map<String, Object>>) dataModel.get("columns");
        for(Map<String, Object> s:output){
            if(!(boolean)s.get("flag")){
                output.remove(s);
            }
        }
        return input;
    }

    //配置连接到sink的input语句
    public void connectToSink(String input_0,Map<String, Object> dataModel,List<Map<String, Object>> input){
        @SuppressWarnings("unchecked")
        TableInfo pseudoData =
                ((InputPortObject<TableInfo>) getInputPorts().get(input_0)).getOutputPseudoData();
        if (pseudoData != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("outPutTableName", dataModel.get("tableName"));
            data.put("tableInfo", input);//pseudoData
            data.put("inPutTableName", pseudoData.getName());
            data.put("columns", dataModel.get("columns"));
            String insertSqlStr = TemplateUtils.format("insert", data, INPUT_SQL);
            this.getSchemaUtil().getGenerateResult().generate(insertSqlStr);
        }
    }

    protected Map<String, Object> getDataModel(String tableName) {
        final String columns = "columns";
        String parameters = "parameters";

        List<Map<String, Object>> parameterLists = getParameterLists();
        Map<String, Object> psFirst = parameterLists.get(0);

        Map<String, Object> result = new HashMap<>();
        result.put(parameters, new HashMap<String, Object>());
        result.put("tableName",tableName);
        if(psFirst.get("tableName")!=null){
            result.put("tableName", psFirst.get("tableName") );
        }
        String primary = psFirst.get("primary").toString();
        if(primary!=null&&!primary.equals("")){
            result.put("primary", primary );
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> watermark = (Map<String, Object>)psFirst.get("watermark");
        if(watermark.get("column")!=null&&!watermark.get("column").equals("")){
            if((Integer)watermark.get("timeSpan")==0){
                watermark.remove("timeSpan");
            }
            result.put("watermark", watermark);
        }

        psFirst.remove("primary");
        psFirst.remove("watermark");

        for (Map.Entry<String, Object> m : psFirst.entrySet()) {
            if (m.getKey().equals(columns)) {
                result.put(columns, m.getValue());
                continue;
            }
            @SuppressWarnings("unchecked")
            HashMap<String, Object> ps = (HashMap<String, Object>) result.get(parameters);
            ps.put(m.getKey(), m.getValue());
        }
        if(parameterLists.size()>1){//文件中存在config
            result.putAll(parameterLists.get(1));
        }
        return result;
    }

    //适用于tableName不为空的数据源
    protected String generateTableName(String tableName) {
        return tableName + "_" + this.getId().substring(this.getId().lastIndexOf('-') + 1);
    }

    @Override
    protected void handleParameters(String parameters) {
        List<Map<String, Object>> pl = getParameterLists(parameters);
        if (CollectionUtils.isEmpty(pl)) {
            return;
        }
        tableInfo = TableDataStreamConverter.getTableInfo(getParameterLists(parameters).get(0));
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
