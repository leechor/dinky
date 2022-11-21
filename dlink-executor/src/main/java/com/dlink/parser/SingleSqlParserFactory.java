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

package com.dlink.parser;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SingleSqlParserFactory
 *
 * @author wenmo
 * @since 2021/6/14 16:49
 */
public class SingleSqlParserFactory {

    public static Map<String, List<String>> generateParser(String sql) {
        BaseSingleSqlParser tmp = getBaseSingleSqlParser(sql);
        if (tmp != null) {
            return tmp.splitSql2Segment();
        }

        throw new IllegalArgumentException(String.format("sql:%s parse fail!", sql));
    }

    private static BaseSingleSqlParser getBaseSingleSqlParser(String sql) {
        sql = sql.replace("\r\n", " ").replace("\n", " ") + " ENDOFSQL";
        if (contains(sql, "(insert\\s+into)(.+)(select)(.+)(from)(.+)")) {
            return new InsertSelectSqlParser(sql);
        }

        if (contains(sql, "(create\\s+aggtable)(.+)(as\\s+select)(.+)")) {
            return new CreateAggTableSelectSqlParser(sql);
        }

        if (contains(sql, CreateTemporalTableFunctionSelectSqlParser.IDENTIFIER_SQL + "(.+)")) {
            return new CreateTemporalTableFunctionSelectSqlParser(sql);
        }

        if (contains(sql, "(execute\\s+cdcsource)")) {
            return new CreateCDCSourceSqlParser(sql);
        }

        if (contains(sql, "(select)(.+)(from)(.+)")) {
            return new SelectSqlParser(sql);
        }

        if (contains(sql, "(delete\\s+from)(.+)")) {
            return new DeleteSqlParser(sql);
        }

        if (contains(sql, "(update)(.+)(set)(.+)")) {
            return new UpdateSqlParser(sql);
        }

        if (contains(sql, "(insert\\s+into)(.+)(values)(.+)")) {
            return new InsertSqlParser(sql);
        }

        if (contains(sql, "(set)(.+)")) {
            return new SetSqlParser(sql);
        }

        if (contains(sql, "(show\\s+fragment)\\s+(.+)")) {
            return new ShowFragmentParser(sql);
        }

        return null;
    }

    /**
     * 看word是否在lineText中存在，支持正则表达式
     *
     * @param sql:要解析的sql语句
     * @param regExp:正则表达式
     * @return
     **/
    private static boolean contains(String sql, String regExp) {
        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        return matcher.find();
    }
}

