import React, { useEffect, useState } from 'react';
import { Node, Edge, Graph } from '@antv/x6';
import { Button, message, Modal, Tooltip } from 'antd';

import styles from "./index.less"
import { getSourceColOrCon, setSourceColumnOrConfig, getTargetConfig, setTargetConfig, getId } from '../../../utils/graph-tools-func';
import { useAppDispatch, useAppSelector } from '../../../hooks/redux-hooks';
import SetColumnConfig, { originDataType } from '../../set-column-config';
import SourceTable from "./source-table"
import TargetTable from "./target-table"
import { changeEdgeClickInfo } from '../../../store/modules/home';
import { FileAddOutlined } from '@ant-design/icons';
import CustomShape from '../../../utils/cons';
import { ProCard, ProFormField } from '@ant-design/pro-components';


const NORMAL_MODAL_OPTIONS = {
    width: "60%",
    bodyStyle: { padding: "20px 30px 10px" },
    destroyOnClose: true,
    maskClosable: false,
    footer: null
};
export type DataSourceType = {
    id: string;
    name: string;
    desc: string;
    outName: string;
    type: string;
}
type PortProFormProps = {
    edgeInfo: { edge: Edge, sourceNode: Node, targetNode: Node, sourcePortId: string, targetPortId: string }
    graph: Graph
};
export const FORM_LAYOUT_PUBLIC = {
    labelCol: { span: 5 },
    wrapperCol: { span: 15 },
};
const EdgeClick: React.FC<PortProFormProps> = (props) => {
    const { edgeClickInfo } = useAppSelector((state) => ({
        edgeClickInfo: state.home.edgeClickInfo
    }));
    const dispatch = useAppDispatch()
    const { sourceNode, sourcePortId, targetNode, targetPortId, edge } = props.edgeInfo;
    const defaultSourceData: DataSourceType[] = getSourceColOrCon(sourceNode, sourcePortId, targetNode, targetPortId, edgeClickInfo.data)
    const defaultTargetData: DataSourceType[] = getTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId)
    const [isShowConfigModal, setIsShowConfig] = useState(false)
    const [dataSource, setDataSource] = useState<DataSourceType[]>(
        () => defaultSourceData
    );
    const [dataTarget, setDataTarget] = useState<DataSourceType[]>(
        () => defaultTargetData
    );
    const getDataConfig = () => {
        let dataconfig = [];
        if (sourceNode.shape !== CustomShape.DUPLICATE_OPERATOR) {
            dataconfig = sourceNode?.getData()?.parameters.output.source
        } else {
            const id = getId(sourceNode, sourcePortId, targetNode, targetPortId)
            dataconfig = sourceNode?.getData()["config"][0][id]
        }
        return dataconfig.length ? dataconfig.map((item: any, index: number) => ({ ...item, id: (Date.now() + index).toString() })) : []
    }
    const dataConfig: originDataType[] = getDataConfig()



    const handleCancel = () => {
        setIsShowConfig(false)
    }
    const handleSourceDataChange = (newData: DataSourceType[]) => {
        // //设置column/config
        setSourceColumnOrConfig(sourceNode, sourcePortId, targetNode, targetPortId, newData)
        dispatch(changeEdgeClickInfo({
            isShowedgeClickModal: true,
            edgeInfo: { edge, sourceNode, sourcePortId, targetNode, targetPortId },
            data: edgeClickInfo.data,
        }))
        const data: DataSourceType[] = getTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId)
        setDataTarget(data)

    }
    const handleTargetDataChange = (newData: DataSourceType[]) => {
        setTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId, newData)
        setSourceColumnOrConfig(sourceNode, sourcePortId, targetNode, targetPortId, newData)
        setDataSource([...newData])
    }
    const handleOriginDataChange = (newData: originDataType[]) => {
        if (newData.length) {
            let transData: DataSourceType[] = newData.map((item, index) => ({ ...item, outName: '', id: (Date.now() + index).toString() }))
            let joinData = [...dataSource, ...transData]
            setSourceColumnOrConfig(sourceNode, sourcePortId, targetNode, targetPortId, joinData)
            setDataSource(joinData)
            dispatch(changeEdgeClickInfo({
                isShowedgeClickModal: true,
                edgeInfo: { edge, sourceNode, sourcePortId, targetNode, targetPortId },
                data: edgeClickInfo.data,
            }))
            const data: DataSourceType[] = getTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId)
            setDataTarget(data)
        }
        setIsShowConfig(false)
        message.success("同步成功")

    }
    const setColumnOrConfig = () => {
        setIsShowConfig(true)
    }
    useEffect(() => {
        handleSourceDataChange(dataSource)
    }, [])

    /**
     * construct role form
     * @constructor
     */

    const renderSourceForm = () => {
        return <>
            <SourceTable dataSource={dataSource} dataChange={handleSourceDataChange} editable={sourceNode.shape !== CustomShape.DUPLICATE_OPERATOR} />
        </>
    };
    const renderTargetForm = () => {
        return <>

            <TargetTable dataTarget={dataTarget} dataChange={handleTargetDataChange} />

        </>
    };

    /**
     * render
     */
    return <>
        <div className={styles["header"]}>
            <div className={styles["head"]}>
                <div className={styles["head-mid"]}>
                    <Tooltip title="根据元数据配置输出">
                        <Button type='primary' onClick={setColumnOrConfig} icon={<FileAddOutlined />}>配置项</Button>
                    </Tooltip>
                </div>
            </div>
        </div>
        <div className={styles["container"]}>
            <div className={styles["left"]}>

                {/* sourceForm */}
                {renderSourceForm()}
            </div>
            <div className={styles["right"]}>
                {/* targetForm */}
                {renderTargetForm()}

            </div>

        </div>
        <ProCard title="映射数据展示" headerBordered collapsible defaultCollapsed>
            <ProFormField
                ignoreFormItem
                fieldProps={{
                    style: {
                        width: "100%",
                    },
                }}
                mode="read"
                valueType="jsonCode"
                text={JSON.stringify(dataTarget)}
            />
        </ProCard>
        <Modal
            {...NORMAL_MODAL_OPTIONS}
            title={sourceNode && `${sourceNode.shape}输出配置选择`}
            open={isShowConfigModal}
            onCancel={() => handleCancel()}

        >
            <SetColumnConfig dataConfig={dataConfig} dataChange={handleOriginDataChange} />
        </Modal>
    </>
};
export default EdgeClick;
