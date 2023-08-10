package com.zdpx.coder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zdpx.coder.graph.Scene;

import java.util.Set;

public interface ICodeContext {
    // region getter/setter
    TypeSpec.Builder getJob();

    MethodSpec.Builder getMain();

    Set<String> getSuppressedWarnings();

    Scene getScene();
}
