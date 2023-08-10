package com.zdpx.coder.graph;

public interface IScene {
    Environment getEnvironment();

    NodeCollection getProcess();

    void setEnvironment(Environment environment);

    void setProcess(NodeCollection process);
}
