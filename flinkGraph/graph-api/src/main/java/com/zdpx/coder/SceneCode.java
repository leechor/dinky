package com.zdpx.coder;

import com.zdpx.coder.code.CodeBuilder;

import java.util.Map;

public interface SceneCode {
    CodeBuilder getGenerateResult();

    void setGenerateResult(CodeBuilder codeBuilder);

    Map<String, String> getUdfFunctionMap();

    void setUdfFunctionMap(Map<String, String> udfFunctionMap);

    Map<String, Object> build();
}
