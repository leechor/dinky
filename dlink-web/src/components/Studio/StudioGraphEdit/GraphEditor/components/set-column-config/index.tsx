import React, { useState } from 'react';
import type { ProColumns } from '@ant-design/pro-components'
import { EditableProTable, ProCard, ProFormField } from '@ant-design/pro-components';
import { Button } from "antd"
import styles from "./index.less"

type PortProFormProps = {
    dataConfig: originDataType[],
    dataChange: (newData: originDataType[]) => void
};

export type originDataType = {
    id: React.Key;
    name: string;
    desc: string;
    type: string;
};



export const FORM_LAYOUT_PUBLIC = {
    labelCol: { span: 5 },
    wrapperCol: { span: 15 },
};
const SetColumnConfig: React.FC<PortProFormProps> = (props) => {



    const [mySelectedRowKeys, setMySelectedRowKeys] = useState<React.Key[]>([])
    const [currentSelectedRows, setCurrentSelectedRows] = useState<originDataType[]>([])
    const dataSource = props.dataConfig;
    const dataChange = props.dataChange
    


    const onChange = (selectedRowKeys: React.Key[], selectedRows: originDataType[]) => {
        setMySelectedRowKeys(selectedRowKeys)
        setCurrentSelectedRows(selectedRows)
    }

    const columns: ProColumns<originDataType>[] = [
        {
            title: "字段名称",
            dataIndex: "name",
            tooltip: "原始输出配置字段名称",
        },
        {
            title: "类型",
            key: "type",
            dataIndex: "type",
            tooltip: "原始输出配置类型",

        },
        {
            title: "注释",
            dataIndex: "desc",
            tooltip: "原始输出配置注释",
        },


    ];

    const setColumnOrConfig = () => {
        dataChange(currentSelectedRows)
    }

    /**
     * construct role form
     * @constructor
     */
    const renderSourceForm = () => {
        return <>
            <EditableProTable<originDataType>
                size='small'
                pagination={{ position: ["bottomRight"], showSizeChanger: true }}
                rowSelection={{
                    type: "checkbox",
                    selectedRowKeys: mySelectedRowKeys,
                    onChange,
                }}
                tableAlertRender={() => {
                    return `已选择 ${mySelectedRowKeys.length} 项`
                }}
                columns={columns}
                rowKey="id"
                value={dataSource}
                recordCreatorProps={false}
                toolBarRender={() => {
                    return [
                        <Button
                            type="primary"
                            key="save"
                            onClick={() => {
                                setColumnOrConfig()
                            }}
                        >
                            确定选中
                        </Button>,
                    ];
                }}

            />

        </>
    };


    /**
     * render
     */
    return <>
        <div className={styles["container"]}>{renderSourceForm()}</div>
        <div className={styles["footer"]}>
            <ProCard title="原始输出数据展示" headerBordered collapsible defaultCollapsed>
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
            </ProCard></div>
    </>
};
export default SetColumnConfig;
