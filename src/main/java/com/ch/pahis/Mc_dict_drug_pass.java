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
public class Mc_dict_drug_pass {
	private static Logger log = Logger.getLogger(Mc_dict_drug_pass.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	JdbcTemplate jdbcTemplate_database=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Strisnull strisnull;
	@Value("${data.insertdatacount}")
    private int insertdatacount;
	public void dict_drug_pass(int match_scheme,String startdate,int database1,String databasetype) throws Exception{
		jdbcTemplate_database=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_database==null){
			log.info("数据库连接失败");
			return;
		}
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="select * from mc_dict_drug_pass where match_scheme=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_drug_pass where match_scheme=?";
//		jdbcTemplate_oracle.update(sql,new Object[]{match_scheme});
		
		//1609版
//		sql="insert into mc_dict_drug_pass(searchcode, oprpi_time, match_scheme, pass_drugname, "
//				+ "comp_name, menulabel, drugname, drugform, drugcode, pass_drugcode, pass_upstate, proid, "
//				+ "pass_approvalcode, pass_form_name, match_time, unable_match_desc, unable_match, pass_st_strength,"
//				+ " match_user, drug_unique_code, approvalcode, pass_dividend, pass_divisor, pass_st_comp_name, "
//				+ "drugspec, oprpi_user, pass_doseunit, pass_nametype, doseunit) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
//				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1712版
		if("MYSQL".equals(databasetype)){
			
		}else if("MSSQL".equals(databasetype)){
			sql="insert into mc_dict_drug_pass( PASS_DRUGNAME,PASS_DOSEUNIT,PASS_APPROVALCODE,DOSEUNIT,SEARCHCODE,APPROVALCODE,MATCH_TIME,MATCH_USER,MENULABEL,PASS_ST_COMP_NAME,PASS_ST_STRENGTH,PASS_FORM_NAME,OPRPI_TIME,OPRPI_USER,UNABLE_MATCH_DESC,COMP_NAME,DRUGSPEC,DRUGFORM,DRUGNAME,DRUGCODE,DRUG_UNIQUE_CODE,PASS_UPSTATE,UNABLE_MATCH,PASS_DIVISOR,PASS_DIVIDEND,PASS_NAMETYPE,PASS_DRUGCODE,MATCH_SCHEME,PROID,UPDATEDATE ) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CONVERT(char(19),?))";
		}else if("ORACLE".equals(databasetype)){
			sql="insert into mc_dict_drug_pass( PASS_DRUGNAME,PASS_DOSEUNIT,PASS_APPROVALCODE,DOSEUNIT,SEARCHCODE,APPROVALCODE,MATCH_TIME,MATCH_USER,MENULABEL,PASS_ST_COMP_NAME,PASS_ST_STRENGTH,PASS_FORM_NAME,OPRPI_TIME,OPRPI_USER,UNABLE_MATCH_DESC,COMP_NAME,DRUGSPEC,DRUGFORM,DRUGNAME,DRUGCODE,DRUG_UNIQUE_CODE,PASS_UPSTATE,UNABLE_MATCH,PASS_DIVISOR,PASS_DIVIDEND,PASS_NAMETYPE,PASS_DRUGCODE,MATCH_SCHEME,PROID,UPDATEDATE ) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
		}
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("updatedate", startdate);
			listbatch.add(map);
			
			if((i+1)%insertdatacount==0){
				batchInsertRows(sql,listbatch);
				log.info("======>mc_dict_drug_pass :"+(i+1));
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch);
					log.info("======>mc_dict_drug_pass :"+(i+1));
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
					pst.setString(1,strisnull.isnull(map.get("PASS_DRUGNAME")));//PASS_DRUGNAME
					pst.setString(2,strisnull.isnull(map.get("PASS_DOSEUNIT")));//PASS_DOSEUNIT
					pst.setString(3,strisnull.isnull(map.get("PASS_APPROVALCODE")));//PASS_APPROVALCODE
					pst.setString(4,strisnull.isnull(map.get("DOSEUNIT")));//DOSEUNIT
					pst.setString(5,strisnull.isnull(map.get("SEARCHCODE")));//SEARCHCODE
					pst.setString(6,strisnull.isnull(map.get("APPROVALCODE")));//APPROVALCODE
					pst.setString(7,strisnull.isnull(map.get("MATCH_TIME")));//MATCH_TIME
					pst.setString(8,strisnull.isnull(map.get("MATCH_USER")));//MATCH_USER
					pst.setString(9,strisnull.isnull(map.get("MENULABEL")));//MENULABEL
					pst.setString(10,strisnull.isnull(map.get("PASS_ST_COMP_NAME")));//PASS_ST_COMP_NAME
					pst.setString(11,strisnull.isnull(map.get("PASS_ST_STRENGTH")));//PASS_ST_STRENGTH
					pst.setString(12,strisnull.isnull(map.get("PASS_FORM_NAME")));//PASS_FORM_NAME
					pst.setString(13,strisnull.isnull(map.get("OPRPI_TIME")));//OPRPI_TIME
					pst.setString(14,strisnull.isnull(map.get("OPRPI_USER")));//OPRPI_USER
					pst.setString(15,strisnull.isnull(map.get("UNABLE_MATCH_DESC")));//UNABLE_MATCH_DESC
					pst.setString(16,strisnull.isnull(map.get("COMP_NAME")));//COMP_NAME
					pst.setString(17,strisnull.isnull(map.get("DRUGSPEC")));//DRUGSPEC
					pst.setString(18,strisnull.isnull(map.get("DRUGFORM")));//DRUGFORM
					pst.setString(19,strisnull.isnull(map.get("DRUGNAME")));//DRUGNAME
					pst.setString(20,strisnull.isnull(map.get("DRUGCODE")));//DRUGCODE
					pst.setString(21,strisnull.isnull(map.get("DRUG_UNIQUE_CODE")));//DRUG_UNIQUE_CODE
					pst.setInt(22,strisnull.isnulltoint_0(map.get("PASS_UPSTATE"),-1));//PASS_UPSTATE
					pst.setInt(23,strisnull.isnulltoint_0(map.get("UNABLE_MATCH"),-1));//UNABLE_MATCH
					pst.setString(24,strisnull.isnull(map.get("PASS_DIVISOR")));//PASS_DIVISOR
					pst.setString(25,strisnull.isnull(map.get("PASS_DIVIDEND")));//PASS_DIVIDEND
					pst.setInt(26,strisnull.isnulltoint_0(map.get("PASS_NAMETYPE"),-1));//PASS_NAMETYPE
					pst.setInt(27,strisnull.isnulltoint_0(map.get("PASS_DRUGCODE"),-1));//PASS_DRUGCODE
					pst.setInt(28,strisnull.isnulltoint_0(map.get("MATCH_SCHEME"),-1));//MATCH_SCHEME
					pst.setInt(29,strisnull.isnulltoint_0(map.get("PROID"),-1));//PROID
					pst.setString(30,startdate);//UPDATEDATE
				}catch(Exception e){
					log.debug("调试==>mc_dict_drug_pass 插表异常 ："+map);
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
