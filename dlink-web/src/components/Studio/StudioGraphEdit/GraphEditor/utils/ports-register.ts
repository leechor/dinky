/**
 *  //初始化连接桩
 * @returns {ports} 连接桩默认类型
 */

export const handleInitPort = () => {
  // 设置连接桩
  return {
    groups: {
      outputs: {
        zIndex: 999,
        position: 'right',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m-6,0,a5,5.5 0 0 1 12,0",
            fill: "#b2a2e9",
            transform: "rotate(90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          path: {
            r: 8,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      inputs: {
        zIndex: 999,
        position: 'left',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m-6,0,a5,5.5 0 0 1 12,0",
            fill: "#b2a2e9",
            transform: "rotate(-90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          path: {
            r: 10,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
    },
  };
};

export const handleInitGroupPort = () => {
  return {
    groups: {
      outputs: {
        zIndex: 999,
        position: 'right',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m-6,0,a5,5.5 0 0 1 12,0",
            fill: "#b2a2e9",
            transform: "rotate(90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          path: {
            r: 8,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      inputs: {
        zIndex: 999,
        position: 'left',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m-6,0,a5,5.5 0 0 1 12,0",
            fill: "#b2a2e9",
            transform: "rotate(-90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          path: {
            r: 10,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      },

      innerOutputs: {
        zIndex: 999,
        position: 'left',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m-6,0,a5,5.5 0 0 1 12,0",
            fill: "#2ac0c4",
            transform: "rotate(90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          path: {
            r: 8,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      innerInputs: {
        zIndex: 999,
        position: 'right',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m-6,0,a5,5.5 0 0 1 12,0",
            fill:  "#2ac0c4",
            transform: "rotate(-90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          path: {
            r: 10,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      }
    },
  };
}
