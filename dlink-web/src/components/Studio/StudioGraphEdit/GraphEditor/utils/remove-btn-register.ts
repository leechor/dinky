import { Graph, Cell, Edge } from '@antv/x6';
export const removeBtnNodeRegister = (graph: Graph) => {
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
export const removeBtnEdgeRegister = (graph: Graph) => {
    Graph.unregisterEdgeTool("rm-edge-btn")
    Graph.registerEdgeTool("rm-edge-btn", {
        inherit: "button-remove",
        onClick({ cell }: { cell: Edge }) {
            
            //清除下游直连node 的config
            const sourceNode = cell.getSourceNode();
            const sourcePort = cell.getSourcePortId();
            const targetNode = cell.getTargetNode();
            const targetPort = cell.getTargetPortId();
            let id = `${sourceNode?.id}&${sourcePort} ${targetNode?.id}&${targetPort}`
            //删除上下游连线相关id的config、
            let sourceConfig = sourceNode?.getData()
            let targetConfig = targetNode?.getData()

            if (sourceConfig) {
                if (sourceConfig["config"].length) {
                    delete sourceConfig["config"][0][id]
                }
            }
            if (targetConfig) {
                if (targetConfig["config"].length) {
                    delete targetConfig["config"][0][id]
                }
            }
            graph.removeEdge(cell)

        }
    })
}