package com.ch.sqlserverpasshis;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_clinic_patient1 {
	@Autowired
	JdbcTemplate jdbcTemplate_sqlserver;
	
	@Autowired
	Sys_pa1 sys_pa;
	public void clinic_patient(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String enddate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_clinic_patient (sex, doctorname, weight, clinicno, diagnosis, ren_damage, iage, "
					+ "is_emergency, height, payclass, deptcode, medgroupname, age, is_lact, birthdate, identitycard, "
					+ "deptname, caseid, ienddate, allergen, doctorcode, hep_damage, medgroupcode, is_preg, cost, "
					+ "preg_starttime, standby, patientname, patientid, hiscode, enddate, telephone) values(?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			String enddate1=enddate;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
			        ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
			        enddate1=sys_pa.date1(enddate1, "yyyy-MM-dd HH:mm:ss");
				}
				for(int j=0;j<anlilist.size();j++){
					a=a+1;
					if(a%2000==0){
						System.out.println("t_mc_clinic_patient --"+a);
					}
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Mz"+Patient.getString("PatCode");

					Map map=new HashMap();
					map.put("Patient", Patient);
					map.put("caseid", caseid);
					map.put("PassClient", PassClient);
					map.put("ienddate1", ienddate1);
					map.put("enddate1", enddate1);
					listbatch.add(map);
					
					if(a%500==0){
						batchInsertRows(sql,listbatch);
						listbatch.clear();
					} 
					
//					if(a-50000>=0){
//						b=b+50000;
//						a=a-50000;
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_clinic_patient总数："+count*anlilist.size()+"-->有效数据："+b);
//						listbatch.clear();
//					}
//					if((i+1)==count){
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_clinic_patient总数："+count*anlilist.size()+"-->有效数据："+(b+a));
//						listbatch.clear();
//					}
					
					json.clear();
					json = null;
				}
			}
			if(listbatch.size()>0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}
			System.out.println("t_mc_clinic_patient总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_clinic_patient制造数据异常:"+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String caseid=map.get("caseid").toString();
				String ienddate1=map.get("ienddate1").toString();
				String enddate1=map.get("enddate1").toString();
				
				try{
					if(StringUtils.isBlank(Patient.getString("Sex"))){
						pst.setString(1,"");//sex
					}else{
						pst.setString(1,Patient.getString("Sex"));//sex
					}
					pst.setString(2,Patient.getString("DoctorName"));//doctorname
					pst.setString(3,Patient.getString("WeighKG"));//weight
					pst.setString(4,Patient.getString("InHospNo"));//clinicno
					pst.setString(5,"");//diagnosis
					pst.setString(6,Patient.getString("RenDamageDegree"));//ren_damage
					pst.setInt(7,0);//iage
					if(Integer.parseInt(Patient.getString("PatStatus"))==3){
						pst.setInt(8,1);//is_emergency
					}else{
						pst.setInt(8,0);//is_emergency
					}
					pst.setString(9,Patient.getString("HeightCM"));//height
					pst.setString(10,Patient.getString("PayClass"));//payclass
					pst.setString(11,Patient.getString("DeptCode"));//deptcode
					pst.setString(12,"门诊心电图_医疗组");//medgroupname
//					if(StringUtils.isBlank(Patient.getString("Age"))){
//						pst.setInt(13,0);//age
//					}else{
//						pst.setString(13,Patient.getString("Age"));//age
//					}
					pst.setString(13,Patient.getString("Age"));//age
					pst.setString(14,Patient.getString("IsLactation"));//is_lact
					pst.setString(15,Patient.getString("Birthday"));//birthdate
					pst.setString(16,Patient.getString("IDCard"));//identitycard
					pst.setString(17,Patient.getString("DeptName"));//deptname
					pst.setString(18,caseid);//caseid
					pst.setInt(19,Integer.parseInt(ienddate1));//ienddate
					pst.setString(20,"");//allergen
					pst.setString(21,Patient.getString("DoctorCode"));//doctorcode
					pst.setString(22,Patient.getString("HepDamageDegree"));//hep_damage
					pst.setInt(23,3207);//medgroupcode
					pst.setString(24,Patient.getString("IsPregnancy"));//is_preg
					pst.setInt(25,100);//cost
					pst.setString(26,Patient.getString("PregStartDate"));//preg_starttime
					pst.setString(27,"");//standby
					pst.setString(28,Patient.getString("Name"));//patientname
					pst.setString(29,Patient.getString("PatCode"));//patientid
					pst.setString(30,PassClient.getString("HospID"));//hiscode
					pst.setString(31,enddate1);//enddate
					pst.setString(32,Patient.getString("Telephone"));//telephone
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
		jdbcTemplate_sqlserver.batchUpdate(sql, setter);
	}
}
