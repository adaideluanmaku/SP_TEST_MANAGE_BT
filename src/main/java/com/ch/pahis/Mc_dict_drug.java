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
import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.dao.SpringJdbc_sqlserver_his;
import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONObject;
@Service
public class Mc_dict_drug {
	private static Logger log = Logger.getLogger(Mc_dict_drug.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	JdbcTemplate jdbcTemplate_database=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Strisnull strisnull;
	@Value("${data.insertdatacount}")
    private int insertdatacount;
	public int database=0;
	public void dict_drug(int match_scheme,String startdate,int database1,String databasetype) throws Exception{
		jdbcTemplate_database=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_database==null){
			log.info("数据库连接失败");
			return;
		}
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
		if("MYSQL".equals(databasetype)){
			
		}else if("MSSQL".equals(databasetype)){
			sql="insert into mc_dict_drug( OPERTIME,OPERUSER,CLASSTITLE,STIMULANTINGRED,SOCIALSECURITY_DESC,SOCIALSECURITY_RATIO,TYPENAME,DRGGRP_SEARCHCODE,DRUGGROUPNAME,SEARCHCODE,DRUGFORM,DRUGNAME,DRUGCODE,IS_STIMULANT,JDMTYPE,IS_SOCIALSECURITY,DRUGFORMTYPE,DRUGTYPE,ANTILEVEL,IS_BLOODMED,IS_POISON,IS_DEARMED,IS_NEEDSKINTEST,IS_SRPREPARATIONS,IS_SOLVENT,IS_SAVE,CLASSID,IS_BISECTION_USE,HIGH_ALERT_LEVEL,OTCTYPE,IS_SUGARMED,STATE,ANTITYPE,IS_ANTI,IS_BASEDRUG_P,IS_BASEDRUG,DRUGGROUPCODE,MATCH_SCHEME,UPDATEDATE ) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "CONVERT(char(19),?))";
		}else if("ORACLE".equals(databasetype)){
			sql="insert into mc_dict_drug( OPERTIME,OPERUSER,CLASSTITLE,STIMULANTINGRED,SOCIALSECURITY_DESC,SOCIALSECURITY_RATIO,TYPENAME,DRGGRP_SEARCHCODE,DRUGGROUPNAME,SEARCHCODE,DRUGFORM,DRUGNAME,DRUGCODE,IS_STIMULANT,JDMTYPE,IS_SOCIALSECURITY,DRUGFORMTYPE,DRUGTYPE,ANTILEVEL,IS_BLOODMED,IS_POISON,IS_DEARMED,IS_NEEDSKINTEST,IS_SRPREPARATIONS,IS_SOLVENT,IS_SAVE,CLASSID,IS_BISECTION_USE,HIGH_ALERT_LEVEL,OTCTYPE,IS_SUGARMED,STATE,ANTITYPE,IS_ANTI,IS_BASEDRUG_P,IS_BASEDRUG,DRUGGROUPCODE,MATCH_SCHEME,UPDATEDATE ) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
		}
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("updatedate", startdate);
			listbatch.add(map);
			
