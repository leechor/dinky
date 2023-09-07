import { memo, useEffect, useRef, useState } from 'react';
import { Cell, Edge, Graph, Node } from '@antv/x6';
import { cloneDeep } from 'lodash';
import { Breadcrumb, message } from 'antd';
import { CaretRightOutlined } from '@ant-design/icons';

import { handleInitPort } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/ports-register';
import { initGraph } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/init-graph';
import { stencilComponentsLoader } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/stencil-components-loader';
import { initStencil } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/init-stencil';
import { handleInitNodes } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/node-by-data-loader';
import registerShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/shape-register';
import unRegisterShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/shape-unregister';
import {
  useAppDispatch,
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { CustomMenu } from './menu';
import { initMenu } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/init-menu';
import NodeModalPreview from '../../../components/node-preview-modal';
import AddModalPort from '../../../components/add-port-modal';
import {
  changeCurrentSelectNode,
  changePositon,
  changePreviewInfo,
  changeAddPortInfo,
  changeDataSourceInfo,
  changeGroupNameInfo,
  changeEdgeClickInfo,
  changeStencilMenuInfo,
  GroupTabItem,
  removeGraphTabs,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import localCache from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/localStorage';
import { DataSourceType, GraphHistory, OperatorType, PortType } from '../../../utils/cons';
import DataSourceModal from '../../../components/data-source-modal';
import GroupName from '../../../components/group-name-modal';
import EdgeClickModal from '../../../components/edge-click-modal';
import { shrinkGroupNode } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/graph-helper';
import { saveCustomGroupInfo, changeCustomGroupInfo } from '@/components/Common/crud';
import { StencilMenu } from '../../../components/stencil-menu';
import { initFlowDataAction } from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import {
  setConfigToNode,
  getSourceTargetByEdge,
  getTargetNodeAndPort,
  getSourceNodeAndPort,
  getId,
  strMapToObj,
  isGroupProcess,
  isDuplicateOperator,
  isCustomerOperator,
  getNodeAndPort,
} from '../../../utils/graph-tools-func';
import { ColumnType, MenuInfo, SubGraphCells } from '../../../types';
import { l } from '@/utils/intl';

export const warningTip = (code: number, msg: string) => {
  if (code === 1) {
    message.error(msg);
  } else {
    message.success(msg);
  }
};
const LeftEditor = memo(() => {
  let timer: any = null;
  const [selectedNodes, setSelectedNodes] = useState<Node[]>([]);
  const [showMenuInfo, setShowMenuInfo] = useState<{
    show: boolean;
    top: number;
    left: number;
    node: Node | null;
  }>({
    show: false,
    top: 0,
    left: 0,
    node: null,
  });
  const editorContentRef = useRef(null);
  const graphRef = useRef<Graph>();
  const stencilRef = useRef(null);
  let v: HTMLDivElement;
  let h: HTMLDivElement;
  let c: HTMLDivElement;

  const dispatch = useAppDispatch();
  const { stencilMenuInfo }: { stencilMenuInfo: MenuInfo } = useAppSelector((state) => ({
    stencilMenuInfo: state.home.stencilMenuInfo,
  }));

  const {
    flowData,
    addPortInfo,
    operatorParameters: operatorParameters,
    groupNameInfo,
    jsonEditor,
    taskName,
    tabs,
    previewInfo,
    dataSourceInfo,
    edgeClickInfo,
  }: { tabs: GroupTabItem[];[key: string]: any } = useAppSelector((state) => ({
    flowData: state.home.flowData,
    operatorParameters: state.home.operatorParameters,
    jsonEditor: state.home.editor,
    taskName: state.home.taskName,
    tabs: state.home.graphTabs,
    previewInfo: state.home.previewInfo,
    addPortInfo: state.home.addPortInfo,
    dataSourceInfo: state.home.dataSourceInfo,
    groupNameInfo: state.home.groupNameInfo,
    edgeClickInfo: state.home.edgeClickInfo,
  }));

  useEffect(() => {
    start();
    console.log(operatorParameters, 'operatorParameters');

    return () => {
      document.removeEventListener('mousemove', onMouseMove);
    };
  }, []);
  const cancelShowMenu = () => {
    dispatch(changeStencilMenuInfo({ x: 0, y: 0, showStencilMenu: false, node: null }));
  };
  useEffect(() => {
    const element = document.getElementsByClassName('leftEditor');
    for (let ele of Array.from(element)) {
      ele.addEventListener('click', cancelShowMenu);
    }
    return () => {
      for (let ele of Array.from(element)) {
        ele.removeEventListener('click', cancelShowMenu);
      }
    };
  }, []);

  const onMouseMove = (e: MouseEvent) => {
    if (!e.altKey) {
      return;
    }

    const target = e.target as HTMLElement;
    const graph = graphRef.current!;
    if (
      graph.container.contains(target) ||
      graph.container === target ||
      target === v ||
      target === h ||
      target === c
    ) {
      const pageX = e.pageX;
      const pageY = e.pageY;
      const clientX = e.clientX;
      const clientY = e.clientY;
      v.style.left = `${pageX + 2}px`;
      h.style.top = `${pageY + 2}px`;

      c.style.left = `${pageX + 10}px`;
      c.style.top = `${pageY + 10}px`;

      const p1 = graph.pageToLocal(pageX, pageY);
      const p2 = graph.localToGraph(p1);

      c.innerHTML = `
        <div>Page(pageX, pageY): ${pageX} x ${pageY}</div>
        <div>Client(clientX, clientY): ${clientX} x ${clientY}</div>
        <div>Local Point: ${p1.x} x ${p1.y}</div>
        <div>Graph Point: ${p2.x} x ${p2.y}</div>
        `;
    } else {
      v.style.left = `${-1000}px`;
      h.style.top = `${-1000}px`;
      c.style.left = `${-10000}px`;
      c.style.top = `${-10000}px`;
    }
  };

  const start = () => {
    const root = document.getElementById('root')!;
    v = document.createElement('div');
    h = document.createElement('div');
    c = document.createElement('div');

    v.style.position = 'absolute';
    v.style.width = '1px';
    v.style.top = '0';
    v.style.bottom = '0';
    v.style.left = '-100px';
    v.style.zIndex = `99`;
    v.style.borderLeft = '1px dashed red';

    h.style.position = 'absolute';
    h.style.height = '1px';
    h.style.left = '0';
    h.style.right = '0';
    h.style.top = '-100px';
    h.style.zIndex = `99`;
    h.style.borderTop = '1px dashed red';

    c.style.position = 'absolute';
    c.style.display = 'inline-block';
    c.style.fontSize = '12px';
    c.style.zIndex = `99`;
    c.style.padding = '4px 8px';
    c.style.borderRadius = '2px';
    c.style.lineHeight = '20px';
    c.style.background = '#f6ffed';
    c.style.border = '1px solid #b7eb8f';

    root.appendChild(v);
    root.appendChild(h);
    root.appendChild(c);
    document.addEventListener('mousemove', onMouseMove);
  };

  const getNewConfig = (cell: Cell, config: any) => {
    if (!cell.getData() || !cell.getData()['config'][0]) {
      return cloneDeep(config);
    }
    let oldConfig = cloneDeep(cell.getData()['config'][0]);
    for (let newKey in config) {
      oldConfig[newKey] = config[newKey];
    }
    return oldConfig;
  };
  const isConnected = (graph: Graph, node: Cell, id: string, isOutputs: boolean) => {
    let edges: Edge[] | null;
    if (isOutputs) {
      edges = graph.model.getOutgoingEdges(node);
      if (!edges) return false;
      return edges.some((edge) => edge.getSourcePortId() === id);
    } else {
      edges = graph.model.getIncomingEdges(node);
      if (!edges) return false;
      return edges.some((edge) => edge.getTargetPortId() === id);
    }
  };
  const handleNodeConfigSet = (graph: Graph) => {
    graph.on('node:port:click', ({ node }: { node: Node }) => {
      dispatch(changeCurrentSelectNode(node));
    });

    graph.on('edge:connected', ({ isNew, edge, currentCell, currentPort }) => {
      //创建新边
      if (!isNew) return

      const targetInfo = getTargetNodeAndPort(edge, graph);
      const sourceInfo = getSourceNodeAndPort(edge, graph);
      if (typeof targetInfo === null || typeof sourceInfo === null) {
        message.warning(l("graph.toolsfuc.sure.connected"));
        return
      }
      const { targetNode, sourceNode, id } = getNodeAndPort(targetInfo!, sourceInfo!)
      //获取source的columns
      if (!sourceNode?.getData()) return;
      let parametersConfig: ColumnType[];
      if (isDuplicateOperator(sourceNode)) {
        //修改（如果是DuplicateOperator则需要从输入节点的config里读取 ）
        //修改（如果是DuplicateOperator则需要从节点的输入连线的config里读取 ）
        const [lastEdge] = graph.getIncomingEdges(sourceNode)!;
        const lastSourceInfo = getSourceNodeAndPort(lastEdge, graph);
        const lastTargetInfo = getTargetNodeAndPort(lastEdge, graph);

        const { id } = getNodeAndPort(lastTargetInfo!, lastSourceInfo!,);
        parametersConfig = sourceNode?.getData()['config'][0][id!];
      } else {
        parametersConfig = sourceNode.getData().hasOwnProperty('parameters') ?
          (sourceNode?.getData()?.parameters.output.columns) : []
      }
      if (!parametersConfig) return;

      let configMap: Map<string, ColumnType[]> = new Map();
      //转换为config设置到当前节点的node-data里
      configMap.set(id, parametersConfig);
      let config = strMapToObj(configMap);
      let newConfigObj = getNewConfig(targetNode, config);
      if (isDuplicateOperator(targetNode)) {
        //连接的是自定义节点遍历节点config,重新赋值
        //设置前节点和当前节点连接config
        let currentNode = edge.getTargetNode();
        newConfigObj = getNewConfig(targetNode, config);
        const edges = graph.model.getOutgoingEdges(targetNode);
        edges && edges.forEach(edge => {
          let targetNode = edge.getTargetNode();
          let targetPortId = edge.getTargetPortId();
          let sourcePortId = edge.getSourcePortId();
          let id = `${currentNode!.id}&${sourcePortId} ${targetNode!.id}&${targetPortId}`;
          newConfigObj[id] = cloneDeep(config);
        })
      }
      setConfigToNode(null, newConfigObj, targetNode);
      if (
        isDuplicateOperator(sourceNode) ||
        isCustomerOperator(sourceNode)
      ) {
        //把input-output config 设置进自定义节点config
        let oldConfigObj = sourceNode.getData()['config'][0];
        if (!oldConfigObj) {
          oldConfigObj = {};
        }
        oldConfigObj[id] = parametersConfig;
        setConfigToNode(null, cloneDeep(oldConfigObj), sourceNode);
      }
      getSourceTargetByEdge(graph, edge, dispatch);

    });
  };
  const handleCancelPort = () => {
    dispatch(changeAddPortInfo({ isShow: false, values: '', node: null }));
  };
  const addPort = (node: Node, type: PortType.INPUTS | PortType.OUTPUTS, portName: string) => {
    graphRef.current?.batchUpdate(GraphHistory.ADD_PORT, () => {
      node!.addPort({
        group: type,
        zIndex: 999,
        id: portName,
        attrs: {
          text: {
            text: portName,
            style: {
              visibility: 'hidden',
              fontSize: 10,
              fill: '#3B4351',
            },
          },
        },
        label: {
          position: 'bottom',
        },
      });
    });
  };
  const handleSubmitPort = (value: any) => {
    const node = addPortInfo.node;
    if (isDuplicateOperator(node)) {
      // 添加连接桩
      addPort(node, PortType.OUTPUTS, value.portName);
      const portId = node?.getPortsByGroup(PortType.INPUTS)[0].id;
      const flag = isConnected(graphRef.current!, node!, portId!, false);
      const edges = graphRef.current!.model.getIncomingEdges(node!);
      if (!flag) return
      //如果是连线状态下点击(有设置config)
      // 获取之前的config
      edges && edges.forEach((edge) => {
        const targetInfo = getTargetNodeAndPort(edge, graphRef.current!);
        const sourceInfo = getSourceNodeAndPort(edge, graphRef.current!);
        if (typeof targetInfo === null || typeof sourceInfo === null) {
          message.warning(l("graph.toolsfuc.sure.connected"));
          return
        }
        const { targetNode, id } = getNodeAndPort(targetInfo!, sourceInfo!)
        //获取最新输0-0连接桩配置
        if (targetNode?.getData().config) {
          let parametersByOutPortConfig = targetNode.getData().config[0];
          if (!parametersByOutPortConfig[id]) {
            message.warning(l("graph.lefteditor.set.input.data.para.config"));
            return;
          }
          let parametersConfig: ColumnType[] = parametersByOutPortConfig[id];
          // 给每个连接桩赋初始值
          let newConfigObj = {};
          newConfigObj[id] = parametersConfig;
          setConfigToNode(node, newConfigObj);
        }
      })

    } else {
      const { inputPort, outputPort } = value;

      inputPort && inputPort.forEach((inPort: string) => {
        // 添加连接桩
        addPort(node, PortType.INPUTS, inPort);
      });

      outputPort && outputPort.forEach((outPort: string) => {
        // 添加连接桩
        addPort(node, PortType.OUTPUTS, outPort);
      });

    }
    message.success(l("graph.lefteditor.add.port.success"));
  };
  const handleShowMenu = (value: boolean) => {
    setShowMenuInfo({ ...showMenuInfo, show: value });
  };

  const handleGroupNameSubmit = (value: any) => {
    const { node, type, groupName } = value;
    const graph = graphRef.current!;

    switch (type) {
      case OperatorType.ADD:
        let groupJson: Cell<Cell.Properties>[] = [];
        if (isGroupProcess(node)) {
          let subGroupObj: SubGraphCells =
            node && graph.cloneSubGraph([graph.getCellById(node.id)], { deep: true });
          subGroupObj &&
            Object.values(subGroupObj).forEach((cls) => {
              groupJson.push(cls);
            });
        } else {
          let subGroupObj: SubGraphCells = node && graph.cloneCells([graph.getCellById(node.id)]);
          subGroupObj &&
            Object.values(subGroupObj).forEach((cls) => {
              if (cls.getData().hasOwnProperty('config')) {
                cls.setData(
                  {
                    ...(node.getData() ? node.getData() : {}),
                    config: [],
                  },
                  { overwrite: true },
                );
              }
              groupJson.push(cls);
            });
        }
        saveCustomGroupInfo('/api/zdpx/customer/save', {
          script: JSON.stringify({ cells: groupJson }),
          name: groupName,
          code: node.shape,
        }).then((res) => {
          warningTip(res.code, res.msg);
          dispatch(initFlowDataAction());
        });
        break;
      case OperatorType.CHANGE:
        changeCustomGroupInfo(
          `/api/zdpx/customer/oldName/${node.prop().name}/newName/${groupName}`,
        ).then((res) => {
          warningTip(res.code, res.msg);
        });
        dispatch(initFlowDataAction());
        break;
      default:
        node.prop('name', groupName);
    }

  };
  const handleGroupNameCancel = () => {
    dispatch(changeGroupNameInfo({ isShowGroupNameModal: false }));
  };

  const handlePreviewCancel = () => {
    dispatch(changePreviewInfo({ node: null, values: '', isShow: false }));
  };
  const handlePreviewSubmit = () => { };
  const handleDataSourceSubmit = (datas: any) => {
    const { tableItem, value: data, type, tableName } = datas;
    //将数据源配置到config
    switch (type) {
      case DataSourceType.Mysql:
        const jsonconfig = cloneDeep(jsonEditor.getValue());
        const { url, username, password } = tableItem;
        const getType = (str: string) => {
          const lastIndex = str.lastIndexOf('_');
          if (lastIndex === -1) return str;
          return str.substring(lastIndex + 1);
        };
        const columns = data.map((item: any) => ({
          type: getType(item.javaType),
          name: item.name,
        }));
        const newJsonConfig = {
          output: { ...jsonconfig.output, source: columns },
          service: { ...jsonconfig.service, tableName, password, username, url },
        };
        jsonEditor.setValue(newJsonConfig);
        message.success(l("graph.lefteditor.config.success"));
        break;
    }
  };
  const handleDataSourceCancel = () => {
    dispatch(changeDataSourceInfo({ datas: '', isShowModal: false, node: null, type: '' }));
  };
  const changeNode = (node: Cell) => {
    dispatch(changeCurrentSelectNode(node));
  };
  const handleEdgeClickSubmit = (value: any) => {
    dispatch(changeEdgeClickInfo({ ...edgeClickInfo }));
  };
  const handleEdgeClickCancel = () => {
    dispatch(changeEdgeClickInfo({ ...edgeClickInfo, isShowedgeClickModal: false }));
  };
  const changePosition = (x: number, y: number) => {
    dispatch(changePositon({ x, y }));
  };
  //上方面包屑控制画布群组导航
  const tabClick = (clickLayer: number) => {
    //如果点击的是最新并且未设置群组
    if ((clickLayer === 0 && tabs.length === 0) || clickLayer === tabs.length - 1) {
      return;
    }

    if (!graphRef.current) {
      return;
    }

    const graph = graphRef.current;

    const groupNode = graph.getCellById(tabs[clickLayer + 1].groupCellId) as Node;
    shrinkGroupNode(graph, groupNode);

    const otherTopCells =
      graph.getCellById(tabs[clickLayer].groupCellId)?.getChildren() ??
      graph.getCells().filter((cell) => !cell.parent) ?? [];

    //设置外部元素可选
    graph.setSelectionFilter((cell) => otherTopCells.map((c: Cell) => c.id).includes(cell.id));

    otherTopCells.forEach((cell: Cell) => {
      cell.show();
      if (isGroupProcess(cell)) {
        cell.setAttrs({ fo: { visibility: 'visible' } });
      }
    });
    dispatch(removeGraphTabs(clickLayer));
  };

  const processTabClick = (index: number) => {
    for (let i = tabs.length - 1; i >= index; i--) {
      tabClick(i);
    }
  };

  const getSubprocess = () => {
    if (tabs.length === 0) return;

    return tabs.map((tab: GroupTabItem, index) => (
      <Breadcrumb.Item onClick={() => processTabClick(index)} key={index}>
        {<span style={{ cursor: 'pointer', display: 'inline-block' }}>{`subprocess${index}`}</span>}
        <CaretRightOutlined />
      </Breadcrumb.Item>
    ));
  };

  useEffect(() => {
    if (editorContentRef.current) {
      const editorContentContainer: HTMLElement = editorContentRef.current;

      //1、初始化画布

      graphRef.current = initGraph(
        editorContentContainer,
        selectedNodes,
        setSelectedNodes,
        dispatch,
        jsonEditor,
      );
      initMenu(graphRef.current, setShowMenuInfo, changeNode, changePosition);

      if (stencilRef.current) {
        const cloneRef: HTMLElement = stencilRef.current;

        //2、加载连接桩
        const ports = handleInitPort();

        //3、注册自定义节点图形
        registerShape(graphRef.current, ports, operatorParameters);

        //4、初始化stencil
        const stencil = initStencil(graphRef.current, cloneRef, operatorParameters);

        // 5、加载自定义的组件图形
        stencilComponentsLoader(graphRef.current, stencil, operatorParameters);

        // 6、加载数据

        const data = localCache.getCache(`${taskName}graphData`);
        timer = handleInitNodes(graphRef.current, data);

        //加载连接装点击事件控制portmodal
        handleNodeConfigSet(graphRef.current);
      }
    }

    return () => {
      if (graphRef.current) {
        graphRef.current.dispose();
        unRegisterShape(operatorParameters);
      }
      timer && clearTimeout(timer)
    };
  }, [dispatch, flowData, operatorParameters]);

  return (
    <>
      <div className="leftEditor">
        <div className="leftEditor-stencil">
          <div ref={stencilRef}></div>
        </div>
        <div className="leftEditor-editor">
          <div className="editor-content">
            <div className="header-bread">
              <Breadcrumb>{getSubprocess()}</Breadcrumb>
            </div>

            <div ref={editorContentRef} className="content-graph">
              {showMenuInfo.show && (
                <CustomMenu
                  top={showMenuInfo.top}
                  left={showMenuInfo.left}
                  node={showMenuInfo.node!}
                  graph={graphRef.current!}
                  show={showMenuInfo.show}
                  handleShowMenu={handleShowMenu}
                />
              )}
            </div>
          </div>
        </div>
      </div>

      {addPortInfo.node && (
        <AddModalPort
          onSubmit={(value: any) => handleSubmitPort(value)}
          onCancel={() => handleCancelPort()}
          modalVisible={addPortInfo.isShow}
          values={addPortInfo.node}
        />
      )}

      <NodeModalPreview
        onSubmit={() => handlePreviewSubmit()}
        onCancel={() => handlePreviewCancel()}
        modalVisible={previewInfo.isShow}
        values={previewInfo.values}
        node={previewInfo.node}
      />

      <DataSourceModal
        onSubmit={(value: any) => handleDataSourceSubmit(value)}
        onCancel={() => handleDataSourceCancel()}
        modalVisible={dataSourceInfo.isShowModal}
        values={dataSourceInfo.datas}
        node={dataSourceInfo.node}
        type={dataSourceInfo.type}
      />
      <GroupName
        onSubmit={(value: any) => handleGroupNameSubmit(value)}
        onCancel={() => handleGroupNameCancel()}
        values={groupNameInfo.node}
      />
      {stencilMenuInfo.showStencilMenu && <StencilMenu />}
      <EdgeClickModal
        onSubmit={(value: any) => handleEdgeClickSubmit(value)}
        onCancel={() => handleEdgeClickCancel()}
        edgeInfo={edgeClickInfo.edgeInfo}
        graph={graphRef.current!}
      />
    </>
  );
});

export default LeftEditor;
