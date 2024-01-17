package com.zdpx.coder.operators.dataSource.kafka;

import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

import java.util.Map;
import java.util.Optional;

import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.operator.OperatorFeature;
import com.zdpx.coder.operators.dataSource.AbstractSqlTable;

import lombok.extern.slf4j.Slf4j;

/** */
@Slf4j
public class KafKaSinkOperator extends AbstractSqlTable {

    private static final String KAFKA_SINK = "KafKaSink";

    @Override
    protected void initialize() {
        getInputPorts().put(INPUT_0, new InputPortObject<>(this, INPUT_0));
    }

    @Override
    public Optional<OperatorFeature> getOperatorFeature() {
        OperatorFeature operatorFeature = OperatorFeature.builder().icon("icon-Kafka1").build();
        return Optional.of(operatorFeature);
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
