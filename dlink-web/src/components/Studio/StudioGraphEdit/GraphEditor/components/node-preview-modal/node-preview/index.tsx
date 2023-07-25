import React from 'react';
import CodeShow from "@/components/Common/CodeShow";
type NodeProFormProps = {
    values: string
};
export const FORM_LAYOUT_PUBLIC = {
    labelCol: { span: 5 },
    wrapperCol: { span: 15 },
};
const NodePreview: React.FC<NodeProFormProps> = (props) => {
    const { values } = props;
    return <>
        <CodeShow code={values} language='sql' height='500px' theme="vs-dark" />
    </>
};
export default NodePreview;