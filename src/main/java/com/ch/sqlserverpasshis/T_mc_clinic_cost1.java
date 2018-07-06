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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_clinic_cost1 {
	@Autowired
	JdbcTemplate jdbcTemplate_sqlserver;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Sys_pa1 sys_pa;
	
	@Autowired
	Strisnull strisnull;
	
	public void clinic_cost(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String costtime){
		
		try {
			List list=null;
			List prescnolist=new ArrayList();
			String sql=null;
			List listbatch=new ArrayList();
			
			//-------插入非药品费用
			sql="insert into t_mc_clinic_cost (doctorname, clinicno, iid, itemcode, is_use, drugform, pharmacists, "
					+ "itemname, routecode, drugsccj, deptcode, medgroupname, itemnum, pharmacists_, deptname, "
					+ "caseid, doctorcode, drugindex, medgroupcode, cost, costtime, drugspec, patientid, prescno, "
					+ "hiscode, itemunit, costtype) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
//					a=a+1;
//					if(a%2000==0){
//						System.out.println("t_mc_clinic_cost 非药--"+a);
//					}
					iid=iid+1;
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject ScreenDrugList=json.getJSONObject("ScreenDrugList");
					JSONArray ScreenDrugs=ScreenDrugList.getJSONArray("ScreenDrugs");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Mz"+Patient.getString("PatCode");
				
					//病原学检测
					String itemcode=null;
					String itemname=null;
					List list_byx=null;
					int IsTestEtiology=0;
					if(Integer.parseInt(Patient.get("IsTestEtiology").toString())==1){
						IsTestEtiology=3;
					}
					String sql1="select a.itemcode,a.itemname from mc_dict_costitem a ,mc_hospital_match_relation b where "
							+ "a.match_scheme=b.costitemmatch_scheme and a.is_byx=? and b.hiscode_user=? order by a.itemcode asc";
					list_byx=jdbcTemplate.queryForList(sql1,new Object[]{IsTestEtiology,hiscode});
					Map byx=(Map)list_byx.get(0);
					itemcode=byx.get("itemcode").toString();
					itemname=byx.get("itemname").toString();
					
					//得到一个病人的所有的处方号，来制作非药品
					for(int k=0;k<ScreenDrugs.size();k++){
						JSONObject ScreenDrug=ScreenDrugs.getJSONObject(k);
						if(prescnolist.size()>0){
							boolean rig=false;
							for(int k1=0;k1<prescnolist.size();k1++){
								String prescnostr=prescnolist.get(k1).toString();
								if(ScreenDrug.get("RecipNo").equals(prescnostr)){
									rig=true;//表示存在重复的处方
								}
							}
							if(!rig){//表示存在重复的处方，不在新增
								prescnolist.add(ScreenDrug.get("RecipNo"));
							}
						}else{
							prescnolist.add(ScreenDrug.get("RecipNo"));
						}
					}
					
					if(prescnolist.size()>0){
						for(int k1=0;k1<prescnolist.size();k1++){
							a=a+1;
							if(a%2000==0){
								System.out.println("t_mc_clinic_cost 非药--"+a);
							}
							String prescnostr=prescnolist.get(k1).toString();
							Map map=new HashMap();
							map.put("iid", iid);
							map.put("Patient", Patient);
							map.put("PassClient", PassClient);
							map.put("costtime1", costtime1);
							map.put("caseid", caseid);
							map.put("prescnostr", prescnostr+"_非_1");//非药和药品不能再一个处方中
							map.put("itemcode",itemcode);
							map.put("itemname",itemname);
							listbatch.add(map);
							
							if(a%500==0){
								batchInsertRows(sql,listbatch);
								listbatch.clear();
							}
							
							break;
						}
						prescnolist.clear();
					}
					
//					if(a-50000>=0){
//						b=b+50000;
//						a=a-50000;
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_clinic_cost总数："+count*anlilist.size()+"-->有效数据："+b);
//						listbatch.clear();
//					}
//					if((i+1)==count){
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_clinic_cost总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_clinic_cost 非药总数："+a+"-->有效数据："+a);
			
			//-------插入药品费用
			sql="insert into t_mc_clinic_cost (doctorname, clinicno, iid, itemcode, is_use, drugform, pharmacists, "
					+ "itemname, routecode, drugsccj, deptcode, medgroupname, itemnum, pharmacists_, deptname, "
					+ "caseid, doctorcode, drugindex, medgroupcode, cost, costtime, drugspec, patientid, prescno, "
					+ "hiscode, itemunit, costtype) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			a=0;
//			b=0;
			iid=0;
			costtime1=costtime;
			ienddate1=ienddate;
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
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
					
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Mz"+Patient.getString("PatCode");
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
							System.out.println("t_mc_clinic_cost 药品--"+a);
						}
						
						
						Map map=new HashMap();
						map.put("drugname", drugname);
						map.put("drugspec", drugspec);
						map.put("drugform", drugform);
						map.put("comp_name", comp_name);
						map.put("doseunit", doseunit);
						map.put("drugcode", drugcode);
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
//							System.out.println("t_mc_clinic_cost总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows1(sql, listbatch);
//							System.out.println("t_mc_clinic_cost总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			System.out.println("t_mc_clinic_cost 药品总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_clinic_cost制造数据异常"+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
//				System.out.println(map);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient"));
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient"));
				int iid=Integer.parseInt(map.get("iid").toString());
				String caseid=map.get("caseid").toString();
				String costtime1=map.get("costtime1").toString();
				String prescnostr=map.get("prescnostr").toString();
				String itemcode=map.get("itemcode").toString();
				String itemname=map.get("itemname").toString();
				try{
					pst.setString(1,Patient.getString("DoctorName"));
					pst.setString(2,Patient.getString("InHospNo"));//门诊/住院号
					pst.setInt(3,iid);//自增长字段
					pst.setString(4,itemcode);//itemcode
					pst.setInt(5,1);//is_use
					pst.setString(6,"");//drugform
					pst.setString(7,"审核/调配药师");//pharmacists
					pst.setString(8,itemname);//itemname
					pst.setString(9,"");//routecode
					pst.setString(10,"");//drugsccj
					pst.setString(11,Patient.getString("DeptCode"));//deptcode
					pst.setString(12,"心血管内科_医疗组");//medgroupname
					pst.setInt(13,10);//itemnum
					pst.setString(14,"核对/发药药师");//pharmacists_
					pst.setString(15,Patient.getString("DeptName"));//deptname
					pst.setString(16,caseid);//caseid
					pst.setString(17,Patient.getString("DoctorCode"));//doctorcode
					pst.setInt(18,iid);//drugindex
					pst.setInt(19,196);//medgroupcode
					pst.setInt(20,100);//cost
					pst.setString(21,costtime1);//costtime
					pst.setString(22,"");//drugspec
					pst.setString(23,Patient.getString("PatCode"));//patientid
					if("".equals(strisnull.isnull(prescnostr))){
						pst.setInt(24,iid);//prescno
					}else{
						pst.setString(24,prescnostr);//prescno
					}
					pst.setString(25,PassClient.getString("HospID"));//hiscode
					pst.setString(26,"");//itemunit
					pst.setInt(27,3);//costtype
				}catch (Exception e){
					System.out.println("T_mc_clinic_cost出现异常的数据:"+map);
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
	
	public void batchInsertRows1(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i){
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
//				if(StringUtils.isBlank(drugcode)){
//					System.out.println("aaaaaaaaaa:"+map);
//				}
				try{
					pst.setString(1,ScreenDrug.getString("DoctorName"));
					pst.setString(2,Patient.getString("InHospNo"));//clinicno
					pst.setInt(3,iid);//自增长字段
					pst.setString(4,drugcode);//itemcode
					pst.setInt(5,1);//is_use
					pst.setString(6,drugform);//drugform
					pst.setString(7,ScreenDrug.getString("Pharmacists"));//pharmacists
					pst.setString(8,drugname);//itemname
					pst.setString(9,ScreenDrug.getString("RouteCode"));//routecode
					pst.setString(10,comp_name);//drugsccj
					pst.setString(11,ScreenDrug.getString("DeptCode"));//deptcode
					pst.setString(12,"心血管内科_医疗组");//medgroupname
					pst.setInt(13,10);//itemnum
					pst.setString(14,ScreenDrug.getString("Pharmacists_"));//pharmacists_
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
					pst.setString(23,Patient.getString("PatCode"));//patientid
					if("".equals(strisnull.isnull(ScreenDrug.getString("RecipNo")))){
						pst.setInt(24,iid);//prescno
					}else{
						pst.setString(24,ScreenDrug.getString("RecipNo"));//prescno
					}
					pst.setString(25,PassClient.getString("HospID"));//hiscode
					pst.setString(26,doseunit);//itemunit
					pst.setInt(27,1);//costtype
				}catch (Exception e){
					System.out.println("T_mc_clinic_cost出现异常的数据:"+map);
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
