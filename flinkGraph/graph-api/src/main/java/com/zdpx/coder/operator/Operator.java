/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.zdpx.coder.operator;

import com.zdpx.coder.SceneCode;
import com.zdpx.coder.graph.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.table.functions.UserDefinedFunction;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.reflections.Reflections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import com.zdpx.coder.Specifications;
import com.zdpx.coder.utils.JsonSchemaValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * 宏算子抽象类
 *
 * @author Licho Sun
 */
@Slf4j
public abstract class Operator extends Node implements Runnable {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, InputPort<? extends PseudoData<?>>> inputPorts = new HashMap<>();

    private Map<String, OutputPort<? extends PseudoData<?>>> outputPorts = new HashMap<>();

    protected Map<String, String> userFunctions;
    private SceneCode sceneCode;

    protected String type;

    protected JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();

    @SuppressWarnings({"NullAway.Init", "PMD.UnnecessaryConstructor"})
    protected Operator() {
        this(null);
    }

    protected Operator(NodeWrapper nodeWrapper) {
        this.nodeWrapper = nodeWrapper;
        initialize();
        definePropertySchema();
    }

    public String getType() {
        return type;
    }

    /**
     * 获取所有{@link Operator}子类, 即所有节点定义信息
     *
     * @return 节点定义集合
     */
    public static Set<Class<? extends Operator>> getAllOperatorGenerators() {
        Reflections reflections = new Reflections("com.zdpx.coder.operators");
        return reflections.getSubTypesOf(Operator.class);
    }

    //处理两种类型的数据，对象或数组
    protected static List<Map<String, Object>> getParameterLists(@Nullable String parametersStr) {
        List<Map<String, Object>> parametersLocal = new ArrayList<>();

        if (Strings.isNullOrEmpty(parametersStr)) {
            return parametersLocal;
        }
        try {
            if (!parametersStr.subSequence(0, 1).equals("[")) {
                parametersLocal.add(objectMapper.readValue(parametersStr, new TypeReference<Map<String, Object>>() {
                }));

            } else {
                parametersLocal = objectMapper.readValue(parametersStr, new TypeReference<List<Map<String, Object>>>() {
                });
            }

        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }
        return parametersLocal;
    }

    @Override
    public void run() {
        if (Objects.isNull(nodeWrapper)) {
            log.error("{} operator not wrapper.", this.getClass().getName());
            return;
        }
        log.info(String.format("execute operator id %s", this.getId()));
        if (applies()) {
            validParameters();
            generateUdfFunctionByInner();
            Map<String, Object> map = formatOperatorParameter();//格式化参数信息
            if(checkOrNot()){
                generateCheckInformation(map);//生成校验信息相关逻辑
            }
            execute(map);//生成sql相关逻辑
        }
    }

    //预览功能不走算子校验
    protected boolean checkOrNot(){
        for(Map.Entry<String, InputPort<? extends PseudoData<?>>> in : getInputPorts().entrySet()){
            if(in.getValue().getConnection()==null){
                return false;
            }
        }
        for(Map.Entry<String, OutputPort<? extends PseudoData<?>>> in : getOutputPorts().entrySet()){
            if(in.getValue().getConnection()==null){
                return false;
            }
        }
        return true;
    }

    protected void registerUdfFunctions(List<FieldFunction> fieldFunctions) {
        fieldFunctions.stream()
                .filter(t -> !Strings.isNullOrEmpty(t.getFunctionName()))
                .forEach(t -> registerUdfFunctions(t.getFunctionName()));
    }

    protected void registerUdfFunctions(String functionName) {
        sceneCode.getUdfFunctionMap().entrySet().stream()
                .filter(t -> t.getKey().equals(functionName))
                .findAny()
                .ifPresent(
                        t ->
                                this.getSceneCode()
                                        .getGenerateResult()
                                        .registerUdfFunction(t.getKey(), t.getValue()));
        sceneCode.getUdfFunctionMap().remove(functionName);
    }

    protected void registerUdfFunctions(String functionName, String clazz) {
        this.getSceneCode().getGenerateResult().registerUdfFunction(functionName, clazz);
    }

    protected void generate(String sqlStr) {
        this.getSceneCode().getGenerateResult().generate(sqlStr);
    }

    /**
     * 校验输入参数是否正确.
     */
    protected void validParameters() {
        String[] split = this.getClass().getName().split("\\.");//获取算子名称
        String parametersString = getParametersString();
        if (jsonSchemaValidator.getSchema() == null) {
            log.warn("{} operator not parameter validation schema.", split[split.length-1]);
            return;
        }

        Set<ValidationMessage> validationMessages = jsonSchemaValidator.validate(parametersString);
        if (!CollectionUtils.isEmpty(validationMessages)) {
            for (ValidationMessage validationMessage : validationMessages) {
                log.error("{} operator parameters error: {}", split[split.length-1], validationMessage);
            }
            throw new InvalidParameterException(validationMessages.toString());
        }
    }

