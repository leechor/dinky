package com.zdpx.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class OperatorFunctionVO {

    private String tableName;

    private List<Map<String, Object>> functions;

    private List<Map<String, Object>> config;
}
