package com.my.xa.core.utills;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	public static final String AMR_NOMAL_TIME_FORMAT = "HH:mm:ss";
	public static final String AMR_NOMAL_DATE_FORMAT = "yyyy/MM/dd";
	public static final String AMR_NOMAL_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String AMR_NOMAL_FULLDATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
	public static final String AMR_NOMAL_FULLTIME_FORMAT = "HH:mm:ss.SSS";
	public static final String TERM_UNIT_DAY = "D";
	public static final String TERM_UNIT_MONTH = "M";
	public static final String TERM_UNIT_YEAR = "Y";

	public static Date getDate(String curDate, String dateFormat) throws ParseException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		cal.setTime(formatter.parse(curDate));
		return cal.getTime();
	}

	public static Calendar getCalendar(String curDate, String dateFormat) throws ParseException {
		Calendar cal = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		cal.setTime(formatter.parse(curDate));
		cal.add(calendar.get(Calendar.MINUTE),0);
		cal.add(calendar.get(Calendar.HOUR_OF_DAY),0);
		cal.add(calendar.get(Calendar.SECOND),0);
		return cal;
	}

	public static String  getDateToString(Date curDate, String dateFormat) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(curDate);
	}
	
	public static String getWeekDay(String date, String format) throws ParseException {
		String[] sWeekDates = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		String[] sWeekDatesE = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		cal.setTime(formatter.parse(date));
		if (format.equals("2"))
			return sWeekDates[(cal.get(7) - 1)];
		if (format.equals("3")) {
			return sWeekDatesE[(cal.get(7) - 1)];
		}
		return String.valueOf(cal.get(7) - 1);
	}

	public static String getCurrTime(String dateFormat ) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(new  Date());
	}
	
	public static int getDays(String sBeginDate, String sEndDate,String dateFormat) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date startDate = formatter.parse(sBeginDate);
		Date endDate = formatter.parse(sEndDate);
		int iDays = (int) ((endDate.getTime() - startDate.getTime()) / 86400000L);
		return iDays;
	}

	public static double getMonths(String beginDate1, String endDate1, String dateFormat) throws ParseException {
		Date beginDate = getDate(beginDate1, dateFormat);
		Date endDate = getDate(endDate1, dateFormat);
		Calendar former = Calendar.getInstance();
		Calendar latter = Calendar.getInstance();
		former.setTime(beginDate);
		latter.setTime(endDate);
		int monthCounter = (latter.get(1) - former.get(1)) * 12 + latter.get(2) - former.get(2);
		former.add(2, monthCounter);
		int dayCounter = latter.get(5) - former.get(5);
		int maxDays = latter.getActualMaximum(5);
		return (monthCounter * 1.0D + dayCounter * 1.0D / maxDays * 1.0D);
	}

	public static int getYears(String beginDate1, String endDate1, String dateFormat) throws ParseException {
		Date beginDate = getDate(beginDate1, dateFormat);
		Date endDate = getDate(endDate1, dateFormat);
		Calendar former = Calendar.getInstance();
		Calendar latter = Calendar.getInstance();
		former.clear();
		latter.clear();
		boolean positive = true;
		if (beginDate.after(endDate)) {
			former.setTime(endDate);
			latter.setTime(beginDate);
			positive = false;
		} else {
			former.setTime(beginDate);
			latter.setTime(endDate);
		}

		int yearCounter = 0;
		while (former.get(1) != latter.get(1)) {
			former.add(1, 1);
			++yearCounter;
		}

		if (positive) {
			return yearCounter;
		}
		return (-yearCounter);
	}

	public static boolean isLeapYear(int year) {
		return ((((year % 4 != 0) || (year % 100 == 0))) && (year % 400 != 0));
	}

	public static boolean isLeapYear(String date) throws Exception {
		return isLeapYear(Integer.parseInt(date.split("/")[0]));
	}

	 
	public static String getDiffTime(String BeginTime, String EndTime,String dateFormat) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		if ((BeginTime == null) || (EndTime == null) || ("".equals(BeginTime)) || ("".equals(EndTime)))
			return "";
		Date begin = formatter.parse(BeginTime);
		Date end = formatter.parse(EndTime);
		long minutediff = (int) ((end.getTime() - begin.getTime()) / 60000.0D);
		long seconddiff = (int) ((end.getTime() - begin.getTime()) % 60000.0D) / 1000;
		String Return = minutediff + "分" + seconddiff + "秒";
		return Return;
	}

	public static boolean monthEnd(String date,String dateFormat) throws ParseException {
		return date.equals(getEndDateOfMonth(date,dateFormat));
	}

	public static String getEndDateOfMonth(String curDate,String dateFormat ) throws ParseException {
		if ((curDate == null) || (curDate.length() != 10)) {
			return null;
		}
		curDate = curDate.substring(0, 8) + "01";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		cal.setTime(formatter.parse(curDate));
		int maxDays = cal.getActualMaximum(5);
		cal.set(5, maxDays);
		return formatter.format(cal.getTime());
	}

	public static String getRelativeDate(String baseDate, String date, String termUnit, int step,String dateFormat)
			throws Exception
	{
		return getRelativeDate(baseDate, false, date, termUnit, step,dateFormat);
	}
	
	public static String getRelativeDate(String date, String termUnit, int term,String dateFormat)
			throws Exception
	{
		return getRelativeDate(date, date, termUnit, term,dateFormat);
	}
	
	public static String getRelativeDate(String date, String termUnit, String term,String dateFormat)
			throws Exception
	{
		return getRelativeDate(date, date, termUnit, Integer.parseInt(term),dateFormat);
	}
	
	public static String getRelativeDate(String baseDate, boolean endOfMonth, String date, String termUnit, int term,String dateFormat)
			throws Exception {
		if (term == 0)
			return date;
		if (!"D".equals(termUnit) && !"M".equals(termUnit) && !"Y".equals(termUnit))
			throw new Exception("[" + termUnit + "]不合法");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		cal.setTime(formatter.parse(date));
		if (termUnit.equals("D"))
			cal.add(5, term);
		else if (termUnit.equals("M")) {
			cal.add(2, term);
			if (monthEnd(baseDate,dateFormat) && endOfMonth) {
				String s = formatter.format(cal.getTime());
				return getEndDateOfMonth(s,dateFormat);
			}
		} else if (termUnit.equals("Y"))
			cal.add(1, term);
		return formatter.format(cal.getTime());
	}
}