    /**
     * 初始化信息,输出/输入端口应该在该函数中完成注册定义
     */
    protected abstract void initialize();

    @Override
    public String getSpecification() {
        String simpleName = this.getClass().getSimpleName();
        return Specifications.readSpecializationFileByClassName(simpleName);
    }

    /**
     * 声明用到的{@link UserDefinedFunction}自定义函数, 初始化时会根据实际声明情况生成注册到flink的代码
     *
     * @return 自定义函数集
     */
    protected abstract Map<String, String> declareUdfFunction();

    /**
     * 该算子逻辑是否执行
     *
     * @return 是否执行
     */
    protected abstract boolean applies();

    protected Map<String, Object> getFirstParameterMap() {
        List<Map<String, Object>> parameterLists = getParameterLists();//增加config中的字段
        Map<String, Object> map = new HashMap<>();
        for (Map<String, Object> p : parameterLists) {
            map.putAll(p);
        }
        return map;
    }

    /**
     * 格式化参数信息
     */
    protected abstract Map<String, Object> formatOperatorParameter();

    /**
     * 生成校验信息相关逻辑
     */
    protected abstract void generateCheckInformation(Map<String, Object> map);

    /**
     * 逻辑执行函数
     */
    protected abstract void execute(Map<String, Object> result);

    /**
     * 注册输入端口
     *
     * @param name 端口名称
     * @return 输入端口
     */
    protected <S extends PseudoData<S>, T extends InputPort<S>> T registerInputPort(
            String name, BiFunction<Operator, String, T> constructor) {
        final T portObject = constructor.apply(this, name);
        inputPorts.put(name, portObject);
        return portObject;
    }

    protected <T extends PseudoData<T>> InputPortObject<T> registerInputObjectPort(String name) {
        return registerInputPort(name, InputPortObject<T>::new);
    }

    /**
     * 注册输出端口
     *
     * @param name 端口名称
     * @return 输出端口
     */
    protected <S extends PseudoData<S>, T extends OutputPort<S>> T registerOutputPort(
            String name, BiFunction<Operator, String, T> constructor) {
        final T portObject = constructor.apply(this, name);
        outputPorts.put(name, portObject);
        return portObject;
    }

    protected <T extends PseudoData<T>> OutputPortObject<T> registerOutputObjectPort(String name) {
        return registerOutputPort(name, OutputPortObject<T>::new);
    }

    /**
     * 根据场景配置文件参数内容, 转换为计算图的参数形式
     *
     * @param parametersStr 配置文件参数内容
     */
    protected void handleParameters(@Nullable String parametersStr) {
        List<Map<String, Object>> parametersLocal = getParameterLists(parametersStr);

        if (CollectionUtils.isEmpty(parametersLocal)) {
            return;
        }

        parametersLocal.forEach(
                p -> p.forEach((key, value) -> getParameters().getParameterList().stream()
                        .filter(pp -> Objects.equals(pp.getKey(), key))
                        .findAny()
                        .ifPresent(
                                pp -> {
                                    pp.setKey(key);
                                    pp.setValue(value);
                                })));
    }

    /**
     * 设置宏算子参数的校验信息.
     */
    protected void definePropertySchema() {
        String propertySchema = getSpecification();
        if (Strings.isNullOrEmpty(propertySchema)) {
            log.debug("operator {} don't have schema file.", this.getClass().getSimpleName());
            return;
        }
        JsonSchema schema = jsonSchemaValidator.getJsonSchemaFromStringContent(propertySchema);
        jsonSchemaValidator.setSchema(schema);
    }

    /**
     * 获取宏算子参数
     *
     * @return 非结构化参数信息
     */
    protected List<Map<String, Object>> getParameterLists() {
        List<Map<String, Object>> parameterLists = getParameterLists(this.nodeWrapper.getParameters());
        Map<String, Object> map = new HashMap<>();
        map.put(OperatorSpecializationFieldConfig.CONFIG, getParameterLists(this.nodeWrapper.getConfig()));
        map.put(OperatorSpecializationFieldConfig.ID, id);//节点校验需要使用

        parameterLists.add(map);
        return parameterLists;
    }

    public String getParametersString() {
        return this.getOperatorWrapper().getParameters();
    }

