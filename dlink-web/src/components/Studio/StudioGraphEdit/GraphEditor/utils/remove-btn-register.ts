import { l } from '@/utils/intl';
import { Graph, Cell, Edge } from '@antv/x6';
import { Modal } from 'antd';

export const removeBtnNodeRegister = (graph: Graph) => {
  Graph.unregisterNodeTool('rm-btn');
  Graph.registerNodeTool('rm-btn', {
    inherit: 'button-remove',
    onClick({ cell }: { cell: Cell }) {
      //清除下游直连node 的config
      let outEdges = graph.model.getOutgoingEdges(cell);
      if (outEdges) {
        for (let outEdge of outEdges) {
          let targetNode = outEdge.getTargetNode();
          targetNode?.setData(
            { ...(targetNode?.getData() ? targetNode.getData() : {}), config: [] },
            { overwrite: true },
          );
        }
      }
      //清除node所有的边
      graph.model.removeConnectedEdges(cell);
      //删除该节点
      graph.model.removeCell(cell);
    },
  });
};
export const removeBtnEdgeRegister = (graph: Graph) => {
  Graph.unregisterEdgeTool('rm-edge-btn');
  Graph.registerEdgeTool('rm-edge-btn', {
    inherit: 'button-remove',
    onClick({ cell }: { cell: Edge }) {
      const { confirm } = Modal;
      confirm({
        title: l("graph.removebtn.confirm.delete.edge"),
        content: l("graph.removebtn.delete.nodeinfo"),
        okType: 'danger',
        okText: l("graph.removebtn.oktext.yes"),
        cancelText: l("graph.removebtn.canceltext.no"),
        onOk() {
          graph.removeEdge(cell);
        },
      });
    },
  });
};
