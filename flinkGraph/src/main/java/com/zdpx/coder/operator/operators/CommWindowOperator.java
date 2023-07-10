package com.zdpx.coder.operator.operators;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.Connection;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.operator.FieldFunction;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.OperatorUtil;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.NameHelper;
import com.zdpx.coder.utils.TemplateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通过创建视图的形式进行开窗和窗口聚合
 * 支持开开窗方式：
 * TUMBLE    ---> TUMBLE(TABLE data, DESCRIPTOR(timecol), size)
 * HOP       ---> HOP(TABLE data, DESCRIPTOR(timecol), slide, size[, offset])
 * CUMULATE  ---> CUMULATE(TABLE data, DESCRIPTOR(timecol), step, size)
 * <p>
 * 普通聚合：
 * GROUP BY columName
 * <p>
 * 窗口聚合方式：
 * 1 GROUP BY
 * from <windows_open>
 * group by windows_start, window_end ,(OTHER_GROUP);
 * <p>
 * OTHER_GROUP暂不实现
 * 1.1 GROUPING SETS
 * 1.2 CUBE
 * 1.3 级联窗口聚合
 * 1.4 分组聚合
 * 1.5 OVER聚合
 */

public class CommWindowOperator extends Operator {

    public static final String TEMPLATE =
            String.format(
                    "<#import \"%s\" as e>CREATE VIEW ${tableName} AS SELECT <@e.fieldsProcess columns/> FROM ${inputTableName} " +
                            "<#if where??>WHERE ${where}</#if> " +
                            "<#if window??> ${window.windowFunction} ( TABLE ${window.table} , DESCRIPTOR(${window.descriptor}), " +
                            "<#if window.slide??>INTERVAL '${window.slide.timeSpan}' ${window.slide.timeUnit},</#if> " +
                            "<#if window.step ??>INTERVAL '${window.step.timeSpan}' ${window.step.timeUnit},</#if>" +
                            "INTERVAL '${window.size.timeSpan}' ${window.size.timeUnit}) " +
                            "</#if>" +
                            "<#if group??>GROUP BY " +
                            "<#if (group.aggregation) == \"group\" > ${group.column} " +
                            "<#elseif (group.aggregation) == \"windowGroup\"> ${group.windowsStart} , ${group.windowEnd} " +
                            "</#if>" +
                            "</#if>" +
                            "<#if orderBy??>ORDER BY <#list orderBy as o> `${o.order}` ${o.sort} </#list></#if>" +
                            "<#if limit?? && (limit?size!=0) >limit <#list limit as l> `${l}` <#sep>,</#sep></#list></#if>"
                    ,
                    Specifications.TEMPLATE_FILE);

    /**
     * 函数字段名常量
     */
    public static final String WHERE = "where";

    public static final String GROUP = "group";

    public static final String ORDER_BY = "orderBy";

    public static final String WINDOW = "window";

    public static final String SIZE = "size";
    public static final String SLIDE = "slide";
    public static final String STEP = "step";
    public static final String LIMIT = "limit";


    private InputPortObject<TableInfo> inputPortObject;
    private OutputPortObject<TableInfo> outputPortObject;

    @Override
    protected void initialize() {
        inputPortObject = registerInputObjectPort("input_0");
        outputPortObject = registerOutputObjectPort("output_0");
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        Map<String, Object> parameters = getParameterLists().get(0);
        List<FieldFunction> ffs = Operator.getFieldFunctions(null, parameters);
        List<String> functions =
                ffs.stream().map(FieldFunction::getFunctionName).collect(Collectors.toList());
        return Scene.getUserDefinedFunctionMaps().entrySet().stream()
                .filter(k -> functions.contains(k.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    protected boolean applies() {
        return true;
    }

    @Override
    protected void execute() {
        Map<String, Object> parameters = getFirstParameterMap();

        Object outputTableName = parameters.get("tableName");
        if(outputTableName==null){
            outputTableName = NameHelper.generateVariableName("CommWindowFunctionOperator");
        }
        //算子预览功能
        Connection<TableInfo> connection = inputPortObject.getConnection();
        String tableName = TABLE_NAME_DEFAULT;
        if(connection!=null){
            tableName = connection.getName();
        }

        List<FieldFunction> ffs = Operator.getFieldFunctions(tableName, parameters);
        Map<String, Object> p = new HashMap<>();
        p.put("tableName", outputTableName);
        p.put(Operator.FIELD_FUNCTIONS, ffs);
        p.put("inputTableName", tableName);
        p.put(WHERE, parameters.get(WHERE));
        p.put(LIMIT, parameters.get(LIMIT));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> order = (List<Map<String, Object>>) parameters.get(ORDER_BY);
        if (order != null && !order.isEmpty()) {
            p.put(ORDER_BY, order);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> windowList = (Map<String, Object>) parameters.get(WINDOW);
        if (windowList != null && !windowList.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> size = (Map<String, Object>) windowList.get(SIZE);
            windowList.putAll(size == null ? new HashMap<>() : size);
            @SuppressWarnings("unchecked")
            Map<String, Object> slide = (Map<String, Object>) windowList.get(SLIDE);
            windowList.putAll(slide == null ? new HashMap<>() : slide);
            @SuppressWarnings("unchecked")
            Map<String, Object> step = (Map<String, Object>) windowList.get(STEP);
            windowList.putAll(step == null ? new HashMap<>() : step);

            //将 window.table 改为输入表名称
            windowList.put("table", tableName);
            p.put(WINDOW, windowList);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> groupList = (Map<String, Object>) parameters.get(GROUP);
        if (groupList != null && !groupList.isEmpty()) {
            p.put(GROUP, groupList);
        }

        String sqlStr = TemplateUtils.format("CommWindowFunction", p, TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        OperatorUtil.postTableOutput(
                outputPortObject,
                outputTableName.toString(),
                Specifications.convertFieldFunctionToColumns(ffs));
    }

}