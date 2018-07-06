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

public class Sqlserverconn {
	static private int len;
	public int getLen(){
		return len;
	}
	public Connection getConn() throws ClassNotFoundException, SQLException, IOException{
		InputStream in=Sqlserverconn.class.getClassLoader().getResourceAsStream("config.properties");
		Properties prop=new Properties();		
		prop.load(in);
		String url=prop.getProperty("sqlserverurl");
		String username=prop.getProperty("sqlservername");
		String password=prop.getProperty("sqlserverpassword");
		String driver=prop.getProperty("sqlserverdriver");

		Class.forName(driver);  
//		System.out.println("����:"+url);
		return DriverManager.getConnection(url, username, password);  
	}
	
	public List getlist(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd=rs.getMetaData();
		this.len=rsmd.getColumnCount();
		List list=new ArrayList();
		while(rs.next()){
			Map map=new HashMap();
			for(int i=1;i<=len;i++){	
				map.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
			}
			list.add(map);
		}
		return list;
	}
}
