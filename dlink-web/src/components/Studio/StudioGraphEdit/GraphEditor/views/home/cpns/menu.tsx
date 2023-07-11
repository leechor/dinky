import { Menu } from '@antv/x6-react-components';
import { FC, memo, useState } from 'react';
import { message, Radio, RadioChangeEvent, Space, Upload } from 'antd';
import type { UploadProps } from 'antd/es/upload/interface';
import { DataUri, Graph, Node, Cell, Edge } from '@antv/x6';
import '@antv/x6-react-components/es/menu/style/index.css';
import {
  CopyOutlined,
  SnippetsOutlined,
  RedoOutlined,
  UndoOutlined,
  ScissorOutlined,
  DeleteOutlined,
  ExportOutlined,
  EditOutlined,
  RadiusSettingOutlined,
  GroupOutlined,
  AlignCenterOutlined,
  BgColorsOutlined,
  UploadOutlined
} from '@ant-design/icons';
import CustomShape from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons";
import { useAppSelector, useAppDispatch } from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { formatDate } from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/math"
import { changeUnselectedCells } from "@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home"
import { edge } from '@antv/g2plot/lib/adaptor/geometries';

type MenuPropsType = {
  top: number;
  left: number;
  graph: Graph;
  node: Node | null,
  show: boolean,
  handleShowMenu: (value: boolean) => void
};

enum HorizontalAlignState {
  LEFT = 1,
  CENTER,
  RIGHT
}
type OuterEdgeType = "input" | "output"


enum VerticalAlignState {
  TOP = 1,
  CENTER,
  BOTTOM
}

enum NoteTextColor {
  YELLOW = 1,
  ORANGE,
  RED,
  PURPLE,
  GREEN,
  BLUE,
  GRAY,
  TRANSPARENT,
}

const NoteTextColorValue: { [key in NoteTextColor]: string } = {
  [NoteTextColor.YELLOW]: "#fcf987",
  [NoteTextColor.RED]: "#ffe9dc",
  [NoteTextColor.ORANGE]: "#fffad2",
  [NoteTextColor.PURPLE]: "#f6deed",
  [NoteTextColor.GREEN]: "#ddeed2",
  [NoteTextColor.BLUE]: "#e3dff1",
  [NoteTextColor.GRAY]: "#d1d1d1",
  [NoteTextColor.TRANSPARENT]: "transparent",
}

const DuplicateOperatorMenu = () => {
  return <Menu.Item icon={<EditOutlined />} name="add-port" text="添加输出桩" />
}

