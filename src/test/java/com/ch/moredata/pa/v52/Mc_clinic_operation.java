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
public class Mc_clinic_operation {
	private static Logger log = Logger.getLogger(Mc_clinic_operation.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void clinic_operation(String date,int datecount,String yuan_date,String databasename){
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
				sql="insert into Mc_clinic_operation(incisiontype, deptcode, mhiscode, startdate, doctorname, doctorcode, deptname, enddate, ienddate, operationname, operationcode, caseid, oprid) "
						+ "select incisiontype, deptcode, mhiscode,  date_add(ifnull(startdate,?), interval ? day) as startdate, doctorname, doctorcode, deptname, date_add(ifnull(enddate,?), interval ? day) as enddate, ? as ienddate, operationname, operationcode, CONCAT(caseid, '_', ?) as caseid, oprid from "+databasename+".Mc_clinic_operation where ienddate=?";
				jdbcTemplate_mysql.update(sql,new Object[]{date,(i),date,(i),ienddate_1,ienddate_1, yuan_date});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Mc_clinic_operation数据插入异常");
		}
		
		log.info("数据导入结束");
	}
}
