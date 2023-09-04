import { Graph, KeyValue, Shape } from '@antv/x6';
import { Clipboard } from '@antv/x6-plugin-clipboard';
import { Selection } from '@antv/x6-plugin-selection';
import { Keyboard } from '@antv/x6-plugin-keyboard';
import { Snapline } from '@antv/x6-plugin-snapline';
import { History } from '@antv/x6-plugin-history';
import { Scroller } from '@antv/x6-plugin-scroller';
import { Transform } from '@antv/x6-plugin-transform';
import { Export } from '@antv/x6-plugin-export';
import CustomShape from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons';

//复制粘贴
export const clipboard = (graph: Graph) => {
  graph.use(
    new Selection({
      enabled: true,
      multiple: true,
      rubberband: true,
      movable: true,
      showNodeSelectionBox: true,
      pointerEvents: 'none',
      showEdgeSelectionBox: false,
    }),
  );

  graph.use(
    new Clipboard({
      enabled: true,
    }),
  );
};

//对齐
export const snapLine = (graph: Graph) => {
  graph.use(
    new Snapline({
      enabled: true,
    }),
  );
};

export const keyboard = (graph: Graph) => {
  graph.use(
    new Keyboard({
      enabled: true,
      global: true,
    }),
  );

  graph.bindKey(['meta+c', 'ctrl+c'], (e) => {
    const cells = graph.getSelectedCells();
    if (cells.length) {
      graph.copy(cells);
    }
    return false;
  });

  graph.bindKey(['meta+x', 'ctrl+x'], (e) => {
    const cells = graph.getSelectedCells();
    if (cells.length) {
      graph.cut(cells);
    }
    return false;
  });

  graph.bindKey(['meta+v', 'ctrl+v'], (e) => {
    if (!graph.isClipboardEmpty()) {
      const cells = graph.paste({ offset: 32 });
      graph.cleanSelection();
      graph.select(cells);
    }
    return false;
  });

  //delete
  graph.bindKey(['backspace', 'delete'], () => {
    const cells = graph.getSelectedCells();
    if (cells.length) {
      graph.removeCells(cells);
    }
  });
};

export const history = (graph: Graph) => {
  const history = new History({
    enabled: true,
    ignoreChange: true,
    beforeAddCommand(event, args: any) {
      if (args.options) {
        return args.options.ignored !== true;
      }
    },
    // afterAddCommand(event,args,cmd){
    //   console.log("afterAddCommand",event,args,cmd);

    // },
    // executeCommand(cmd,revert,options){
    //   console.log("executeCommand",cmd,revert,options);

    // }
  });
  graph.use(history);
  // history.on("batch", ({ cmd, options }: { cmd: any, options: any }) => {
  //   switch (cmd.event) {
  //     case "cell:change:ports":
  //       const cell = graph.getCellById(cmd.data.id)
  //       if (cell.isNode()) {
  //         const nextPortId = cmd.data.next.ports.items.map((item: any) => item.id)
  //         const prePortId = cmd.data.next.ports.items.map((item: any) => item.id)
  //         const changedPortId = nextPortId.find((nId: string) => !prePortId.includes(nId))
  //         cell.removePort(changedPortId)
  //       }

  //       break;
  //   }

  // })
  //undo redo
  graph.bindKey(['meta+z', 'ctrl+z'], () => {
    if (graph.canUndo()) {
      graph.undo();
    }
    return false;
  });

  graph.bindKey(['meta+y', 'ctrl+y'], () => {
    graph.redo();
    return false;
  });
};
export const scroller = (graph: Graph) => {
  graph.use(
    new Scroller({
      pannable: true,
      modifiers: ['ctrl', 'alt'],
      pageBreak: true,
      pageVisible: true,
      pageHeight: graph.getGraphArea().height,
      pageWidth: graph.getGraphArea().width,
    }),
  );
  // graph.centerContent();
  graph.lockScroller();
};

export const transform = (graph: Graph) => {
  graph.use(
    new Transform({
      resizing: {
        enabled(node) {
          if (node.shape === CustomShape.GROUP_PROCESS) {
            return false;
          } else {
            return true;
          }
        },
        orthogonal: false,
      },
    }),
  );
  // graph.centerContent();
};

export const exportGraph = (graph: Graph) => {
  graph.use(new Export());
};

export default function loadPlugin(graph: Graph) {
  exportGraph(graph);
  clipboard(graph);
  snapLine(graph);
  keyboard(graph);
  history(graph);
  transform(graph);
  scroller(graph);
}
