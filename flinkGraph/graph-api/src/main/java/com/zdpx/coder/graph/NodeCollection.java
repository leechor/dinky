package com.zdpx.coder.graph;

import java.util.List;
import java.util.Set;

import com.zdpx.coder.operator.Identifier;
import com.zdpx.coder.operator.Operator;

public interface NodeCollection extends Identifier {
    boolean isExpanded();

    void setExpanded(boolean expanded);

    Set<Operator> getOperators();

    Set<NodeCollection> getProcessPackages();

    Set<NodeCollection> getProcessGroup();

    void setChildren(Set<Node> originOperatorWrappers);

    void addOperator(Operator operator);

    List<Node> getChildren();

    List<Connection> getConnects();

    NodeWrapper getNodeWrapper();
}
