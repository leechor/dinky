import {useState} from "react";
import GraphEditor from "@/components/Studio/StudioGraphEdit/GraphEditor";
import {Dispatch, DocumentStateType} from "@@/plugin-dva/connect";
import {connect} from "umi";


const FlinkGraphEditor = (props: any) => {
    const {
        tabsKey,
        height = '100%',
        width = '100%',
        language = 'sql',
        onChange = (val: string, event: any) => {
        },
        options = {
            selectOnLineNumbers: true,
            renderSideBySide: false,
            autoIndent: 'None',
        },
        sql,
        monaco,
        // sqlMetaData,
        fillDocuments,
    } = props;

    const [code, setCode] = useState<string>(sql);

    return (
        <>
            <GraphEditor
                ref={monaco}
                width={width}
                height={height}
                language={language}
                value={code}
                options={options}
                onChange={() => {
                }}
                theme="vs-dark"
                editorDidMount={() => {
                }}>

            </GraphEditor>
        </>
    )
}


const mapDispatchToProps = (dispatch: Dispatch) => ({
    /*saveText:(tabs:any,tabIndex:any)=>dispatch({
      type: "Studio/saveTask",
      payload: tabs.panes[tabIndex].task,
    }),*/
    saveSql: (val: any) => dispatch({
        type: "Studio/saveGraph",
        payload: val,
    }), saveSqlMetaData: (sqlMetaData: any, key: number) => dispatch({
        type: "Studio/saveSqlMetaData",
        payload: {
            activeKey: key,
            sqlMetaData,
            isModified: true,
        }
    }),
})

export default connect(({Document}: { Document: DocumentStateType }) => ({
    fillDocuments: Document.fillDocuments,
}), mapDispatchToProps)(FlinkGraphEditor);
