package com.zdpx.coder.graph;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public abstract class NodeWrapper {
    private String parameters;
    private String config;//保存算子input节点的字段勾选信息，类型： List<Map<String, List<Map<String,Object>>>>
    private Node parent;
    private List<Node> children = new ArrayList<>();
}
