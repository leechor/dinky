import { Tooltip } from 'antd';
import { WarningOutlined, CloseOutlined } from '@ant-design/icons';
import styles from './index.less';
import CpnShape, { NodeType } from './cpn-shape';
import { absolute } from '@antv/x6/lib/registry/port-layout/absolute';


const BaseNode = (props: { nodeType: NodeType; iconPath: string, name: string }) => {
  const {
    nodeType: { node },
    iconPath,
    name,
  } = props;

  const handleWarning = () => {
    console.log(">>>>>");

  }

  const getErrorMessage = () => {
    const { sqlErrorMsg, portInformation, edge } = node.prop().errorMsg;
    return <>
      {sqlErrorMsg && < div className={styles["error-msg"]} ><div className={styles["error-msg-title"]}><CloseOutlined style={{ color: "red" }} /><span  >节点错误：</span></div><div className={styles["error-msg-body"]} >{sqlErrorMsg}</div></div>
      }
      {edge && <div className={styles["error-msg"]}><div className={styles["error-msg-title"]}><CloseOutlined style={{ color: "red" }} /><span>边错误:</span></div><div className={styles["error-msg-body"]}>{edge.map((e: string) => <div key={e} >{e}</div>)}</div></div>}
      {portInformation && <div className={styles["error-msg"]}>
        <div className={styles["error-msg-title"]}><CloseOutlined style={{ color: "red" }} /><span>链接桩错误:</span></div>
        <div className={styles["error-msg-body"]}>{portInformation && Object.keys(portInformation).map((port) => {
          return (<div>
            <div > <CloseOutlined style={{ color: "red" }} />{port} </div>
            <div>{portInformation[port] && portInformation[port].map((info: string, key: number) => { return (<div key={key} style={{ textIndent: "1em" }}><span>{key + 1}: </span><span>{info}</span></div>) })}
            </div></div>)
        })}
        </div>
      </div>}
    </>
  }
  return (
    <>
      {!node.prop().isStencil && <div className={styles['custom-calcu-node-head']}><div style={{ whiteSpace: 'nowrap', textAlign: 'center' }}>{name}</div></div>}
      <div className={styles['custom-calcu-node']}>
        {(node&&node.prop().isStencil) && (
          <Tooltip title={name}>
            <div className={styles['custom-calcu-node-label']}>{name}</div>
          </Tooltip>
        )}
        <div className={styles['custom-calcu-node-svg']}>
          <CpnShape iconPath={iconPath} />
        </div>
        {node.prop().isError && (<div className={styles["custom-calcu-node-waring"]} onClick={handleWarning}>
          <Tooltip title={getErrorMessage()} placement="bottom" overlayClassName='error-msg'>
            <WarningOutlined />
          </Tooltip>
        </div>)}

      </div></>
  );
};

export default BaseNode;
