import BaseNode from './base-node';
import { NodeType } from './cpn-shape';

const OperatorNode = (icon: string, name: string) => (nodeType: NodeType) =>
  <BaseNode nodeType={nodeType} iconPath={icon} name={name} />;

export default OperatorNode;
