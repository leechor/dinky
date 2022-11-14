package com.zdpx.coder.code;

/**
 *
 */
public interface CodeBuilder {

    void registerUdfFunction(String udfFunctionName, Class<?> functionClass);

    void firstBuild();

    void generate(String sql);

    String lastBuild();
}
