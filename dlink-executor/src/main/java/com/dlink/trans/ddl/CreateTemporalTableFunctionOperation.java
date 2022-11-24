package com.dlink.trans.ddl;

import com.dlink.executor.CustomTableEnvironment;
import com.dlink.executor.CustomTableEnvironmentImpl;
import com.dlink.executor.Executor;
import com.dlink.trans.AbstractOperation;
import com.dlink.trans.Operation;

import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.expressions.Expression;
import org.apache.flink.table.expressions.ValueLiteralExpression;
import org.apache.flink.table.functions.TemporalTableFunction;

/**
 *
 */
public class CreateTemporalTableFunctionOperation extends AbstractOperation implements Operation {
    private static final String KEY_WORD = "CREATE TTF";

    public CreateTemporalTableFunctionOperation() {
    }

    public CreateTemporalTableFunctionOperation(String statement) {
        super(statement);
    }

    @Override
    public String getHandle() {
        return KEY_WORD;
    }

    @Override
    public Operation create(String statement) {
        return new CreateTemporalTableFunctionOperation(statement);
    }

    @Override
    public TableResult build(Executor executor) {
        TemporalTable temporalTable = TemporalTable.build(statement);
        CustomTableEnvironment env = executor.getCustomTableEnvironment();
        CustomTableEnvironmentImpl customTableEnvironmentImpl = ((CustomTableEnvironmentImpl)env);
        Expression expression1 = new ValueLiteralExpression(temporalTable.getColumns()[0]);
        Expression expression2 = new ValueLiteralExpression(temporalTable.getColumns()[1]);
        TemporalTableFunction ttf = customTableEnvironmentImpl.from(temporalTable.getTable())
            .createTemporalTableFunction(expression1, expression2);
        customTableEnvironmentImpl.createTemporarySystemFunction(temporalTable.getName(), ttf);
        return null;
    }
}
