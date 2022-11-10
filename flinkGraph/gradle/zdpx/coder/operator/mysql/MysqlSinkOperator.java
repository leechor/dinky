package com.zdpx.coder.operator.mysql;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.TemplateUtils;

/**
 *
 */
@Slf4j
public class MysqlSinkOperator extends MysqlTable {

    private InputPortObject<TableInfo> inputPortObject;

    @Override
    protected void initialize() {
        inputPortObject = new InputPortObject<>(this, "input_0");
        inputPorts.add(inputPortObject);
    }

    @Override
    protected void execute() {
        var dataModel = getDataModel();
        var sqlStr = TemplateUtils.format("sink", dataModel, TEMPLATE);

        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        var sql = String.format(
            "INSERT INTO ${tableName} (<#list tableInfo.columns as column>${column.name}<#sep>,</#sep></#list>) SELECT <#list tableInfo.columns as column>${column.name}<#sep>, </#list> FROM ${tableInfo.name}");

        var pseudoData = inputPortObject.getOutputPseudoData();
        if (pseudoData == null) {
            log.warn("{} input table info empty error.", getName());
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("tableName", dataModel.get("tableName"));
        data.put("tableInfo", pseudoData);
        var insertSqlStr = TemplateUtils.format("insert", data, sql);
        this.getSchemaUtil().getGenerateResult().generate(insertSqlStr);

    }

}
