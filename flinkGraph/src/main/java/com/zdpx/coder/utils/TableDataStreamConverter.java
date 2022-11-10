package com.zdpx.coder.utils;

import com.zdpx.coder.operator.Column;
import com.zdpx.coder.operator.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class TableDataStreamConverter {
    private TableDataStreamConverter() {
    }

    @SuppressWarnings("unchecked")
    public static TableInfo getTableInfo(Map<String, Object> dataModel) {
        var columns = (List<Map<String, String>>) dataModel.get("columns");
        var cs = new ArrayList<Column>();
        for (var dm : columns) {
            cs.add(new Column(dm.get("name"), dm.get("type")));
        }

        return TableInfo.newBuilder()
                .name((String)dataModel.get("tableName"))
                .columns(cs)
                .build();
    }
}
