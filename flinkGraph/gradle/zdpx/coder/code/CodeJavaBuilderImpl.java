package com.zdpx.coder.code;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.zdpx.coder.CodeContext;
import com.zdpx.coder.Specifications;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeJavaBuilderImpl implements CodeJavaBuilder {
    public static final String SRC_MAIN_JAVA_GENERATE = "flinkGraph/src/main/java/generate";
    private static final Path directory = Path.of(SRC_MAIN_JAVA_GENERATE);
    private final CodeContext codeContext;

    public CodeJavaBuilderImpl(CodeContext codeContext) {
        this.codeContext = codeContext;
    }

    @Override
    public void registerUdfFunction(String udfFunctionName, Class<?> functionClass) {
        codeContext.getMain().addStatement("$L.createTemporarySystemFunction($S, $T.class)", Specifications.TABLE_ENV, udfFunctionName, functionClass);
    }

    @Override
    public void firstBuild() {
        final var environment = codeContext.getScene().getEnvironment();

        codeContext.getMain()
            .addStatement("$T $L = $T.getExecutionEnvironment()", Specifications.SEE, Specifications.ENV, Specifications.SEE)
            .addStatement("$N.setRuntimeMode($T.$L)", Specifications.ENV, Specifications.RUNTIME_EXECUTION_MODE, environment.getMode())
            .addStatement("$N.setParallelism($L)", Specifications.ENV, environment.getParallelism())
            .addStatement("$T $N = $T.create($N)", Specifications.STE, Specifications.TABLE_ENV, Specifications.STE, Specifications.ENV)
            .addCode(System.lineSeparator());
    }

    @Override
    public void generate(String sql) {
        var cb = CodeBlock.builder().addStatement(Specifications.EXECUTE_SQL, Specifications.TABLE_ENV, sql).build();
        this.codeContext.getMain().addCode(cb).addCode(System.lineSeparator());
    }

    @Override
    public void generateJavaFunction(CodeBlock codeBlock) {
        codeContext.getMain().addCode(codeBlock).addCode(System.lineSeparator());
    }

    @Override
    public CodeContext getCodeContext() {
        return codeContext;
    }

    @Override
    public void lastBuild() {
        codeContext.getMain().addStatement("$N.execute()", Specifications.ENV);
        final var classBody =
            TypeSpec.classBuilder(codeContext.getScene().getEnvironment().getName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(codeContext.getMain().build())
                .build();

        var javaFile = JavaFile.builder(Specifications.COM_ZDPX_CJPG, classBody).build();

        try {
            javaFile.writeTo(directory);
            reformat();
        } catch (IOException e) {
            log.error(String.format("write file %s error!Error: %s", directory, e.getMessage()));
        } catch (FormatterException e) {
            log.error(String.format("reformat file %s error! Error: %s", directory, e.getMessage()));
        }
    }

    /**
     * 格式化生成的所有源码文件
     *
     * @throws FormatterException FormatterException
     * @throws IOException        IOException
     */
    public static void reformat() throws FormatterException, IOException {
        try (Stream<Path> stream = Files.walk(directory)) {
            List<Path> files = stream.filter(path -> path.toString().endsWith(".java")).collect(Collectors.toList());
            var formatter = new Formatter();
            for (Path file : files) {
                String source = Files.readString(file);
                String formatted = formatter.formatSourceAndFixImports(source);
                Files.writeString(file, formatted);
            }
        }
    }

}
