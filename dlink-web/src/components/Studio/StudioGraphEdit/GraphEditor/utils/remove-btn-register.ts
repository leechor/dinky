import { Graph,Cell } from '@antv/x6';
export default (graph:Graph) => {
    Graph.unregisterNodeTool("rm-btn")
    Graph.registerNodeTool("rm-btn", {
        inherit: "button-remove",
        onClick({ cell }: { cell: Cell }) {
            //清除下游直连node 的config
            let outEdges = graph.model.getOutgoingEdges(cell);
            if (outEdges) {
                for (let outEdge of outEdges) {
                    let targetNode = outEdge.getTargetNode()
                    targetNode?.setData({ ...(targetNode?.getData() ? targetNode.getData() : {}), config: [] }, { overwrite: true })
                }
            }
            //清除node所有的边
            graph.model.removeConnectedEdges(cell)
            //删除该节点
            graph.model.removeCell(cell)
        }
    })
}