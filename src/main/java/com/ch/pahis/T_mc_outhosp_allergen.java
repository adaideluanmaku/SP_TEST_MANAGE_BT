package com.ch.pahis;

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
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.DataBaseType;
import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.dao.SpringJdbc_sqlserver_his;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_outhosp_allergen {
	private static Logger log = Logger.getLogger(T_mc_outhosp_allergen.class);
	JdbcTemplate jdbcTemplate_dataBase=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Sys_pa sys_pa;
	@Value("${data.insertdatacount}")
    private String insertdatacount;
	public void outhosp_allergen(int trunca, int count, int sum_date,List anlilist,String hiscode,
			String ienddate,int database1){
		jdbcTemplate_dataBase=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_dataBase==null){
			log.info("数据库连接失败");
			return;
		}
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_outhosp_allergen (allercode, patientid, visitid, hiscode, allername, symptom, caseid"
					+ ") values(?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			JSONObject json=null;
			JSONObject PassClient=null;
			JSONObject Patient=null;
			JSONObject ScreenAllergenList=null;
			JSONArray ScreenAllergens=null;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				}
				for(int j=0;j<anlilist.size();j++){
					json=JSONObject.fromObject(anlilist.get(j));
					PassClient=json.getJSONObject("PassClient");
					Patient=json.getJSONObject("Patient");
					ScreenAllergenList=json.getJSONObject("ScreenAllergenList");
					ScreenAllergens=ScreenAllergenList.getJSONArray("ScreenAllergens");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Cy"+Patient.getString("PatCode");
					for(int k=0;k<ScreenAllergens.size();k++){
						JSONObject ScreenAllergen=ScreenAllergens.getJSONObject(k);
						if(StringUtils.isBlank(ScreenAllergen.getString("AllerCode"))){
							continue;
						}
						a=a+1;
						Map map=new HashMap();
						map.put("ScreenAllergen", ScreenAllergen);
						map.put("Patient", Patient);
						map.put("PassClient", PassClient);
						map.put("caseid", caseid);
						listbatch.add(map);
						
						if(a%Integer.parseInt(insertdatacount)==0){
							batchInsertRows(sql,listbatch);
							log.info("======>t_mc_clinic_lab :"+a);
							listbatch.clear();
						} 
						
//						if(a-50000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_inhosp_allergen总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_inhosp_allergen总数："+count*anlilist.size()+"-->有效数据："+(b+a));
//							listbatch.clear();
//						}
					}
					json.clear();
					json = null;
				}
			}
			if(listbatch.size()>0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}
			log.info("======>t_mc_outhosp_allergen 总数  ："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("调试==>t_mc_outhosp_allergen 制造数据异常 ："+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject ScreenAllergen=JSONObject.fromObject(map.get("ScreenAllergen").toString());
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String caseid=map.get("caseid").toString();
				
				try{
					pst.setString(1,ScreenAllergen.getString("AllerCode"));//[allercode
					pst.setString(2,Patient.getString("PatCode"));//patientid
					pst.setString(3,Patient.getString("VisitCode"));//visitid
					pst.setString(4,PassClient.getString("HospID"));//hiscode
					pst.setString(5,ScreenAllergen.getString("AllerName"));//allername
					pst.setString(6,"过敏症状");//symptom
					pst.setString(7,caseid);//caseid]
				}catch (Exception e){
					log.debug("调试==>t_mc_outhosp_allergen 插表异常 ："+map);
					log.debug("调试==>"+e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_dataBase.batchUpdate(sql, setter);
	}
}
