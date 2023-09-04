import { memo, FC, useRef } from 'react';
import { Modal } from 'antd';
import { Node } from '@antv/x6';
import DataSource from './data-source';
import { TitleByNodeType } from '../node-preview-modal';
const NORMAL_MODAL_OPTIONS = {
  width: '95%',
  bodyStyle: { padding: '20px 30px 10px' },
  destroyOnClose: true,
  maskClosable: false,
};
type DataSourceProps = {
  onCancel: (flag?: boolean) => void;
  onSubmit: (values: any) => void;
  modalVisible: boolean;
  values: any;
  node: Node;
  type: string;
};
const DataSourceModal: FC<DataSourceProps> = memo((props) => {
  const {
    onSubmit: handleSubmit,
    onCancel: handleModalVisible,
    modalVisible,
    values,
    node,
    type,
  } = props;
  const dataSourceRef = useRef<any>();
  const selectedNode = node;
  const handleCancel = () => {
    handleModalVisible();
  };

  /**
   * submit form
   */
  const submitForm = async () => {
    await handleSubmit(dataSourceRef.current);
    await handleCancel();
  };
  const getDBConfigInfo = (data: any) => {
    const { id, tableName, value } = data;
    const tableItem = values.find((tableItem: any) => tableItem.id === id);
    dataSourceRef.current = { tableItem, value, type, tableName };
  };
  return (
    <>
      <Modal
        {...NORMAL_MODAL_OPTIONS}
        title={selectedNode && TitleByNodeType(selectedNode)}
        open={modalVisible}
        onCancel={() => handleCancel()}
        onOk={() => submitForm()}
      >
        <DataSource values={values} modalVisible={modalVisible} getDBConfigInfo={getDBConfigInfo} />
      </Modal>
    </>
  );
});
export default DataSourceModal;
