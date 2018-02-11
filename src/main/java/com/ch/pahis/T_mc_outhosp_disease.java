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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_outhosp_disease {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	@Autowired
	JdbcTemplate jdbcTemplate_passpa2db;
	
	@Autowired
	Sys_pa sys_pa;
	public void outhosp_disease(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_outhosp_disease (discode, patientid, diseasetype, visitid, treatment, is_hospinfection, "
					+ "hiscode, disname, is_main, caseid) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			int iid=0;
			String ienddate1=ienddate;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				}
				for(int j=0;j<anlilist.size();j++){
					iid=iid+1;
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject ScreenMedCondList=json.getJSONObject("ScreenMedCondList");
					JSONArray ScreenMedConds=ScreenMedCondList.getJSONArray("ScreenMedConds");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Cy"+Patient.getString("PatCode");
					for(int k=0;k<ScreenMedConds.size();k++){
						JSONObject ScreenMedCond=ScreenMedConds.getJSONObject(k);
						if(StringUtils.isBlank(ScreenMedCond.getString("DiseaseName"))){
							continue;
						}
						a=a+1;
						if(a%2000==0){
							System.out.println("t_mc_outhosp_disease --"+a);
						}
						
						Map map=new HashMap();
						map.put("ScreenMedCond", ScreenMedCond);
						map.put("Patient", Patient);
						map.put("PassClient", PassClient);
						map.put("ScreenMedCond", ScreenMedCond);
						map.put("caseid", caseid);
						map.put("iid", iid);
						listbatch.add(map);
						
						if(a%500==0){
							batchInsertRows(sql,listbatch);
							listbatch.clear();
						} 
						
//						if(a-50000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_outhosp_disease总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_outhosp_disease总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_outhosp_disease总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_outhosp_disease制造数据异常");
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject ScreenMedCond=JSONObject.fromObject(map.get("ScreenMedCond"));
				JSONObject Patient=JSONObject.fromObject(map.get("Patient"));
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient"));
				int iid=Integer.parseInt(map.get("iid").toString());
				String caseid=map.get("caseid").toString();
				
				try{
					pst.setString(1,ScreenMedCond.getString("DiseaseCode"));//[discode
					pst.setString(2,Patient.getString("PatCode"));//patientid
					pst.setInt(3,0);//diseasetype
					pst.setString(4,Patient.getString("VisitCode"));//visitid
					pst.setInt(5,0);//treatment
					pst.setString(6,ScreenMedCond.getString("Ishospinfection"));//is_hospinfection
					pst.setString(7,PassClient.getString("HospID"));//hiscode
					pst.setString(8,ScreenMedCond.getString("DiseaseName"));//disname
					pst.setInt(9,1);//is_main
					pst.setString(10,caseid);//caseid]
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
