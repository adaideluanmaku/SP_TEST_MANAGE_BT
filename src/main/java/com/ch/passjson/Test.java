package com.ch.passjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.dom4j.DocumentException;


public class Test {
	public static void main(String args[]) throws ClassNotFoundException, SQLException, TimeoutException, DocumentException, IOException, NumberFormatException, IllegalArgumentException, IllegalAccessException, ParseException{
//		InputStream in=Passscreen.class.getClassLoader().getResourceAsStream("config.properties");
//		Properties prop=new Properties();
//		prop.load(in);
//		prop.setProperty("hiscode", "123");
//		System.out.println(prop.get("hiscode"));
		
//		Redis_keys Redis_keys=new Redis_keys();
//		Redis_keys.setAnliname("�ֶ�����-��Χ020");
//		Redis_keys.setTablename("mc_config");
//		Redis_keys.setVersion("1609");
//		Redis_keys.shoudongchaxun();
		
//		Queryresule Queryresule=new Queryresule();
//		String a=Queryresule.Xmltojson("{\"Delimiter\":\"<?|^&>\",\"SubDelimiter\":\"<|>\",\"Slcode\":0,\"DisplayMode\":1,\"ReferenceType\":51,\"ReferenceTip\":\"\",\"ResultMode\":\"PASS\",\"Title\":\"\",\"ReferenceResults\":[{\"Slcode\":0,\"BriefItems\":\"ҩƷ������Ϣ<|>��<b>�˷ܼ��ɷ�</b>��<br/>&nbsp;&nbsp;&nbsp;&nbsp;����ͼ����ʾ<br/>��<b>�߾�ʾҩƷ</b>��<br/>&nbsp;&nbsp;&nbsp;&nbsp;A��-ʹ��Ƶ�ʸߣ�һ����ҩ���󣬻�������������ߵĸ�ΣҩƷ��Ӧ�ص����ͼ໤��\",\"OtherItems\":\"\",\"DetailItems\":\"basedrug<|>����ҩ��<?|^&>social<|>����<?|^&>otc_b<|>OTC����<?|^&>radioactivity<|>������ҩƷ<?|^&>stimulant<|>���˷ܼ�ҩƷ<?|^&>highalert<|>�߾�ʾҩƷ<?|^&>skintest<|>��Ƥ��\"}]}");
//		System.out.println(a);
		
//		Sa_gather_json Sa_gather_json=new Sa_gather_json();
//		Sa_gather_json.setId(7679);
//		Sa_gather_json.setAnlitype(7);
//		Sa_gather_json.setAnliname("123");
//		Sa_gather_json.setVersion("1609");
//		Sa_gather_json.setAnlistatus(1);
//		Sa_gather_json.getUpdatelog();
		
		
//		Json_err Json_err=new Json_err();
//		Json_err.setAnlitype(8);
//		Json_err.setVersion("1609");
//		Json_err.Jsonscreenall();
		
		final int sum_date=10;//表示N天，每分割一次，起始日期增加一天，不设置0，N表示循环N次分割
		final int count=1*sum_date;//一天数据量，一批案例的循环次数,数据集22条
		final int countzy=1*sum_date;//一天数据量，一批案例的循环次数,数据集22条
		final int countcy=1*sum_date;//一天数据量，一批案例的循环次数,数据集22条
		
		String hiscode="HISCODE001";//其实是用户HISCODE，就是hiscode_user 1609PA HISCODE001
		final String ienddate="20170907";//设置时间起始点
		final String enddate="2017-09-07";
		final String costtime="2017-09-07 01:01:01";
		
		final String startdate="2017-09-07 01:01:01";//住院开始时间
		
		String ienddate1=ienddate;
		String startdate1=startdate;
		SimpleDateFormat sdf=null;
		Date time=null;
		Calendar cal=null;
		for(int i=0;i<count;i++){
			//数据分割，增加时间
			if(i%(count/sum_date)==0 && i>0){
		        sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				time = sdf.parse(startdate);
				cal = Calendar.getInstance();   
		        cal.setTime(time);   
		        cal.add(Calendar.DATE, i/sum_date-new Random().nextInt(10)); //通过calendar方法计算天数加减法，例如：1或者-1
		        startdate1=sdf.format(cal.getTime());
		        System.out.println(startdate1);
			}
		}
	}
}
