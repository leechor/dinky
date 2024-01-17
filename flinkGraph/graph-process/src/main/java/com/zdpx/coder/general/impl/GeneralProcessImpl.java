package com.zdpx.coder.general.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdpx.coder.general.GeneralProcess;
import com.zdpx.coder.operator.FieldFunction;

public class GeneralProcessImpl implements GeneralProcess {

    @Override
    public List<String> getFunction(Map<String, Map<String, Object>> parametersLocal) {

        List<String> lists = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> column =
                (List<Map<String, Object>>) parametersLocal.get("output").get("columns");

        for (Map<String, Object> c : column) {
            FieldFunction fo = FieldFunction.processFieldConfigure("", c, false, null);
            if (fo.getFunctionName() != null) {
                lists.add(fo.getFunctionName());
            } else {
                lists.add(c.get("name").toString());
            }
        }

        return lists;
    }

    @Override
    public List<Object> getOutPutColumn(Map<String, Map<String, Object>> parametersLocal) {

        Object statementBody = parametersLocal.get("service").get("statementBody");
        List<Object> columns = new ArrayList<>();

        if (statementBody != null && statementBody.toString().length() > 20) {

            String sql = statementBody.toString().replaceAll("`", "");
            String head = sql.substring(0, 20).toUpperCase();

            if (head.contains("CREATE")
                    && head.contains("TABLE")) { //  CREATE TABLE 起始符(   终止符)  分隔符 ,
                columns.addAll(getColumnFromCreatTable(sql));
            } else if (head.contains("CREATE")
                    && head.contains("VIEW")) { //  CREATE VIEW   起始符 第一个select  终止符最后一个from 分隔符 ,
                columns.addAll(getColumnFromCreateView(sql));
            } else if (head.contains("INSERT")
                    && head.contains("INTO")) { // INSERT INTO  起始符(   终止符)  分隔符 ,
                columns.addAll(getColumnFromInsertInto(sql));
            } else if (sql.contains("MATCH_RECOGNIZE")) { //  CEP   起始符MEASURES  终止符PATTERN 分隔符 ,
                columns.addAll(getColumnFromCEP(sql));
            }
        }

        return columns;
    }

    public List<Map<String, String>> getColumnFromInsertInto(String str) {
        List<Map<String, String>> list = new ArrayList<>();
        List<String> split =
                autoSplit(str.substring(str.indexOf('(') + 1, creatTableA(str, 0) - 1));

        for (String value : split) {
            list.add(assemble(value));
        }
        return list;
    }

    public List<Map<String, String>> getColumnFromCreateView(String str) {
        List<Map<String, String>> list = new ArrayList<>();

        String rep = str.replaceAll("select", "SELECT").replaceAll("from", "FROM");
        List<String> strings =
                autoSplit(rep.substring(rep.indexOf("SELECT") + 6, rep.lastIndexOf("FROM")));

        for (String value : strings) {
            String[] split = value.replaceAll(" as ", " AS ").trim().split(" AS ");
            String[] split1 = split[split.length - 1].split("\\.");
            if (!split1[split1.length - 1].equals("*")) {
                list.add(assemble(split1[split1.length - 1]));
            }
        }
        return list;
    }

    public List<Map<String, String>> getColumnFromCEP(String str) {

        List<Map<String, String>> list = new ArrayList<>();
        List<String> strings =
                autoSplit(str.substring(str.indexOf("MEASURES") + 8, str.indexOf("PATTERN")));
        for (String value : strings) {
            String[] split = value.replaceAll(" as ", " AS ").trim().split(" AS ");
            String[] split1 = split[split.length - 1].split("\\n|\\t|\\s");
            list.add(assemble(split1[0]));
        }
        return list;
    }

    public List<Map<String, String>> getColumnFromCreatTable(String str) {
        List<Map<String, String>> list = new ArrayList<>();
        List<String> split =
                autoSplit(str.substring(str.indexOf('(') + 1, creatTableA(str, 0) - 1));

        for (String value : split) {
            String first = value.trim().split(" ")[0];
            if (first.equalsIgnoreCase("PRIMARY") || first.equalsIgnoreCase("WATERMARK")) {
                continue;
            }
            list.add(assemble(first));
        }
        return list;
    }

    // 寻找字符串中第一次出现 非数字) 的下标
    public int creatTableA(String sql, int sum) {
        int i = sql.indexOf(")");
        sum = sum + i + 1;
        boolean matches =
                sql.substring(i - 5, i).replaceAll("[\\n\\t\\s]", "").matches("^(?=.*\\d).*$");
        if (matches) {
            sum = creatTableA(sql.substring(i + 1), sum);
        }
        return sum;
    }
    // 有条件的自动分割，当字符串中存在未合并的 ( 或 < 时不分割字符串 否则根据逗号分割
    public List<String> autoSplit(String str) {
        List<String> list = new ArrayList<>();
        String[] split = str.split(",");
        int flag = 0;
        StringBuilder builder = new StringBuilder();

        for (String value : split) {
            builder.append(value);
            String s = value.replaceAll("[>)]", "~~A");
            s = s.replaceAll("[<(]", "!!A");

            int beginGT = s.split("!!").length;
            if (beginGT > 1) {
                flag = flag + (beginGT - 1);
            }
            int beginLT = s.split("~~").length;
            if (beginLT > 1) {
                flag = flag - (beginLT - 1);
            }

            if (flag == 0) {
                list.add(builder.toString());
                builder.delete(0, builder.length());
            }
        }
        return list;
    }

    public Map<String, String> assemble(String str) {
        Map<String, String> map = new HashMap<>();
        map.put("name", str);
        map.put("type", "STRING");
        map.put("desc", "");
        return map;
    }

    public static void main(String[] args) {

        String str =
                "        select\n"
                        + "            distinct\n"
                        + "            b.id\n"
                        + "                   ,b.role_code\n"
                        + "                   ,b.role_name\n"
                        + "                   ,b.is_delete\n"
                        + "                   ,b.note\n"
                        + "                   ,b.create_time\n"
                        + "                   ,b.update_time\n"
                        + "        from\n"
                        + "            dinky_user_role a\n"
                        + "                inner join dinky_role b on b.id = a.role_id\n"
                        + "        where a.user_id =  #{userId}\n"
                        + "          and b.is_delete = 0";

        GeneralProcessImpl generalProcess = new GeneralProcessImpl();
        System.out.println(generalProcess.getColumnFromCreateView(str));
    }
}
