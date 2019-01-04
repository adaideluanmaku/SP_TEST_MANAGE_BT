package com.ch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 原始JDBC数据库连接方式
 */
public class Mysqlconnection {
	private static String url="jdbc:mysql://172.18.7.160:3306/passrh_v52_big_big?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
	private static String username="root";
	private static String password="zfxmz123";
	private static String driver="com.mysql.jdbc.Driver";
	
	public Connection getConn() throws ClassNotFoundException, SQLException{
		Class.forName(driver);  
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
}
