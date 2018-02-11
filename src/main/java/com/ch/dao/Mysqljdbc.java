package com.ch.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class Mysqljdbc {

	public Connection getConn() throws ClassNotFoundException, SQLException, IOException{
		InputStream in=Mysqljdbc.class.getClassLoader().getResourceAsStream("config.properties");
		Properties prop=new Properties();		
		prop.load(in);
		String url=prop.getProperty("mysqlurl");
		String username=prop.getProperty("mysqlname");
		String password=prop.getProperty("mysqlpassword");
		String driver=prop.getProperty("mysqldriver");
		
		Class.forName(driver);  
//		System.out.println("����:"+url);
		return DriverManager.getConnection(url, username, password); 
	}
	
	public List getlist(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd=rs.getMetaData();
		int len=rsmd.getColumnCount();
		List list=new ArrayList();
		while(rs.next()){
			Map map=new HashMap();
			for(int i=1;i<=len;i++){
				map.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			list.add(map);
		}
		return list;
	}
	
	public String getdatasjson(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd=rs.getMetaData();
		int len=rsmd.getColumnCount();
		
		JSONObject json=new JSONObject();
		int datacount=0;
		json.accumulate("datacount",datacount);
		
		JSONObject columnnames=new JSONObject();
		//ƴװ�ֶ����
		for(int i=1;i<=len;i++){
			if(!"datacount".equals(rsmd.getColumnName(i).toString())){
				json.accumulate("columnnames", rsmd.getColumnName(i).toString());
			}
		}
		
		JSONArray datas=new JSONArray();
		JSONObject data=new JSONObject();
		//ƴװ�ֶ���ƺ�ֵ
		while(rs.next()){
			for(int i=1;i<=len;i++){
				if("datacount".equals(rsmd.getColumnName(i).toString())){
					datacount=Integer.parseInt(rs.getObject(i).toString());
				}else{
					data.put(rsmd.getColumnName(i).toString(), rs.getObject(i).toString());
				}
			}
			datas.add(data);
		}
		json.put("datas", datas);
		json.put("datacount", datacount);
		System.out.println(json.toString());
		
		return json.toString();
	}
}
