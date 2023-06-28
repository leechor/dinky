import { memo, useEffect, useRef, useState, FC } from 'react';
import { Graph, Node, Cell, Edge } from '@antv/x6';
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
import NodeModalForm from '@/components/Studio/StudioGraphEdit/GraphEditor/components/node-modal-form';
import styles from './index.less';
import { message } from 'antd';
import AddModalPort from '../../../components/add-port-modal';
import {
  changeCurrentSelectNode,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import localCache from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/localStorage"

export interface ParametersConfigType {
  name: string,
  flag: boolean,
  type?: string,
}
interface PortTypes {
  inputs: string,
  outputs: string,
}
interface CompareCheckProps {
  origin: ParametersConfigType[],
  parameters: string[],
  isOutputs: boolean,
  readConfigData: ReadConfigData,
}
interface ReadConfigData {
  currentCell: Cell,
  currentPort: string,
  id?: string
}
export interface ParametersData {
  isOutputs: boolean,
  parametersConfig: ParametersConfigType[],
  readConfigData: ReadConfigData
}
function strMapToObj(strMap: Map<string, ParametersConfigType[]>) {
  let obj = {};
  for (let [key, value] of strMap) {
    obj[key] = value
  }
  return obj
}
const portType: PortTypes = { inputs: "inputs", outputs: "outputs" }
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
    node: null
  });
  const [modalVisible, handlemodalVisible] = useState(false)
  const [parametersConfig, setParametersConfig] = useState<ParametersData>({ isOutputs: true, parametersConfig: [], readConfigData: {} as ReadConfigData } as ParametersData)
  const editorContentRef = useRef(null);
  const graphRef = useRef<Graph>();
  const stencilRef = useRef(null);

  const dispatch = useAppDispatch();

  const { flowData, operatorParameters: operatorParameters, jsonEditor } = useAppSelector((state) => ({
    flowData: state.home.flowData,
    operatorParameters: state.home.operatorParameters,
    currentSelectNode: state.home.currentSelectNode,
    jsonEditor: state.home.editor
  }));
  const getNewConfig = (cell: Cell, config: any) => {

    if (!cell.getData() || !cell.getData()["config"][0]) {
      return config
    }
    let oldConifg = cell.getData()["config"][0]
    for (let newKey in config) {
      oldConifg[newKey] = config[newKey]
    }
    return oldConifg
  }
  const isConnected = (graph: Graph, node: Cell, id: string, isOutputs: boolean) => {
    let edges: Edge[] | null;
    if (isOutputs) {
      edges = graph.model.getOutgoingEdges(node)
      if (!edges) return false;
      return edges.some(edge => edge.getSourcePortId() === id)
    } else {
      edges = graph.model.getIncomingEdges(node)
      if (!edges) return false;
      return edges.some(edge => edge.getTargetPortId() === id)
    }
  }
  const handleNodeConfigSet = (graph: Graph) => {
    graph.on("node:port:click", ({ node, port }: { node: Node, port: string }) => {
      dispatch(changeCurrentSelectNode(node))

      if (!node.getData()&&node.shape!=="DuplicateOperator") {
        message.warning("请设置节点参数！")
        return
      }

      if(!node.getData()&&node.shape==="DuplicateOperator"){
        message.warning("请检查输入源或连线！")
        return
      }

      // 输出  由editor配置
      if (node.getPort(port)?.group === portType.outputs) {

        //修改（如果是DuplicateOperator则需要从输入节点的config里读取 ）
        const parametersConfig: ParametersConfigType[] = node.getData()?.parameters.columns;
        if (parametersConfig.some(config => !config.name)) { message.warning("节点参数名称不能为空！"); return }
        setParametersConfig({
          isOutputs: true,
          parametersConfig: [...parametersConfig],
          readConfigData: {
            currentCell: node,
            currentPort: port,
          }
        })
        handlemodalVisible(true)
      } else {
        const edges = graph.model.getIncomingEdges(node)
        console.log(edges, ".....");

        if (!edges || !isConnected(graphRef.current!, node, port, false)) {
          message.warning("请选择连线！");
          return
        }

        for (let edge of edges) {
          const targetPortId = edge.getTargetPortId();
          const targetCell = edge.getTargetCell();
          const sourcePortId = edge.getSourcePortId()
          const sourceCell = edge.getSourceCell()
          //连接边的终点portid 与 当前点击id一致
          if (targetPortId === port) {
            //获取config
            let id = `${sourceCell!.id}&${sourcePortId} ${targetCell!.id}&${targetPortId}`
            //获取最新输出配置
            if (targetCell?.getData().config) {
              readConfigFromData(targetCell, sourceCell!, sourcePortId!, targetPortId!, id)
            }
          }
        }
      }
    })

    //读取target-config配置，界面回显
    function readConfigFromData(currentCell: Cell, sourceCell: Cell, sourcePortId: string, currentPort: string, id: string) {
      let parametersByOutPortConfig = currentCell.getData().config[0]
      if (!parametersByOutPortConfig[id]) { message.warning("请设置输入参数配置!"); return }
      let parametersConfig: ParametersConfigType[] = parametersByOutPortConfig[id]
      //c从config读取，是否筛选出flag true,第一次连线，config里均为true,点击确定修改配置后config有false,但也应该显示，所以不删选字段
      // parametersConfig = parametersConfig.filter(item => item.flag)
      setParametersConfig({
        isOutputs: false,
        parametersConfig: parametersConfig,
        readConfigData: { currentCell, currentPort, id }
      })
      handlemodalVisible(true)
    }

    graph.on("edge:connected", ({ isNew, edge, currentCell, currentPort }) => {
      //创建新边
      if (isNew) {
        //拿到该边的sourcecell和sourcePortId
        const sourcePortId = edge.getSourcePortId()
        const sourceCell = edge.getSourceCell()
        //获取source的columns
        if (!sourceCell?.getData()) return

        //修改（如果是DuplicateOperator则需要从输入节点的config里读取 ）
        let parametersConfig: ParametersConfigType[] = sourceCell?.getData()?.parameters.columns;
        if (parametersConfig.some(config => !config.name)) { message.warning("节点参数名称不能为空！"); return }
        //将输出数据筛选后设置给输入【flag为true】
        parametersConfig = parametersConfig.filter(item => item.flag)
        if (!parametersConfig) return
        let configMap: Map<string, ParametersConfigType[]> = new Map();
        //转换为config设置到当前节点的node-data里
        if (sourceCell && sourcePortId && currentCell && currentPort) {
          let id = `${sourceCell.id}&${sourcePortId} ${currentCell.id}&${currentPort}`
          configMap.set(id, parametersConfig);
          let config = strMapToObj(configMap)
          let newConfigObj = getNewConfig(currentCell, config)
          currentCell.setData({ ...(currentCell.getData() ? currentCell.getData() : {}), config: [newConfigObj] }, { overwrite: true });

          //从node-data 读取config
          readConfigFromData(currentCell, sourceCell, sourcePortId, currentPort, id)
        }
      }

    })

  }

  const handleCancel = () => {
    handlemodalVisible(false);
  }
  const handleCancelPort = () => {
    setShowMenuInfo({ ...showMenuInfo, show: true, node: {} as Node })
  }


  const handleSubmit = (value: CompareCheckProps) => {

    value.origin.forEach(item => {
      if (value.parameters.includes(item.name)) {
        item.flag = true;
      } else {
        item.flag = false
      }
    })
    if (value.isOutputs) {
      //判断是否连线
      const flag = isConnected(graphRef.current!, value.readConfigData.currentCell, value.readConfigData.currentPort, value.isOutputs)
      //同步选中节点信息   修改columns
      // value.readConfigData.currentCell.setData({ parameters: { columns: value.origin } });
      value.readConfigData.currentCell.getData().parameters.columns = value.origin;
      //同步jsoneditor
      jsonEditor.setValue(value.readConfigData.currentCell.getData()?.parameters)
      if (flag) {

        //找到对应targetCell 设置config
        let edges = graphRef.current!.model.getOutgoingEdges(value.readConfigData.currentCell);
        for (let edge of edges!) {
          const sourcePortId = edge.getSourcePortId();
          if (sourcePortId === value.readConfigData.currentPort) {
            let targetCell = edge.getTargetCell();
            let targetPortId = edge.getTargetPortId()
            //修改target里config（目前直接覆盖，后面考虑对比保存）
            let configMap: Map<string, ParametersConfigType[]> = new Map();
            let id = `${value.readConfigData.currentCell.id}&${sourcePortId} ${targetCell!.id}&${targetPortId}`
            let filterConfigMap = value.origin.filter(item => item.flag)
            configMap.set(id, filterConfigMap);
            let config = strMapToObj(configMap)
            let newConfigObj = getNewConfig(targetCell!, config)
            targetCell!.setData({ ... (targetCell?.getData() ? targetCell.getData() : {}), config: [newConfigObj] }, { overwrite: true });
          }
        }
      }
    } else {
      const flag = isConnected(graphRef.current!, value.readConfigData.currentCell, value.readConfigData.currentPort, value.isOutputs)
      console.log(flag);

      if (!flag) return
      //如果是输入则修改config
      let configMap: Map<string, ParametersConfigType[]> = new Map();
      configMap.set(value.readConfigData.id!, value.origin);
      let config = strMapToObj(configMap)
      let newConfigObj = getNewConfig(value.readConfigData.currentCell!, config)
      value.readConfigData.currentCell.setData({ ...(value.readConfigData.currentCell.getData() ? value.readConfigData.currentCell.getData() : {}), config: [newConfigObj] }, { overwrite: true });
    }

    console.log(graphRef.current?.toJSON(), "confirm");

  }
  const handleSubmitPort = (value: any) => {

    showMenuInfo.node!.addPort({
      group: 'outputs',
      zIndex: 999,
      id: value.portName,
      attrs: {
        text: {
          text: `${value.portName}`,
          style: {
            visibility: "hidden",
            fontSize: 10,
            fill: "#3B4351",
          },
        },
      },
      label: {
        position: "bottom",
      }
    })
    message.success("连接桩添加成功！")
  }
  const handleShowMenu = (value: boolean) => {
    setShowMenuInfo({ ...showMenuInfo, show: value })
  }
  useEffect(() => {
    if (editorContentRef.current) {
      const editorContentContainer: HTMLElement = editorContentRef.current;

      //1、初始化画布
      graphRef.current = initGraph(
        editorContentContainer,
        selectedNodes,
        setSelectedNodes,
        dispatch,
      );

      initMenu(graphRef.current, setShowMenuInfo);

      if (stencilRef.current) {
        const cloneRef: HTMLElement = stencilRef.current;

        //2、加载连接桩
        const ports = handleInitPort();

        //3、注册自定义节点图形
        registerShape(graphRef.current, ports, operatorParameters)

        //4、初始化stencil
        const stencil = initStencil(graphRef.current, cloneRef, operatorParameters);

        // 5、加载自定义的组件图形
        stencilComponentsLoader(graphRef.current, stencil, operatorParameters);

        // 6、加载数据
        const data = localCache.getCache("graphData")
        timer = handleInitNodes(graphRef.current, data);

        //加载连接装点击事件控制portmodal
        handleNodeConfigSet(graphRef.current)
      }
    }

    return () => {
      if (graphRef.current) {
        graphRef.current.dispose();
        unRegisterShape(operatorParameters);
      }
      if (timer) {
        clearTimeout(timer)
      }
    };
  }, [dispatch, flowData, operatorParameters]);

  return (
    <>
      <div className={styles['leftEditor']}>
        <div className={styles['leftEditor-stencil']}>
          <div ref={stencilRef}></div>
        </div>
        <div className={styles['leftEditor-editor']}>
          <div className={styles['editor-content']}>
            <div ref={editorContentRef} className={styles['x6-graph']}>
              {showMenuInfo.show && (
                <CustomMenu
                  top={showMenuInfo.top}
                  left={showMenuInfo.left}
                  node={showMenuInfo.node}
                  graph={graphRef.current!}
                  show={showMenuInfo.show}
                  handleShowMenu={handleShowMenu}
                />
              )}
            </div>
          </div>
        </div>
      </div>

      <NodeModalForm onSubmit={(value: any) => handleSubmit(value)}
        onCancel={() => handleCancel()}
        modalVisible={modalVisible}
        values={parametersConfig} />

      {showMenuInfo.node ? <AddModalPort onSubmit={(value: any) => handleSubmitPort(value)}
        onCancel={() => handleCancelPort()}
        modalVisible={!showMenuInfo.show && showMenuInfo.node.shape === "DuplicateOperator"}
        values={showMenuInfo.node} /> : null}

    </>

  );
});

export default LeftEditor
