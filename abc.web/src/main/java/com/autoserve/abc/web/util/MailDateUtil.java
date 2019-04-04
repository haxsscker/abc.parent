package com.autoserve.abc.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-10,15:43
 */
public class MailDateUtil {
    /** 时间标志位 */
    public static final int       DAY_FLAG_NEXTYEAR         = -1;
    public static final int       DAY_FLAG_TOMORROW         = 0;
    public static final int       DAY_FLAG_TODAY            = 1;
    /** 昨天 */
    public static final int       DAY_FLAG_YESTERDAY        = 2;
    public static final int       DAY_FLAG_CURRENT_WEEK     = 3;
    public static final int       DAY_FLAG_LAST_WEEK        = 4;
    public static final int       DAY_FLAG_THIS_YEAR        = 5;
    public static final int       DAY_FLAG_OTHER            = 6;

    // 2010年2月21日 11:04
    public static final String    DATE_FORMAT_REPLY_CN      = "yyyy年MM月dd日 HH:mm";
    public static final String    DATE_FORMAT_REPLY_EN      = "yyyy-MM-dd HH:mm";

    public static final String    DAY_FORMAT_CN             = "MM月dd日";
    public static final String    DAY_FORMAT_EN             = "MM-dd";
    public static final String    YESTERDAY_DAY_FORMAT_CN   = "昨天HH:mm";
    public static final String    YESTERDAY_DAY_FORMAT_EN   = "HH:mm";
    public static final String    TIME_FORMAT               = "HH:mm";

    public static final String[] DAY_OF_WEEK_NAME           = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
    public static final String[] DAY_OF_WEEK_NAME_EN        = new String[] { "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"};


    /**
     * 把输入的时间格式化成为字符串 备注： 相关邮件显示业务逻辑如下： <br/>
     * 1. 如果当天是一周的第3～7天，则显示今天，昨天，本周，上周，更早。 对应日期如没有邮件，该项不显示。<br/>
     * 2. 如果当天是一周的第1天，显示今天、上周、更早，没有昨天和本周 <br/>
     * 3. 如果当天是一周的第2天，则显示今天，昨天，上周，没有本周<br/>
     * 格式化输入的时间，并返回 今天、昨天、本周、上周、更早 今天：显示时刻 如：19：24 昨天：显示昨天+时间 如：昨天19：24
     * 本周：显示日期+(周几) 如：11月17日（周二） 上周：显示日期 如：11月12日 更早： 今年显示日期 如：10月12日； 往年显示年月日期
     * 如：2008-10-12
     *
     * @param date 时间
     * @param locale 地区
     * @return String 时间字符串
     */
    public static String formatMailDate(Date date, Locale locale) {
        return formatMailDate(date, new Date(), locale);
    }

    /**
     * 把输入的时间格式化成为字符串 备注： 相关邮件显示业务逻辑如下： <br/>
     * 1. 如果当天是一周的第3～7天，则显示今天，昨天，本周，上周，更早。 对应日期如没有邮件，该项不显示。<br/>
     * 2. 如果当天是一周的第1天，显示今天、上周、更早，没有昨天和本周 <br/>
     * 3. 如果当天是一周的第2天，则显示今天，昨天，上周，没有本周<br/>
     * 格式化输入的时间，并返回 今天、昨天、本周、上周、更早 今天：显示时刻 如：19：24 昨天：显示昨天+时间 如：昨天19：24
     * 本周：显示日期+(周几) 如：11月17日（周二） 上周：显示日期 如：11月12日 更早： 今年显示日期 如：10月12日； 往年显示年月日期
     * 如：2008-10-12
     *
     * @param date 时间
     * @param currentDate 当天时间
     * @param locale 地区
     * @return String 时间字符串
     */
    public static String formatMailDate(Date date, Date currentDate, Locale locale) {
        if (date == null || currentDate == null) {
            return "";
        }
        // 时间标记
        int dayFlag = getDayFlag(date, currentDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String strDate = "";
        String format = "";
        switch (dayFlag) {
            //如果是明年
            case DAY_FLAG_NEXTYEAR:
                strDate = DateUtil.formatDate(date, DateUtil.DATE_FORMAT);
                break;
            //如果是明天以前本年之内
            case DAY_FLAG_TOMORROW:
                if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
                    format = DAY_FORMAT_CN;
                } else {
                    format = DAY_FORMAT_EN;
                }
                strDate = DateUtil.formatDate(date, format);
                break;
            // 输入时间是当天
            case DAY_FLAG_TODAY:
                strDate = DateUtil.formatDate(date, TIME_FORMAT);
                break;
            // 输入时间是昨天
            case DAY_FLAG_YESTERDAY:
                if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
                    format = YESTERDAY_DAY_FORMAT_CN;
                    strDate = DateUtil.formatDate(date, format);
                } else {
                    format = YESTERDAY_DAY_FORMAT_EN;
                    strDate = "Yesterday" + DateUtil.formatDate(date, format);
                }
                break;
            // 输入时间是本周
            case DAY_FLAG_CURRENT_WEEK:
                // 本周：显示日期+(周几)       如：11月17日（周二）
                StringBuffer sb = new StringBuffer();

                if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
                    sb.append(DateUtil.formatDate(date, DAY_FORMAT_CN)).append("（")
                            .append(DAY_OF_WEEK_NAME[cal.get(Calendar.DAY_OF_WEEK) - 1]).append("）");
                } else {
                    sb.append(DateUtil.formatDate(date, DAY_FORMAT_EN)).append("(")
                            .append(DAY_OF_WEEK_NAME_EN[cal.get(Calendar.DAY_OF_WEEK) - 1]).append(")");
                }

