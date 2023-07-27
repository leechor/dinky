
import React from 'react';
import MetaDataContainer from "@/pages/DataCenter/MetaData"
export type DataSourceProps = {
    values?: any;
    modalVisible?: boolean
    getDBConfigInfo: (value: any) => void

};
export const FORM_LAYOUT_PUBLIC = {
    labelCol: { span: 5 },
    wrapperCol: { span: 15 },
};
const DataSource: React.FC<DataSourceProps> = (props) => {

    const { values, modalVisible, getDBConfigInfo } = props

    return <>
        <MetaDataContainer values={values} modalVisible={modalVisible} getDBConfigInfo={getDBConfigInfo} />
    </>
};
export default DataSource;
