package com.zdpx.coder;

import java.util.Map;

import com.zdpx.coder.code.CodeBuilder;

public interface SceneCode {
    CodeBuilder getGenerateResult();

    void setGenerateResult(CodeBuilder codeBuilder);

    Map<String, String> getUdfFunctionMap();

    void setUdfFunctionMap(Map<String, String> udfFunctionMap);

    Map<String, Object> build();
}
