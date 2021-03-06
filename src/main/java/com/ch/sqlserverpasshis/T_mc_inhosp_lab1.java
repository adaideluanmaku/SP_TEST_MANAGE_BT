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
public class T_mc_inhosp_lab1 {
	@Autowired
	JdbcTemplate jdbcTemplate_sqlserver;
	
	@Autowired
	Sys_pa1 sys_pa;
	public void inhosp_lab(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String startdate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_inhosp_lab (ienddate, doctorname, samplingtime, doctorcode, labcode, "
					+ "resultflag, instrument, itemcode, requestno, unit, sampletype, range_0, patientid, "
					+ "reporttime, visitid, itemname, labresult, hiscode, labname, caseid) values(?,?,?,?,?,?,?,?,?,?,?,?"
					+ ",?,?,?,?,?,?,?,?)";
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
						System.out.println("t_mc_inhosp_lab --"+a);
					}
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_zy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_住院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Zy"+Patient.getString("PatCode");

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
//						System.out.println("t_mc_inhosp_lab总数："+count*anlilist.size()+"-->有效数据："+b);
//						listbatch.clear();
//					}
//					if((i+1)==count){
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_inhosp_lab总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_inhosp_lab总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_inhosp_lab制造数据异常");
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
					pst.setString(3,startdate1);//samplingtime
					pst.setString(4,Patient.getString("DoctorCode"));
					pst.setString(5,"0200509240004");//labcode
					pst.setString(6,"H");//resultflag
					pst.setString(7,"检验仪器");//instrument
					pst.setString(8,"01.0101");//itemcode
					pst.setString(9,"20140401000001");//requestno
					pst.setString(10,"10^9/L");//unit
					pst.setString(11,"样本类型");//sampletype
					pst.setString(12,"22.0~55.0");//range_0
					pst.setString(13,Patient.getString("PatCode"));//patientid
					pst.setString(14,startdate1);//reporttime
					pst.setString(15,Patient.getString("VisitCode"));//visitid
					pst.setString(16,"脑池穿刺术");//itemname
					pst.setString(17,"检验结果");//labresult
					pst.setString(18,PassClient.getString("HospID"));//hiscode
					pst.setString(19,"血清总胆固醇测定");//labname
					pst.setString(20,caseid);//caseid
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
