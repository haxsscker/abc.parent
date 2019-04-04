package com.autoserve.abc.service.util;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * 时间处理工具类
 * 
 * @author pp 2014-11-28 上午11:31:45
 */
public class DateUtil {

	public static final String DEFAULT_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_FORMAT_STYLE_NOSPACE = "yyyy-MM-dd_HH:mm:ss";
	public static final String DEFAULT_MINUTE_STYLE = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_MINUTE_STYLE_NOSPACE = "yyyy-MM-dd_HH:mm";
	public static final String DEFAULT_HOUR_STYLE = "yyyy-MM-dd HH";
	public static final String DEFAULT_HOUR_STYLE_NOSPACE = "yyyy-MM-dd_HH";
	public static final String DEFAULT_HOUR_STYLE_NOYEAR = "MM-dd HH:mm";
	public static final String DEFAULT_ONLYHOUR_STYLE = "HH:mm";
	public static final String DEFAULT_DAY_STYLE = "yyyy-MM-dd";
	public static final String DEFAULT_DAY_STYLE_NOYEAR = "MM-dd";
	public static final String DEFAULT_DAY_STYLE_NOSPACE = "yyyyMMdd";
	public static final String DEFAULT_MILLISECOND_STYLE = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String DEFAULT_MILLISECOND_STYLE_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DEFAULT_ONLYSEC_STYLE = "mm:ss";
	public static final String DEFAULT_ONLY_HOUR_STYLE = "HH";
	public static final String DEFAULT_HAOMIAO = "hhmmssSSS";
	public static final String DEFAULT_FULL = "yyyyMMddhhmmssSSS";
	public static final String DEFAULT_DATE_STYLE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 计算当天内24个小时的起始点
	 * 
	 * @param day
	 *            天
	 * @return 24小时的起点
	 * @throws ParseException
	 */
	public static List<Date> fetchAllHours(String day) throws ParseException {

		Date startDate = DateUtil.parseDate(day, DateUtil.DEFAULT_DAY_STYLE);

		List<Date> results = new ArrayList<Date>();

		results.add(startDate);

		for (int i = 1; i < 24; i++) {
			results.add(DateUtil.after(i, startDate, TimeUnit.HOURS));
		}

		return results;
	}

	public static int diff(Date startDate, Date endDate, TimeUnit timeUnit) {
		long diff = endDate.getTime() - startDate.getTime();
		if (timeUnit == TimeUnit.SECONDS) {
			return (int) (diff / 1000);
		} else if (timeUnit == TimeUnit.MINUTES) {
			return (int) (diff / 1000 / 60);
		} else if (timeUnit == TimeUnit.HOURS) {
			return (int) (diff / 1000 / 60 / 60);
		} else if(timeUnit==TimeUnit.DAYS){
        	return (int) (diff / 1000 / 60 / 60 / 24);
        } else {
			throw new RuntimeException("only support second/minute/hour/day!");
		}
	}
	
