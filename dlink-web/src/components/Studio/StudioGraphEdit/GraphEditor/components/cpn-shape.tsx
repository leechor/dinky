import { memo } from 'react';
import { SvgType } from '../types';
import CustomSvg from './custom-svg';

const CpnShape = memo<SvgType>(({ iconPath, styleObj }) => {
  return <CustomSvg iconPath={iconPath} styleObj={styleObj} />;
});

export default CpnShape;
