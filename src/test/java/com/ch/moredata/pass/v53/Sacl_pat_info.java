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
public class Sacl_pat_info {
	private static Logger log = Logger.getLogger(Sacl_pat_info.class);
	JdbcTemplate jdbcTemplate_mysql=null;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql_pass;
	
	@Autowired
	Sys_pa sys_pa;
	public void pat_info(String startdate,int datecount){
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
				sql="insert into sacl_pat_info(patlevel,usetime,pharmerremark,opertime,operid,comment,collected,temperature,isfast,medicaretype,focusdesc,isfocus,standby,hep_damage,ren_damage,preg_starttime,is_preg,is_lact,allergen,diagnosis,birthdate,enddate,doctorname,deptname,weight,height,age,doctorcode,deptcode,sex,iage,patientname,mednum,patientid,prescno,visitcode,patstatus,caseid,mhiscode,autoevid) "
						+ "select patlevel,usetime,pharmerremark,opertime,operid,comment,collected,temperature,isfast,medicaretype,focusdesc,isfocus,standby,hep_damage,ren_damage,preg_starttime,is_preg,is_lact,allergen,diagnosis,birthdate,enddate,doctorname,deptname,weight,height,age,doctorcode,deptcode,sex,iage,patientname,mednum,patientid,prescno,visitcode,patstatus,CONCAT(caseid, '_', ?) as caseid,mhiscode,concat(?,right(autoevid,10)) AS autoevid from sacl_pat_info_copy ";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,ienddate_1});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("sacl_pat_info数据插入异常");
		}
		log.info("数据导入结束");
	}
}
