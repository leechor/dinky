package com.zdpx.coder.code;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zdpx.coder.graph.IScene;

import java.util.Set;

public interface ICodeContext {
    // region getter/setter
    TypeSpec.Builder getJob();

    MethodSpec.Builder getMain();

    Set<String> getSuppressedWarnings();

    IScene getScene();
}
