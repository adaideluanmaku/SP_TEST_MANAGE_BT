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
public class Mdc2_dict_drug1 {
	@Autowired
	JdbcTemplate jdbcTemplate_sqlserver;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Strisnull strisnull;
	
	public void dict_drug(int match_scheme,String startdate) throws Exception{
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="SELECT a.drugcode,c.drug_unique_code,	b.drugname,	b.drugform,	b.drugspec,	c.approvalcode,	c.comp_name,	c.doseunit, " + 
				" a.drugtype,a.DRUGGROUPCODE,a.DRUGGROUPNAME,a.DRGGRP_SEARCHCODE,	b.costunit,	b.adddate,	b.is_use,	a.is_anti, " + 
				"	a.antitype,	a.antilevel,	b.ddd,	b.dddunit,	a.is_basedrug FROM	mc_dict_drug a  " + 
				" left join	mc_dict_drug_sub b on a.drugcode=b.drugcode and a.match_scheme=b.match_scheme " + 
				" left join 	mc_dict_drug_pass c on a.drugcode = c.drugcode and c.match_scheme=b.match_scheme " + 
				" where a.match_scheme=? ";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_drug where match_scheme=?";
//		jdbcTemplate_sqlserver.update(sql,new Object[]{match_scheme});
		
		//1609版
//		sql="insert into mc_dict_drug(searchcode, is_bisection_use, operuser, is_save, drugformtype, "
//				+ "jdmtype, state, druggroupcode, match_scheme, is_bloodmed, socialsecurity_ratio, "
//				+ "drugtype, drugname, drugform, drugcode, stimulantingred, is_anti, antilevel, antitype, "
//				+ "is_basedrug_p, classid, druggroupname, is_socialsecurity, is_sugarmed, is_stimulant, "
//				+ "classtitle, is_basedrug, is_dearmed, high_alert_level, is_srpreparations, is_needskintest, "
//				+ "socialsecurity_desc, typename, is_solvent, is_poison, otctype, opertime, drggrp_searchcode) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1712版
		sql="insert into mdc2_dict_drug(drugcode,drug_unique_code ,drugname,drugform,drugspec,"
				+ "approvalcode,comp_name ,doseunit ,drugtype ,DRUGGROUPCODE ,DRUGGROUPNAME,"
				+ "DRGGRP_SEARCHCODE ,costunit ,adddate ,is_use ,is_anti ,antitype ,antilevel ,"
				+ "ddd ,dddunit,is_basedrug,UPDATEDATE ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
				+ ",?,?,?,CONVERT(char(19),?))";
		
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
					pst.setString(1,strisnull.isnull(map.get("drugcode")).toString());//drugcode
					pst.setString(2,strisnull.isnull(map.get("drug_unique_code")).toString());//drug_unique_code
					pst.setString(3,strisnull.isnull(map.get("drugname")).toString());//drugname
					pst.setString(4,strisnull.isnull(map.get("drugform")).toString());//drugform
					pst.setString(5,strisnull.isnull(map.get("drugspec")).toString());//drugspec
					pst.setString(6,strisnull.isnull(map.get("approvalcode")).toString());//approvalcode
					pst.setString(7,strisnull.isnull(map.get("comp_name")).toString());//comp_name
					pst.setString(8,strisnull.isnulltostr(map.get("doseunit"),"-1").toString());//doseunit
					pst.setString(9,strisnull.isnulltostr(map.get("drugtype"),"-1").toString());//drugtype
					pst.setString(10,strisnull.isnulltostr(map.get("DRUGGROUPCODE"),"-1").toString());//DRUGGROUPCODE
					pst.setString(11,strisnull.isnull(map.get("DRUGGROUPNAME")).toString());//DRUGGROUPNAME
					pst.setString(12,strisnull.isnulltostr(map.get("DRGGRP_SEARCHCODE"),"-1").toString());//DRGGRP_SEARCHCODE
					pst.setString(13,strisnull.isnull(map.get("costunit")).toString());//costunit
					pst.setString(14,strisnull.isnull(map.get("adddate")).toString());//adddate
					pst.setString(15,strisnull.isnulltostr(map.get("is_use"),"-1").toString());//is_use
					pst.setString(16,strisnull.isnulltostr(map.get("is_anti"),"-1").toString());//is_anti
					pst.setString(17,strisnull.isnulltostr(map.get("antitype"),"-1").toString());//antitype
					pst.setString(18,strisnull.isnulltostr(map.get("antilevel"),"-1").toString());//antilevel
					pst.setString(19,strisnull.isnulltostr(map.get("ddd"),"-1").toString());//ddd
					pst.setString(20,strisnull.isnull(map.get("dddunit")).toString());//dddunit
					pst.setString(21,strisnull.isnulltostr(map.get("is_basedrug"),"-1").toString());//is_basedrug
					pst.setString(22,strisnull.isnull(startdate));//druggroupname
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
		jdbcTemplate_sqlserver.batchUpdate(sql, setter);
	}
}
