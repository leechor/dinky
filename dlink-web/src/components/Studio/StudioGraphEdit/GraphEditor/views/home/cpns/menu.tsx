import { Menu } from '@antv/x6-react-components';
import { FC, memo, useState } from 'react';
import { message, Radio, RadioChangeEvent, Space } from 'antd';
import { Cell, DataUri, Edge, Graph, Node } from '@antv/x6';
import '@antv/x6-react-components/es/menu/style/index.css';
import {
  AlignCenterOutlined,
  BgColorsOutlined,
  CopyOutlined,
  DeleteOutlined,
  EditOutlined,
  ExportOutlined,
  GroupOutlined,
  RedoOutlined,
  ScissorOutlined,
  SnippetsOutlined,
  UndoOutlined,
  UploadOutlined,
  EyeOutlined,
  DatabaseOutlined,
} from '@ant-design/icons';
import CustomShape, {
  GraphHistory,
  PreNodeInfo,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons';
import {
  useAppDispatch,
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import type { Parameter } from '@/components/Studio/StudioGraphEdit/GraphEditor/ts-define/parameter';
import { formatDate } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/math';
import {
  convertAbsoluteToRelativePosition,
  getGraphViewSize,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/graph-helper';
import {
  changePreviewInfo,
  changeDataSourceInfo,
  changeGroupNameInfo,
  changeAddPortInfo,
} from '../../../store/modules/home';
import { getNodePreviewInfo, getDataSourceType } from '@/components/Common/crud';
import CpnShape from '@/components/Studio/StudioGraphEdit/GraphEditor/components/cpn-shape';

type MenuPropsType = {
  top: number;
  left: number;
  graph: Graph;
  node: Node | null;
  show: boolean;
  handleShowMenu: (value: boolean) => void;
};

enum HorizontalAlignState {
  LEFT = 1,
  CENTER,
  RIGHT,
}

type OuterEdgeType = 'input' | 'output';

enum VerticalAlignState {
  TOP = 1,
  CENTER,
  BOTTOM,
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
  [NoteTextColor.YELLOW]: '#fcf987',
  [NoteTextColor.RED]: '#ffe9dc',
  [NoteTextColor.ORANGE]: '#fffad2',
  [NoteTextColor.PURPLE]: '#f6deed',
  [NoteTextColor.GREEN]: '#ddeed2',
  [NoteTextColor.BLUE]: '#e3dff1',
  [NoteTextColor.GRAY]: '#d1d1d1',
  [NoteTextColor.TRANSPARENT]: 'transparent',
};

const DuplicateOperatorMenu = () => {
  return <Menu.Item icon={<EditOutlined />} name="add-port" text="Add Port" key="add-port" />;
};

function createPackageNode(graph: Graph) {
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
        text: CustomShape.GROUP_PROCESS,
        fontSize: 2,
      },
    },
  });
  return node;
}

