import 'spectre.css/dist/spectre.css';
import 'spectre.css/dist/spectre-exp.css';
import 'spectre.css/dist/spectre-icons.css';
import styles from './index.less';

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
  changeCurrentSelectNodeName,
  changeJsonEditor,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';

const Editor = memo(() => {
  const jsonRef = useRef<HTMLDivElement>(null);
  let editor: InstanceType<typeof JSONEditor>
  const dispatch = useAppDispatch();
  const { operatorParameters, currentSelectNode, currentSelectNodeName } = useAppSelector(
    (state) => ({
      operatorParameters: state.home.operatorParameters,
      currentSelectNode: state.home.currentSelectNode,
      currentSelectNodeName: state.home.currentSelectNodeName,
    }),
  );
  const currentNodeDes = operatorParameters.find((item: any) => item.name === currentSelectNodeName);
  const config: JSONEditorOptions<any> = {
    schema: currentNodeDes?.specification ?? null,
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
  };

  useEffect(() => {
    if (jsonRef.current) {
      const container = jsonRef.current;
      container.innerHTML = '';
      if (!config.schema) return
      
      editor = new JSONEditor<any>(container, config);
      editor.on('ready', function () {
        dispatch(changeJsonEditor(editor))
        if (currentSelectNode instanceof Node) {
          if (currentSelectNode.getData()&&currentSelectNode.getData().parameters) {
            
            //解决bug 防止直接将config的值设置
            editor.setValue(currentSelectNode.getData().parameters)
          }
        }
      });
      editor.on('change', function () {
        //先恢复初始值
        dispatch(changeCurrentSelectNodeParamsData([]));
        //设置当前属性值
        dispatch(changeCurrentSelectNodeParamsData(editor.getValue()));
        if (currentSelectNode instanceof Node) {
          
          let config=[]
          if(currentSelectNode.getData()&&currentSelectNode.getData().config){
            config=currentSelectNode.getData().config
          }
          currentSelectNode.setData({ parameters: editor.getValue(),config},{overwrite:true});
          dispatch(changeCurrentSelectNode(currentSelectNode));
        }
      });

    }
    return () => {
      editor.destroy()
    }

  }, [operatorParameters, currentSelectNodeName,currentSelectNode]);

  return <div className={styles['json-editor-content']} ref={jsonRef}></div>;
});

export default Editor;
