package com.ch.test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <ul>
 * <li>项目名称：pass_core </li>
 * <li>类名称：AgeDaysUtil </li>
 * <li>类描述：特殊人群年龄比较 </li>
 * <li>创建人：张正通 </li>
 * <li>创建时间：2016年3月14日 </li>
 * <li>修改备注：</li>
 * </ul>
 */
public class AgeDaysUtil {
	private static Logger logger = LoggerFactory.getLogger(AgeDaysUtil.class);
	/**
	 * <ul>
	 * <li>方法名：  isAgeDays </li>
	 * <li>功能描述：年龄匹配方法 </li>
	 * <li>创建人：  张正通 </li>
	 * <li>创建时间：2016年3月28日 </li>
	 * </ul> 
	 * @param agelow		年龄低值
	 * @param agelow_unit	年龄低值单位(岁、月、天)
	 * @param birthday		出生日期
	 * @param agehigh		年龄高值
	 * @param agehigh_unit	年龄高值单位(岁、月、天)
	 * @param useTime		审查时间
	 * @param unequal_low	是否不等于低值（0-等于，1-不等于）
	 * @param unequal_high	是否不等于高值（0-等于，1-不等于）
	 * @param confsec		小儿=12,儿童=16,老人=60
	 * @return
	 */
	public static boolean isAgeDays(String agelow,String agelow_unit,String birthday,
			String agehigh,String agehigh_unit,String useTime,
			int unequal_low,int unequal_high,String confsec){
		DateTime dateTime1 = null;
		if(StringUtils.isNumeric(agelow)){
			dateTime1 = getDateTime(birthday,agelow_unit,Integer.valueOf(agelow));
		}else{
			dateTime1 = getDateTimeDes(birthday,agelow,confsec);
		}
		
		DateTime dateTime2 = null;
		if(StringUtils.isNumeric(agehigh)){
			dateTime2 = getDateTime(birthday,agehigh_unit,Integer.valueOf(agehigh));
		}else{
			dateTime2 = getDateTimeDes(birthday,agehigh,confsec);
		}
		return compDateTime(useTime,dateTime1,dateTime2,unequal_low,unequal_high);
	}
	
	
	
	public static boolean compDateTime(String useTime,DateTime dateTime1,DateTime dateTime2,int unequal_low,int unequal_high){
		boolean isTrue = true;
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		DateTime dateTime = new DateTime(useTime);
		int comp = comparator.compare(dateTime, dateTime1);
		if(unequal_low == 0 && comp < 0){
			return false;
		}else if(unequal_low == 1 && comp <= 0){
			return false;
		}
		comp = comparator.compare(dateTime2, dateTime);
		if(unequal_high == 0 && comp < 0){
			return false;
		}else if(unequal_high == 1 && comp <= 0){
			return false;
		}
		return isTrue;
	}
	
	public static DateTime getDateTimeDes(String birthday,String agedes,String confsec){
		Map<String,Integer> conf = defConf(confsec);
		DateTime dateTime = new DateTime(birthday);
		if("小儿".equals(agedes)){
			dateTime = dateTime.plusYears(conf.get("小儿")).minusDays(1);
		}else if("儿童".equals(agedes)){
			dateTime = dateTime.plusYears(conf.get("儿童")).minusDays(1);
		}else if("老人".equals(agedes)){
			dateTime = dateTime.plusYears(conf.get("老人")).minusDays(1);
		}
		return dateTime;
	}
	
