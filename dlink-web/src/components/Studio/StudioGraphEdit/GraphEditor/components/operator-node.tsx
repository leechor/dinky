
import BaseNode from './base-node';
import { NodeType } from './cpn-shape';

const OperatorNode = (icon:string ) =>(nodeType: NodeType) => <BaseNode nodeType={nodeType} iconPath={icon}/>;

export default OperatorNode;
