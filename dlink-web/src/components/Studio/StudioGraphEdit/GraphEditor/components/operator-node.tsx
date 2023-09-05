import { NodeType } from '../types';
import BaseNode from './base-node';


const OperatorNode = (icon: string, name: string) => (nodeType: NodeType) =>
  <BaseNode nodeType={nodeType} iconPath={icon} name={name} />;

export default OperatorNode;
