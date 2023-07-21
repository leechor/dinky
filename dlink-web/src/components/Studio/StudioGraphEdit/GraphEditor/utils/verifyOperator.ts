import { Graph, Node, Cell } from '@antv/x6';
interface VerifyOperatorItem {
    color: string,
    edge: null | string[],
    operatorErrorMsg: null | string[],
    operatorId: string,
    portInformation: PortInformation | null,
    sqlErrorMsg: string | null,
    tableName: string
}
interface PortInformation {
    [propName: string]: string[]
}
export default (verifyDatas: VerifyOperatorItem[], graph: Graph) => {
    console.log(verifyDatas);
    //检验节点错误信息
    verifyDatas.forEach(({ edge, operatorErrorMsg, operatorId, portInformation, sqlErrorMsg }) => {
        let color = "#c45d5d"
        const cell = graph.getCellById(operatorId)
        cell.prop("isErro",true)
        if (sqlErrorMsg) {
            //改变节点边框颜色
            cell.attr({
                body: {
                    style: {
                        'background-color': `${color}`,
                        border: '1px solid #949494',
                        "border-radius": "2px"
                    },
                },
            }, { overwrite: true })
        }
        if (portInformation) {
            //设置连接桩错误颜色
            Object.keys(portInformation).forEach(portInfo => {
                (cell as Node).setPortProp(portInfo, 'attrs/path', { fill: color, visibility: 'visibility', })
            })
        }
        if (edge) {
            //设置边错误颜色
            edge.forEach(e => {
                const edgeFind = graph.getConnectedEdges(cell).find(edge => edge.id === e)
                if (edgeFind) {
                    edgeFind.setAttrByPath("line", {
                        stroke: color
                    })
                }
            })
        }

    })
}