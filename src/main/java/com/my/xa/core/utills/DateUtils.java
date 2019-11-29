package com.my.xa.core.utills;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.*;

/**
 * 日期操作辅助类
 * 
 * 
 */
public final class DateUtils {
	private DateUtils() {

	}

	static String PATTERN = "yyyy-MM-dd";
	private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

	public static String getDateYDMHMS() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static Long getMillis()
	{
		return Calendar.getInstance().getTimeInMillis();
	}
	
	
	  /*** 
     * 日期月份减一个月 
     *  
     * @param datetime 
     *            日期(2014-11) 
     * @return 2014-10 
     */  
    public static String dateFormat(String datetime) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
        Date date = null;  
        try {  
            date = sdf.parse(datetime);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        Calendar cl = Calendar.getInstance();  
        cl.setTime(date);  
        cl.add(Calendar.MONTH, -1);  
        date = cl.getTime();  
        return sdf.format(date);  
    }  
    
    /*** 
     * 天数相加
     * @param datetime 当前时间
     * @param day 相加的天数
     * @return 
     */  
    public static String addDay(String datetime,int day) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cl = Calendar.getInstance();  
        Date date = null;  
  
        try {  
            date = (Date) sdf.parse(datetime);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        cl.setTime(date);  
        cl.add(Calendar.DAY_OF_MONTH, day);  
        date = cl.getTime();  
        return sdf.format(date);  
    }  
    
