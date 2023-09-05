import { memo } from 'react';
import GraphEditor from '@/components/Studio/StudioGraphEdit/GraphEditor/views/home/index';
import { useAppDispatch } from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { initFlowDataAction } from '@/components/Studio/StudioGraphEdit/GraphEditor/store/modules/home';
import styles from './index.less';

const FlinkGraphEditor = memo(() => {
  const dispatch = useAppDispatch();
  dispatch(initFlowDataAction());
  return (
    <div className={styles['graph-container']}>
      <GraphEditor />
    </div>
  );
});

export default FlinkGraphEditor;
