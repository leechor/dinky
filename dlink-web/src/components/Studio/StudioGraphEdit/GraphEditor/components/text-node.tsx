import React, { FC, useEffect, useRef } from 'react';
import styles from './index.less';
import { NodeType } from './cpn-shape';

const TextNode: FC<NodeType> = ({ node }: NodeType) => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  // const convertVerticalAlign = (align: string) => {
  //   switch (align) {
  //     case "top":
  //       return "flex-start";
  //     case "bottom":
  //       return "flex-end";
  //     case "center":
  //       return "center"
  //     default:
  //       return "flex-start"
  //   }
  // }
  window.testnode = node;
  const horizontalAlign = node.getData()?.horizontalAlign ?? 'left';
  // const verticalAlign = (node.getData()?.verticalAlign) ? convertVerticalAlign(node.getData()?.verticalAlign) : "flex-start"
  const backgroundColor = node.getData()?.backgroundColor ?? '#efdbff';
  const fontColor = node.getData()?.fontColor ?? { fontColor: '#000000' };
  const fontSize = node.getData()?.fontSize ?? { fontSize: 14 };
  const fontWeight = node.getData()?.fontWeight ?? { fontWeight: 14 };
  const fontFamily = node.getData()?.fontFamily ?? { fontFamily: 'Arial' };

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    node.setData({
      parameters: e.target.value,
      fontColor,
      backgroundColor,
      fontSize,
      fontFamily,
      fontWeight,
    });
    if (textareaRef.current) {
      node.resize(node.size().width, textareaRef.current?.scrollHeight);
    }
  };
  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.value = node.getData()?.parameters ?? '';
    }
  }, []);

  return (
    <div
      style={{
        display: 'flex',
        width: '100%',
        height: '100%',
        // alignItems: verticalAlign,
        backgroundColor: backgroundColor,
      }}
    >
      <textarea
        ref={textareaRef}
        className={styles['text-area']}
        style={{
          width: '100%',
          height: '100%',
          backgroundColor: backgroundColor,
          textAlign: horizontalAlign,
          color: fontColor,
          resize: 'none',
          border: 'none',
          fontSize,
          fontWeight,
          fontFamily,
        }}
        onChange={onChange}
        placeholder="input..."
      />
    </div>
  );
};

export default TextNode;
