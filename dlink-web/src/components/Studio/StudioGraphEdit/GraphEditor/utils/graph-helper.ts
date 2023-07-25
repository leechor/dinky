import {Node} from "@antv/x6";

export function getGraphViewSize() {
  const view = document.querySelector('.x6-graph-scroller-pannable')
  if (!view) {
    return
  }
  const size: { x: number, y: number, width: number, height: number } = view.getBoundingClientRect()
  return size;
}

export const convertAbsoluteToRelativePosition = (source: { x: number, y: number }, target: Node) => {
  return {
    x: source.x - target.position().x,
    y: source.y - target.position().y,
  }
}
const convertAbsoluteToRelativePositionNode = (source: Node, target: Node) => {
  return convertAbsoluteToRelativePosition(source.position(), target)
}

export class PreNodeRect {
  height: number;
  width: number;
  x: number;
  y: number;

  constructor({x, y}: { x: number, y: number }, {width, height}: { width: number, height: number }) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public get position() {
    return {x: this.x, y: this.y}
  }

  public get size() {
    return {width: this.width, height: this.height}
  }
}
