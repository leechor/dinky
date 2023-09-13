package com.zdpx.coder.operators;

import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.InputPortObject;
import com.zdpx.coder.graph.OutputPortObject;
import com.zdpx.coder.graph.Port;
import com.zdpx.coder.operator.OperatorFeature;
import com.zdpx.coder.operator.TableInfo;
import com.zdpx.coder.operator.Column;
import com.zdpx.coder.operator.Operator;

import java.util.*;
import java.util.stream.Collectors;

import static com.zdpx.coder.graph.OperatorSpecializationFieldConfig.*;

public class CustomerOperator extends Operator {

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
    public Optional<OperatorFeature> getOperatorFeature() {
        OperatorFeature operatorFeature = OperatorFeature.builder()
                .icon("icon-xinjianku")
                .build();
        return Optional.of(operatorFeature);
    }

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        Map<String, Object> parameters = getFirstParameterMap();

        if(getInputPorts()!=null&&!getInputPorts().isEmpty()){

            String statementBody = parameters.get("statementBody").toString();
            //获取连接桩名
            List<String> collect = getInputPorts().values().stream().map(Port::getName).collect(Collectors.toList());
            //替换sql中的表
            for(String s:collect){
                @SuppressWarnings("unchecked")
                String tableName = ((InputPortObject<TableInfo>) getInputPorts().get(s)).getOutputPseudoData().getName();
                statementBody=statementBody.replaceAll(s,tableName);
            }
            parameters.put("statementBody",statementBody);

        }
        //处理COLUMNS
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>)parameters.get(COLUMNS);
        List<Column> collect1 = columns.stream().map(item -> new Column(item.get(NAME).toString(), item.get(TYPE).toString())).collect(Collectors.toList());
        parameters.put(COLUMNS,collect1);

        return parameters;
    }


    /**
     * 校验内容：
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {
        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get("id").toString());
        model.setColor("green");

        this.getSceneCode().getGenerateResult().addCheckInformation(model);
    }

    @Override
    protected void execute(Map<String, Object> result) {

        this.getSceneCode().getGenerateResult().generate((String)result.get("statementBody"));//本算子输入的sql不用转换

        if(getOutputPorts()!=null&&!getOutputPorts().isEmpty()){


            @SuppressWarnings("unchecked")
            List<Column> column = (List<Column>)result.get(COLUMNS);//所有输出字段名称

            getOutputPorts()
                    .values()
                    .forEach(t -> {
                        @SuppressWarnings("unchecked")
                        OutputPortObject<TableInfo> out = (OutputPortObject<TableInfo>) t;
                        out.setPseudoData(new TableInfo(t.getName(),column));
                    });
        }
    }


}
