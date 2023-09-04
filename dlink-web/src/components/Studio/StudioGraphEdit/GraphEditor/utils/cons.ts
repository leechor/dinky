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
export default CustomShape;
