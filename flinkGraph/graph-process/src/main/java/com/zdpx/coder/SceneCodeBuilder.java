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

package com.zdpx.coder;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.zdpx.coder.code.CodeBuilder;
import com.zdpx.coder.code.CodeJavaBuilderImpl;
import com.zdpx.coder.code.ICodeContext;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.code.CodeSqlBuilderImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置场景生成操作类
 *
 * @author Licho Sun
 */
@Slf4j
public class SceneCodeBuilder implements SceneCode {
    // 自定义函数操作算子集
    private Map<String, String> udfFunctionMap = new HashMap<>();

    private CodeBuilder codeBuilder;
    private final Scene scene;

    public SceneCodeBuilder(Scene scene) {
        this.scene = scene;
        if (scene.getEnvironment().getResultType() == ResultType.JAVA) {
            ICodeContext ICodeContext = createCodeContext(scene);
            codeBuilder = new CodeJavaBuilderImpl(ICodeContext);
        } else {
            codeBuilder = new CodeSqlBuilderImpl();
        }
    }

    @Override
    public CodeBuilder getGenerateResult() {
        return codeBuilder;
    }

    @Override
    public void setGenerateResult(CodeBuilder codeBuilder) {
        this.codeBuilder = codeBuilder;
    }

    @Override
    public Map<String, String> getUdfFunctionMap() {
        return udfFunctionMap;
    }

    @Override
    public void setUdfFunctionMap(Map<String, String> udfFunctionMap) {
        this.udfFunctionMap = udfFunctionMap;
    }

    /**
     * 根据场景配置生成可执行的java源文件
     *
     * @throws IOException ignore
     */
    @Override
    public Map<String, Object> build() {
        if (codeBuilder == null) {
            throw new IllegalStateException(String.format("Code Builder %s empty!", codeBuilder));
        }

        codeBuilder.firstBuild();
        createOperatorsCode();
        String sql = codeBuilder.lastBuild();
        Map<String, Object> outputMap = codeBuilder.getCheckInformation();
        outputMap.put("SQL", sql);
        return outputMap;
    }

    /**
     * 广度优先遍历计算节点, 生成相对应的源码
     */
    private void createOperatorsCode() {
        List<Operator> sinkOperatorNodes =
                Scene.getSinkOperatorNodes(this.scene.getProcess());
        List<Operator> sinks = new ArrayList<>(sinkOperatorNodes);
        Deque<Operator> ops = new ArrayDeque<>();

        bft(new HashSet<>(sinks), ops::push);
        ops.stream().distinct().forEach(this::operate);
    }


    /**
     * 广度优先遍历计算节点, 执行call 函数
     *
     * @param operators 起始节点集
     * @param call      待执行函数
     */
    private void bft(Set<Operator> operators, Consumer<Operator> call) {
        if (operators.isEmpty()) {
            return;
        } else if (operators.size() == 1) {
            Operator o = operators.iterator().next();
            if (o.getInputPorts().size() == 0 && o.getOutputPorts().size() == 0) {
                call.accept(o);
                return;
            }
        }

        List<Operator> ops =
                operators.stream()
                        .sorted(Comparator.comparing(Operator::getId, Comparator.naturalOrder()))
                        .collect(Collectors.toList());
        final Set<Operator> preOperators = new HashSet<>();
        for (Operator op : ops) {
            if (op.getInputPorts().size() != 0 || op.getOutputPorts().size() != 0) {
                call.accept(op);
                op.getInputPorts().values().stream()
                        .filter(t -> !Objects.isNull(t.getConnection()))
                        .map(t -> t.getConnection().getFromPort())
                        .forEach(fromPort -> preOperators.add(fromPort.getParent()));
            } else {
                preOperators.add(op);
            }
        }
        bft(preOperators, call);
    }

    /**
     * 执行每个宏算子的代码生成逻辑
     *
     * @param op 宏算子
     */
    private void operate(Operator op) {
        op.setSceneCode(this);
        op.run();
    }

    public ICodeContext createCodeContext(Scene scene) {
        return CodeContext.newBuilder(scene.getEnvironment().getName()).scene(scene).build();
    }

}
