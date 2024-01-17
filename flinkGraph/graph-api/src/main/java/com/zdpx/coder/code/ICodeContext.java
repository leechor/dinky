package com.zdpx.coder.code;

import java.util.Set;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zdpx.coder.graph.IScene;

public interface ICodeContext {
    // region getter/setter
    TypeSpec.Builder getJob();

    MethodSpec.Builder getMain();

    Set<String> getSuppressedWarnings();

    IScene getScene();
}