    /*** 
     * 天数相加
     * @param datetime 当前时间
     * @param day 相加的天数
     * @return 
     */  
    public static String addDay(Date datetime,int day) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cl = Calendar.getInstance();  
        Date date = datetime;
        cl.setTime(date);  
        cl.add(Calendar.DAY_OF_MONTH, day);  
        date = cl.getTime();  
        return sdf.format(date);  
    }  
    
    
	


	/**
	 * 判断字符串是否是日期
	 * 
	 * @param timeString
	 * @return
	 */
	public static boolean isDate(String timeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			format.parse(timeString);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化时间
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String dateFormat(Date timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(timestamp);
	}

	/**
	 * 获取系统时间Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取系统时间Date
	 * 
	 * @return
	 */
	public static Date getSysDate() {
		return new Date();
	}

	/**
	 * 生成时间随机数
	 * 
	 * @return
	 */
	public static String getDateRandom() {
		String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return s;
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static final String format(Date date) {
		return format(date, PATTERN);
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (pattern == null) {
			return format(date);
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取日期
	 *
	 * @return
	 */
	public static final String getDate() {
		return format(new Date());
	}

	/**
	 * 获取日期时间
	 *
	 * @return
	 */
	public static final String getDateTime() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static final String getDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 获取日期
	 *
	 * @param pattern
	 * @return
	 */
	public static final String getDateTime(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 日期计算
	 *
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static final Date addDate(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 字符串转换为日期:不支持yyM[M]d[d]格式
	 *
	 * @param date
	 * @return
	 */
	public static final Date stringToDate(String date) {
		if (date == null) {
			return null;
		}
		String separator = String.valueOf(date.charAt(4));
		String pattern = "yyyyMMdd";
		if (!separator.matches("\\d*")) {
			pattern = "yyyy" + separator + "MM" + separator + "dd";
			if (date.length() < 10) {
				pattern = "yyyy" + separator + "M" + separator + "d";
			}
		} else if (date.length() < 8) {
			pattern = "yyyyMd";
		} 
		pattern += " HH:mm:ss.SSS";
		pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 字符串转换为日期:YYYY
	 *
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 间隔天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getDayBetween(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		long n = end.getTimeInMillis() - start.getTimeInMillis();
		return (int) (n / (60 * 60 * 24 * 1000L));
	}

	/**
	 * 间隔月
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		return n;
	}

	/**
	 * 间隔月，多一天就多算一个月
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetweenWithDay(Date startDate,
			Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		int day1 = start.get(Calendar.DAY_OF_MONTH);
		int day2 = end.get(Calendar.DAY_OF_MONTH);
		if (day1 <= day2) {
			n++;
		}
		return n;
	}

	public static final Integer getYearBetween(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		Integer result = year2 - year1;
		return result;
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
    /**
     *  获得当前时间
     *  格式为：yyyy-MM-dd HH:mm:ss
    */
    public static String getNowTime() {
        Date nowday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
        String time = sdf.format(nowday);
        return time;
    }

    /**
     * 获取当前系统时间戳
     * @return
     */
    public static Long getNowTimeStamp() {
        return System.currentTimeMillis();
    }

    public static Long getNowDateTime() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 自定义日期格式
     * @param format
     * @return
     */
    public static String getNowTime(String format) {
        Date nowday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 精确到秒
        String time = sdf.format(nowday);
        return time;
    }

    /**
     * 将时间字符转成Unix时间戳
     * @param timeStr
     * @return
     * @throws ParseException
     */
    public static Long getTime(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
        Date date = sdf.parse(timeStr);
        return date.getTime()/1000;
    }

    /**
     * 将Unix时间戳转成时间字符
     * @param timestamp
     * @return
     */
    public static String getTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
        Date date = new Date(timestamp*1000);
        return sdf.format(date);
    }

    /**
     * 获取半年后的时间
     * 时间字符格式为：yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String getHalfYearLaterTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒

        Calendar calendar = Calendar.getInstance();
        int currMonth = calendar.get(Calendar.MONTH) + 1;

        if (currMonth >= 1 && currMonth <= 6) {
            calendar.add(Calendar.MONTH, 6);
        } else {
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, currMonth - 6 - 1);
        }

        return sdf.format(calendar.getTime());
    }
    
	
	/**
	 * 比较两个Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。 
	 * 如果firstDate = secondDate，则返回 0 值； 
	 * 如果此firstDate < secondDate，则返回小于 -1 的值；
	 * 如果此firstDate > secondDate，则返回大于 1 的值
	 * **/
	public static int compare(Date firstDate, Date secondDate) {
		Calendar firstCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		try {
			return firstCalendar.compareTo(secondCalendar);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
     * 获取当前月的上一个月
     * @return Date
     */
	public static Date getUpMonth(Date date){
	   Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.MONTH, -1);
       return cal.getTime();
	}
	
	/**
	 * @Method 修改指定时间的分秒
	 * @param date
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date changeMinute(Date date, int minute,int second){
		 Calendar cal = Calendar.getInstance();
	     cal.setTime(date);
	     cal.add(Calendar.MINUTE,minute);
	     cal.add(Calendar.SECOND,second);
	     return cal.getTime();
	}
	
	// 获取当天的开始时间
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获取当天的结束时间
	public static Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 获取昨天的开始时间
	public static Date getBeginDayOfYesterday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取昨天的结束时间
	public static Date getEndDayOfYesterDay() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取明天的开始时间
	public static Date getBeginDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	// 获取明天的结束时间
	public static Date getEndDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	// 获取本周的开始时间
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	// 获取本周的结束时间
	public static Date getEndDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	// 获取本年的开始时间
	public static Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		// cal.set
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return getDayStartTime(cal.getTime());
	}

	// 获取本年的结束时间
	public static Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}

	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
		{ 
			calendar.setTime(d);
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
		{
			calendar.setTime(d);
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}
     
	// 获取今年是哪一年
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}
	
	public static String formatSecond(Date before, Date after){
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return formatSecond(afterTime - beforeTime);
	}
	
	public static String formatSecond(long ms){
		int ss = 1000; //加上毫秒的
		//int ss = 1; //秒
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		String result = "";
		if(day > 0){
			result+=strDay + "天";
		}
		if(!"".equals(result)){
			result+=strHour + "小时";
		}else if(hour > 0){
			result+=strHour + "小时";
		}
		if(!"".equals(result)){
			result+=strMinute + "分";
		}else if(minute > 0){
			result+=strMinute + "分";
		}
		if(!"".equals(result)){
			result+=strSecond + "秒";
		}else if(second > 0){
			result+=strSecond + "秒";
		}
		if(!"".equals(result)){
			result+=strMilliSecond + "毫秒";
		}else if(milliSecond > 0){
			result+=strMilliSecond + "毫秒";
		}
		//return strDay + "天" + strHour + "小时" + strMinute + "分" + strSecond + "秒" + strMilliSecond + "毫秒"; //加上毫秒的
		//return strDay + "天" + strHour + "小时" + strMinute + "分" + strSecond + "秒";
		return result;
	}
	
	/**
	 * 当前时间分钟加减
	 * @param i 提前分钟；为负数
	 * @return
	 * @throws Exception
	 */
	public static String addOrMinusSecond(int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date();
		cal.setTime(date);
		cal.add(12, i);
		rtn = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rtn);
	}
	
	/**
	 * 时间格式转时间格式
	 * @throws ParseException 
	 */
	public static String strTostr(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(sdf.parse(time));
		return dateString;
	
	}

	public static DateFormat getFormat(String pattern) {
		DateFormat format = DFS.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			DFS.put(pattern, format);
		}
		return format;
	}

	public static Date parse(String source, String pattern) {
		if (source == null) {
			return null;
		}
		Date date;
		try {
			DateFormat format = getFormat(pattern);
			date = format.parse(source);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

    /**
     * 将Unix时间戳转成时间字符
     * @param timestamp
     * @return
     */
    public static String longTOStr(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);// 精确到秒
        Date date = new Date(timestamp*1000);
        return sdf.format(date);
    }



	
}
