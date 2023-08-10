package com.zdpx.operators;

import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.operator.Column;
import com.zdpx.coder.operator.Operator;

import java.util.*;

import static com.zdpx.coder.utils.TableDataStreamConverter.assembleNewTableInfo;

public class CustomerOperator extends Operator {

    private OutputPortObject<TableInfo> outputPortObject;

    public static final String TEMPLATE =
            String.format(
                    "<#import \"%s\" as e>" +
                            "<#list Table as item>${item.sql}" +
                            "<#if item.type??>" +
                            "<#if item.type == \"inputTableName\">${item.column}" +
                            "<#elseif item.type == \"outPutTableName\">${item.column}" +
                            "<#elseif item.type == \"fieldName\"><#if item.needBrackets == \"true\">(</#if><@e.fieldsProcess item.columns/><#if item.needBrackets == \"true\">)</#if>" +
                            "<#elseif item.type == \"outPutFieldName\"><#if item.needBrackets == \"true\">(</#if><@e.fieldsProcess item.columns/><#if item.needBrackets == \"true\">)</#if>" +
                            "</#if> " +
                            "</#if> " +
                            "</#list>"
                    , Specifications.TEMPLATE_FILE);

    public static final String STATEMENT_BODY = "statementBody";
    public static final String PLACEHOLDER = "placeholder";
    public static final String TYPE = "type";
    public static final String OUT_PUT_TABLE_NAME = "outPutTableName";
    public static final String COLUMNS = "columns";
    public static final String NAME = "name";
    public static final String FIELD_FUNCTIONS = "fieldFunctions";
    public static final String OUT_PUT_FIELD_NAME = "outPutFieldName";
    public static final String FIELD_NAME = "fieldName";

    @Override
    protected void initialize() {
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

        if(getInputPorts()!=null&&getInputPorts().isEmpty()){
            //获取sql 中的输入字段信息  todo 表名.字段名

            //获取input中的输入字段信息

            //获取input中的表名称

            //替换sql中的表名称

        }



//        @SuppressWarnings("unchecked")
//        String statementBody = (String) parameters.get(STATEMENT_BODY);
//
//        //placeholder : inputTableName ,outPutTableName ,fieldName ,outPutFieldName
//        @SuppressWarnings("unchecked")
//        List<HashMap<String, Object>> placeholder = (ArrayList<HashMap<String, Object>>) parameters.get(PLACEHOLDER);
//
//        List<FieldFunction> outPutFfs = new ArrayList<>();
//
//        //排序，防止占位符名称和数组中顺序不一致
//        String[] arrays = new String[placeholder.size()];
//        String outputTableName = placeholder.stream().filter(item -> item.get(TYPE).equals(OUT_PUT_TABLE_NAME)).findFirst().orElse(null).get(COLUMNS).toString();
//        HashMap<String, HashMap<String, Object>> order = new HashMap<>();
//
//        for (int i = 0; i < placeholder.size(); i++) {
//            HashMap<String, Object> stringObjectHashMap = placeholder.get(i);
//            arrays[i] = String.valueOf(stringObjectHashMap.get(NAME));
//            order.put(arrays[i], stringObjectHashMap);
//            String type = stringObjectHashMap.get(TYPE).toString();
//            if (type.contains(FIELD_NAME)) {
//                List<FieldFunction> ffs = Operator.getFieldFunctions(outputTableName, stringObjectHashMap,new ArrayList<>());
//                stringObjectHashMap.put(FIELD_FUNCTIONS, ffs);
//                placeholder.set(i, stringObjectHashMap);
//                if (type.equals(OUT_PUT_FIELD_NAME)) {
//                    outPutFfs = ffs;
//                }
//            }
//        }
//        for (int i = 0; i < arrays.length - 1; i++) {
//            for (int j = 0; j < arrays.length - 1 - i; j++) {
//                int index1 = statementBody.indexOf(arrays[j]);
//                int index2 = statementBody.indexOf(arrays[j + 1]);
//                if (index1 > index2) {
//                    String temp = arrays[j];
//                    arrays[j] = arrays[j + 1];
//                    arrays[j + 1] = temp;
//                }
//            }
//        }
//        List<HashMap<String, Object>> orderList = new ArrayList<>();
//
//        //截断
//        for (int i = 0; i < arrays.length; i++) {
//            String[] split = statementBody.split(arrays[i]);
//            HashMap<String, Object> map = order.get(arrays[i]);
//            map.put("sql", split[0]);
//            statementBody = split[1];
//            orderList.add(map);
//        }
//        //组装输入
//        HashMap<String, Object> last = new HashMap<>();
//        last.put("sql", statementBody);
//        orderList.add(last);
//
//
        HashMap<String, Object> result = new HashMap<>();
//        result.put("Table", orderList);
//        result.put("outputTableName", outputTableName);
//        result.put("outPutFfs", outPutFfs);
//        result.put("id", parameters.get("id"));

        return result;
    }

    /**
     * 校验内容：
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get("id").toString());
        model.setColor("green");
        model.setTableName(map.get("tableName").toString());

        this.getSceneCode().getGenerateResult().addCheckInformation(model);
    }

    @Override
    protected void execute(Map<String, Object> result) {

        this.getSceneCode().getGenerateResult().generate((String)result.get("sql"));//本算子输入的sql不用转换

        if(getOutputPorts()!=null&&!getOutputPorts().isEmpty()){

            @SuppressWarnings("unchecked")
            List<Column> tableInfo = (List<Column>)result.get(TABLE_INFO);//认为tableInfo中包含所有不冲突的输入字段
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> column = (List<Map<String, Object>>)result.get(COLUMN);//所有输出字段名称

            getOutputPorts()
                    .values()
                    .forEach(t -> {
                        @SuppressWarnings("unchecked")
                        OutputPortObject<TableInfo> out = (OutputPortObject<TableInfo>) t;
                        out.setPseudoData(assembleNewTableInfo(new TableInfo("InputTableName",tableInfo), column));//不关心表名
                    });
        }
    }


}
