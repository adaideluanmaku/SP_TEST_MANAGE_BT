package com.ch.sqlserverpasshis;

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
public class Mc_dict_drug_sub1 {
	@Autowired
	JdbcTemplate jdbcTemplate_sqlserver;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Strisnull strisnull;
	
	public void dict_drug_sub(int match_scheme,String startdate) throws Exception{
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="select * from mc_dict_drug_sub where match_scheme=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_drug_sub where match_scheme=?";
//		jdbcTemplate_sqlserver.update(sql,new Object[]{match_scheme});
		
//		sql="insert into mc_dict_drug_sub(searchcode, dddunit, ddd, is_save, state, adddate, match_scheme, "
//				+ "is_use, drugname, drugspec, ddd_costunit, inserttime, drugform, drugcode, costunit) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1609版
//		sql="insert into mc_dict_drug_sub(searchcode, dddunit, ddd, is_save, state, adddate, match_scheme, "
//				+ "is_use, drugname, drugspec, ddd_costunit, drugform, drugcode, costunit , inserttime) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1712版
		sql="insert into mc_dict_drug_sub(searchcode, dddunit, ddd, is_save, state, adddate, match_scheme, "
				+ "is_use, drugname, drugspec, ddd_costunit, drugform, drugcode, costunit , updatedate) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,CONVERT(char(19),?))";
		
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
					pst.setString(1,strisnull.isnull(map.get("searchcode")).toString());//searchcode
					pst.setString(2,strisnull.isnull(map.get("dddunit")).toString());//dddunit
					pst.setString(3,strisnull.isnulltostr(map.get("ddd"),"-1").toString());//ddd
					pst.setString(4,strisnull.isnulltostr(map.get("is_save"),"-1").toString());//is_save
					pst.setString(5,strisnull.isnulltostr(map.get("state"),"-1").toString());//state
					pst.setString(6,strisnull.isnull(map.get("adddate")).toString());//adddate
					pst.setString(7,strisnull.isnull(map.get("match_scheme")).toString());//match_scheme
					pst.setString(8,strisnull.isnulltostr(map.get("is_use"),"-1").toString());//is_use
					pst.setString(9,strisnull.isnull(map.get("drugname")).toString());//drugname
					pst.setString(10,strisnull.isnull(map.get("drugspec")).toString());//drugspec
					pst.setString(11,strisnull.isnulltostr(map.get("ddd_costunit"),"-1").toString());//ddd_costunit
					pst.setString(12,strisnull.isnull(map.get("drugform")).toString());//drugform
					pst.setString(13,strisnull.isnull(map.get("drugcode")).toString());//drugcode
					pst.setString(14,strisnull.isnull(map.get("costunit")).toString());//costunit
					pst.setString(15,strisnull.isnull(startdate));//inserttime
					
				}catch(Exception e){
					System.out.println("mc_dict_drug_sub出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_sqlserver.batchUpdate(sql, setter);
	}
}
