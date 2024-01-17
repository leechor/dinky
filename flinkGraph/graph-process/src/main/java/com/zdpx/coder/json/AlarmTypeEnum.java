package com.zdpx.coder.json;

public enum AlarmTypeEnum {
    SQL("sqlAlarm", "sql语句校验错误"),
    GRAPH("graphAlarm", "流程图节点校验错误");

    private final String key;
    private final String value;

    AlarmTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
