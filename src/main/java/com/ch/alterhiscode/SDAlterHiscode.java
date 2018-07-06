package com.ch.alterhiscode;
//package ch.com.alterhiscode;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
////手动修改hiscode,并保存到数据库
//public class SDAlterHiscode {
//	public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException{
//		Mysqlconn mysql=new Mysqlconn();
//		Connection mysqlconn=mysql.getConn();
//		
//		PreparedStatement pst=null;
//		Statement st=null;
//		ResultSet rs=null;
//		List list=null;
//		String sql=null;
//		
//		String hiscode="1609PASS";
//		
//		sql="select id,gatherbaseinfo from sa_gather_log where anlitype=1 or anlitype=2";
//		st=mysqlconn.createStatement();
//		rs=st.executeQuery(sql);
//		list=mysql.getlist(rs);
//		for(int i=0;i<list.size();i++){
//			Map map=(Map)list.get(i);
//			JSONObject json=JSONObject.fromObject(map.get("gatherbaseinfo"));
//			JSONObject PassClient=json.getJSONObject("PassClient");
//			PassClient.put("HospID", "1609PASS");
//			json.put("PassClient", PassClient);
//			sql="update sa_gather_log set gatherbaseinfo=? where id=?";
//			pst=mysqlconn.prepareStatement(sql);
//			pst.setString(1, json.toString());
//			pst.setInt(2, Integer.parseInt(map.get("id").toString()));
//			pst.executeUpdate();
//			System.out.println(i+1);
//		}
//		
//		pst.close();
//		st.close();
//		mysqlconn.close();
//	}
//}
