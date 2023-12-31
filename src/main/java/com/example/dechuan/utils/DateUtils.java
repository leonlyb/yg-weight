package com.example.dechuan.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author WangLijun
 * @version 1.0
 */
public class DateUtils {
	/**鏃ュ織绫�*/
	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
	/**鏃ユ湡鏍煎紡鍖栫被*/
	protected static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();
	/** FORMAT_DATE_DEFAULT:YYYY-MM-DD eg: 2013-01-11 */
	public static final String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";
	/** FORMAT_DATE_YYYYMMDD:yyyyMMdd eg:20130111 */
	public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
	/** FORMAT_DATE_YYYYMMDDHH:yyyyMMddHH eg:2013011101 */
	public static final String FORMAT_DATE_YYYYMMDDHH = "yyyyMMddHH";
	/** FORMAT_DATE_YYYYMMDDHH:yyyyMMddHH eg:2013011101 */
	public static final String FORMAT_DATE_YYYYMM= "yyyyMM";
	/** FORMAT_DATE_YYYY_MM_DD:yyyy-MM-dd */
	public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	/** FORMAT_DATE_SLASH_YYYY_MM_DD:yyyy/MM/dd */
	public static final String FORMAT_DATE_SLASH_YYYY_MM_DD = "yyyy/MM/dd";
	/** FORMAT_DATE_SLASH_YYYY_M_DD:yyyy/M/dd */
	public static final String FORMAT_DATE_SLASH_YYYY_M_DD = "yyyy/M/dd";
	/** FORMAT_DATETIME_DEFAULT:yyyy-MM-dd HH:mm:ss */
	public static final String FORMAT_DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/** FORMAT_DATETIME_DEFAULT:yyyy-MM-dd HH:mm:ss */
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS = FORMAT_DATETIME_DEFAULT;
	/** FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS_SSS:yyyy-MM-dd HH:mm:ss.SSS */
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	/** FORMAT_DATETIME_YYYY_MM_DD_HHMM:yyyy-MM-dd HHmm */
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";
	/** FORMAT_DATETIME_YYYY_MM_DD_HH_MM:yyyy-MM-dd HH:mm */
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	/** FORMAT_DATETIME_YYYY_MM_DD_HHMMSS:yyyy-MM-dd HHmmss */
	/** FORMAT_DATETIME_YYYYMMDDHHMMSS:yyyyMMddHHmmss */
	public static final String FORMAT_DATETIME_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/** FORMAT_DATETIME_YYYYMMDDHHMMSSSSS:yyyyMMddHHmmssSSS */
	public static final String FORMAT_DATETIME_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	/** FORMAT_TIME_DEFAULT:HH:mm:ss */
	public static final String FORMAT_TIME_DEFAULT = "HH:mm:ss";
	/** FORMAT_TIME_HH_MM:HH:mm */
	public static final String FORMAT_TIME_HH_MM = "HH:mm";
	/** FORMAT_TIME_HH_MM_SS:HHmm */
	public static final String FORMAT_TIME_HHMM = "HHmm";
	/** FORMAT_TIME_HH_MM_SS:HH:mm:ss */
	public static final String FORMAT_TIME_HH_MM_SS = "HH:mm:ss";
	/** FORMAT_TIME_HHMMSS:HHmmss */
	public static final String FORMAT_TIME_HHMMSS = "HHmmss";
	/** FORMAT_TIME_HHMMSS:HHmmssSSS*/
	public static final String FORMAT_TIME_HHMMSS_SSS= "HHmmssSSS";
	/**
	 * 
	 * @param formatPattern
	 * @return
	 */
	protected static DateFormat getCachedDateFormat(String formatPattern) {
		DateFormat dateFormat = dateFormatMap.get(formatPattern);
		if (dateFormat == null) {
			dateFormat = DateUtils.getDateFormat(formatPattern);
			dateFormatMap.put(formatPattern,
					DateUtils.getDateFormat(formatPattern));
		}
		return dateFormat;
	}

	/**
	 * 
	 * @param date
	 * @param formatPattern
	 * @return
	 */
	public static String formatDate(Date date, String formatPattern) {

		log.debug("date:{},formatPattern:{}",date,formatPattern);
 

		return date==null?null:getCachedDateFormat(formatPattern).format(date);
	}

	/***
	 * 
	 * @param formatPattern
	 * @return DateFormat
	 */
	public static DateFormat getDateFormat(String formatPattern) {
		return new SimpleDateFormat(formatPattern);
	}
	
	/***
	 * 
	 * @param formatPattern
	 * @return DateFormat
	 */
	public static Date parser(String dateStr,String formatPattern) {
		try {
			return getDateFormat(formatPattern).parse(dateStr);
		} catch (ParseException e) {
			
		}
		return null;
	}
    //获取时间yyyy-MM-dd HH:mm:ss
    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentDate);
        return dateString;
    }
   //获取时间yyyy-MM-dd
    public static String getDate() {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String format = df.format(day);
        return format;
    }
   //获取昨天
    public static String getyymmdd(){
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(date);
        return format;
    }

    /**
     * 获取现在时间
     * @return返回字符串格式 yyyyMMddHHmmss
     */
    public static String getStringAllDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    public static String getDistanceTime(String inDateTime,String outDateTime) throws ParseException {
        long diff;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sf.parse(inDateTime);
        if(outDateTime == null){
            Date date = new Date();//当前时间
            // 当前时间与其他时间相差的毫秒数
            diff = date.getTime() - time.getTime();
        }else{
            Date date = sf.parse(outDateTime);
            diff = date.getTime() - time.getTime();
        }

        long hours = (diff) / (1000 * 60 * 60);
        long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
        log.info("时间差为:"+hours+"小时"+minutes+"分");
        return hours+"小时"+minutes+"分";
    }

    public static String getAfterDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dd = df.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dd);
            calendar.add(Calendar.MINUTE, -2);//减5分钟
            System.out.println("减2分钟之后：" + df.format(calendar.getTime()));
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //实现日期加一天的方法
    public static String getAddDay(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, 1);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }

}
