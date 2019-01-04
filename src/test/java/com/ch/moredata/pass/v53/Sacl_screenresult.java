package com.ch.moredata.pass.v53;

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

import com.ch.dao.Mysqlconnection;
import com.ch.dao.SpringJdbc_mysql_pass;
import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.dao.SpringJdbc_sqlserver_his;
import com.ch.pahis.Sys_pa;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class Sacl_screenresult {
	private static Logger log = Logger.getLogger(Sacl_screenresult.class);
	JdbcTemplate jdbcTemplate_mysql=null;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql_pass;
	
	@Autowired
	Sys_pa sys_pa;
	public void _screenresult(String startdate,int datecount){
		jdbcTemplate_mysql=springJdbc_mysql_pass.getJdbcTemplate();
		if(jdbcTemplate_mysql==null){
			log.info("数据库连接失败");
			return;
		}
		try {
			String sql=null;
			String ienddate=sys_pa.date6(startdate, "yyyyMMdd");
			String ienddate_1="";
			for(int i=0;i<datecount;i++){
				ienddate_1=sys_pa.date7(ienddate, "yyyyMMdd",(i));
				sql="insert into sacl_screenresult(lasttimebyday,patlevel,focusdesc,isfocus,doctorfilter,screenelements,usetime,isuser,shield,duration,executetime,enddatetime,startdatetime,doctorname,doctorcode,deptname,deptcode,routename,routecode,numunit,num,istempdrug,frequency,dosepertime,doseunit,drugname,drugcode,druguniquecode,druguniquehash,orderindex,recipno,allergen,diseases,age,sex,patname,visitcode,inhospno,patcode,patstatusdesc,patstatus,isforstatic,usedate,hisname,otherinfo,warninghash,warning,severity,slcodename,passcoreslcode,slcode,modulename,moduleitems,moduleid,checktype,warntype,prcaseid,caseid,mhiscode,sendtime,AUTOEVID) "
						+ "select lasttimebyday,patlevel,focusdesc,isfocus,doctorfilter,screenelements,usetime,isuser,shield,duration,executetime,enddatetime,startdatetime,doctorname,doctorcode,deptname,deptcode,routename,routecode,numunit,num,istempdrug,frequency,dosepertime,doseunit,drugname,drugcode,druguniquecode,druguniquehash,orderindex,recipno,allergen,diseases,age,sex,patname,visitcode,inhospno,patcode,patstatusdesc,patstatus,isforstatic,? AS usedate,hisname,otherinfo,warninghash,warning,severity,slcodename,passcoreslcode,slcode,modulename,moduleitems,moduleid,checktype,warntype,prcaseid,CONCAT(caseid, '_', ?) as caseid,mhiscode,sendtime,concat(?,right(autoevid,10)) AS autoevid from sacl_screenresult_copy ";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1,ienddate_1});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("sacl_screenresult数据插入异常");
		}
		log.info("数据导入结束");
	}
}