			if((i+1)%insertdatacount==0){
				batchInsertRows(sql,listbatch);
				log.info("======>mc_dict_drug :"+(i+1));
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch);
					log.info("======>mc_dict_drug :"+(i+1));
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
					pst.setString(1,strisnull.isnull(map.get("OPERTIME")));//OPERTIME
					pst.setString(2,strisnull.isnull(map.get("OPERUSER")));//OPERUSER
					pst.setString(3,strisnull.isnull(map.get("CLASSTITLE")));//CLASSTITLE
					pst.setString(4,strisnull.isnull(map.get("STIMULANTINGRED")));//STIMULANTINGRED
					pst.setString(5,strisnull.isnull(map.get("SOCIALSECURITY_DESC")));//SOCIALSECURITY_DESC
					pst.setString(6,strisnull.isnull(map.get("SOCIALSECURITY_RATIO")));//SOCIALSECURITY_RATIO
					pst.setString(7,strisnull.isnull(map.get("TYPENAME")));//TYPENAME
					pst.setString(8,strisnull.isnull(map.get("DRGGRP_SEARCHCODE")));//DRGGRP_SEARCHCODE
					pst.setString(9,strisnull.isnull(map.get("DRUGGROUPNAME")));//DRUGGROUPNAME
					pst.setString(10,strisnull.isnull(map.get("SEARCHCODE")));//SEARCHCODE
					pst.setString(11,strisnull.isnull(map.get("DRUGFORM")));//DRUGFORM
					pst.setString(12,strisnull.isnull(map.get("DRUGNAME")));//DRUGNAME
					pst.setString(13,strisnull.isnull(map.get("DRUGCODE")));//DRUGCODE
					pst.setInt(14,strisnull.isnulltoint_0(map.get("IS_STIMULANT"),-1));//IS_STIMULANT
					pst.setInt(15,strisnull.isnulltoint_0(map.get("JDMTYPE"),-1));//JDMTYPE
					pst.setInt(16,strisnull.isnulltoint_0(map.get("IS_SOCIALSECURITY"),-1));//IS_SOCIALSECURITY
					pst.setInt(17,strisnull.isnulltoint_0(map.get("DRUGFORMTYPE"),-1));//DRUGFORMTYPE
					pst.setInt(18,strisnull.isnulltoint_0(map.get("DRUGTYPE"),-1));//DRUGTYPE
					pst.setInt(19,strisnull.isnulltoint_0(map.get("ANTILEVEL"),-1));//ANTILEVEL
					pst.setInt(20,strisnull.isnulltoint_0(map.get("IS_BLOODMED"),-1));//IS_BLOODMED
					pst.setInt(21,strisnull.isnulltoint_0(map.get("IS_POISON"),-1));//IS_POISON
					pst.setInt(22,strisnull.isnulltoint_0(map.get("IS_DEARMED"),-1));//IS_DEARMED
					pst.setInt(23,strisnull.isnulltoint_0(map.get("IS_NEEDSKINTEST"),-1));//IS_NEEDSKINTEST
					pst.setInt(24,strisnull.isnulltoint_0(map.get("IS_SRPREPARATIONS"),-1));//IS_SRPREPARATIONS
					pst.setInt(25,strisnull.isnulltoint_0(map.get("IS_SOLVENT"),-1));//IS_SOLVENT
					pst.setInt(26,strisnull.isnulltoint_0(map.get("IS_SAVE"),-1));//IS_SAVE
					pst.setInt(27,strisnull.isnulltoint_0(map.get("CLASSID"),-1));//CLASSID
					pst.setInt(28,strisnull.isnulltoint_0(map.get("IS_BISECTION_USE"),-1));//IS_BISECTION_USE
					pst.setInt(29,strisnull.isnulltoint_0(map.get("HIGH_ALERT_LEVEL"),-1));//HIGH_ALERT_LEVEL
					pst.setInt(30,strisnull.isnulltoint_0(map.get("OTCTYPE"),-1));//OTCTYPE
					pst.setInt(31,strisnull.isnulltoint_0(map.get("IS_SUGARMED"),-1));//IS_SUGARMED
					pst.setInt(32,strisnull.isnulltoint_0(map.get("STATE"),-1));//STATE
					pst.setInt(33,strisnull.isnulltoint_0(map.get("ANTITYPE"),-1));//ANTITYPE
					pst.setInt(34,strisnull.isnulltoint_0(map.get("IS_ANTI"),-1));//IS_ANTI
					pst.setInt(35,strisnull.isnulltoint_0(map.get("IS_BASEDRUG_P"),-1));//IS_BASEDRUG_P
					pst.setInt(36,strisnull.isnulltoint_0(map.get("IS_BASEDRUG"),-1));//IS_BASEDRUG
					pst.setInt(37,strisnull.isnulltoint_0(map.get("DRUGGROUPCODE"),-1));//DRUGGROUPCODE
					pst.setInt(38,strisnull.isnulltoint_0(map.get("MATCH_SCHEME"),-1));//MATCH_SCHEME
					pst.setString(39,startdate);//UPDATEDATE
				}catch(Exception e){
					log.debug("调试==>mc_dict_drug 插表异常 ："+map);
					log.debug("调试==>"+e);
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
