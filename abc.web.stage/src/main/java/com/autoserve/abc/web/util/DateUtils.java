package com.autoserve.abc.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.LocalDate;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
			"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
				* 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
				+ sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			before=sdf.parse(sdf.format(before));
			after=sdf.parse(sdf.format(after));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
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
	/**
	 * 获取两时间之间的天、时、分、秒
	 * 
	 * @param before
	 * @param after
	 * @return long[] 天：long[0]、时：long[1]、分：long[2]、秒：long[3] 杨杰
	 */
	public static long[] getDistanceTimes(Date before, Date after) {
		long afterTime = after.getTime();
		long beforeTime = before.getTime();
		long diff;

		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		if (beforeTime < afterTime) {
			diff = afterTime - beforeTime;
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		}

		long[] times = { day, hour, min, sec };
		return times;
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
	 * 将时间中的时分秒置为最大值
	 * 
	 * @param date
	 * @return 例如返回 2015-11-20 23:59:59
	 * @author zhangkang
	 */
	public static Date shortTime(Date date) {
		if (date == null)
			return null;
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
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
	 * 获取日期，不带时间
	 * @return
	 * Date
	 * @author hoader
	 */
	public static Date getDate(Date date){
		if (date == null){
			return null;
		}
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		try {
			result = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
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
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// System.out.println(formatDate(parseDate("2010/3/6")));
		// System.out.println(getDate("yyyy年MM月dd日 E"));
		// long time = new Date().getTime()-parseDate("2012-11-19").getTime();
		// System.out.println(time/(24*60*60*1000));
		Date date = DateUtils.fullTime(null);
		System.out.println(date);
	}
}
