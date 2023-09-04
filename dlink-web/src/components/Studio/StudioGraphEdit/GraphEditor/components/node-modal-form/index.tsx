import React, { useEffect } from 'react';
import { Form, Modal } from 'antd';

import { ParametersData } from '../../views/home/cpns/left-editor';
import NodeForm from './node-form';
import CpnShape from '../cpn-shape';
import { Node } from '@antv/x6';

interface FormContextValue {
  resetForm: () => void;
}
const NORMAL_MODAL_OPTIONS = {
  width: '50%',
  bodyStyle: { padding: '20px 30px 10px' },
  destroyOnClose: true,
  maskClosable: false,
};

type NodeModalProps = {
  onCancel: (flag?: boolean) => void;
  onSubmit: (values: string) => void;
  modalVisible: boolean;
  values: ParametersData;
};

const TitleByNodeType = (selectedNode: Node) => {
  if (!selectedNode.shape) {
    return <span style={{ paddingLeft: '5px' }}>{selectedNode.shape}</span>;
  }

  if (selectedNode?.shape.includes('Mysql')) {
    return (
      <>
        <CpnShape iconPath="icon-MySQL-icon-02" />
        <span style={{ paddingLeft: '5px' }}>{selectedNode.shape}</span>
      </>
    );
  }

  if (selectedNode?.shape.includes('Operator')) {
    return (
      <>
        <CpnShape iconPath="icon-operator" />
        <span style={{ paddingLeft: '5px' }}>{selectedNode.shape}</span>
      </>
    );
  }

  return undefined;
};

const NodeModalForm: React.FC<NodeModalProps> = (props) => {
  /**
   * init form
   */
  const [form] = Form.useForm();
  /**
   * init form context
   */
  const formContext = React.useMemo<FormContextValue>(
    () => ({
      resetForm: () => form.resetFields(), // 定义 resetForm 方法
    }),
    [form],
  );

  /**
   * init props
   */
  const { onSubmit: handleSubmit, onCancel: handleModalVisible, modalVisible, values } = props;

  const initValue = values?.parametersConfig
    .filter((value) => value.flag)
    .map((value) => value.name);
  const selectedNode = values.readConfigData?.currentCell as Node;

  /**
   * when modalVisible or values changed, set form values
   */
  useEffect(() => {
    form.setFieldsValue(values);
  }, [modalVisible, values, form]);

  /**
   * handle cancel
   */
  const handleCancel = () => {
    handleModalVisible();
    formContext.resetForm();
  };
  /**
   * submit form
   */
  const submitForm = async () => {
    const fieldsValue = await form.validateFields();
    handleSubmit({
      origin: [...values.parametersConfig],
      ...fieldsValue,
      isOutputs: values.isOutputs,
      readConfigData: values.readConfigData,
    });
    handleCancel();
  };

  /**
   * render
   */
  return (
    <>
      <Modal
        {...NORMAL_MODAL_OPTIONS}
        title={selectedNode && TitleByNodeType(selectedNode)}
        open={modalVisible}
        onCancel={() => handleCancel()}
        onOk={submitForm}
      >
        <NodeForm form={form} values={values.parametersConfig} initValue={initValue} />
      </Modal>
    </>
  );
};
export default NodeModalForm;
