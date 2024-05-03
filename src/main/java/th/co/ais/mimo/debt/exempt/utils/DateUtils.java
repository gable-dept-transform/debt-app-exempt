package th.co.ais.mimo.debt.exempt.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtils {

	public static final Locale thLocale = new Locale("th", "TH");
	public static final Locale enLocale = new Locale("en", "US");
	
	public static final String DEFAULT_DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	public static final String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	public static final String DEFAULT_DATE_PATTERN_YYMM = "yyMM";
	public static final String DEFAULT_DATE_PATTERN_YYYYMM = "yyyyMM";
	public static final String DEFAULT_DATE_PATTERN_YYYYMMDD = "yyyyMMdd";
	public static final long MILLISECS_PER_DAY = 86400000;
	private static final Locale DEFAULT_LOCALE = new Locale("th","TH");
	private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT+07:00");
	
    public static Date getCurrentDate()  {
		return new GregorianCalendar(enLocale).getTime();
	}
    
    public static Date getCurrentDateTh()  {
		return new GregorianCalendar(thLocale).getTime();
	}
    
    public static Calendar getCurrentDateTime() {
		return Calendar.getInstance(DEFAULT_TIMEZONE,DEFAULT_LOCALE);
	}
    
    public static String toStringThaiDateTimeSimpleFormat(Date date){
		
		String dateStr = null;
		SimpleDateFormat thFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN, thLocale);
		if (date != null) {
			dateStr = thFormat.format(date);
		}
		return dateStr;
	}
    

	public static String toStringEngDateSimpleFormat(Date date,String pattern){
		
		String dateStr = null;
		SimpleDateFormat engFormat = new SimpleDateFormat(pattern, enLocale);
		if (date != null) {
			dateStr = engFormat.format(date);
		}
		return dateStr;
	}
	
	public static String toStringEngDateSimpleFormat(Date date){
		
		String dateStr = null;
		SimpleDateFormat engFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN, enLocale);
		if (date != null) {
			dateStr = engFormat.format(date);
		}
		return dateStr;
	}
	
	public static Date getDateByFormatEnLocale(String dateStr, String format) {
		Date date = null;
		try {
			if(StringUtils.isNotBlank(dateStr)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format, enLocale);
				date = dateFormat.parse(dateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateByFormatEnLocale(String dateStr) {
		Date date = null;
		try {
			if(StringUtils.isNotBlank(dateStr)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN, enLocale);
				date = dateFormat.parse(dateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date removeTime(Date date) {  
		Date dt = null;
		if(date != null){
			Calendar cal = Calendar.getInstance();  
			cal.setTime(date);  
			cal.set(Calendar.HOUR_OF_DAY, 0);  
			cal.set(Calendar.MINUTE, 0);  
			cal.set(Calendar.SECOND, 0);  
			cal.set(Calendar.MILLISECOND, 0); 
			dt = cal.getTime(); 
		} 
		return dt;
	}

}
