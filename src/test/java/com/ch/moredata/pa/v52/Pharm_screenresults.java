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
public class Pharm_screenresults {
	private static Logger log = Logger.getLogger(Pharm_screenresults.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void _screenresults(String date,int datecount,String yuan_date,String databasename){
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
				sql="insert into Pharm_screenresults(chkresid, jdmtype, orderno, drugmaxwarn, is_anti, recipeno, deptcode, drug_unique_code, doctorname, moduleitem, ienddate, modulename, warning, is_allow, slcode, drugspec, sys_datetime, severity, case_no, deptmatch_scheme, drugmatch_scheme, visitcode, mhiscode, drugname, doctormatch_scheme, doctorcode, usetime, costunit, deptname, patientname, classid, antilevel, antitype, ilevel, patstatus, caseid, moduleid, cid, startdatetime) "
						+ "select concat(?,right(chkresid,10)) AS chkresid,jdmtype, orderno, drugmaxwarn, is_anti, recipeno, deptcode, drug_unique_code, doctorname, moduleitem, ? as ienddate, modulename, warning, is_allow, slcode, drugspec, sys_datetime, severity, case_no, deptmatch_scheme, drugmatch_scheme, visitcode, mhiscode, drugname, doctormatch_scheme, doctorcode, usetime, costunit, deptname, patientname, classid, antilevel, antitype, ilevel, patstatus, CONCAT(caseid, '_', ?) as caseid, moduleid, CONCAT(cid, '_', ?) as cid, startdatetime from "+databasename+".Pharm_screenresults where ienddate=?";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1,ienddate_1,ienddate_1,yuan_date});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Pharm_screenresults数据插入异常");
		}
		log.info("数据导入结束");
	}
}
