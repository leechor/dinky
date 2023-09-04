import { Graph, Node, Cell } from '@antv/x6';
interface VerifyOperatorItem {
  color: string;
  edge: null | string[];
  operatorErrorMsg: null | string[];
  operatorId: string;
  portInformation: PortInformation | null;
  sqlErrorMsg: string | null;
  tableName: string;
}
interface PortInformation {
  [propName: string]: string[];
}
export default (verifyDatas: VerifyOperatorItem[], graph: Graph) => {
  graph instanceof Graph && setOriginColor(graph);
  graph instanceof Graph &&
    graph.getNodes().forEach((node) => {
      node.prop('previousNodeColor', node.getAttrByPath('body/style'));
    });

  graph instanceof Graph &&
    graph.getEdges().forEach((edge) => {
      edge.setAttrByPath('previousEdgeConfig', edge?.getAttrByPath('line'));
    });
  //检验节点错误信息
  verifyDatas.forEach(({ edge, operatorErrorMsg, operatorId, portInformation, sqlErrorMsg }) => {
    //颜色信息不保存（前端保存初始颜色信息，保存时重新赋值传给后端）

    let color = '#c45d5d';
    let errorMsg = {};
    const cell = graph.getCellById(operatorId);

    if (sqlErrorMsg) {
      // cell.prop("previousNodeColor", cell.getAttrByPath("body/style"))
      //改变节点边框颜色
      cell &&
        cell.attr(
          {
            body: {
              style: {
                'background-color': `${color}`,
                border: '1px solid #949494',
                'border-radius': '2px',
              },
            },
          },
          { overwrite: true },
        );
    }
    if (portInformation) {
      //设置连接桩错误颜色
      Object.keys(portInformation).forEach((portInfo) => {
        // cell.prop("previousPortColor", (cell as Node).getPortProp(portInfo, 'attrs/path'));
        cell &&
          (cell as Node).setPortProp(portInfo, 'attrs/path', {
            fill: color,
            visibility: 'visibility',
          });
      });
      errorMsg['portInformation'] = portInformation;
    }
    if (edge) {
      //设置边错误颜色
      edge.forEach((e) => {
        const edgeFind = graph.getConnectedEdges(cell).find((edge) => edge.id === e);
        if (edgeFind) {
          edgeFind.setAttrByPath('line', {
            stroke: color,
          });
        }
      });
      errorMsg['edge'] = edge;
    }
    errorMsg['sqlErrorMsg'] = sqlErrorMsg ? sqlErrorMsg : null;
    errorMsg['portInformation'] = portInformation ? portInformation : null;
    errorMsg['edge'] = edge ? edge : null;
    if (sqlErrorMsg || portInformation || edge) {
      cell && cell.prop('isError', true);
    }
    cell && cell.prop('errorMsg', errorMsg);
  });
};

export const setOriginColor = (graph: Graph) => {
  //更新节点、边和连接桩为初始颜色配置
  graph.getCells().forEach((cell) => {
    cell.removeProp('errorMsg');
    cell.removeProp('isError');
  });

  graph.getNodes().forEach((node) => {
    const nodeConfig: any = node.prop().previousNodeColor;
    nodeConfig &&
      node.attr(
        {
          body: {
            style: nodeConfig,
          },
        },
        { overwrite: true },
      );
    node.removeProp('previousNodeColor');

    node.getPorts().forEach((portData) => {
      node.removePortProp(portData.id!, 'attrs/path');
    });
  });

  graph.getEdges().forEach((edge) => {
    const edgeConfig: any = edge.getAttrByPath('previousEdgeConfig');
    edgeConfig && edge.setAttrByPath('line', edgeConfig);
    edge.removeAttrByPath('previousEdgeConfig');
  });
};
