import { Cell, Edge, Graph, Model, Node, Shape } from '@antv/x6';
import loadPlugin from './plugin';
import { removeBtnEdgeRegister, removeBtnNodeRegister } from './remove-btn-register';
import CustomShape, { GraphHistory, PreNodeInfo, PortTypeConst } from './cons';
import store from '../store';
import {
  addGraphTabs,
  changeCurrentSelectNode,
  changeCurrentSelectNodeName,
  changeGraph,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import React from 'react';
import {
  getGraphViewSize,
  getPointsBox,
  PreNodeRect,
  shrinkGroupNode,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/graph-helper';
import { getCustomGroupInfo } from '@/components/Common/crud';
import { warningTip } from '../views/home/cpns/left-editor';
import { getSourceTargetByEdge, isCustomTextNode, isGroupProcess } from './graph-tools-func';

const setParentAndSelection = (graph: Graph, preGroupNodeId: string, node: Cell) => {
  if (preGroupNodeId) {
    const currentGroup = graph.getCellById(preGroupNodeId);
    currentGroup.insertChild(node);

    //新加进来的节点可选择
    graph.setSelectionFilter((cell) => {
      return currentGroup
        .getChildren()!
        .map((c) => c.id)
        .includes(cell.id);
    });
  }
};
const cloneSubCells = (cell: Cell, graph: Graph) => {
  const tabs = store.getState().home.graphTabs;
  const currentGroupId = tabs[tabs.length - 1];
  let addedCell: Cell[] = [];

  if (!isGroupProcess(cell) && cell.shape.includes('custom-node')) {
    getCustomGroupInfo(`/api/zdpx/customer/${cell.prop().name!}`).then((res) => {
      if (res.code === 0) {
        graph.removeCell(cell);
        const { cells } = JSON.parse(res.datas);
        cells.map((c: Cell) => {
          const node = graph.addNode(c as Node);
          node.prop('name', cell.prop().name!);
          const { x, y } = (cell as Node).position();
          node.setPosition(x, y, { relative: true, deep: true });
          addedCell.push(node);
        });
        const clonedCells = graph.cloneCells(addedCell);
        Object.values(clonedCells).forEach((cell) => {
          //考虑到组节点父子关系与位置关系，再次进行处理,同时新加进来的节点可选择
          graph.addCell(cell);
          setParentAndSelection(graph, currentGroupId.groupCellId, cell);
        });
        graph.removeCells(addedCell);
      }
      warningTip(res.code, res.msg);
    });
  }

  if (
    isGroupProcess(cell) &&
    cell.prop().name &&
    cell.prop().isStencil !== undefined
  ) {
    let addedCells: Cell[] = [];
    let addedGroup: Cell;
    //请求该节点对应参数
    getCustomGroupInfo(`/api/zdpx/customer/${cell.prop().name!}`).then((res) => {
      if (res.code === 0) {
        graph.removeCell(cell);
        const { cells } = JSON.parse(res.datas);
        cells.map((c: Cell) => {
          if (c.shape === 'edge') {
            const edge = graph.addEdge(c);
            addedCells.push(edge);
          } else {
            const node = graph.addNode(c as Node);
            if (isGroupProcess(node) && !node.hasParent()) {
              node.prop('name', cell.prop().name!);
              const { x, y } = (cell as Node).position();
              node.setPosition(x, y, { relative: true, deep: true });
              node.prop(PreNodeInfo.PREVIOUS_NODE_RECT, {
                ...node.position({ relative: true }),
                ...node.size(),
              });
              //将新增组节点设置为当前组节点的子节点

              if (currentGroupId.groupCellId) {
                const currentGroup = graph.getCellById(currentGroupId.groupCellId);
                currentGroup.insertChild(node);
              }
              addedGroup = node;
              addedCells.push(node);
            }
          }
        });
        //克隆子图，删除原先子图
        const clonedCells = graph.cloneSubGraph([addedGroup], { deep: true });
        Object.values(clonedCells).forEach((cell) => {
          //考虑到组节点父子关系与位置关系，再次进行处理
          graph.addCell(cell);
          if (isGroupProcess(cell) && !cell.hasParent()) {
            //将新增组节点设置为当前组节点的子节点
            setParentAndSelection(graph, currentGroupId.groupCellId, cell);
          }
        });
        graph.removeCells(addedCells);
      }
      warningTip(res.code, res.msg);
    });
  }

  if (isCustomTextNode(cell)) {
    setParentAndSelection(graph, currentGroupId.groupCellId, cell);
  }
};

const setNodeAndEdgeAttrs = (node: Node, hasInsertsection: boolean, graph: Graph) => {
  node.toFront();
  node.attr('body', {
    style: {
      'box-shadow': hasInsertsection ? 'rgb(255 255 255) 0px -3px 10px 12px' : null,
    },
  });
  const edges = graph.getConnectedEdges(node);
  const lineAttrs = hasInsertsection
    ? {
      stroke: "#b2a2e9",
      filter: {
        name: "outline",
        args: {
          width: 2,
          color: "#ffffff",
          blur: 5,
          opacity: 0.8,
          margin: 0
        }
      }
    }
    : {
      filter: null,
      stroke: '#b2a2e9',
      strokeWidth: 2,
      targetMarker: '',
    };
  for (const edge of edges) {
    edge.toFront();
    edge.setAttrByPath('line', lineAttrs);
  }
};

const getOhterTextNode = (node: Node, graph: Graph) => {
  return graph
    .getNodes()
    .filter((n) => n.id !== node.id)
    .filter((n) => isCustomTextNode(n));
};

const isNodeIntersect = (node: Node, otherTextNodes: Node[], graph: Graph) => {
  const box1 = node.getBBox();
  for (const otherNode of otherTextNodes) {
    const box2 = otherNode.getBBox();
    if (
      box1.x < box2.x + box2?.width &&
      box1.x + box1.width > box2.x &&
      box1.y < box2.y + box2.height &&
      box1.y + box1.height > box2.y
    ) {
      return true;
    }
  }
  return false;
};

/**
 *
 * @param container //画布容器
 * @param selectedNodes //react state selected NODE
 * @param setSelectedNodes //react setSelected
 * @param dispatch
 * @returns
 */
export const initGraph = (
  container: HTMLElement,
  selectedNodes: Node<Node.Properties>[],
  setSelectedNodes: React.Dispatch<React.SetStateAction<Node<Node.Properties>[]>>,
  dispatch: any,
  jsonEditor: any,
) => {
  const graph = new Graph({
    container,
    //鼠标滚轮
    mousewheel: {
      enabled: true,
      zoomAtMousePosition: true,
      modifiers: 'ctrl',
      minScale: 0.5,
      maxScale: 3,
    },
    connecting: {
      connector: {
        name: 'rounded',
      },
      router: {
        name: 'manhattan',
        args: {
          step: 5,
        },
      },
      anchor: {
        name: 'orth',
        args: {},
      },
      connectionPoint: {
        name: 'anchor',
      },

      // 是否允许连接到画布空白位置的点
      allowBlank: false,
      //是否允许在相同的起始节点和终止之间创建多条边
      allowMulti: false,
      allowLoop: false,
      // 自动吸附
      snap: {
        radius: 20,
      },
      //连接的过程中创建新的边
      createEdge() {
        let edge = new Shape.Edge({
          attrs: {
            line: {
              stroke: '#b2a2e9',
              strokeWidth: 2,
              targetMarker: '',
            },
            outline: {
              stroke: '#effabc',
              strokeWidth: 12,
              connection: true,
              strokeLinejoin: 'round',
            },
          },
        });
        edge.toFront();
        return edge;
      },

      // 在移动边的时候判断连接是否有效，如果返回 false，当鼠标放开的时候，不会连接到当前元素，否则会连接到当前元素
      validateConnection({ sourceMagnet, targetMagnet }) {
        if (!targetMagnet || !sourceMagnet) return false;

        //输入桩限制 (目标桩是输入桩的话则不能连接)
        const inputsPort = targetMagnet.getAttribute('port-group') ?? '';
        if ([PortTypeConst.INPUTS, PortTypeConst.INNER_INPUTS].includes(inputsPort)) {
          //输出桩限制 如果
          const outputsPort = sourceMagnet.getAttribute('port-group') ?? '';
          return [PortTypeConst.OUTPUTS, PortTypeConst.INNER_OUTPUTS].includes(outputsPort);
        }
        return false;
      },
    },
    // 当链接桩可以被链接时，在链接桩外围渲染一个 2px 圆形框
    highlighting: {
      magnetAdsorbed: {
        name: 'stroke',
        args: {
          padding: 4,
          attrs: {
            fill: '#b2a2e9',
            stroke: '#828283',
          },
        },
      },
    },
    background: {
      //画布背景色
      color: '#fff',
    },
    grid: true,
  });
  //自定义删除按钮
  removeBtnNodeRegister(graph);
  removeBtnEdgeRegister(graph);

  //加载相关插件
  loadPlugin(graph);

  function showPortOrLabel(port: Element | SVGElement | null, show: boolean) {
    if (port instanceof SVGElement) {
      port.style.visibility = show ? 'visible' : 'hidden';
    }
  }

  // 控制连接桩显示/隐藏
  const showPortsOrLabels = (portsOrLabels: NodeListOf<Element>, show: boolean) => {
    portsOrLabels.forEach((portOrLabel) => {
      showPortOrLabel(portOrLabel, show);
    });
  };

  function showAllPorts(show: boolean) {
    //显示连接桩

    const ports = container.querySelectorAll('.x6-port-body');
    const labels = container.querySelectorAll('.x6-port-label');
    showPortsOrLabels(ports, show);
    showPortsOrLabels(labels, show);
  }

  graph.on('node:mouseenter', ({ cell }) => {
    if (cell.isNode() && cell.hasPorts()) {
      showAllPorts(true);
    }

    //显示删除按钮
    cell.addTools(
      [
        {
          name: 'rm-btn',
          args: {
            x: 0,
            y: 0,
            offset: { x: 0, y: 0 },
          },
        },
      ],
      { ignored: true },
    );
  });

  graph.on('node:mouseleave', ({ cell }) => {
    showAllPorts(false);

    //移除删除工具
    cell.removeTools({ ignored: true });
  });

  const LINE_STOKE_WIDTH = 'line/strokeWidth';

  function showEdgePorts(edge: Edge<Edge.Properties>, show: boolean) {
    const visibility = show ? 'visible' : 'hidden';
    const VISIBILITY_PATH = 'attrs/circle/style/visibility';
    edge
      .getSourceNode()
      ?.setPortProp(edge.getSourcePortId()!, VISIBILITY_PATH, visibility, { ignored: true });
    edge
      .getTargetNode()
      ?.setPortProp(edge.getTargetPortId()!, VISIBILITY_PATH, visibility, { ignored: true });
  }

  graph.on('edge:mouseenter', ({ view, edge }) => {
    // edge.attr(LINE_STOKE_WIDTH, 4);
    showEdgePorts(edge, true);

    edge.addTools(
      [
        {
          name: 'rm-edge-btn',
          args: {
            distance: view.path.length() / 2,
          },
        },
        {
          name: "vertices",
          args: {
            attrs: {
              fill: "#666"
            }
          }
        }

      ],
      { ignored: true },
    );
  });
  graph.on('node:change', ({ node }: { node: Node }) => {
    if (isCustomTextNode(node)) {
    }
  });
  graph.on('edge:mouseleave', ({ edge }) => {
    // edge.setAttrByPath(LINE_STOKE_WIDTH, 2);
    showEdgePorts(edge, false);
    edge.removeTools({ ignored: true });
  });

  graph.on('node:selected', ({ node }) => {
    //组节点放大后不允许选择
    if (
      isGroupProcess(node) &&
      (node.getSize().height >= graph.getGraphArea().height ||
        node.getSize().width >= graph.getGraphArea().height)
    ) {
      graph.unselect(node);
    } else {
      if (isCustomTextNode(node)) {
      }
      dispatch(changeCurrentSelectNode(node));
      dispatch(changeCurrentSelectNodeName(node.shape));
      //深拷贝,数组要改变地址子组件才能监听到变化
      selectedNodes.push(node);
      setSelectedNodes([...selectedNodes]);
    }
  });

  graph.on('node:collapse', ({ cell: node }: any) => {
    node.toggleCollapse(node.isCollapsed());
  });

  graph.on('node:change:size', ({ options }) => {
    if (options.skipParentHandler) {
      return;
    }
  });

  graph.on('node:resizing', ({ node }) => {
    node.setAttrs({
      image: {
        width: node.size().width,
        height: node.size().height,
      },
    });
  });

  //节点/边被取消选中时触发
  graph.on('cell:unselected', (cell: Cell, options: Model.SetOptions) => {
    selectedNodes.length = 0;
    setSelectedNodes(selectedNodes);
  });

  //节点/边被选中时触发。
  graph.on('cell:selected', ({ cell }: { cell: Cell }, options: Model.SetOptions) => {
    //节点被选中时隐藏连接桩
    const ports = container.querySelectorAll('.x6-port-body');
    showPortsOrLabels(ports, false);
    //放大节点时不允许选
    if (isGroupProcess(cell)) {
      console.log(cell.id, 'groupid');
    }
  });

  graph.on('cell:added', ({ cell }) => {
    //更新表格数据
    cloneSubCells(cell, graph);
  });

  function extendGroupNode(groupNode: Node<Node.Properties>) {
    if (!isGroupProcess(groupNode)) {
      return;
    }

    dispatch(addGraphTabs({ groupCellId: groupNode.id, layer: 1 }));

    graph.getCells().forEach((cell) => {
      cell.hide();
    });

    if (groupNode.hasParent()) {
      shrinkGroupNode(graph, groupNode.parent as Node);
    }

    //隐藏组节点, 先显示, 再隐藏, 否则会导致子节点无法显示
    groupNode.show();
    groupNode.setAttrs({ fo: { visibility: 'hidden' } });

    let children = groupNode.getChildren() ?? [];
    if (!children.length) {
      return;
    }

    const preChildrenBox = getPointsBox(
      children
        .filter((item) => item.isNode())
        .map((item) => item.prop(PreNodeInfo.PREVIOUS_NODE_RECT)),
    );

    const innerOutputPorts = groupNode.getPortsByGroup('innerOutputs');
    graph
      ?.getOutgoingEdges(groupNode)
      ?.filter((edge) => innerOutputPorts.some((port) => edge.getSourcePortId() == port.id))
      .forEach((edge) => {
        children.push(edge);
      });

    const innerInputPorts = groupNode.getPortsByGroup(PortTypeConst.INNER_INPUTS);
    graph
      ?.getIncomingEdges(groupNode)
      ?.filter((edge) => innerInputPorts.some((port) => edge.getTargetPortId() == port.id))
      .forEach((edge) => {
        children.push(edge);
      });

    const graphViewBox = getGraphViewSize();
    if (!graphViewBox) {
      return;
    }

    groupNode.setPosition(
      groupNode.position().x + graphViewBox.width,
      groupNode.position().y + graphViewBox.height,
      { relative: true, deep: true },
    );

    groupNode.resize(graphViewBox.width / 2, graphViewBox.height / 2, { direction: 'top-left' });
    groupNode.resize(graphViewBox.width, graphViewBox.height, { direction: 'bottom-right' });

    graph.centerCell(groupNode);
    groupNode.toBack();

    //将隐藏的cell设置为不可选
    graph.setSelectionFilter((cell) => {
      return !!children?.map((c) => c.id).includes(cell.id);
    });

    graph.cleanSelection();

    children?.forEach((item) => {
      if (item.isNode()) {
        const preNodeRect = item.prop(PreNodeInfo.PREVIOUS_NODE_RECT) as PreNodeRect;
        const { x: localX, y: localY } = graph.clientToLocal(graphViewBox.x, graphViewBox.y);
        const x =
          (graphViewBox.width - preChildrenBox.width) / 2 +
          (preNodeRect.x - preChildrenBox.x) +
          localX;
        const y =
          (graphViewBox.height - preChildrenBox.height) / 2 +
          (preNodeRect.y - preChildrenBox.y) +
          localY;
        item.setPosition(x, y);

        if (isGroupProcess(item)) {
          item.setAttrs({ fo: { visibility: 'visible' } });
        }
      }

      item.toFront();
      item.show();
    });
  }

  graph.on('node:dblclick', ({ node }) => {
    extendGroupNode(node);
  });

  graph.on('edge:dblclick', ({ edge }) => {
    getSourceTargetByEdge(graph, edge, dispatch);
  });
  graph.on('history:undo', ({ cmds, options }: { cmds: any; options: any }) => {
    const cmd = cmds[0];
    switch (cmd.options.eventType) {
      case GraphHistory.CREATE_PROCESS:
        graph.fromJSON(cmd.options.graphJson);
        graph.setSelectionFilter((cell) => {
          return graph
            .getCells()
            .map((c) => c.id)
            .includes(cell.id);
        });
        break;
    }
  });

  graph.on('scale', ({ sx, sy, ox, oy }) => {
    scaleCurrentGroup(sx, sy, ox, oy, graph);
  });
  graph.on('resize', ({ width, height }) => { });
  graph.on('translate', ({ tx, ty }) => {
    translateCurrentGroup(tx, ty, graph);
  });

  graph.on('node:moved', ({ e, x, y, node, view }) => {
    const hasInsertsection = isNodeIntersect(node, getOhterTextNode(node, graph), graph);
    setNodeAndEdgeAttrs(node, hasInsertsection, graph);
  });
  dispatch(changeGraph(graph));
  return graph;
};


const translateCurrentGroup = (tx: number, ty: number, graph: Graph) => {
  graph.getNodes().forEach((node) => {
    const position = node.getPosition();
    node.position(position.x - tx, position.y - ty);
  });

};
const scaleCurrentGroup = (sx: number, sy: number, ox: number, oy: number, graph: Graph) => {
  graph.getNodes().forEach((node) => {
    const size = node.size();
    console.log(size);
    node.resize(Math.floor((70 * 1) / sx), Math.floor((50 * 1) / sy));
  });
};
