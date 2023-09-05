import { Stencil } from '@antv/x6-plugin-stencil';
import { Graph, Node } from '@antv/x6';
import { Parameter } from '@/components/Studio/StudioGraphEdit/GraphEditor/ts-define/parameter';
import CustomShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons';

/**
 * //注册stencil中的组件
 * @description 加载左侧自定义图形（用来拖拽）
 * @param graph
 * @param stencil
 * @param operatorParameters
 */

export const stencilComponentsLoader = (
  graph: Graph,
  stencil: Stencil,
  operatorParameters: Parameter[],
) => {
  const registeredStenCpn: { cpn: Node<Node.Properties>; cpnName: string }[] = [];
  const groupsName: { [key: string]: string[] } = {};
  let node: Node;
  //根据算子参数注册stencil组件
  operatorParameters?.forEach((param: Parameter) => {
    node = graph.createNode({
      name: param.name,
      shape: param.code,
      width: 70,
      height: 50,
      attrs: {
        body: {
          rx: 7,
          ry: 6,
        },
        text: {
          text: param.name,
          fontSize: 2,
        },
      },
    });
    node.prop('isStencil', true);

    registeredStenCpn.push({ cpn: node, cpnName: param.code });
    //保存组和节点关系
    const groupParamName = param.group.split('.')[0];
    if (!(groupParamName in groupsName)) {
      groupsName[groupParamName] = [];
    }
    groupsName[groupParamName].push(param.code);
  });

  //文本节点
  const textAreaNode = graph.createNode({
    shape: CustomShape.TEXT_NODE,
  });
  stencil.load([textAreaNode], 'textArea');

  //算子节点
  Object.entries(groupsName).forEach(([group, groupNames]) => {
    // 每个分组需要加载的组件
    const groupRegisteredCpn: Node<Node.Properties>[] = registeredStenCpn
      .filter(({ cpnName }) => groupNames.includes(cpnName))
      .map(({ cpn }) => cpn);

    stencil.load(groupRegisteredCpn, group);
  });
};
