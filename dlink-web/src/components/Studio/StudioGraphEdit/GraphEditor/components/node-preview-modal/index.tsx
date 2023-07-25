import React, { useEffect } from 'react';
import { Modal } from 'antd';
import NodePreview from "./node-preview";
import CpnShape from "../cpn-shape"
import { Node } from '@antv/x6';
const NORMAL_MODAL_OPTIONS = {
    width: "50%",
    bodyStyle: { padding: "20px 30px 10px" },
    destroyOnClose: true,
    maskClosable: false,
};

type NodeModalProps = {
    onCancel: (flag?: boolean) => void;
    onSubmit: (values: string) => void;
    modalVisible: boolean;
    values: string;
    node: Node|null,
};

const TitleByNodeType = (selectedNode: Node) => {
    if (!selectedNode.shape) {
        return <span style={{ paddingLeft: "5px" }}>{selectedNode.shape}</span>
    }

    if (selectedNode?.shape.includes("Mysql")) {
        return <>
            <CpnShape iconPath='icon-MySQL-icon-02' />
            <span style={{ paddingLeft: "5px" }}>{selectedNode.shape}</span>
        </>
    }

    if (selectedNode?.shape.includes("Operator")) {
        return <>
            <CpnShape iconPath='icon-operator' />
            <span style={{ paddingLeft: "5px" }}>{selectedNode.shape}</span>
        </>
    }

    return undefined
}

const NodeModalPreview: React.FC<NodeModalProps> = (props) => {
    /**
     * init props
     */
    const {
        onSubmit: handleSubmit,
        onCancel: handleModalVisible,
        modalVisible,
        values,
        node
    } = props;


    const selectedNode = node

    /**
     * when modalVisible or values changed, set form values
     */
    useEffect(() => {
    }, [modalVisible, values]);

    /**
     * handle cancel
     */
    const handleCancel = () => {
        handleModalVisible();
    }
    /**
     * submit form
     */
    const submitForm = async () => {
        // handleSubmit();
        handleCancel();
    };

    /**
     * render
     */
    return <>
        <Modal
            {...NORMAL_MODAL_OPTIONS}
            title={selectedNode && TitleByNodeType(selectedNode)}
            open={modalVisible}
            onCancel={() => handleCancel()}
            onOk={submitForm}
        >
            <NodePreview values={values} />
        </Modal>
    </>

};
export default NodeModalPreview;
