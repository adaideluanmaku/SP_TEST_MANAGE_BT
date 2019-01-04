package com.ch.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.ch.pahis.Sys_pa;
import com.ch.sys.StrLevenshtein;

public class Test {
	
	public static void main(String args[]){
		AgeDaysUtil ageDaysUtil=new AgeDaysUtil();
//		for(int i=0;i<10;i++){
//			System.out.println(ageDaysUtil.getDateTimeFormat("1900-01-01 00:00:00",0));
//			
//		}
		System.out.println(ageDaysUtil.getDateTimeFormat("1900:01:01 00:00:00",0));
	}
	
}

