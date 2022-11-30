package com.zdpx.coder;

import org.apache.flink.table.functions.UserDefinedFunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdpx.coder.code.CodeBuilder;
import com.zdpx.coder.code.CodeJavaBuilderImpl;
import com.zdpx.coder.code.CodeSqlBuilderImpl;
import com.zdpx.coder.graph.OperatorWrapper;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.json.ResultType;
import com.zdpx.coder.json.SceneNode;
import com.zdpx.coder.operator.Identifier;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.utils.InstantiationUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置场景生成操作类
 *
 * @author Licho Sun
 */
@Slf4j
public class SceneCodeBuilder {
    // 生成文件所有路径
    // 自定义函数操作算子集
    public final Set<Class<? extends UserDefinedFunction>> udfFunctions = new HashSet<>();

    private CodeBuilder codeBuilder;
    private final Scene scene;

    public SceneCodeBuilder(Scene scene) {
        this.scene = scene;
        if (scene.getEnvironment().getResultType() == ResultType.JAVA) {
            var codeContext = createCodeContext(scene);
            codeBuilder = new CodeJavaBuilderImpl(codeContext);
        } else if (scene.getEnvironment().getResultType() == ResultType.SQL) {
            codeBuilder = new CodeSqlBuilderImpl();
        }
    }

    public CodeBuilder getGenerateResult() {
        return codeBuilder;
    }

    public void setGenerateResult(CodeBuilder codeBuilder) {
        this.codeBuilder = codeBuilder;
    }

    public Scene getScene() {
        return scene;
    }

    public Set<Class<? extends UserDefinedFunction>> getUdfFunctions() {
        return udfFunctions;
    }

    /**
     * 根据场景配置生成可执行的java源文件
     *
     * @throws IOException ignore
     */
    public String build() {
        if (codeBuilder == null) {
            throw new IllegalStateException(String.format("Code Builder %s empty!", codeBuilder));
        }

        codeBuilder.firstBuild();
        createOperatorsCode();
        return codeBuilder.lastBuild();
    }

    /**
     * 广度优先遍历计算节点, 生成相对应的源码
     */
    private void createOperatorsCode() {
        var sinkOperatorNodes = Scene.getSinkOperatorNodes(this.scene.getProcess());
        var sinks =
            sinkOperatorNodes.stream().map(OperatorWrapper::getOperator).collect(Collectors.toList());
        Deque<Operator> ops = new ArrayDeque<>();
        bft(Set.copyOf(sinks), ops::push);
        ops.forEach(this::operate);
    }

    /**
     * 广度优先遍历计算节点, 执行call 函数
     *
     * @param operators 起始节点集
     * @param call      待执行函数
     */
    private void bft(Set<Operator> operators, Consumer<Operator> call) {
        if (operators.isEmpty()) {
            return;
        }

        var ops = operators.stream()
            .sorted(Comparator.comparing(t -> t.getOperatorWrapper().getId(), Comparator.naturalOrder()))
            .collect(Collectors.toList());
        final var preOperators = new HashSet<Operator>();
        for (var op : ops) {
            call.accept(op);
            op.getInputPorts().stream()
                .filter(t -> !Objects.isNull(t.getConnection()))
                .map(t -> t.getConnection().getFromPort())
                .forEach(fromPort -> preOperators.add(fromPort.getParent()));
        }
        bft(preOperators, call);
    }

    /**
     * 执行每个宏算子的代码生成逻辑
     *
     * @param op 宏算子
     */
    private void operate(Operator op) {
        op.setSchemaUtil(this);
        op.run();
    }

    /**
     * 获取operator的定义, key为{@link Identifier#getCode()} 返回值, 目前为Operator类的全限定名
     * value为类型定义.
     *
     * @return 返回operator集
     */
    public static Map<String, Class<? extends Operator>> getCodeClassMap(
        Set<Class<? extends Operator>> operators) {
        return operators.stream()
            .filter(c -> !java.lang.reflect.Modifier.isAbstract(c.getModifiers()))
            .collect(Collectors.toMap(t -> {
                Operator bcg = InstantiationUtil.instantiate(t);
                var code = bcg.getCode();
                bcg = null;
                return code;
            }, t -> t));
    }

    /**
     * 创建场景代码上下文
     *
     * @param scene 场景类
     * @return 代码上下文
     */
    public CodeContext createCodeContext(Scene scene) {
        return CodeContext.newBuilder(scene.getEnvironment().getName()).scene(scene).build();
    }

    /**
     * 读取配置文件, 生成场景节点(对应于配置)
     *
     * @param filePath 配置文件路径
     * @return 场景节点
     */
    public static SceneNode readSceneFromFile(String filePath) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(filePath);
            return readScene(fis);
        } catch (FileNotFoundException e) {
            log.error("readScene error, file not exists {}", e.getMessage());
        }
        return null;
    }

    /**
     * 根据输入流, 生成场景节点(对应于配置)
     *
     * @param in 配置文件输入流
     * @return 场景节点
     */
    public static SceneNode readScene(InputStream in) {
        return readSceneInternal(in);
    }

    /**
     * 根据输入流, 生成场景节点(对应于配置)
     *
     * @param in 配置文件输入流
     * @return 场景节点
     */
    public static SceneNode readScene(String in) {
        return readSceneInternal(in);
    }

    private static SceneNode readSceneInternal(Object in) {
        final var objectMapper = new ObjectMapper();
        SceneNode scene = null;
        try {
            if (in instanceof String) {
                scene = objectMapper.readValue((String) in, SceneNode.class);
            } else if (in instanceof InputStream) {
                scene = objectMapper.readValue((InputStream) in, SceneNode.class);
            } else {
                return null;
            }

            scene.initialize();
            return scene;
        } catch (IOException e) {
            log.error("readScene error, exception {}", e.getMessage());
        }
        return null;
    }

    /**
     * 将外部场景节点(对应于配置文件)转换为内部场景类
     *
     * @param sceneNode 外部场景节点
     * @return 内部场景类
     */
    public static Scene convertToInternal(SceneNode sceneNode) {
        return new Scene(sceneNode);
    }


}
