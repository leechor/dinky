import { map } from 'rxjs/operators';

import CustomShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons';
import { cloneDeep } from 'lodash';

import { message } from 'antd';
import { DataSourceType } from '../components/edge-click-modal/edge-click';
import type { Parameter } from "@/components/Studio/StudioGraphEdit/GraphEditor/ts-define/parameter"
import { Graph, Edge, Node, Cell } from "@antv/x6"
import { changeEdgeClickInfo } from '../store/modules/home';
import { getFunctionName } from '@/components/Common/crud';

export interface sourceConfigType {
    flag: boolean,
    name: string,
    type: string,
    decs: string,
    outName: string,
}

//判断是否为数据源类型节点
export const isSourceDataType = (schemaData: Parameter[], node: Node) => {
    const itemData = schemaData.find(item => item.code === node.shape)
    return itemData!.group.includes("dataSource")
}
//通过边获取源、目标节点及port
export const getSourceTargetByEdge = (graph: Graph, edge: Edge, dispatch: any) => {

    const targetInfo = getTargetNodeAndPort(edge, graph)
    const sourceInfo = getSourceNodeAndPort(edge, graph)
    if (typeof targetInfo !== null && typeof sourceInfo !== null) {
        const targetNode = targetInfo?.targetNode!
        const targetPortId = targetInfo?.targetPortId!
        const sourceNode = sourceInfo?.sourceNode!;
        const sourcePortId = sourceInfo?.sourcePortId!;
        const id = getId(sourceNode, sourcePortId, targetNode, targetPortId)
        if (sourceNode.getData() !== undefined) {
            if (!sourceNode.getData().hasOwnProperty("parameters") || !sourceNode.getData().hasOwnProperty("config")) {
                message.warning("请设置输出原始配置!")
                return
            } else {
                let columns = [];
                if (sourceNode.shape === CustomShape.DUPLICATE_OPERATOR) {
                    
                    columns = sourceNode.getData()["config"][0][id]
                } else {
                    columns = sourceNode.getData()?.parameters.output.columns
                }
                let data: string[] = []
                if (columns.length) {
                    getFunctionName("/api/zdpx/getFunction", { columns }).then(res => {
                        if (res.code === 0) {
                            data = res.datas
                            dispatch(changeEdgeClickInfo({
                                isShowedgeClickModal: true,
                                edgeInfo: { edge, sourceNode, sourcePortId, targetNode, targetPortId },
                                data: data,
                            }))
                        } else {
                            message.error("获取函数别名失败" + res.msg)
                            data = []
                        }

                    })
                } else {
                    dispatch(changeEdgeClickInfo({
                        isShowedgeClickModal: true,
                        edgeInfo: { edge, sourceNode, sourcePortId, targetNode, targetPortId },
                        data,
                    }))
                }


            }
        } else {
            message.warning("请设置数据源参数配置!")
            return
        }

    } else {
        message.warning("请先确定是否连线！")
    }
}
export const getTargetNodeAndPort = (edge: Edge, graph: Graph): { targetNode: Node, targetPortId: string } | null => {

    const node = edge.getTargetNode()!
    if (node.shape !== CustomShape.GROUP_PROCESS) {
        return { targetNode: node, targetPortId: edge.getTargetPortId()! }
    } else {
        const portIdOutter = edge.getTargetPortId();
        let findEdge: Edge<Edge.Properties>[] | undefined

        if (!portIdOutter?.includes("_in")) {
            //从外面连线
            findEdge = graph.getOutgoingEdges(node)?.filter(edge => {
                return edge.getSourcePortId() === `${portIdOutter}_in`
            })
        } else {
            //从里面连线
            findEdge = graph.getOutgoingEdges(node)?.filter(edge => {
                return edge.getSourcePortId() === portIdOutter.substring(0, portIdOutter.lastIndexOf("_in"))
            })
        }

        if (findEdge) {
            return getTargetNodeAndPort(findEdge[0], graph)
        } else {
            return null
        }
    }
}
export const getSourceNodeAndPort = (edge: Edge, graph: Graph): { sourceNode: Node, sourcePortId: string } | null => {

    const node = edge.getSourceNode()!
    if (node.shape !== CustomShape.GROUP_PROCESS) {
        return { sourceNode: node, sourcePortId: edge.getSourcePortId()! }
    } else {
        const portIdOutter = edge.getSourcePortId();
        let findEdge: Edge<Edge.Properties>[] | undefined;
        if (!portIdOutter?.includes("_in")) {
            //从外面连线
            findEdge = graph.getIncomingEdges(node)?.filter(edge => {
                return edge.getTargetPortId() === `${portIdOutter}_in`
            })
        } else {
            //从里面连线
            findEdge = graph.getIncomingEdges(node)?.filter(edge => {
                return edge.getTargetPortId() === portIdOutter.substring(0, portIdOutter.lastIndexOf("_in"))
            })
        }

        if (findEdge) {
            return getSourceNodeAndPort(findEdge[0], graph)
        } else {
            return null
        }
    }
}
//获取源column或config
export const getSourceColOrCon = (sourceNode: Node, sourcePortId: string, targetNode: Node, targetPortId: string, data: string[]) => {
    let columns = []
    const id = getId(sourceNode, sourcePortId, targetNode, targetPortId)
    let dataSource: DataSourceType[];
    if (sourceNode.shape === CustomShape.DUPLICATE_OPERATOR) {
        
        //修改（如果是DuplicateOperator则需要从输入节点的config里读取 ）
        columns = sourceNode?.getData()["config"][0][id]

    } else {
        columns = sourceNode?.getData()?.parameters.output.columns;
    }
    dataSource = columns.map((item: any, index: number) => {
        return {
            ...item,
            name: data[index]
        }
    })
    dataSource = dataSource && dataSource.map((item, index) => ({
        ...item,
        id: (Date.now() + index).toString()
    }))
    return dataSource
}
export const getTargetConfig = (sourceNode: Node, sourcePortId: string, targetNode: Node, targetPortId: string) => {
    const id = getId(sourceNode, sourcePortId, targetNode, targetPortId)
    let dataTarget: DataSourceType[];
    if (targetNode.getData() !== undefined) {
        if (targetNode.getData().hasOwnProperty("config")) {
            if (targetNode.getData()["config"].length) {
                dataTarget = targetNode?.getData()["config"][0][id]
                return dataTarget && dataTarget.map((item, index) => ({
                    ...item,
                    id: (Date.now() + index).toString()
                }))
            } else {
                return []
            }
        } else {
            return []
        }
    } else {
        return []
    }


}
//返回config 中id
export const getId = (sourceCell: Cell, sourcePortId: string, targetCell: Cell, targetPortId: string) => {
    return `${sourceCell.id}&${sourcePortId} ${targetCell.id}&${targetPortId}`

}
//
export const transDataToColumn = (data: any[]) => {
    return cloneDeep(data).map(item => {
        delete item.id
        return { ...item }
    })
}


