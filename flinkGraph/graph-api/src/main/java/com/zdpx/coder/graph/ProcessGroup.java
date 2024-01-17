package com.zdpx.coder.graph;

import java.util.stream.Collectors;

import com.zdpx.coder.operator.Operator;

public class ProcessGroup extends Operator implements NodeCollection {
    private boolean expanded;

    // region getter/setter

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Set<Operator> getOperators() {
        return getNodeWrapper().getChildren().stream()
                .filter(Operator.class::isInstance)
                .map(t -> (Operator) t)
                .collect(Collectors.toSet());
    }

    public Set<NodeCollection> getProcessPackages() {
        return getNodeWrapper().getChildren().stream()
                .filter(ProcessPackage.class::isInstance)
                .map(t -> (ProcessPackage) t)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NodeCollection> getProcessGroup() {
        return getNodeWrapper().getChildren().stream()
                .filter(ProcessGroup.class::isInstance)
                .map(t -> (ProcessGroup) t)
                .collect(Collectors.toSet());
    }

    public void setChildren(Set<Node> originOperatorWrappers) {
        getNodeWrapper().setChildren(new ArrayList<>(originOperatorWrappers));
    }

    public void addOperator(Operator operator) {
        getNodeWrapper().getChildren().add(operator);
    }

    public List<Node> getChildren() {
        return getNodeWrapper().getChildren();
    }

    public List<Connection> getConnects() {
        return getNodeWrapper().getChildren().stream()
                .filter(Connection.class::isInstance)
                .map(t -> (Connection) t)
                .collect(Collectors.toList());
    }

    @Override
    protected void initialize() {}

    @Override
    protected Map<String, String> declareUdfFunction() {
        return new HashMap<>();
    }

    @Override
    protected boolean applies() {
        return false;
    }

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        return null;
    }

    @Override
    protected void generateCheckInformation(Map<String, Object> map) {}

    @Override
    protected void execute(Map<String, Object> result) {}
}
