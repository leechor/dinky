package com.zdpx.coder.general.impl;

import com.zdpx.coder.general.GeneralProcess;
import com.zdpx.coder.json.preview.OperatorPreviewBuilder;
import com.zdpx.coder.operator.FieldFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneralProcessImpl implements GeneralProcess {

    @Override
    public String operatorPreview(String graph){
        OperatorPreviewBuilder preview = new OperatorPreviewBuilder(graph);
        return preview.operatorPreview();
    }

    @Override
    public List<String> getFunction(String graph) {
        List<String> lists = new ArrayList<>();

//        List<Map<String, Object>> column = columns.get("columns");
//
//        if(column.get(0).get("function")==null){//是一个源算子
//            lists.addAll(column.stream().map(i-> i.get("name").toString()).collect(Collectors.toList()));
//        }else{//是一个中间算子
//            List<FieldFunction> fieldFunctions = FieldFunction.analyzeParameters("", column, false, null, null);
//            lists.addAll(fieldFunctions.stream().map(FieldFunction::getFunctionName).collect(Collectors.toList()));
//        }

        return lists;
    }

}
