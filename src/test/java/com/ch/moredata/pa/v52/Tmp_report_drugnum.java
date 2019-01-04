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
public class Tmp_report_drugnum {
	private static Logger log = Logger.getLogger(Tmp_report_drugnum.class);
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	
	@Autowired
	Sys_pa sys_pa;
	public void report_drugnum(String date,int datecount,String yuan_date,String databasename){
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
				sql="insert into Tmp_report_drugnum(is_anti, is_basedrug_p, drug_itemcode, deptcode, doctorname, ienddate, is_srpreparations, route_type, drugtype, deptmatch_scheme, costtime, mhiscode, is_sugarmed, doctorcode, doctormatch_scheme, deptname, match_druggroupcode, druggroupcode, antilevel, classid, routecode, antitype, itemcode, patstatus, is_basedrug, drugformtype, is_bloodmed) "
						+ "select is_anti, is_basedrug_p, drug_itemcode, deptcode, doctorname,? as ienddate, is_srpreparations, route_type, drugtype, deptmatch_scheme, date_add(ifnull(costtime,?), interval ? day) as costtime, mhiscode, is_sugarmed, doctorcode, doctormatch_scheme, deptname, match_druggroupcode, druggroupcode, antilevel, classid, routecode, antitype, itemcode, patstatus, is_basedrug, drugformtype, is_bloodmed from "+databasename+".Tmp_report_drugnum where ienddate=?";
				jdbcTemplate_mysql.update(sql,new Object[]{ienddate_1,date,(i),yuan_date});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Tmp_report_drugnum数据插入异常");
		}
		
		log.info("数据导入结束");
	}
}
