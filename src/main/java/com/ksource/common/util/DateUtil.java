package com.ksource.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * @version $Revision: 1.1 $ $Date: 2007/01/24 02:52:04 $
 */
public class DateUtil {
	// ~ Static fields/initializers
	// =============================================

	private static Logger log = LogManager.getLogger(DateUtil.class);

	private static String defaultDatePattern = null;

	private static String timePattern = "HH:mm";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		// Locale locale = LocaleContextHolder.getLocale();
		// try {
		// defaultDatePattern =
		// ResourceBundle.getBundle(""ApplicationResources";", locale)
		// .getString("date.format");
		// } catch (MissingResourceException mse) {
		defaultDatePattern = "yyyy-MM-dd";
		// }

		return defaultDatePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		if (aMask == null || strDate == null || strDate.equals("")
				|| "".equals(aMask)) {
			return null;
		}
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: ", pe);
			// throw new ParseException(pe.getMessage(), pe.getErrorOffset());
			return date;
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null || aMask == null || "".equals(aMask)) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * @param dMask
	 * @param aDate
	 * @return
	 */
	public static final String convertDateToString(String dMask, Date aDate) {
		return getDateTime(dMask, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;

		if (log.isDebugEnabled()) {
			log.debug("converting date with pattern: " + getDatePattern());
		}

		aDate = convertStringToDate(getDatePattern(), strDate);

		return aDate;
	}

	/**
	 * add 天
	 * @param dt
	 * @param n
	 * @return
	 */
	public static String addDays(Date dt, int n) {
		Date result=addDateDays(dt,n);
		if(result==null){
			return "";
		}else{
			return getDate(result);
		}
	}

	/**
	 * 相差多少小时，未超时预警返回的结果是正数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0L;
		}
		long l =date1.getTime()- date2.getTime() ;
		long d = l / (60 * 60 * 1000) + 1L;
		return d;
		
	}
	
	/**
	 * 相差多少小时，超时预警返回的是负数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDiffTimeOut(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0L;
		}
		long l =date1.getTime()- date2.getTime() ;
		long d = l / (60 * 60 * 1000) - 1L;
		return d;

	}

	/**
	 * 相差多少毫秒
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDiffOfMM(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0L;
		}
		long l =date1.getTime()- date2.getTime();
		return l;

	}
	/**
	 * 日期相加。
	 * 首先此方法会根据日期字符格式把日期字符串解析成年，月，日，时，分，秒，
	 * 之后把它们一个个的添加到addDate中。
	 * 日期格式必须且只能是["yyyy","MM","dd","HH","mm","ss"]中的一个或多个组合，并且不能出现重复。
	 * 比如："MM:dd:HH","yyyy:mm","yyyydd","yyyy-dd"是对的。"MM:dd:KK","MM:dd:MM"是错的。　　
	 * <pre>
	 * 例子：
	 * addDate(new Date(),"00:00:01","MM:dd:HH");
	 * </pre>
	 * @param addDate  被加的日期
	 * @param stringDate　日期字符串
	 * @param pattern　　　日期字符格式
	 * @return
	 */
	public static Date addDate(Date addDate,String stringDate,String pattern){
		//yyyy:MM:dd:HH
		String[] model = new String[]{"yyyy","MM","dd","hh","mm","ss"};
		Map<String, Integer> dateMap = parseDate(stringDate, pattern, model);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(addDate);
		calendar.add(Calendar.YEAR, dateMap.get(model[0]));
		calendar.add(Calendar.MONTH, dateMap.get(model[1]));
		calendar.add(Calendar.DAY_OF_MONTH, dateMap.get(model[2]));
		calendar.add(Calendar.HOUR_OF_DAY, dateMap.get(model[3]));
		calendar.add(Calendar.MINUTE, dateMap.get(model[4]));
		calendar.add(Calendar.SECOND, dateMap.get(model[5]));
		return calendar.getTime();
	}

	//满足首页预警，用特殊格式
	public static Date addDate(Date addDate,String stringDate){
		String[] dates = stringDate.split(":");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(addDate);
		calendar.add(Calendar.MONTH, Integer.parseInt(dates[0]));
		calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[1]));
		calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(dates[2]));
		return calendar.getTime();
	}
	
	
	private static Map<String, Integer> parseDate(String stringDate,
			String pattern, String[] model) {
		Map<String,Integer> dateMap = new HashMap<String,Integer>();
		int defaultVar =0;
		for(int i=0;i<model.length;i++){
			int startIndex =pattern.indexOf(model[i]);
			if(startIndex!=-1){
				defaultVar = Integer.parseInt(stringDate.substring(startIndex,startIndex+model[i].length()));
			}else{
				defaultVar =0;
			}
			dateMap.put(model[i],defaultVar);
		}
		/*byte[] dateByte =stringDate.getBytes();
		byte[] patternByte =stringDate.getBytes();
		byte mark =0;
		for(byte a:dateByte){
			for(byte b:patternByte){
				if(a==b){
					mark = a;
					break;
				}
			}
		}
		char c = (char)mark;
		*/
		return dateMap;
	}
	
	public static Date addDate(Date addDate,Date add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(add);
		//int year = calendar.get(Calendar.YEAR);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month      = calendar.get(Calendar.MONTH)+1;
		int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY);
		int minute     = calendar.get(Calendar.MINUTE);
		int second     = calendar.get(Calendar.SECOND);
		calendar.setTime(addDate);
		//calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.add(Calendar.MINUTE, minute);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}
	/**
	 * add 年
	 * @param dt
	 * @param n
	 * @return
	 */
	public static Date addDateYears(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.YEAR, n);
			Date dt1 = rightNow.getTime();
			return dt1;
		} catch (Exception ex) {
			return null;
		}
	}
	/**
	 * add 月
	 * @param dt
	 * @param n
	 * @return
	 */
	public static Date addDateMonths(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MONTH, n);
			Date dt1 = rightNow.getTime();
			return dt1;
		} catch (Exception ex) {
			return null;
		}
	}
	/**
	 * add 天
	 * @param dt
	 * @param n
	 * @return
	 */
	public static Date addDateDays(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.DATE, n);
			Date dt1 = rightNow.getTime();
			return dt1;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * add 小时
	 * @param dt
	 * @param n
	 * @return
	 */
	public static Date addDateHours(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.HOUR_OF_DAY, n);
			Date dt1 = rightNow.getTime();
			return dt1;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * add分钟
	 * @param dt
	 * @param n
	 * @return
	 */
	public static Date addDateMinutes(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MINUTE, n);
			Date dt1 = rightNow.getTime();
			return dt1;
		} catch (Exception ex) {
			return null;
		}
	}


	/**
	 * 是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		
		
		return c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)
				&& c1.get(Calendar.DAY_OF_YEAR)==c2.get(Calendar.DAY_OF_YEAR);
	}
	/**
	 * 是否是今天
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date){
		return isSameDay(date, new Date());
	}
	
	/**
	 * 匹配日期字符串
	 * 匹配如下格式:
	 * 2011-8-10  2011.8.10  2011/8/10
	 * @param args
	 * @return
	 */
	public static boolean isDateString(Object args) {
		if(!(args instanceof String))return false;
		String str=args.toString();
		StringBuffer regEx=new StringBuffer(); 
		regEx.append("^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$");
		
		Pattern p=Pattern.compile(regEx.toString()); 
		Matcher m=p.matcher(str); 
		return m.find(); 
	}
	
	public static String formateDate(Date date,String pattern){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String dateStr = dateFormat.format(date);
		return dateStr;
	}
	
	
	public static void main(String[] args) {
		long a = 5;
		long b =6;
		System.out.println(a/b);
	}
	
	   /**
     * 相差多少天
     * @param date1
     * @param date2
     * @return
     */
    public static long dayDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }
        long l =date1.getTime()- date2.getTime() ;
        long d = l / (60 * 60 * 1000 * 24) + 1L;
        return d;
    }
	
}
