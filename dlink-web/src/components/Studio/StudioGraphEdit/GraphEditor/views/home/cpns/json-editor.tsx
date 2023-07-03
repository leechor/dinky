import 'spectre.css/dist/spectre.css';
import 'spectre.css/dist/spectre-exp.css';
import 'spectre.css/dist/spectre-icons.css';
import styles from './index.less';
import { textSchema } from "../../../assets/json-data/text-node-schema"

import { memo, useEffect, useRef, useState } from 'react';
import { Node } from '@antv/x6';
import {
  useAppDispatch,
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { JSONEditor, JSONEditorOptions } from '@json-editor/json-editor';
import {
  changeCurrentSelectNode,
  changeCurrentSelectNodeParamsData,
  changeJsonEditor,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';

const Editor = memo(() => {

  const jsonRef = useRef<HTMLDivElement>(null);
  let editor: InstanceType<typeof JSONEditor>
  const dispatch = useAppDispatch();
  const { operatorParameters, currentSelectNode } = useAppSelector(
    (state) => ({
      operatorParameters: state.home.operatorParameters,
      currentSelectNode: state.home.currentSelectNode,
    }),
  );
  let isTextNode: boolean = currentSelectNode.shape === "custom-text-node";
  const textNodeSchema = isTextNode ? textSchema : null
  const currentNodeDes = operatorParameters.find((item: any) => item.code === currentSelectNode.shape);
  const config: JSONEditorOptions<any> = {
    schema: isTextNode ? textNodeSchema : (currentNodeDes?.specification ?? null),
    //设置主题,可以是bootstrap或者jqueryUI等
    theme: 'spectre',
    //设置字体
    iconlib: 'spectre',
    //如果设置为 true, 将隐藏编辑属性按钮.
    disable_properties: true,
    disable_edit_json: true,
    //如果设置为 true, 数组对象将不显示“向上”、“向下”移动按钮.
    disable_array_reorder: true,
    //属性为object时,属性默认normal,设置grid可以一排多个
    object_layout: 'normal',
    disable_array_delete: false,
    compact: true,
  };

  useEffect(() => {
    if (jsonRef.current) {
      const container = jsonRef.current;
      container.innerHTML = '';
      if (!config.schema) return

      editor = new JSONEditor<any>(container, config);
      editor.on('ready', function () {
        dispatch(changeJsonEditor(editor))
        if(currentSelectNode.shape === "custom-text-node"){
          //解决bug 防止直接将config的值设置
          editor.setValue(currentSelectNode.getData())
        }
        if (currentSelectNode instanceof Node) {
          if (currentSelectNode.getData() && currentSelectNode.getData().parameters) {

            //解决bug 防止直接将config的值设置
            editor.setValue(currentSelectNode.getData().parameters)
          }
        }
      });
      editor.on('change', function () {
        //先恢复初始值
        dispatch(changeCurrentSelectNodeParamsData([]));
        //设置当前属性值
        console.log(editor.getValue());

        dispatch(changeCurrentSelectNodeParamsData(editor.getValue()));
        if (currentSelectNode.shape === "custom-text-node") {
          currentSelectNode.setData(editor.getValue())
          dispatch(changeCurrentSelectNode(currentSelectNode));
          return
        }
        if (currentSelectNode instanceof Node) {

          let nodeConfig = []
          if (currentSelectNode.getData() && currentSelectNode.getData().config) {
            nodeConfig = currentSelectNode.getData().config
          }
          console.log(editor.getValue());

          //判断当前节点是否有变化
          // 如果是同一个节点，说明只是值发生变化
          //如果节点发生变化，则不设置，让其读取自己的配置
          currentSelectNode.setData({ parameters: editor.getValue(), config: nodeConfig }, { overwrite: true });
          dispatch(changeCurrentSelectNode(currentSelectNode));
        }
      });

    }
    return () => {
      editor.destroy()
    }

  }, [operatorParameters, currentSelectNode]);

  return <div className={styles['json-editor-content']} ref={jsonRef}></div>;
});

export default Editor;
