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

package com.zdpx.coder.operators.dataSource.mysql;

import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

import java.util.Map;
import java.util.Optional;

import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.OperatorFeature;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.operators.dataSource.AbstractSqlTable;

/** */
public class MysqlSourceOperator extends AbstractSqlTable {

    private OutputPortObject<TableInfo> outputPortObject;

    private static final String MYSQL_SOURCE = "MysqlSource";

    @Override
    protected void initialize() {
        outputPortObject = new OutputPortObject<>(this, OUTPUT_0);
        getOutputPorts().put(OUTPUT_0, outputPortObject);
        this.type = "Mysql";
        setName("MySQL数据源");
    }

    @Override
    public Optional<OperatorFeature> getOperatorFeature() {
        OperatorFeature operatorFeature =
                OperatorFeature.builder().icon("icon-xingzhuang1").build();
        return Optional.of(operatorFeature);
    }

    @Override
    protected void execute(Map<String, Object> dataModel) {
        processLogic(outputPortObject, dataModel);
    }

    @Override
    protected String getDefaultName() {
        return MYSQL_SOURCE;
    }
}
