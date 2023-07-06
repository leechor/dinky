package com.zdpx.coder.graph;

import com.zdpx.coder.operator.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessGroup extends Operator implements NodeCollection {
    private boolean expanded;
    ProcessPackage processPackage = new ProcessPackage();
    // region getter/setter


    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Set<Operator> getOperators() {
        return processPackage.getOperators();
    }

    public Set<ProcessPackage> getProcessPackages() {
        return processPackage.getProcessPackages();
    }

    public void setChildren(Set<Node> originOperatorWrappers) {
        processPackage.setChildren(originOperatorWrappers);
    }

    public void addOperator(Operator operator) {
        processPackage.addOperator(operator);
    }

    public List<Node> getChildren() {
        return processPackage.getChildren();
    }

    public List<Connection> getConnects() {
        return processPackage.getConnects();
    }

    @Override
    protected void initialize() {
        return;
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return new HashMap<>();
    }

    @Override
    protected boolean applies() {
        return false;
    }

    @Override
    protected void execute() {

    }
}
