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

package com.zdpx.coder.operators.dataSource.oracle;

import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

import java.util.Map;
import java.util.Optional;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.operator.OperatorFeature;
import com.zdpx.coder.operators.dataSource.AbstractSqlTable;

import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
public class OracleSinkOperator extends AbstractSqlTable {

    private static final String ORACLE_SINK = "OracleSink";

    @Override
    protected void initialize() {
        getInputPorts().put(INPUT_0, new InputPortObject<>(this, INPUT_0));
        this.type = "Oracle";
    }

    @Override
    public Optional<OperatorFeature> getOperatorFeature() {
        OperatorFeature operatorFeature = OperatorFeature.builder().icon("icon-xingzhuang").build();
        return Optional.of(operatorFeature);
    }

    @Override
    protected void execute(Map<String, Object> dataModel) {
        processLogic(null, dataModel);
    }

    @Override
    protected String getDefaultName() {
        return ORACLE_SINK;
    }
}
