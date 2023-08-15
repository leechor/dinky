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

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

/**
 * 定义sql语句中每个字段调用的函数, 函数可以嵌套. <b>该函数在freemarker中用于类型判断,所以禁止重命名</b>
 *
 * <p>例: 根据以下配置文件:
 *
 * <pre>{@code
 * "fieldFunctions": [
 *                   {
 *                     "fieldOutName": "type",
 *                     "functionName": "CAST",
 *                     "delimiter": "AS",
 *                     "fieldParameters": [
 *                       "t",
 *                       "STRING"
 *                     ]
 *                   },
 *                   {
 *                     "fieldOutName": "data"
 *                   },
 *                   {
 *                     "fieldOutName": "taskId",
 *                     "functionName": "JSON_VALUE",
 *                     "fieldParameters": [
 *                       "task",
 *                       "'$.taskId'"
 *                     ]
 *                   },
 *                   {
 *                     "fieldOutName": "taskStatus",
 *                     "functionName": "CAST",
 *                     "delimiter": "AS",
 *                     "fieldParameters": [
 *                       {
 *                         "functionName": "JSON_VALUE",
 *                         "fieldParameters": [
 *                           "task",
 *                           "'$.taskStatus'"
 *                         ]
 *                       },
 *                       "INT"
 *                     ]
 *                   },
 *                   {
 *                     "fieldOutName": "proc_time",
 *                     "fieldParameters": [
 *                       "PROCTIME()"
 *                     ]
 *                   }
 *                 ]
 *
 * }</pre>
 *
 * <p>可生成代码:
 *
 * <pre>{@code
 *     SELECT
 *         CAST( t AS STRING ) AS type,
 *         data,
 *         JSON_VALUE( task , '$.taskId' ) AS taskId,
 *         CAST( JSON_VALUE( task , '$.taskStatus' ) AS INT ) AS taskStatus,
 *         PROCTIME() AS proc_time
 *     FROM
 *         _BroadcastResult6
 *         }
 * </code>
 * </pre>
 *
 * @author Licho Sun
 */
public class FieldFunction  {

    /** 函数需要的参数, 如果类型为{@link FieldFunction}, 会按照嵌套函数处理, */
    List<Object> parameters = new ArrayList<>();
    /** 字段输入名称,通过<b>AS</b>关键字进行字段重命名 */
    private String outName;
    /** 字段输出类型, 约定(deprecated). 算子校验需要类型推断，启用该字段 */
    private String outType;
    /** 自定义调用的函数名称 */
    private String functionName;
    /** 函数参数分隔符,如内置函数CAST的分隔符可视为<b>AS</b> */
    private String delimiter;

    /**
     * 解析字段处理方法配置, 生成{@link FieldFunction}定义
     * 所有字段的通用方法，可仅指定parameters，表示该字段不做任何处理
     *
     * @param fos 字段配置信息
     * @return 方法定义
     */
    static FieldFunction processFieldConfigure(String tableName, Map<String, Object> fos, boolean flag, List<Map<String, Object>> inputColumns) {

        FieldFunction fo = new FieldFunction();
        fo.setOutName(fos.get(OUT_NAME).equals("")? null:(String) fos.get(OUT_NAME));
        fo.setDelimiter((String) fos.get("delimiter"));//此字段可以去掉

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = ((List<Map<String, Object>>) fos.get(FUNCTION));
        fo.setFunctionName(pretreatment(tableName, columns, flag,inputColumns,new StringBuffer(),"").toString());

        return fo;
    }

    public static StringBuffer pretreatment(String tableName, List<Map<String, Object>> recursion, boolean flag, List<Map<String, Object>> inputColumns,StringBuffer str,String last) {

        for(Map<String, Object> inputName : recursion){

            Object functionName = inputName.get(FUNCTION_NAME);
            String now = separator(functionName, str, false);
            if(functionName!=null&&!functionName.equals("")){
                str.append(functionName).append("(");
            }

            //到达最内层时
            @SuppressWarnings("unchecked")
            List<String> n1 = ((List<String>)inputName.get(NAME));
            if(n1!=null&&!n1.isEmpty()){
                for(String n : n1){
                    String input= n ;
                    if(inputColumns!=null){
                        for(Map<String, Object> map : inputColumns){//出现在输入连接桩中的字段，设置表名称
                            if(map.get(NAME).equals(n)){
                                input = insertTableName(tableName,n,flag,functionName);
                            }
                        }
                    }
                    if(n1.get(n1.size()-1).equals(n)){
                        str.append(input).append(last);
                        break;
                    }
                    str.append(input).append(last);
                }
            }


            //1、当 recursionFunc 不为空时，需要进行递归
            recursion( str,inputName, tableName, flag, inputColumns,now , last,functionName,RECURSION_FUNC);
            //2、当 recursionName 不为空时，需要进行递归
            recursion( str,inputName, tableName, flag, inputColumns,now , last,functionName,RECURSION_NAME);

        }
        return str;
    }

