import { Cell, Graph } from "@antv/x6";

export type DispatchMenuInfo = React.Dispatch<
  React.SetStateAction<{
    show: boolean;
    top: number;
    left: number;
    cell:Cell|null
  }>
>;

export function initMenu(graph: Graph, isShowMenuInfo: DispatchMenuInfo) {
  //右键菜单点击node时
  graph.on("node:contextmenu", ({ cell, e }) => {
    debugger
    const p = graph.clientToGraph(e.clientX, e.clientY);
    isShowMenuInfo({
      show: true,
      top: p.y,
      left: p.x,
      cell,
    });
  });

  //画图区域右键
  graph.on("blank:contextmenu", ({ e }) => {
    const p = graph.clientToGraph(e.clientX, e.clientY);
    isShowMenuInfo({
      show: true,
      top: p.y,
      left: p.x,
      cell:null,
    });
  });

  graph.on("blank:click", () => {
    isShowMenuInfo({
      show: false,
      top: 0,
      left: 0,
      cell:null
    });
  });
}
