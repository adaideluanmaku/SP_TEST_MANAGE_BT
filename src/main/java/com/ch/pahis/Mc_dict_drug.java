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
public class Mc_dict_drug {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Strisnull strisnull;
	
	public void dict_drug(int match_scheme,String startdate) throws Exception{
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="select * from mc_dict_drug where match_scheme=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_drug where match_scheme=?";
//		jdbcTemplate_oracle.update(sql,new Object[]{match_scheme});
		
		//1609版
//		sql="insert into mc_dict_drug(searchcode, is_bisection_use, operuser, is_save, drugformtype, "
//				+ "jdmtype, state, druggroupcode, match_scheme, is_bloodmed, socialsecurity_ratio, "
//				+ "drugtype, drugname, drugform, drugcode, stimulantingred, is_anti, antilevel, antitype, "
//				+ "is_basedrug_p, classid, druggroupname, is_socialsecurity, is_sugarmed, is_stimulant, "
//				+ "classtitle, is_basedrug, is_dearmed, high_alert_level, is_srpreparations, is_needskintest, "
//				+ "socialsecurity_desc, typename, is_solvent, is_poison, otctype, opertime, drggrp_searchcode) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1712版
		sql="insert into mc_dict_drug(searchcode, is_bisection_use, operuser, is_save, drugformtype, "
				+ "jdmtype, state, druggroupcode, match_scheme, is_bloodmed, socialsecurity_ratio, "
				+ "drugtype, drugname, drugform, drugcode, stimulantingred, is_anti, antilevel, antitype, "
				+ "is_basedrug_p, classid, druggroupname, is_socialsecurity, is_sugarmed, is_stimulant, "
				+ "classtitle, is_basedrug, is_dearmed, high_alert_level, is_srpreparations, is_needskintest, "
				+ "socialsecurity_desc, typename, is_solvent, is_poison, otctype, opertime, drggrp_searchcode, "
				+ "updatedate) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
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
				
//				System.out.println(map.get("drugcode").toString());
				try{
					pst.setString(1,strisnull.isnull(map.get("searchcode")).toString());//searchcode
					pst.setString(2,strisnull.isnull(map.get("is_bisection_use")).toString());//is_bisection_use
					pst.setString(3,strisnull.isnull(map.get("operuser")).toString());//operuser
					pst.setString(4,strisnull.isnull(map.get("is_save")).toString());//is_save
					pst.setString(5,strisnull.isnull(map.get("drugformtype")).toString());//drugformtype
					pst.setString(6,strisnull.isnull(map.get("jdmtype")).toString());//jdmtype
					pst.setString(7,strisnull.isnull(map.get("state")).toString());//state
					pst.setString(8,strisnull.isnull(map.get("druggroupcode")).toString());//druggroupcode
					pst.setString(9,strisnull.isnull(map.get("match_scheme")).toString());//match_scheme
					pst.setString(10,strisnull.isnull(map.get("is_bloodmed")).toString());//is_bloodmed
					pst.setString(11,strisnull.isnull(map.get("socialsecurity_ratio")).toString());//socialsecurity_ratio
					pst.setString(12,strisnull.isnull(map.get("drugtype")).toString());//drugtype
					pst.setString(13,strisnull.isnull(map.get("drugname")).toString());//drugname
					pst.setString(14,strisnull.isnull(map.get("drugform")).toString());//drugform
					pst.setString(15,strisnull.isnull(map.get("drugcode")).toString());//drugcode
					pst.setString(16,strisnull.isnull(map.get("stimulantingred")).toString());//stimulantingred
					pst.setString(17,strisnull.isnull(map.get("is_anti")).toString());//is_anti
					pst.setString(18,strisnull.isnull(map.get("antilevel")).toString());//antilevel
					pst.setString(19,strisnull.isnull(map.get("antitype")).toString());//antitype
					pst.setString(20,strisnull.isnull(map.get("is_basedrug_p")).toString());//is_basedrug_p
					pst.setString(21,strisnull.isnull(map.get("classid")).toString());//classid
					pst.setString(22,strisnull.isnull(map.get("druggroupname")).toString());//druggroupname
					pst.setString(23,strisnull.isnull(map.get("is_socialsecurity")).toString());//is_socialsecurity
					pst.setString(24,strisnull.isnull(map.get("is_sugarmed")).toString());//is_sugarmed
					pst.setString(25,strisnull.isnull(map.get("is_stimulant")).toString());//is_stimulant
					pst.setString(26,strisnull.isnull(map.get("classtitle")).toString());//classtitle
					pst.setString(27,strisnull.isnull(map.get("is_basedrug")).toString());//is_basedrug
					pst.setString(28,strisnull.isnull(map.get("is_dearmed")).toString());//is_dearmed
					pst.setString(29,strisnull.isnull(map.get("high_alert_level")).toString());//high_alert_level
					pst.setString(30,strisnull.isnull(map.get("is_srpreparations")).toString());//is_srpreparations
					pst.setString(31,strisnull.isnull(map.get("is_needskintest")).toString());//is_needskintest
					pst.setString(32,strisnull.isnull(map.get("socialsecurity_desc")).toString());//socialsecurity_desc
					pst.setString(33,strisnull.isnull(map.get("typename")).toString());//typename
					pst.setString(34,strisnull.isnull(map.get("is_solvent")).toString());//is_solvent
					pst.setString(35,strisnull.isnull(map.get("is_poison")).toString());//is_poison
					pst.setString(36,strisnull.isnull(map.get("otctype")).toString());//otctype
					pst.setString(37,strisnull.isnull(map.get("opertime")).toString());//opertime
					pst.setString(38,strisnull.isnull(map.get("drggrp_searchcode")).toString());//drggrp_searchcode
					pst.setString(39,strisnull.isnull(startdate));//updatedate
				}catch(Exception e){
					System.out.println("mc_dict_drug出现异常的数据:"+map);
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