    public static void recursion(StringBuffer str, Map<String, Object> inputName, String tableName, Boolean flag,
                                 List<Map<String, Object>> inputColumns, String now , String last, Object functionName , String nameOrFunc){
        if(!inputName.get(nameOrFunc).toString().equals("[]")){
            str.append(separator(functionName, str, true));
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> recursionFunc = ((List<Map<String, Object>>)inputName.get(nameOrFunc));
            pretreatment(tableName, recursionFunc, flag, inputColumns, str, now.equals("") ? last : now);

            if(functionName!=null&&!functionName.equals("")){
                if(str.toString().endsWith(now)){// 在函数级别上，去除最末尾的一个连接符
                    str.delete(str.length()-now.length(),str.length());
                }
                if(nameOrFunc.equals(RECURSION_FUNC)){ //recursionFunc需要判断参数是否结束
                    if(inputName.get(RECURSION_NAME).toString().equals("[]")){
                        str.append(")");
                    }
                }else{  //recursionName直接结束
                    str.append(")");
                }
            }
        }
    }

    /**
     * @param flag 是否需要在列明前增加表名
     * */
    public static String insertTableName(String primaryTableName, String param , boolean flag ,Object functionName) {
        boolean notAt = !param.startsWith("@");

        if (notAt || Strings.isBlank(primaryTableName)) {
            return param;
        }

        if (param.startsWith("@")) {
            param = param.substring(1);
        }
        if(flag){
            return primaryTableName + "." + modifyName(param);
        }
        return modifyName(param);
    }
    //字段名加单引号
    public static String modifyName(String param){
        String[] split = param.split("\\.");
        if(split.length>1){
            return split[0]+".`"+split[1]+"`";
        }
        return "`"+split[0]+"`";
    }

    /**
     * 分析字段配置, 转化为{@link FieldFunction} 形式.
     *
     * @param funcs 字段处理函数配置
     * @param flag 是否需要在列明前添加表名称 ,true：需要  false 不需要
     * @return {@link FieldFunction}形式的字段处理定义
     */
    public static List<FieldFunction> analyzeParameters(
            String primaryTableName, List<Map<String, Object>> funcs ,boolean flag ,List<Column> inputColumn, List<Map<String, Object>> config) {
        List<FieldFunction> fieldFunctions = new ArrayList<>();
        for (Map<String, Object> fos : funcs) {//此处过滤掉未选中的节点
            if((boolean)fos.get(FLAG)){
                FieldFunction fo = processFieldConfigure(primaryTableName, fos ,flag, config);
                fo.setOutType(typeInference(inputColumn, fo ,fos.get(TYPE)==null? null:fos.get(TYPE).toString()));
                fieldFunctions.add(fo);
            }
        }
        return fieldFunctions;
    }

    public static String typeInference(List<Column> inputColumn,FieldFunction fo,String type){
        if(inputColumn!=null&&!inputColumn.isEmpty()){
            for(Column c :inputColumn){//优先使用上一级名称
                if(c.getName().equals(fo.getFunctionName())){
                    return c.getType();
                }
            }
        }
        return type;
    }

    //根据函数名称确定使用的分隔符
    public static String separator(Object sep,StringBuffer str ,boolean flag){
        String separator = "";

        if(sep!=null&&!sep.equals("")){

            if(str.toString().endsWith("(") && flag){
                return "";
            }else if("to_timestamp".compareToIgnoreCase(sep.toString())==0){
                separator=" AS ";
            }else{
                separator=",";
            }
        }
        return separator;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
    // endregion
}
