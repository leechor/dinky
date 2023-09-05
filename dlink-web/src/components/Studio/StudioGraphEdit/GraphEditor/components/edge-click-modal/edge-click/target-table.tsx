import React, { useEffect, useState } from 'react';
import { Form, Input, Select, Table, Tooltip } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';
import { DataSourceType } from '.';
import TextArea from 'antd/lib/input/TextArea';
import { options } from '../../../utils/cons';
import { l } from '@/utils/intl';

interface TargetTableProps {
  dataTarget: DataSourceType[];
  dataChange: (newData: DataSourceType[]) => void;
}

interface EditableCellProps extends React.HTMLAttributes<HTMLElement> {
  editing: boolean;
  dataIndex: string;
  title: any;
  inputType: 'select' | 'text';
  record: DataSourceType;
  index: number;
  children: React.ReactNode;
}
const optionsType = options.map((type) => ({ label: type, value: type }));

export const getInputNode = (inputType: string) => {
  switch (inputType) {
    case 'select':
      return <Select options={optionsType} />;
    case 'textArea':
      return <TextArea />;
    default:
      return <Input />;
  }
};
export const getInputType = (dataIndex: string) => {
  switch (dataIndex) {
    case 'type':
      return 'select';
    case 'desc':
      return 'textArea';
    default:
      return 'text';
  }
};
const EditableCell: React.FC<EditableCellProps> = ({
  editing,
  dataIndex,
  title,
  inputType,
  record,
  index,
  children,
  ...restProps
}) => {
  return (
    <td {...restProps}>
      {editing ? (
        <Form.Item
          name={dataIndex}
          style={{ margin: 0 }}
          rules={[{ required: true, message: `Please Input ${title}!` }]}
        >
          {getInputNode(inputType)}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  );
};

const TargetTable: React.FC<TargetTableProps> = (props) => {
  const [form] = Form.useForm();
  const [data, setData] = useState([...props.dataTarget]);
  const [editingKey] = useState('');

  const isEditing = (record: DataSourceType) => record.id === editingKey;

  useEffect(() => {
    setData([...props.dataTarget]);
  }, [props.dataTarget]);

  const columns = [
    {
      title: () => (
        <span>
          {l("graph.edgeclick.actual.output.name")}
          <Tooltip title={l("graph.edgeclick.mapping.actual.output.name")}>
            <QuestionCircleOutlined />
          </Tooltip>
        </span>
      ),
      dataIndex: 'name',
      width: '20%',
      editable: false,
      className: 'target-table-title',
    },
  ];

  const mergedColumns: any = columns.map((col) => {
    if (!col.editable) {
      return { ...col, align: 'center' };
    }
    return {
      ...col,
      align: 'center',
      onCell: (record: DataSourceType) => ({
        record,
        inputType: getInputType(col.dataIndex),
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    };
  });
  return (
    <>
      <Form form={form} component={false}>
        <Table
          size="small"
          tableLayout="fixed"
          components={{ body: { cell: EditableCell } }}
          bordered
          dataSource={data}
          columns={mergedColumns}
          rowClassName="editable-row-target"
          pagination={false}
        />
      </Form>
    </>
  );
};

export default TargetTable;
