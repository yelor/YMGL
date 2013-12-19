/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.jskj.asset.client.constants.Constants;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author woderchen
 */
public class StringHelper {

    private static Pattern pattern_sql = Pattern.compile("^select\\s+.+from", Pattern.CASE_INSENSITIVE);
    private static Pattern pattern_blank = Pattern.compile("\\s", Pattern.CASE_INSENSITIVE);
    private static Pattern pattern_blanktrim = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);

    /**
     * 转换第一个字符为大写字母
     *
     * @param tableNameStr
     * @return
     */
    public static String formatFirstToUpper(String tableNameStr) {

        if (tableNameStr != null && !tableNameStr.equals("")) {
            if (tableNameStr.length() > 1) {
                String head = tableNameStr.substring(0, 1);
                String temp = tableNameStr.substring(1);
                return head.toUpperCase() + temp;
            } else {
                return tableNameStr.toUpperCase();
            }
        }
        return "";
    }


    /**
     * 获取SQL的查询对象
     *
     * @param sql
     * @return
     */
    public static Iterable<String> getStringForSpliter(String str, String spliter) {
        return Splitter.on(spliter).trimResults().split(str);
    }

    /**
     * 得到最后一个划分值
     *
     * @param str
     * @return
     */
    public static String getLastBlankSplitResult(String str) {
        Iterable<String> its = Splitter.on(pattern_blanktrim).split(str);
        Iterator it = its.iterator();
        Object lastValue = "";
        while (it.hasNext()) {
            lastValue = it.next();
        }
        return lastValue.toString();
    }

    public static Iterable<String> getBlankSplitResult(String str) {
        return Splitter.on(pattern_blanktrim).split(str);
    }

    /**
     * 获取SQL的查询对象
     *
     * @param sql
     * @return
     */
    public static Iterable<String> getQueryObjectBySQL(String sql) {
        String lower = sql.trim().toLowerCase();
        if (sql != null && lower.startsWith("select")) {
            String temp = sql.trim().substring(6, lower.indexOf("from"));
            return Splitter.on(',').trimResults().split(temp);
        }
        return null;
    }

    /**
     * 获取SQL的查询对象,去掉as关键字和后面的字符串
     *
     * @param sql
     * @return
     */
    public static List<String> getQueryObjectBySQLWithoutAS(String sql) {
        List<String> columnList = new ArrayList<String>();

        String lower = sql.trim().toLowerCase();
        if (sql != null && lower.startsWith("select")) {
            String temp = lower.substring(6, lower.indexOf("from"));
            Iterable<String> its = Splitter.on(',').trimResults().split(temp);
            for (String it : its) {
                int asPos = it.toLowerCase().indexOf("as");
                if (asPos > 0) {
                    columnList.add(it.substring(0, asPos).trim());
                } else {
                    columnList.add(it);
                }
            }

        }
        if (columnList.size() <= 0) {
            columnList.add("*");
        }
        return columnList;
    }

    /**
     * 获取SQL的查询对象，得到as关键字和后面的字符串
     *
     * @param sql
     * @return
     */
    public static List<String> getQueryCharBySQLWithAS(String sql) {
        List<String> columnList = new ArrayList<String>();

        String lower = sql.trim().toLowerCase();
        if (sql != null && lower.startsWith("select")) {
            String temp = lower.substring(6, lower.indexOf("from"));
            Iterable<String> its = Splitter.on(',').trimResults().split(temp);
            for (String it : its) {
                int asPos = it.toLowerCase().indexOf("as");
                if (asPos > 0) {
                    columnList.add(it.substring(asPos + 3).trim());
                } else {
                    columnList.add(it);
                }
            }

        }
        if (columnList.size() <= 0) {
            columnList.add("*");
        }
        return columnList;
    }

    public static String getStringByArray(Object[] strs) {
        String temp = "";
        if (strs != null && strs.length > 0) {
            return Joiner.on(", ").skipNulls().join(strs);
        }
        return temp;
    }

//    public static String getStringByArray(List strs) {
//        String temp = "";
//        if (strs != null && strs.size() > 0) {
//            return Joiner.on(", ").skipNulls().join(strs);
//        }
//        return temp;
//    }
    public static String getStringByArray(Iterable<?> strs) {
        String temp = "";
        if (strs != null) {
            return Joiner.on(", ").skipNulls().join(strs);
        }
        return temp;
    }

    public static List<String> getFieldsOfClass(Class<?> classs, String... exceptFieldName) {
        List<String> fields = new ArrayList<String>();
        Field[] files = classs.getDeclaredFields();
        loop1:
        for (Field f : files) {
            loop2:
            for (String ex : exceptFieldName) {
                if (ex.equalsIgnoreCase(f.getName())) {
                    continue loop1;
                }
            }
            fields.add(f.getName());
        }
        return fields;
    }

    public static List<String> getFieldsOfClass(Class<?> classs) {
        List<String> fields = new ArrayList<String>();
        Field[] files = classs.getDeclaredFields();
        for (Field f : files) {
            fields.add(f.getName());
        }
        return fields;
    }

    public static String match(String sql) {
        Matcher matcher = pattern_sql.matcher(sql.trim());
        while (matcher.find()) {
            System.out.println("@@@@@:" + matcher.group());
        }
        return "";
    }

    public static void main(String[] str) {
        StringHelper.match("select d.ddr_num, d.load_carrier, char(d.w_act_ship_date) as ship_date, char(d.w_plan_supply_date) AS     supply_date, d.load_mot, d.request_type aS   type from (select * from d159pod.deliver_tbl) d, d159pod.order_tbl o  where d.load_carrier like 'SW%' and o.ddr_num = d.ddr_num group     by d.ddr_num, load_carrier, char(d.w_act_ship_date), char(d.w_plan_supply_date) , d.load_mot , d.request_type  order by d.ddr_num");
        StringHelper.match("   select           * from  table");
    }
}
