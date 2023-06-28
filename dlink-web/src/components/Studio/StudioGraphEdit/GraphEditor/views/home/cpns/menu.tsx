import { Menu } from '@antv/x6-react-components';
import { FC, memo, useState } from 'react';
import {message, Radio, RadioChangeEvent, Space} from 'antd';
import { DataUri, Graph, Node } from '@antv/x6';
import '@antv/x6-react-components/es/menu/style/index.css';
import {
  CopyOutlined,
  SnippetsOutlined,
  RedoOutlined,
  UndoOutlined,
  ScissorOutlined,
  DeleteOutlined,
  ExportOutlined,
  EditOutlined, RadiusSettingOutlined
} from '@ant-design/icons';
import CustomShape from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons";

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

enum VerticalAlignState {
  TOP = 1,
  CENTER,
  BOTTOM
}

enum NoteTextColor {
  YELLOW=1,
  ORANGE,
  RED,
  PURPLE,
  GREEN,
  BLUE,
  GRAY,
  TRANSPARENT,
}

const DuplicateOperatorMenu = () => {
  return <Menu.Item icon={<EditOutlined />} name="add-port" text="添加输出桩" />
}

export const CustomMenu: FC<MenuPropsType> = memo(({ top = 0, left = 0, graph, node, handleShowMenu, show }) => {
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

  const {Item: MenuItem, SubMenu, Divider} = Menu;
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
    cells?.forEach(c => {c.setZIndex((c.getZIndex() ?? 0 ) + 1)});
  };

  const backward = () => {
    const cells = graph.getSelectedCells();
    cells?.forEach(c => {c.setZIndex((c.getZIndex() ?? 0) - 1)});
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
      .forEach(c => c.setData({...c.getData(), 'horizontalAlign': align}))
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
    const cc= graph.getSelectedCells()?.filter(c => c.shape == CustomShape.TEXT_NODE)
    cc.forEach(c => c.setData({...c.getData(), 'verticalAlign': align}))
    setVerticalAlign(verticalAlign)
  }

   const noteTextColorHandler = (e: RadioChangeEvent) => {
     const noteTextColor = e.target.value as number;
     const color = ((value: number) => {
       switch (value) {
         case NoteTextColor.YELLOW:
           return "yellow";
         case NoteTextColor.ORANGE:
           return "orange";
         case NoteTextColor.RED:
           return "red";
         case NoteTextColor.PURPLE:
           return "purple";
         case NoteTextColor.GREEN:
           return "green";
         case NoteTextColor.BLUE:
           return "blue";
         case NoteTextColor.GRAY:
           return "gray";
         default:
           return "transparent";
       }
     })(noteTextColor);
     const cc= graph.getSelectedCells()?.filter(c => c.shape == CustomShape.TEXT_NODE)
     cc.forEach(c => c.setData({...c.getData(), 'backgroundColor': color}))
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
        const blob = new Blob([data], {type: 'text/json'}),
          e = new MouseEvent('click'),
          a = document.createElement('a');

        a.download = '流程数据';
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
      default:
        break;
    }
  };

  const onMenuItemClick = () => { };
  const blankMenu = () => {

    return (<Menu hasIcon={true} onClick={onMenuClick}>
      {(node?.shape === "DuplicateOperator") && DuplicateOperatorMenu()}

      { node?.shape == CustomShape.TEXT_NODE &&<>
        <SubMenu name="align" icon={<RadiusSettingOutlined/>} text="Text alignment">
          <Radio.Group name="horizontal" onChange={horizontalAlignHandler} value={horizontalAlign}>
            <Space direction="vertical">
              <Radio value={HorizontalAlignState.LEFT}>Left</Radio>
              <Radio value={HorizontalAlignState.CENTER}>H-Center</Radio>
              <Radio value={HorizontalAlignState.RIGHT}>Right</Radio>
            </Space>
          </Radio.Group>
          <Divider/>
          <Radio.Group name="vertical" onChange={verticalAlignHandler} value={verticalAlign}>
            <Space direction="vertical">
              <Radio value={VerticalAlignState.TOP}>Top</Radio>
              <Radio value={VerticalAlignState.CENTER}>V-Center</Radio>
              <Radio value={VerticalAlignState.BOTTOM}>Bottom</Radio>
            </Space>
          </Radio.Group>
        </SubMenu>
        <SubMenu name="color" icon={<RadiusSettingOutlined/>} text="Note Color">
          <Radio.Group name="color" size="small" onChange={noteTextColorHandler} value={noteTextColor}>
            <Space direction="horizontal">
              <Radio value={NoteTextColor.YELLOW} style={{backgroundColor: "yellow"}}/>
              <Radio value={NoteTextColor.ORANGE} style={{backgroundColor: "orange"}}/>
              <Radio value={NoteTextColor.RED} style={{backgroundColor: "red"}}/>
              <Radio value={NoteTextColor.PURPLE} style={{backgroundColor: "purple"}}/>
              <Radio value={NoteTextColor.GREEN} style={{backgroundColor: "green"}}/>
              <Radio value={NoteTextColor.BLUE} style={{backgroundColor: "blue"}}/>
              <Radio value={NoteTextColor.GRAY} style={{backgroundColor: "gray"}}/>
              <Radio value={NoteTextColor.TRANSPARENT} style={{backgroundColor: "transparent"}}/>
            </Space>
          </Radio.Group>
        </SubMenu>
      <Divider/>
      </>
      }

      <MenuItem
        onClick={onMenuItemClick}
        name="undo"
        icon={<UndoOutlined />}
        hotkey="Cmd+Z"
        text="Undo"
      />
      <MenuItem name="redo" icon={<RedoOutlined />} hotkey="Cmd+Shift+Z" text="Redo" />

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
       <MenuItem name="front" icon={<RadiusSettingOutlined/>} hotkey="Cmd+Shift+F" text="Bring to Front"/>
       <MenuItem name="back" icon={<RadiusSettingOutlined/>} hotkey="Cmd+Shift+B" text="Bring to Back"/>
        <Divider/>
       <MenuItem name="forward" icon={<RadiusSettingOutlined/>} hotkey="Cmd+F" text="Bring Forward"/>
       <MenuItem name="backward" icon={<RadiusSettingOutlined/>} hotkey="Cmd+B" text="Bring Backward"/>
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
