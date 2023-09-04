import { FC } from 'react';
import { SvgType } from '../types';



const defaultStyle = {
  width: '20px',
  height: '20px',
};

/** 自定义的菜单 icon  */
const CustomIconCom: FC<SvgType> = ({ iconPath, styleObj = defaultStyle }: SvgType) => {
  return (
    <svg className="icon custom-svg" style={styleObj} aria-hidden="true">
      <use xlinkHref={`#${iconPath}`}></use>
    </svg>
  );
};

export default CustomIconCom;
