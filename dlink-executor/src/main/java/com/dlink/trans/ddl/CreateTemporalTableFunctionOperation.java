package com.dlink.trans.ddl;

import com.dlink.executor.CustomTableEnvironment;
import com.dlink.executor.CustomTableEnvironmentImpl;
import com.dlink.executor.Executor;
import com.dlink.trans.AbstractOperation;
import com.dlink.trans.Operation;

import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
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
        TemporalTableFunction ttf = customTableEnvironmentImpl.from(temporalTable.getTable())
            .createTemporalTableFunction(temporalTable.getColumns()[0],
                temporalTable.getColumns()[1]);
        customTableEnvironmentImpl.registerFunction(temporalTable.getName(), ttf);
        return null;
    }
}
