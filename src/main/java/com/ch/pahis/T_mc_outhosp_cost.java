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

import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_outhosp_cost {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Sys_pa sys_pa;
	
	@Autowired
	Strisnull strisnull;
	
	public void outhosp_cost(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String costtime){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			//-------插入非药品费用
			sql="insert into t_mc_outhosp_cost (doctorname, iid, wardcode, itemcode, is_use, drugform, is_out, routecode, "
					+ "itemname, visitid, drugsccj, deptcode, medgroupname, itemnum, deptname, caseid, doctorcode, "
					+ "drugindex, medgroupcode, cost, costtime, drugspec, wardname, patientid, hiscode, itemunit, "
					+ "costtype) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			int iid=0;
			String ienddate1=ienddate;
			String costtime1=costtime;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					 ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				     costtime1=sys_pa.date1(costtime1, "yyyy-MM-dd HH:mm:ss");
				}
				for(int j=0;j<anlilist.size();j++){
					a=a+1;
					iid=iid+1;
					if(a%2000==0){
						System.out.println("t_mc_outhosp_cost 非药--"+a);
					}
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Cy"+Patient.getString("PatCode");
				
					//病原学检测
					String itemcode=null;
					String itemname=null;
					List list_byx=null;
					int IsTestEtiology=0;
					if(Integer.parseInt(Patient.get("IsTestEtiology").toString())==1){
						IsTestEtiology=3;
					}
					String sql1="select a.itemcode,a.itemname from mc_dict_costitem a ,mc_hospital_match_relation b where "
							+ "a.match_scheme=b.costitemmatch_scheme and a.is_byx=? and b.hiscode_user=?  order by a.itemcode asc";
					list_byx=jdbcTemplate.queryForList(sql1,new Object[]{IsTestEtiology,hiscode});
					Map byx=(Map)list_byx.get(0);
					itemcode=byx.get("itemcode").toString();
					itemname=byx.get("itemname").toString();
					
					Map map=new HashMap();
					map.put("iid", iid);
					map.put("Patient", Patient);
					map.put("PassClient", PassClient);
					map.put("costtime1", costtime1);
					map.put("caseid", caseid);
					map.put("itemcode",itemcode);
					map.put("itemname",itemname);
					listbatch.add(map);
					
					if(a%500==0){
						batchInsertRows(sql,listbatch);
						listbatch.clear();
					} 
//					if(a-50000>=0){
//						b=b+50000;
//						a=a-50000;
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_outhosp_cost总数："+count*anlilist.size()+"-->有效数据："+b);
//						listbatch.clear();
//					}
//					if((i+1)==count){
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_outhosp_cost总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_outhosp_cost 非药总数："+a+"-->有效数据："+a);
			
			
			//-------插入药品费用
			sql="insert into t_mc_outhosp_cost (doctorname, iid, wardcode, itemcode, is_use, drugform, is_out, routecode, "
					+ "itemname, visitid, drugsccj, deptcode, medgroupname, itemnum, deptname, caseid, doctorcode, "
					+ "drugindex, medgroupcode, cost, costtime, drugspec, wardname, patientid, hiscode, itemunit, "
					+ "costtype) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			a=0;
//			b=0;
			iid=0;
			ienddate1=ienddate;
			costtime1=costtime;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					 ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				     costtime1=sys_pa.date1(costtime1, "yyyy-MM-dd HH:mm:ss");
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
						//字典表找数据
						JSONObject ScreenDrug=ScreenDrugs.getJSONObject(k);
						String sql1="select drugname,drugcode,drugspec,drugform,comp_name,doseunit from "
								+ "mc_dict_drug_pass where drug_unique_code=? and match_scheme= "
								+ "(select drugmatch_scheme from mc_hospital_match_relation where  hiscode_user=? ) "
								+ "and doseunit=?";
						List list_drug_pass=jdbcTemplate.queryForList(sql1,new Object[]{
								ScreenDrug.getString("DrugUniqueCode"),hiscode,ScreenDrug.getString("DoseUnit")});
						String drugspec="";
						String drugform="";
						String comp_name="";
						String doseunit="";
						String drugcode="";
						String drugname="";
						for(int k1=0;k1<list_drug_pass.size();k1++){
							if(StringUtils.isNotBlank(drugcode)){
								break;
							}
							Map map=(Map)list_drug_pass.get(k1);
							if(map.get("drugname")!=null){
								drugname=map.get("drugname").toString();
							}
							if(map.get("drugcode")!=null){
								drugcode=map.get("drugcode").toString();
							}
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
						}
						if(StringUtils.isBlank(drugcode)){
							System.out.println("未找到字典表药品数据,病人姓名："+Patient.get("Name"));
							continue;
						}
						a=a+1;
						
						if(a%2000==0){
							System.out.println("t_mc_outhosp_cost 药品--"+a);
						}
						
						Map map=new HashMap();
						map.put("drugname", drugname);
						map.put("drugcode", drugcode);
						map.put("drugspec", drugspec);
						map.put("drugform", drugform);
						map.put("comp_name", comp_name);
						map.put("doseunit", doseunit);
						map.put("Patient", Patient);
						map.put("ScreenDrug", ScreenDrug);
						map.put("costtime1", costtime1);
						map.put("iid", iid);
						map.put("caseid", caseid);
						map.put("PassClient", PassClient);
						listbatch.add(map);
						
						if(a%500==0){
							batchInsertRows1(sql,listbatch);
							listbatch.clear();
						} 
						
//						if(a-50000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows1(sql, listbatch);
//							System.out.println("t_mc_outhosp_cost总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows1(sql, listbatch);
//							System.out.println("t_mc_outhosp_cost总数："+count*anlilist.size()+"-->有效数据："+(b+a));
//							listbatch.clear();
//						}
					}
					
					json.clear();
					json = null;
				}
			}
			if(listbatch.size()>0){
				batchInsertRows1(sql,listbatch);
				listbatch.clear();
			}
			System.out.println("t_mc_outhosp_cost 药品总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_outhosp_cost制造数据异常"+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient"));
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient"));
				int iid=Integer.parseInt(map.get("iid").toString());
				String caseid=map.get("caseid").toString();
				String costtime1=map.get("costtime1").toString();
				String itemcode=map.get("itemcode").toString();
				String itemname=map.get("itemname").toString();
				
				try{
					pst.setString(1,Patient.getString("DoctorName"));//[doctorname
					pst.setInt(2,iid);//自增长字段
					pst.setString(3,Patient.getString("DeptCode"));//wardcode
					pst.setString(4,itemcode);//itemcode
					pst.setInt(5,1);//is_use
					pst.setString(6,"");//drugform
					pst.setInt(7,0);//is_out，和药品表对应
					pst.setString(8,"");//routecode
					pst.setString(9,itemname);//itemname
					pst.setString(10,Patient.getString("VisitCode"));//visitid
					pst.setString(11,"");//drugsccj
					pst.setString(12,Patient.getString("DeptCode"));//deptcode
					pst.setString(13,"心血管内科_医疗组");//medgroupname
					pst.setInt(14,10);//itemnum
					pst.setString(15,Patient.getString("DeptName"));//deptname
					pst.setString(16,caseid);//caseid
					pst.setString(17,Patient.getString("DoctorCode"));//doctorcode
					pst.setInt(18,iid);//drugindex
					pst.setInt(19,196);//medgroupcode
					pst.setInt(20,100);//cost
					pst.setString(21,costtime1);//costtime
					pst.setString(22,"");//drugspec
					pst.setString(23,Patient.getString("DeptName"));//wardname
					pst.setString(24,Patient.getString("PatCode"));//patientid
					pst.setString(25,PassClient.getString("HospID"));//hiscode
					pst.setString(26,"");//itemunit
					pst.setInt(27,3);//costtype
				}catch (Exception e){
					System.out.println("T_mc_outhosp_cost出现异常的数据:"+map);
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
	
	public void batchInsertRows1(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				String drugname=map.get("drugname").toString();
				String drugspec=map.get("drugspec").toString();
				String drugform=map.get("drugform").toString();
				String comp_name=map.get("comp_name").toString();
				String doseunit=map.get("doseunit").toString();
				String drugcode=map.get("drugcode").toString();
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject ScreenDrug=JSONObject.fromObject(map.get("ScreenDrug").toString());
				String costtime1=map.get("costtime1").toString();
				int iid=Integer.parseInt(map.get("iid").toString());
				String caseid=map.get("caseid").toString();
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				
				try{
					pst.setString(1,ScreenDrug.getString("DoctorName"));//doctorname
					pst.setInt(2,iid);//自增长字段
					pst.setString(3,Patient.getString("DeptCode"));//wardcode
					pst.setString(4,drugcode);//itemcode
					pst.setInt(5,1);//is_use
					pst.setString(6,drugform);//drugform
					pst.setInt(7,0);//is_out，和药品表对应
					pst.setString(8,ScreenDrug.getString("RouteCode"));//routecode
					pst.setString(9,drugname);//itemname
					pst.setString(10,Patient.getString("VisitCode"));//visitid
					pst.setString(11,comp_name);//drugsccj
					pst.setString(12,ScreenDrug.getString("DeptCode"));//deptcode
					pst.setString(13,"心血管内科_医疗组");//medgroupname
					pst.setInt(14,10);//itemnum
					pst.setString(15,ScreenDrug.getString("DeptName"));//deptname
					pst.setString(16,caseid);//caseid
					pst.setString(17,ScreenDrug.getString("DoctorCode"));//doctorcode
					if("".equals(strisnull.isnull(ScreenDrug.getString("Index")))){
						pst.setInt(18,iid);//drugindex
					}else{
						pst.setString(18,ScreenDrug.getString("Index"));//drugindex
					}
					pst.setInt(19,196);//medgroupcode
					if(StringUtils.isBlank(ScreenDrug.getString("Cost"))){
						pst.setInt(20,100);//cost
					}else{
						pst.setString(20,ScreenDrug.getString("Cost"));//cost
					}
					pst.setString(21,costtime1);//costtime
					pst.setString(22,drugspec);//drugspec
					pst.setString(23,Patient.getString("DeptName"));//wardname
					pst.setString(24,Patient.getString("PatCode"));//patientid
					pst.setString(25,PassClient.getString("HospID"));//hiscode
					pst.setString(26,doseunit);//itemunit
					pst.setInt(27,1);//costtype
				}catch (Exception e){
					System.out.println("T_mc_outhosp_cost出现异常的数据:"+map);
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
