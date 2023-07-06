package com.zdpx.coder.graph;

import com.zdpx.coder.operator.Operator;

import java.util.List;
import java.util.Set;

public interface NodeCollection {
    boolean isExpanded();

    void setExpanded(boolean expanded);

    Set<Operator> getOperators();

    Set<ProcessPackage> getProcessPackages();

    void setChildren(Set<Node> originOperatorWrappers);

    void addOperator(Operator operator);

    List<Node> getChildren();

    List<Connection> getConnects();
}
