import { forwardRef } from 'react';
import styles from './index.less';
const Stencil = forwardRef<HTMLDivElement>((props, ref) => {
  return (
    <>
      <div className={styles['leftEditor-stencil']}>
        <div ref={ref}></div>
      </div>
    </>
  );
});
export default Stencil;
