import React, {FC, useState} from 'react';

import { NodeType } from './cpn-shape';


const TextNode: FC<NodeType> = ({ node }: NodeType) => {
  const ha = node.getData()?.horizontalAlign ?? 'left'
  const va = node.getData()?.verticalAlign ?? 'top'
  const [backgroundColor] = useState(node.getData()?.backgroundColor ?? '#efdbff');
  const [fontColor] = useState(node.getData()?.fontColor ?? {fontColor: '#000000'});
  const [horizontalAlign, setHorizontalAlign] = useState(ha);
  const [verticalAlign, setVerticalAlign]  = useState(va);

  if (horizontalAlign !== ha) {
    setHorizontalAlign(ha)
  }

  if (verticalAlign !== va) {
    setVerticalAlign(va)
  }

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
        }}
        onChange={onChange}
        placeholder="input..."
      />
  );
};

export default TextNode;