	public static DateTime getDateTime(String birthday,String unit,Integer num){
		DateTime dateTime = null;
		try {
			dateTime = new DateTime(birthday).minusDays(1);
			if("岁".equals(unit)){
				dateTime = dateTime.plusYears(num);
			}else if("月".equals(unit)){
				dateTime = dateTime.plusMonths(num);
			}else if("周".equals(unit)){
				dateTime = dateTime.plusWeeks(num);
			}else if("天".equals(unit)){
				dateTime = dateTime.plusDays(num);
			}
		} catch (Exception e) {
		}
		return dateTime;
	}
	
	
	public static Long getDataTimemsec(String requesttime,String endTime){
		DateTime dateTime1 = null;
		DateTime dateTime2 = null;
		Long timemisc = 0L;
		try {
			dateTime1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss SSS").parseDateTime(requesttime);  
			dateTime2 =DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss SSS").parseDateTime(endTime);  
			timemisc = dateTime2.getMillis() - dateTime1.getMillis();
			return timemisc;
		}catch(Exception e){
			return 0L;
		}
	}
	
	public static int getDataTimeminOrDay(String requesttime,String endTime){
		DateTime dateTime1 = null;
		DateTime dateTime2 = null;
		int timemisc = 0;
		try {
			dateTime1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(requesttime);  
			dateTime2 =DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(endTime);  
			timemisc = dateTime2.getMillisOfDay() - dateTime1.getMillisOfDay();
			return timemisc;
		}catch(Exception e){
			return 0;
		}
	}
	
	
	
	
	public static Map<String,Integer> defConf(String conf){
		Map<String,Integer> map = Maps.newHashMap();
		String[] sec = StringUtils.split(conf,",");
		for(int i = 0;i < sec.length;i++){
			String[] s = StringUtils.split(sec[i],"=");
			map.put(s[0], Integer.valueOf(s[1]));
		}
		return map;
	}
	
	
	/**
	 * 
	 * <ul>
	 * <li>方法名：  getAgeFromBirth </li>
	 * <li>功能描述：根据生日获取到年龄 </li>
	 * <li>创建人：  周应强 </li>
	 * <li>创建时间：2016年3月31日 </li>
	 * </ul> 
	 * @param birth
	 * @return
	 */
	public static int getAgeFromBirth(String birth) {
	    if (birth == null || "".equals(birth)) {
	        return 0;
	    }
	    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
	    //时间解析
	    LocalDate birthday = DateTime.parse(birth, format).toLocalDate();
	    LocalDate now = new LocalDate();
	    Period period = new Period(birthday, now, PeriodType.yearMonthDay());
	    return period.getYears();
	}
	
