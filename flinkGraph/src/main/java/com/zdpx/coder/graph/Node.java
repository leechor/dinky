package com.zdpx.coder.graph;

import com.zdpx.coder.operator.Identifier;
import com.zdpx.coder.operator.OperatorFeature;
import com.zdpx.coder.operator.Parameters;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public abstract class Node implements Identifier {
    protected String id;
    protected String name;

    protected Parameters parameters = new Parameters();
    protected NodeWrapper nodeWrapper;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<OperatorFeature> getOperatorFeature() {
        return Optional.empty();
    }

    public NodeWrapper getNodeWrapper() {
        return nodeWrapper;
    }

    public void setNodeWrapper(NodeWrapper nodeWrapper) {
        this.nodeWrapper = nodeWrapper;
    }

    @Override
    public String getSpecification() {
        return null;
    }
}
