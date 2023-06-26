import React, { memo, FC } from 'react';

import { NodeType } from './cpn-shape';


const TextNode: FC<NodeType> = memo(({ node }: NodeType) => {
  const {backgroundColor} = node.getData() ?? {backgroundColor: "#efdbff"};
  const {fontColor} = node.getData()?.fontColor ?? "#000000";

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
          color: fontColor,
          resize: "none",
          border: "none",
        }}
        onChange={onChange}
        placeholder="input..."
      />
  );
});

export default TextNode;
