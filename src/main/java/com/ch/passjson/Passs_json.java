package com.ch.passjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ch.alterhiscode.AlterHiscode;
import com.ch.sys.Passservice;

import net.sf.json.JSONObject;

@Service
public class Passs_json {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	Screenresule_1609 screenresule_1609;
	@Autowired
	Screenresule_1712 screenresule_1712;
	@Autowired
	Screenresule_1809 screenresule_1809;
	@Autowired
	Detailresule detailresule;
	@Autowired
	Queryresule queryresule;
	@Autowired
	Moduleresule moduleresule;
	@Autowired
	Xmltojson xmltojson;
	
	public List getResult(String gatherresult,String gatherresult_java,String jsonversion,int jsontype,int testid) throws TimeoutException, IOException, ClassNotFoundException, SQLException, DocumentException{
//		AlterHiscode alterHiscode = new AlterHiscode();
//		gatherbaseinfo=alterHiscode.alterhis(gatherbaseinfo);
		
		String sql="update pass_testmng set testout_response=? where testid=?";
		jdbcTemplate.update(sql,new Object[]{gatherresult_java,testid});
		
		List json_err = new ArrayList();
		//json
		//jsontype 1,审查，2，查询，3详细，4浮动窗口，5说明书，6模块，7其他
		if("1609".equals(jsonversion)){
			if(jsontype==1){
				json_err=screenresule_1609.screenres(gatherresult,gatherresult_java);
			}else if(jsontype==2){
				json_err=detailresule.detail(gatherresult, gatherresult_java);
			}else if(jsontype==3){
				json_err=queryresule.query(gatherresult, gatherresult_java);
			}else if(jsontype==4){
				json_err=moduleresule.module(gatherresult, gatherresult_java);
			}else {
				if(gatherresult.contains("<?xml")){
					gatherresult=xmltojson.getjson(gatherresult);
				}
				if(gatherresult_java.contains("<?xml")){
					gatherresult_java=xmltojson.getjson(gatherresult_java);
				}
				if(!gatherresult_java.equals(gatherresult)){
					json_err.add("Assertion："+gatherresult);
					json_err.add("response："+gatherresult_java);
				}
			}
		}
		if("1712".equals(jsonversion)){
			if(jsontype==1){
				json_err=screenresule_1712.screenres(gatherresult,gatherresult_java);
			}else if(jsontype==2){
				json_err=detailresule.detail(gatherresult, gatherresult_java);
			}else if(jsontype==3){
				json_err=queryresule.query(gatherresult, gatherresult_java);
			}else if(jsontype==4){
				json_err=moduleresule.module(gatherresult, gatherresult_java);
			}else {
				if(gatherresult.contains("<?xml")){
					gatherresult=xmltojson.getjson(gatherresult);
				}
				if(gatherresult_java.contains("<?xml")){
					gatherresult_java=xmltojson.getjson(gatherresult_java);
				}
				if(!gatherresult_java.equals(gatherresult)){
					json_err.add("Assertion："+gatherresult);
					json_err.add("response："+gatherresult_java);
				}
			}
		}
		if("1809".equals(jsonversion)){
			if(jsontype==1){
				json_err=screenresule_1809.screenres(gatherresult,gatherresult_java);
			}else if(jsontype==2){
				json_err=detailresule.detail(gatherresult, gatherresult_java);
			}else if(jsontype==3){
				json_err=queryresule.query(gatherresult, gatherresult_java);
			}else if(jsontype==4){
				json_err=moduleresule.module(gatherresult, gatherresult_java);
			}else {
				if(gatherresult.contains("<?xml")){
					gatherresult=xmltojson.getjson(gatherresult);
				}
				if(gatherresult_java.contains("<?xml")){
					gatherresult_java=xmltojson.getjson(gatherresult_java);
				}
				if(!gatherresult_java.equals(gatherresult)){
					json_err.add("Assertion："+gatherresult);
					json_err.add("response："+gatherresult_java);
				}
			}
		}
		
		return json_err;
	}
}
