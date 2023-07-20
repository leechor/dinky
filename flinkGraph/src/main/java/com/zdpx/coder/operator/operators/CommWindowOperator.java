package com.zdpx.coder.operator.operators;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.*;
import com.zdpx.coder.operator.*;
import com.zdpx.coder.utils.NameHelper;
import com.zdpx.coder.utils.TemplateUtils;

import java.util.ArrayList;
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

    private InputPortObject<TableInfo> inputPortObject;
    private OutputPortObject<TableInfo> outputPortObject;

    @Override
    protected void initialize() {
        inputPortObject = registerInputObjectPort(INPUT_0);
        outputPortObject = registerOutputObjectPort(OUTPUT_0);
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        Map<String, Object> parameters = getParameterLists().get(0);
        List<FieldFunction> ffs = Operator.getFieldFunctions(null, parameters,new ArrayList<>());
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
    protected Map<String, Object> formatOperatorParameter() {
        Map<String, Object> parameters = getFirstParameterMap();

        Object outputTableName = parameters.get(TABLE_NAME);
        if (outputTableName == null || outputTableName.equals("")) {
            outputTableName = NameHelper.generateVariableName("CommWindowFunctionOperator");
        }
        //算子预览功能
        String tableName = TABLE_NAME_DEFAULT;
        List<Column> columns = new ArrayList<>();
        if (inputPortObject.getConnection() != null) {
            tableName = inputPortObject.getOutputPseudoData().getName();
            columns = inputPortObject.getConnection().getFromPort().getPseudoData().getColumns();
        }

        List<FieldFunction> ffs = Operator.getFieldFunctions(tableName, parameters,columns);
        Map<String, Object> p = new HashMap<>();
        p.put(TABLE_NAME, outputTableName);
        p.put(Operator.COLUMNS, ffs);
        p.put(INPUT_TABLE_NAME, tableName);
        p.put(WHERE, parameters.get(WHERE));
        p.put(LIMIT, parameters.get(LIMIT));
        p.put(ID, parameters.get(ID));
        p.put(CONFIG,formatProcessing(parameters));

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
        return p;
    }

    /**
     * 校验内容：
     * 输入字段名拼写错误
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);
        model.setTableName(map.get(TABLE_NAME).toString());
        List<String> edge = new ArrayList<>();

        List<String> list = new ArrayList<>();
        Map<String, List<String>> portInformation = new HashMap<>();

        @SuppressWarnings("unchecked")
        List<FieldFunction> columns = (List<FieldFunction>) map.get(COLUMNS);
        @SuppressWarnings("unchecked")
        List<Map<String, String>> config = (List<Map<String, String>>) map.get(CONFIG);
        List<String> inputName = config.stream().map(item -> item.get(NAME)).collect(Collectors.toList());

        for(FieldFunction column : columns){
            for(int i=0;i<inputName.size();i++){
                if(column.getOutName().equals(inputName.get(i))){
                    break;
                }
                if(i==inputName.size()-1){
                    list.add("算子输入不包含该字段, 未匹配的字段名： "+ column.getOutName());
                }
            }
        }

        if(!list.isEmpty()){
            model.setColor(RED);
            edge.add(getInputPorts().get(INPUT_0).getConnection().getId());
            model.setEdge(edge);
            portInformation.put(inputPortObject.getName(),list);
            model.setPortInformation(portInformation);
        }
        this.getSchemaUtil().getGenerateResult().addCheckInformation(model);
    }

    @Override
    protected void execute(Map<String, Object> p) {

        String sqlStr = TemplateUtils.format("CommWindowFunction", p, TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        @SuppressWarnings("unchecked")
        List<FieldFunction> ffs = (List<FieldFunction>) p.get(Operator.COLUMNS);
        OperatorUtil.postTableOutput(
                outputPortObject,
                p.get(TABLE_NAME).toString(),
                Specifications.convertFieldFunctionToColumns(ffs));
    }

}