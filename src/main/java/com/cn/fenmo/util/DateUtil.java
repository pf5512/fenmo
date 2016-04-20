package com.cn.fenmo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 文件名: DateUtil 项目工程名: 系统通用模块 
 * 描述: 时间，日期操作工具
 * JDK版本:jdk 1.60+
 * @category 基础类
 * @author ray 日期: 2014-7-1
 * @version v 1.0
 */
public class DateUtil {
	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31 };
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	/** yyyy */
	public static final SimpleDateFormat DATE_FORMAT_YYYY = new SimpleDateFormat(
			"yyyy");
	/** yyyyMM */
	public static final SimpleDateFormat DATE_FORMAT_YYYY_MM = new SimpleDateFormat("yyyyMM");
	/** yyyyMMdd */
	public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat(
			"yyyyMMdd");
	/** yyyyMMddHHmm */
	public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HH_MM = new SimpleDateFormat(
			"yyyyMMddHHmm");
	/** yyyyMMddHHmmss */
	public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/** yyyyMMddHHmmss */
	public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	/** yyyy-MM */
	public static final SimpleDateFormat DATE_FORMAT_DASH_YYYY_MM = new SimpleDateFormat(
			"yyyy-MM");
	/** yyyy-MM-dd */
	public static final SimpleDateFormat DATE_FORMAT_DASH_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	/** yyyy-MM-dd HH:mm */
	public static final SimpleDateFormat DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	/** yyyy-MM-dd HH:mm:ss */
	public static final SimpleDateFormat DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** yyyy-MM-dd HH:mm:ss,SSS */
	public static final SimpleDateFormat DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS_SSS = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss,SSS");
	/** yyyy/MM */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_YYYY_MM = new SimpleDateFormat(
			"yyyy/MM");
	/** yyyy/MM/dd */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_YYYY_MM_DD = new SimpleDateFormat(
			"yyyy/MM/dd");
	/** yyyy/MM/dd HH:mm */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");

	/** yyyy/MM/dd HH:mm:ss */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	/** yyyy/MM/dd HH:mm:ss,SSS */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS_SSS = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss,SSS");

	/** yyyy年MM月 */
	public static final SimpleDateFormat DATE_FORMAT_CHINESE_YYYY_MM = new SimpleDateFormat(
			"yyyy年MM月");

	/** yyyy年MM月dd日 */
	public static final SimpleDateFormat DATE_FORMAT_CHINESE_YYYY_MM_DD = new SimpleDateFormat(
			"yyyy年MM月dd日");

	/** yyyy年MM月dd日 HH:mm */
	public static final SimpleDateFormat DATE_FORMAT_CHINESE_YYYY_MM_DD_HH_MM = new SimpleDateFormat(
			"yyyy年MM月dd日 HH:mm");

	/** yyyy-MM-dd_HH-mm-ss */
	public static final SimpleDateFormat DATE_FORMAT_FILE_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
			"yyyy-MM-dd_HH-mm-ss");

	/** 标准日期格式 */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_MM_DD_YYYY = new SimpleDateFormat(
			"MM/dd/yyyy");

	/** 标准时间格式 */
	public static final SimpleDateFormat DATE_FORMAT_SLASH_MM_DD_YYYY_HH_MM = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm");

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            要转换的日期
	 * @param inPattern
	 *            转换前的格式
	 * @param outPattern
	 *            转换后的格式
	 * @return
	 * @throws ParseException 
	 */
	public static synchronized String dateFormat(String date,
			SimpleDateFormat inPattern, SimpleDateFormat outPattern) throws ParseException {
		if (date == null || "".equals(date)) {
			return "";
		} else {
			return getDateFormat(
					DateUtil.parseDateFormat(date, inPattern.toPattern()),
					outPattern.toPattern());
		}
	}

	/**
	 * 取得Calendar
	 * 
	 * @return Calendar
	 */
	public static synchronized Calendar getCalendar() {
		return GregorianCalendar.getInstance();
	}

	// ===================================================================== //
	// 无斜线、无破折号
	// ===================================================================== //

	/**
	 * pattern = "yyyy"
	 * 
	 * @return String
	 */
	public static synchronized String getDateYearFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateYearFormat(cal);
	}

	/**
	 * pattern = "yyyy"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateYearFormat(Calendar cal) {
		String pattern = DATE_FORMAT_YYYY.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateYearFormat(Date date) {
		String pattern = DATE_FORMAT_YYYY.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarYearFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateYearFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyyMM"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMonthFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMonthFormat(cal);
	}

	/**
	 * pattern = "yyyyMM"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMonthFormat(Calendar cal) {
		String pattern = DATE_FORMAT_YYYY_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyyMM"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMonthFormat(Date date) {
		String pattern = DATE_FORMAT_YYYY_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyyMM"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMonthFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyyMM"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMonthFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyyMMdd"
	 * 
	 * @return String
	 */
	public static synchronized String getDateDayFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayFormat(cal);
	}

	/**
	 * pattern = "yyyyMMdd"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDayFormat(Calendar cal) {
		String pattern = DATE_FORMAT_YYYY_MM_DD.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyyMMdd"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateDayFormat(Date date) {
		String pattern = DATE_FORMAT_YYYY_MM_DD.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyyMMdd"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarDayFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyyMMdd"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateDayFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyyMMddHHmm"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteFormat(cal);
	}

	/**
	 * pattern = "yyyyMMddHHmm"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(Calendar cal) {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmm"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(Date date) {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmm"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMinuteFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmm"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMinuteFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyyMMddHHmmss"
	 * 
	 * @return String
	 */
	public static synchronized String getDateSecondFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondFormat(cal);
	}

	/**
	 * pattern = "yyyyMMddHHmmss"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(Calendar cal) {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmmss"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(Date date) {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmmss"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarSecondFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmmss"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateSecondFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyyMMddHHmmssSSS"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMilliFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliFormat(cal);
	}

	/**
	 * pattern = "yyyyMMddHHmmssSSS"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(Calendar cal) {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmmssSSS"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(Date date) {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmmssSSS"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMilliFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyyMMddHHmmssSSS"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMilliFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //
	// 破折号
	// ===================================================================== //

	/**
	 * pattern = "yyyy-MM"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMonthDashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMonthDashFormat(cal);
	}

	/**
	 * pattern = "yyyy-MM"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMonthDashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy-MM"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMonthDashFormat(Date date) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy-MM"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMonthDashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy-MM"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMonthDashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy-MM-dd"
	 * 
	 * @return String
	 */
	public static synchronized String getDateDayDashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayDashFormat(cal);
	}

	/**
	 * pattern = "yyyy-MM-dd"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDayDashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateDayDashFormat(Date date) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarDayDashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateDayDashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy-MM-dd HH:mm"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMinuteDashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteDashFormat(cal);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteDashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteDashFormat(Date date) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMinuteDashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMinuteDashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return String
	 */
	public static synchronized String getDateSecondDashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondDashFormat(cal);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateSecondDashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondDashFormat(Date date) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarSecondDashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateSecondDashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss,SSS"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMilliDashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliDashFormat(cal);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss,SSS"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMilliDashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss,SSS"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMilliDashFormat(Date date) {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss,SSS"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMilliDashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd HH:mm:ss,SSS"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMilliDashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_DASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //
	// 斜线
	// ===================================================================== //

	/**
	 * pattern = "yyyy/MM"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMonthSlashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMonthSlashFormat(cal);
	}

	/**
	 * pattern = "yyyy/MM"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMonthSlashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy/MM"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMonthSlashFormat(Date date) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy/MM"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMonthSlashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy/MM"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMonthSlashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy/MM/dd"
	 * 
	 * @return String
	 */
	public static synchronized String getDateDaySlashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDaySlashFormat(cal);
	}

	/**
	 * pattern = "yyyy/MM/dd"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDaySlashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateDaySlashFormat(Date date) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarDaySlashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateDaySlashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy/MM/dd HH:mm"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMinuteSlashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteSlashFormat(cal);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteSlashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteSlashFormat(Date date) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMinuteSlashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMinuteSlashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss"
	 * 
	 * @return String
	 */
	public static synchronized String getDateSecondSlashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondSlashFormat(cal);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateSecondSlashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondSlashFormat(Date date) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarSecondSlashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateSecondSlashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss,SSS"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMilliSlashFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliSlashFormat(cal);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss,SSS"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMilliSlashFormat(Calendar cal) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss,SSS"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMilliSlashFormat(Date date) {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss,SSS"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMilliSlashFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy/MM/dd HH:mm:ss,SSS"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMilliSlashFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_SLASH_YYYY_MM_DD_HH_MM_SS_SSS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //
	// 带有汉字格式的日期
	// ===================================================================== //

	/**
	 * pattern = "yyyy年MM月"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMonthChineseFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMonthChineseFormat(cal);
	}

	/**
	 * pattern = "yyyy年MM月"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMonthChineseFormat(Calendar cal) {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy年MM月"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMonthChineseFormat(Date date) {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy年MM月"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMonthChineseFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy年MM月"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMonthChineseFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy年MM月dd日"
	 * 
	 * @return String
	 */
	public static synchronized String getDateDayChineseFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayChineseFormat(cal);
	}

	/**
	 * pattern = "yyyy年MM月dd日"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDayChineseFormat(Calendar cal) {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy年MM月dd日"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateDayChineseFormat(Date date) {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy年MM月dd日"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarDayChineseFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy年MM月dd日"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateDayChineseFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //

	/**
	 * pattern = "yyyy年MM月dd日 HH:mm"
	 * 
	 * @return String
	 */
	public static synchronized String getDateMinuteChineseFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteChineseFormat(cal);
	}

	/**
	 * pattern = "yyyy年MM月dd日 HH:mm"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteChineseFormat(Calendar cal) {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy年MM月dd日 HH:mm"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteChineseFormat(Date date) {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD_HH_MM.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy年MM月dd日 HH:mm"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarMinuteChineseFormat(
			String strDate) throws ParseException {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD_HH_MM.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy年MM月dd日 HH:mm"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateMinuteChineseFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_CHINESE_YYYY_MM_DD_HH_MM.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //
	// 文件格式的日期
	// ===================================================================== //

	/**
	 * pattern = "yyyy-MM-dd_HH-mm-ss"
	 * 
	 * @return String
	 */
	public static synchronized String getDateFileFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateFileFormat(cal);
	}

	/**
	 * pattern = "yyyy-MM-dd_HH-mm-ss"
	 * 
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFileFormat(Calendar cal) {
		String pattern = DATE_FORMAT_FILE_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(cal, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd_HH-mm-ss"
	 * 
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFileFormat(Date date) {
		String pattern = DATE_FORMAT_FILE_YYYY_MM_DD_HH_MM_SS.toPattern();
		return getDateFormat(date, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd_HH-mm-ss"
	 * 
	 * @param strDate
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarFileFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_FILE_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * pattern = "yyyy-MM-dd_HH-mm-ss"
	 * 
	 * @param strDate
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateFileFormat(String strDate) throws ParseException {
		String pattern = DATE_FORMAT_FILE_YYYY_MM_DD_HH_MM_SS.toPattern();
		return parseDateFormat(strDate, pattern);
	}

	// ===================================================================== //
	//
	// ===================================================================== //

	/**
	 * @param cal
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(Calendar cal, String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(Date date, String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return Calendar
	 * @throws ParseException 
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate,
			String pattern) throws ParseException {
		synchronized (sdf) {
			Calendar cal = null;
			sdf.applyPattern(pattern);
				sdf.parse(strDate);
				cal = sdf.getCalendar();
			return cal;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return Date
	 * @throws ParseException 
	 */
	public static synchronized Date parseDateFormat(String strDate,
			String pattern) throws ParseException {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
				date = sdf.parse(strDate);
			return date;
		}
	}

	// ===================================================================== //
	// 日期的计算
	// ===================================================================== //

	/**
	 * @param month
	 * @return
	 */
	public static synchronized int getLastDayOfMonth(int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear()) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	public static synchronized int getLastDayOfMonth(int year, int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear(year)) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	/**
	 * @return
	 */
	public static synchronized boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
	 * 
	 * @param year
	 * @return
	 */
	public static synchronized boolean isLeapYear(int year) {
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static synchronized boolean isLeapYear(Date date) {
		// int year = date.getYear();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized boolean isLeapYear(Calendar gc) {
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 得到指定日期的前一个工作日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的前一个工作日
	 */
	public static synchronized Date getPreviousWeekDay(Date date) {
		{
			// 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
			GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			gc.setTime(date);
			return getPreviousWeekDay(gc);
		}
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Date getPreviousWeekDay(Calendar gc) {
		{
			// 1.如果date是星期日，则减3天 2.如果date是星期六，则减2天 3.否则减1天
			switch (gc.get(Calendar.DAY_OF_WEEK)) {
			case (Calendar.MONDAY):
				gc.add(Calendar.DATE, -3);
				break;
			case (Calendar.SUNDAY):
				gc.add(Calendar.DATE, -2);
				break;
			default:
				gc.add(Calendar.DATE, -1);
				break;
			}
			return gc.getTime();
		}
	}

	/**
	 * 得到指定日期的后一个工作日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的后一个工作日
	 */
	public static synchronized Date getNextWeekDay(Date date) {
		// 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getNextWeekDay(Calendar gc) {
		// 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc;
	}

	/**
	 * 取得指定日期的下一个月的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月的最后一天
	 */
	public static synchronized Date getLastDayOfNextMonth(Date date) {
		// 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getLastDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的最后一天
	 */
	public static synchronized Date getLastDayOfNextWeek(Date date) {
		// 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getLastDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月的第一天
	 */
	public static synchronized Date getFirstDayOfNextMonth(Date date) {
		// 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized Calendar getFirstDayOfNextMonth(Calendar gc) {
		// 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
		return gc;
	}

	/**
	 * 取得指定日期的下一个星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的第一天
	 */
	public static synchronized Date getFirstDayOfNextWeek(Date date) {
		// 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized Calendar getFirstDayOfNextWeek(Calendar gc) {
		// 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
		return gc;
	}

	/**
	 * 取得指定日期的下一年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一年
	 */
	public static synchronized Date getNextYear(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.YEAR, 1);
		return gc.getTime();
	}

	/**
	 * 取得下一年
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getNextYear(Calendar gc) {
		gc.add(Calendar.YEAR, 1);
		return gc;
	}

	/**
	 * 取得指定日期的前一年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的前一年
	 */
	public static synchronized Date getPreviousYear(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.YEAR, -1);
		return gc.getTime();
	}

	/**
	 * 取得前一年
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getPreviousYear(Calendar gc) {
		gc.add(Calendar.YEAR, -1);
		return gc;
	}

	/**
	 * 指定日期加N年
	 * 
	 * @param date
	 *            指定日期。
	 * @param n
	 *            n是数字
	 * @return 指定日期加N年
	 */
	public static synchronized Date getAddNYear(Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.YEAR, n);
		return gc.getTime();
	}

	/**
	 * 指定日期加N年
	 * 
	 * @param gc
	 * @param n
	 * @return
	 */
	public static synchronized Calendar getAddNYear(Calendar gc, int n) {
		gc.add(Calendar.YEAR, n);
		return gc;
	}

	/**
	 * 取得指定日期的下一个月
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月
	 */
	public static synchronized Date getNextMonth(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}

	/**
	 * 取得下一个月
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getNextMonth(Calendar gc) {
		gc.add(Calendar.MONTH, 1);
		return gc;
	}

	/**
	 * 取得指定日期的前一个月
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的前一个月
	 */
	public static synchronized Date getPreviousMonth(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -1);
		return gc.getTime();
	}

	/**
	 * 取得前一个月
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getPreviousMonth(Calendar gc) {
		gc.add(Calendar.MONTH, -1);
		return gc;
	}

	/**
	 * 指定日期加N月
	 * 
	 * @param date
	 *            指定日期。
	 * @param n
	 * @return 指定日期加N月
	 */
	public static synchronized Date getAddNMonth(Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, n);
		return gc.getTime();
	}

	/**
	 * 指定日期加N月
	 * 
	 * @param gc
	 * @param n
	 * @return
	 */
	public static synchronized Calendar getAddNMonth(Calendar gc, int n) {
		gc.add(Calendar.MONTH, n);
		return gc;
	}

	/**
	 * 取得指定日期的下一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一天
	 */
	public static synchronized Date getNextDay(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getNextDay(Calendar gc) {
		gc.add(Calendar.DATE, 1);
		return gc;
	}

	/**
	 * 取得指定日期的前一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的前一天
	 */
	public static synchronized Date getPreviousDay(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getPreviousDay(Calendar gc) {
		gc.add(Calendar.DATE, -1);
		return gc;
	}

	/**
	 * 指定日期加N天
	 * 
	 * @param date
	 *            指定日期。
	 * @param n
	 * @return 指定日期加N天
	 */
	public static synchronized Date getAddNDay(Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, n);
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @param n
	 * @return
	 */
	public static synchronized Calendar getAddNDay(Calendar gc, int n) {
		gc.add(Calendar.DATE, n);
		return gc;
	}

	/**
	 * 取得指定日期的下一个星期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期
	 */
	public static synchronized Date getNextWeek(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getNextWeek(Calendar gc) {
		gc.add(Calendar.DATE, 7);
		return gc;
	}

	/**
	 * 指定日期加N个星期
	 * 
	 * @param date
	 *            指定日期。
	 * @param n
	 * @return 指定日期加N个星期
	 */
	public static synchronized Date getAddNWeek(Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7 * n);
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @param n
	 * @return
	 */
	public static synchronized Calendar getAddNWeek(Calendar gc, int n) {
		gc.add(Calendar.DATE, 7 * n);
		return gc;
	}

	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的最后一天
	 */
	public static synchronized Date getLastDayOfWeek(Date date) {
		// 1.如果date是星期日，则加6天
		// 2.如果date是星期一，则加5天
		// 3.如果date是星期二，则加4天
		// 4.如果date是星期三，则加3天
		// 5.如果date是星期四，则加2天
		// 6.如果date是星期五，则加1天
		// 7.如果date是星期六，则加0天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 1);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 0);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的最后一天
	 */
	public static synchronized Date getChineseLastDayOfWeek(Date date) {
		// 1.如果date是星期日，则加6天
		// 2.如果date是星期一，则加5天
		// 3.如果date是星期二，则加4天
		// 4.如果date是星期三，则加3天
		// 5.如果date是星期四，则加2天
		// 6.如果date是星期五，则加1天
		// 7.如果date是星期六，则加0天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 1);
			break;
		}
		gc.set(Calendar.HOUR_OF_DAY, 23);//add:wuyub
		gc.set(Calendar.MINUTE, 59);//add:wuyub
		gc.set(Calendar.SECOND, 59);//add:wuyub
		return gc.getTime();
	}	
	
	/**
	 * 取得指定日期的所处星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的第一天
	 */
	public static synchronized Date getFirstDayOfWeek(Date date) {
		// 1.如果date是星期日，则减0天
		// 2.如果date是星期一，则减1天
		// 3.如果date是星期二，则减2天
		// 4.如果date是星期三，则减3天
		// 5.如果date是星期四，则减4天
		// 6.如果date是星期五，则减5天
		// 7.如果date是星期六，则减6天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的第一天
	 */
	public static synchronized Date getChineseFirstDayOfWeek(Date date) {
		// 1.如果date是星期日，则减0天
		// 2.如果date是星期一，则减1天
		// 3.如果date是星期二，则减2天
		// 4.如果date是星期三，则减3天
		// 5.如果date是星期四，则减4天
		// 6.如果date是星期五，则减5天
		// 7.如果date是星期六，则减6天
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, -6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -5);
			break;
		}
		gc.set(Calendar.HOUR_OF_DAY, 0);//add:wuyub
		gc.set(Calendar.MINUTE, 0);//add:wuyub
		gc.set(Calendar.SECOND, 0);//add:wuyub
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getFirstDayOfWeek(Calendar gc) {
		// 1.如果date是星期日，则减0天
		// 2.如果date是星期一，则减1天
		// 3.如果date是星期二，则减2天
		// 4.如果date是星期三，则减3天
		// 5.如果date是星期四，则减4天
		// 6.如果date是星期五，则减5天
		// 7.如果date是星期六，则减6天
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc;
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getChineseFirstDayOfWeek(Calendar gc) {
		// 1.如果date是星期日，则减0天
		// 2.如果date是星期一，则减1天
		// 3.如果date是星期二，则减2天
		// 4.如果date是星期三，则减3天
		// 5.如果date是星期四，则减4天
		// 6.如果date是星期五，则减5天
		// 7.如果date是星期六，则减6天
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, -6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -5);
			break;
		}
		return gc;
	}

	/**
	 * 取得指定日期的所处月份的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的最后一天
	 */
	public static synchronized Date getLastDayOfMonth(Date date) {
		// 1.如果date在1月，则为31日
		// 2.如果date在2月，则为28日
		// 3.如果date在3月，则为31日
		// 4.如果date在4月，则为30日
		// 5.如果date在5月，则为31日
		// 6.如果date在6月，则为30日
		// 7.如果date在7月，则为31日
		// 8.如果date在8月，则为31日
		// 9.如果date在9月，则为30日
		// 10.如果date在10月，则为31日
		// 11.如果date在11月，则为30日
		// 12.如果date在12月，则为31日
		// 1.如果date在闰年的2月，则为29日
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		gc.set(Calendar.HOUR_OF_DAY, 23);//add:wuyub
		gc.set(Calendar.MINUTE, 59);//add:wuyub
		gc.set(Calendar.SECOND, 59);//add:wuyub
		return gc.getTime();
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getLastDayOfMonth(Calendar gc) {
		// 1.如果date在1月，则为31日
		// 2.如果date在2月，则为28日
		// 3.如果date在3月，则为31日
		// 4.如果date在4月，则为30日
		// 5.如果date在5月，则为31日
		// 6.如果date在6月，则为30日
		// 7.如果date在7月，则为31日
		// 8.如果date在8月，则为31日
		// 9.如果date在9月，则为30日
		// 10.如果date在10月，则为31日
		// 11.如果date在11月，则为30日
		// 12.如果date在12月，则为31日
		// 1.如果date在闰年的2月，则为29日
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc;
	}

	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的第一天
	 */
	public static synchronized Date getFirstDayOfMonth(Date date) {
		// 1.设置为1号
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.set(Calendar.HOUR_OF_DAY, 0);//add:wuyub
		gc.set(Calendar.MINUTE, 0);//add:wuyub
		gc.set(Calendar.SECOND, 0);//add:wuyub
		return gc.getTime();
	}
	/**
	 * 获取上月第一天
	 * @param date
	 * @return
	 */
	public static synchronized Date getFirstDayOfLastMonth(Date date) {
		// 1.设置为1号
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -1);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.set(Calendar.HOUR_OF_DAY, 0);//add:wuyub
		gc.set(Calendar.MINUTE, 0);//add:wuyub
		gc.set(Calendar.SECOND, 0);//add:wuyub
		return gc.getTime();
	}
	
	/**
	 * 获取上月最后一天
	 * @param date
	 * @return
	 */
	public static synchronized Date getEndDayOfLastMonth(Date date) {
		// 1.设置为1号
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		gc.set(Calendar.HOUR_OF_DAY, 23);//add:wuyub
		gc.set(Calendar.MINUTE, 59);//add:wuyub
		gc.set(Calendar.SECOND, 59);//add:wuyub
		return gc.getTime();
	}
	
	public static synchronized Date getFirstDayOfYear(Date date) {
		// 1.设置为1号
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.MONTH, 1);
		return getFirstDayOfMonth(gc.getTime());
	}

	/**
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getFirstDayOfMonth(Calendar gc) {
		// 1.设置为1号
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc;
	}

	/**
	 * 获取日期的开始时间 即 时间我 00:00:00
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Date getZoerTime(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}

	/**
	 * 获取日期的开始时间 即 时间我 00:00:00
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getZoerTime(Calendar gc) {
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc;
	}

	/**
	 * 获取日期的开始时间 即 时间我 00:00:00
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Date getEndTime(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 23);
		gc.set(Calendar.MINUTE, 59);
		gc.set(Calendar.SECOND, 59);
		return gc.getTime();
	}

	/**
	 * 获取日期的开始时间 即 时间我 00:00:00
	 * 
	 * @param gc
	 * @return
	 */
	public static synchronized Calendar getEndTime(Calendar gc) {
		gc.set(Calendar.HOUR_OF_DAY, 23);
		gc.set(Calendar.MINUTE, 59);
		gc.set(Calendar.SECOND, 59);
		return gc;
	}

	// ===================================================================== //
	// 其他
	// ===================================================================== //

	/**
	 * 将日期对象转换成为指定ORA日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
	 * 
	 * @param theDate
	 *            将要转换为字符串的日期对象。
	 * @param hasTime
	 *            如果返回的字符串带时间则为true
	 * @return 转换的结果
	 */
	public static synchronized String toOraString(Date theDate, boolean hasTime) {
		// 1.如果有时间，则设置格式为getOraDateTimeFormat()的返回值
		// 2.否则设置格式为getOraDateFormat()的返回值
		// 3.调用toString(Date theDate, DateFormat theDateFormat)
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getOraDateTimeFormat();
		} else {
			theFormat = getOraDateFormat();
		}
		return toString(theDate, theFormat);
	}

	/**
	 * 将日期对象转换成为指定日期、时间格式的字符串形式。如果日期对象为空，返回 一个空字符串对象，而不是一个空对象。
	 * 
	 * @param theDate
	 *            将要转换为字符串的日期对象。
	 * @param hasTime
	 *            如果返回的字符串带时间则为true
	 * @return 转换的结果
	 */
	public static synchronized String toString(Date theDate, boolean hasTime) {
		// 1.如果有时间，则设置格式为getDateTimeFormat的返回值
		// 2.否则设置格式为getDateFormat的返回值
		// 3.调用toString(Date theDate, DateFormat theDateFormat)
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getDateTimeFormat();
		} else {
			theFormat = getDateFormat();
		}
		return toString(theDate, theFormat);
	}

	/**
	 * 创建一个标准日期格式的克隆
	 * 
	 * @return 标准日期格式的克隆
	 */
	public static synchronized DateFormat getDateFormat() {
		// 1.返回DATE_FORMAT
		SimpleDateFormat theDateFormat = (SimpleDateFormat) DATE_FORMAT_SLASH_MM_DD_YYYY
				.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	/**
	 * 创建一个标准时间格式的克隆
	 * 
	 * @return 标准时间格式的克隆
	 */
	public static synchronized DateFormat getDateTimeFormat() {
		// 1.返回DATE_TIME_FORMAT
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_FORMAT_SLASH_MM_DD_YYYY_HH_MM
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	/**
	 * 创建一个标准ORA日期格式的克隆
	 * 
	 * @return 标准ORA日期格式的克隆
	 */
	public static synchronized DateFormat getOraDateFormat() {
		// 1.返回ORA_DATE_FORMAT
		SimpleDateFormat theDateFormat = (SimpleDateFormat) DATE_FORMAT_YYYY_MM_DD
				.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	/**
	 * 创建一个标准ORA时间格式的克隆
	 * 
	 * @return 标准ORA时间格式的克隆
	 */
	public static synchronized DateFormat getOraDateTimeFormat() {
		// 1.返回ORA_DATE_TIME_FORMAT
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_FORMAT_YYYY_MM_DD_HH_MM
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	/**
	 * 创建一个标准ORA时间格式的克隆
	 * 
	 * @return 标准ORA时间格式的克隆
	 */
	public static synchronized DateFormat getOraDateTimeExtendedFormat() {
		// 1.返回ORA_DATE_TIME_EXTENDED_FORMAT
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_FORMAT_YYYY_MM_DD_HH_MM_SS
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	/**
	 * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串，而不是一个空对象。
	 * 
	 * @param theDate
	 *            要转换的日期对象
	 * @param theDateFormat
	 *            返回的日期字符串的格式
	 * @return 转换结果
	 */
	public static synchronized String toString(Date theDate,
			DateFormat theDateFormat) {
		// 1.theDate为空，则返回""
		// 2.否则使用theDateFormat格式化
		if (theDate == null) {
			return "";
		}
		return theDateFormat.format(theDate);
	}
	
	
	public static int getAge(String birthDay) throws Exception { 
    Calendar cal = Calendar.getInstance(); 
    if (cal.before(birthDay)) { 
        throw new IllegalArgumentException( 
            "The birthDay is before Now.It's unbelievable!"); 
    } 
    int yearNow = cal.get(Calendar.YEAR); 
    int monthNow = cal.get(Calendar.MONTH)+1; 
    int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
    cal.setTime(parseDateDayFormat(birthDay)); 
    int yearBirth = cal.get(Calendar.YEAR); 
    int monthBirth = cal.get(Calendar.MONTH); 
    int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
    int age = yearNow - yearBirth; 
    if (monthNow <= monthBirth) { 
        if (monthNow == monthBirth) { 
            if (dayOfMonthNow < dayOfMonthBirth) { 
              age--; 
            } 
        } else { 
          age--; 
        } 
    } 
    return age; 
  }
  private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };  
  private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };  
  public static String getConstellation(int month, int day) {  
     return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];  
  } 

 	public static void main(String[] agrs) {
	    try {
	      Date date = DateUtil.parseDateDayDashFormat("2016-04-22");
	      int month = date.getMonth() + 1;
        int day = date.getDate();
        System.out.println("month="+month+";="+day );
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
	}
}
