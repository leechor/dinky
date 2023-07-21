import { Tooltip } from 'antd';
import { WarningOutlined } from '@ant-design/icons';
import styles from './index.less';
import CpnShape, { NodeType } from './cpn-shape';


const BaseNode = (props: { nodeType: NodeType; iconPath: string, name: string }) => {
  const {
    nodeType: { node },
    iconPath,
    name,
  } = props;
  console.log(node.prop("isError"),);

  const handleWarning = () => {
    console.log(">>>>>");

  }
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
      {node.prop().isError && <div className={styles["custom-calcu-node-waring"]} onClick={handleWarning}>
        <Tooltip title={name} placement="bottom">
          <WarningOutlined />
        </Tooltip>

      </div>}

    </div>
  );
};

export default BaseNode;
