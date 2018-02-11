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
public class T_mc_outhosp_order {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	@Autowired
	JdbcTemplate jdbcTemplate_passpa2db;
	
	@Autowired
	Sys_pa sys_pa;
	public void outhosp_order(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String startdate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_outhosp_order (grouptag, orderstatus, doctorname, is_temp, remark, pa_enddatetime, "
					+ "orderno, wardcode, purpose, singledose, frequency, drugform, is_out, visitid, routecode, "
					+ "deptcode, ordercode, deptname, caseid, reasonable_desc, doctorcode, meditime, drug_unique_code, "
					+ "startdatetime, ordertype, routename, enddatetime, cid, drugspec, executetime, wardname, "
					+ "patientid, hiscode, ordername, groupstate, doseunit, is_allow) values(?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			int iid=0;
			int cid=0;
			String ienddate1=ienddate;
			String startdate1=startdate;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
					startdate1=sys_pa.date2(startdate, "yyyy-MM-dd HH:mm:ss",i,sum_date);
				}
				for(int j=0;j<anlilist.size();j++){
					iid=iid+1;
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject ScreenDrugList=json.getJSONObject("ScreenDrugList");
					JSONArray ScreenDrugs=ScreenDrugList.getJSONArray("ScreenDrugs");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Cy"+Patient.getString("PatCode");
					
					for(int k=0;k<ScreenDrugs.size();k++){
						cid=cid+1;
						//字典表找数据
						JSONObject ScreenDrug=ScreenDrugs.getJSONObject(k);
						String sql1="select drugname,drugcode,drugspec,drugform,comp_name,doseunit from "
								+ "mc_dict_drug_pass where drug_unique_code=? and match_scheme= "
								+ "(select drugmatch_scheme from mc_hospital_match_relation where  hiscode_user=? ) "
								+ "and doseunit=?";
						List list_drug_pass=jdbcTemplate_passpa2db.queryForList(sql1,new Object[]{
								ScreenDrug.getString("DrugUniqueCode"),hiscode,ScreenDrug.getString("DoseUnit")});
						String drugspec="";
						String drugform="";
						String comp_name="";
						String doseunit="";
						String drugcode="";
						for(int k1=0;k1<list_drug_pass.size();k1++){
							if(StringUtils.isNotBlank(drugcode)){
//								cid=cid+1;
								break;
							}
							Map map=(Map)list_drug_pass.get(k1);
							if(map.get("drugspec")!=null){
								drugspec=map.get("drugspec").toString();
							}
							if(map.get("drugform")!=null){
								drugform=map.get("drugform").toString();
							}
							if(map.get("comp_name")!=null){
								comp_name=map.get("comp_name").toString();
							}
							if(map.get("doseunit")!=null){
								doseunit=map.get("doseunit").toString();
							}
							if(map.get("drugcode")!=null){
								drugcode=map.get("drugcode").toString();
							}
						}
						if(StringUtils.isBlank(drugcode)){
							System.out.println("未找到字典表药品数据,病人姓名："+Patient.get("Name"));
							continue;
						}
						a=a+1;
						if(a%2000==0){
							System.out.println("t_mc_outhosp_order --"+a);
						}
						
						Map map=new HashMap();
						map.put("drugspec", drugspec);
						map.put("drugform", drugform);
						map.put("comp_name", comp_name);
						map.put("doseunit", doseunit);
						map.put("drugcode", drugcode);
						map.put("ScreenDrug", ScreenDrug);
						map.put("Patient", Patient);
						map.put("caseid", caseid);
						map.put("PassClient", PassClient);
						map.put("iid", iid);
						map.put("cid", cid);
						map.put("startdate1", startdate1);
						listbatch.add(map);
						
						if(a%500==0){
							batchInsertRows(sql,listbatch);
							listbatch.clear();
						} 
//						if(a-50000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_outhosp_order总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_outhosp_order总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_outhosp_order总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_outhosp_order制造数据异常");
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				String drugspec=map.get("drugspec").toString();
				String drugform=map.get("drugform").toString();
				String comp_name=map.get("comp_name").toString();
				String doseunit=map.get("doseunit").toString();
				String drugcode=map.get("drugcode").toString();
				JSONObject ScreenDrug=JSONObject.fromObject(map.get("ScreenDrug").toString());
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String caseid=map.get("caseid").toString();
				int iid=Integer.parseInt(map.get("iid").toString());
				int cid=Integer.parseInt(map.get("cid").toString());
				String startdate1=map.get("startdate1").toString();
				
				try{
					pst.setString(1,ScreenDrug.getString("GroupTag"));//[grouptag
					pst.setInt(2,1);//orderstatus
					pst.setString(3,ScreenDrug.getString("DoctorName"));//doctorname
					pst.setString(4,ScreenDrug.getString("IsTempDrug"));//is_temp
					pst.setString(5,"");//remark
					pst.setString(6,startdate1);//pa_enddatetime
					pst.setString(7,ScreenDrug.getString("Index"));//orderno
					pst.setString(8,Patient.getString("DeptCode"));//wardcode
					pst.setInt(9,Integer.parseInt(ScreenDrug.getString("Purpose")));//purpose
					pst.setString(10,ScreenDrug.getString("DosePerTime"));//singledose
					pst.setString(11,ScreenDrug.getString("Frequency"));//frequency
					pst.setString(12,drugform);//drugform
					pst.setInt(13,0);//is_out,如果是出院带药1的话，会导致PASS不审
					pst.setString(14,Patient.getString("VisitCode"));//visitid
					pst.setString(15,ScreenDrug.getString("RouteCode"));//routecode
					pst.setString(16,ScreenDrug.getString("DeptCode"));//deptcode
					pst.setString(17,drugcode);//ordercode
					pst.setString(18,ScreenDrug.getString("DeptName"));//deptname
					pst.setString(19,caseid);//caseid
					pst.setString(20,"合理越权描述");//reasonable_desc
					pst.setString(21,ScreenDrug.getString("DoctorCode"));//doctorcode
					pst.setInt(22,0);//meditime
					pst.setString(23,ScreenDrug.getString("DrugUniqueCode"));//drug_unique_code
					pst.setString(24,ScreenDrug.getString("StartTime"));//startdatetime
					pst.setInt(25,1);//ordertype
					pst.setString(26,ScreenDrug.getString("RouteName"));//routename
					pst.setString(27,ScreenDrug.getString("EndTime"));//enddatetime
					pst.setInt(28,cid);//cid
					pst.setString(29,drugspec);//drugspec
					pst.setString(30,ScreenDrug.getString("ExecuteTime"));//executetime
					pst.setString(31,Patient.getString("DeptName"));//wardname
					pst.setString(32,Patient.getString("PatCode"));//patientid
					pst.setString(33,PassClient.getString("HospID"));//hiscode
					pst.setString(34,ScreenDrug.getString("DrugName"));//ordername
					pst.setInt(35,1);//groupstate，特意将出院的数据置成有用药理由
					pst.setString(36,doseunit);//doseunit
					pst.setInt(37,1);//is_allow]
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
