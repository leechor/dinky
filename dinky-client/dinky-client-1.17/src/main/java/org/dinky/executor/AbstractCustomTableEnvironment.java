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

package org.dinky.executor;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.bridge.java.internal.StreamTableEnvironmentImpl;
import org.apache.flink.table.delegation.ExtendedOperationExecutor;
import org.apache.flink.table.delegation.Planner;
import org.apache.flink.table.planner.delegation.ParserImpl;
import org.apache.flink.table.planner.delegation.PlannerBase;

import cn.hutool.core.util.ReflectUtil;
import org.apache.flink.table.planner.parse.ExtendedParseStrategy;
import org.dinky.utils.DinkyReflectUtil;

import java.util.ArrayList;
import java.util.List;

/** */
public abstract class AbstractCustomTableEnvironment
        implements CustomTableEnvironment, DefaultTableEnvironmentInternal, DefaultStreamTableEnvironment {

    protected StreamTableEnvironment streamTableEnvironment;

    protected AbstractCustomTableEnvironment() {}

    protected AbstractCustomTableEnvironment(StreamTableEnvironment streamTableEnvironment) {
        this.streamTableEnvironment = streamTableEnvironment;
    }

    @Override
    public TableEnvironment getTableEnvironment() {
        return streamTableEnvironment;
    }

    public StreamExecutionEnvironment getStreamExecutionEnvironment() {
        return ((StreamTableEnvironmentImpl) streamTableEnvironment).execEnv();
    }

    public Planner getPlanner() {
        return ((StreamTableEnvironmentImpl) streamTableEnvironment).getPlanner();
    }

    public void injectParseStrategies(List<ExtendedParseStrategy> extendedParseStrategies) {
        Planner planner = getPlanner();
        DinkyReflectUtil.removeFinalStaticField(ParserImpl.class, "PARSE_STRATEGIES");
        List<ExtendedParseStrategy> parseStrategies =
                (List<ExtendedParseStrategy>) ReflectUtil.getFieldValue(planner.getParser(), "PARSE_STRATEGIES");
        List<ExtendedParseStrategy> newParseStrategies = new ArrayList<>(parseStrategies);
        for (ExtendedParseStrategy extendedParseStrategy : extendedParseStrategies) {
            if (!newParseStrategies.contains(extendedParseStrategy)){
                newParseStrategies.add(extendedParseStrategy);
            }
        }

        ReflectUtil.setFieldValue(planner.getParser(), "PARSE_STRATEGIES", newParseStrategies);
    }

    @Override
    public void injectExtendedExecutor(CustomExtendedOperationExecutor extendedExecutor) {
        PlannerBase plannerBase = (PlannerBase) getPlanner();
        ExtendedOperationExecutor extendedOperationExecutor =
                new ExtendedOperationExecutorWrapper(plannerBase.getExtendedOperationExecutor(), extendedExecutor);

        ReflectUtil.setFieldValue(getPlanner(), "extendedOperationExecutor", extendedOperationExecutor);
    }

    @Override
    public Configuration getRootConfiguration() {
        return (Configuration) this.getConfig().getRootConfiguration();
    }
}
