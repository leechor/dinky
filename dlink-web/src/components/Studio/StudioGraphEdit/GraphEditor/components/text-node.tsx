import React, {FC} from 'react';

import { NodeType } from './cpn-shape';


const TextNode: FC<NodeType> = ({ node }: NodeType) => {
  const horizontalAlign = node.getData()?.horizontalAlign ?? 'left'
  const verticalAlign = node.getData()?.verticalAlign ?? 'top'
  const backgroundColor = node.getData()?.backgroundColor ?? '#efdbff';
  const fontColor = node.getData()?.fontColor ?? {fontColor: '#000000'};
  const fontSize = node.getData()?.fontSize ?? {fontSize: 14};
  const fontWeight = node.getData()?.fontWeight ?? {fontWeight: 14};
  const fontFamily = node.getData()?.fontFamily ?? {fontFamily: "Arial"};

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    node.setData({ parameters: e.target.value, fontColor: fontColor, backgroundColor: backgroundColor });
  };

  return (
      <textarea
        style={{
          marginBottom: 24,
          width: "100%",
          height: "100%",
          backgroundColor: backgroundColor,
          verticalAlign: verticalAlign,
          textAlign: horizontalAlign,
          color: fontColor,
          resize: "none",
          border: "none",
          fontSize,
          fontWeight,
          fontFamily
        }}
        onChange={onChange}
        placeholder="input..."
      />
  );
};

export default TextNode;