export const CustomMenu: FC<MenuPropsType> = memo(
  ({ top = 0, left = 0, graph, node, handleShowMenu, show }) => {
    const { taskName, operatorParameters } = useAppSelector((state) => ({
      taskName: state.home.taskName,
      operatorParameters: state.home.operatorParameters,
    }));
    const dispatch = useAppDispatch();

    const isDataSource: boolean = operatorParameters
      .filter((op: Parameter) => op.group.includes('dataSource'))
      .some((op: Parameter) => op.code === node?.shape);

    const convertHorizontalAlign = (align: string) => {
      switch (align) {
        case 'left':
          return HorizontalAlignState.LEFT;
        case 'center':
          return HorizontalAlignState.CENTER;
        case 'right':
          return HorizontalAlignState.RIGHT;
        default:
          return HorizontalAlignState.LEFT;
      }
    };

    const convertVerticalAlign = (align: string) => {
      switch (align) {
        case 'top':
          return VerticalAlignState.TOP;
        case 'center':
          return VerticalAlignState.CENTER;
        case 'bottom':
          return VerticalAlignState.BOTTOM;
        default:
          return VerticalAlignState.TOP;
      }
    };

    const { Item: MenuItem, SubMenu, Divider } = Menu;
    const [messageApi, contextHolder] = message.useMessage();
    const [isDisablePaste, setIsDisablePaste] = useState(true);
    const [horizontalAlign, setHorizontalAlign] = useState(
      convertHorizontalAlign(node?.getData()?.horizontalAlign ?? 'left'),
    );
    const [verticalAlign, setVerticalAlign] = useState(
      convertVerticalAlign(node?.getData()?.verticalAlign ?? 'top'),
    );
    const [noteTextColor, setNoteTextColor] =
      useState(node?.getData()?.backgroundColor) ?? 'yellow';
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
      cells?.forEach((c) => c.toFront());
    };

    const back = () => {
      const cells = graph.getSelectedCells();
      cells?.forEach((c) => c.toBack());
    };

    const forward = () => {
      const cells = graph.getSelectedCells();
      cells?.forEach((c) => {
        c.setZIndex((c.getZIndex() ?? 0) + 1);
      });
    };

    const backward = () => {
      const cells = graph.getSelectedCells();
      cells?.forEach((c) => {
        c.setZIndex((c.getZIndex() ?? 0) - 1);
      });
    };

    const horizontalAlignHandler = (e: RadioChangeEvent) => {
      const horizontalAlign = e.target.value as number;
      const align = ((value: number) => {
        switch (value) {
          case HorizontalAlignState.LEFT:
            return 'left';
          case HorizontalAlignState.CENTER:
            return 'center';
          case HorizontalAlignState.RIGHT:
            return 'right';
          default:
            return 'left';
        }
      })(horizontalAlign);
      graph
        .getSelectedCells()
        ?.filter((c) => c.shape == CustomShape.TEXT_NODE)
        .forEach((c) => c.setData({ ...c.getData(), horizontalAlign: align }));
      setHorizontalAlign(horizontalAlign);
    };

    const verticalAlignHandler = (e: RadioChangeEvent) => {
      const verticalAlign = e.target.value as number;
      const align = ((value: number) => {
        switch (value) {
          case VerticalAlignState.TOP:
            return 'top';
          case VerticalAlignState.CENTER:
            return 'center';
          case VerticalAlignState.BOTTOM:
            return 'bottom';
          default:
            return 'top';
        }
      })(verticalAlign);
      const cc = graph.getSelectedCells()?.filter((c) => c.shape == CustomShape.TEXT_NODE);
      cc.forEach((c) => c.setData({ ...c.getData(), verticalAlign: align }));
      setVerticalAlign(verticalAlign);
    };

    const noteTextColorHandler = (e: RadioChangeEvent) => {
      const noteTextColor = e.target.value as number;
      const color = noteTextColor
        ? NoteTextColorValue[noteTextColor]
        : NoteTextColorValue[NoteTextColor.TRANSPARENT];
      const cc = graph.getSelectedCells()?.filter((c) => c.shape == CustomShape.TEXT_NODE);
      cc.forEach((c) => c.setData({ ...c.getData(), backgroundColor: color }));
      setNoteTextColor(noteTextColor);
    };
    const deleteCells = () => {
      let selectedCells = graph.getSelectedCells();
      if (selectedCells.length) {
        graph.removeCells(graph.getSelectedCells());
      } else {
        graph.clearCells();
      }
    };
    const onMenuClick = (name: string) => {
      handleShowMenu(false);
      switch (name) {
        case 'undo':
          graph.undo();
          break;
        case 'redo':
          graph.redo();
          break;
        case 'delete':
          deleteCells();
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
          break;
        case 'add-port':
          dispatch(changeAddPortInfo({ isShow: true, values: '', node }));
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
        case 'createProcess':
          createProcess();
          break;
        case 'preview':
          preview();
          break;
        case 'customGroup':
          customGroup();
          break;
        case 'setDataSource':
          setDataSource();
          break;
        case 'import':
          uploadFileClick();
          break;
      }
    };

    const customGroup = () => {
      dispatch(changeGroupNameInfo({ node, isShowGroupNameModal: true, type: 'ADD' }));
    };
    const createProcess = () => {
      const selectedNodes = graph
        .getSelectedCells()
        .filter((item) => item.isNode())
        .map((item) => item as Node);

      if (selectedNodes.length === 0) {
        return;
      }

      const selectNodeParents = selectedNodes.map((node) => node.parent) ?? [];
      let haveSameParent = false;
      if (selectNodeParents.length > 0) {
        haveSameParent =
          !!selectNodeParents[0] &&
          selectNodeParents.every((value) => value === selectNodeParents[0]);
      }
      const originJson = graph.toJSON();
      const groupNode = graph.addNode(createPackageNode(graph), {
        eventType: GraphHistory.CREATE_PROCESS,
        graphJson: originJson,
      });

      dispatch(
        changeGroupNameInfo({
          node: groupNode,
          isShowGroupNameModal: true,
          type: 'CREAT_GROUP_NAME',
        }),
      );
      const selectRectangle = graph.getCellsBBox(selectedNodes)!;

      let relativeParentPosition = { x: selectRectangle.x, y: selectRectangle.y };
      if (haveSameParent) {
        selectNodeParents[0]?.insertChild(groupNode);
        relativeParentPosition = convertAbsoluteToRelativePosition(
          selectRectangle,
          groupNode.parent as Node,
        );
      }

      groupNode.setPosition(
        relativeParentPosition.x + (selectRectangle.width - groupNode.size().width) / 2,
        relativeParentPosition.y + (selectRectangle.height - groupNode.size().height) / 2,
        { relative: true, deep: true },
      );

      const subGraphCells = graph.model.getSubGraph(selectedNodes);

      groupNode.prop(PreNodeInfo.PREVIOUS_NODE_RECT, {
        ...groupNode.position({ relative: true }),
        ...groupNode.size(),
      });

      const { width, height } = getGraphViewSize() ?? {};
      if (!width || !height) {
        return;
      }

      subGraphCells.forEach((item) => {
        item.insertTo(groupNode);
        if (item.isNode()) {
          const x = item.position().x - relativeParentPosition.x;
          const y = item.position().y - relativeParentPosition.y;
          item.prop(PreNodeInfo.PREVIOUS_NODE_RECT, { x, y, ...item.size() });
          item.setPosition(groupNode.position());
        }
        item.hide();
      });
      //此时selectIncomingEdge包含组节点内的output_0_in这类线
      const selectedIncomingEdge: (Edge | null)[] = selectedNodes
        .flatMap((node) => graph.model.getIncomingEdges(node))
        .filter((edge) => !selectedNodes.some((node) => node.id === edge?.getSourceNode()!.id))
        .filter((edge) => edge !== null)
        .filter((edge) => {
          const portId = edge?.getTargetPortId();
          const targetCell = edge?.getTargetNode();
          const portMetadata = portId && targetCell?.getPort(portId);
          const portGroupType = portMetadata && portMetadata.group;
          return portGroupType !== 'innerInputs';
        });
      addOuterPortAndEdge(selectedIncomingEdge, groupNode, 'input', originJson);
      removeEdges(selectedIncomingEdge, originJson);

      const selectedOutgoingEdge: (Edge | null)[] = selectedNodes
        .flatMap((node) => graph.model.getOutgoingEdges(node))
        .filter((edge) => !selectedNodes.some((node) => node.id === edge?.getTargetNode()!.id))
        .filter((edge) => edge !== null)
        .filter((edge) => {
          const portId = edge?.getSourcePortId();
          const sourceCell = edge?.getSourceNode();
          const portMetadata = portId && sourceCell?.getPort(portId);
          const portGroupType = portMetadata && portMetadata.group;
          return portGroupType !== 'innerOutputs';
        });
      addOuterPortAndEdge(selectedOutgoingEdge, groupNode, 'output', originJson);
      removeEdges(selectedOutgoingEdge, originJson);

      //将隐藏的节点设置为不可选
      graph.setSelectionFilter((cell) => {
        return !subGraphCells?.map((c) => c.id)!.includes(cell.id);
      });
    };
    const preview = async () => {
      const data = graph.toJSON().cells.find((cell) => cell.id === node!.id);
      const { datas, msg, code } = await getNodePreviewInfo(
        '/api/zdpx/preview',
        JSON.stringify(data),
      );
      if (code === 1) {
        messageApi.error(msg);
      }
      if (code === 0) {
        dispatch(changePreviewInfo({ node, values: datas, isShow: true }));
      }
    };
    const setDataSource = async () => {
      const op: Parameter = operatorParameters.find((op: Parameter) => op.code === node?.shape);
      const { datas, msg, code } = await getDataSourceType(
        `/api/database/listEnabledByType/${op.type}`,
      );
      if (code === 1) {
        messageApi.error(msg);
      }
      if (code === 0) {
        dispatch(changeDataSourceInfo({ isShowModal: true, datas, node, type: op.type }));
      }
    };
    const addOuterPortAndEdge = (
      outEdge: (Edge | null)[],
      groupNode: Node,
      type: OuterEdgeType,
      originJson: any,
    ) => {
      //将外部节点与组节点连线

      if (!outEdge.length) {
        //情况1：所选组无连线，默认一个连接装并且无连线

        return;
      }

      //添加外部输入桩
      if (outEdge.length > 0) {
        //根据连线长度，添加n-1个输入桩
        for (let i = 1; i < outEdge.length; i++) {
          //添加连接桩
          groupNode.addPort({
            id: `${type}_${i}`,
            group: type + 's',
            attrs: {
              text: {
                text: `${type}_${i}`,
                style: {
                  visibility: 'hidden',
                  fontSize: 10,
                  fill: '#3B4351',
                },
              },
              path: {
                d: type == 'input' ? 'm-6,2,a5,5.5 0 0 1 12,0' : 'm-6,2,a5,5.5 0 0 1 12,0',
              },
            },
            label: {
              position: type == 'input' ? 'left' : 'right',
            },
          });
        }
      }

      //添加外部输入连线
      if (outEdge.length !== 0) {
        outEdge.forEach((edge, index) => {
          if (type === 'input') {
            const sourceCell = edge!.getSourceCell();
            const sourcePortId = edge!.getSourcePortId();
            const innerTargetCell = edge?.getTargetCell();
            const innerTargetPortId = edge?.getTargetPortId();
            graph.addEdge(
              {
                attrs: {
                  line: {
                    stroke: '#b2a2e9',
                    strokeWidth: 2,
                    targetMarker: {
                      name: 'classic',
                      size: 10,
                    },
                  },
                },
                source: { cell: sourceCell!, port: sourcePortId },
                target: { cell: groupNode!, port: `input_${index}` },
              },
              { eventType: GraphHistory.CREATE_PROCESS, graphJson: originJson },
            );
            //添加内部桩和连线
            addInnerPortAndEdge(
              groupNode,
              'output',
              `input_${index}`,
              innerTargetCell,
              innerTargetPortId,
              originJson,
            );
          } else {
            const targetCell = edge!.getTargetCell();
            const targetPortId = edge!.getTargetPortId();
            const innerSourceCell = edge?.getSourceCell();
            const innerSourcePortId = edge?.getSourcePortId();
            graph.addEdge(
              {
                attrs: {
                  line: {
                    stroke: '#b2a2e9',
                    strokeWidth: 2,
                    targetMarker: {
                      name: 'classic',
                      size: 10,
                    },
                  },
                },
                source: { cell: groupNode!, port: `${type}_${index}` },
                target: { cell: targetCell!, port: targetPortId },
              },
              { eventType: GraphHistory.CREATE_PROCESS, graphJson: originJson },
            );
            addInnerPortAndEdge(
              groupNode,
              'input',
              `${type}_${index}`,
              innerSourceCell,
              innerSourcePortId,
              originJson,
            );
          }
        });
      }
    };

    const addInnerPortAndEdge = (
      groupNode: Node,
      type: OuterEdgeType,
      outPortId: string,
      targetCell: Cell | null | undefined,
      targetPortId: string | undefined,
      originJson: any,
    ) => {
      if (type === 'output') {
        //添加连接桩
        if (!groupNode.hasPort(`${outPortId}_in`)) {
          groupNode.addPort({
            id: `${outPortId}_in`,
            group: 'innerOutputs',
            attrs: {
              text: {
                text: outPortId + '_in',
                style: {
                  visibility: 'hidden',
                  fontSize: 10,
                  fill: '#3B4351',
                },
              },
            },
            args: {
              dx: 2,
            },
            label: {
              position: 'right',
            },
          });
        }
        addInnerEdges(
          groupNode.id,
          `${outPortId}_in`,
          'output',
          targetCell!.id,
          targetPortId!,
          originJson,
        );
      } else {
        //添加连接桩
        if (!groupNode.hasPort(`${outPortId}_in`)) {
          groupNode.addPort({
            id: `${outPortId}_in`,
            group: 'innerInputs',
            attrs: {
              text: {
                text: outPortId + '_in',
                style: {
                  visibility: 'hidden',
                  fontSize: 10,
                  fill: '#3B4351',
                },
              },
            },
            args: {
              dx: -2,
            },
            label: {
              position: 'left',
            },
          });
        }
        addInnerEdges(
          groupNode.id,
          `${outPortId}_in`,
          'input',
          targetCell!.id,
          targetPortId!,
          origin,
        );
      }
    };
    const removeEdges = (edges: (Edge | null)[], originJson: any) => {
      if (edges.length) {
        for (let edge of edges) {
          graph.removeEdge(edge!, {
            eventType: GraphHistory.CREATE_PROCESS,
            graphJson: originJson,
          });
        }
      }
    };

    const addInnerEdges = (
      gId: string,
      gPortId: string,
      type: OuterEdgeType,
      conSourceCellId: string,
      conSourPortId: string,
      originJson: any,
    ) => {
      if (type === 'output') {
        graph
          .addEdge(
            {
              attrs: {
                line: {
                  stroke: '#b2a2e9',
                  strokeWidth: 2,
                  targetMarker: {
                    name: 'classic',
                    size: 10,
                  },
                },
              },
              router: 'orth',
              connector: {
                name: 'rounded',
              },
              source: { cell: gId, port: gPortId },
              target: { cell: conSourceCellId, port: conSourPortId },
            },
            { eventType: GraphHistory.CREATE_PROCESS, graphJson: originJson },
          )
          .hide();
      } else {
        graph
          .addEdge(
            {
              attrs: {
                line: {
                  stroke: '#b2a2e9',
                  strokeWidth: 2,
                  targetMarker: {
                    name: 'classic',
                    size: 10,
                  },
                },
              },
              router: 'orth',
              connector: {
                name: 'rounded',
              },
              source: { cell: conSourceCellId, port: conSourPortId },
              target: { cell: gId, port: gPortId },
            },
            { eventType: GraphHistory.CREATE_PROCESS, graphJson: originJson },
          )
          .hide();
      }
    };
    const uploadFileClick = () => {
      const fileInput = document.createElement('input');
      fileInput.type = 'file';
      fileInput.accept = '.json';
      fileInput.style.display = 'none';
      fileInput.addEventListener('change', handleFileChange);
      fileInput.click();
    };

    const handleFileChange = (event: any) => {
      const selectFiles = event.target.files;
      if (selectFiles.length === 1) {
        const selectFile = selectFiles[0];
        if (selectFile && selectFile.type == 'application/json') {
          const reader = new FileReader();
          reader.onload = (event: any) => {
            const fileContent = event.target.result;
            const jsonData = JSON.parse(fileContent);
            try {
              graph.fromJSON(jsonData);
            } catch (error) {
              message.error('导入数据失败！');
            }
          };
          reader.readAsText(selectFile);
        } else {
          message.warning('文件类型为json');
        }
      } else {
        message.warning('每次只能上传一个文件！');
      }
    };

    const isShowProcessMenu = () => {
      const rect = graph.model.getCellsBBox(graph.getSelectedCells());
      const p = graph.localToGraph(rect!);
      return left >= p.x! && left <= p.x + p.width && top >= p.y! && top <= p.y + p.height;
    };
    const blankMenu = () => {
      return (
        <Menu hasIcon={true} onClick={onMenuClick}>
          {(node?.shape === CustomShape.CUSTOMER_OPERATOR ||
            node?.shape === CustomShape.DUPLICATE_OPERATOR) &&
            DuplicateOperatorMenu()}

          {node?.shape == CustomShape.TEXT_NODE && (
            <>
              <SubMenu
                key="align"
                name="align"
                icon={<AlignCenterOutlined />}
                text="Text alignment"
              >
                <Radio.Group
                  key="horizontal"
                  name="horizontal"
                  onChange={horizontalAlignHandler}
                  value={horizontalAlign}
                >
                  <Space key="Space-horizontal" direction="vertical">
                    <Radio key="LEFT" name="horizontal" value={HorizontalAlignState.LEFT}>
                      Left
                    </Radio>
                    <Radio key="CENTER" name="horizontal" value={HorizontalAlignState.CENTER}>
                      H-Center
                    </Radio>
                    <Radio key="RIGHT" name="horizontal" value={HorizontalAlignState.RIGHT}>
                      Right
                    </Radio>
                  </Space>
                </Radio.Group>
                <Divider />
                <Radio.Group
                  key="vertical"
                  name="vertical"
                  onChange={verticalAlignHandler}
                  value={verticalAlign}
                >
                  <Space direction="vertical" key="Space-vertical">
                    <Radio key="Top" name="vertical" value={VerticalAlignState.TOP}>
                      Top
                    </Radio>
                    <Radio key="V-Center" name="vertical" value={VerticalAlignState.CENTER}>
                      V-Center
                    </Radio>
                    <Radio key="Bottom" name="vertical" value={VerticalAlignState.BOTTOM}>
                      Bottom
                    </Radio>
                  </Space>
                </Radio.Group>
              </SubMenu>
              <SubMenu key="color" name="color" icon={<BgColorsOutlined />} text="Note Color">
                <MenuItem>
                  <Radio.Group
                    name="color"
                    size="small"
                    onChange={noteTextColorHandler}
                    value={noteTextColor}
                  >
                    <Space direction="horizontal" key="Space-color">
                      {Object.keys(NoteTextColor)
                        .filter((key) => !isNaN(Number(NoteTextColor[key])))
                        .map((key) => (
                          <div key={key}>
                            <style>{`.ant-radio > input#radio-text-${key} + span.ant-radio-inner {background-color: ${
                              NoteTextColorValue[NoteTextColor[key]]
                            }}`}</style>
                            <Radio id={`radio-text-${key}`} value={NoteTextColor[key]} />
                          </div>
                        ))}
                    </Space>
                  </Radio.Group>
                </MenuItem>
              </SubMenu>
              <Divider />
            </>
          )}
          {isShowProcessMenu() && (
            <>
              <MenuItem
                name="createProcess"
                icon={<GroupOutlined />}
                text="Move into new subprocess"
                key="createProcess"
              />
            </>
          )}
          {node && node?.shape !== CustomShape.GROUP_PROCESS && (
            <MenuItem name="preview" icon={<EyeOutlined />} text="Node preview" key="preview" />
          )}
          {node && (
            <MenuItem
              key="customGroup"
              name="customGroup"
              icon={<EyeOutlined />}
              text="Custom Group"
            />
          )}
          {node && isDataSource && (
            <MenuItem
              name="setDataSource"
              key="setDataSource"
              icon={<DatabaseOutlined />}
              text="Select dataSource"
            />
          )}
          <MenuItem name="undo" key="undo" icon={<UndoOutlined />} hotkey="Cmd+Z" text="Undo" />
          <MenuItem name="redo" icon={<RedoOutlined />} hotkey="Cmd+Shift+Z" text="Redo" />
          <MenuItem name="import" icon={<UploadOutlined />} key="import" text="Import" />
          {!node && (
            <SubMenu key="Export" text="Export" icon={<ExportOutlined />}>
              <MenuItem key="save-PNG" name="save-PNG" text="Save As PNG" />
              <MenuItem key="save-SVG" name="save-SVG" text="Save As SVG" />
              <MenuItem key="save-JPEG" name="save-JPEG" text="Save As JPEG" />
              <MenuItem key="save-JSON" name="save-JSON" text="Save As JSON" />
            </SubMenu>
          )}

          <Divider />

          <MenuItem key="cut" icon={<ScissorOutlined />} name="cut" hotkey="Cmd+X" text="Cut" />
          <MenuItem key="copy" icon={<CopyOutlined />} name="copy" hotkey="Cmd+C" text="Copy" />

          <MenuItem
            key="paste"
            name="paste"
            icon={<SnippetsOutlined />}
            hotkey="Cmd+V"
            disabled={isDisablePaste}
            text="Paste"
          />
          <MenuItem
            key="delete"
            name="delete"
            icon={<DeleteOutlined />}
            hotkey="Delete"
            text="Delete"
          />

          <Divider />

          <SubMenu
            name="order"
            key="order"
            icon={<CpnShape iconPath="icon-a-ziyuan52-copy" />}
            text="Change Order"
          >
            <MenuItem
              key="front"
              name="front"
              icon={<CpnShape iconPath="icon-zhiyudingceng" />}
              hotkey="Cmd+Shift+F"
              text="Bring to Front"
            />
            <MenuItem
              key="back"
              name="back"
              icon={<CpnShape iconPath="icon-zhiyudiceng" />}
              hotkey="Cmd+Shift+B"
              text="Bring to Back"
            />
            <Divider />
            <MenuItem
              key="forward"
              name="forward"
              icon={<CpnShape iconPath="icon-xiangqianyiceng" />}
              hotkey="Cmd+F"
              text="Bring Forward"
            />
            <MenuItem
              key="backward"
              name="backward"
              icon={<CpnShape iconPath="icon-xianghouyiceng" />}
              hotkey="Cmd+B"
              text="Bring Backward"
            />
          </SubMenu>
        </Menu>
      );
    };

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
  },
);
