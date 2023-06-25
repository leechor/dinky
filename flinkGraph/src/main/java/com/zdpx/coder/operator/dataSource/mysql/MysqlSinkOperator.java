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

package com.zdpx.coder.operator.dataSource.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.operator.dataSource.AbstractSqlTable;
import com.zdpx.coder.utils.TemplateUtils;

import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
public class MysqlSinkOperator extends AbstractSqlTable {

    public static final String INPUT_0 = "input_0";

    @Override
    protected void initialize() {
        getInputPorts().put(INPUT_0, new InputPortObject<>(this, INPUT_0));
        setName("MysqlSink");
    }

    @Override
    protected void execute() {
        Map<String, Object> dataModel = getDataModel();

        //任意数据源格式转换
        dataModel.put("parameters",formatConversion(dataModel,"dataSink"));

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
        output.removeIf(s -> !(boolean) s.get("flag"));


        String sqlStr = TemplateUtils.format("sink", dataModel, AbstractSqlTable.TEMPLATE);

        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        String sql =
                "INSERT INTO ${outPutTableName} (<#list tableInfo as column>${column.name}<#sep>,</#sep></#list>) "
                        + "SELECT <#list tableInfo as column>${column.name}<#sep>, </#list> "
                        + "FROM ${inPutTableName}";

        @SuppressWarnings("unchecked")
        TableInfo pseudoData =
                ((InputPortObject<TableInfo>) getInputPorts().get(INPUT_0)).getOutputPseudoData();
        if (pseudoData == null) {
            log.warn("{} input table info empty error.", getName());
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("outPutTableName", dataModel.get("tableName"));
        data.put("tableInfo", input);//pseudoData
        data.put("inPutTableName", pseudoData.getName());
        String insertSqlStr = TemplateUtils.format("insert", data, sql);
        this.getSchemaUtil().getGenerateResult().generate(insertSqlStr);
    }
}
