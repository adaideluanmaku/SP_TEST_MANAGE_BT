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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.DataBaseType;
import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.dao.SpringJdbc_sqlserver_his;
import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_clinic_cost {
	private static Logger log = Logger.getLogger(T_mc_clinic_cost.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	JdbcTemplate jdbcTemplate_dataBase=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Sys_pa sys_pa;
	
	@Autowired
	Strisnull strisnull;
	@Value("${data.insertdatacount}")
    private String insertdatacount;
	public void clinic_cost(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String costtime,int database1){
		jdbcTemplate_dataBase=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_dataBase==null){
			log.info("数据库连接失败");
			return;
		}
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
			JSONObject json=null;
			JSONObject PassClient=null;
			JSONObject Patient=null;
			JSONObject ScreenDrugList=null;
			JSONArray ScreenDrugs=null;
			String caseid=null;
			int IsTestEtiology=0;
			JSONObject ScreenDrug=null;
			String itemcode="";
			String itemname="";
			
			String sql1="select CONCAT(b.hiscode_user,',',a.is_byx) as hii,a.itemcode,a.itemname from mc_dict_costitem a ,mc_hospital_match_relation b where "
					+ "a.match_scheme=b.costitemmatch_scheme order by a.itemcode asc";
			List list_byx=jdbcTemplate.queryForList(sql1);
			
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
					json=JSONObject.fromObject(anlilist.get(j));
					PassClient=json.getJSONObject("PassClient");
					Patient=json.getJSONObject("Patient");
					ScreenDrugList=json.getJSONObject("ScreenDrugList");
					ScreenDrugs=ScreenDrugList.getJSONArray("ScreenDrugs");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					caseid="Mz"+Patient.getString("PatCode");
				
					//病原学检测
					itemcode="";
					itemname="";
//					list_byx=null;
					IsTestEtiology=0;
					if(Integer.parseInt(Patient.get("IsTestEtiology").toString())==1){
						IsTestEtiology=3;
					}
//					sql1="select a.itemcode,a.itemname from mc_dict_costitem a ,mc_hospital_match_relation b where "
//							+ "a.match_scheme=b.costitemmatch_scheme and a.is_byx=? and b.hiscode_user=? order by a.itemcode asc";
//					list_byx=jdbcTemplate.queryForList(sql1,new Object[]{IsTestEtiology,hiscode});
//					if(list_byx.size()>0){
//						Map byx=(Map)list_byx.get(0);
//						itemcode=byx.get("itemcode").toString();
//						itemname=byx.get("itemname").toString();
//					}
					for(int k=0;k<list_byx.size();k++){
						Map byx=(Map)list_byx.get(k);
						if(byx.get("hii").toString().equals(hiscode+","+IsTestEtiology)){
							itemcode=byx.get("itemcode").toString();
							itemname=byx.get("itemname").toString();
							break;
						}
					}
					
					//得到一个病人的所有的处方号，来制作非药品
					for(int k=0;k<ScreenDrugs.size();k++){
						ScreenDrug=ScreenDrugs.getJSONObject(k);
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
							
							if(a%Integer.parseInt(insertdatacount)==0){
								batchInsertRows(sql,listbatch);
								log.info("======>t_mc_clinic_cost 非药:"+a);
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
			log.info("======>t_mc_clinic_cost 非药总数："+a+"-->有效数据："+a);
			
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
			String drugspec="";
			String drugform="";
			String comp_name="";
			String doseunit="";
			String drugcode="";
			String drugname="";
			String hdd="";
			sql1="select CONCAT(b.hiscode_user,',',a.drug_unique_code,',',a.doseunit) as hdd ,a.drugname,a.drugcode, "
					+ "a.drugspec,a.drugform,a.comp_name,a.doseunit from mc_dict_drug_pass a, "
					+ "mc_hospital_match_relation b where  a.match_scheme= b.drugmatch_scheme " ;
			List list_drug_pass=jdbcTemplate.queryForList(sql1);
			
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
			        ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
			        costtime1=sys_pa.date1(costtime1, "yyyy-MM-dd HH:mm:ss");
				}
				for(int j=0;j<anlilist.size();j++){
					iid=iid+1;
					json=JSONObject.fromObject(anlilist.get(j));
					PassClient=json.getJSONObject("PassClient");
					Patient=json.getJSONObject("Patient");
					ScreenDrugList=json.getJSONObject("ScreenDrugList");
					ScreenDrugs=ScreenDrugList.getJSONArray("ScreenDrugs");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
					
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					caseid="Mz"+Patient.getString("PatCode");
					for(int k=0;k<ScreenDrugs.size();k++){
						//字典表找数据
						ScreenDrug=ScreenDrugs.getJSONObject(k);
						hdd=hiscode+","+ScreenDrug.getString("DrugUniqueCode")+","+ScreenDrug.getString("DoseUnit");
//						sql1="select b.hiscode_user,a.drugname,a.drugcode,a.drugspec,a.drugform,a.comp_name,a.doseunit from " + 
//								"mc_dict_drug_pass a,mc_hospital_match_relation b where  a.match_scheme= b.drugmatch_scheme " + 
//								"and a.drug_unique_code=? and b.hiscode_user=? and a.doseunit=? ";
//						List list_drug_pass=jdbcTemplate.queryForList(sql1,new Object[]{
//								ScreenDrug.getString("DrugUniqueCode"),hiscode,ScreenDrug.getString("DoseUnit")});
						drugspec="";
						drugform="";
						comp_name="";
						doseunit="";
						drugcode="";
						drugname="";
						for(int k1=0;k1<list_drug_pass.size();k1++){
							if(StringUtils.isNotBlank(drugcode)){
								break;
							}
							Map map=(Map)list_drug_pass.get(k1);
							if(map.get("hdd").equals(hdd)){
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
						}
						if(StringUtils.isBlank(drugcode)){
							log.info("======>未找到字典表药品数据,病人姓名：:"+Patient.get("Name"));
							continue;
						}
						a=a+1;
						
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
						
						if(a%Integer.parseInt(insertdatacount)==0){
							batchInsertRows1(sql,listbatch);
							log.info("======>t_mc_clinic_cost 药品:"+a);
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
			log.info("======>t_mc_clinic_cost 药品总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("调试==>t_mc_clinic_cost制造数据异常："+e);
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
				String itemcode=strisnull.isnull(map.get("itemcode"));
				String itemname=strisnull.isnull(map.get("itemname"));
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
					log.debug("调试==>t_mc_clinic_cost插表异常："+map);
					log.debug("调试==>"+e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_dataBase.batchUpdate(sql, setter);
		
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
					log.debug("调试==>t_mc_clinic_cost插表异常："+map);
					log.debug("调试==>"+e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_dataBase.batchUpdate(sql, setter);
	}
	
}
