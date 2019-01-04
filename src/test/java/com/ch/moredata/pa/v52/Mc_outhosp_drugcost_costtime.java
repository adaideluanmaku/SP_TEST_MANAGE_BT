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
public class Mc_outhosp_drugcost_costtime {
	private static Logger log = Logger.getLogger(Mc_outhosp_drugcost_costtime.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void outhosp_drugcost_costtime(String date,int datecount,String yuan_date,String databasename){
		jdbcTemplate_mysql=springJdbc_mysql.getJdbcTemplate();
		if(jdbcTemplate_mysql==null){
			log.info("数据库连接失败");
			return;
		}
		try {
			String sql=null;
			String ienddate=sys_pa.date6(date, "yyyyMMdd");
			String ienddate_1="";
			String costtime="";
			for(int i=0;i<datecount;i++){
				ienddate_1=sys_pa.date7(ienddate, "yyyyMMdd",(i));
				costtime=sys_pa.date6(ienddate_1, "yyyy-MM-dd HH:mm:ss");
				sql="insert into Mc_outhosp_drugcost_costtime(cost, costtime, itemunit, deptcode, wardcode, drugindex, mhiscode, doctorcode, medgroupcode, itemnum, routecode, ienddate, itemcode, is_out, caseid, is_use,drugmatch_scheme,routematch_scheme) "
						+ "select cost,date_add(ifnull(costtime,?), interval ? day) as costtime, itemunit, deptcode, wardcode, drugindex, mhiscode, doctorcode, medgroupcode, itemnum, routecode, ? as ienddate, itemcode, is_out,  CONCAT(caseid, '_', ?) as caseid, is_use,drugmatch_scheme,routematch_scheme from "+databasename+".Mc_outhosp_drugcost_costtime where ienddate=?";
				jdbcTemplate_mysql.update(sql,new Object[]{date,(i),ienddate_1,ienddate_1,yuan_date});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Mc_outhosp_drugcost_costtime数据插入异常");
		}
		
		log.info("数据导入结束");
	}
}
