import { Tooltip } from 'antd';
import { WarningOutlined, CloseOutlined } from '@ant-design/icons';
import styles from './index.less';
import CpnShape, { NodeType } from './cpn-shape';
import { useEffect, useRef } from 'react';
import { useAppDispatch, useAppSelector } from '../hooks/redux-hooks';
import { changeStencilMenuInfo } from '../store/modules/home';

const BaseNode = (props: { nodeType: NodeType; iconPath: string, name: string }) => {
  const dispatch = useAppDispatch();
  const { postionToGroup } = useAppSelector((state) => ({
    postionToGroup: state.home.postionToGroup

  }))
  const {
    nodeType: { node },
    iconPath,
    name,
  } = props;
  const handleWarning = () => {

  }
  const timerRef = useRef<any>([])
  useEffect(() => {
    if (node.shape.includes("custom-node")) {
      const nodeEle = document.getElementsByClassName('custom-calcu-node')
      for (let ele of Array.from(nodeEle)) {
        let timer = ele?.addEventListener("contextmenu", (event: any) => {
          const x = ele.getBoundingClientRect().left + document.documentElement.scrollLeft;
          const y = ele.getBoundingClientRect().top + document.documentElement.scrollTop
          node.prop().name && (postionToGroup.isFullScreen ? dispatch(changeStencilMenuInfo({ x, y, showStencilMenu: true, node })) :
            dispatch(changeStencilMenuInfo({ x: x - postionToGroup.x, y: y - postionToGroup.y, showStencilMenu: true, node })))
        })
        timerRef.current.push(timer)
      }
    }

    return () => {
      timerRef.current.forEach((timer: any) => {
        removeEventListener("contextmenu", timer)
      })
    }

  }, [])
  const getErrorMessage = () => {
    const { sqlErrorMsg, portInformation, edge } = node.prop().errorMsg;

    return <>
      {sqlErrorMsg !== null && < div className={styles["error-msg"]} ><div className={styles["error-msg-title"]}><CloseOutlined style={{ color: "red" }} /><span  >节点错误：</span></div><div className={styles["error-msg-body"]} >{sqlErrorMsg}</div></div>
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
      {!node.prop().isStencil && <div className={styles['custom-calcu-node-head']}><div style={{ whiteSpace: 'nowrap', textAlign: 'center' }}>{node.prop().name ? node.prop().name : name}</div></div>}
      <div className={`${styles['custom-calcu-node']} custom-calcu-node`}>
        {(node && node.prop().isStencil) && (
          <Tooltip title={name ? name : node.prop().name}>
            <div className={styles['custom-calcu-node-label']}>{name ? name : node.prop().name}</div>
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
