import { cloneDeep } from 'lodash';
import { Graph, Node } from '@antv/x6';
import { register } from '@antv/x6-react-shape';
import TextNode from '@/components/Studio/StudioGraphEdit/GraphEditor/components/text-node';
import OperatorNode from '@/components/Studio/StudioGraphEdit/GraphEditor/components/operator-node';
import { Parameter } from '@/components/Studio/StudioGraphEdit/GraphEditor/ts-define/parameter';
import { PortManager } from '@antv/x6/es/model/port';
import React from 'react';
import { GroupNode } from '@/components/Studio/StudioGraphEdit/GraphEditor/components/group-node';
import unRegisterShape from "./shape-unregister"
import CustomShape from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons";
import GroupProcess from '@/components/Studio/StudioGraphEdit/GraphEditor/components/group-process';


function registerTextNode() {
  //注册文本节点
  register({
    shape: CustomShape.TEXT_NODE,
    component: TextNode,
    width: 180,
    height: 180,
    effect: ['data'],
    attrs: {
      text: {
        text: CustomShape.TEXT_NODE,
      },
    },
    zIndex: 20,
  });
}

function registerGroupProgress(ports: Partial<PortManager.Metadata> | PortManager.PortMetadata[],) {
  register({
    width: 80,
    height: 50,
    attrs: {
      body: {
        style: {
          'background-color': 'none',
          border: 'none',
        },
      },
    },
    shape: CustomShape.GROUP_PROCESS,
    component: GroupProcess,
    ports: {
      ...ports, items: [
        {
          group: 'inputs',
          zIndex: 999,
          attrs: {
            text: {
              text: `input_0`,
              style: {
                visibility: "hidden",
                fontSize: 10,
                fill: "#3B4351",
              },
            },
            path: {
              d: "m-6,2,a5,5.5 0 0 1 12,0"
            }
          },
          label: {
            position: "bottom",
          },
          id: "input_0"
        }, {
          group: 'outputs',
          zIndex: 999,
          id: "output_0",
          attrs: {
            text: {
              text: `output_0`,
              style: {
                visibility: "hidden",
                fontSize: 10,
                fill: "#3B4351",
              },
            },
            path: {
              d: "m-6,2,a5,5.5 0 0 1 12,0",
            }
          },
          label: {
            position: "bottom",
          }
        }
      ]
    },
  })
}

function registerOperatorNode(
  code: string,
  ports: Partial<PortManager.Metadata> | PortManager.PortMetadata[],
  registerCpn: React.ComponentType<{
    node: Node;
    graph: Graph;
  }>,
  portItem: PortManager.PortMetadata[],
) {

  register({
    width: 80,
    height: 50,
    attrs: {
      body: {
        style: {
          'background-color': '#c6e5ff',
          border: '1px solid #949494',
          "border-radius": "2px"
        },
      },
    },
    shape: code,
    component: registerCpn,
    ports: { ...ports, items: portItem },
  });
}

export default (
  graph: Graph,
  ports: Partial<PortManager.Metadata> | PortManager.PortMetadata[],
  operatorParameters: Parameter[],
) => {
  //取消已注册，重新注册
  unRegisterShape(operatorParameters)
  operatorParameters?.forEach((param) => {
    //生成ports Item
    const portItem: PortManager.PortMetadata[] = [];

    portItem.push(
      ...param.ports.inputs.map((item: { id: string }) => ({
        group: 'inputs',
        id: item.id,
        zIndex: 999,
        attrs: {
          text: {
            text: `${item.id}`,
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
      })),
    );

    portItem.push(
      ...param.ports.outputs.map((item: { id: string }) => ({
        group: 'outputs',
        zIndex: 999,
        id: item.id,
        attrs: {
          text: {
            text: `${item.id}`,
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
      })),
    );

    //保存组和节点关系
    registerOperatorNode(param.code, ports, OperatorNode(param.icon, param.name), portItem);

  });
  registerTextNode();
  registerGroupProgress(ports)
  Graph.registerNode('package', GroupNode);



};
