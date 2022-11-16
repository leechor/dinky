package com.zdpx.coder.operator;

import org.apache.flink.table.functions.UserDefinedFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.utils.NameHelper;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class JoinOperator extends Operator {
    public static final String TEMPLATE =
        String.format("<#import \"%s\" as e>CREATE VIEW ${tableName} AS SELECT <@e.fieldsProcess fieldFunctions/> FROM ${inputTableName} <#if where??>WHERE ${where}</#if>",
            Specifications.TEMPLATE_FILE);

    private InputPortObject<TableInfo> primaryInput;
    private InputPortObject<TableInfo> secondInput;
    private OutputPortObject<TableInfo> outputPort;

    @Override
    protected void initialize() {
        primaryInput = registerInputPort("primaryInput");
        secondInput = registerInputPort("secondInput");
        outputPort = registerOutputPort("output_0");
    }

    @Override
    protected Set<Class<? extends UserDefinedFunction>> declareUdfFunction() {
        return Collections.emptySet();
    }

    @Override
    protected boolean applies() {
        return true;
    }

    @Override
    protected void execute() {
        if (outputPorts.isEmpty() || this.operatorWrapper == null) {
            log.error("JoinOperator information err.");
            return;
        }

        var parameters = getFirstParameterMap();
        var primaryParams = OperatorParameterUtils.getColumns("primary", parameters);
        var broadcastParams = OperatorParameterUtils.getColumns("second", parameters);
        var outputParams = OperatorParameterUtils.getColumns("output", parameters);

        List<Column> cls = new ArrayList<>();
        BiConsumer<Map<String, String>, InputPortObject<TableInfo>> func =
            (Map<String, String> params, InputPortObject<TableInfo> input) ->
                params.forEach((k, v) -> input.getOutputPseudoData().getColumns().stream()
                    .filter(t -> t.getName().equals(k))
                    .findAny()
                    .ifPresent(cls::add));
        func.accept(primaryParams, primaryInput);
        func.accept(broadcastParams, secondInput);

        postOutput(outputPort, NameHelper.generateVariableName("JoinOperator"), cls);
    }
}
