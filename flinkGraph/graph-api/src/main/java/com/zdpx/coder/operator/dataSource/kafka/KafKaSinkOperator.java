
package com.zdpx.coder.operator.dataSource.kafka;

import com.zdpx.coder.operator.dataSource.AbstractSqlTable;
import com.zdpx.coder.graph.InputPortObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 *
 */
@Slf4j
public class KafKaSinkOperator extends AbstractSqlTable {

    private static final String KAFKA_SINK = "KafKaSink";

    @Override
    protected void initialize() {
        getInputPorts().put(INPUT_0, new InputPortObject<>(this, INPUT_0));
    }

    @Override
    protected void execute(Map<String, Object> dataModel) {
        processLogic(null, dataModel);
    }

    @Override
    protected String getDefaultName() {
        return KAFKA_SINK;
    }
}
