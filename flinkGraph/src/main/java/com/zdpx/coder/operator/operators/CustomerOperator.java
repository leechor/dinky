package com.zdpx.coder.operator.operators;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.FieldFunction;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.OperatorUtil;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.TemplateUtils;

import java.util.*;

public class CustomerOperator extends Operator {

    private OutputPortObject<TableInfo> outputPortObject;

    public static final String TEMPLATE =
            String.format(
            "<#import \"%s\" as e>" +
            "<#list Table as item>${item.sql}" +
                    "<#if item.type??>" +
                    "<#if item.type == \"inputTableName\">${item.columns}" +
                    "<#elseif item.type == \"outPutTableName\">${item.columns}" +
                    "<#elseif item.type == \"fieldName\"><#if item.needBrackets == \"true\">(</#if><@e.fieldsProcess item.fieldFunctions/><#if item.needBrackets == \"true\">)</#if>" +
                    "<#elseif item.type == \"outPutFieldName\"><#if item.needBrackets == \"true\">(</#if><@e.fieldsProcess item.fieldFunctions/><#if item.needBrackets == \"true\">)</#if>" +
                    "</#if> " +
                    "</#if> " +
            "</#list>"
                    ,Specifications.TEMPLATE_FILE);

    public static final String STATEMENT_BODY = "statementBody";
    public static final String PLACEHOLDER = "placeholder";
    public static final String TYPE = "type";
    public static final String OUT_PUT_TABLE_NAME = "outPutTableName";
    public static final String COLUMNS = "columns";
    public static final String NAME = "name";
    public static final String FIELD_FUNCTIONS = "fieldFunctions";
    public static final String OUT_PUT_FIELD_NAME = "outPutFieldName";
    public static final String FIELD_NAME = "ieldName";

    @Override
    protected void initialize() {
        outputPortObject = registerOutputObjectPort("output_0");
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
    protected void execute() {

        Map<String, Object> parameters = getFirstParameterMap();

        @SuppressWarnings("unchecked")
        String statementBody = (String) parameters.get(STATEMENT_BODY);

        //placeholder : inputTableName ,outPutTableName ,fieldName ,outPutFieldName
        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> placeholder = (ArrayList<HashMap<String, Object>>) parameters.get(PLACEHOLDER);

        List<FieldFunction> outPutFfs = new ArrayList<>();

        //排序，防止占位符名称和数组中顺序不一致
        String[] arrays = new String[placeholder.size()];
        String outputTableName = placeholder.stream().filter(item -> item.get(TYPE).equals(OUT_PUT_TABLE_NAME)).findFirst().orElse(null).get(COLUMNS).toString();
        HashMap<String, HashMap<String, Object>> order = new HashMap<>();

        for(int i=0;i<placeholder.size();i++){
            HashMap<String, Object> stringObjectHashMap = placeholder.get(i);
            arrays[i]=String.valueOf(stringObjectHashMap.get(NAME));
            order.put(arrays[i],stringObjectHashMap);
            String type = stringObjectHashMap.get(TYPE).toString();
            if(type.contains(FIELD_NAME)){
                List<FieldFunction> ffs = Operator.getFieldFunctions(outputTableName, stringObjectHashMap);
                stringObjectHashMap.put(FIELD_FUNCTIONS,ffs);
                placeholder.set(i,stringObjectHashMap);
                if(type.equals(OUT_PUT_FIELD_NAME)){
                    outPutFfs=ffs;
                }
            }
        }
        for(int i=0;i<arrays.length-1;i++){
            for(int j=0;j<arrays.length-1-i;j++){
                int index1 = statementBody.indexOf(arrays[j]);
                int index2 = statementBody.indexOf(arrays[j+1]);
                if(index1 > index2){
                    String temp = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = temp;
                }
            }
        }
        List<HashMap<String, Object>> orderList = new ArrayList<>();

        //截断
        for(int i=0;i<arrays.length;i++){
            String[] split = statementBody.split(arrays[i]);
            HashMap<String, Object> map = order.get(arrays[i]);
            map.put("sql",split[0]);
            statementBody=split[1];
            orderList.add(map);
        }
        //组装输入
        HashMap<String, Object> last = new HashMap<>();
        last.put("sql",statementBody);
        orderList.add(last);


        HashMap<String, Object> result = new HashMap<>();
        result.put("Table",orderList);
        String sqlStr = TemplateUtils.format("customerTableName", result, TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        OperatorUtil.postTableOutput(
                outputPortObject,
                outputTableName,
                Specifications.convertFieldFunctionToColumns(outPutFfs));

    }



}
