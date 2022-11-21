package com.dlink.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class CreateTemporalTableFunctionSelectSqlParser extends BaseSingleSqlParser {

    public static final String IDENTIFIER_SQL = "(create\\s+ttf)(.+)(as\\s+select)";
    private static final String BODY_SPLIT = "[,]";

    private static final String[] SQL_SEGMENTS = new String[] {
        IDENTIFIER_SQL,
        "(select)(.+)(from)",
        "(from)(.+)( ENDOFSQL)"
    };

    public CreateTemporalTableFunctionSelectSqlParser(String originalSql) {
        super(originalSql);
    }

    @Override
    protected void initializeSegments() {
        final List<SqlSegment> sqlSegmentList = Arrays.stream(SQL_SEGMENTS)
            .map(t -> new SqlSegment(t, BODY_SPLIT))
            .collect(Collectors.toList());
        segments.addAll(sqlSegmentList);
    }
}
