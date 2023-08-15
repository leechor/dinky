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

package com.zdpx.coder.operators;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.operator.FieldFunction;
import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.Column;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.OperatorUtil;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.NameHelper;
import com.zdpx.coder.utils.TemplateUtils;
import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

/**
 * <b>Complex Event Processing</b> operator, which allows for pattern detection in event streams.
 * the sql statement follow <I>Row Pattern Recognition</I>(ISO/IEC TR 19075-5:2016), using the
 * <b>MATCH_RECOGNIZE</b> clause for complex event processing in SQL.
 *
 * <p>A <b>MATCH_RECOGNIZE</b> clause enables the following tasks:
 *
 * <ul>
 *   <li>Logically partition and order the data that is used with the <i>PARTITION BY</i> and
 *       <i>ORDER BY</i> clauses.
 *   <li>Define patterns of rows to seek using the <i>PATTERN</i> clause. These patterns use a
 *       syntax similar to that of regular expressions.
 *   <li>The logical components of the row pattern variables are specified in the <i>DEFINE</i>
 *       clause.
 *   <li>Define measures, which are expressions usable in other parts of the SQL query, in the
 *       MEASURES clause.
 * </ul>
 *
 * <p>Every <b>MATCH_RECOGNIZE</b> query consists of the following clauses:
 *
 * <ul>
 *   <li><b>PARTITION BY</b> - defines the logical partitioning of the table; similar to a GROUP BY
 *       operation.
 *   <li><b>ORDER BY</b> - specifies how the incoming rows should be ordered; this is essential as
 *       patterns depend on an order.
 *   <li><b>MEASURES</b> - defines output of the clause; similar to a SELECT clause.
 *   <li><b>ONE ROW PER MATCH</b> - output mode which defines how many rows per match should be
 *       produced.
 *   <li><b>AFTER MATCH SKIP</b> - specifies where the next match should start; this is also a way
 *       to control how many distinct matches a single event can belong to.
 *   <li><b>PATTERN</b> - allows constructing patterns that will be searched for using a regular
 *       expression-like syntax .
 *   <li><b>DEFINE</b> - this section defines the conditions that the pattern variables must
 *       satisfy.
 * </ul>
 *
 * <p>The following example illustrates the syntax for basic pattern recognition:
 *
 * <pre>{@code
 * SELECT T.aid, T.bid, T.cid
 * FROM MyTable
 *     MATCH_RECOGNIZE (
 *       PARTITION BY userid
 *       ORDER BY proctime
 *       MEASURES
 *         A.id AS aid,
 *         B.id AS bid,
 *         C.id AS cid
 *       PATTERN (A B C)
 *       DEFINE
 *         A AS name = 'a',
 *         B AS name = 'b',
 *         C AS name = 'c'
 *     ) AS T
 * }</pre>
 *
 * <note> Currently, the MATCH_RECOGNIZE clause can only be applied to an <b>append table</b>
 * </note>
 *
 * @author Licho Sun
 */
public class CepOperator extends Operator {

    private static final String TEMPLATE =
            MessageFormat.format(
                    "<#import \"{0}\" as e>\nCREATE VIEW $'{'{1}.{2}'}' \nAS \n<@e.cepFunction {1}/>",
                    Specifications.TEMPLATE_FILE, CEP, OUTPUT_TABLE_NAME);

    private final ObjectMapper mapper = new ObjectMapper();
    private InputPortObject<TableInfo> inputPortObject;
    private OutputPortObject<TableInfo> outputPortObject;

    @Override
    protected void initialize() {
        inputPortObject = registerInputObjectPort(INPUT_0);
        outputPortObject = registerOutputObjectPort(OUTPUT_0);
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return new HashMap<>();
    }

    @Override
    protected boolean applies() {
        return true;
    }

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        Map<String, Object> parameters = getFirstParameterMap();
        final String partition = (String) parameters.get(PARTITION);//定义表的逻辑分区

        String orderBy = (String) parameters.get(ORDER_BY);//指定传入行的排序方式

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> defineList = (List<Map<String, Object>>) parameters.get(DEFINES);//定义模式的具体含义
        List<Define> defines =
                mapper.convertValue(defineList, new TypeReference<List<Define>>() {
                });

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> patternList =
                (List<Map<String, Object>>) parameters.get(PATTERNS);//定义事件
        List<Pattern> patterns =
                mapper.convertValue(patternList, new TypeReference<List<Pattern>>() {
                });

        SkipStrategy skipStrategy =
                mapper.convertValue(parameters.get(SKIP_STRATEGY), SkipStrategy.class);//AFTER 跳过策略