    /**
     * 生成内部用户自定义函数(算子)对应的注册代码, 以便在flink sql中对其进行引用调用.
     */
    private void generateUdfFunctionByInner() {
        Map<String, String> ufs = this.getUserFunctions();
        if (ufs == null || ufs.isEmpty()) {
            return;
        }

        Map<String, String> udfFunctions = getSceneCode().getUdfFunctionMap();
        Sets.difference(ufs.entrySet(), udfFunctions.entrySet())
                .forEach(
                        u ->
                                this.getSceneCode()
                                        .getGenerateResult()
                                        .registerUdfFunction(u.getKey(), u.getValue()));
        udfFunctions.putAll(ufs);
    }

    public static JsonNode getNestValue(Map<String, Object> maps, String path) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        root = mapper.valueToTree(maps);
        return root.at(path);
    }

    public static Map<String, Object> getNestMapValue(Map<String, Object> maps, String path) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        root = mapper.valueToTree(maps);
        return getJsonAsMap(root.at(path));
    }

    @SuppressWarnings("unchecked")
    public static List<FieldFunction> getFieldFunctions(
            String primaryTableName, Map<String, Object> parameters , List<Column> inputColumn, List<Map<String, Object>> inputColumns) {
        return FieldFunction.analyzeParameters(
                primaryTableName, (List<Map<String, Object>>) parameters.get(OperatorSpecializationFieldConfig.COLUMNS), true,inputColumn, inputColumns);
    }

    public static Map<String, Object> getJsonAsMap(JsonNode inputs) {
        return new ObjectMapper().convertValue(inputs, new TypeReference<Map<String, Object>>() {
        });
    }

    public static List<Column> getColumnFromFieldFunctions(List<FieldFunction> ffs) {
        return ffs.stream()
                .map(t -> new Column(getColumnName(t.getOutName() == null ? t.getParameters().get(0).toString() : t.getOutName()), t.getOutType()))
                .collect(Collectors.toList());
    }

    public static String getColumnName(String fullColumnName) {
        return fullColumnName.substring(fullColumnName.lastIndexOf('.') + 1);
    }

    //处理config中的数据格式和勾选情况
    public List<Map<String, Object>> formatProcessing(Map<String, Object> dataModel) {

        List<Map<String, Object>> input = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Map<String, List<Map<String, Object>>>> config = (List<Map<String, List<Map<String, Object>>>>) dataModel.get(OperatorSpecializationFieldConfig.CONFIG);
        if (config.isEmpty()) {
            return input;
        }

        //从config中获取输入
        for (Map.Entry<String, List<Map<String, Object>>> m2 : config.get(0).entrySet()) {
            String[] split = m2.getKey().split("&");
            m2.getValue().forEach(l->{
                if ((boolean) l.get(OperatorSpecializationFieldConfig.FLAG)) {
                    l.put(OperatorSpecializationFieldConfig.TABLE_NAME,split[split.length-1]);
                    input.add(l);
                }
            });
        }

        //删除没有勾选的字段
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> output = (List<Map<String, Object>>) dataModel.get(OperatorSpecializationFieldConfig.COLUMNS);
        output.removeIf(s -> !(boolean) s.get(OperatorSpecializationFieldConfig.FLAG));

        return input;
    }

    // region g/s

    public Map<String, String> getUserFunctions() {
        return userFunctions;
    }

    public void setUserFunctions(Map<String, String> userFunctions) {
        this.userFunctions = userFunctions;
    }

    public NodeWrapper getOperatorWrapper() {
        return nodeWrapper;
    }

    public void setOperatorWrapper(NodeWrapper originNodeWrapper) {
        if (originNodeWrapper == null) {
            return;
        }
        super.setNodeWrapper(originNodeWrapper);
        handleParameters(originNodeWrapper.getParameters());
        setUserFunctions(declareUdfFunction());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Operator operator = (Operator) o;
        return nodeWrapper.equals(operator.nodeWrapper);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
//        return Objects.hash(nodeWrapper);
    }

    public Map<String, InputPort<? extends PseudoData<?>>> getInputPorts() {
        return inputPorts;
    }

    public void setInputPorts(Map<String, InputPort<? extends PseudoData<?>>> inputPorts) {
        this.inputPorts = inputPorts;
    }

    public Map<String, OutputPort<? extends PseudoData<?>>> getOutputPorts() {
        return outputPorts;
    }

    public void setOutputPorts(Map<String, OutputPort<? extends PseudoData<?>>> outputPorts) {
        this.outputPorts = outputPorts;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public SceneCode getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(SceneCode sceneCodeBuilder) {
        this.sceneCode = sceneCodeBuilder;
    }

    // endregion
}
