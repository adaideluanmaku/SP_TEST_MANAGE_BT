package com.ch.pahis;

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

import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONObject;
@Service
public class Mc_dict_frequency {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Strisnull strisnull;
	
	public void dict_frequency(int match_scheme,String startdate) throws Exception{
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="select * from mc_dict_frequency where match_scheme=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_frequency where match_scheme=?";
//		jdbcTemplate_oracle.update(sql,new Object[]{match_scheme});
		
		//1609版
//		sql="insert into mc_dict_frequency(pharmfrequency, days, times, is_save, match_desc, "
//				+ "match_scheme, frequency, unable_match) values(?,?,?,?,?,?,?,?)";
		
		//1712版
		sql="insert into mc_dict_frequency(pharmfrequency, days, times, is_save, match_desc, "
				+ "match_scheme, frequency, unable_match, updatedate) values(?,?,?,?,?,?,?,?,"
				+ "to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("updatedate", startdate);
			listbatch.add(map);
			
			if((i+1)%500==0){
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
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				String startdate=map.get("updatedate").toString();
				try{
					pst.setString(1,strisnull.isnull(map.get("pharmfrequency")).toString());//pharmfrequency
					pst.setString(2,strisnull.isnull(map.get("days")).toString());//days
					pst.setString(3,strisnull.isnull(map.get("times")).toString());//times
					pst.setString(4,strisnull.isnull(map.get("is_save")).toString());//is_save
					pst.setString(5,strisnull.isnull(map.get("match_desc")).toString());//match_desc
					pst.setString(6,strisnull.isnull(map.get("match_scheme")).toString());//match_scheme
					pst.setString(7,strisnull.isnull(map.get("frequency")).toString());//frequency
					pst.setString(8,strisnull.isnull(map.get("unable_match")).toString());//unable_match
					pst.setString(9,strisnull.isnull(startdate));//updatedate
				}catch(Exception e){
					System.out.println("mc_dict_frequency出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_oracle.batchUpdate(sql, setter);
	}
}