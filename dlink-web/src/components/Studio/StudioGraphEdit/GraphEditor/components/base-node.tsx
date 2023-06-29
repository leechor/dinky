import { Tooltip } from 'antd';
import styles from './index.less';
import CpnShape, { NodeType } from './cpn-shape';

const BaseNode = (props: { nodeType: NodeType; iconPath: string,name:string }) => {
  const {
    nodeType: { node },
    iconPath,
    name,
  } = props;
  
  return (
    <div className={styles['custom-calcu-node']}>
      {node && (
        <Tooltip title={name}>
          <div className={styles['custom-calcu-node-label']}>{name}</div>
        </Tooltip>
      )}
      <div className={styles['custom-calcu-node-svg']}>
        <CpnShape iconPath={iconPath} />
      </div>
    </div>
  );
};

export default BaseNode;
