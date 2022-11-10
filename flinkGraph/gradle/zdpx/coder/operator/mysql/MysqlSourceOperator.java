package com.zdpx.coder.operator.mysql;

import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.utils.TableDataStreamConverter;
import com.zdpx.coder.utils.TemplateUtils;


/**
 *
 */
public class MysqlSourceOperator extends MysqlTable {

    private OutputPortObject<TableInfo> outputPortObject;

    @Override
    protected void initialize() {
        outputPortObject = new OutputPortObject<>(this, "output_0");
        outputPorts.add(outputPortObject);
    }

    @Override
    protected void execute() {

        var sqlStr = TemplateUtils.format("Source", getDataModel(), TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);

        var parameters = getParameterLists().get(0);
        final TableInfo ti = TableDataStreamConverter.getTableInfo(parameters);
        ti.setName(generateTableName(ti.getName()));
        outputPortObject.setPseudoData(ti);
    }

    //endregion
}
