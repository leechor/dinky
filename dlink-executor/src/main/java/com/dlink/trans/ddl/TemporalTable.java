package com.dlink.trans.ddl;

import com.dlink.parser.SingleSqlParserFactory;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class TemporalTable {
    private String statement;
    private String name;
    private String[] columns;
    private String table;

    private TemporalTable(String statement, String name, String[] columns, String table) {
        this.statement = statement;
        this.name = name;
        this.columns = columns;
        this.table = table;
    }

    public static TemporalTable build(String statement) {
        Map<String, List<String>> map = SingleSqlParserFactory.generateParser(statement);
        return new TemporalTable(statement,
            getString(map, "CREATE TTF"),
            map.get("SELECT").toArray(new String[0]),
            getString(map, "FROM"));
    }

    private static String getString(Map<String, List<String>> map, String key) {
        return StringUtils.join(map.get(key), ",");
    }

    public String getStatement() {
        return statement;
    }

    public String getName() {
        return name;
    }

    public String[] getColumns() {
        return columns;
    }

    public String getTable() {
        return table;
    }
}
