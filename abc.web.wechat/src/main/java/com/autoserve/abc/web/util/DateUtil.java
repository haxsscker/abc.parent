package com.autoserve.abc.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;

/**
 * 时间操作类
 *
 * @author hongqing.huang
 */
public class DateUtil extends DateUtils {

    public static final String DATE_FORMAT           = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT      = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SHORT_TIME_CN    = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_SHORT_TIME_EN    = "yyyy-MM-dd HH:mm";
    public static final String DATE_LONG_TIME_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
    public static final String DATE_FORMAT_ZONE      = "yyyy/MM/dd HH:mm:ss Z";
    public static final String DATE_FORMAT_VIEW_CN   = "yyyy年MM月dd日(EEE) HH:mm";
    public static final String DATE_FORMAT_VIEW_EN   = DATE_TIME_FORMAT;

    public static final String DATE_FORMAT_SEND_MAIL = "yyyy-MM-dd HH:mm:ss Z";
    public static final String WS_DATE_FORMAT        = "yyyy/MM/dd HH:mm:ss Z";

    /**
     * 获取当前时间的毫秒数，主要用于请求js等资源时添加ver=
     */
    public static String getCurrentTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }

        return new DateTime(date).toString(DATE_FORMAT);
    }

    /**
     * 根据指定的格式格式化输入时间，并返回
     *
     * @param date 时间
     * @param format 格式
     * @return String 格式化之后的字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String formatDate(String date, String format) {
        if (date == null) {
            return "";
        }
        Date date2 = parseDate(date, format);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date2);
    }

    /**
     * 根据指定的格式格式化输入时间，并返回
     *
     * @param date 时间
     * @param format 格式
     * @return String 格式化之后的字符串
     */
    public static String formatDate(Long date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(date));
    }

    /**
     * 把指定的字符串数据格式化时间数据，并返回
     *
     * @param date 时间字符串
     * @param format 格式
     * @return Date 时间 如果输入为空返回null
     * @exception RuntimeException 如果数据格式不正确
     */
    public static Date parseDate(String date, String format) {
        if (date == null) {
            return null;
        }
        date = date.trim();
        if (date.length() == 0) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //    public static final String[] DAY_OF_WEEK_SHORT_NAME     = new String[] { "日", "一", "二", "三", "四", "五", "六" };
    //    public static final String[] DAY_OF_WEEK_SHORT_NAME_EN  = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
    //            "Sat"};

    /**
     * 格式化时间 格式为：例如2009年12月12日 14：23（二）
     *
     * @param date 时间
     * @param locale 本地化信息
     * @return String 如够date为空，则返回null
     */
    //    public static String formateDateB(Date date, Locale locale) {
    //        if (date == null) {
    //            return "";
    //        }
    //        StringBuilder sb = new StringBuilder();
    //
    //        String format = "";
    //        if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
    //            format = DATE_SHORT_TIME_CN;
    //        } else {
    //            format = DATE_SHORT_TIME_EN;
    //        }
    //        sb.append(formatDate(date, format));
    //        Calendar calendar = Calendar.getInstance();
    //        calendar.setTime(date);
    //
    //        if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
    //            sb.append("（").append(DAY_OF_WEEK_SHORT_NAME[calendar.get(Calendar.DAY_OF_WEEK) - 1]).append("）");
    //        } else {
    //            sb.append("(").append(DAY_OF_WEEK_SHORT_NAME_EN[calendar.get(Calendar.DAY_OF_WEEK) - 1]).append(")");
    //        }
    //
    //        return sb.toString();
    //    }

    /**
     * 把字符串解析为时间类型,解析如下类型格式的时间：<br/>
     * EEE, d MMM yyyy HH:mm:ss Z<br/>
     * 时区为：Locale.US
     *
     * @param date 时间字符串
     * @return Date 时间
     * @throws ParseException 解析失败
     */
    public static Date parse(String date) throws ParseException {
        if (date == null) {
            return null;
        }
        date = date.trim();
        if (date.length() == 0) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_LONG_TIME_FORMAT, Locale.US);
        return format.parse(date);
    }

    /**
     * 把字符串解析为时间类型,解析如下类型格式的时间：<br/>
     * EEE, d MMM yyyy HH:mm:ss Z<br/>
     * 时区为：Locale.US
     *
     * @param date 时间字符串
     * @return Date 时间
     * @throws ParseException 解析失败
     */
    //    public static String formateDataC(Date date) {
    //        if (date == null) {
    //            return null;
    //        }
    //        try {
    //            SimpleDateFormat format = new SimpleDateFormat(DATE_LONG_TIME_FORMAT, Locale.US);
    //            return format.format(date);
    //        } catch (Exception e) {
    //            // TODO: handle exception
    //        }
    //        return null;
    //    }

    /**
     * 把ws时间类型解析为Date,解析如下类型格式的时间：<br/>
     *
     * @param date 时间字符串
     * @return Date 时间
     * @throws ParseException 解析失败
     */
    public static Date parseWsDate(String date) throws ParseException {
        return parseDate(date, WS_DATE_FORMAT);
    }

    //    public static final int               FULL_NAME     = 0;
    //    public static final int               SHORT_NAME    = 1;
    //    public static final int               SHORTEST_NAME = 2;
    //
    //    private static Map<Integer, String[]> weekdayNames  = buildWeekdayNamesTable();
    //    private static Map<Integer, String[]> monthNames    = buildMonthNamesTable();
    //
    //    private static Map<Integer, String[]> buildWeekdayNamesTable() {
    //        Map<Integer, String[]> m = new HashMap<Integer, String[]>();
    //
    //        m.put(Integer.valueOf(Calendar.SUNDAY), new String[] { "display.date.week.full.sunday",
    //                "display.date.week.short.sunday", "display.date.week.shortest.sunday" });
    //        m.put(Integer.valueOf(Calendar.MONDAY), new String[] { "display.date.week.full.monday",
    //                "display.date.week.short.monday", "display.date.week.shortest.monday" });
    //        m.put(Integer.valueOf(Calendar.TUESDAY), new String[] { "display.date.week.full.tuesday",
    //                "display.date.week.short.tuesday", "display.date.week.shortest.tuesday" });
    //        m.put(Integer.valueOf(Calendar.WEDNESDAY), new String[] { "display.date.week.full.wednesday",
    //            "display.date.week.short.wednesday", "display.date.week.shortest.wednesday" });
    //        m.put(Integer.valueOf(Calendar.THURSDAY), new String[] { "display.date.week.full.thursday",
    //                "display.date.week.short.thursday", "display.date.week.shortest.thursday" });
    //        m.put(Integer.valueOf(Calendar.FRIDAY), new String[] { "display.date.week.full.friday",
    //                "display.date.week.short.friday", "display.date.week.shortest.friday" });
    //        m.put(Integer.valueOf(Calendar.SATURDAY), new String[] { "display.date.week.full.saturday",
    //                "display.date.week.short.saturday", "display.date.week.shortest.saturday" });
    //
    //        return m;
    //    }
    //
    //    private static Map<Integer, String[]> buildMonthNamesTable() {
    //        Map<Integer, String[]> m = new HashMap<Integer, String[]>();
    //
    //        m.put(Integer.valueOf(Calendar.JANUARY), new String[] { "display.date.month.full.january",
    //                "display.date.month.short.january", "display.date.month.shortest.january" });
    //        m.put(Integer.valueOf(Calendar.FEBRUARY), new String[] { "display.date.month.full.february",
    //            "display.date.month.short.february", "display.date.month.shortest.february" });
    //        m.put(Integer.valueOf(Calendar.MARCH), new String[] { "display.date.month.full.march",
    //                "display.date.month.short.march", "display.date.month.shortest.march" });
    //        m.put(Integer.valueOf(Calendar.APRIL), new String[] { "display.date.month.full.april",
    //                "display.date.month.short.april", "display.date.month.shortest.april" });
    //        m.put(Integer.valueOf(Calendar.MAY), new String[] { "display.date.month.full.may",
    //                "display.date.month.short.may", "display.date.month.shortest.may" });
    //        m.put(Integer.valueOf(Calendar.JUNE), new String[] { "display.date.month.full.june",
    //                "display.date.month.short.june", "display.date.month.shortest.june" });
    //        m.put(Integer.valueOf(Calendar.JULY), new String[] { "display.date.month.full.july",
    //                "display.date.month.short.july", "display.date.month.shortest.july" });
    //        m.put(Integer.valueOf(Calendar.AUGUST), new String[] { "display.date.month.full.august",
    //                "display.date.month.short.august", "display.date.month.shortest.august" });
    //        m.put(Integer.valueOf(Calendar.SEPTEMBER), new String[] { "display.date.month.full.september",
    //            "display.date.month.short.september", "display.date.month.shortest.september" });
    //        m.put(Integer.valueOf(Calendar.OCTOBER), new String[] { "display.date.month.full.october",
    //                "display.date.month.short.october", "display.date.month.shortest.october" });
    //        m.put(Integer.valueOf(Calendar.NOVEMBER), new String[] { "display.date.month.full.november",
    //            "display.date.month.short.november", "display.date.month.shortest.november" });
    //        m.put(Integer.valueOf(Calendar.DECEMBER), new String[] { "display.date.month.full.december",
    //            "display.date.month.short.december", "display.date.month.shortest.december" });
    //
    //        return m;
    //    }
    //
    //    public static String getWeekdayNameKey(int d, int type) {
    //        if (weekdayNames.containsKey(Integer.valueOf(d))) {
    //            return weekdayNames.get(Integer.valueOf(d))[type];
    //        } else {
    //            return "";
    //        }
    //    }
    //
    //    public static String getMonthNameKey(int d, int type) {
    //        if (monthNames.containsKey(Integer.valueOf(d))) {
    //            return monthNames.get(Integer.valueOf(d))[type];
    //        } else {
    //            return "";
    //        }
    //    }
    //
    //    public static boolean checkDayStart(Calendar cal) {
    //        return cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0;
    //    }
    //
    //    public static String getTwoDigit(int value) {
    //        if (value >= 10) {
    //            return Integer.toString(value);
    //        } else {
    //            return "0" + Integer.toString(value);
    //        }
    //    }

    public static String formatWsDate(Date date) {
        return formatDate(date, "yyyy/MM/dd HH:mm:ss Z");
    }

    /**
     * 校验日期能否转为指定的格式，如果成功返回true 注意不能将 2010－13－01转换为2011－01－01等
     *
     * @param dateSource
     * @param dateFormat
     * @return
     */
    public static boolean validGivenFormat(String dateSource, String dateFormat) {
        boolean result = false;
        if (StringUtils.isEmpty(dateSource)) {
            return false;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            format.setLenient(false);
            format.parse(dateSource);
            result = true;
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

    /**
     * 校验日期能否转为指定的格式，如果成功返回true,且日期大于当前日期 注意不能将 2010－13－01转换为2011－01－01等
     *
     * @param dateSource
     * @param dateFormat
     * @return
     */
    public static boolean validDateAfterToday(String dateSource, String dateFormat) {
        boolean result = false;
        if (StringUtils.isEmpty(dateSource)) {
            return false;
        }
        try {
            result = validGivenFormat(dateSource, dateFormat);
            if (result) {
                Date date = new SimpleDateFormat(dateFormat).parse(dateSource);
                result = date.after(new Date());
            }
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

    /**
     * 转换WS日期字符串至指定格式的日期字符串
     *
     * @param wsDateStr
     * @param format
     * @return
     */
    public static String convertWsDate(String wsDateStr, String format) {
        if (StringUtils.isNotBlank(wsDateStr)) {
            try {
                return formatDate(parseWsDate(wsDateStr), format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取两个指定日期间的所有日期
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static List<Date> getBetweenDate(Date startDate, Date endDate) throws ParseException {
        List<Date> list = new ArrayList<Date>();

        if (startDate != null && endDate != null) {
            GregorianCalendar gc1 = new GregorianCalendar();
            GregorianCalendar gc2 = new GregorianCalendar();
            gc1.setTime(startDate);
            gc2.setTime(endDate);

            GregorianCalendar gc3;
            do {
                gc3 = (GregorianCalendar) gc1.clone();
                if (DateUtils.isSameDay(gc3.getTime(), startDate)) {
                    gc3.set(Calendar.HOUR_OF_DAY, gc1.get(Calendar.HOUR_OF_DAY));
                } else if (DateUtils.isSameDay(gc2, gc3)) {
                    gc3.set(Calendar.HOUR_OF_DAY, gc2.get(Calendar.HOUR_OF_DAY));
                } else {
                    gc3.set(Calendar.HOUR_OF_DAY, 0);
                }
                list.add(gc3.getTime());
                gc1.add(Calendar.DAY_OF_MONTH, 1);
            } while (!gc1.after(gc2));
        } else {
            if (startDate != null) {
                list.add(startDate);
            }
            if (endDate != null) {
                list.add(endDate);
            }
            ;
        }
        return list;
    }

}
