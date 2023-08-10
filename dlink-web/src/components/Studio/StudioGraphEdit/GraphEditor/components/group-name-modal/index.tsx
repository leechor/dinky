import React, { memo, FC } from "react";
import { Form, Modal } from 'antd';
import { Node } from "@antv/x6";
import PortForm from "./group-name";
import { useAppSelector } from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';

const NORMAL_MODAL_OPTIONS = {
    width: "50%",
    bodyStyle: { padding: "20px 30px 10px" },
    destroyOnClose: true,
    maskClosable: false,
};
type GroupNameProps = {
    onCancel: (flag?: boolean) => void;
    onSubmit: (values: string) => void;
    values: Node;
};
interface FormContextValue {
    resetForm: () => void;
}

const GroupName: FC<GroupNameProps> = memo((props) => {
    const { groupNameInfo } = useAppSelector((state) => ({ groupNameInfo: state.home.groupNameInfo }));
    const {
        onSubmit: handleSubmit,
        onCancel: handleModalVisible,
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
        console.log(fieldsValue);

        await handleSubmit({ ...fieldsValue, node: values, type: groupNameInfo.type });
        await handleCancel();
    };

    return <>
        <Modal
            {...NORMAL_MODAL_OPTIONS}
            title={values && values.shape}
            open={groupNameInfo.isShowGroupNameModal}
            onCancel={() => handleCancel()}
            onOk={() => submitForm()}
        >
            <PortForm form={form} values={values} />
        </Modal></>
})
export default GroupName