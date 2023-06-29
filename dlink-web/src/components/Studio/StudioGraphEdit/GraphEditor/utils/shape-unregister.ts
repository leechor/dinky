import { Graph } from "@antv/x6";
import CustomShape from "@/components/Studio/StudioGraphEdit/GraphEditor/utils/cons";
export default (operatorParameters: any) => {
  operatorParameters.forEach((item: any) => {
    Graph.unregisterNode(item.code);
  });
  Graph.unregisterNode(CustomShape.TEXT_NODE);
  Graph.unregisterNode("package");
};
