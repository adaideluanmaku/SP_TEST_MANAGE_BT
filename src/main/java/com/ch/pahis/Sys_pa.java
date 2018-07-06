package com.ch.pahis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Sys_pa {
	SimpleDateFormat sdf=null;
	Date time=null;
	Calendar cal=null;
	//yyyyMMdd
	//yyyy-MM-dd HH:mm:ss
	//yyyy-MM-dd
	public String date1(String date1,String yyyy){
		String date2="";
		String date2_1="";
		String yyyy1="yyyyMMdd";
		int datelength=date1.length();
		if(date1.length()==19){
			date2=date1.substring(0,4)+date1.substring(5,7)+date1.substring(8,10);
			date2_1=date1.substring(11,19);
		}else if(date1.length()==10){
			date2=date1.substring(0,4)+date1.substring(5,7)+date1.substring(8,10);
		}else{
			date2=date1;
		}
		
		String datestr=date2;
		sdf=new SimpleDateFormat(yyyy1);
		try {
			time = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal = Calendar.getInstance();   
        cal.setTime(time);   
        cal.add(Calendar.DATE, 1); //通过calendar方法计算天数加减法，例如：1或者-1
        datestr=sdf.format(cal.getTime());
        
        if(yyyy.length()==19){
        	if(StringUtils.isBlank(date2_1)){
        		date2=datestr.substring(0,4)+"-"+datestr.substring(4,6)+"-"+datestr.substring(6,8)+" 00:00:00";
        	}else{
        		date2=datestr.substring(0,4)+"-"+datestr.substring(4,6)+"-"+datestr.substring(6,8)+" "+date2_1;
        	}
        	
        }else if(yyyy.length()==10){
        	date2=datestr.substring(0,4)+"-"+datestr.substring(4,6)+"-"+datestr.substring(6,8);
        }else{
        	date2=datestr;
        }   
        
        return date2;
	}
	
	//yyyyMMdd
	public String date2(String date1,String yyyy,int a,int b){
		String datestr=date1;
		sdf=new SimpleDateFormat(yyyy);
		try {
			time = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal = Calendar.getInstance();   
        cal.setTime(time);   
//        cal.add(Calendar.DATE, a/b-new Random().nextInt(10)); //通过calendar方法计算天数加减法，例如：1或者-1
        cal.add(Calendar.DATE, a/b-5);
        datestr=sdf.format(cal.getTime());
        
        return datestr;
	}
	
	//yyyyMMdd 改变某个时间
	public String date3(String date1,String yyyy,int days){
		String datestr=date1;
		sdf=new SimpleDateFormat(yyyy);
		try {
			time = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal = Calendar.getInstance();   
        cal.setTime(time);   
//        cal.add(Calendar.DATE, -days+new Random().nextInt(5)); //通过calendar方法计算天数加减法，例如：1或者-1,取一个时间范围
        cal.add(Calendar.DATE, -days);
        datestr=sdf.format(cal.getTime());
        
        return datestr;
	}
	
	//yyyyMMdd 改变某个时间，取时间范围
	public String date4(String date1,String yyyy,int days){
		String datestr=date1;
		sdf=new SimpleDateFormat(yyyy);
		try {
			time = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal = Calendar.getInstance();   
        cal.setTime(time);   
        cal.add(Calendar.DATE, days); //通过calendar方法计算天数加减法，例如：1或者-1,取一个时间范围
        datestr=sdf.format(cal.getTime());
        
        return datestr;
	}
	
	/**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
	public int differentDaysByMillisecond(Date date1, Date date2 , String sdf) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	//"yyyy-MM-dd HH:mm:ss"
	public int differentDaysByMillisecond(String date1, String date2, String sdfor) {
		SimpleDateFormat sdf=null;
		Date time1=null;
		Date time2=null;
		
		sdf=new SimpleDateFormat(sdfor);
		try {
			time1 = sdf.parse(date1);
			time2 = sdf.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int days = (int) ((time1.getTime() - time2.getTime()) / (1000 * 3600 * 24));
		return days;
	}
}
