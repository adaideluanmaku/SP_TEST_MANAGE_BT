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

import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_inhosp_operation {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	@Autowired
	Sys_pa sys_pa;
	@Autowired
	Strisnull strisnull;
	
	public void inhosp_operation(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String enddate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_inhosp_operation (doctorname, doctorcode, startdate, operationname, patientid, visitid, "
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
//					iid=iid+1;
//					a=a+1;
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject ScreenOperationList=json.getJSONObject("ScreenOperationList");
					JSONArray ScreenOperations=ScreenOperationList.getJSONArray("ScreenOperations");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_zy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_住院_"+Patient.getString("InHospNo"));
					
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Zy"+Patient.getString("PatCode");
					
					for(int k=0;k<ScreenOperations.size();k++){
						JSONObject ScreenOperation=ScreenOperations.getJSONObject(k);
						if(StringUtils.isBlank(ScreenOperation.getString("OprName"))){
							continue;
						}
						iid=iid+1;
						a=a+1;
						if(a%2000==0){
							System.out.println("t_mc_inhosp_operation --"+a);
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
//							System.out.println("t_mc_inhosp_operation总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_inhosp_operation总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_inhosp_operation总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_inhosp_operation制造数据异常");
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
					pst.setString(11,strisnull.isnulltostr(ScreenOperation.getString("OprEndDate"),enddate1));//enddate
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
		jdbcTemplate_oracle.batchUpdate(sql, setter);
	}
}
