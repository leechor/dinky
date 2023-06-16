import { memo, useEffect, useRef, useState, FC } from 'react';
import { Graph, Node, Cell } from '@antv/x6';
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
import PortModalForm from '@/components/Studio/StudioGraphEdit/GraphEditor/components/port-modal-form';
import styles from './index.less';
import { message } from 'antd';

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
  id: string,
}

export interface ParametersData {
  isOutputs: boolean,
  parametersConfig: ParametersConfigType[],
  id?: string,
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
  }>({
    show: false,
    top: 0,
    left: 0,
  });
  const [modalVisible, handlemodalVisible] = useState(false)
  const [parametersConfig, setParametersConfig] = useState<ParametersData>({ isOutputs: true, parametersConfig: [] } as ParametersData)
  const [selectedNode, setSelectedNode] = useState<Node>({} as Node)
  const editorContentRef = useRef(null);
  const graphRef = useRef<Graph>();
  const stencilRef = useRef(null);

  const dispatch = useAppDispatch();

  const { flowData, operatorParameters: operatorParameters, jsonEditor } = useAppSelector((state) => ({
    flowData: state.home.flowData,
    operatorParameters: state.home.operatorParameters,
    jsonEditor: state.home.editor
  }));

  const handlePortModal = (graph: Graph) => {
    graph.on("node:port:click", ({ node, port }: { node: Node, port: string }) => {
      if (!node.getData()) {
        message.warning("请先设置节点参数！")
        return
      }
      // 输出由editor配置
      if (node.getPort(port)?.group === portType.outputs) {
        const paramersConfig: ParametersConfigType[] = node.getData()?.parameters[0].columns
        setParametersConfig({
          isOutputs: true,
          parametersConfig: [...paramersConfig],
          id: ""
        })
        setSelectedNode(node)
        handlemodalVisible(true)
      } else {
        const edges = graph.model.getIncomingEdges(node)
        console.log(edges, ".....");
        if (!edges) return
        for (let edge of edges) {
          const targetPortId = edge.getTargetPortId();
          const targetCell = edge.getTargetCell();
          const sourcePortId = edge.getSourcePortId()
          const sourceCell = edge.getSourceCell()
          //连接边的终点portid 与 当前点击id一致
          if (targetPortId === port) {
            //获取config
            let id = `${sourceCell!.id}&${sourcePortId} ${targetCell!.id}&${targetPortId}`
            readConfigFromData(node,id)
          } else {
            return
          }
        }


      }




    })

    function readConfigFromData(currentCell:Cell,id:string) {
      debugger;
      console.log(currentCell.getData().config);
      
      const paramersByOutPortConfig = currentCell?.getData()?.config.find((item: any) => {
        for (let key in item) {
          if (key === id) {
            return true
          } else {
            return false
          }
        }
      })
      console.log(paramersByOutPortConfig[id]);
      if (!paramersByOutPortConfig[id]) { message.warning("请设置输入参数配置!"); return }
      let parametersConfig: ParametersConfigType[] = paramersByOutPortConfig[id]
      parametersConfig = parametersConfig.filter(item => item.flag)
      setParametersConfig({
        isOutputs: false,
        parametersConfig: parametersConfig,
        id
      })

      setSelectedNode(currentCell as Node)
      handlemodalVisible(true)
    }
    graph.on("edge:connected", ({ isNew, edge, currentCell, currentPort }) => {

      //创建新边
      if (isNew) {
        //拿到该边的sourcecell和sourcePortId
        const sourcePortId = edge.getSourcePortId()
        const sourceCell = edge.getSourceCell()
        //获取source的columns
        const paramersConfig: ParametersConfigType[] = sourceCell?.getData()?.parameters[0].columns;
        if(!paramersConfig) return
        let configMap: Map<string, ParametersConfigType[]> = new Map();
        //转换为config设置到当前节点的node-data里
        if (sourceCell && sourcePortId && currentCell && currentPort) {
          let id = `${sourceCell.id}&${sourcePortId} ${currentCell.id}&${currentPort}`
          configMap.set(id, paramersConfig);
          let config = strMapToObj(configMap)
          currentCell.setData({ config: [config] });
          //从node-data 读取config
          readConfigFromData(currentCell,id)
        }




      }

    })

  }

  const handleCancel = () => {
    handlemodalVisible(false);
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
      //如果是输出则修改columns


      //同步选中节点信息   修改columns
      selectedNode.setData({ parameters: [{ columns: value.origin }] });
      //同步jsoneditor
      jsonEditor.setValue(selectedNode.getData()?.parameters)
    } else {
      //如果是输入则修改config


      let configMap: Map<string, ParametersConfigType[]> = new Map();
      configMap.set(value.id, value.origin);
      let config = strMapToObj(configMap)
      selectedNode.setData({ config: [config] });
    }

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
        timer = handleInitNodes(graphRef.current, flowData);

        //加载连接装点击事件控制portmodal
        handlePortModal(graphRef.current)
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
                  graph={graphRef.current}
                />
              )}
            </div>
          </div>
        </div>
      </div>

      <PortModalForm onSubmit={(value: any) => handleSubmit(value)}
        onCancel={() => handleCancel()}
        modalVisible={modalVisible}
        values={parametersConfig} selectedNode={selectedNode} />

    </>

  );
});

export default LeftEditor;
