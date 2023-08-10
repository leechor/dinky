import { memo } from 'react';
import RightDetail from './cpns/right-detail';
import LeftEditor from './cpns/left-editor';
import styles from './index.less';
import { Portal } from '@antv/x6-react-shape';
const x6ReactProtalProvider = Portal.getProvider()
const GraphEditor = memo((props) => {
  //获取数据

  return (
    <div className={styles['graph-container']}>
      {x6ReactProtalProvider()}
      <LeftEditor />
      <RightDetail />
    </div>
  );
});



export default GraphEditor;
