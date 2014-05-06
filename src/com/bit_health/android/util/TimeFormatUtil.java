/********************************************************
 * 类名：TimeFormatUtil.java
 *
 * 作者：陈建平
 * 主要功能：
 * 创建日期：下午2:19:43
 ********************************************************/
package com.bit_health.android.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class TimeFormatUtil {
	/****************************************************************
	 * 方法描述：日期格式转换
	 * 
	 * @param time
	 *            形如：20140121
	 * @return 返回：2014-01-21
	 ***************************************************************/
	static public String turnDateFormat(String dateValue) {
		dateValue = dateValue.subSequence(0, 4) + "-"
				+ dateValue.subSequence(4, 6) + "-"
				+ dateValue.subSequence(6, 8);
		return dateValue;
	}

	/***************************************************************
	 * 方法描述：转换时间格式
	 * 
	 * @param time
	 *            形如：20140122120153
	 * @return 返回：2014-01-22 12:01:53
	 **************************************************************/
	static public String turnDateAndTime(String time) {
		// TODO Auto-generated method stub
		StringBuffer showTimeValue = new StringBuffer();
		showTimeValue.append(time.substring(0, 4)).append("-")
				.append(time.substring(4, 6)).append("-")
				.append(time.substring(6, 8)).append(" ")
				.append(time.substring(8, 10)).append(":")
				.append(time.substring(10, 12)).append(":")
				.append(time.substring(12, 14));
		return showTimeValue.toString();
	}

	/***************************************************************
	 * 方法描述：转换时间格式
	 * 
	 * @param time
	 *            形如：20140122120153
	 * @return 返回：（2015-01-22 12:01） 或 （ 01-22 12:01） 或 （今天 12:01） 或 （昨天 12:01）
	 **************************************************************/
	static public String turnSimpleTime(String time) {
		// TODO Auto-generated method stub
		StringBuffer showTimeValue = new StringBuffer();
		showTimeValue.append(time.substring(0, 4)).append("-")
				.append(time.substring(4, 6)).append("-")
				.append(time.substring(6, 8)).append(" ")
				.append(time.substring(8, 10)).append(":")
				.append(time.substring(10, 12));

		return ecgOrBppShowTime(showTimeValue.toString());
	}

	/****************************************************************
	 * 方法描述：时间格式转换
	 * 
	 * @param time
	 *            形如：1023
	 * @return 返回：10:23
	 ***************************************************************/
	static public String turnTimeFormat(String time) {
		time = time.subSequence(0, 2) + ":" + time.subSequence(2, 4);
		return time;
	}

	/****************************************************************
	 * 方法描述：获取今天的时间，按照20140209140730
	 * 
	 * @return 返回：20140209140730
	 ***************************************************************/
	static public String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		return df.format(new Date());
	}

	/****************************************************************
	 * 方法描述：获取前N天时间，按照20140209140730 int days 前N天时间
	 * 
	 * @return 返回：20140209140730
	 ***************************************************************/
	public static String getPreDaysTime(int days) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		return df.format(date);
	}

	/****************************************************************
	 * 方法描述：获取前N个月的时间，按照20140209140730 int month 前N个月时间
	 * 
	 * @return 返回：20140209140730
	 ***************************************************************/
	public static String getPreMonthTime(int month) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -month);
		date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		return df.format(date);
	}

	public static String formateDate(Date iDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(iDate);
		return dateString;
	}

	/*
	 * 20140212141736
	 */
	public static long strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate.getTime();
	}

	/*
	 * 2014-02-12
	 */
	public static long strToDateLongEx(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate.getTime();
	}

	/***********************************************************
	 * 方法描述：将毫秒转化为：年月日时分秒
	 * 
	 * @param time
	 *            :系统时间的毫秒数
	 * @return 返回格式形如：2014-02-12 09:36:49
	 ***********************************************************/
	public static String longToDate(long time) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		return sdf.format(date);
	}

	/**********************************************************************
	 * 方法描述：报告列表中心电、脉搏的时间显示
	 * 
	 * @param db_time
	 *            2014-03-18 12:22
	 **********************************************************************/
	public static String ecgOrBppShowTime(String db_time) {
		String subNow[] = db_time.split(" ");// 将"2012-12-11 12:22"截取出"12:22"

		SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		String todayDate = s_format.format(curDate);
		String resultTime = "123";// 将显示到列表的时间格式

		if (db_time.substring(0, 4).equals(todayDate.substring(0, 4))) { // 同一年，时间显示格式做如下处理
			if (subNow[0].equals(todayDate)) {// 一天之内显示格式
				resultTime = "今天" + " " + subNow[1];
			} else if (getDateOfYesterday().equals(subNow[0])) {
				resultTime = "昨天" + " " + subNow[1];
			} else {
				String temp = db_time.substring(5, todayDate.length());// 得到形如：04-10
				String monthAndDay[] = temp.split("-");// 分别获取月和日
				if (monthAndDay[0].startsWith("0")) {
					monthAndDay[0] = monthAndDay[0].substring(1);
				}
				if (monthAndDay[1].startsWith("0")) {
					monthAndDay[1] = monthAndDay[1].substring(1);
				}

				resultTime = monthAndDay[0] + "月" + monthAndDay[1] + "日" + " "
						+ subNow[1];// 得到形如：04月10日 19:14
			}
		} else {// 一年之外显示格式：2014-10-1
			resultTime = db_time;
		}
		return resultTime;
	}

	/**********************************************************************
	 * 方法描述：报告列表中血糖和血压的时间显示:今天、昨天、具体日期
	 * 
	 * @param db_time
	 *            :2014-03-18
	 **********************************************************************/
	public static String bsOrBpShowTime(String db_time) {
		SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		String todayDate = s_format.format(curDate);
		String resultTime = "123";// 将显示到列表的时间格式

		if (db_time.substring(0, 4).equals(todayDate.substring(0, 4))) { // 同一年，时间显示格式做如下处理
			if (db_time.equals(todayDate)) {// 一天之内显示格式
				resultTime = "今天";
			} else if (getDateOfYesterday().equals(db_time)) {
				resultTime = "昨天";
			} else {
				String temp = db_time.substring(5, todayDate.length());// 得到形如：04-10
				String monthAndDay[] = temp.split("-");// 分别获取月和日
				if (monthAndDay[0].startsWith("0")) {// 如果以0开头，则把0去掉
					monthAndDay[0] = monthAndDay[0].substring(1);
				}
				if (monthAndDay[1].startsWith("0")) {
					monthAndDay[1] = monthAndDay[1].substring(1);
				}
				resultTime = monthAndDay[0] + "月" + monthAndDay[1] + "日";// 得到形如：4月10日
			}
		} else {// 一年之外显示格式：2014-10-1
			resultTime = db_time;
		}
		return resultTime;
	}

	// 获取昨天的日期
	private static String getDateOfYesterday() {
		SimpleDateFormat _format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		long t = c.getTimeInMillis();
		long l = t - 24 * 3600 * 1000;
		Date d = new Date(l);

		return _format.format(d);
	}

}