                strDate = sb.toString();
                break;
            case DAY_FLAG_OTHER:
                strDate = DateUtil.formatDate(date, DateUtil.DATE_FORMAT);
                break;
            // 如果是上周、本年
            default:
                if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
                    strDate = DateUtil.formatDate(date, DAY_FORMAT_CN);
                } else {
                    strDate = DateUtil.formatDate(date, DAY_FORMAT_EN);
                }
        }

        return strDate;
    }

    /**
     * 根据输入时间，返回对应的标志位。规则如下所示： 相关邮件显示业务逻辑如下： 1. 如果当天是一周的第3～7天，则显示今天，昨天，本周，上周，更早。
     * 对应日期如没有邮件，该项不显示。<br/>
     * 2. 如果当天是一周的第1天，显示今天、上周、更早，没有昨天和本周 <br/>
     * 3. 如果当天是一周的第2天，则显示今天，昨天，上周，没有本周<br/>
     * 返回数值如下所示：<br/>
     * 如果是当天返回 DateUtil.DAY_FLAG_TODAY（包含今天和将来的）<br/>
     * 如果是昨天返回 DateUtil.DAY_FLAG_YESTADAY<br/>
     * 如果是本周返回 DateUtil.DAY_FLAG_CURRENT_WEEK<br/>
     * 如果是上周返回 DateUtil.DAY_FLAG_LAST_WEEK<br/>
     * 如果是本年返回 DateUtil.DAY_FLAG_THIS_YEAR<br/>
     * 如果是其它返回 DateUtil.DAY_FLAG_OTHER <br/>
     *
     * @param date 时间
     * @return int 时间标记
     * @throws IllegalArgumentException if date is null
     */
    public static int getDayFlag(Date date) throws IllegalArgumentException {
        return getDayFlag(date, new Date());
    }

    /**
     * 与<code>getDayFlag(int date)</code>相同，当前时间为currentDate。 当传入的date或current
     *
     * @see #getDayFlag(Date)
     * @param date
     * @param currentDate
     * @return
     */
    public static int getDayFlag(Date date, Date currentDate) {
        if (date == null || currentDate == null) {
            // 业务逻辑上认为null date属于OTHER类型
            return DAY_FLAG_OTHER;
        }

        // get today's start time calendar
        Calendar checkPoint = Calendar.getInstance();
        checkPoint.setTime(currentDate);
        checkPoint.set(Calendar.HOUR_OF_DAY, 0);
        checkPoint.set(Calendar.MINUTE, 0);
        checkPoint.set(Calendar.SECOND, 0);
        checkPoint.set(Calendar.MILLISECOND, 0);

        // get date's calendar
        Calendar dateToCheck = Calendar.getInstance();
        dateToCheck.setTime(date);

        Calendar nextyear = Calendar.getInstance();
        nextyear.setTime(currentDate);
        nextyear.add(Calendar.YEAR, 1);
        nextyear.set(Calendar.MONTH, 0);
        nextyear.set(Calendar.DATE, 1);
        nextyear.set(Calendar.HOUR_OF_DAY, 0);
        nextyear.set(Calendar.MINUTE, 0);
        nextyear.set(Calendar.SECOND, 0);
        nextyear.set(Calendar.MILLISECOND, 0);

        if (dateToCheck.compareTo(nextyear) >= 0) {
            return DAY_FLAG_NEXTYEAR;
        }

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(currentDate);
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);

        if (dateToCheck.compareTo(nextyear) < 0 && dateToCheck.compareTo(tomorrow) >= 0) {
            return DAY_FLAG_TOMORROW;
        }

        // 如果是当天凌晨0点之后
        if (dateToCheck.compareTo(checkPoint) >= 0) {
            return DAY_FLAG_TODAY;
        }

        int dayOfWeek = checkPoint.get(Calendar.DAY_OF_WEEK);
        // 如果当前是周一或者以后，则看是否是昨天
        if (dayOfWeek >= Calendar.MONDAY) {
            checkPoint.add(Calendar.DATE, -1);
            // 如果输入时间大于昨天凌晨0点
            if (dateToCheck.compareTo(checkPoint) >= 0) {
                return DAY_FLAG_YESTERDAY;
            }
            // 如果当前是周二以后,要判断是否是当周
            if (dayOfWeek >= Calendar.TUESDAY) {
                // 设置时间为本周日凌晨0点
                checkPoint.add(Calendar.DAY_OF_MONTH, ((dayOfWeek * -1) + 2));
                // 如果当前时间大于本周日凌晨0点
                if (dateToCheck.compareTo(checkPoint) >= 0) {
                    // 本周：显示日期+(周几)       如：11月17日（周二）
                    StringBuffer sb = new StringBuffer();
                    sb.append(DateUtil.formatDate(date, DAY_FORMAT_CN)).append("（")
                            .append(DAY_OF_WEEK_NAME[dateToCheck.get(Calendar.DAY_OF_WEEK) - 1]).append("）");
                    return DAY_FLAG_CURRENT_WEEK;
                }
            }
        }
        // 设置时间为上周日凌晨0点
        checkPoint.add(Calendar.DAY_OF_MONTH, -7);
        // 如果当前时间大于等于上周日凌晨0点
        if (dateToCheck.compareTo(checkPoint) >= 0) {
            // 显示上周
            return DAY_FLAG_LAST_WEEK;
        } else {
            // 显示更早
            // 取得当前1月1日凌晨0点
            checkPoint = Calendar.getInstance();
            checkPoint.setTime(currentDate);
            checkPoint.set(Calendar.DAY_OF_YEAR, 1);
            checkPoint.set(Calendar.HOUR_OF_DAY, 0);
            checkPoint.set(Calendar.MINUTE, 0);
            checkPoint.set(Calendar.SECOND, 0);
            checkPoint.set(Calendar.MILLISECOND, 0);
            if (dateToCheck.compareTo(checkPoint) >= 0) {
                // 今年显示日期      如：10月12日
                return DAY_FLAG_THIS_YEAR;
            } else {
                // 往年显示年月日期  如：2008-10-12
                return DAY_FLAG_OTHER;
            }
        }
    }

    /**
     * 把时间类型解析为字符串,解析如下类型格式的时间：<br/>
     * 2008年1月1日（星期一）上午 09:30<br/>
     *
     * @param date 时间字符串
     * @return Date 时间
     * @throws java.text.ParseException 解析失败
     */
    public static String formatMailViewDate(Date date, Locale locale) throws ParseException {
        if (date == null) {
            return "";
        }

        SimpleDateFormat format = null;
        if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
            format = new SimpleDateFormat(DateUtil.DATE_FORMAT_VIEW_CN, Locale.CHINA);
        } else {
            format = new SimpleDateFormat(DateUtil.DATE_FORMAT_VIEW_EN, Locale.CHINA);
        }

        return format.format(date);
    }

    /**
     * 把时间类型解析为字符串,解析如下类型格式的时间：<br/>
     * 2008年1月1日（星期一）上午 09:30<br/>
     *
     * @param date 时间字符串
     * @return Date 时间
     * @throws ParseException 解析失败
     */
    public static String formatReplyDate(Date date, Locale locale) throws ParseException {
        if (date == null) {
            return "";
        }

        SimpleDateFormat format = null;
        if (locale == null || "zh".equals(locale.getLanguage()) || "zh_cn".equals(locale.getLanguage())) {
            format = new SimpleDateFormat(DATE_FORMAT_REPLY_CN, Locale.CHINA);
        } else {
            format = new SimpleDateFormat(DATE_FORMAT_REPLY_EN, Locale.CHINA);
        }

        return format.format(date);
    }
}
