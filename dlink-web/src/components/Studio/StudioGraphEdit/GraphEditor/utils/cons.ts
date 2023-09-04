import { JSONEditorOptions } from "@json-editor/json-editor";

enum CustomShape {
  TEXT_NODE = 'custom-text-node',
  GROUP_PROCESS = 'group-process',
  DUPLICATE_OPERATOR = 'DuplicateOperator',
  CUSTOMER_OPERATOR = 'CustomerOperator',
}

export enum PreNodeInfo {
  PREVIOUS_NODE_RECT = 'preNodeRect',
}

export enum FunctionType {
  ANALYSE = 'analyse', //算子解析
}

export enum GraphHistory {
  ADD_PORT = 'addPort',
  CREATE_PROCESS = 'createProcess',
  EXTEND_PROCESS = 'extendProcess',
}

export const JSONEDITORCONFIG: JSONEditorOptions<any> = {
  //设置主题,可以是bootstrap或者jqueryUI等
  theme: 'spectre',
  //设置字体
  iconlib: 'spectre',
  //如果设置为 true, 将隐藏编辑属性按钮.
  disable_properties: true,
  disable_edit_json: true,
  //如果设置为 true, 数组对象将不显示“向上”、“向下”移动按钮.
  disable_array_reorder: true,
  //属性为object时,属性默认normal,设置grid可以一排多个
  object_layout: 'normal',
  disable_array_delete: false,
  compact: true,
}
export enum DataSourceType {
  Mysql = 'Mysql',
}

export default CustomShape;
