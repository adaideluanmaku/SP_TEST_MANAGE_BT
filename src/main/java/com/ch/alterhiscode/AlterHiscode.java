package com.ch.alterhiscode;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AlterHiscode {
	public String alterhis(String jsonstr) throws IOException{
		int on=0;//替换HISCODE，开关,1开0关
		
		if(on==1){
			//根据配置来修改hiscode
			InputStream in=AlterHiscode.class.getClassLoader().getResourceAsStream("config.properties");
			Properties prop=new Properties();
			prop.load(in);
			
			JSONObject json=JSONObject.fromObject(jsonstr);
			JSONObject PassClient=json.getJSONObject("PassClient");
			PassClient.put("HospID", prop.getProperty("hiscode"));
			json.put("PassClient", PassClient);
			jsonstr=json.toString();
		}
		return jsonstr;
	}
}
