import React, { useEffect, useState } from 'react';
import { Button, Form, Table, Tooltip } from 'antd';

import {
  FormOutlined,
  FileOutlined,
  CloseCircleOutlined,
  QuestionCircleOutlined,
  DeleteOutlined,
} from '@ant-design/icons';
import { DataSourceType } from '.';
import { getInputNode, getInputType } from './target-table';

interface SourceTableProps {
  dataSource: DataSourceType[];
  dataChange: (newData: DataSourceType[]) => void;
  editable: boolean;
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
          rules={[
            () => ({
              validator(rule: any, value, callback) {
                if (rule.fullField === 'outName') {
                  callback();
                  return;
                }
                if (rule.fullField === 'desc') {
                  callback();
                  return;
                }
                if (!value) {
                  callback('该字段不能为空');
                } else {
                  callback();
                }
              },
            }),
          ]}
        >
          {getInputNode(inputType)}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  );
};

const SourceTable: React.FC<SourceTableProps> = (props) => {
  const [form] = Form.useForm();
  const [data, setData] = useState([...props.dataSource]);
  const { dataChange, editable } = props;
  const [editingKey, setEditingKey] = useState('');

  const isEditing = (record: DataSourceType) => record.id === editingKey;

  const edit = (record: Partial<DataSourceType> & { id: React.Key }) => {
    form.setFieldsValue({ name: '', type: '', outName: '', desc: '', ...record });
    setEditingKey(record.id);
  };

  const cancel = () => {
    setEditingKey('');
  };
  const deleteItem = (record: Partial<DataSourceType> & { id: React.Key }) => {
    const newData = [...data];
    const index = newData.findIndex((item) => record.id === item.id);
    newData.splice(index, 1);
    setData(newData);
    dataChange(newData);
    setEditingKey('');
  };
  const save = async (key: React.Key) => {
    try {
      const row = (await form.validateFields()) as DataSourceType;

      const newData = [...data];
      const index = newData.findIndex((item) => key === item.id);
      if (index > -1) {
        const item = newData[index];
        newData.splice(index, 1, {
          ...item,
          ...row,
        });
        setData(newData);
        setEditingKey('');
      } else {
        newData.push(row);
        setData(newData);
        setEditingKey('');
      }

      dataChange([...newData]);
    } catch (error) {
      return;
    }
  };

  const columns = [
    {
      title: () => (
        <span>
          {'字段名称'}
          <Tooltip title="映射字段名称">
            <QuestionCircleOutlined />
          </Tooltip>
        </span>
      ),
      dataIndex: 'name',
      width: '20%',
      editable,
    },
    {
      title: () => (
        <span>
          {'类型'}
          <Tooltip title="映射类型">
            <QuestionCircleOutlined />
          </Tooltip>
        </span>
      ),
      dataIndex: 'type',
      width: '15%',
      editable,
    },
    {
      title: () => (
        <span>
          {'别名'}
          <Tooltip title="映射别名">
            <QuestionCircleOutlined />
          </Tooltip>
        </span>
      ),
      dataIndex: 'outName',
      width: '15%',
      editable,
    },
    {
      title: () => (
        <span>
          {'注释'}
          <Tooltip title="映射注释">
            <QuestionCircleOutlined />
          </Tooltip>
        </span>
      ),
      dataIndex: 'desc',
      width: '15%',
      editable,
    },
    {
      title: '操作',
      width: '15%',
      dataIndex: 'operation',
      render: (_: any, record: DataSourceType) => {
        if (editable) {
          const editable = isEditing(record);
          return editable ? (
            <>
              <Button
                type="link"
                icon={<FileOutlined />}
                onClick={() => save(record.id)}
                style={{ color: '#52c41a' }}
              />
              <Button type="link" danger icon={<CloseCircleOutlined />} onClick={cancel} />
            </>
          ) : (
            <>
              <Button
                type="link"
                icon={<FormOutlined />}
                disabled={editingKey !== ''}
                onClick={() => edit(record)}
              />
              <Button
                type="link"
                icon={<DeleteOutlined />}
                danger
                onClick={() => deleteItem(record)}
              />
            </>
          );
        } else {
          return (
            <>
              <Button type="link" icon={<FormOutlined />} disabled onClick={() => edit(record)} />
              <Button
                type="link"
                icon={<DeleteOutlined />}
                danger
                onClick={() => deleteItem(record)}
              />
            </>
          );
        }
      },
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
  useEffect(() => {
    setData(props.dataSource);
  }, [props.dataSource]);
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
          rowClassName="editable-row"
          pagination={false}
        />
      </Form>
    </>
  );
};

export default SourceTable;
