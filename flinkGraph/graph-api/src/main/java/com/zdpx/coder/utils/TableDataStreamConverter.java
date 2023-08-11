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

package com.zdpx.coder.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zdpx.coder.operator.Column;
import com.zdpx.coder.operator.TableInfo;
import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

/** */
public class TableDataStreamConverter {
    private TableDataStreamConverter() {}

    //字段非函数格式，使用此方法构建tableInfo
    public static TableInfo getTableInfo(Map<String, Object> dataModel) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) dataModel.get(COLUMNS);
        List<Column> cs = new ArrayList<>();
        for (Map<String, Object> dm : columns) {
            if((boolean)dm.get(FLAG)){
                cs.add(new Column(dm.get(NAME).toString(), dm.get(TYPE).toString()));
            }
        }
        return TableInfo.newBuilder().name((String) dataModel.get(TABLE_NAME)).columns(cs).build();
    }

    //根据原有的TableInfo和config中的字段构建新的TableInfo
    public static TableInfo assembleNewTableInfo(TableInfo old,List<Map<String, Object>> count){
        List<Column> cs = new ArrayList<>();
        List<Column> columns = old.getColumns();

        count.stream().filter(dm-> (boolean)dm.get(FLAG)).forEach(dm->{
            columns.stream().filter(c-> c.getName().equals(dm.get(NAME))).forEach(c->{
                cs.add(new Column(c.getName(), c.getType()));
            });
        });
        return TableInfo.newBuilder().name(old.getName()).columns(cs).build();
    }

}
