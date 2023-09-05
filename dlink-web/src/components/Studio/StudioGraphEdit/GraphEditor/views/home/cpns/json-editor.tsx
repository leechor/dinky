import 'spectre.css/dist/spectre.css';
import 'spectre.css/dist/spectre-exp.css';
import 'spectre.css/dist/spectre-icons.css';
import { Tooltip } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';
import { createRoot } from 'react-dom/client';
import { cloneDeep } from 'lodash';

import styles from './index.less';
import { textSchema } from '../../../assets/json-data/text-node-schema';
import { memo, useEffect, useRef } from 'react';
import { Graph, Node } from '@antv/x6';
import {
  useAppDispatch,
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { JSONEditor, JSONEditorOptions } from '@json-editor/json-editor';
import {
  changeCurrentSelectNode,
  changeJsonEditor,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import { MyMonacoEditor as myMonaco } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/my-monaco-editor';
import { MyAutoCompleteEditor as myautoinput } from '@/components/Studio/StudioGraphEdit/GraphEditor/utils/my-auto-complete-editor';
import CustomShape, { aceModes, FunctionType } from '../../../utils/cons';
import {
  getId,
  getSourceNodeAndPort,
  getTargetNodeAndPort,
  isCustomerOperator,
  isCustomTextNode,
  setConfigToNode,
} from '../../../utils/graph-tools-func';
import { handleMultipleFuction } from '@/components/Common/crud';
import { JSONEDITORCONFIG } from '../../../utils/cons';

const EditorTip = (title: string) => {
  return (
    <Tooltip title={title}>
      <QuestionCircleOutlined />
    </Tooltip>
  );
};

function preProcessJsonEditor() {
  // register monaco
  JSONEditor.defaults.editors['monaco'] = myMonaco;

  const monaco: any = (schema: any) =>
    schema.type === 'string' && aceModes.includes(schema.format) && 'monaco';
  JSONEditor.defaults.resolvers = [monaco, ...JSONEditor.defaults.resolvers];

  // register autocomplete.js
  JSONEditor.defaults.editors['autoinput'] = myautoinput;
  const autoinput: any = (schema: any) =>
    schema.type === 'string' && schema.format === 'autoinput' && 'autoinput';
  JSONEditor.defaults.resolvers = [autoinput, ...JSONEditor.defaults.resolvers];
}

const Editor = memo(() => {
  const jsonRef = useRef<HTMLDivElement>(null);
  let editor: InstanceType<typeof JSONEditor>;
  const dispatch = useAppDispatch();

  const { operatorParameters, currentSelectNode, edgeClickInfo, graph } = useAppSelector(
    (state) => ({
      operatorParameters: state.home.operatorParameters,
      currentSelectNode: state.home.currentSelectNode,
      edgeClickInfo: state.home.edgeClickInfo,
      graph: state.home.graph,
    }),
  );

  let isTextNode: boolean = currentSelectNode.shape === CustomShape.TEXT_NODE;
  const textNodeSchema = isTextNode ? textSchema : null;

  const currentNodeDes = operatorParameters.find(
    (item: any) => item.code === currentSelectNode.shape,
  );
  const config: JSONEditorOptions<any> = {
    schema: isTextNode ? textNodeSchema : currentNodeDes?.specification ?? null,
    ...JSONEDITORCONFIG
  };

  const setCustomData = () => {
    const edges = (graph as Graph).getOutgoingEdges(currentSelectNode);
    edges && edges.map(edge => {
      const targetInfo = getTargetNodeAndPort(edge, graph);
      const sourceInfo = getSourceNodeAndPort(edge, graph);
      if (typeof targetInfo !== null && typeof sourceInfo !== null) {
        const targetNode = targetInfo?.targetNode!;
        const targetPortId = targetInfo?.targetPortId!;
        const sourceNode = sourceInfo?.sourceNode!;
        const sourcePortId = sourceInfo?.sourcePortId!;
        const id = getId(sourceNode, sourcePortId, targetNode, targetPortId);
        let newConfigObj: any = {};
        //将下个节点的所有连接装都重置(不影响前节点config保持input-output config不变)
        for (let key in sourceNode.getData()['config'][0]) {
          key !== id ? newConfigObj[key] = cloneDeep(sourceNode.getData()['config'][0][key]) :
            newConfigObj[key] = cloneDeep((editor.getValue() as any).output?.columns);
        }
        setConfigToNode(sourceNode, newConfigObj);
      }
    })
  };
  useEffect(() => {
    if (!jsonRef.current || !config.schema) {
      return;
    }

    const container = jsonRef.current;
    container.innerHTML = '';
    preProcessJsonEditor();

    editor = new JSONEditor<any>(container, config);

    editor.on('ready', function () {
      dispatch(changeJsonEditor(editor));

      if (!(currentSelectNode instanceof Node)) {
        return;
      }
      //解决bug 防止直接将config的值设置
      currentSelectNode.getData()?.parameters && editor.setValue(currentSelectNode.getData().parameters);
      isCustomTextNode(currentSelectNode) && editor.setValue(currentSelectNode.getData());
      if (isCustomerOperator(currentSelectNode)) {
        editor.watch('root.service', async () => {
          let editorData: any = editor.getValue();

          const datas = await handleMultipleFuction('/api/zdpx/operator/generalProcess', {
            [FunctionType.ANALYSE]: JSON.stringify(editorData),
          });
          if (datas.analyse.length) {
            let outputEditor = editor.getEditor('root.output.columns');
            outputEditor.setValue(datas.analyse);
          }
        });
        editor.watch('root.output.columns', () => {
          //输入字段变化，更改本身节点向下连线的config
          setCustomData();
        });
      }
    });

    editor.on('change', async function () {

      if (!(currentSelectNode instanceof Node)) {
        return;
      }
      if (isCustomerOperator(currentSelectNode)) {
        setCustomData();
      }
      if (isCustomTextNode(currentSelectNode)) {
        currentSelectNode.setData(editor.getValue());
        dispatch(changeCurrentSelectNode(currentSelectNode));
        return;
      }

      changeStyle();

      currentSelectNode.setData(
        {
          parameters: editor.getValue(),
          config: currentSelectNode.getData()?.config ?? [],
        },
        { overwrite: true },
      );
      dispatch(changeCurrentSelectNode(currentSelectNode));
    });

    function changeStyle() {
      //modify description
      const labels = document.querySelectorAll('.je-panel label');
      labels.forEach((item: Element) => {
        const desc: HTMLElement | null | undefined = item.parentElement?.querySelector('p.je-desc');
        if (!desc) {
          return;
        }

        const dev = document.createElement('div');
        const root = createRoot(dev);
        root.render(<>{EditorTip(desc.textContent ?? '')}</>);
        if (item.querySelector('div') === null) {
          item.append(dev);
        }
        desc.remove();
      });

      //modify question mark
      const h4Label = document.querySelectorAll('.je-panel h4');
      h4Label.forEach((item: Element) => {
        const desc: HTMLElement | null | undefined = item.parentElement?.querySelector(
          'h4 + span + div + p, h4 + div + p',
        );
        if (!desc) {
          return;
        }

        const dev = document.createElement('div');
        const root = createRoot(dev);
        root.render(<>{EditorTip(desc.textContent ?? '')}</>);
        if (item.querySelector('div') === null) {
          item.append(dev);
        }
        desc.remove();
      });

      //modify delete columns button
      const dataObjects = document.querySelectorAll('.je-object__container');
      dataObjects.forEach((item) => {
        const deleteButton: HTMLElement | null = item.querySelector(
          ':scope > .btn-group > button[title^="Delete"]',
        );
        if (!deleteButton) {
          return;
        }

        const icon = deleteButton.querySelector('.icon-delete + span');
        if (!icon) {
          return;
        }
        icon.innerHTML = '';

        const h4 = item.querySelector('h4.je-object__title');
        h4?.append(deleteButton.parentElement!);
      });

      //modify delete items button
      const deleteItems = document.querySelectorAll('.je-panel > div > .je-panel');
      deleteItems.forEach((item) => {
        const deleteButton = item.querySelector('.btn-group > button[title^="Delete"]');
        if (!deleteButton) {
          return;
        }

        const itemFormGroup = item.querySelector('.form-group');
        itemFormGroup?.append(deleteButton.parentElement!);
        itemFormGroup?.setAttribute('style', 'flex-wrap: nowrap');
      });
    }

    return () => {
      editor.destroy();
    };
  }, [operatorParameters, currentSelectNode, edgeClickInfo]);

  return <div className={styles['json-editor-content']} ref={jsonRef}></div>;
});

export default Editor;
