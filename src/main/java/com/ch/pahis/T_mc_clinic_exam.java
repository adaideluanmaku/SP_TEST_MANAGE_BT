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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_clinic_exam {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	@Autowired
	JdbcTemplate jdbcTemplate_passpa2db;
	
	@Autowired
	Sys_pa sys_pa;
	public void clinic_exam(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String startdate){
		try {
			List list=null;
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_clinic_exam (ienddate, doctorname, bodypart, doctorcode, examresult, clinicno, "
					+ "instrument, requestno, patientid, reporttime, hiscode, examname, examcode, caseid) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			String startdate1=startdate;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
			        ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
			        startdate1=sys_pa.date2(startdate, "yyyy-MM-dd HH:mm:ss",i,sum_date);
				}
				for(int j=0;j<anlilist.size();j++){
					a=a+1;
					if(a%2000==0){
						System.out.println("t_mc_clinic_exam --"+a);
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
					map.put("ienddate1", ienddate1);
					map.put("Patient", Patient);
					map.put("startdate1", startdate1);
					map.put("PassClient", PassClient);
					map.put("caseid", caseid);
					listbatch.add(map);
					
					if(a%500==0){
						batchInsertRows(sql,listbatch);
						listbatch.clear();
					} 
					
//					if(a-50000>=0){
//						b=b+50000;
//						a=a-50000;
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_clinic_exam总数："+count*anlilist.size()+"-->有效数据："+b);
//						listbatch.clear();
//					}
//					if((i+1)==count){
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_clinic_exam总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_clinic_exam总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_clinic_exam制造数据异常");
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				String ienddate1=map.get("ienddate1").toString();
				JSONObject Patient=JSONObject.fromObject(map.get("Patient"));
				String startdate1=map.get("startdate1").toString();
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient"));
				String caseid=map.get("caseid").toString();
				
				try{
					pst.setInt(1,Integer.parseInt(ienddate1));
					pst.setString(2,Patient.getString("DoctorName"));
					pst.setString(3,"检查部位");//bodypart
					pst.setString(4,Patient.getString("DoctorCode"));
					pst.setString(5,"检查结果");//examresult
					pst.setString(6,Patient.getString("InHospNo"));//clinicno
					pst.setString(7,"检查仪器");//instrument
					pst.setString(8,"申请单号");//requestno
					pst.setString(9,Patient.getString("PatCode"));//patientid
					pst.setString(10,startdate1);//reporttime
					pst.setString(11,PassClient.getString("HospID"));
					pst.setString(12,"普通透射电镜检查与诊断组套");//examname
					pst.setString(13,"020050925006N");//examcode
					pst.setString(14,caseid);
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
