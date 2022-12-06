package com.zdpx.coder.operator.mysql;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.TableDataStreamConverter;

/**
 *
 */
public abstract class MysqlTable extends Operator {
    public static final String TEMPLATE = "CREATE TABLE ${tableName} ("
        + "<#list columns as column>${column.name} ${column.type}<#sep>, "
        + "</#list>) "
        + "WITH ("
        + "<#list parameters as key, value>"
        + "'${(key == \"tableName\")?then(\"table-name\", key)}' = '${value}'<#sep>, "
        + "</#list>)";
    protected TableInfo tableInfo;

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getDataModel() {
        final String columns = "columns";
        String parameters = "parameters";

        var psFirst = getParameterLists().get(0);

        Map<String, Object> result = new HashMap<>();
        result.put(parameters, new HashMap<String, Object>());
        result.put("tableName", generateTableName((String) psFirst.get("tableName")));
        for (var m : psFirst.entrySet()) {
            if (m.getKey().equals(columns)) {
                result.put(columns, m.getValue());
                continue;
            }

            var ps = (HashMap<String, Object>) result.get(parameters);
            ps.put(m.getKey(), m.getValue());
        }
        return result;
    }

    protected String generateTableName(String tableName) {
        return tableName + "_" + this.operatorWrapper.getId();
    }

    @Override
    protected void handleParameters(String parameters) {
        var pl = getParameterLists(parameters);
        if (CollectionUtils.isEmpty(pl)) {
            return;
        }
        tableInfo = TableDataStreamConverter.getTableInfo(getParameterLists(parameters).get(0));
    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return Map.of();
    }

    @Override
    protected boolean applies() {
        return true;
    }
}
