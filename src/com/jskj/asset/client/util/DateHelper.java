/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jskj.asset.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author woderchen
 */
public class DateHelper {

    /**

     * 格式化输出日期

     *

     * @param date

     *            日期

     * @param format

     *            格式

     * @return 返回字符型日期

     */
    public static String format(java.util.Date date, String format) {
        String result = "";

        try {
            if (date != null) {
                java.text.DateFormat df = new java.text.SimpleDateFormat(format);

                result = df.format(date);

            }
        } catch (Exception e) {
        }
        return result;

    }

    public static String format(java.util.Date date) {
        return format(date, "yyyy/MM/dd");

    }

    public static String formatTime(java.util.Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");

    }

    /**
     * 字符串转换成日期
     * @param stringdate 要转换的字符串(字符串格式 yyyy-MM-dd)
     * @return
     */
    public static Date getStringtoDate(String stringdate) {
        Date dd = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date cDate;
        try {
            cDate = df.parse(stringdate);
            dd = new java.sql.Date(cDate.getTime());
        } catch (ParseException e1) {
        }
        return dd;
    }

    public static Date getStringtoTimestamp(String stringdate) {
        Date dd = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cDate;
        try {
            cDate = df.parse(stringdate);
            dd = new java.sql.Date(cDate.getTime());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return dd;
    }

    /**
     * 字符串转换成日期
     * @param stringdate 要转换的字符串
     * @param format 字符串的日期格式
     * @return
     */
    public static Date getStringtoDate(String stringdate, String format) {
        Date dd = null;
        Date cDate;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            cDate = df.parse(stringdate);
            dd = new java.sql.Date(cDate.getTime());
        } catch (ParseException e1) {
        }
        return dd;
    }

    /**

     * 返回年份

     *

     * @param date

     *            日期

     * @return 返回年份

     */
    public static int getYear(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.get(java.util.Calendar.YEAR);

    }

    /**

     * 返回月份

     *

     * @param date

     *            日期

     * @return 返回月份

     */
    public static int getMonth(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.get(java.util.Calendar.MONTH) + 1;

    }

    /**

     * 返回日份

     *

     * @param date

     *            日期

     * @return 返回日份

     */
    public static int getDay(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.get(java.util.Calendar.DAY_OF_MONTH);

    }

    /**

     * 返回小时

     *

     * @param date

     *            日期

     * @return 返回小时

     */
    public static int getHour(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.get(java.util.Calendar.HOUR_OF_DAY);

    }

    /**

     * 返回分钟

     *

     * @param date

     *            日期

     * @return 返回分钟

     */
    public static int getMinute(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.get(java.util.Calendar.MINUTE);

    }

    /**

     * 返回秒钟

     *

     * @param date

     *            日期

     * @return 返回秒钟

     */
    public static int getSecond(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.get(java.util.Calendar.SECOND);

    }

    /**

     * 返回毫秒

     *

     * @param date

     *            日期

     * @return 返回毫秒

     */
    public static long getMillis(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTime(date);

        return c.getTimeInMillis();

    }

    /**

     * 返回字符型日期

     *

     * @param date

     *            日期

     * @return 返回字符型日期

     */
    public static String getDate(java.util.Date date) {
        return format(date, "yyyy/MM/dd");

    }

    /**

     * 返回字符型时间

     *

     * @param date

     *            日期

     * @return 返回字符型时间

     */
    public static String getTime(java.util.Date date) {
        return format(date, "HH:mm:ss");

    }

    /**
     * 
     * @param time
     * @return
     */
    public static String getDate(long time) {
        return formatTime((new Date(time)));
    }

    /**

     * 返回字符型日期时间

     *

     * @param date

     *            日期

     * @return 返回字符型日期时间

     */
    public static String getDateTime(java.util.Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");

    }

    /**

     * 日期相加

     *

     * @param date

     *            日期

     * @param day

     *            天数

     * @return 返回相加后的日期

     */
    public static java.util.Date addDate(java.util.Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);

        return c.getTime();

    }

    /**

     * 日期相减

     *

     * @param date

     *            日期

     * @param date1

     *            日期

     * @return 返回相减后的日期

     */
    public static int diffDate(java.util.Date date, java.util.Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));

    }
}
