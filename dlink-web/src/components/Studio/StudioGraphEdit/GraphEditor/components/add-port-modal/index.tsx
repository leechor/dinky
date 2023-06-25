import React,{ memo, FC } from "react";
import { Form, Modal } from 'antd';
import { Cell } from "@antv/x6";
const NORMAL_MODAL_OPTIONS = {
    width: "50%",
    bodyStyle: { padding: "20px 30px 10px" },
    destroyOnClose: true,
    maskClosable: false,
};
type PortAddProps = {
    onCancel: (flag?: boolean) => void;
    onSubmit: (values: string) => void;
    modalVisible: boolean;
    values: Cell;
};
interface FormContextValue {
    resetForm: () => void;
}

const AddModalPort: FC<PortAddProps> = memo((props) => {
    const {
        onSubmit: handleSubmit,
        onCancel: handleModalVisible,
        modalVisible,
        values,
    } = props;
    const [form] = Form.useForm();
    /**
     * init form context
     */
    const formContext = React.useMemo<FormContextValue>(() => ({
        resetForm: () => form.resetFields(), // 定义 resetForm 方法
    }), [form]);
    const handleCancel = () => {
        handleModalVisible();
        formContext.resetForm();
    }

    /**
     * submit form
     */
    const submitForm = async () => {
        const fieldsValue = await form.validateFields();
        await handleSubmit({ ...fieldsValue,});
        await handleCancel();
    };

    return <>
        <Modal
            {...NORMAL_MODAL_OPTIONS}
            title={values && values.shape}
            open={modalVisible}
            onCancel={() => handleCancel()}
            onOk={() => submitForm()}
        >
            <div>test</div>
        </Modal></>
})
export default AddModalPort