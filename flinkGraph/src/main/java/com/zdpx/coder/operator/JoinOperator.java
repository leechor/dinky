package com.zdpx.coder.operator;

import org.apache.flink.table.functions.UserDefinedFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.utils.NameHelper;
import com.zdpx.coder.utils.TemplateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class JoinOperator extends Operator {
    public static final String TEMPLATE =
        String.format("<#import \"%s\" as e>CREATE VIEW ${tableName} AS " +
                "SELECT <@e.fieldsProcess fieldFunctions/> " +
                "FROM ${inputTableName} " +
                "${joinType?upper_case} JOIN ${anotherTableName} " +
                "<#if systemTimeColumn??>FOR SYSTEM_TIME AS OF ${systemTimeColumn}</#if> " +
                "<#if where??>WHERE ${where}</#if> " +
                "<#if onLeftColumn??>ON ${onLeftColumn} = ${onRightColumn}</#if>",
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
        var joinType = getNestValue(parameters, "/join/type").textValue();
        var forSystemTime = getNestValue(parameters, "/systemTimeColumn").textValue();
        var onLeftColumn = getNestValue(parameters, "/on/leftColumn").textValue();
        var onRightColumn = getNestValue(parameters, "/on/rightColumn").textValue();

        List<Column> cls = new ArrayList<>();
        BiConsumer<Map<String, String>, InputPortObject<TableInfo>> func =
            (Map<String, String> params, InputPortObject<TableInfo> input) ->
                params.forEach((k, v) -> input.getOutputPseudoData().getColumns().stream()
                    .filter(t -> t.getName().equals(k))
                    .findAny()
                    .ifPresent(cls::add));
        func.accept(primaryParams, primaryInput);
        func.accept(broadcastParams, secondInput);

        var outputTableName = NameHelper.generateVariableName("JoinOperator");
        var primaryTableName = primaryInput.getOutputPseudoData().getName();
        var secondTableName = secondInput.getOutputPseudoData().getName();
        List<FieldFunction> ffsPrimary = getFieldFunctions(primaryTableName, getNestMapValue(parameters, "/primaryInput"));
        List<FieldFunction> ffsSecond = getFieldFunctions(secondTableName, getNestMapValue(parameters, "/secondInput"));
        ffsPrimary.addAll(ffsSecond);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("tableName", outputTableName);
        dataModel.put("inputTableName", primaryTableName);
        dataModel.put("anotherTableName", secondTableName);
        dataModel.put(FIELD_FUNCTIONS, ffsPrimary);
        dataModel.put("joinType", joinType);
        dataModel.put("systemTimeColumn", FieldFunction.insertTableName(primaryTableName, null, forSystemTime));
        dataModel.put("onLeftColumn", FieldFunction.insertTableName(primaryTableName, null, onLeftColumn));
        dataModel.put("onRightColumn", FieldFunction.insertTableName(secondTableName, null, onRightColumn));

        var sqlStr = TemplateUtils.format(this.getName(), dataModel, TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        postOutput(outputPort, outputTableName, cls);
    }
}
