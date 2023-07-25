import {Cell, Dom, Edge, Graph, Model, Node, Shape} from '@antv/x6';
import loadPlugin from './plugin';
import {removeBtnEdgeRegister, removeBtnNodeRegister} from './remove-btn-register';
import CustomShape, {PreNodeInfo} from './cons';
import {
  addActiveKey,
  addGraphTabs,
  changeCurrentSelectNode,
  changeCurrentSelectNodeName,
  changeGraph,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import store from '../store';
import React from 'react';
import {getGraphViewSize, PreNodeRect} from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/graph-helper";

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
        }
      },
      anchor: {
        name: 'orth',
        args: {}
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
              targetMarker: {
                name: 'classic',
                size: 10,
              },
            },
          },
        });
        edge.toFront()
        return edge
      },

      // 在移动边的时候判断连接是否有效，如果返回 false，当鼠标放开的时候，不会连接到当前元素，否则会连接到当前元素
      validateConnection({sourceMagnet, targetMagnet}) {

        if (!targetMagnet || !sourceMagnet) return false;

        //输入桩限制 (目标桩是输入桩的话则不能连接)
        const inputsPort = targetMagnet.getAttribute('port-group') ?? '';
        if (['inputs', "innerInputs"].includes(inputsPort)) {
          //输出桩限制 如果
          const outputsPort = sourceMagnet.getAttribute('port-group') ?? '';
          return ['outputs', "innerOutputs"].includes(outputsPort);
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
      embedding: {
        name: 'stroke',
        args: {
          padding: -1,
          rx: 4,
          ry: 4,
          attrs: {
            stroke: '#f6a548',
            strokeWidth: 2,
          },
        },
      },
    },
    //  将一个节点拖动到另一个节点中，使其成为另一节点的子节点
    embedding: {
      enabled: true,
    },
    background: {
      //画布背景色
      color: '#fff',
    },
    grid: true,
  });
  //自定义删除按钮
  removeBtnNodeRegister(graph)
  removeBtnEdgeRegister(graph)

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
    const ports = container.querySelectorAll(".x6-port-body");
    const labels = container.querySelectorAll(".x6-port-label")
    showPortsOrLabels(ports, show);
    showPortsOrLabels(labels, show);

  }

  graph.on('node:mouseenter', ({cell}) => {
    if (cell.isNode() && cell.hasPorts()) {
      showAllPorts(true);
    }

    //显示删除按钮
    cell.addTools([
      {
        name: 'rm-btn',
        args: {
          x: 0,
          y: 0,
          offset: {x: 0, y: 0},
        },
      },
    ]);
  });

  graph.on('node:mouseleave', ({cell}) => {
    // showAllPorts(false);

    //移除删除工具
    cell.removeTools();
  });

  const LINE_STOKE_WIDTH = 'line/strokeWidth';

  function showEdgePorts(edge: Edge<Edge.Properties>, show: boolean) {
    const visibility = show ? 'visible' : 'hidden';
    const VISIBILITY_PATH = 'attrs/circle/style/visibility';
    edge.getSourceNode()?.setPortProp(edge.getSourcePortId()!, VISIBILITY_PATH, visibility);
    edge.getTargetNode()?.setPortProp(edge.getTargetPortId()!, VISIBILITY_PATH, visibility);
  }

  graph.on('edge:mouseenter', ({e, view, edge, cell}) => {

    // edge.attr(LINE_STOKE_WIDTH, 4);
    // showEdgePorts(edge, true);

    edge.addTools([
      {
        name: 'rm-edge-btn',
        args: {
          distance: view.path.length() / 2,
        },
      },
    ]);
  });
  graph.on('edge:mouseleave', ({e, view, edge, cell}) => {
    // edge.setAttrByPath(LINE_STOKE_WIDTH, 2);
    // showEdgePorts(edge, false);
    edge.removeTools();
  });

  graph.on('node:selected', ({node}) => {
    dispatch(changeCurrentSelectNode(node));
    dispatch(changeCurrentSelectNodeName(node.shape));

    //深拷贝,数组要改变地址子组件才能监听到变化
    selectedNodes.push(node);
    setSelectedNodes([...selectedNodes]);
    //组节点放大后不允许选择
    if (node.shape === CustomShape.GROUP_PROCESS
      && (node.getSize().height >= graph.getGraphArea().height
        || node.getSize().width >= graph.getGraphArea().height)) {
      graph.unselect(node)
    }
  });

  //右键菜单
  graph.on('node:contextmenu', ({cell, e}) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
  });

  graph.on('blank:contextmenu', ({e}) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
  });

  graph.on('node:collapse', ({cell: node}: any) => {
    node.toggleCollapse(node.isCollapsed());
  });

  //群组大小自适应处理
  let ctrlPressed = false;
  graph.on('node:embedding', ({e}: { e: Dom.MouseMoveEvent }) => {
  });

  graph.on('node:embedded', ({node, currentParent}) => {
    ctrlPressed = false;
    //设置父节点zindex小于子节点
    currentParent?.toBack();
  });

  graph.on('node:change:size', ({node, options}) => {
    if (options.skipParentHandler) {
      return;
    }
  });

  graph.on('node:change:position', ({node, options}) => {
    if (options.skipParentHandler || ctrlPressed) {
      return;
    }
  });

  graph.on('node:resizing', ({node}) => {
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
  graph.on('cell:selected', ({cell}: { cell: Cell }, options: Model.SetOptions) => {
    //节点被选中时隐藏连接桩
    const ports = container.querySelectorAll('.x6-port-body');
    showPortsOrLabels(ports, false);
    //放大节点时不允许选
    if (cell.shape === CustomShape.GROUP_PROCESS) {
      console.log(cell.id, 'groupid');
    }


  });

  graph.on('cell:added', ({cell, index, options}) => {
    //更新表格数据
    if (cell.shape === 'package') {
      cell.setZIndex(-1);
    }

    // 在添加的时候将该节点加入到上级画布的innercells
    const activeKey = store.getState().home.activeKey
    const tabSaved = store.getState().home.graphTabs
    if (tabSaved.length) {
      tabSaved[activeKey - 1].innerCells.push(cell)
    }

    // updateGraphData(graph);
  });

  graph.on('cell:removed', ({cell, index, options}) => {
    // updateGraphData(graph);
  });

  graph.on("node:mousemove", ({node, x, y}) => {
  })

  function extendGroupNode(groupNode: Node<Node.Properties>) {

    if (groupNode.shape !== CustomShape.GROUP_PROCESS) {
      return;
    }
    //隐藏组节点, 先显示, 再隐藏, 否则会导致子节点无法显示
    groupNode.show()
    groupNode.setAttrs({fo: {visibility: "hidden"}})

    graph.getCells().forEach(cell => {
      cell.hide()
    })

    const activeKey = store.getState().home.activeKey
    const tabSaved = store.getState().home.graphTabs
    if (activeKey) {
      tabSaved[activeKey - 1].innerCells.push(groupNode)
    }

    let innerCells = groupNode.getChildren() ?? [];
    if (!innerCells.length) {
      console.log('没有子节点')
      return
    }

    const childrenBox = graph.getCellsBBox(innerCells)!

    const innerOutputPorts = groupNode.getPortsByGroup("innerOutputs")
    graph?.getOutgoingEdges(groupNode)?.filter(edge =>
      innerOutputPorts.some(port => edge.getSourcePortId() == port.id))
      .forEach(edge => {
        innerCells.push(edge)
      })

    const innerInputPorts = groupNode.getPortsByGroup("innerInputs")
    graph?.getIncomingEdges(groupNode)?.filter(edge =>
      innerInputPorts.some(port => edge.getTargetPortId() == port.id))
      .forEach(edge => {
        innerCells.push(edge)
      })

    //设置当前key
    dispatch(addActiveKey(1))
    dispatch(addGraphTabs({groupCellId: groupNode.id, layer: 1, innerCells}))

    const {width, height} = getGraphViewSize() ?? {width: 0, height: 0}
    if (!width) {
      return
    }

    groupNode.setPosition(groupNode.position().x + width, groupNode.position().y + height, {relative: true, deep: true});

    groupNode.resize(width / 2, height / 2, {direction: 'top-left'});
    groupNode.resize(width, height, {direction: 'bottom-right'});

    graph.centerCell(groupNode)
    groupNode.toBack();


    //将隐藏的cell设置为不可选
    graph.setSelectionFilter((cell) => {
      return !!innerCells?.map(c => c.id).includes(cell.id)
    })

    graph.cleanSelection();

    innerCells?.forEach(item => {
      if (item.isNode()) {
        const preNodeRect = item.prop().preNodeRect
        const x = width / 2 + preNodeRect.x
        const y = height / 2+ preNodeRect.y
        item.setPosition(x, y, {relative: true, deep: true})

        if (item.shape === CustomShape.GROUP_PROCESS) {
          item.setAttrs({fo: {visibility: "visible"}})
        }
      }

      item.show()
    })

  }

  graph.on('node:dblclick', ({node, e, view}) => {
    extendGroupNode(node);
  });

  graph.on("edge:click", ({edge}) => {
    console.log(edge.id, "edgeid");

  })

  dispatch(changeGraph(graph));
  return graph;
};
