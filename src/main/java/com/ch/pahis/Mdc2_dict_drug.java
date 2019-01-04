package com.ch.pahis;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.DataBaseType;
import com.ch.dao.SpringJdbc_sqlserver_his;
import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONObject;
@Service
public class Mdc2_dict_drug {
	private static Logger log = Logger.getLogger(Mdc2_dict_drug.class);
	JdbcTemplate jdbcTemplate_database=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Strisnull strisnull;
	@Value("${data.insertdatacount}")
    private int insertdatacount;
	public void dict_drug(int match_scheme,String startdate,int database1,String databasetype) throws Exception{
		jdbcTemplate_database=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_database==null){
			log.info("数据库连接失败");
			return;
		}
		
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
		if("MYSQL".equals(databasetype)){
			
		}else if("MSSQL".equals(databasetype)){
			sql="insert into mdc2_dict_drug( DDDUNIT,COSTUNIT,DRGGRP_SEARCHCODE,DRUGGROUPNAME,DOSEUNIT,COMP_NAME,APPROVALCODE,DRUGSPEC,DRUGFORM,DRUGNAME,DRUG_UNIQUE_CODE,DRUGCODE,IS_BASEDRUG,DDD,ANTILEVEL,ANTITYPE,IS_ANTI,IS_USE,ADDDATE,DRUGGROUPCODE,DRUGTYPE,MATCH_SCHEME,UPDATEDATE ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CONVERT(char(19),?))";
		}else if("ORACLE".equals(databasetype)){
			sql="insert into mdc2_dict_drug( DDDUNIT,COSTUNIT,DRGGRP_SEARCHCODE,DRUGGROUPNAME,DOSEUNIT,COMP_NAME,APPROVALCODE,DRUGSPEC,DRUGFORM,DRUGNAME,DRUG_UNIQUE_CODE,DRUGCODE,IS_BASEDRUG,DDD,ANTILEVEL,ANTITYPE,IS_ANTI,IS_USE,ADDDATE,DRUGGROUPCODE,DRUGTYPE,MATCH_SCHEME,UPDATEDATE ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
		}

		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("updatedate", startdate);
			listbatch.add(map);
			
			if((i+1)%insertdatacount==0){
				batchInsertRows(sql,listbatch);
				log.info("======>mdc2_dict_drug :"+(i+1));
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch);
					log.info("======>mdc2_dict_drug :"+(i+1));
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
					pst.setString(1,strisnull.isnull(map.get("DDDUNIT")));//DDDUNIT
					pst.setString(2,strisnull.isnull(map.get("COSTUNIT")));//COSTUNIT
					pst.setString(3,strisnull.isnull(map.get("DRGGRP_SEARCHCODE")));//DRGGRP_SEARCHCODE
					pst.setString(4,strisnull.isnull(map.get("DRUGGROUPNAME")));//DRUGGROUPNAME
					pst.setString(5,strisnull.isnull(map.get("DOSEUNIT")));//DOSEUNIT
					pst.setString(6,strisnull.isnull(map.get("COMP_NAME")));//COMP_NAME
					pst.setString(7,strisnull.isnull(map.get("APPROVALCODE")));//APPROVALCODE
					pst.setString(8,strisnull.isnull(map.get("DRUGSPEC")));//DRUGSPEC
					pst.setString(9,strisnull.isnull(map.get("DRUGFORM")));//DRUGFORM
					pst.setString(10,strisnull.isnull(map.get("DRUGNAME")));//DRUGNAME
					pst.setString(11,strisnull.isnull(map.get("DRUG_UNIQUE_CODE")));//DRUG_UNIQUE_CODE
					pst.setString(12,strisnull.isnull(map.get("DRUGCODE")));//DRUGCODE
					pst.setInt(13,strisnull.isnulltoint_0(map.get("IS_BASEDRUG"),-1));//IS_BASEDRUG
					pst.setInt(14,strisnull.isnulltoint_0(map.get("DDD"),-1));//DDD
					pst.setInt(15,strisnull.isnulltoint_0(map.get("ANTILEVEL"),-1));//ANTILEVEL
					pst.setInt(16,strisnull.isnulltoint_0(map.get("ANTITYPE"),-1));//ANTITYPE
					pst.setInt(17,strisnull.isnulltoint_0(map.get("IS_ANTI"),-1));//IS_ANTI
					pst.setInt(18,strisnull.isnulltoint_0(map.get("IS_USE"),-1));//IS_USE
					pst.setInt(19,strisnull.isnulltoint_0(map.get("ADDDATE"),-1));//ADDDATE
					pst.setInt(20,strisnull.isnulltoint_0(map.get("DRUGGROUPCODE"),-1));//DRUGGROUPCODE
					pst.setInt(21,strisnull.isnulltoint_0(map.get("DRUGTYPE"),-1));//DRUGTYPE
					pst.setInt(22,strisnull.isnulltoint_0(map.get("MATCH_SCHEME"),-1));//MATCH_SCHEME
					pst.setString(23,startdate);//UPDATEDATE
					
				}catch(Exception e){
					System.out.println("MDC2_DICT_DRUG出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_database.batchUpdate(sql, setter);
	}
}
