import { memo } from 'react';
import Editor from './json-editor';
import styles from './index.less';
import CpnShape from '../../../components/cpn-shape';
import {
  useAppSelector,
} from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
const RightDetail = memo(() => {
  const { currentSelectNode } = useAppSelector(
    (state) => ({
      currentSelectNode: state.home.currentSelectNode,
    }),
  );
  const getTitleInfo = () => {
    if (currentSelectNode && currentSelectNode.shape) {
      return <span>{currentSelectNode.shape}</span>
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
