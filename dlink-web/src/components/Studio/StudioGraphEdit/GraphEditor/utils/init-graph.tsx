import { Cell, Dom, Edge, Graph, Model, Node, Shape } from '@antv/x6';
import loadPlugin from './plugin';
import { removeBtnNodeRegister, removeBtnEdgeRegister } from './remove-btn-register';
import CustomShape from './cons';
import {
  changeCurrentSelectNode,
  changeCurrentSelectNodeName,
  changeGraph,
  addGraphTabs,
  addActiveKey,
  GroupTabItem,

} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import store from '../store';
import React from 'react';

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
        args: {
        }
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
      validateConnection({ sourceMagnet, targetMagnet }) {

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
      findParent({ node }) {
        return this.getNodes().filter((targetNode) => {
          if (targetNode.shape == 'package') {
            const bbox = node.getBBox();
            const targetBBox = targetNode.getBBox();
            return bbox.isIntersectWithRect(targetBBox);
          }
          return false;
        });
      },
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

  graph.on('node:mouseenter', ({ cell }) => {
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
          offset: { x: 0, y: 0 },
        },
      },
    ]);
  });

  graph.on('node:mouseleave', ({ cell }) => {
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

  graph.on('edge:mouseenter', ({ e, view, edge, cell }) => {

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
  graph.on('edge:mouseleave', ({ e, view, edge, cell }) => {
    // edge.setAttrByPath(LINE_STOKE_WIDTH, 2);
    // showEdgePorts(edge, false);
    edge.removeTools();
  });

  graph.on('node:selected', ({ node }) => {
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
  graph.on('node:contextmenu', ({ cell, e }) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
  });

  graph.on('blank:contextmenu', ({ e }) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
  });

  graph.on('node:collapse', ({ cell: node }: any) => {
    node.toggleCollapse(node.isCollapsed());
  });

  //群组大小自适应处理
  let ctrlPressed = false;
  graph.on('node:embedding', ({ e }: { e: Dom.MouseMoveEvent }) => { });

  graph.on('node:embedded', ({ node, currentParent }) => {
    ctrlPressed = false;
    //设置父节点zindex小于子节点
    currentParent?.toBack();
  });

  graph.on('node:change:size', ({ node, options }) => {
    if (options.skipParentHandler) {
      return;
    }
  });

  graph.on('node:change:position', ({ node, options }) => {
    if (options.skipParentHandler || ctrlPressed) {
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
    if (cell.shape === CustomShape.GROUP_PROCESS) {
      console.log(cell.id, 'groupid');
    }


  });

  graph.on('cell:added', ({ cell, index, options }) => {
    //更新表格数据
    if (cell.shape === 'package') {
      cell.setZIndex(-1);
    }
    // 在添加的时候将该节点加入到上级画布的innercells
    const activeKey = store.getState().home.activeKey
    const tabsaved = store.getState().home.graphTabs
    if (tabsaved.length) {
      tabsaved[activeKey-1].innerCells.push(cell)
    }

    // updateGraphData(graph);
  });

  graph.on('cell:removed', ({ cell, index, options }) => {
    // updateGraphData(graph);
  });
  graph.on("node:mousemove", ({ node, x, y }) => {
    if (node.shape === CustomShape.GROUP_PROCESS) {
      node.prop("previousPosition", node.position({ relative: true }))
    }
  })

  function shrinkGroupNode(node: Node<Node.Properties>) {
    if (node.shape !== CustomShape.GROUP_PROCESS) {
      return;
    }

    //保存每一次平移时当前画布中组节点内外的cells
    let innerCells = node.getChildren()!;

    let outerCells: Cell[] = []
    const activeKey = store.getState().home.activeKey
    const tabSaved = store.getState().home.graphTabs
    if (!activeKey) {
      const cells = graph.getCells();
      outerCells = cells.filter(cell => !innerCells.some(inCell => inCell.id === cell.id))
    } else {
      const cells = tabSaved[activeKey - 1].innerCells

      outerCells = cells.filter(cell => !innerCells.some(inCell => inCell.id === cell.id))
      tabSaved[activeKey - 1].innerCells.push(node)
      outerCells.push(graph.getCellById(tabSaved[activeKey - 1].groupCellId))
      outerCells.push(node)

    }

    let incomeEdges = graph?.getIncomingEdges(node)
    let outEdges = graph?.getOutgoingEdges(node)
    let innerInputPorts = node.getPortsByGroup("innerInputs")
    let innerOutputPorts = node.getPortsByGroup("innerOutputs")
    if (innerOutputPorts.length > 0) {
      for (let edge of outEdges!) {
        const sourcePortId = edge.getSourcePortId()
        if (innerOutputPorts.some(port => port.id === sourcePortId)) {
          innerCells.push(edge);
        } else {
          outerCells.push(edge)
        }
      }
    }
    if (innerInputPorts.length > 0) {
      for (let edge of incomeEdges!) {
        const targetPortId = edge.getTargetPortId()
        if (innerInputPorts.some(port => port.id === targetPortId)) {

          innerCells.push(edge);
        } else {
          outerCells.push(edge)
        }
      }
    }
    outerCells = outerCells.filter(outCell =>
      !innerCells.some(innerCell => innerCell.id === outCell.id)
    )

    console.log(innerCells, outerCells, "inner,outer");

    //设置当前key
    dispatch(addActiveKey(1))
    //新增导航
    dispatch(addGraphTabs({groupCellId: node.id, layer: 1, innerCells, outerCells: outerCells}))
    //将组节点外部的节点全部隐藏
    outerCells.forEach(cell => {
      cell.hide()
    })

    const tabs = store.getState().home.graphTabs;
    const dx = graph.getGraphArea().width;
    const dy = graph.getGraphArea().height;
    const prePos = node.getProp().previousPosition
    node.translate(-prePos.x, -prePos.y)
    node.translate(dx, 0)
    //反向平移画布
    graph.translate(-dx / tabs[tabs.length - 1].layer, 0)
    //放大到画布大小
    node.resize(dx / tabs[tabs.length - 1].layer, dy)
    node.toBack();
    //隐藏组节点
    node.show();
    node.setAttrs({fo: {visibility: "hidden"}})
    graph.cleanSelection();
    //将隐藏的节点设置为不可选
    graph.setSelectionFilter((cell) => {
      return !!innerCells?.map(c => c.id).includes(cell.id)
    })
    innerCells?.forEach(item => {
      item.show()
      //将节点位移到和之前对应的地方
      if (item.isNode()) {
        const prePos = item.getProp().previousPosition
        item.prop("position", {x: dx / tabs[tabs.length - 1].layer + prePos.x, y: prePos.y})
      }
    })
  }

  graph.on('node:dblclick', ({ node, e, view }) => {
    shrinkGroupNode(node);
  });

  graph.on("edge:click", ({ edge }) => {
    console.log(edge.id, "edgeid");

  })

  dispatch(changeGraph(graph));
  return graph;
};
