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
public class Springbatch1 {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void Springbatch_Run(List datalist,String sql, String column) throws Exception{
		if(datalist.size()==0){
			return;
		}
		String[] columns=column.split(",");
		List list=datalist;
		List listbatch = new ArrayList();
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			listbatch.add(map);
			
			if((i+1)%100==0){
				batchInsertRows(sql,listbatch,columns);
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch,columns);
					listbatch.clear();
				}
			}
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch ,final String[] column) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
//				System.out.println(map);
				try{
					for(int k=0;k<column.length;k++){
//						System.out.println(map.get(column[k]));
						if (map.get(column[k]) instanceof Number) {
							pst.setInt((k+1), Integer.parseInt(map.get(column[k]).toString()));
						}else{
							pst.setString((k+1),isnull(map.get(column[k])).toString());
						}
					}
					
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
