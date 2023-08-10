
package com.zdpx.coder.operator.dataSource.customer;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.operator.dataSource.AbstractSqlTable;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 *
 */
@Slf4j
public class CustomerSinkOperator extends AbstractSqlTable {

    private static final String CUSTOMER_SINK = "customerSink";

    @Override
    protected void initialize() {
        getInputPorts().put(INPUT_0, new InputPortObject<>(this, INPUT_0));
    }

    @Override
    protected void execute(Map<String, Object> dataModel) {
        processLogic( null, dataModel);
    }

    @Override
    protected String getDefaultName() {
        return CUSTOMER_SINK;
    }
}
