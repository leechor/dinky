package com.zdpx.coder.operator;

import java.util.Map;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;

/**
 * 用于端口数据复制, 转为多路输出
 */
public class DuplicateOperator extends Operator {

    @Override
    protected void initialize() {
        final var inputPortInfo = new InputPortObject<TableInfo>(this, "input_0");
        inputPorts.add(inputPortInfo);

    }

    @Override
    protected void handleParameters(String parameters) {
        if (outputPorts.isEmpty() && this.operatorWrapper != null) {
            var outputInfo = getParameterLists(parameters);

            for (var oi : outputInfo) {
                var opi = new OutputPortObject<TableInfo>(this, oi.get("outputName").toString());
                outputPorts.add(opi);
            }
        }
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return Map.of();
    }

    @Override
    protected boolean applies() {
        return true;
    }

    @Override
    protected void execute() {

        var pseudoData = inputPorts.stream()
            .map(t -> t.getConnection().getFromPort().getPseudoData())
            .findAny()
            .orElse(null);
        outputPorts.forEach(t -> t.setPseudoData(pseudoData));
    }
}
