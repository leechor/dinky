package com.zdpx.coder.json.preview;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.graph.NodeWrapper;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.json.ResultType;
import com.zdpx.coder.json.x6.X6NodeWrapper;
import com.zdpx.coder.json.x6.X6ToInternalConvert;
import com.zdpx.coder.operator.Operator;
import lombok.SneakyThrows;

public class OperatorPreviewBuilder{

    private String graph;

    public OperatorPreviewBuilder(String graph){
        this.graph=graph;
    }

    @SneakyThrows
    public String operatorPreview() {

        //初始化算子环境  默认每次传一个算子
        JsonNode cell = new ObjectMapper().readTree(graph).path("cells").get(0);
        X6ToInternalConvert x6ToInternalConvert = new X6ToInternalConvert();
        String name = cell.get("shape").asText();
        Operator operator =x6ToInternalConvert.createOperatorByCode(name);

        //添加算子信息
        operator.setNodeWrapper(new X6NodeWrapper());
        operator.setName(name);
        JsonNode data = cell.get("data");
        if (data != null) {
            NodeWrapper nodeWrapper = operator.getNodeWrapper();
            if (data.get("parameters") != null) {
                String parameters = data.get("parameters").toPrettyString();
                nodeWrapper.setParameters(parameters);
            }
            if (data.get("config") != null) {
                String config = data.get("config").toPrettyString();
                nodeWrapper.setConfig(config);
            }
            operator.setOperatorWrapper(nodeWrapper);
        }

        //执行算子
        Scene scene = new Scene();
        scene.getEnvironment().setResultType(ResultType.SQL);
        SceneCodeBuilder sceneCodeBuilder = new SceneCodeBuilder(scene);
        operator.setSchemaUtil(sceneCodeBuilder);
        operator.run();

        //获取执行结果
        return sceneCodeBuilder.getGenerateResult().lastBuild();
    }

}