	/**
	 * <ul>
	 * <li>方法名：  getDateTimeFormat </li>
	 * <li>功能描述：获得时间字符串中的日期,暂时这样吧，后续需要再修改 </li>
	 * <li>创建人：  张正通 </li>
	 * <li>创建时间：2016年4月6日 </li>
	 * </ul> 
	 * @param datatimestr	时间/日期字符串
	 * @param dtype			0-日期+时间 1-日期 2-时间
	 * @return
	 */
	private static DateTimeFormatter format =DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC();  
	
//	 public static java.time.LocalDate parseLocalDate(String dateStr, String pattern) {
//	      return java.time.LocalDate.parse(dateStr,  java.time.format.DateTimeFormatter.ofPattern(pattern));
//	 }
	
	
	public static String getDateTimeFormat(String datatimestr,int dtype){
		String dateTimeStr = "";
		DateTime dateTime = null;
		try {
			if(StringUtils.isBlank(datatimestr))return "";
			if("00-00-00".equals(datatimestr))return "";
			if("00-00-00 00:00:00".equals(datatimestr))return "";
			if(datatimestr.indexOf("-  -") >= 0)return "";
			if(datatimestr.indexOf("- -") >= 0)return "";
			if(Pattern.compile("(?i)[a-z]").matcher(datatimestr).find())return "";
			while(datatimestr.indexOf("- ") > -1){
				datatimestr = datatimestr.replace("- ", "-");
			}
			while(datatimestr.indexOf(" -") > -1){
				datatimestr = datatimestr.replace(" -", "-");
			}
			while(datatimestr.indexOf(": ") > -1){
				datatimestr = datatimestr.replace(": ", ":");
			}
			while(datatimestr.indexOf(" :") > -1){
				datatimestr = datatimestr.replace(" :", ":");
			}
			try {
				if(datatimestr.indexOf(":") > 0){
					dateTime = DateTime.parse(datatimestr,format);
					//dateTime = format.parseDateTime(datatimestr);
				}else{
					//dateTime = new DateTime(datatimestr);
					dateTime = format.parseDateTime(datatimestr);
				}
			} catch (Exception e) {
				if(datatimestr.length()==10) {
					dateTime = new DateTime(datatimestr);
				}else {
					dateTime = new DateTime(datatimestr.split(" ")[0]);
				}
				
			}
			
			if(null == dateTime){
				String dateStyle = getDateStyle(datatimestr);
				dateTime = DateTime.parse(datatimestr, DateTimeFormat.forPattern(dateStyle));
			}
			if(dtype == 1){
				dateTimeStr = dateTime.toString("yyyy-MM-dd");
			}else if(dtype == 2){
				dateTimeStr = dateTime.toString("HH:mm:ss");
				if("00:00:00".equals(dateTimeStr)){
					dateTimeStr = "";
				}
			}else{
				dateTimeStr = dateTime.toString("yyyy-MM-dd HH:mm:ss");
				if(dateTimeStr.indexOf("00:00:00") >= 0){
					dateTimeStr = dateTime.toString("yyyy-MM-dd");
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("时间判断错误，可能是没有相关时间日期格式：",e);
		}
		return dateTimeStr;
	}
	
	public static DateTime dateFormat(String date){
		DateTime dateTime = null;
		try {
			date = StringUtils.trim(date);
			String dateStyle = getDateStyle(date);
			if(StringUtils.isNoneBlank(dateStyle)){
				DateTime datetime = DateTime.parse(date, DateTimeFormat.forPattern(dateStyle));
				dateTime = new DateTime(datetime.toString("yyyy-MM-dd"));
			}
		} catch (Exception e) {
			logger.error("日期转换为年月日错误：",e);
		}
		return dateTime;
	}
	
	public static DateTime dateFormat2(String date){
		DateTime dateTime = null;
		try {
			date = StringUtils.trim(date);
			String dateStyle = getDateStyle(date);
			if(StringUtils.isNoneBlank(dateStyle)){
				dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateStyle));
			}
		} catch (Exception e) {
			logger.error("日期转换为年月日错误：",e);
		}
		return dateTime;
	}
	
