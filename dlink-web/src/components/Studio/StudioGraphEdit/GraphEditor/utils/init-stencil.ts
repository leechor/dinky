import { Stencil } from '@antv/x6-plugin-stencil';
import { Cell, Graph } from '@antv/x6';
import { Parameter } from '@/components/Studio/StudioGraphEdit/GraphEditor/ts-define/parameter';
import CustomShape from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons";
import { getCustomGroupInfo } from '@/components/Common/crud';


export const initStencil = (
  graph: Graph,
  stencilRef: HTMLElement,
  operatorParameters: Parameter[],
) => {

  const groupsName: { [key: string]: string[] } = {};
  //根据算子节点参数整理需注册的stencil组
  operatorParameters?.forEach((param) => {
    //保存组和节点关系
    const groupParamName = param.group.split('.')[0];
    if (!(groupParamName in groupsName)) {
      groupsName[groupParamName] = [];
    }
    groupsName[groupParamName].push(param.name);
  });

  const groups = Object.keys(groupsName).map((name) => {
    return {
      name,
      title: name,
      collapsable: true,
      collapsed: true,
    };
  });

  const otherGroups = [
    {
      name: 'groupNode',
      title: '分组',
      collapsable: true,
      collapsed: true,
      layoutOptions: {
        resizeToFit: false,
        columns: 1,
        dx: 50,
      },
    },
    {
      name: 'textArea',
      title: '文本',
      collapsable: true,
      collapsed: true,
      layoutOptions: {
        resizeToFit: true,
      },
    },
  ];

  const stencil = new Stencil({
    title: '组件信息',
    target: graph,
    search(cell, keyword) {
      if (cell.shape === CustomShape.GROUP_PROCESS) {
        return cell.prop().name.indexOf(keyword) !== -1;
      } else {
        return cell.shape.indexOf(keyword) !== -1;
      }
    },
    getDropNode(node) {
      if (node.shape === CustomShape.TEXT_NODE) {
        const { width, height } = node.size();
        // 返回一个新的节点作为实际放置到画布上的节点
        return node.clone().size(width * 3, height * 3);
      } else {
        node.prop("isStencil", false)
        return node.clone();
      }
    },
    placeholder: '查找',
    notFoundText: 'Not Found',
    collapsable: true,
    stencilGraphWidth: 180,
    layoutOptions: {
      columns: 2,
      resizeToFit: false,
    },
    groups: [...groups, ...otherGroups],
  });
  stencilRef.appendChild(stencil.container);
  return stencil;
};
