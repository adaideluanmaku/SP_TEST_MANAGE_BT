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
public class Updatatest {
	private static Logger log = Logger.getLogger(Updatatest.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void clinic_allergen(String date,int datecount){
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
				sql="update Mc_clinic_drugorder_main a, " + 
						"(select concat(?,right(GOV_CPRSP_ID,10)) AS GOV_CPRSP_ID,concat(caseid,'_',?) as caseid ,concat(cid,'_',?) as cid " + 
						"from passrh_v52_big_big_bak.Mc_clinic_drugorder_main ) b " + 
						"set a.GOV_CPRSP_ID=b.GOV_CPRSP_ID " + 
						"where  a.ienddate=? and a.caseid=b.caseid and a.cid=b.cid ";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1,ienddate_1,ienddate_1});
				
				sql="update Mc_clinic_prescription a," + 
						"(select concat(?,right(GOV_CPRSP_ID,10)) AS GOV_CPRSP_ID,concat(?,right(GOV_CPB_ID,10)) AS GOV_CPB_ID, " + 
						"concat(caseid,'_',?) as caseid,prescno from passrh_v52_big_big_bak.Mc_clinic_prescription ) b " + 
						"set a.GOV_CPRSP_ID=b.GOV_CPRSP_ID " + 
						"where  a.ienddate=? and a.caseid=b.caseid and a.prescno=b.prescno ";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1,ienddate_1,ienddate_1});
				
				sql="update Mc_outhosp_drugorder_main a, " + 
						"(select concat(?,right(GOV_CPRSP_ID,10)) AS GOV_CPRSP_ID, " + 
						"concat(caseid,'_',?) as caseid,concat(cid,'_',?) as cid from passrh_v52_big_big_bak.Mc_outhosp_drugorder_main ) b " + 
						"set a.GOV_CPRSP_ID=b.GOV_CPRSP_ID " + 
						"where  a.ienddate=? and a.caseid=b.caseid and a.cid=b.cid ";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1,ienddate_1,ienddate_1});
				
				sql="update Mc_outhosp_patient_medinfo a, " + 
						"(select concat(?,right(GOV_CPRSP_ID,10)) AS GOV_CPRSP_ID, " + 
						"concat(caseid,'_',?) as caseid from passrh_v52_big_big_bak.Mc_outhosp_patient_medinfo ) b " + 
						"set a.GOV_CPRSP_ID=b.GOV_CPRSP_ID " + 
						"where  a.ienddate=? and a.caseid=b.caseid ; ";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1,ienddate_1});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("update数据插入异常");
		}
		log.info("数据导入结束");
	}
}
