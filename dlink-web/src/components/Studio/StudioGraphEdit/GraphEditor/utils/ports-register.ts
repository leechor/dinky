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
        attrs: {
          circle: {
            r: 8,
            magnet: true,
            stroke: '#818181',
            strokeWidth: 1,
            fill: '#b2a2e9',
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      inputs: {
        zIndex:999,
        position: 'left',
        attrs: {
          circle: {
            r: 8,
            magnet: true,
            stroke: '#818181',
            strokeWidth: 1,
            fill: '#915dac',
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
    },
  };
};
