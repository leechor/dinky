import {Node, Rectangle} from "@antv/x6";

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

export interface PreNodeRectangle extends Rectangle {

}
