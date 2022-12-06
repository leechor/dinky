package com.zdpx.coder.operator;

import static com.zdpx.coder.Specifications.TABLE_ENV;
import static com.zdpx.coder.Specifications.TABLE_NAME;

import java.util.Collections;
import java.util.Map;

import com.zdpx.coder.code.CodeJavaBuilder;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.utils.NameHelper;

/**
 *
 */
public class TableConvertToDataStreamOperator extends Operator {

    private OutputPortObject<TableInfo> outputPortObject;

    @Override
    protected void initialize() {
        parameters.getParameterList().add(new Parameter(TABLE_NAME));
        outputPortObject = registerOutputPort("output_0)");
        registerInputPort("input_0");
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
        String tn = NameHelper.generateVariableName(parameters.getParameterByName(TABLE_NAME));
        if (!(this.getSchemaUtil().getGenerateResult() instanceof CodeJavaBuilder)) {
            return;
        }

        var gjr = (CodeJavaBuilder) this.getSchemaUtil().getGenerateResult();
        gjr.getCodeContext().getMain().addStatement("DataStream<Row> $2L = $1L.toDataStream($1L.sqlQuery(\"select * from $2L\"))",
                TABLE_ENV, tn)
            .addCode(System.lineSeparator());

        var pseudoData = inputPorts.stream()
            .map(t -> t.getConnection().getFromPort().getPseudoData())
            .findAny()
            .orElse(null);

        outputPortObject.setPseudoData((TableInfo) pseudoData);
    }
}
