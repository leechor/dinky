import {memo, useEffect, useRef, useState} from 'react';
import {Cell, Edge, Graph, Node} from '@antv/x6';
import {cloneDeep} from 'lodash';
import {Breadcrumb, message} from 'antd';
import {CaretRightOutlined} from '@ant-design/icons';

import {handleInitPort} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/ports-register';
import {initGraph} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/init-graph';
import {stencilComponentsLoader} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/stencil-components-loader';
import {initStencil} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/init-stencil';
import {handleInitNodes} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/node-by-data-loader';
import registerShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/shape-register';
import unRegisterShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/shape-unregister';
import {useAppDispatch, useAppSelector,} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import {CustomMenu} from './menu';
import {initMenu} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/init-menu';
import NodeModalForm from '@/components/Studio/StudioGraphEdit/GraphEditor/components/node-modal-form';
import NodeModalPreview from '../../../components/node-preview-modal';
import AddModalPort from '../../../components/add-port-modal';
import {
  changeCurrentSelectNode,
  changeDataSourceInfo,
  changeGroupNameInfo,
  changePositon,
  changePreviewInfo,
<<<<<<< HEAD
  changeDataSourceInfo,
  removeGraphTabs,
  changeGroupNameInfo,
  changeStencilMenuInfo,
  GroupTabItem,
  changeEdgeClickInfo
=======
  changeStencilMenuInfo,
  GroupTabItem,
  initFlowDataAction,
  removeGraphTabs
>>>>>>> f413ced42f344e1242f631ab1eedf45185b79b17
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import localCache from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/localStorage"
import CustomShape from '../../../utils/cons';
import DataSourceModal from '../../../components/data-source-modal';
import GroupName from '../../../components/group-name-modal';
<<<<<<< HEAD
import EdgeClickModal from '../../../components/edge-click-modal';
import { shrinkGroupNode } from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/graph-helper";
import { saveCustomGroupInfo, changeCustomGroupInfo } from '@/components/Common/crud';
import { StencilMenu } from '../../../components/stencil-menu';
import { initFlowDataAction } from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';

=======
import {shrinkGroupNode} from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/graph-helper";
import {changeCustomGroupInfo, saveCustomGroupInfo} from '@/components/Common/crud';
import {StencilMenu} from '../../../components/stencil-menu';
>>>>>>> f413ced42f344e1242f631ab1eedf45185b79b17


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
enum DataSourceType {
  Mysql = "Mysql"
}
type SubGraphCells = {
  [oldCellId: string]: Cell
}
type MenuInfo = {
  x: number,
  y: number,
  node: Node,
  showStencilMenu: boolean

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
export const warningTip = (code: number, msg: string) => {
  if (code === 1) {
    message.error(msg)
  } else {
    message.success(msg)
  }
}
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

  const [modalVisible, isModalVisible] = useState(false)
  const [parametersConfig, setParametersConfig] = useState<ParametersData>({
    isOutputs: true,
    parametersConfig: [],
    readConfigData: {} as ReadConfigData
  } as ParametersData)
  const editorContentRef = useRef(null);
  const graphRef = useRef<Graph>();
  const stencilRef = useRef(null);
  let v: HTMLDivElement
  let h: HTMLDivElement
  let c: HTMLDivElement

  const dispatch = useAppDispatch();
  const { stencilMenuInfo }: { stencilMenuInfo: MenuInfo } = useAppSelector((state) => ({
    stencilMenuInfo: state.home.stencilMenuInfo

  }));

  const { flowData, operatorParameters: operatorParameters, groupNameInfo,
    jsonEditor, taskName, tabs, previewInfo, dataSourceInfo, edgeClickInfo }: { tabs: GroupTabItem[], [key: string]: any }
    = useAppSelector((state) => ({
      flowData: state.home.flowData,
      operatorParameters: state.home.operatorParameters,
      jsonEditor: state.home.editor,
      taskName: state.home.taskName,
      tabs: state.home.graphTabs,
      previewInfo: state.home.previewInfo,
      dataSourceInfo: state.home.dataSourceInfo,
      groupNameInfo: state.home.groupNameInfo,
      edgeClickInfo: state.home.edgeClickInfo
    }));

  useEffect(() => {
    start()

    return () => {
      document.removeEventListener('mousemove', onMouseMove)
    }
  }, [])
  const cancelShowMenu = () => {
    dispatch(changeStencilMenuInfo({ x: 0, y: 0, showStencilMenu: false, node: null }))
  }
  useEffect(() => {
    const element = document.getElementsByClassName("leftEditor")
    for (let ele of Array.from(element)) {
      ele.addEventListener("click", cancelShowMenu)
    }
    return () => {
      for (let ele of Array.from(element)) {
        ele.removeEventListener("click", cancelShowMenu)
      }
    }
  }, [])

  const onMouseMove = (e: MouseEvent) => {
    if (!e.altKey) {
      return
    }

    const target = e.target as HTMLElement;
    const graph = graphRef.current!
    if (
      graph.container.contains(target) ||
      graph.container === target ||
      target === v ||
      target === h ||
      target === c
    ) {
      const pageX = e.pageX
      const pageY = e.pageY
      const clientX = e.clientX
      const clientY = e.clientY
      v.style.left = `${pageX + 2}px`
      h.style.top = `${pageY + 2}px`

      c.style.left = `${pageX + 10}px`
      c.style.top = `${pageY + 10}px`

      const p1 = graph.pageToLocal(pageX, pageY)
      const p2 = graph.localToGraph(p1)

      c.innerHTML = `
        <div>Page(pageX, pageY): ${pageX} x ${pageY}</div>
        <div>Client(clientX, clientY): ${clientX} x ${clientY}</div>
        <div>Local Point: ${p1.x} x ${p1.y}</div>
        <div>Graph Point: ${p2.x} x ${p2.y}</div>
        `
    } else {
      v.style.left = `${-1000}px`
      h.style.top = `${-1000}px`
      c.style.left = `${-10000}px`
      c.style.top = `${-10000}px`
    }
  }

  const start = () => {
    const root = document.getElementById('root')!
    v = document.createElement('div')
    h = document.createElement('div')
    c = document.createElement('div')

    v.style.position = 'absolute'
    v.style.width = '1px'
    v.style.top = '0'
    v.style.bottom = '0'
    v.style.left = '-100px'
    v.style.zIndex = `99`
    v.style.borderLeft = '1px dashed red'

    h.style.position = 'absolute'
    h.style.height = '1px'
    h.style.left = '0'
    h.style.right = '0'
    h.style.top = '-100px'
    h.style.zIndex = `99`
    h.style.borderTop = '1px dashed red'

    c.style.position = 'absolute'
    c.style.display = 'inline-block'
    c.style.fontSize = '12px'
    c.style.zIndex = `99`
    c.style.padding = '4px 8px'
    c.style.borderRadius = '2px'
    c.style.lineHeight = '20px'
    c.style.background = '#f6ffed'
    c.style.border = '1px solid #b7eb8f'

    root.appendChild(v)
    root.appendChild(h)
    root.appendChild(c)
    document.addEventListener('mousemove', onMouseMove)
  }

  const getNewConfig = (cell: Cell, config: any) => {

    if (!cell.getData() || !cell.getData()["config"][0]) {
      return cloneDeep(config)
    }
    let oldConfig = cloneDeep(cell.getData()["config"][0])
    for (let newKey in config) {
      oldConfig[newKey] = config[newKey]
    }
    return oldConfig
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

  const readConfigFromData = (currentCell: Cell, sourceCell: Cell, sourcePortId: string, currentPort: string, id: string) => {
    let parametersByOutPortConfig = currentCell.getData().config[0]
    if (!parametersByOutPortConfig[id]) {
      message.warning("请设置输入参数配置!");
      return
    }
    let parametersConfig: ParametersConfigType[] = parametersByOutPortConfig[id]
    //c从config读取，是否筛选出flag true,第一次连线，config里均为true,点击确定修改配置后config有false,但也应该显示，所以不删选字段
    // parametersConfig = parametersConfig.filter(item => item.flag)
    setParametersConfig({
      isOutputs: false,
      parametersConfig: parametersConfig,
      readConfigData: { currentCell, currentPort, id }
    })
    isModalVisible(true)
  }
  const handleNodeConfigSet = (graph: Graph) => {

    graph.on("node:port:click", ({ node, port }: { node: Node, port: string }) => {
      dispatch(changeCurrentSelectNode(node))

      if (!node.getData() && node.shape !== CustomShape.DUPLICATE_OPERATOR) {
        message.warning("请设置节点参数！")
        return
      }
      if (!node.getData() && node.shape === CustomShape.DUPLICATE_OPERATOR) {
        message.warning("请检查输入源或连线！")
        return
      }
      // 输出连接桩点击
      if (node.getPort(port)?.group === portType.outputs) {
        if (node.shape === CustomShape.DUPLICATE_OPERATOR) {
          const flag = isConnected(graphRef.current!, node, port, true)
          if (flag) {
            const edges = graph.model.getOutgoingEdges(node)
            for (let edge of edges!) {
              let targetPortId = edge.getTargetPortId()
              let targetNode = edge.getTargetNode()
              let sourcePortId = edge.getSourcePortId();
              if (sourcePortId == port) {
                let id = `${node.id}&${port} ${targetNode!.id}&${targetPortId}`
                let parametersByOutPortConfig = node.getData().config[0]
                if (!parametersByOutPortConfig[id]) {
                  message.warning("请设置输入参数配置!");
                  return
                }
                let parametersConfig: ParametersConfigType[] = parametersByOutPortConfig[id]
                setParametersConfig({
                  isOutputs: true,
                  parametersConfig: parametersConfig,
                  readConfigData: { currentCell: node, currentPort: port, id }
                })
                isModalVisible(true)

              }
            }
          } else {
            //没连线 修改自身连接装config
            let parametersByOutPortConfig = node.getData().config[0]
            if (!parametersByOutPortConfig[port]) {
              message.warning("请设置输入参数配置!");
              return
            }
            let parametersConfig: ParametersConfigType[] = parametersByOutPortConfig[port]
            setParametersConfig({
              isOutputs: true,
              parametersConfig: parametersConfig,
              readConfigData: { currentCell: node, currentPort: port, id: port }
            })
            isModalVisible(true)
          }
        } else {
          //修改（如果是DuplicateOperator则需要从输入节点的config里读取 ）
          const parametersConfig: ParametersConfigType[] = node.getData()?.parameters.columns;
          if (parametersConfig.some(config => !config.name)) {
            message.warning("节点参数名称不能为空！");
            return
          }
          setParametersConfig({
            isOutputs: true,
            parametersConfig: [...parametersConfig],
            readConfigData: {
              currentCell: node,
              currentPort: port,
            }
          })
          isModalVisible(true)
        }

      } else {
        const edges = graph.model.getIncomingEdges(node)
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

    graph.on("edge:connected", ({ isNew, edge, currentCell, currentPort }) => {
      //创建新边
      if (isNew) {
        //拿到该边的sourcecell和sourcePortId
        const sourcePortId = edge.getSourcePortId()
        const sourceCell = edge.getSourceCell()
        //获取source的columns
        if (!sourceCell?.getData()) return
        let parametersConfig: ParametersConfigType[];
        if (sourceCell.shape === CustomShape.DUPLICATE_OPERATOR) {
          //修改（如果是DuplicateOperator则需要从输入节点的config里读取 ）
          parametersConfig = sourceCell?.getData()["config"][0][sourcePortId!]
        } else {
          parametersConfig = sourceCell?.getData()?.parameters.columns;
        }
        if (parametersConfig.some(config => !config.name)) {
          message.warning("节点参数名称不能为空！");
          return
        }
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
          if (currentCell.shape === CustomShape.DUPLICATE_OPERATOR) {
            //连接的是自定义节点遍历节点config,重新赋值
            //设置前节点和当前节点连接config
            let currentNode = edge.getTargetNode()
            newConfigObj = getNewConfig(currentCell, config)
            const outPortIds = currentNode?.getPortsByGroup("outputs").map(data => data.id)
            if (outPortIds) {
              for (let key of outPortIds) {
                newConfigObj[key!] = cloneDeep(config)
              }
            }
            const edges = graph.model.getOutgoingEdges(currentCell);
            if (edges) {
              for (let edge of edges) {
                let targetNode = edge.getTargetNode();
                let targetPortId = edge.getTargetPortId();
                let sourcePortId = edge.getSourcePortId();
                let id = `${currentNode!.id}&${sourcePortId} ${targetNode!.id}&${targetPortId}`
                newConfigObj[id] = cloneDeep(config)
              }
            }


          }
          currentCell.setData({
            ...(currentCell.getData() ? currentCell.getData() : {}),
            config: [newConfigObj]
          }, { overwrite: true });
          if (sourceCell.shape === CustomShape.DUPLICATE_OPERATOR) {
            //把input-output config 设置进自定义节点config
            let oldConfigObj = sourceCell.getData()["config"][0]
            oldConfigObj[id] = parametersConfig
            sourceCell.setData({
              ...(sourceCell.getData() ? sourceCell.getData() : {}),
              config: [cloneDeep(oldConfigObj)]
            }, { overwrite: true })
          }
          //从node-data 读取config
          readConfigFromData(currentCell, sourceCell, sourcePortId, currentPort, id)
        }
      }
    })
  }
  const handleCancel = () => {
    isModalVisible(false);
  }
  const handleCancelPort = () => {
    setShowMenuInfo({ ...showMenuInfo, show: true, node: null })
  }

  const handleSubmit = (value: CompareCheckProps) => {
    value.origin.forEach(item => {
      item.flag = value.parameters.includes(item.name);
    })
    if (value.isOutputs) {
      //判断是否连线
      const flag = isConnected(graphRef.current!, value.readConfigData.currentCell, value.readConfigData.currentPort, value.isOutputs)
      //同步选中节点信息   修改columns
      // value.readConfigData.currentCell.setData({ parameters: { columns: value.origin } });
      if (value.readConfigData.currentCell.shape !== CustomShape.DUPLICATE_OPERATOR) {
        value.readConfigData.currentCell.getData().parameters.columns = value.origin;
        //同步jsoneditor
        jsonEditor.setValue(value.readConfigData.currentCell.getData()?.parameters)
      }
      if (flag) {
        let node = value.readConfigData.currentCell
        //找到对应targetCell 设置config
        let edges = graphRef.current!.model.getOutgoingEdges(value.readConfigData.currentCell);
        for (let edge of edges!) {
          const sourcePortId = edge.getSourcePortId();
          if (sourcePortId === value.readConfigData.currentPort) {
            let targetCell = edge.getTargetNode();
            let targetPortId = edge.getTargetPortId()
            //修改target里config（目前直接覆盖，后面考虑对比保存）
            let configMap: Map<string, ParametersConfigType[]> = new Map();
            let id = `${value.readConfigData.currentCell.id}&${sourcePortId} ${targetCell!.id}&${targetPortId}`
            let filterConfigMap = value.origin.filter(item => item.flag)
            if (value.readConfigData.currentCell.shape === CustomShape.DUPLICATE_OPERATOR) {
              //修改自身config   同步下个cell config
              let oldConfigObj = value.readConfigData.currentCell.getData()["config"][0]
              oldConfigObj[id] = cloneDeep(value.origin)
              node.setData({ ...(node?.getData() ?? {}), config: [cloneDeep(oldConfigObj)] }, { overwrite: true })
            }
            configMap.set(id, filterConfigMap);
            let config = strMapToObj(configMap)
            let newConfigObj = getNewConfig(targetCell!, config)
            targetCell!.setData({
              ...(targetCell?.getData() ? targetCell.getData() : {}),
              config: [newConfigObj]
            }, { overwrite: true });
          }
        }
      } else {
        //没连线
        if (value.readConfigData.currentCell.shape === CustomShape.DUPLICATE_OPERATOR) {
          //修改自身的节点config
          value.readConfigData.currentCell.getData()["config"][0][value.readConfigData.currentPort] = cloneDeep(value.origin)
        }
      }
    } else {
      const flag = isConnected(graphRef.current!, value.readConfigData.currentCell, value.readConfigData.currentPort, value.isOutputs)
      if (!flag) return
      if (value.readConfigData.currentCell.shape === CustomShape.DUPLICATE_OPERATOR) {
        const edges = graphRef.current!.model.getIncomingEdges(value.readConfigData.currentCell)
        for (let edge of edges!) {
          let currentNode = edge.getTargetNode();
          // const outPortIds = currentNode!.getPortsByGroup("outputs").map(data => data.id)
          let newConfigObj = {};
          //将下个节点的所有连接装都重置(不影响前节点config保持input-output config不变)
          let newConfig = value.origin.filter(config => config.flag)


          // newConfigObj[value.readConfigData.id!] = cloneDeep(value.readConfigData.currentCell.getData()["config"][0][value.readConfigData.id!])
          // for (const addPortId of outPortIds!) {
          //   newConfigObj[addPortId!] = cloneDeep(newConfig)
          // }
          for (let key in value.readConfigData.currentCell.getData()["config"][0]) {
            if (key !== value.readConfigData.id) {
              newConfigObj[key] = cloneDeep(newConfig)
            } else {
              newConfigObj[key] = cloneDeep(value.readConfigData.currentCell.getData()["config"][0][key])
            }

          }

          currentNode?.setData({
            ...(currentNode.getData() ? currentNode.getData() : {}),
            config: [newConfigObj]
          }, { overwrite: true })
        }
      } else {
        //如果是输入则修改config
        let configMap: Map<string, ParametersConfigType[]> = new Map();
        configMap.set(value.readConfigData.id!, value.origin);
        let config = strMapToObj(configMap)
        let newConfigObj = getNewConfig(value.readConfigData.currentCell!, config)
        value.readConfigData.currentCell.setData({
          ...(value.readConfigData.currentCell.getData() ? value.readConfigData.currentCell.getData() : {}),
          config: [newConfigObj]
        }, { overwrite: true });
      }

    }
  }
  const handleSubmitPort = (value: any) => {
    const node = showMenuInfo.node
    // 添加连接桩
    node!.addPort({
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
    const portId = node?.getPortsByGroup("inputs")[0].id;
    const outPortIds = node?.getPortsByGroup("outputs").map(data => data.id)
    const flag = isConnected(graphRef.current!, node!, portId!, false)
    const edges = graphRef.current!.model.getIncomingEdges(node!)
    if (flag) {
      //如果是连线状态下点击(有设置config)
      // 获取之前的config
      // let oldConifg=node?.getData()["config"][0];
      for (let edge of edges!) {
        const targetPortId = edge.getTargetPortId();
        const targetCell = edge.getTargetCell();
        const sourcePortId = edge.getSourcePortId()
        const sourceCell = edge.getSourceCell()
        let id = `${sourceCell!.id}&${sourcePortId} ${targetCell!.id}&${targetPortId}`
        //获取最新输0-0连接桩配置
        if (targetCell?.getData().config) {
          let parametersByOutPortConfig = targetCell.getData().config[0]
          if (!parametersByOutPortConfig[id]) {
            message.warning("请设置输入参数配置!");
            return
          }
          let parametersConfig: ParametersConfigType[] = parametersByOutPortConfig[id]
          // 给每个连接桩赋初始值
          let newConfigObj = {};
          newConfigObj[id] = parametersConfig;
          for (const addPortId of outPortIds!) {
            newConfigObj[addPortId!] = cloneDeep(parametersConfig)
          }
          node?.setData({ ...(node.getData() ? node.getData() : {}), config: [newConfigObj] })
        }
      }
    } else {
      //非连线下直接添加新的config
      let configMap: Map<string, ParametersConfigType[]> = new Map();
      configMap.set(value.portName, []);
      let newConfigObj = strMapToObj(configMap)
      node?.setData({ ...(node.getData() ? node.getData() : {}), config: [newConfigObj] })
    }
    message.success("连接桩添加成功！")

  }
  const handleShowMenu = (value: boolean) => {
    setShowMenuInfo({ ...showMenuInfo, show: value })
  }

  const handleGroupNameSubmit = (value: any) => {

    if (value.type === "ADD") {
      let subGroupObj: SubGraphCells = value.node && graphRef.current!.cloneSubGraph([graphRef.current!.getCellById(value.node.id)], { deep: true });
      let groupJson: Cell<Cell.Properties>[] = []
      subGroupObj && Object.values(subGroupObj).forEach((cls) => {
        groupJson.push(cls)
      })
      saveCustomGroupInfo("/api/zdpx/customer/save", {
        script: JSON.stringify({ cells: groupJson }),
        name: value.groupName
      }).then(res => {
        warningTip(res.code, res.msg)
        dispatch(initFlowDataAction())
      })


    } else {
      changeCustomGroupInfo(`/api/zdpx/customer/oldName/${value.node.prop().name}/newName/${value.groupName}`).then(res => {
        warningTip(res.code, res.msg)
      })
    }
    dispatch(initFlowDataAction())
  }
  const handleGroupNameCancel = () => {
    dispatch(changeGroupNameInfo({ isShowGroupNameModal: false }))
  }

  const handlePreviewCancel = () => {
    dispatch(changePreviewInfo({ node: null, values: "", isShow: false }))
  }
  const handlePreviewSubmit = () => { }
  const handleDataSourceSubmit = (datas: any) => {
    const { tableItem, value: data, type, tableName } = datas
    //将数据源配置到config

    switch (type) {
      case DataSourceType.Mysql:
        const jsonconfig = cloneDeep(jsonEditor.getValue());
        const { url, username, password } = tableItem;
        const getType = (str: string) => {
          const lastIndex = str.lastIndexOf("_");
          if (lastIndex !== -1) {
            const result = str.substring(lastIndex + 1)
            return result
          } else {
            return str
          }
        }
        const columns = data.map((item: any) => ({
          flag: true,
          type: getType(item.javaType),
          name: item.name
        }))
        const newJsonConfig = { ...jsonconfig, url, username, password, columns, tableName }
        jsonEditor.setValue(newJsonConfig)
        message.success("配置成功！")
        break
    }



  }
  const handleDataSourceCancel = () => {
    dispatch(changeDataSourceInfo({ datas: "", isShowModal: false, node: null, type: "" }))
  }
  const changeNode = (node: Cell) => {
    dispatch(changeCurrentSelectNode(node))
  }
  const handleEdgeClickSubmit = (value: any) => {
    dispatch(changeEdgeClickInfo({ ...edgeClickInfo, data: Date.now() }))
  }
  const handleEdgeClickCancel = () => {
    dispatch(changeEdgeClickInfo({ isShowedgeClickModal: false, edgeInfo: null, data: null }))
  }
  const changePosition = (x: number, y: number) => {
    dispatch(changePositon({ x, y }))
  }
  //上方面包屑控制画布群组导航
  const tabClick = (clickLayer: number) => {

    //如果点击的是最新并且未设置群组
    if ((clickLayer === 0 && tabs.length === 0) || clickLayer === tabs.length - 1) {
      return
    }

    if (!graphRef.current) {
      return
    }

    const graph = graphRef.current;

    const groupNode = graph.getCellById(tabs[clickLayer + 1].groupCellId) as Node;
    shrinkGroupNode(graph, groupNode);

    const otherTopCells = graph.getCellById(tabs[clickLayer].groupCellId)?.getChildren() ??
      graph.getCells().filter(cell => !cell.parent) ?? []

    //设置外部元素可选
    graph.setSelectionFilter(cell => otherTopCells.map((c: Cell) => c.id).includes(cell.id))

    otherTopCells.forEach((cell: Cell) => {
      cell.show()
      if (cell.shape == CustomShape.GROUP_PROCESS) {
        cell.setAttrs({ fo: { visibility: "visible" } })
      }
    });
    dispatch(removeGraphTabs(clickLayer))
  }

  const processTabClick = (index: number) => {
    for (let i = tabs.length - 1; i >= index; i--) {
      tabClick(i)
    }
  }

  const getSubprocess = () => {
    if (tabs.length === 0) {
      return
    }

    return tabs.map((tab: GroupTabItem, index) =>
      <Breadcrumb.Item onClick={() => (processTabClick(index))} key={index} >
        {<span style={{ cursor: "pointer", display: "inline-block" }}>{`subprocess${index}`}</span>}
        <CaretRightOutlined />
      </Breadcrumb.Item>
    )
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
        jsonEditor,
      );
      window.graph = graphRef.current
      initMenu(graphRef.current, setShowMenuInfo, changeNode, changePosition);

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

        const data = localCache.getCache(`${taskName}graphData`)
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
      <div className="leftEditor">
        <div className="leftEditor-stencil">
          <div ref={stencilRef}></div>
        </div>
        <div className="leftEditor-editor">

          <div className="editor-content">
            <div className="header-bread">
              <Breadcrumb>
                {getSubprocess()}
              </Breadcrumb>
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

      <NodeModalForm onSubmit={(value: any) => handleSubmit(value)}
        onCancel={() => handleCancel()}
        modalVisible={modalVisible}
        values={parametersConfig} />

      {showMenuInfo.node ? <AddModalPort onSubmit={(value: any) => handleSubmitPort(value)}
        onCancel={() => handleCancelPort()}
        modalVisible={!showMenuInfo.show && showMenuInfo.node.shape === CustomShape.DUPLICATE_OPERATOR}
        values={showMenuInfo.node} /> : null}

      <NodeModalPreview
        onSubmit={() => handlePreviewSubmit()}
        onCancel={() => handlePreviewCancel()}
        modalVisible={previewInfo.isShow}
        values={previewInfo.values}
        node={previewInfo.node} />

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
      {stencilMenuInfo.showStencilMenu &&
        <StencilMenu />}
      <EdgeClickModal onSubmit={(value: any) => handleEdgeClickSubmit(value)}
        onCancel={() => handleEdgeClickCancel()} edgeInfo={edgeClickInfo.edgeInfo} graph={graphRef.current!} />
    </>

  );
});

export default LeftEditor
