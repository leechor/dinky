package com.zdpx.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OperatorFunctionVO {

    private String tableName;

    private List<Map<String, Object>> functions;

    private List<Map<String, Object>> config;

}
