
package com.zdpx.coder.operator.dataSource.es;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.operator.dataSource.AbstractSqlTable;
import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
public class ESSinkOperator extends AbstractSqlTable {

    private static final String KAFKA_SINK = "KafKaSink";

    @Override
    protected void initialize() {
        getInputPorts().put(INPUT_0, new InputPortObject<>(this, INPUT_0));
    }

    @Override
    protected void execute() {
        processLogic(KAFKA_SINK,true,null);
    }
}
