import React, { useRef, useState } from 'react';
import type { ActionType, ProColumns } from '@ant-design/pro-components'
import { EditableProTable, ProCard, ProFormField } from '@ant-design/pro-components';
import { Button } from "antd"
import { Node, Edge, Graph } from '@antv/x6';



import styles from "./index.less"
import { getSourceColOrCon, setSourceColumnOrConfig, getTargetConfig, setTargetConfig } from '../../../utils/graph-tools-func';
import { useAppDispatch, useAppSelector } from '../../../hooks/redux-hooks';
import { changeEdgeClickInfo } from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home'

type PortProFormProps = {
    edgeInfo: { edge: Edge, sourceNode: Node, targetNode: Node, sourcePortId: string, targetPortId: string }
    graph: Graph
};

export type DataSourceType = {
    id: React.Key;
    name: string;
    decs: string;
    outName: string;
    flag: boolean;
    type: string;
    children?: DataSourceType[];
};


const optionsType = ["CHAR", "VARCHAR", "STRING",
    "BOOLEAN", "BINARY", "VARBINARY", "BYTES",
    "DECIMAL", "TINYINT", "SMALLINT", "INT", "BIGINT",
    "FLOAT", "DOUBLE", "DATE", "TIME", "TIMESTAMP", "TIMESTAMP(3)",
    "TIMESTAMP_LTZ(3)", "INTERVAL", "ARRAY", "MULTISET", "MAP", "ROW", "RAW"].map(type => ({ label: type, value: type }))

export const FORM_LAYOUT_PUBLIC = {
    labelCol: { span: 5 },
    wrapperCol: { span: 15 },
};
const EdgeClick: React.FC<PortProFormProps> = (props) => {
    const dispatch = useAppDispatch()
    const actionRef = useRef<ActionType>()
    const { operatorParameters }
        = useAppSelector((state) => ({
            operatorParameters: state.home.operatorParameters,
        }));

    const { sourceNode, sourcePortId, targetNode, targetPortId, edge } = props.edgeInfo;

    const defaultSourceData: DataSourceType[] = getSourceColOrCon(sourceNode, sourcePortId, targetNode, targetPortId)
    const defaultTargetData: DataSourceType[] = getTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId)

    const [editableKeys, setEditableRowKeys] = useState<React.Key[]>(defaultSourceData.map((item) => item.id)
    );
    const [editableKeysTarget, setEditableRowKeysTarget] = useState<React.Key[]>(defaultTargetData.map((item) => item.id)
    );

    const [dataSource, setDataSource] = useState<DataSourceType[]>(
        () => defaultSourceData
    );
    const [dataTarget, setDataTarget] = useState<DataSourceType[]>(
        () => defaultTargetData
    );

    const vallidateType = (rule: any, val: string, callback: any) => {
        if (!val) {
            callback("请选择类型")
        } else {
            callback()
        }
    }
    const columns: ProColumns<DataSourceType>[] = [
        {
            title: "字段名称",
            dataIndex: "name",
            width: 100,
            formItemProps: {
                rules: [
                    {
                        required: true,
                        whitespace: true,
                        message: "此项必填",
                    },
                ],
            },
        },
        {
            title: "类型",
            width: 110,
            key: "type",
            dataIndex: "type",
            valueType: "select",
            fieldProps: {
                options: optionsType,
            },
            formItemProps: {
                rules: [
                    { validator: (rule, val, callback) => { vallidateType(rule, val, callback) } }],
            },

        },
        {
            title: "是否勾选",
            dataIndex: "flag",
            width: 100,
            valueType: "switch",
            fieldProps: {
                options: [
                    { label: "是", value: true },
                    { label: "否", value: false },
                ]
            }
        },
        {
            title: "操作",
            valueType: "option",
            width: 40,
            render: () => {
                return null;
            },
        },
        {
            title: "别名",
            width: 100,
            key: "outName",
            dataIndex: "outName",
        },


        {
            title: "注释",
            width: 100,
            dataIndex: "decs",
        },


    ];

    const setColumnOrConfig = () => {
        //设置column/config
        setSourceColumnOrConfig(sourceNode, sourcePortId, targetNode, targetPortId, dataSource)
        dispatch(changeEdgeClickInfo({
            isShowedgeClickModal: true,
            edgeInfo: { edge, sourceNode, sourcePortId, targetNode, targetPortId },
            data: null,
        }))
        const data: DataSourceType[] = getTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId)
        setDataTarget(data)
    }
    const setTargetColumnOrConfig = () => {
        //设置column/config
        setTargetConfig(sourceNode, sourcePortId, targetNode, targetPortId, dataTarget, props.graph)
    }
    /**
     * construct role form
     * @constructor
     */
    const renderSourceForm = () => {
        return <>
            <EditableProTable<DataSourceType>
                headerTitle={sourceNode.shape}
                columns={columns}
                rowKey="id"
                value={dataSource}
                onChange={setDataSource}
                recordCreatorProps={{
                    newRecordType: "dataSource",
                    record: () => ({
                        id: Date.now(),
                        name: "",
                        decs: "",
                        outName: "",
                        flag: true,
                        type: ""
                    }),
                }}
                toolBarRender={() => {
                    return [
                        <Button
                            type="primary"
                            key="save"
                            onClick={() => {
                                // dataSource 就是当前数据，可以调用 api 将其保存
                                setColumnOrConfig()
                            }}
                        >
                            保存数据
                        </Button>,
                    ];
                }}
                editable={{
                    type: "multiple",
                    editableKeys,
                    actionRender: (row, config, defaultDoms) => {
                        return [defaultDoms.delete];
                    },
                    onValuesChange: (record, recordList) => {
                        setDataSource(recordList);
                    },
                    onChange: setEditableRowKeys,
                }}
            />
            <ProCard title="数据展示" headerBordered collapsible defaultCollapsed>
                <ProFormField
                    ignoreFormItem
                    fieldProps={{
                        style: {
                            width: "100%",
                        },
                    }}
                    mode="read"
                    valueType="jsonCode"
                    text={JSON.stringify(dataSource)}
                />
            </ProCard>
        </>
    };
    const renderTargetForm = () => {
        return <>
            <EditableProTable<DataSourceType>
                headerTitle={targetNode.shape}
                columns={columns}
                rowKey="id"
                actionRef={actionRef}
                value={dataTarget}
                onChange={setDataTarget}
                recordCreatorProps={{
                    newRecordType: "dataSource",
                    record: () => ({
                        id: Date.now(),
                        name: "",
                        decs: "",
                        outName: "",
                        flag: true,
                        type: ""

                    }),
                }}
                toolBarRender={() => {
                    return [
                        <Button
                            type="primary"
                            key="save"
                            onClick={() => {
                                // dataSource 就是当前数据，可以调用 api 将其保存
                                setTargetColumnOrConfig()
                            }}
                        >
                            保存数据
                        </Button>,
                    ];
                }}
                editable={{
                    type: "multiple",
                    editableKeys: editableKeysTarget,
                    actionRender: (row, config, defaultDoms) => {
                        return [defaultDoms.delete];
                    },
                    onValuesChange: (record, recordList) => {
                        setDataTarget(recordList);
                    },
                    onChange: setEditableRowKeysTarget,
                }}
            />
            <ProCard title="数据展示" headerBordered collapsible defaultCollapsed>
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
        </>
    };

    /**
     * render
     */
    return <>
        <div className={styles["header"]}>
            <div>source</div>
            <div>target</div>
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
        <div className={styles["footer"]}>footer</div>
    </>
};
export default EdgeClick;