export const CustomMenu: FC<MenuPropsType> = memo(({ top = 0, left = 0, graph, node, handleShowMenu, show }) => {

  const { taskName, unSelectedCellIds, position } = useAppSelector((state) => ({
    taskName: state.home.taskName,
    unSelectedCellIds: state.home.unSelectedCellIds,
    position: state.home.position
  }));

  const dispatch = useAppDispatch();
  const convertHorizontalAlign = (align: string) => {
    switch (align) {
      case 'left':
        return HorizontalAlignState.LEFT
      case 'center':
        return HorizontalAlignState.CENTER
      case 'right':
        return HorizontalAlignState.RIGHT
      default:
        return HorizontalAlignState.LEFT
    }
  }

  const convertVerticalAlign = (align: string) => {
    switch (align) {
      case 'top':
        return VerticalAlignState.TOP
      case 'center':
        return VerticalAlignState.CENTER
      case 'bottom':
        return VerticalAlignState.BOTTOM
      default:
        return VerticalAlignState.TOP
    }
  }

  const { Item: MenuItem, SubMenu, Divider } = Menu;
  const [messageApi, contextHolder] = message.useMessage();
  const [isDisablePaste, setIsDisablePaste] = useState(true);
  const [horizontalAlign, setHorizontalAlign] =
    useState(convertHorizontalAlign(node?.getData()?.horizontalAlign ?? 'left'));
  const [verticalAlign, setVerticalAlign] =
    useState(convertVerticalAlign(node?.getData()?.verticalAlign ?? 'top'));
  const [noteTextColor, setNoteTextColor] = useState(node?.getData()?.backgroundColor) ?? 'yellow';
  const copy = () => {
    const cells = graph.getSelectedCells();
    if (cells.length) {
      graph.copy(cells);
    }
  };

  const cut = () => {
    const cells = graph.getSelectedCells();
    if (cells.length) {
      graph.cut(cells);
    }
  };

  const paste = () => {
    if (!graph.isClipboardEmpty()) {
      const cells = graph.paste({ offset: 32 });
      graph.cleanSelection();
      graph.select(cells);
    }
  };

  const front = () => {

    const cells = graph.getSelectedCells();
    cells?.forEach(c => c.toFront())
  };

  const back = () => {
    const cells = graph.getSelectedCells();
    cells?.forEach(c => c.toBack())
  };

  const forward = () => {
    const cells = graph.getSelectedCells();
    cells?.forEach(c => { c.setZIndex((c.getZIndex() ?? 0) + 1) });
  };

  const backward = () => {
    const cells = graph.getSelectedCells();
    cells?.forEach(c => { c.setZIndex((c.getZIndex() ?? 0) - 1) });
  };

  const horizontalAlignHandler = (e: RadioChangeEvent) => {
    const horizontalAlign = e.target.value as number;
    const align = ((value: number) => {
      switch (value) {
        case HorizontalAlignState.LEFT:
          return 'left'
        case HorizontalAlignState.CENTER:
          return 'center'
        case HorizontalAlignState.RIGHT:
          return 'right'
        default:
          return 'left'
      }
    })(horizontalAlign);
    graph.getSelectedCells()?.filter(c => c.shape == CustomShape.TEXT_NODE)
      .forEach(c => c.setData({ ...c.getData(), 'horizontalAlign': align }))
    setHorizontalAlign(horizontalAlign)
  }

  const verticalAlignHandler = (e: RadioChangeEvent) => {
    const verticalAlign = e.target.value as number;
    const align = ((value: number) => {
      switch (value) {
        case VerticalAlignState.TOP:
          return 'top'
        case VerticalAlignState.CENTER:
          return 'center'
        case VerticalAlignState.BOTTOM:
          return 'bottom'
        default:
          return 'top'
      }
    })(verticalAlign);
    const cc = graph.getSelectedCells()?.filter(c => c.shape == CustomShape.TEXT_NODE)
    cc.forEach(c => c.setData({ ...c.getData(), 'verticalAlign': align }))
    setVerticalAlign(verticalAlign)
  }

  const noteTextColorHandler = (e: RadioChangeEvent) => {
    const noteTextColor = e.target.value as number;
    const color = noteTextColor ? NoteTextColorValue[noteTextColor] : NoteTextColorValue[NoteTextColor.TRANSPARENT]
    const cc = graph.getSelectedCells()?.filter(c => c.shape == CustomShape.TEXT_NODE)
    cc.forEach(c => c.setData({ ...c.getData(), 'backgroundColor': color }))
    setNoteTextColor(noteTextColor)
  }


  const onMenuClick = (name: string) => {
    messageApi.info(`clicked ${name}`);
    handleShowMenu(false)

    switch (name) {
      case 'undo':
        graph.undo();
        break;
      case 'redo':
        graph.redo();
        break;
      case 'delete':
        graph.clearCells();
        break;
      case 'save-PNG':
        graph.toPNG(
          (dataUri: string) => {
            DataUri.downloadDataUri(dataUri, 'chartx.png');
          },
          {
            backgroundColor: 'white',
            padding: {
              top: 20,
              right: 30,
              bottom: 40,
              left: 50,
            },
            quality: 1,
          },
        );
        break;
      case 'save-SVG':
        graph.toSVG((dataUri: string) => {
          // 下载
          DataUri.downloadDataUri(DataUri.svgToDataUrl(dataUri), 'chart.svg');
        });
        break;
      case 'save-JPEG':
        graph.toJPEG((dataUri: string) => {
          // 下载
          DataUri.downloadDataUri(dataUri, 'chart.jpeg');
        });
        break;
      case 'print':
        // graph.printPreview();
        break;
      case 'copy':
        copy();
        setIsDisablePaste(false);
        break;
      case 'cut':
        cut();
        setIsDisablePaste(false);
        break;
      case 'paste':
        paste();
        setIsDisablePaste(true);
        break;
      case 'save-JSON':
        let data = JSON.stringify(graph.toJSON(), null, 4);
        const blob = new Blob([data], { type: 'text/json' }),
          e = new MouseEvent('click'),
          a = document.createElement('a');
        a.download = `${taskName}_${formatDate(Date.now())}.json`;
        a.href = window.URL.createObjectURL(blob);
        a.dataset.downloadurl = ['text/json', a.download, a.href].join(':');
        a.dispatchEvent(e);

        // graph.fromJSON({cells:[graph.toJSON().cells[0],graph.toJSON().cells[1]]})
        break;
      case 'add-port':
        break;
      case 'front':
        front();
        break;
      case 'back':
        back();
        break;
      case 'forward':
        forward();
        break;
      case 'backward':
        backward();
        break;
      case "createProcess":
        createProcess();
        break;
      default:
      case "import":
        uploadFileClick()
        break;
    }
  };

  const createProcess = () => {
    //获取选中包围盒的位置信息
    const selectedBox = document.querySelector(".x6-widget-selection-inner")
    const rect = selectedBox?.getBoundingClientRect()!;

    const nodes = graph.getSelectedCells()
      .filter(item => item.isNode())
      .map(item => item as Node);

    if (nodes.length === 0) return

    const node = graph.createNode({
      shape: CustomShape.GROUP_PROCESS,
      width: 70,
      height: 50,
      attrs: {
        body: {
          rx: 7,
          ry: 6,
        },
        text: {
          text: " param.name",
          fontSize: 2,
        },
      },
    });

    const group = graph.addNode(node);
    group.setPosition(graph.clientToLocal(rect.x + (rect.width - group.size().width) / 2,
      rect.y + (rect.height - group.size().height) / 2))
    group.setChildren(nodes)
    graph.centerCell(group)

    nodes?.flatMap(item => {
      item.prop("previousPosition", item.position())
      item.hide()
      return graph.getConnectedEdges(item)
    }).filter((edge) => nodes.includes(edge.getSourceNode()!) && nodes.includes(edge.getTargetNode()!))
      .forEach(item => {
        item.hide()
        group.addChild(item)
      })

    //将隐藏的节点设置为不可选
    graph.setSelectionFilter((cell) => {
      return ! nodes?.map(c => c.id)!.includes(cell.id)
    })

    const selectedIncomingEdge: (Edge | null)[] = nodes
      .flatMap(item => graph.model.getIncomingEdges(item))
      .filter(item => item?.getSourceNode() && !nodes.includes(item.getSourceNode()!))

    //获取输出所有边
    const selectedOutgoingEdge: (Edge | null)[] = nodes
      .flatMap(item => graph.model.getOutgoingEdges(item))
      .filter(item => item?.getTargetNode() && !nodes.includes(item.getTargetNode()!))

    addOuterPortAndEdge(selectedIncomingEdge, group, "input")
    addOuterPortAndEdge(selectedOutgoingEdge, group, "output")

    //根据组节点外部的输入和输出添加放大后的输入输出桩
    const out_inputPortIds = node?.getPortsByGroup("inputs").map(data => data.id);
    const out_outputPortIds = node?.getPortsByGroup("outputs").map(data => data.id);


    // const selectAbleIds:string:[] = unSelectedNodes.map(node => { id: node.id })
    // graph.setSelectionFilter(selectAbleIds)
    // 将该组节点子节点添加进不可选中数组中
    // dispatch(changeUnselectedCells({ type: "push", data: { groupId: node.id, childrenId } }))
  }

  const addOuterPortAndEdge = (outEdge: (Edge | null)[], groupNode: Node, type: OuterEdgeType) => {
    //将外部节点与组节点连线

    if (!outEdge.length && !outEdge.length) {
      //情况1：所选组无连线，默认一个连接装并且无连线
      return
    }

    //添加外部输入桩
    if (outEdge.length > 1) {

      //根据连线长度，添加n-1个输入桩
      for (let i = 1; i < outEdge.length; i++) {
        //添加连接桩
        groupNode.addPort({
          id: `${type}_${i}`, group: type + "s",
          attrs: {
            text: {
              text: `${type}_${i}`,
              style: {
                visibility: "hidden",
                fontSize: 10,
                fill: "#3B4351",
              },
            },
            path: {
              d: type == "input" ? "m-6,2,a5,5.5 0 0 1 12,0" : "m-6,2,a5,5.5 0 0 1 12,0",
            }
          },
          label: {
            position: "bottom",
          }
        })
      }
    }

    //添加外部输入连线
    if (outEdge.length !== 0) {
      outEdge.forEach((edge, index) => {
        if (type === "input") {
          const sourceCell = edge!.getSourceCell();
          const sourcePortId = edge!.getSourcePortId();
          graph.addEdge({
            source: { cell: sourceCell!, port: sourcePortId },
            target: { cell: groupNode!, port: `input_${index}` },
          })
        } else {
          const targetCell = edge!.getTargetCell();
          const targetPortId = edge!.getTargetPortId();
          graph.addEdge({
            source: { cell: groupNode!, port: `${type}_${index}` },
            target: { cell: targetCell!, port: targetPortId },
          })
        }
      })
    }
  }
  const uploadFileClick = () => {
    const fileInput = document.createElement("input")
    fileInput.type = "file"
    fileInput.accept = ".json"
    fileInput.style.display = "none"
    fileInput.addEventListener("change", handleFileChange);
    fileInput.click()
  }

  const handleFileChange = (event: any) => {
    const selectFiles = event.target.files;
    if (selectFiles.length === 1) {
      const selectFile = selectFiles[0];
      if (selectFile && selectFile.type == "application/json") {
        const reader = new FileReader()
        reader.onload = (event: any) => {
          const fileContent = event.target.result;
          const jsonData = JSON.parse(fileContent);
          try {
            graph.fromJSON(jsonData)
          } catch (error) {
            message.error("导入数据失败！")
          }
        }
        reader.readAsText(selectFile)

      } else {
        message.warning("文件类型为json")
      }
    } else {
      message.warning("每次只能上传一个文件！")
    }
  }
  const onMenuItemClick = () => { };
  const blankMenu = () => {

    return (<Menu hasIcon={true} onClick={onMenuClick}>
      {(node?.shape === "DuplicateOperator") && DuplicateOperatorMenu()}

      {node?.shape == CustomShape.TEXT_NODE && <>
        <SubMenu name="align" icon={<AlignCenterOutlined />} text="Text alignment">
          <Radio.Group name="horizontal" onChange={horizontalAlignHandler} value={horizontalAlign}>
            <Space.Compact direction="vertical">
              <Radio value={HorizontalAlignState.LEFT}>Left</Radio>
              <Radio value={HorizontalAlignState.CENTER}>H-Center</Radio>
              <Radio value={HorizontalAlignState.RIGHT}>Right</Radio>
            </Space.Compact>
          </Radio.Group>
          <Divider />
          <Radio.Group name="vertical" onChange={verticalAlignHandler} value={verticalAlign}>
            <Space.Compact direction="vertical">
              <Radio value={VerticalAlignState.TOP}>Top</Radio>
              <Radio value={VerticalAlignState.CENTER}>V-Center</Radio>
              <Radio value={VerticalAlignState.BOTTOM}>Bottom</Radio>
            </Space.Compact>
          </Radio.Group>
        </SubMenu>
        <SubMenu name="color" icon={<BgColorsOutlined />} text="Note Color">
          <Radio.Group name="color" size="small" onChange={noteTextColorHandler} value={noteTextColor}>
            <Space.Compact direction="horizontal">
              {Object.keys(NoteTextColor).filter(key => !isNaN(Number(NoteTextColor[key]))).map(
                (key) =>
                (
                  <>
                    <style>{`.ant-radio > input#radio-text-${key} + span.ant-radio-inner {background-color: ${NoteTextColorValue[NoteTextColor[key]]}}`}</style>
                    <Radio id={`radio-text-${key}`} value={NoteTextColor[key]} />
                  </>
                )
              )}
            </Space.Compact>
          </Radio.Group>
        </SubMenu>
        <Divider />
      </>
      }
      {node && <>
        <MenuItem
          onClick={onMenuItemClick}
          name="createProcess"
          icon={<GroupOutlined />}
          text="Move into new subprocess"
        />
      </>}
      <MenuItem
        onClick={onMenuItemClick}
        name="undo"
        icon={<UndoOutlined />}
        hotkey="Cmd+Z"
        text="Undo"
      />
      <MenuItem name="redo" icon={<RedoOutlined />} hotkey="Cmd+Shift+Z" text="Redo" />
      <MenuItem
        onClick={onMenuItemClick}
        name="import"
        icon={<UploadOutlined />}
        // text={<Upload maxCount={1} onChange={onFileChange} beforeUpload={beforeUpload}>import</Upload>}
        text={"import"}
      />
      {!node && <SubMenu text="Export" icon={<ExportOutlined />}>
        <MenuItem name="save-PNG" text="Save As PNG" />
        <MenuItem name="save-SVG" text="Save As SVG" />
        <MenuItem name="save-JPEG" text="Save As JPEG" />
        <MenuItem name="save-JSON" text="Save As JSON" />
      </SubMenu>}

      <Divider />

      <MenuItem icon={<ScissorOutlined />} name="cut" hotkey="Cmd+X" text="Cut" />
      <MenuItem icon={<CopyOutlined />} name="copy" hotkey="Cmd+C" text="Copy" />

      <MenuItem
        name="paste"
        icon={<SnippetsOutlined />}
        hotkey="Cmd+V"
        disabled={isDisablePaste}
        text="Paste"
      />
      <MenuItem name="delete" icon={<DeleteOutlined />} hotkey="Delete" text="Delete" />

      <Divider />

      <SubMenu name="order" icon={<RadiusSettingOutlined />} text="Change Order">
        <MenuItem name="front" icon={<RadiusSettingOutlined />} hotkey="Cmd+Shift+F" text="Bring to Front" />
        <MenuItem name="back" icon={<RadiusSettingOutlined />} hotkey="Cmd+Shift+B" text="Bring to Back" />
        <Divider />
        <MenuItem name="forward" icon={<RadiusSettingOutlined />} hotkey="Cmd+F" text="Bring Forward" />
        <MenuItem name="backward" icon={<RadiusSettingOutlined />} hotkey="Cmd+B" text="Bring Backward" />
      </SubMenu>
    </Menu>)
  }

  const styleObj: any = {
    position: 'absolute',
    top: `${top}px`, // 将top属性设置为state变量的值
    left: `${left}px`,
    width: '100px',
    height: '105px',
    zIndex: 9999,
  };

  return (
    <div style={styleObj}>
      {contextHolder}
      {blankMenu()}

    </div>
  );
});
