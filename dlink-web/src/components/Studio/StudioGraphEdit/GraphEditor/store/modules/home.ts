
import { JSONEditor } from '@json-editor/json-editor';
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { Parameter } from '@/components/Studio/StudioGraphEdit/GraphEditor/ts-define/parameter';
import { Graph, Node, Cell } from '@antv/x6';
import { getOperatorConfigure } from '../../service/request/test';
import { message } from 'antd';
export const initFlowDataAction = createAsyncThunk('fetchData', (payload, store) => {
  getOperatorConfigure().then((res: any) => {
    if (res.status !== 200) {
      message.error('请求节点参数失败!');
      return;
    }
    if (res?.data?.datas) {
      store.dispatch(initOperatorParameters(res.data.datas));
    }
  });
  // import(
  //   '@/components/Studio/StudioGraphEdit/GraphEditor/assets/json-data/operatorParameters.json'
  // ).then((res: any) => {
  //   if (res?.datas) {
  //     store.dispatch(initOperatorParameters(res.datas));
  //   }
  // });
});
export type GraphTabs = GraphTabsItem[]
export type Position = {
  x: number,
  y: number
}
export type GraphTabsItem = {
  id: string,
  key: number,
}
export type UnselectedCell = {
  groupId: string,
  childrenId: string[]
}

export type GroupTabItem = {
  groupCellId: string,
}


interface GraphEditorData {
  flowData: {},
  taskName: string,
  parameters: {},
  //初始化算子参数，注册算子组件
  operatorParameters: Parameter[],
  currentSelectNode: {} | Node,
  //当前选中的算子节点名称
  currentSelectNodeName: '',
  //当前选中的节点参数数据
  currentSelectNodeParamsData: [],
  //保存graph在其他组件中调用
  graph: Graph,
  editor: {} | InstanceType<typeof JSONEditor>,
  graphTabs: GroupTabItem[],
  unSelectedCellIds: UnselectedCell[]
  position: Position,
  verifyOperDatas: [],
  previewInfo: {
    values: string,
    node: Node | null,
    isShow: boolean
  },
  dataSourceInfo: {
    isShowModal: false,
    datas: any,
    node: Node | null,
    type: string,
  },
  groupNameInfo: {
    isShowGroupNameModal: boolean,
    node: Node | null,
    type: 'ADD' | 'CHANGE'
  },
  stencilMenuInfo: {
    x: number, y: number, showStencilMenu: boolean, node: null | Node
  },
  postionToGroup: {
    x: number,
    y: number,
    isFullScreen: boolean,
  }

}
const initialState: GraphEditorData = {
  flowData: {},
  taskName: "",
  parameters: {},
  //初始化算子参数，注册算子组件
  operatorParameters: [] as Parameter[],
  currentSelectNode: {},
  //当前选中的算子节点名称
  currentSelectNodeName: '',
  //当前选中的节点参数数据
  currentSelectNodeParamsData: [],
  //保存graph在其他组件中调用
  graph: {} as Graph,
  //保存jsoneditor 示例用作保存校验
  editor: {},
  graphTabs: [{
    groupCellId: ""
  }],
  unSelectedCellIds: [],
  position: { x: 0, y: 0 },
  verifyOperDatas: [],
  previewInfo: { values: "", node: null, isShow: false },
  dataSourceInfo: {
    isShowModal: false,
    datas: null,
    node: null,
    type: "",
  },
  groupNameInfo: {
    isShowGroupNameModal: false,
    node: null,
    type: "ADD"
  },
  stencilMenuInfo: {
    x: 0, y: 0, showStencilMenu: false, node: null
  },
  postionToGroup: {
    x: 0,
    y: 0,
    isFullScreen: false,
  }

}
const homeSlice = createSlice({
  name: 'home',
  initialState,
  reducers: {
    initFlowDataInfo(state, { payload }) {
      state.flowData = payload;
    },
    initTaskName(state, { payload }) { state.taskName = payload },
    changeParameters(state, { payload }) {
      let parameters = payload?.store?.data?.data?.parameters;
      state.parameters = parameters.length > 0 ? parameters[0] : {};
    },
    //初始化算子参数stencil
    initOperatorParameters(state, { payload }) {
      
      state.operatorParameters = payload;
    },
    //保存当前选中的节点信息
    changeCurrentSelectNode(state, { payload }) {
      state.currentSelectNode = payload;
    },
    //保存当前选中的节点名称
    changeCurrentSelectNodeName(state, { payload }) {
      state.currentSelectNodeName = payload;
    },
    //设置当前节点属性数据
    changeCurrentSelectNodeParamsData(state, { payload }) {
      state.currentSelectNode = payload;
    },
    changeGraph(state, { payload }) {
      state.graph = payload;
    },
    changeJsonEditor(state, { payload }) {
      state.editor = payload
    },

    addGraphTabs(state, { payload }) {
      state.graphTabs.push({ groupCellId: payload.groupCellId, })
    },

    removeGraphTabs(state, { payload }) {
      state.graphTabs = state.graphTabs.slice(0, payload + 1)
    },

    changeUnselectedCells(state, { payload }) {
      const unSelectedCellIds = state.unSelectedCellIds;
      if (payload.type == "push") {
        unSelectedCellIds.push(payload.data)
        state.unSelectedCellIds = [...unSelectedCellIds]
      }
      if (payload.type == "shift") {

      }

    },
    changePositon(state, { payload }) {
      state.position = payload
    },
    changeVerifyOperDatas(state, { payload }) {
      state.verifyOperDatas = payload
    },
    changePreviewInfo(state, { payload }) {
      state.previewInfo = payload
    },
    changeDataSourceInfo(state, { payload }) {
      state.dataSourceInfo = payload
    },
    changeGroupNameInfo(state, { payload }) {
      state.groupNameInfo = payload
    },
    changeStencilMenuInfo(state, { payload }) {
      
      state.stencilMenuInfo = payload
    },
    changePostionToGroup(state, { payload }) {
      state.postionToGroup = payload
    }
  },
  extraReducers: {},
});

export const {
  initFlowDataInfo,
  initTaskName,
  changeParameters,
  initOperatorParameters,
  changeCurrentSelectNode,
  changeCurrentSelectNodeName,
  changeCurrentSelectNodeParamsData,
  changeGraph,
  changeJsonEditor,
  addGraphTabs,
  removeGraphTabs,
  changePositon,
  changeVerifyOperDatas,
  changePreviewInfo,
  changeDataSourceInfo,
  changeGroupNameInfo,
  changeStencilMenuInfo,
  changePostionToGroup
} = homeSlice.actions;

export default homeSlice.reducer;
