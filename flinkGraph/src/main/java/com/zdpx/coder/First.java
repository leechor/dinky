package com.zdpx.coder;

import static org.apache.flink.table.api.Expressions.$;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.functions.TemporalTableFunction;

public final class First {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql(
            "CREATE TABLE DT_addSource_1 (" +
                "typ STRING," +
                " taskId STRING," +
                " id STRING," +
                " longitude DOUBLE," +
                " latitude DOUBLE," +
                " dt TIMESTAMP(0)," +
                " va DOUBLE," +
                " WATERMARK FOR dt AS dt - INTERVAL '15' SECOND) " +
                "WITH ('connector' = 'gbuzl', 'table-name' = 'DT')");

        tableEnv.executeSql(
            "CREATE TABLE TS_addSource_2 (" +
                "typ STRING," +
                " taskId STRING," +
                " taskStatus INT," +
                " dt TIMESTAMP(0)," +
                " WATERMARK FOR dt AS dt - INTERVAL '15' SECOND, " +
                " PRIMARY KEY(taskId) NOT ENFORCED) " +
                "WITH ('connector' = 'task', 'table-name' = 'TS')");

        tableEnv.executeSql("CREATE VIEW V_GBU_addSource_1 AS " +
            "SELECT typ, taskId, id, longitude, latitude, dt, va " +
            "FROM DT_addSource_1 WHERE typ = 'gbu'");

        tableEnv.executeSql("CREATE VIEW V_ZL_addSource_1 AS " +
            "SELECT typ, taskId, id, longitude, latitude, dt, va " +
            "FROM DT_addSource_1 WHERE typ = 'zl'");

        TemporalTableFunction rates = tableEnv
                .from("TS_addSource_2")
                .createTemporalTableFunction($("dt"), $("taskId"));

        tableEnv.registerFunction("tp", rates);

        tableEnv.executeSql("CREATE VIEW JoinOperator16 AS " +
            "SELECT id, V_GBU_addSource_1.taskId, taskStatus, V_GBU_addSource_1.dt FROM V_GBU_addSource_1 LEFT JOIN TS_addSource_2 FOR SYSTEM_TIME AS OF V_GBU_addSource_1.dt " +
            "ON V_GBU_addSource_1.taskId = TS_addSource_2.taskId");


        Table table = tableEnv.sqlQuery("select * from JoinOperator16");
        tableEnv.toChangelogStream(table).print();
        env.execute();
    }
}
