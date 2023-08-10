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

package com.zdpx.coder.operators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.TableInfo;

import static com.zdpx.coder.utils.TableDataStreamConverter.assembleNewTableInfo;
import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

/**
 * 用于端口数据复制, 转为多路输出
 */
public class DuplicateOperator extends Operator {

    @Override
    protected void initialize() {
        registerInputObjectPort(INPUT_0);
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

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        return getFirstParameterMap();
    }

    /**
     * 校验内容： 无
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);

        this.getSceneCode().getGenerateResult().addCheckInformation(model);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Map<String, Object> map) {
        final TableInfo pseudoData =
                getInputPorts().values().stream()
                        .map(t -> (TableInfo) t.getConnection().getFromPort().getPseudoData())
                        .findAny()
                        .orElse(null);

        //从config中获取输出字段,数组的长度只可能是1
        @SuppressWarnings("unchecked")
        Map<String, Object> firstParameterMap = ((List<Map<String, Object>>) getFirstParameterMap().get(CONFIG)).get(0);

        getOutputPorts()
                .values()
                .forEach(t -> {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> o = (List<Map<String, Object>>)firstParameterMap.get(t.getName());
                    ((OutputPortObject<TableInfo>) t).setPseudoData(assembleNewTableInfo(pseudoData, o));
                });
    }
}
