import { memo } from 'react';
import Editor from './json-editor';
import styles from './index.less';
import CpnShape from '../../../components/cpn-shape';
import {
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
const RightDetail = memo(() => {
  const { currentSelectNode, operatorParameters } = useAppSelector(
    (state) => ({
      currentSelectNode: state.home.currentSelectNode,
      operatorParameters: state.home.operatorParameters
    }),
  );
  const getTitleInfo = () => {
    console.log(currentSelectNode, operatorParameters);

    if (currentSelectNode && currentSelectNode.shape) {
      const found = operatorParameters.find((item: any) => item.code === currentSelectNode.shape)
      if (found) {
        return (<>
          <CpnShape iconPath={found.icon} />
          <span>{found.name}</span>
        </>)
      } else {199
        return <span>节点信息</span>
      }
    } else {
      return <span>节点信息</span>
    }
  }
  return (
    <div className={styles['rightDetail']}>
      <div className={styles['rightDetail-header']}>{getTitleInfo()}</div>
      <div className={styles['rightDetail-content']}>
        <Editor />
      </div>
    </div>
  );
});

export default RightDetail;
