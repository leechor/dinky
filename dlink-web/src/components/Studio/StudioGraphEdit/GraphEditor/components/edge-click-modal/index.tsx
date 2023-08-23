import { memo, FC } from "react";
import { Modal } from 'antd';
import { Node, Edge, Graph } from "@antv/x6";
import EdgeClick from "./edge-click";
import { useAppSelector } from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';

const NORMAL_MODAL_OPTIONS = {
    width: "60%",
    bodyStyle: { padding: "20px 30px 10px" },
    destroyOnClose: true,
    maskClosable: false,
    footer: null,

};
type EdgeClickProps = {
    onCancel: (flag?: boolean) => void;
    onSubmit: (values: string) => void;
    edgeInfo: { edge: Edge, sourceNode: Node, targetNode: Node, sourcePortId: string, targetPortId: string } | null;
    graph: Graph
};
const EdgeClickModal: FC<EdgeClickProps> = memo((props) => {
    const { edgeClickInfo } = useAppSelector((state) => ({ edgeClickInfo: state.home.edgeClickInfo }));
    const {
        onSubmit: handleSubmit,
        onCancel: handleModalVisible,
        edgeInfo,
        graph,
    } = props;

    /**
     * init form context
     */

    const handleCancel = () => {
        handleModalVisible();
    }

    /**
     * submit form
     */
    const submitForm = async () => {

        await handleSubmit("");
        await handleCancel();
    };

    return <>
        <Modal
            {...NORMAL_MODAL_OPTIONS}
           
            title={edgeInfo && `${edgeInfo.sourceNode.shape}-${edgeInfo.targetNode.shape}`}
            open={edgeClickInfo.isShowedgeClickModal}
            onCancel={() => handleCancel()}
            onOk={() => submitForm()}
        >
            <EdgeClick edgeInfo={edgeInfo!} graph={graph} />
        </Modal></>
})
export default EdgeClickModal