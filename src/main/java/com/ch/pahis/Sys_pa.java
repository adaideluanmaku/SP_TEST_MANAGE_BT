package com.ch.pahis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
        cal.add(Calendar.DATE, 1); //通过calendar方法计算天数加减法，例如：1或者-1
        datestr=sdf.format(cal.getTime());
        
        return datestr;
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
        cal.add(Calendar.DATE, a/b-new Random().nextInt(10)); //通过calendar方法计算天数加减法，例如：1或者-1
        datestr=sdf.format(cal.getTime());
        
        return datestr;
	}
}