        //算子预览的特殊处理
        TableInfo tableInfo = null;
        String tableName = TABLE_NAME_DEFAULT;
        List<Column> columns = new ArrayList<>();
        if (inputPortObject.getConnection() != null) {
            tableName = inputPortObject.getOutputPseudoData().getName();
            tableInfo = inputPortObject.getOutputPseudoData();
            columns = inputPortObject.getConnection().getFromPort().getPseudoData().getColumns();
        }

        Object outputTableName = parameters.get(TABLE_NAME);
        if (outputTableName == null || outputTableName.equals("")) {
            outputTableName = NameHelper.generateVariableName("CepOperator");
        }

        List<Map<String, Object>> config = formatProcessing(parameters);
        @SuppressWarnings("unchecked")
        List<FieldFunction> ffs =
                FieldFunction.analyzeParameters(
                        outputTableName.toString(), (List<Map<String, Object>>) parameters.get(COLUMNS), false, columns,config); //根据匹配成功的输入事件构造输出事件,字段名不加表名

        //4 定义匹配成功后的输出方式  ONE ROW PER MATCH/ALL ROWS PER MATCH
        String outPutMode = (String) parameters.get(OUT_PUT_MODE);

        //6 定义匹配事件的最大时间跨度,格式： WITHIN INTERVAL "string" timeUnit
        String timeSpan = null;
        String timeUnit = null;
        if (!parameters.get(TIME_SPAN).toString().equals("0")) {
            timeSpan = String.valueOf(parameters.get(TIME_SPAN));
            timeUnit = (String) parameters.get(TIME_UNIT);
        }

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(OUTPUT_TABLE_NAME, outputTableName);
        parameterMap.put(INPUT_TABLE_NAME, tableName);
        parameterMap.put(PARTITION, partition);
        parameterMap.put(ORDER_BY, orderBy);
        parameterMap.put(OUT_PUT_MODE, outPutMode);
        parameterMap.put(SKIP_STRATEGY, skipStrategy);
        parameterMap.put(COLUMNS, ffs);
        parameterMap.put(TIME_SPAN, timeSpan);
        parameterMap.put(TIME_UNIT, timeUnit);
        parameterMap.put(
                DEFINES, defines.stream().map(Define::toString).collect(Collectors.toList()));
        parameterMap.put(
                PATTERNS, patterns.stream().map(Pattern::toString).collect(Collectors.toList()));

        parameterMap.put(TABLE_INFO, tableInfo);
        parameterMap.put(ID, parameters.get(ID));
        parameterMap.put(CONFIG,config);

