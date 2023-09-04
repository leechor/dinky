import { Cell, Graph } from '@antv/x6';
import { DispatchMenuInfo } from '../types';



export function initMenu(
  graph: Graph,
  isShowMenuInfo: DispatchMenuInfo,
  changeNode: (node: Cell) => void,
  changePositon: (x: number, y: number) => void,
) {
  //右键菜单点击node时
  graph.on('node:contextmenu', ({ cell, e }) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
    changePositon(p.x, p.y);
    changeNode(cell);
    isShowMenuInfo({
      show: true,
      top: p.y,
      left: p.x,
      node: cell,
    });
  });

  //画图区域右键
  graph.on('blank:contextmenu', ({ e }) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
    isShowMenuInfo({
      show: true,
      top: p.y,
      left: p.x,
      node: null,
    });
  });

  graph.on('blank:click', () => {
    isShowMenuInfo({
      show: false,
      top: 0,
      left: 0,
      node: null,
    });
  });
}
