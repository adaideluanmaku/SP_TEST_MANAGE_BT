package com.ch.sys;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;

/**
 * 写成活的后，效率感觉很慢，未进行优化
 * @author 陈辉
 *
 */
@Service
public class Springbatch {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void sa_gather_log(List datalist,String sql) throws Exception{
		if(datalist.size()==0){
			return;
		}
		List list=datalist;
		List listbatch = new ArrayList();
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			listbatch.add(map);
			
			if((i+1)%300==0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch);
					listbatch.clear();
				}
			}
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch ) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
//				System.out.println(map);
				try{
					pst.setString(1, map.get("gatherbaseinfo").toString());
					pst.setString(2, map.get("gatherresult").toString());
					pst.setString(3, map.get("testname").toString());
					pst.setInt(4, Integer.parseInt(map.get("anlitype").toString()));
					pst.setInt(5, Integer.parseInt(map.get("moduleid").toString()));
					pst.setString(6, map.get("modulename").toString());
				}catch(Exception e){
					System.out.println("批处理出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate.batchUpdate(sql, setter);
	}
	
	public void pass_testmng(List datalist,String sql) throws Exception{
		if(datalist.size()==0){
			return;
		}
		List list=datalist;
		List listbatch = new ArrayList();
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			listbatch.add(map);
			
			if((i+1)%300==0){
				batchInsertRows1(sql,listbatch);
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows1(sql,listbatch);
					listbatch.clear();
				}
			}
		}
	}
	
	public void batchInsertRows1(String sql, final List listbatch ) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
//				System.out.println(map);
				try{
					pst.setInt(1, Integer.parseInt(map.get("status").toString()));
					pst.setInt(2, Integer.parseInt(map.get("projectid").toString()));
					pst.setString(3, map.get("testname").toString());
					pst.setString(4, map.get("testno").toString());
					pst.setString(5, map.get("testin").toString());
					pst.setString(6, map.get("testout").toString());
					pst.setInt(7, Integer.parseInt(map.get("moduleid").toString()));
					pst.setString(8, map.get("modulename").toString());
					pst.setInt(9, Integer.parseInt(map.get("anlitype").toString()));
					pst.setInt(10, Integer.parseInt(map.get("userid").toString()));
				}catch(Exception e){
					System.out.println("批处理出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate.batchUpdate(sql, setter);
	}
	
	public String isnull(Object obj){
		if(obj==null || "".equals(obj)){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	public String isnullorstr(Object obj,String str){
		if(obj==null || "".equals(obj)){
			return str;
		}else{
			return obj.toString();
		}
	}
		
}
