package com.zdpx.coder.json.x6;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zdpx.coder.json.ToInternalConvert;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.utils.InstantiationUtil;

import lombok.Data;

public final class X6ToInternalConvert implements ToInternalConvert {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Scene convert(String origin) {

        Map<String, TempNode> tempNodeMap = new HashMap<>();
        try {
            JsonNode x6 = objectMapper.readTree(origin);
            JsonNode cells = x6.path("cells");

            for (JsonNode cell : cells) {
                String id = cell.get("id").asText();
                JsonNode parent = cell.get("parent");
                ArrayNode children = (ArrayNode) cell.get("children");

                String parentStr = null;
                if (parent != null) {
                    parentStr = parent.asText();
                }

                List<String> childrenList = new ArrayList<>();
                if (children != null) {
                    for (JsonNode child : children) {
                        childrenList.add(child.asText());
                    }
                }

                TempNode tempNode = new TempNode(id, cell, parentStr, childrenList);
                tempNodeMap.put(id, tempNode);
            }

            Map<String, Node> nodes = createNodesWithPackage(tempNodeMap);

            processPackageAndGroup(nodes, tempNodeMap);
            processOperators(nodes, tempNodeMap);
            processConnections(nodes, tempNodeMap);

            ProcessPackage processPackage = new ProcessPackage();
            processPackage.setNodeWrapper(new X6NodeWrapper());

            nodes.values().stream()
                    .filter(node -> node.getNodeWrapper().getParent() == null)
                    .filter(
                            node ->
                                    !node.getClass()
                                            .getName()
                                            .equals("com.zdpx.coder.graph.ProcessGroup"))
                    .collect(Collectors.toList())
                    .forEach(
                            t -> {
                                t.getNodeWrapper().setParent(processPackage);
                                processPackage.getNodeWrapper().getChildren().add(t);
                            });
            Scene scene = new Scene();
            scene.setProcess(processPackage);
            return scene;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 按照从顶层向下初始化垂直信息
     *
     * @param allTempNodes temp nodes
     * @return node dict
     */
    private Map<String, Node> createNodesWithPackage(Map<String, TempNode> allTempNodes) {
        Map<String, Node> nodes = new HashMap<>();
        for (Map.Entry<String, TempNode> tempNode : allTempNodes.entrySet()) {
            Node node;
            JsonNode cell = tempNode.getValue().getNode();
            String cellShape = cell.get("shape").asText();
            switch (cellShape) {
                case "edge":
                    node = new Connection<>();
                    break;
                case "package":
                    node = new ProcessPackage();
                    break;
                case "group-process":
                    node = new ProcessGroup();
                    break;
                case "custom-text-node":
                    continue;
                default:
                    node = createOperatorByCode(cellShape);
            }
            node.setId(tempNode.getKey());
            node.setNodeWrapper(new X6NodeWrapper());
            nodes.put(tempNode.getKey(), node);
        }
        return nodes;
    }

    private static void processPackageAndGroup(
            Map<String, Node> nodes, Map<String, TempNode> tempNodeMap) {
        List<ProcessPackage> processPackages =
                nodes.values().stream()
                        .filter(ProcessPackage.class::isInstance)
                        .map(ProcessPackage.class::cast)
                        .collect(Collectors.toList());

        List<ProcessGroup> processGroups =
                nodes.values().stream()
                        .filter(ProcessGroup.class::isInstance)
                        .map(ProcessGroup.class::cast)
                        .collect(Collectors.toList());

        List<NodeCollection> nodeCollections = new ArrayList<>();
        nodeCollections.addAll(processPackages);
        nodeCollections.addAll(processGroups);

        nodeCollections.forEach(
                t -> {
                    String parentId = tempNodeMap.get(t.getId()).getParent();
                    t.getNodeWrapper().setParent(nodes.get(parentId));

                    List<String> childrenId = tempNodeMap.get(t.getId()).getChildren();
                    List<Node> childrenNode =
                            nodes.values().stream()
                                    .filter(tt -> childrenId.contains(tt.getId()))
                                    .collect(Collectors.toList());

                    childrenNode.forEach(
                            node -> {
                                TempNode tn = tempNodeMap.get(node.getId());
                                String name =
                                        tn.getNode()
                                                .path("attrs")
                                                .path("text")
                                                .path("text")
                                                .asText();
                                node.setName(name);
                                node.getNodeWrapper().setParent((Node) t);
                            });
                    t.getNodeWrapper().setChildren(childrenNode);
                });
    }

    private static void processConnections(
            Map<String, Node> nodes, Map<String, TempNode> tempNodeMap) {
        List<Connection<?>> connections =
                nodes.values().stream()
                        .filter(Connection.class::isInstance)
                        .map(node -> (Connection<?>) node)
                        .collect(Collectors.toList());

        List<String> groupsIds =
                nodes.values().stream()
                        .filter(ProcessGroup.class::isInstance)
                        .map(ProcessGroup.class::cast)
                        .map(ProcessGroup::getId)
                        .collect(Collectors.toList());

        List<Connection<?>> copyConnections = new ArrayList<>(connections);

        Map<String, String> jump = new HashMap<>(); // 不删除原集合中的数据，如果有问题后续再删除
        connections.forEach(
                t -> {
                    if (jump.get(t.getId()) == null) {
                        TempNode tn = tempNodeMap.get(t.getId());
                        JsonNode cell = tn.getNode();

                        // 分组的特殊逻辑，对正常节点没有影响
                        Map<String, String> sourceConnect =
                                findConnect(
                                        copyConnections,
                                        tempNodeMap,
                                        cell.get("source"),
                                        new HashMap<>(),
                                        false,
                                        groupsIds);
                        String sourceCell = sourceConnect.get("cell");
                        String sourcePort = sourceConnect.get("port");
                        jump.putAll(sourceConnect);

                        // 分组的特殊逻辑，对正常节点没有影响
                        Map<String, String> targetConnect =
                                findConnect(
                                        copyConnections,
                                        tempNodeMap,
                                        cell.get("target"),
                                        new HashMap<>(),
                                        true,
                                        groupsIds);
                        String targetCell = targetConnect.get("cell");
                        String targetPort = targetConnect.get("port");
                        jump.putAll(targetConnect);

                        Operator sourceOperator = (Operator) nodes.get(sourceCell);
                        OutputPort<?> outputPort = null;
                        if (sourceOperator.getOutputPorts().containsKey(sourcePort)) {
                            outputPort = sourceOperator.getOutputPorts().get(sourcePort);
                        } else {
                            outputPort = new OutputPortObject<>(sourceOperator, sourcePort);
                        }
                        outputPort.setConnection((Connection) t);

                        Operator targetOperator = (Operator) nodes.get(targetCell);
                        InputPort<?> inputPort = null;
                        if (targetOperator.getInputPorts().containsKey(targetPort)) {
                            inputPort = targetOperator.getInputPorts().get(targetPort);
                        } else {
                            inputPort = new InputPortObject<>(targetOperator, targetPort);
                        }
                        inputPort.setConnection((Connection) t);
                    }
                });
    }

    private void processOperators(Map<String, Node> nodes, Map<String, TempNode> tempNodeMap) {
        List<Operator> operators =
                nodes.values().stream()
                        .filter(Operator.class::isInstance)
                        .map(Operator.class::cast)
                        .collect(Collectors.toList());

        operators.forEach(
                t -> {
                    TempNode tn = tempNodeMap.get(t.getId());
                    String name = tn.getNode().path("attrs").path("text").path("text").asText();
                    t.setName(name);

                    JsonNode data = tn.getNode().get("data");
                    if (data != null) {
                        NodeWrapper nodeWrapper = t.getNodeWrapper();
                        if (data.get("parameters") != null) {
                            String parameters = data.get("parameters").toPrettyString();
                            nodeWrapper.setParameters(parameters);
                        }
                        if (data.get("config") != null) {
                            String config = data.get("config").toPrettyString();
                            nodeWrapper.setConfig(config);
                        }
                        t.setOperatorWrapper(nodeWrapper);
                    }
                });
    }

    public Operator createOperatorByCode(String code) {
        Class<? extends Operator> cl = Scene.OPERATOR_MAP.get(code);
        if (cl == null) {
            String l = String.format("operator %s not exists.", code);
            log.error(l);
            throw new NullPointerException(l);
        }

        return InstantiationUtil.instantiate(cl);
    }

    @Data
    private static class TempNode {

        private final String id;
        private final JsonNode node;
        private final String parent;
        private final List<String> children;

        public TempNode(String id, JsonNode node, String parent, List<String> children) {
            this.id = id;
            this.node = node;
            this.parent = parent;
            this.children = children;
        }
    }
    /**
     * 分组 存在 源节点 id等于当前目标节点的id时，递归获取下一源节点指向的目标节点（跳过），直到找不到符合条件的源节点 source: false -> -in 存在 目标节点
     * id等于当前源节点的id时，递归获取上一目标节点指向的源节点（跳过），直到找不到符合条件的目标节点 target: true -> -in
     */
    public static Map<String, String> findConnect(
            List<Connection<?>> copyConnections,
            Map<String, TempNode> tempNodeMap,
            JsonNode node,
            Map<String, String> map,
            Boolean direction,
            List<String> groupsIds) {
        String cell = node.get("cell").asText(); // node：当遍历后没有匹配时，认为该节点是最终节点
        String port = node.get("port").asText();

        for (int i = 0; i < copyConnections.size(); i++) {

            JsonNode text = tempNodeMap.get(copyConnections.get(i).getId()).getNode();
            // direction: 寻找的方向
            JsonNode t1 = text.get(direction ? "source" : "target"); // ：正向获取

            String groupCell =
                    groupsIds.stream()
                            .filter(t -> t1.get("cell").asText().equals(t))
                            .findFirst()
                            .orElse(null);
            // 认为：找到了穿过组节点的线，或者，穿过组节点后直接连入下一组节点的线
            if ((cell.equals(t1.get("cell").asText())
                            && port.equals(t1.get("port").asText() + "_in"))
                    || (groupCell != null
                            && cell.equals(t1.get("cell").asText())
                            && t1.get("port").asText().equals(port + "_in"))) {
                Map<String, String> connect =
                        findConnect(
                                copyConnections,
                                tempNodeMap,
                                text.get(!direction ? "source" : "target"),
                                map,
                                direction,
                                groupsIds); // 反向获取
                // 进入到这里的线都需要删除(todo 暂不删除连线)
                //
                // connect.put(copyConnections.get(i).getId(),copyConnections.get(i).getId());
                return connect;
            }

            if (i == copyConnections.size() - 1) {
                // 当遍历后 还没有匹配时，认为获取到了最终的节点指向，此条件只可能被执行一次
                map.put("cell", node.get("cell").asText());
                map.put("port", node.get("port").asText());
                return map;
            }
        }
        return null;
    }
}
