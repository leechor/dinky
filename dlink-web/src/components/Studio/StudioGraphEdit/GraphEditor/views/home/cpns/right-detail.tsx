import { memo } from 'react';
import Editor from './json-editor';
import styles from './index.less';
import CpnShape from '../../../components/cpn-shape';
import { useAppSelector } from '@/components/Studio/StudioGraphEdit/GraphEditor/hooks/redux-hooks';
import { l } from '@/utils/intl';

const RightDetail = memo(() => {

  const { currentSelectNode, operatorParameters } = useAppSelector((state) => ({
    currentSelectNode: state.home.currentSelectNode,
    operatorParameters: state.home.operatorParameters,
  }));

  const getTitleInfo = () => {
    if (currentSelectNode && currentSelectNode.shape) {
      const found = operatorParameters.find((item: any) => item.code === currentSelectNode.shape);
      if (!found) return
      return (
        <>
          <CpnShape iconPath={found.icon} />
          <span>{found.name}</span>
        </>
      );

    }
    return <span>{l("graph.rightdetail.node.information")}</span>;
  };
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
