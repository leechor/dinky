import { Tooltip } from 'antd';
import styles from './index.less';
import CpnShape, { NodeType } from './cpn-shape';

const GroupProcess = (props: any) => {

    const node = props.node;
    debugger
    return (
        <div className={styles['custom-calcu-node-process']}>
            {node && (
                <Tooltip title={"subProcess"}>
                    <div className={styles['custom-calcu-node-label']}></div>
                </Tooltip>
            )}
            <div className={styles['custom-calcu-node-process-top']}>
                <div className={styles['custom-calcu-node-svg-top']}>
                    <CpnShape iconPath={"icon-liuchengtu"} />
                </div>
            </div>
            <div className={styles['custom-calcu-node-process-bottom']}></div>
            {/* <div className={styles['custom-calcu-node-svg']}>
      <CpnShape iconPath={iconPath} />
    </div> */}
        </div>
    );
};

export default GroupProcess;
