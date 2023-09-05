import React, { useState } from 'react';
import type { ProColumns } from '@ant-design/pro-components';
import { EditableProTable, ProCard, ProFormField } from '@ant-design/pro-components';
import { Button } from 'antd';
import styles from './index.less';
import { l } from '@/utils/intl';

type PortProFormProps = {
  dataConfig: originDataType[];
  dataChange: (newData: originDataType[]) => void;
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
  const [mySelectedRowKeys, setMySelectedRowKeys] = useState<React.Key[]>([]);
  const [currentSelectedRows, setCurrentSelectedRows] = useState<originDataType[]>([]);
  const dataSource = props.dataConfig;
  const dataChange = props.dataChange;

  const onChange = (selectedRowKeys: React.Key[], selectedRows: originDataType[]) => {
    setMySelectedRowKeys(selectedRowKeys);
    setCurrentSelectedRows(selectedRows);
  };

  const columns: ProColumns<originDataType>[] = [
    {
      title: l('graph.edgeclick.field.name'),
      dataIndex: 'name',
      tooltip: l('graph.setcolumn.origin.name'),
    },
    {
      title: l('graph.edgeclick.type'),
      key: 'type',
      dataIndex: 'type',
      tooltip: l("graph.setcolumn.origin.type"),
    },
    {
      title: l('graph.edgeclick.desc'),
      dataIndex: 'desc',
      tooltip: l("graph.setcolumn.origin.description"),
    },
  ];

  const setColumnOrConfig = () => {
    dataChange(currentSelectedRows);
  };

  /**
   * construct role form
   * @constructor
   */
  const renderSourceForm = () => {
    return (
      <>
        <EditableProTable<originDataType>
          size="small"
          rowSelection={{
            type: 'checkbox',
            selectedRowKeys: mySelectedRowKeys,
            onChange,
          }}
          scroll={{ y: 500 }}
          tableAlertRender={() => {
            return l('graph.setcolumn.selected.items', "", { length: mySelectedRowKeys.length });
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
                  setColumnOrConfig();
                }}
              >
                {l("graph.setcolumn.confirm.selection")}
              </Button>,
            ];
          }}
        />
      </>
    );
  };

  /**
   * render
   */
  return (
    <>
      <div className={styles['container']}>{renderSourceForm()}</div>
      <div className={styles['footer']}>
        <ProCard title={l("graph.setcolumn.origin.output.data.display")} headerBordered collapsible defaultCollapsed>
          <ProFormField
            ignoreFormItem
            fieldProps={{
              style: {
                width: '100%',
              },
            }}
            mode="read"
            valueType="jsonCode"
            text={JSON.stringify(dataSource)}
          />
        </ProCard>
      </div>
    </>
  );
};
export default SetColumnConfig;
