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
            d: "m0,0,a5,5.5 0 0 1 12,0",
            fill: "#b2a2e9",
            transform: "rotate(90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          circle: {
            r: 8,
            magnet: true,
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      inputs: {
        zIndex:999,
        position: 'left',
        markup: {
          tagName: "path",
          selector: "path",
          attrs: {
            d: "m0,0,a5,5.5 0 0 1 12,0",
            fill: "#b2a2e9",
            transform: "rotate(-90)",
            strokeWidth: 1,
            stroke: "null",
          }
        },
        attrs: {
          circle: {
            r: 8,
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
