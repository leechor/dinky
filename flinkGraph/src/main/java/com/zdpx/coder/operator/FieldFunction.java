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

import com.zdpx.coder.graph.OperatorSpecializationFieldConfig;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class FieldFunction extends OperatorSpecializationFieldConfig {

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
    static FieldFunction processFieldConfigure(String tableName, Map<String, Object> fos , boolean flag) {
        FieldFunction fo = new FieldFunction();
        fo.setOutName(fos.get(NAME).equals("")? null:(String) fos.get(NAME));
        fo.setFunctionName(fos.get(FUNCTION_NAME).equals("") ? null :(String) fos.get(FUNCTION_NAME) );
        fo.setDelimiter((String) fos.get("delimiter"));
        @SuppressWarnings("unchecked")
        List<Object> fieldParameters = (List<Object>) fos.get(PARAMETERS);

        if (fieldParameters == null) {
            fo.setOutName(insertTableName(tableName, fo, fo.getOutName(),flag));
            return fo;
        }

        List<Object> result = new ArrayList<>();
        for (Object fieldParameter : fieldParameters) {
            if (fieldParameter instanceof Map) {
                // 表示函数需要递归处理 todo 未实现递归
                @SuppressWarnings("unchecked")
                Map<String, Object> fp = (Map<String, Object>) fieldParameter;
                result.add(processFieldConfigure(tableName, fp,flag));
            } else if (fieldParameter instanceof String) {
                String field = (String) fieldParameter;
                field = insertTableName(tableName, fo, field,flag);
                result.add(field);
            } else {
                result.add(fieldParameter);
            }
        }
        fo.setParameters(result);

        return fo;
    }

    /**
     * @param flag 是否需要在列明前增加表名
     * */
    public static String insertTableName(String primaryTableName, FieldFunction fo, String param , boolean flag) {
        boolean notAt = !param.startsWith("@")
                && fo != null
                && Strings.isNotEmpty(fo.getFunctionName());

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
            String primaryTableName, List<Map<String, Object>> funcs ,boolean flag ,List<Column> inputColumn) {
        List<FieldFunction> fieldFunctions = new ArrayList<>();
        for (Map<String, Object> fos : funcs) {//此处过滤掉未选中的节点
            if((boolean)fos.get(FLAG)){
                FieldFunction fo = processFieldConfigure(primaryTableName, fos ,flag);
                fo.setOutType(typeInference(inputColumn, fo,fos));
                fieldFunctions.add(fo);
            }
        }
        return fieldFunctions;
    }

    public static String typeInference(List<Column> inputColumn,FieldFunction fo,Map<String, Object> list){
        if(!inputColumn.isEmpty()&&fo.getOutType()==null){
            @SuppressWarnings("unchecked")
            List<String> l = (List<String>)list.get(PARAMETERS);
            if(l.size()==1){
                for(Column c :inputColumn){
                    if(c.getName().equals(l.get(0))){
                         return c.getType();
                    }
                }
            }
        }
        return null;
    }

    // region g/s

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
