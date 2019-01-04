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
public class Mc_outhosp_drugorder_main {
	private static Logger log = Logger.getLogger(Mc_outhosp_drugorder_main.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void outhosp_drugorder_main(String date,int datecount,String yuan_date,String databasename){
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
				sql="insert into Mc_outhosp_drugorder_main(pa_enddatetime, GOV_CPRSP_ID, deptcode, mhiscode, doctorcode, executetime, meditime, routecode, ordercode, ienddate, caseid, is_out, enddatetime, is_allow, is_use, startdatetime, cid) "
						+ "select date_add(ifnull(pa_enddatetime,?), interval ? day) as pa_enddatetime, concat(?,right(GOV_CPRSP_ID,10)) AS GOV_CPRSP_ID, deptcode, mhiscode, doctorcode, date_add(ifnull(executetime,?), interval ? day) as executetime, meditime, routecode, ordercode, ? as ienddate, CONCAT(caseid, '_', ?) as caseid, is_out, enddatetime, is_allow, is_use, startdatetime, CONCAT(cid, '_', ?) as cid from "+databasename+".Mc_outhosp_drugorder_main where ienddate=?";
				jdbcTemplate_mysql.update(sql,new Object[]{date,(i),ienddate_1,date,(i),ienddate_1,ienddate_1,ienddate_1,yuan_date});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Mc_outhosp_drugorder_main数据插入异常");
			log.debug(e);
		}
		
		log.info("数据导入结束");
	}
}