        return parameterMap;
    }

    /**
     * 校验内容：
     * <p>
     * 输入节点是否包含时间属性字段
     * ORDER BY需要为时间属性字段
     * 使用了未定义的事件
     * partition、orderBy不属于输入参数
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);
        model.setTableName(map.get(OUTPUT_TABLE_NAME).toString());
        List<String> edge = new ArrayList<>();

        Map<String, List<String>> portInformation = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<Column> collect = ((TableInfo) map.get(TABLE_INFO)).getColumns().stream().filter(item -> item.getType()!=null&&item.getType().contains("TIMESTAMP")).collect(Collectors.toList());
        if (collect.isEmpty()) {
            list.add("CEP算子入参需要包含时间属性字段，类型包括TIMESTAMP(3)、TIMESTAMP_LTZ(3)等");
        }else{
            Column column = collect.stream().filter(item -> item.getName().equals(map.get(ORDER_BY).toString())).findFirst().orElse(null);
            if (column == null) {
                list.add("ORDER BY 对应的字段，必须是时间属性字段");
            }
        }

        @SuppressWarnings("unchecked")
        List<String> defines = (List<String>) map.get(DEFINES);
        String patterns = map.get(PATTERNS).toString();
        String s = defines.stream().filter(item -> !patterns.contains(item.split(" AS")[0])).findFirst().orElse(null);
        if (s != null) {
            list.add("DEFINE 使用了 PATTERN 中未声明的事件");
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> config = (List<Map<String, Object>>)map.get(CONFIG);
        Object par = config.stream().filter(item -> item.get(NAME).equals(map.get(PARTITION))).findFirst().orElse(null);
        Object ord = config.stream().filter(item -> item.get(NAME).equals(map.get(ORDER_BY))).findFirst().orElse(null);
        if(par==null){
            list.add("输入连接桩中未寻找到 PARTITION 中定义的字段");
        }
        if(ord==null){
            list.add("输入连接桩中未寻找到 ORDER_BY 中定义的字段");
        }

        if(!list.isEmpty()){
            model.setColor(RED);
            edge.add(getInputPorts().get(INPUT_0).getConnection().getId());
            model.setEdge(edge);
            portInformation.put(INPUT_0,list);
            model.setPortInformation(portInformation);
        }
        this.getSceneCode().getGenerateResult().addCheckInformation(model);
    }

    @Override
    protected void execute(Map<String, Object> parameterMap) {

        Map<String, Object> result = new HashMap<>();
        result.put(CEP, parameterMap);
        String sqlStr = TemplateUtils.format(this.getClass().getName(), result, TEMPLATE);
        this.getSceneCode().getGenerateResult().generate(sqlStr);
        @SuppressWarnings("unchecked")
        List<Column> columns = Specifications.convertFieldFunctionToColumns((List<FieldFunction>) parameterMap.get(COLUMNS));

        TableInfo tableInfo = (TableInfo) parameterMap.get(TABLE_INFO);
        if (parameterMap.get(TABLE_INFO) != null) {
            Column first = tableInfo.getColumns().stream().filter(t -> t.getName().equals(parameterMap.get(PARTITION))).findFirst().orElse(null);
            if(first!=null){
                columns.removeIf(f -> f.getName().equals(first.getName()));
                columns.add(first);
            }
        }
        OperatorUtil.postTableOutput(outputPortObject, parameterMap.get(OUTPUT_TABLE_NAME).toString(), columns);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getSpecialTypeList(
            Map<String, Object> parameters, String key, Class<T> type) {
        List<Map<String, Object>> measureList = (List<Map<String, Object>>) parameters.get(key);
        return mapper.convertValue(measureList, new TypeReference<List<T>>() {
        });
    }

    /**
     * The {@link Define DEFINE} and MEASURES keywords have similar meanings to the WHERE and SELECT
     * clauses in a simple SQL query.
     *
     * <p>The MEASURES clause defines what will be included in the output of a matching pattern. It
     * can project columns and define expressions for evaluation. The number of produced rows
     * depends on the output mode setting.
     *
     * <p>The {@link Define DEFINE} clause specifies conditions that rows have to fulfill in order
     * to be classified to a corresponding pattern variable. If a condition is not defined for a
     * pattern variable, a default condition will be used which evaluates to true for every row.
     */
    static class Define {
        private static final String AS = "AS";
        /**
         * Pattern Variable
         */
        private String variable;
        /**
         * condition like where sql statement.
         */
        private String condition;

        public Define() {
            // for jackson
        }

        public String generateStatement() {
            return variable + " " + AS + " " + condition;
        }

        @Override
        public String toString() {
            return generateStatement();
        }

        // region g/s
        public String getVariable() {
            return variable;
        }

        public void setVariable(String variable) {
            this.variable = variable;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }
        // endregion
    }

    static class Pattern {
        /**
         * pattern variable name
         */
        private String variable;
        /**
         * pattern variable quantifier
         */
        private String quantifier;

        public Pattern() {
            //            for jackson
        }

        @Override
        public String toString() {
            return generateStatement();
        }

        public String generateStatement() {
            return variable + quantifier;
        }

        // region g/s
        public String getVariable() {
            return variable;
        }

        public void setVariable(String variable) {
            this.variable = variable;
        }

        public String getQuantifier() {
            return quantifier;
        }

        public void setQuantifier(String quantifier) {
            this.quantifier = quantifier;
        }
        // endregion

    }

    /**
     * The AFTER MATCH SKIP clause specifies where to start a new matching procedure after a
     * complete match was found.
     */
    static class SkipStrategy {
        /**
         * SKIP PAST LAST ROW - resumes the pattern matching at the next row after the last row of
         * the current match.
         */
        public static final String LAST_ROW = "LAST_ROW";
        /**
         * <b>SKIP TO NEXT ROW</b> - continues searching for a new match starting at the next row
         * after the starting row of the match.
         */
        public static final String NEXT_ROW = "NEXT_ROW";
        /**
         * <b>SKIP TO LAST variable</b> - resumes the pattern matching at the last row that is
         * mapped to the specified pattern variable.
         */
        public static final String LAST = "LAST";
        /**
         * <b>SKIP TO FIRST variable</b> - resumes the pattern matching at the first row that is
         * mapped to the specified pattern variable.
         */
        public static final String FIRST = "FIRST";

        /**
         * strategy
         */
        private String strategy;
        /**
         * pattern variable
         */
        private String variable;

        public String generateStatement() {
            switch (strategy) {
                case LAST_ROW: {
                    return "PAST LAST ROW";
                }
                case NEXT_ROW: {
                    return "TO NEXT ROW";
                }
                case LAST: {
                    return "TO LAST " + variable;
                }
                case FIRST: {
                    return "TO FIRST " + variable;
                }
                default:
                    return "";
            }
        }

        @Override
        public String toString() {
            return generateStatement();
        }

        // region g/s
        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public String getVariable() {
            return variable;
        }

        public void setVariable(String variable) {
            this.variable = variable;
        }
        // endregion
    }
}
