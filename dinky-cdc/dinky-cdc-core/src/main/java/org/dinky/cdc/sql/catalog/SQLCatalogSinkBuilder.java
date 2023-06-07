/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.dinky.cdc.sql.catalog;

import com.google.common.collect.Lists;
import org.apache.flink.api.dag.Transformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.table.types.logical.DateType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.TimestampType;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.dinky.assertion.Asserts;
import org.dinky.cdc.CDCBuilder;
import org.dinky.cdc.SinkBuilder;
import org.dinky.cdc.sql.AbstractSqlSinkBuilder;
import org.dinky.cdc.utils.FlinkStatementUtil;
import org.dinky.data.model.FlinkCDCConfig;
import org.dinky.data.model.Schema;
import org.dinky.data.model.Table;
import org.dinky.executor.CustomTableEnvironment;
import org.dinky.utils.LogUtil;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SQLCatalogSinkBuilder extends AbstractSqlSinkBuilder implements Serializable {

    public static final String KEY_WORD = "sql-catalog";
    private ZoneId sinkTimeZone = ZoneId.of("UTC");

    {
        typeConverterList = Lists.newArrayList(
                this::convertDateType,
                this::convertTimestampType,
                this::convertDecimalType,
                this::convertBigIntType,
                this::convertVarBinaryType
        );
    }

    public SQLCatalogSinkBuilder() {}

    private SQLCatalogSinkBuilder(FlinkCDCConfig config) {
        super(config);
    }

    private void addTableSink(
            CustomTableEnvironment customTableEnvironment,
            DataStream<Row> rowDataDataStream,
            Table table) {

        String catalogName = config.getSink().get("catalog.name");
        String sinkSchemaName = getSinkSchemaName(table);
        String tableName = getSinkTableName(table);
        String sinkTableName = catalogName + "." + sinkSchemaName + "." + tableName;
        String viewName = "VIEW_" + table.getSchemaTableNameWithUnderline();

        customTableEnvironment.createTemporaryView(viewName, rowDataDataStream);
        logger.info("Create {} temporaryView successful...", viewName);

        createInsertOperations(customTableEnvironment, table, viewName, sinkTableName);
    }

    @Override
    public String getHandle() {
        return KEY_WORD;
    }

    @Override
    public SinkBuilder create(FlinkCDCConfig config) {
        return new SQLCatalogSinkBuilder(config);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public DataStreamSource<String> build(
            CDCBuilder cdcBuilder,
            StreamExecutionEnvironment env,
            CustomTableEnvironment customTableEnvironment,
            DataStreamSource<String> dataStreamSource) {
        final String timeZone = config.getSink().get("timezone");
        config.getSink().remove("timezone");
        if (Asserts.isNotNullString(timeZone)) {
            sinkTimeZone = ZoneId.of(timeZone);
        }
        final List<Schema> schemaList = config.getSchemaList();
        if (Asserts.isNullCollection(schemaList)) {
            return dataStreamSource;
        }

        logger.info("Build catalog successful...");
        customTableEnvironment.executeSql(FlinkStatementUtil.getCreateCatalogStatement(config));

        logger.info("Build deserialize successful...");
        Map<Table, OutputTag<Map>> tagMap = new HashMap<>();
        Map<String, Table> tableMap = new HashMap<>();
        for (Schema schema : schemaList) {
            for (Table table : schema.getTables()) {
                String sinkTableName = getSinkTableName(table);
                OutputTag<Map> outputTag = new OutputTag<Map>(sinkTableName) {};
                tagMap.put(table, outputTag);
                tableMap.put(table.getSchemaTableName(), table);
            }
        }

        final String schemaFieldName = config.getSchemaFieldName();
        SingleOutputStreamOperator<Map> mapOperator =
                dataStreamSource
                        .map(x -> objectMapper.readValue(x, Map.class))
                        .returns(Map.class);

        SingleOutputStreamOperator<Map> processOperator =
                mapOperator.process(
                        new ProcessFunction<Map, Map>() {

                            @Override
                            public void processElement(
                                    Map map,
                                    ProcessFunction<Map, Map>.Context ctx,
                                    Collector<Map> out) {
                                LinkedHashMap source = (LinkedHashMap) map.get("source");
                                try {
                                    Table table =
                                            tableMap.get(
                                                    source.get(schemaFieldName).toString()
                                                            + "."
                                                            + source.get("table").toString());
                                    OutputTag<Map> outputTag = tagMap.get(table);
                                    ctx.output(outputTag, map);
                                } catch (Exception e) {
                                    out.collect(map);
                                }
                            }
                        });
        tagMap.forEach(
                (table, tag) -> {
                    final String schemaTableName = table.getSchemaTableName();
                    try {
                        DataStream<Map> filterOperator = shunt(processOperator, table, tag);
                        logger.info("Build {} shunt successful...", schemaTableName);
                        List<String> columnNameList = new ArrayList<>();
                        List<LogicalType> columnTypeList = new ArrayList<>();
                        buildColumn(columnNameList, columnTypeList, table.getColumns());
                        DataStream<Row> rowDataDataStream =
                                buildRow(
                                                filterOperator,
                                                columnNameList,
                                                columnTypeList,
                                                schemaTableName)
                                        .rebalance();
                        logger.info("Build {} flatMap successful...", schemaTableName);
                        logger.info("Start build {} sink...", schemaTableName);

                        addTableSink(
                                customTableEnvironment,
                                rowDataDataStream,
                                table);
                    } catch (Exception e) {
                        logger.error("Build {} cdc sync failed...", schemaTableName);
                        logger.error(LogUtil.getError(e));
                    }
                });

        List<Transformation<?>> trans =
                customTableEnvironment.getPlanner().translate(modifyOperations);
        for (Transformation<?> item : trans) {
            env.addOperator(item);
        }
        logger.info("A total of {} table cdc sync were build successful...", trans.size());
        return dataStreamSource;
    }

    protected Optional<Object> convertDateType(Object value, LogicalType logicalType) {
        if (logicalType instanceof DateType) {
            if (value instanceof Integer) {
                return Optional.of(Instant.ofEpochMilli(((Integer) value).longValue())
                        .atZone(sinkTimeZone)
                        .toLocalDate());
            }
            return Optional.of(Instant.ofEpochMilli((long) value).atZone(sinkTimeZone).toLocalDate());
        }
        return Optional.empty();
    }

    protected Optional<Object> convertTimestampType(Object value, LogicalType logicalType) {
        if (logicalType instanceof TimestampType) {
            if (value instanceof Integer) {
                return Optional.of(Instant.ofEpochMilli(((Integer) value).longValue())
                        .atZone(sinkTimeZone)
                        .toLocalDateTime());
            }

            if (value instanceof String) {
                return Optional.of(Instant.parse((String) value).atZone(sinkTimeZone).toLocalDateTime());
            }

            return Optional.of(Instant.ofEpochMilli((long) value).atZone(sinkTimeZone).toLocalDateTime());
        }
        return Optional.empty();
    }
}
