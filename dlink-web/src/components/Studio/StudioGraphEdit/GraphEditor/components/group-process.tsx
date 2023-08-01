import { Menu, Tooltip } from 'antd';
import styles from './index.less';
import CpnShape, { NodeType } from './cpn-shape';

import { useEffect, useRef } from 'react';
import { useAppDispatch, useAppSelector, } from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { changeStencilMenuInfo } from '../store/modules/home';




const GroupProcess = (props: any) => {
    const timerRef = useRef<any>([])
    const dispatch = useAppDispatch();
    const node = props.node;

    const { postionToGroup } = useAppSelector((state) => ({
        postionToGroup: state.home.postionToGroup

    }))
    useEffect(() => {
        const groupProcessEle = document.getElementsByClassName('custom-calcu-node-process')

        for (let ele of Array.from(groupProcessEle)) {
            let timer = ele?.addEventListener("contextmenu", (event: any) => {
                const x = ele.getBoundingClientRect().left + document.documentElement.scrollLeft;
                const y = ele.getBoundingClientRect().top + document.documentElement.scrollTop
                node.prop().name && (postionToGroup.isFullScreen ? dispatch(changeStencilMenuInfo({ x, y, showStencilMenu: true, node })) :
                    dispatch(changeStencilMenuInfo({ x: x - postionToGroup.x, y: y - postionToGroup.y, showStencilMenu: true, node })))
            })
            timerRef.current.push(timer)
        }
        return () => {
            timerRef.current.forEach((timer: any) => {
                removeEventListener("contextmenu", timer)
            })
        }

    }, [])

    return (
        <>
            {!node.prop().isStencil && <div className={styles['custom-calcu-node-head']}><div style={{ whiteSpace: 'nowrap', textAlign: 'center' }}>{node.prop().name ? node.prop().name : "subProcess"}</div></div>}
            <div className='custom-calcu-node-process'>
                {node && node.prop().isStencil && (
                    <Tooltip title={"subProcess"}>
                        <div className={styles['custom-calcu-node-label']}>{node.prop().name ? node.prop().name : 'subProcess'}</div>
                    </Tooltip>
                )}
                <div className='custom-calcu-node-process-top'>
                    <div className='custom-calcu-node-svg-top'>
                        <CpnShape iconPath={"icon-liuchengtu"} />
                    </div>
                </div>
                <div className='custom-calcu-node-process-bottom'></div>

            </div>

        </>
    );

};

export default GroupProcess;