	/**
	 * 计算两个日期相差的天数-不考虑时间
	 * date1-date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int substractDay(Date date1, Date date2) {
		if(date1==null || date2==null){
			throw new IllegalArgumentException("参数不合法");
		}
		LocalDate localDate1 = new LocalDate(date1);// 去掉时间
		LocalDate localDate2 = new LocalDate(date2);
		long diff = localDate1.toDate().getTime()
				- localDate2.toDate().getTime();
		return (int)(diff / (1000 * 60 * 60 * 24));
	}
	
	/**
	 * 比较两个日期-只考虑日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDay(Date date1, Date date2){
		LocalDate localDate1 = new LocalDate(date1);// 去掉时间
		LocalDate localDate2 = new LocalDate(date2);
		return localDate1.compareTo(localDate2);
	}

//	public static void main(String[] args) {
//		DateTime date = new DateTime(2015,10, 19, 9, 55 ,0);
//		System.out.println(DateUtil.formatDate(date.toDate()));
//		System.out.println(DateUtil.formatDate(new Date()));
//		System.out.println(compareDay(date.toDate(), new Date()));
//	}

	/**
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getBetweenMin(Date start, Date end)
			throws ParseException {
		if (!start.before(end)) {
			return null;
		}
		start = DateUtil.format2Min(start);
		List<Date> list = new ArrayList<Date>();
		while (start.before(end)) {
			list.add(start);
			start = after(1, start, TimeUnit.MINUTES);
		}
		return list;
	}

	public static List<Date> getBetweenHours(Date start, Date end)
			throws ParseException {
		if (!start.before(end)) {
			return null;
		}
		start = DateUtil.format2Hour(start);
		List<Date> list = new ArrayList<Date>();
		while (start.before(end)) {
			list.add(start);
			start = after(1, start, TimeUnit.HOURS);
		}
		return list;
	}

	/**
	 * 获取start到end之间的所有的精确到日的日期；注意右边是开区间 例如：2013-01-13 02:02:02~2013-01-14
	 * 02:02:02 返回[2013-01-13,2013-01-14]; 2013-01-13 02:02:02~2013-01-14
	 * 00:00:00 返回[2013-01-13]
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getBetweenDays(Date start, Date end)
			throws ParseException {
		if (!start.before(end)) {
			return null;
		}

		List<Date> list = new ArrayList<Date>();
		Date date = format2Day(start);
		while (date.before(end)) {
			list.add(date);
			date = after(1, date, TimeUnit.DAYS);
		}

		return list;
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getPastHours(int hours) throws ParseException {
		// now
		Date d = format2Hour(before(hours, HOURS));

		// get past 24 hours
		List<Date> pastHours = new ArrayList<Date>();
		for (int i = 0; i < hours; i++) {
			pastHours.add(d);
			d = before(-1, d, HOURS);
		}

		return pastHours;
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getPast15Days() throws ParseException {
		Date d = format2Day(before(15, DAYS));

		List<Date> past15Days = new ArrayList<Date>();
		for (int i = 0; i < 15; i++) {
			past15Days.add(d);
			d = before(-1, d, DAYS);
		}

		return past15Days;
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getPast30Minutes() throws ParseException {
		// now
		Date d = format2Min(before(30, MINUTES));

		// get past 30 minutes
		List<Date> past30Minutes = new ArrayList<Date>();
		for (int i = 0; i < 30; i++) {
			past30Minutes.add(d);
			d = before(-1, d, MINUTES);
		}

		return past30Minutes;
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getPast12Hours() throws ParseException {
		// now
		Date d = format2Hour(before(12, HOURS));

		// get past 24 hours
		List<Date> past12Hours = new ArrayList<Date>();
		for (int i = 0; i < 12; i++) {
			past12Hours.add(d);
			d = before(-1, d, HOURS);
		}

		return past12Hours;
	}

	/**
	 * 将日期转换成"yyyy-MM-dd HH:mm:ss"格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_FORMAT_STYLE);
	}

	/**
	 * @param date
	 * @param formatStyle
	 * @return
	 */
	public static String formatDate(Date date, String formatStyle) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
		return sdf.format(date);
	}

	/**
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString) throws ParseException {
		return parseDate(dateString, DEFAULT_FORMAT_STYLE);
	}

	/**
	 * @param dateString
	 * @param formatStyle
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString, String formatStyle)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
		return sdf.parse(dateString);
	}

	/**
	 * @param times
	 * @param timeUnit
	 * @return
	 */
	public static Date before(int times, TimeUnit timeUnit) {
		return before(times, new Date(), timeUnit);
	}

	/**
	 * @param times
	 * @param d
	 * @param timeUnit
	 * @return
	 */
	public static Date before(int times, Date date, TimeUnit timeUnit) {
		// ignore millisecond
		long d = date.getTime() / 1000;
		if (timeUnit == SECONDS) {
			return new Date((d - times) * 1000);
		} else if (timeUnit == MINUTES) {
			return new Date((d - times * 60) * 1000);
		} else if (timeUnit == HOURS) {
			return new Date((d - times * 60 * 60) * 1000);
		} else if (timeUnit == DAYS) {
			return new Date((d - times * 60 * 60 * 24) * 1000);
		}

		throw new RuntimeException(
				"only support TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS!");
	}

	/**
	 * @param times
	 * @param date
	 * @param timeUnit
	 * @return
	 */
	public static Date after(int times, Date date, TimeUnit timeUnit) {
		// ignore millisecond
		long d = date.getTime() / 1000;
		if (timeUnit == SECONDS) {
			return new Date((d + times) * 1000);
		} else if (timeUnit == MINUTES) {
			return new Date((d + times * 60) * 1000);
		} else if (timeUnit == HOURS) {
			return new Date((d + times * 60 * 60) * 1000);
		} else if (timeUnit == DAYS) {
			return new Date((d + times * 60 * 60 * 24) * 1000);
		}
		throw new RuntimeException(
				"only support TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS !");
	}

	/**
	 * @return java.util.Date
	 */
	public static Date getTomorrow() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.DATE, rightNow.get(Calendar.DATE) + 1);
		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MILLISECOND, 0);
		return rightNow.getTime();
	}

	public static Date getMorning() {
		return getMorning(new Date());
	}

	public static Date getYesterday() {
		return getMorning(DateUtil.before(1, TimeUnit.DAYS));
	}

	public static Date getMorning(Date d) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d);

		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MILLISECOND, 0);
		return rightNow.getTime();
	}

	public static Date getDateWithOffset(int offset) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.DATE, rightNow.get(Calendar.DATE) + offset);
		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MILLISECOND, 0);
		return rightNow.getTime();
	}

	public static Date getHourWithOffset(int offset) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.HOUR_OF_DAY, rightNow.get(Calendar.HOUR_OF_DAY)
				+ offset);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MILLISECOND, 0);
		return rightNow.getTime();
	}

	public static Date addSeconds(Date date, long secs) {
		return new Date(date.getTime() + (secs * 1000));
	}

	public static Date addHours(Date date, long hours) {
		return new Date(date.getTime() + (hours * 60 * 60 * 1000));
	}

	/**
	 * @param d
	 * @return
	 */
	public static List<String> roundOneMinute(Date d) {
		return roundOneMinute(d, DEFAULT_FORMAT_STYLE_NOSPACE);
	}

	/**
	 * @param d
	 * @param formatStyle
	 * @return
	 */
	public static List<String> roundOneMinute(Date d, String formatStyle) {
		List<String> dateStringList = new ArrayList<String>(60);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		for (int i = 0; i < 60; i++) {
			calendar.set(Calendar.SECOND, i);
			dateStringList.add(formatDate(calendar.getTime(), formatStyle));
		}

		return dateStringList;
	}

	/**
	 * @param d
	 * @return
	 */
	public static String formatDay(Date d) {
		return formatDate(d, DEFAULT_DAY_STYLE);
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date format2Hour(Date date) throws ParseException {
		return parseDate(formatDate(date, DEFAULT_HOUR_STYLE),
				DEFAULT_HOUR_STYLE);
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date format2Min(Date date) throws ParseException {
		return parseDate(formatDate(date, DEFAULT_MINUTE_STYLE),
				DEFAULT_MINUTE_STYLE);
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date format2Second(Date date) throws ParseException {
		return parseDate(formatDate(date, DEFAULT_FORMAT_STYLE),
				DEFAULT_FORMAT_STYLE);
	}

	/**
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date format2Day(Date d) throws ParseException {
		return parseDate(formatDate(d), DEFAULT_DAY_STYLE);
	}

	public static long getSystemTime() {
		return System.currentTimeMillis();
	}

	private DateUtil() {
	}
	
	/**
     * 取得当前日期所在周的第一天(周一)
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
    	if(date==null){
    		return null;
    	}
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }
 
    /**
     * 取得当前日期所在周的最后一天(周日)
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
    	if(date==null){
    		return null;
    	}
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }
    
    /**
     * 取得当前日期所在月的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
    	if(date==null){
    		return null;
    	}
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }
    
    /**
     * 取得当前日期所在月的最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
    	if(date==null){
    		return null;
    	}
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
    
    /**
     * 取得当前日期所在年的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
    	if(date==null){
    		return null;
    	}
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, 1);
        return c.getTime();
    }
    
    /**
     * 取得当前日期所在年的最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
    	if(date==null){
    		return null;
    	}
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        c.set(Calendar.DAY_OF_MONTH, 31);
        return c.getTime();
    }
    
    /**
	 * 将时间中的时分秒置为0
	 * 
	 * @param date
	 * @return 例如返回 2015-11-20 00:00:00
	 * @author zhangkang
	 */
	public static Date clearTime(Date date) {
		if (date == null)
			return null;
		LocalDate result = new LocalDate(date);
		return result.toDate();
	}

	/**
	 * 将时间中的时分秒置为最大值
	 * 
	 * @param date
	 * @return 例如返回 2015-11-20 23:59:59
	 * @author zhangkang
	 */
	public static Date fullTime(Date date) {
		if (date == null)
			return null;
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		try {
			result = sdf2.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 日期加天数
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Date addDate(Date d, int day) throws ParseException {
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(d);
		fromCal.add(Calendar.DATE, day);
		return fromCal.getTime();
	}
	
	/**
	 * 日期加月份
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Date addMonth(Date d, int month) throws ParseException {
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(d);
		fromCal.add(Calendar.MONTH, month);
		return fromCal.getTime();
	}
	/**
	 * 日期加年份
	 * @param d
	 * @param year
	 * @return
	 * @throws ParseException
	 */
	public static Date addYear(Date d, int year) throws ParseException {
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(d);
		fromCal.add(Calendar.YEAR, year);
		return fromCal.getTime();
	}
    public static void main(String[] args) throws ParseException {
    	Date parseDate1 = parseDate("2018-08-22 00:00:00",DEFAULT_FORMAT_STYLE);
    	Date parseDate2 = parseDate("2018-07-20 00:00:00",DEFAULT_FORMAT_STYLE);
    	Date parseDate3 = parseDate("2018-08-16 00:00:00",DEFAULT_FORMAT_STYLE);
    	
    	BigDecimal allDay = new BigDecimal(DateUtil.substractDay(
    			parseDate1, parseDate2));
		BigDecimal afterDay = new BigDecimal(DateUtil.substractDay(
				parseDate1, parseDate3));
		
		Date paytime = DateUtil.parseDate(DateUtil.formatDate(parseDate1, DateUtil.DEFAULT_DAY_STYLE)+" 00:00:00",DateUtil.DEFAULT_FORMAT_STYLE);

		System.out.println(DateUtil.diff(new Date(),DateUtil.addHours(parseDate1, 16),TimeUnit.SECONDS));
		//System.out.println(afterDay);
	}
}
