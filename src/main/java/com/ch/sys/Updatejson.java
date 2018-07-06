package com.ch.sys;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class Updatejson {
	String json="";

	//JSON
	public String getUpdate(String jsonin,String hiscode) throws IOException{
		JSONObject obj=JSONObject.fromObject(jsonin);
		JSONObject obj1=obj.getJSONObject("PassClient");
		obj1.element("HospID", hiscode);
		json=obj.toString();
		return json; 
	}
//	
//	public void getUpdatejson(String hiscode,String version) throws ClassNotFoundException, SQLException, IOException{
//		//mysql
//		Mysqlconn mysql=new Mysqlconn();
//		Connection mysqlconn=mysql.getConn();
//		PreparedStatement pst=null;
//		ResultSet rs=null;
//		String sql=null;
//		List list=null;
//		
//		sql="select * from sa_gather_log where gatherbaseinfo not like ? and version=?";
//		pst=mysqlconn.prepareStatement(sql);
//		pst.setString(1, "%\"HospID\":\""+hiscode+"%");
//		pst.setString(1, version);
//		rs=pst.executeQuery();
//		list=mysql.getlist(rs);
//		for(int i=0;i<list.size();i++){
//			Map map=(Map)list.get(i);
//			String jsonhiscode=getUpdate(map.get("gatherbaseinfo").toString(),hiscode);
//			sql="update sa_gather_log set gatherbaseinfo=? where id=?";
//			pst=mysqlconn.prepareStatement(sql);
//			pst.setString(1, jsonhiscode);
//			pst.setInt(2, Integer.parseInt(map.get("id").toString()));
//			pst.executeUpdate();
//		}
//		rs.close();
//		pst.close();
//		mysqlconn.close();
//	}
}
