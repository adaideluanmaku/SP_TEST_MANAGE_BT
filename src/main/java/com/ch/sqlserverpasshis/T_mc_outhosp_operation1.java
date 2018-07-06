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

import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_outhosp_operation1 {
	@Autowired
	JdbcTemplate jdbcTemplate_sqlserver;
	
	@Autowired
	Sys_pa1 sys_pa;
	@Autowired
	Strisnull strisnull;
	
	public void outhosp_operation(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String enddate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_outhosp_operation (doctorname, doctorcode, startdate, operationname, patientid, visitid, "
					+ "deptcode, incisiontype, oprid, hiscode, enddate, deptname, operationcode, caseid) values(?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			int iid=0;
			String ienddate1=ienddate;
			String enddate1=enddate;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
					 enddate1=sys_pa.date1(enddate1, "yyyy-MM-dd");
				}
				for(int j=0;j<anlilist.size();j++){
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject ScreenOperationList=json.getJSONObject("ScreenOperationList");
					JSONArray ScreenOperations=ScreenOperationList.getJSONArray("ScreenOperations");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Cy"+Patient.getString("PatCode");
					
					//设置数据时间偏移量
					JSONObject ScreenDrugList=json.getJSONObject("ScreenDrugList");
					JSONArray ScreenDrugs=ScreenDrugList.getJSONArray("ScreenDrugs");
					String starttimeaa="";
					String starttimebb="";
					int timesum=0;
					//设置开药时间为出院前7天
					starttimeaa = sys_pa.date4(enddate1, "yyyy-MM-dd", -7);
					for(int i1=0;i1<ScreenDrugs.size();i1++){
						//字典表找数据
						JSONObject ScreenDrug=ScreenDrugs.getJSONObject(i1);
						if(StringUtils.isBlank(ScreenDrug.getString("StartTime"))){
							continue;
						}else{
							starttimebb = ScreenDrug.getString("StartTime");
							break;
						}
					}
					
					//计算原开药时间和新开药时间的中间间隔时间
					if(StringUtils.isNotBlank(starttimebb)){
						timesum = sys_pa.differentDaysByMillisecond(starttimeaa.substring(0, 10),starttimebb.substring(0, 10),"yyyy-MM-dd");
					}
					
					for(int k=0;k<ScreenOperations.size();k++){
						JSONObject ScreenOperation=ScreenOperations.getJSONObject(k);
						if(StringUtils.isBlank(ScreenOperation.getString("OprName"))){
							continue;
						}
						iid=iid+1;
						a=a+1;
						if(a%2000==0){
							System.out.println("t_mc_outhosp_operation --"+a);
						}
						
						//针对出院的病人，需要根据住院的时间段，来调整手术时间
						String OprStartDate=ScreenOperation.getString("OprStartDate");
						String OprEndDate=ScreenOperation.getString("OprEndDate");
						if(StringUtils.isNotBlank(OprStartDate)){
							if(OprStartDate.length()<19){
								OprStartDate=OprStartDate+" 01:01:01";
							}
							
							ScreenOperation.put("OprStartDate", sys_pa.date4(OprStartDate.substring(0,10), "yyyy-MM-dd", timesum)+" "+OprStartDate.substring(11,19));
						}
						
						if(StringUtils.isNotBlank(OprEndDate)){
							if(OprEndDate.length()<19){
								OprEndDate=OprEndDate+" 01:01:01";
							}
							ScreenOperation.put("OprEndDate", sys_pa.date4(OprEndDate.substring(0,10), "yyyy-MM-dd", timesum)+" "+OprEndDate.substring(11,19));
						}
						
						Map map=new HashMap();
						map.put("Patient", Patient);
						map.put("PassClient", PassClient);
						map.put("iid", iid);
						map.put("caseid", caseid);
						map.put("ScreenOperation", ScreenOperation);
						map.put("enddate1", enddate1);
						
						listbatch.add(map);
						
						if(a%500==0){
							batchInsertRows(sql,listbatch);
							listbatch.clear();
						} 
						
//						if(a-50000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_outhosp_operation总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_outhosp_operation总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_outhosp_operation总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_outhosp_operation制造数据异常");
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject ScreenOperation=JSONObject.fromObject(map.get("ScreenOperation").toString());
				String caseid=map.get("caseid").toString();
				int iid=Integer.parseInt(map.get("iid").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String enddate1=map.get("enddate1").toString();
				try{
					pst.setString(1,Patient.getString("DoctorName"));//[doctorname
					pst.setString(2,Patient.getString("DoctorCode"));//doctorcode
					pst.setString(3,ScreenOperation.getString("OprStartDate"));//startdate
					pst.setString(4,ScreenOperation.getString("OprName"));//operationname
					pst.setString(5,Patient.getString("PatCode"));//patientid
					pst.setString(6,Patient.getString("VisitCode"));//visitid
					pst.setString(7,Patient.getString("DeptCode"));//deptcode
					pst.setInt(8,Integer.parseInt(strisnull.isnulltostr(ScreenOperation.getString("IncisionType"),"1")));//incisiontype
					pst.setInt(9,iid);//oprid
					pst.setString(10,PassClient.getString("HospID"));//hiscode
					//出院手术必须要有enddate
					pst.setString(11,strisnull.isnulltostr(ScreenOperation.getString("OprEndDate"),enddate1));//enddate
//					pst.setString(11,ScreenOperation.getString("OprEndDate"));//enddate
					
					pst.setString(12,Patient.getString("DeptName"));//deptname
					pst.setString(13,ScreenOperation.getString("OprCode"));//operationcode
					pst.setString(14,caseid);//caseid]
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
