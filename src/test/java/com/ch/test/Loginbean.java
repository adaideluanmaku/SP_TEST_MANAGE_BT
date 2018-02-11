package com.ch.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.dao.Mysqljdbc;

@Service
public class Loginbean {
//	@Autowired
//	private static Mysqljdbc jdbc;
	
	public static void main(String args[]) throws InterruptedException, IOException, ParseException{
		Date time = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();   
        cal.setTime(time);   
        cal.add(Calendar.DATE, 1); //天数加减法，例如：1或者-1
        System.out.println(sdf.format(cal.getTime()));
		
        //String 转 date
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
		Date time1 = sdf1.parse("20170930");
		Calendar cal1 = Calendar.getInstance();   
        cal1.setTime(time1);   
        cal1.add(Calendar.DATE, 1); //通过calendar方法计算天数加减法，例如：1或者-1
        System.out.println(sdf1.format(cal1.getTime()));
	}
}
