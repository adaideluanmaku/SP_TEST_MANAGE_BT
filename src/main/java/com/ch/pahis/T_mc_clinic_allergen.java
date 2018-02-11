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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.Message;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_clinic_allergen {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	@Autowired
	Sys_pa sys_pa;
	
	public void clinic_allergen(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate){

		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_clinic_allergen (allercode, patientid, clinicno, hiscode, allername, symptom, "
					+ "caseid) values(?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
			        ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				}
				for(int j=0;j<anlilist.size();j++){
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject ScreenAllergenList=json.getJSONObject("ScreenAllergenList");
					JSONArray ScreenAllergens=ScreenAllergenList.getJSONArray("ScreenAllergens");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Mz"+Patient.getString("PatCode");
					for(int k=0;k<ScreenAllergens.size();k++){
						JSONObject ScreenAllergen=ScreenAllergens.getJSONObject(k);
						if(StringUtils.isBlank(ScreenAllergen.getString("AllerCode"))){
							continue;
						}
						a=a+1;
						if(a%2000==0){
							System.out.println("t_mc_clinic_allergen--"+a);
						}
						Map map=new HashMap();
						map.put("ScreenAllergen", ScreenAllergen);
						map.put("Patient", Patient);
						map.put("PassClient", PassClient);
						map.put("caseid", caseid);
						
						listbatch.add(map);
						
						if(a%500==0){
							batchInsertRows(sql,listbatch);
							listbatch.clear();
						} 
						
//						if(a-10000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_clinic_allergen总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_clinic_allergen总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_clinic_allergen总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_clinic_allergen制造数据异常"+e);
		}
	
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
//				if(i%1000==0){
//					System.out.println("bbbbbbbb"+i);
//				}
				Map map=(Map)listbatch.get(i);
				JSONObject ScreenAllergen=JSONObject.fromObject(map.get("ScreenAllergen"));
				JSONObject Patient=JSONObject.fromObject(map.get("Patient"));
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient"));
				String caseid=map.get("caseid").toString();
				
				try{
					pst.setString(1,ScreenAllergen.getString("AllerCode"));
					pst.setString(2,Patient.getString("PatCode"));
					pst.setString(3,Patient.getString("InHospNo"));
					pst.setString(4,PassClient.getString("HospID"));
					pst.setString(5,ScreenAllergen.getString("AllerName"));
					pst.setString(6,"过敏症状");
					pst.setString(7,caseid);
				}catch (Exception e){
					System.out.println("出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_oracle.batchUpdate(sql, setter);
	}
}
