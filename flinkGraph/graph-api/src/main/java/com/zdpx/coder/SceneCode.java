package com.zdpx.coder;

import com.zdpx.coder.code.CodeBuilder;
import com.zdpx.coder.graph.Scene;

import java.util.Map;

public interface SceneCode {
    CodeBuilder getGenerateResult();

    void setGenerateResult(CodeBuilder codeBuilder);

    Scene getScene();

    Map<String, String> getUdfFunctionMap();

    void setUdfFunctionMap(Map<String, String> udfFunctionMap);

    Map<String, Object> build();

    CodeContext createCodeContext(Scene scene);
}