	public static String getDateStyle(String date) {
		String dataStyle = "";
		date = StringUtils.trim(date);
		for (DateStyle style : DateStyle.values()) {
			ParsePosition pos = new ParsePosition(0);
			Date dateTmp = getDateFormat(style.getValue()).parse(date,pos);
			if(null != dateTmp){
				dataStyle = style.getValue();
				break;
			}
		}
		return dataStyle;
	}
	
	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
	private static final Object object = new Object();
	private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}
	/**
	 * <ul>
	 * <li>方法名：  daysDiff </li>
	 * <li>功能描述： 计算时间差　，　例如:startdate  2012-11-11 enddate 2012-11-12 结果是正数1天 ，反之是-1</li>
	 * <li>创建人：  张正通 </li>
	 * <li>创建时间：2016年4月18日 </li>
	 * </ul> 
	 * @param nowdate	开始时间
	 * @param thendate	结束时间
	 * @return	Integer 
	 * @deprecated 使用这个方法请注意,这个和c++那边不一样,正反相反,而且天数差1天,所以请小心使用
	 * 如果要使用C++方法,请自己重新写一个方法,deprecated by jy
	 */
	public static Integer daysDiff(String stardate,String enddate){
		Integer diff = 0;
		try {
			if(StringUtils.isBlank(stardate)||StringUtils.isBlank(enddate)) {
				logger.error("计算时间差错误：为空值 stardate->{},enddate->{} ",stardate,enddate);
				return diff;
			}
			DateTime d1 = dateFormat(stardate);
			DateTime d2 = dateFormat(enddate);
			diff = Days.daysBetween(d1,d2).getDays();
			return diff;
		} catch (Exception e) {
			logger.error("计算时间差错误：",e);
		}
		
		return diff;
	}
	
	
	
	/**
	 * 
	 * <ul>
	 * <li>方法名：  daysDiff </li>
	 * <li>功能描述： 获取天数的时间差</li>
	 * <li>创建人：  周应强 </li>
	 * <li>创建时间：2016年8月23日 </li>
	 * </ul> 
	 * @param stardate
	 * @param enddate
	 * @param pPlusValue
	 * @return
	 */
	public static Integer daysDiff(String stardate,String enddate,int pPlusValue){
		Integer diff = 0;
	    try{
	    	diff = daysDiff(stardate,enddate);
	        if (diff < 0){
	        	diff = diff - pPlusValue;
	        }else{
	        	diff = diff + pPlusValue; // 对于相等的两个日期，取其天数差为1天
	        }
	    }
	    catch(Exception e) {
			logger.error("计算时间差错误：",e);
		}
	    return diff;
	}
	
	/**
	 * 
	 * <ul>
	 * <li>方法名：  getBetweenDates </li>
	 * <li>功能描述：获取两个日期之间的日期(特别注意,不包括,开始和结束日期,请自己补上) </li>
	 * <li>创建人：  jy </li>
	 * <li>创建时间：2017年10月31日 </li>
	 * </ul> 
	 * @param minDate
	 * @param maxDate
	 * @param shareDBDateType
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenDates(String minDate, String maxDate, DateStyle dateStyle) {
		List<String> result = Lists.newArrayList();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateStyle.getValue());// 格式化为年月
			//
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(sdf.parse(minDate));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(sdf.parse(maxDate));

			while (tempStart.before(tempEnd)) {
				result.add(sdf.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (Exception ex) {
			logger.error("获取两个日期之间的日期  错误", ex);
		}
		return result;
	}

	/**
	 * 
	 * <ul>
	 * <li>方法名：  getBetweenDates </li>
	 * <li>功能描述：获取两个日期之间的日期 </li>
	 * <li>创建人：  jy </li>
	 * <li>创建时间：2017年10月31日 </li>
	 * </ul> 
	 * @param minDate
	 * @param maxDate
	 * @param shareDBDateType
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenDates(String minDate, String maxDate, DateStyle dateStyle, boolean contains_beginAndEnd) {
		List<String> result = Lists.newArrayList();
		try {
			if (StringUtils.isBlank(minDate) || StringUtils.isBlank(maxDate)) {
				return result;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(dateStyle.getValue());// 格式化为年月

			//开始时间大于结束时间,退出
			Date minDate_ = sdf.parse(minDate);
			Date maxDate_ = sdf.parse(maxDate);
			if (minDate_.compareTo(maxDate_) > 0) {
				result.clear();
				return result;
			}
			
			if (contains_beginAndEnd) {
				minDate = sdf.format(minDate_);
				result.add(minDate);
			}
			
			maxDate = sdf.format(maxDate_);
			
			
			result.addAll(getBetweenDates(minDate, maxDate, dateStyle));

			if (contains_beginAndEnd && !StringUtils.equals(minDate, maxDate)) {
				result.add(maxDate);
			}
		} catch (Exception ex) {
			logger.error("获取两个日期之间的日期  错误", ex);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		//String a = getDateTimeFormat("1991-04-13 11:12:13", 1);
//		String a = getDateTimeFormat("2016-06-31",0);
//		System.out.println(a);
		 List<String> a = getBetweenDates("2017-01-32 10:10:11", "2017-02-01 10:10:11", DateStyle.YYYY_MM_DD, true);
		 for (String string : a) {
			System.out.println(string);
		}
	}
	
	
	
	
}
