package com.ch.moredata.pa.v52;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.SpringJdbc_mysql_pass;
import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.dao.SpringJdbc_sqlserver_his;
import com.ch.pahis.Sys_pa;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class Mc_outhosp_patient_baseinfo {
	private static Logger log = Logger.getLogger(Mc_outhosp_patient_baseinfo.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void outhosp_patient_baseinfo(String date,int datecount,String yuan_date,String databasename){
		jdbcTemplate_mysql=springJdbc_mysql.getJdbcTemplate();
		if(jdbcTemplate_mysql==null){
			log.info("数据库连接失败");
			return;
		}
		try {
			String sql=null;
			String ienddate=sys_pa.date6(date, "yyyyMMdd");
			String ienddate_1="";
			for(int i=0;i<datecount;i++){
				ienddate_1=sys_pa.date7(ienddate, "yyyyMMdd",(i));
				sql="insert into Mc_outhosp_patient_baseinfo(nursingclass, ren_damage, allergen, birthdate, is_preg, is_lact, standby, patientid, weight, mhiscode, telephone, hep_damage, usetime, visitid, preg_starttime, ienddate, bedno, caseid, identitycard, accountdate, age, height) "
						+ " select nursingclass, ren_damage, allergen, birthdate, is_preg, is_lact, standby, patientid, weight, mhiscode, telephone, hep_damage,  date_add(ifnull(usetime,?), interval ? day) as usetime, visitid, date_add(ifnull(preg_starttime,?), interval ? day) as preg_starttime, ? as ienddate, bedno,  CONCAT(caseid, '_', ?) as caseid, identitycard, accountdate, age, height from "+databasename+".Mc_outhosp_patient_baseinfo where ienddate=?";
				jdbcTemplate_mysql.update(sql,new Object[]{date,(i),date,(i),ienddate_1,ienddate_1,yuan_date});
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Mc_outhosp_patient_baseinfo数据插入异常");
			log.debug(e);
		}
		
		log.info("数据导入结束");
	}
}
