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
public class Mc_dict_drug_sub {
	private static Logger log = Logger.getLogger(Mc_dict_drug_sub.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	JdbcTemplate jdbcTemplate_database=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Strisnull strisnull;
	@Value("${data.insertdatacount}")
    private int insertdatacount;
	public void dict_drug_sub(int match_scheme,String startdate,int database1,String databasetype) throws Exception{
		jdbcTemplate_database=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_database==null){
			log.info("数据库连接失败");
			return;
		}
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="select * from mc_dict_drug_sub where match_scheme=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_drug_sub where match_scheme=?";
//		jdbcTemplate_oracle.update(sql,new Object[]{match_scheme});
		
//		sql="insert into mc_dict_drug_sub(searchcode, dddunit, ddd, is_save, state, adddate, match_scheme, "
//				+ "is_use, drugname, drugspec, ddd_costunit, inserttime, drugform, drugcode, costunit) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1609版
//		sql="insert into mc_dict_drug_sub(searchcode, dddunit, ddd, is_save, state, adddate, match_scheme, "
//				+ "is_use, drugname, drugspec, ddd_costunit, drugform, drugcode, costunit , inserttime) "
//				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1712版
		if("MYSQL".equals(databasetype)){
			
		}else if("MSSQL".equals(databasetype)){
			sql="insert into mc_dict_drug_sub( ADDDATE,DDDUNIT,COSTUNIT,DRUGSPEC,DRUGFORM,SEARCHCODE,DRUGNAME,DRUGCODE,STATE,IS_SAVE,IS_USE,DDD_COSTUNIT,DDD,MATCH_SCHEME,UPDATEDATE ) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,CONVERT(char(19),?))";
		}else if("ORACLE".equals(databasetype)){
			sql="insert into mc_dict_drug_sub( ADDDATE,DDDUNIT,COSTUNIT,DRUGSPEC,DRUGFORM,SEARCHCODE,DRUGNAME,DRUGCODE,STATE,IS_SAVE,IS_USE,DDD_COSTUNIT,DDD,MATCH_SCHEME,UPDATEDATE ) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
		}
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("updatedate", startdate);
			listbatch.add(map);
			
			if((i+1)%insertdatacount==0){
				batchInsertRows(sql,listbatch);
				log.info("======>mc_dict_drug_sub :"+(i+1));
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch);
					log.info("======>mc_dict_drug_sub :"+(i+1));
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
					pst.setString(1,strisnull.isnull(map.get("ADDDATE")));//ADDDATE
					pst.setString(2,strisnull.isnull(map.get("DDDUNIT")));//DDDUNIT
					pst.setString(3,strisnull.isnull(map.get("COSTUNIT")));//COSTUNIT
					pst.setString(4,strisnull.isnull(map.get("DRUGSPEC")));//DRUGSPEC
					pst.setString(5,strisnull.isnull(map.get("DRUGFORM")));//DRUGFORM
					pst.setString(6,strisnull.isnull(map.get("SEARCHCODE")));//SEARCHCODE
					pst.setString(7,strisnull.isnull(map.get("DRUGNAME")));//DRUGNAME
					pst.setString(8,strisnull.isnull(map.get("DRUGCODE")));//DRUGCODE
					pst.setInt(9,strisnull.isnulltoint_0(map.get("STATE"),-1));//STATE
					pst.setInt(10,strisnull.isnulltoint_0(map.get("IS_SAVE"),-1));//IS_SAVE
					pst.setInt(11,strisnull.isnulltoint_0(map.get("IS_USE"),-1));//IS_USE
					pst.setInt(12,strisnull.isnulltoint_0(map.get("DDD_COSTUNIT"),-1));//DDD_COSTUNIT
					pst.setInt(13,strisnull.isnulltoint_0(map.get("DDD"),-1));//DDD
					pst.setInt(14,strisnull.isnulltoint_0(map.get("MATCH_SCHEME"),-1));//MATCH_SCHEME
					pst.setString(15,startdate);//UPDATEDATE
				}catch(Exception e){
					System.out.println(map);
					log.debug("调试==>mc_dict_drug_sub 插表异常 ："+map);
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