export const getNewConfig = (cell: Cell, config: any) => {
    if (!cell.getData() || !cell.getData()["config"][0]) {
        return cloneDeep(config)
    }
    let oldConfig = cloneDeep(cell.getData()["config"][0])
    for (let newKey in config) {
        oldConfig[newKey] = config[newKey]
    }
    return oldConfig
}

export const strMapToObj = (strMap: Map<string, sourceConfigType[]>) => {
    let obj = {};
    for (let [key, value] of strMap) {
        obj[key] = value
    }
    return obj
}
//将configdata设置给节点config
export const setConfigToNode = (node?: Node | null, config?: any, cell?: Cell) => {
    if (node) {
        node.setData({
            ...(node.getData() ? node.getData() : {}),
            config: [config]
        }, { overwrite: true });
    }
    if (cell) {
        cell.setData({
            ...(cell.getData() ? cell.getData() : {}),
            config: [config]
        }, { overwrite: true });
    }
}

//设置源column or config
export const setSourceColumnOrConfig = (sourceNode: Node, sourcePortId: string, targetNode: Node, targetPortId: string, data: DataSourceType[]) => {
    //修改源 columns
    const columns = transDataToColumn(data)
    //修改目标 config
    // const config = columns.filter(item => item.flag)
    const id = getId(sourceNode, sourcePortId, targetNode, targetPortId)
    if (sourceNode.shape === CustomShape.DUPLICATE_OPERATOR) {
        
        //修改源 config
        let newConfigObj = {}
        // for (let key in sourceNode.getData()["config"][0]) {
        //     newConfigObj[key] = cloneDeep(columns)
        // }
        for (let key in sourceNode.getData()["config"][0]) {
            if (key !== id) {
                newConfigObj[key] = cloneDeep(sourceNode.getData()["config"][0][key])
            } else {
                newConfigObj[key] = cloneDeep(columns)
            }
        }
        setConfigToNode(sourceNode, newConfigObj)

        // 同步下个cell config
    } else {
        // 同步自身节点mapcolumns
        sourceNode.getData().parameters.output.columns = columns
        let configMap: Map<string, sourceConfigType[]> = new Map();
        //转换为config设置到目标节点的node-data里
        configMap.set(id, columns);
        let targetConfig = strMapToObj(configMap)
        let newConfigObj = getNewConfig(targetNode, targetConfig)
        setConfigToNode(targetNode, newConfigObj)
    }

    if (targetNode.shape === CustomShape.DUPLICATE_OPERATOR) {
        
        let newConfigObj: any = {};
        //将下个节点的所有连接装都重置(不影响前节点config保持input-output config不变)
        for (let key in targetNode.getData()["config"][0]) {
            newConfigObj[key] = cloneDeep(columns)
        }
        setConfigToNode(targetNode, newConfigObj)

    } else {
        let configMap: Map<string, sourceConfigType[]> = new Map();
        //转换为config设置到目标节点的node-data里
        configMap.set(id, columns);
        let targetConfig = strMapToObj(configMap)
        let newConfigObj = getNewConfig(targetNode, targetConfig)
        setConfigToNode(targetNode, newConfigObj)
    }

    return columns
}


export const setTargetConfig = (sourceNode: Node, sourcePortId: string, targetNode: Node, targetPortId: string, data: DataSourceType[]) => {
    const config = transDataToColumn(data)
    //修改目标 config
    // const config = columns.filter(item => item.flag)
    const id = getId(sourceNode, sourcePortId, targetNode, targetPortId)
    if (targetNode.shape === CustomShape.DUPLICATE_OPERATOR) {
        
        let newConfigObj: any = {};
        //将下个节点的所有连接装都重置(不影响前节点config保持input-output config不变)
        for (let key in targetNode.getData()["config"][0]) {
            if (key !== id) {
                newConfigObj[key] = cloneDeep(targetNode.getData()["config"][0][key])
            } else {
                newConfigObj[key] =cloneDeep(config)
            }
        }
        setConfigToNode(targetNode, newConfigObj)

    } else {
        let configMap: Map<string, sourceConfigType[]> = new Map();
        //转换为config设置到目标节点的node-data里
        configMap.set(id, config);
        let targetConfig = strMapToObj(configMap)
        let newConfigObj = getNewConfig(targetNode, targetConfig)
        setConfigToNode(targetNode, newConfigObj)
    }
}