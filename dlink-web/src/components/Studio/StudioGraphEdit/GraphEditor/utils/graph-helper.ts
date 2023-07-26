import {Node} from "@antv/x6";

export function getGraphViewSize() {
  const view = document.querySelector('.x6-graph-scroller-pannable')
  if (!view) {
    return
  }
  const size = view.getBoundingClientRect()
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

export interface PreNodeRect {
  x: number,
  y: number,
  height: number,
  width: number,
}

export const getPointsBox = (points: PreNodeRect[]): PreNodeRect => {
  let left: number, top: number, right: number, bottom: number, width: number = 0, height: number = 0;
  points.forEach((point) => {
    if (!point) {
      return
    }
    if (left === undefined || point.x < left) {
      left = point.x;
    }
    if (top === undefined || point.y < top) {
      top = point.y
    }
    if (right === undefined || point.x + point.width > right) {
      right = point.x + point.width
    }
    if (bottom === undefined || point.y + point.height > bottom) {
      bottom = point.y + point.height
    }
    width = right - left
    height = bottom - top
  })
  return {x: left!, y: top!, width, height}
}
