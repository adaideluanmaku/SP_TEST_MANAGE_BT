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
public class T_mc_clinic_order {
	private static Logger log = Logger.getLogger(T_mc_clinic_order.class);
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
	public void clinic_order(int trunca, int count, int sum_date,List anlilist,String hiscode,
			String ienddate,int database1){
		jdbcTemplate_dataBase=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_dataBase==null){
			log.info("数据库连接失败");
			return;
		}
//		int cid=cidin;
		try {
			String sql=null;
			List listbatch=new ArrayList();
			List prescnolist=new ArrayList();
			
			//插入非药品数据
			sql="insert into t_mc_clinic_order (grouptag, orderstatus, doctorname, remark, clinicno, orderno, purpose,"
					+ " singledose, frequency, drugform, routecode, deptcode, ordercode, deptname, caseid, "
					+ "reasonable_desc, doctorcode, drug_unique_code, startdatetime, ordertype, presctype, "
					+ "routename, cost, cid, drugspec, num, days, patientid, prescno, hiscode, ordername, "
					+ "groupstate, doseunit, numunit, is_allow) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
					+ ",?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			int iid=0;
			String cid="";
			int cidno=0;
			String ienddate1=ienddate;
			String costtime1=ienddate;
			JSONObject json=null;
			JSONObject PassClient=null;
			JSONObject Patient=null;
			JSONObject ScreenDrugList=null;
			JSONArray ScreenDrugs=null;
			String itemcode="";
			String itemname="";
			
			String sql1="select CONCAT(b.hiscode_user,',',a.is_byx,',',a.itemcode) as hii,a.itemname from mc_dict_costitem a ,mc_hospital_match_relation b where "
					+ "a.match_scheme=b.costitemmatch_scheme order by a.itemcode asc";
			List list_byx=jdbcTemplate.queryForList(sql1);
			
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
					costtime1=sys_pa.date1(costtime1, "yyyy-MM-dd HH:mm:ss");
				}
				cid=hiscode+"_mz_"+"0"+ienddate1;
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
					String caseid="Mz"+Patient.getString("PatCode");
					
					//病原学检测
					itemcode="";
					itemname="";
					int IsTestEtiology=0;
					if(Integer.parseInt(Patient.get("IsTestEtiology").toString())==1){
						IsTestEtiology=3;
					}
					for(int k=0;k<list_byx.size();k++){
						Map byx=(Map)list_byx.get(k);
						if(byx.get("hii").toString().contains(hiscode+","+IsTestEtiology)){
							itemcode=byx.get("hii").toString().split(",")[2];
							itemname=byx.get("itemname").toString();
							break;
						}
					}
					
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
							cidno=cidno+1;
							String prescnostr=prescnolist.get(k1).toString();
							Map map=new HashMap();
							map.put("iid", iid);
							map.put("cid",cid+cidno);
							map.put("Patient", Patient);
							map.put("HospID", PassClient.getString("HospID"));
							map.put("costtime1", costtime1);
							map.put("caseid", caseid);
							map.put("prescnostr", prescnostr+"_非_1");//非药和药品不能再一个处方中
							map.put("itemcode",itemcode);
							map.put("itemname",itemname);
							listbatch.add(map);
							
							if(a%Integer.parseInt(insertdatacount)==0){
								batchInsertRows(sql,listbatch);
								log.info("======>t_mc_clinic_order 药品:"+a);
								listbatch.clear();
							}
							
							break;
						}
						prescnolist.clear();
					}
					
				}
			}
			if(listbatch.size()>0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}
			log.info("======>t_mc_clinic_order 非药总数："+a+"-->有效数据："+a);
			
			//插入药品数据
			sql="insert into t_mc_clinic_order (grouptag, orderstatus, doctorname, remark, clinicno, orderno, purpose,"
					+ " singledose, frequency, drugform, routecode, deptcode, ordercode, deptname, caseid, "
					+ "reasonable_desc, doctorcode, drug_unique_code, startdatetime, ordertype, presctype, "
					+ "routename, cost, cid, drugspec, num, days, patientid, prescno, hiscode, ordername, "
					+ "groupstate, doseunit, numunit, is_allow) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
					+ ",?,?,?,?,?,?,?,?,?,?,?,?,?)";
			a=0;
//			b=0;
			iid=0;
//			cid=0;
			ienddate1=ienddate;
			String drugspec="";
			String drugform="";
			String comp_name="";
			String doseunit="";
			String drugcode="";
			String drugtype="";
			String hdd="";
			//字典表找数据
			sql1="select CONCAT(c.hiscode_user,',',a.drug_unique_code,',',a.doseunit) as hdd ,a.drugname,a.drugcode, "
					+ "a.drugspec,a.drugform,a.comp_name,a.doseunit,b.drugtype from mc_dict_drug_pass a " + 
					"left join mc_dict_drug b on a.drugcode=b.drugcode and a.match_scheme=b.match_scheme " + 
					"inner join mc_hospital_match_relation c on a.match_scheme=c.drugmatch_scheme ";
			List list_drug_pass=jdbcTemplate.queryForList(sql1);
			
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				}
				cid=hiscode+"_mz_"+"1"+ienddate1;
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
					String caseid="Mz"+Patient.getString("PatCode");
					
					for(int k=0;k<ScreenDrugs.size();k++){
						cidno=cidno+1;
						JSONObject ScreenDrug=ScreenDrugs.getJSONObject(k);
						hdd=hiscode+","+ScreenDrug.getString("DrugUniqueCode")+","+ScreenDrug.getString("DoseUnit");
						drugspec="";
						drugform="";
						comp_name="";
						doseunit="";
						drugcode="";
						drugtype="";
						for(int k1=0;k1<list_drug_pass.size();k1++){
							if(StringUtils.isNotBlank(drugcode)){
								break;
							}
							Map map=(Map)list_drug_pass.get(k1);
							if(map.get("hdd").equals(hdd)){
								if(map.get("drugtype")!=null){
									drugtype=map.get("drugtype").toString();
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
							log.info("======>未找到字典表药品数据,病人姓名 ："+Patient.get("Name"));
							continue;
						}
						a=a+1;
						
						Map map=new HashMap();
						map.put("drugspec", drugspec);
						map.put("drugform", drugform);
						map.put("comp_name", comp_name);
						map.put("doseunit", doseunit);
						map.put("drugcode", drugcode);
						map.put("drugtype", drugtype);
						map.put("ScreenDrug", ScreenDrug);
						map.put("Patient", Patient);
						map.put("caseid", caseid);
						map.put("PassClient", PassClient);
						map.put("iid", iid);
						map.put("cid", cid+cidno);
						listbatch.add(map);
						
						if(a%Integer.parseInt(insertdatacount)==0){
							batchInsertRows1(sql,listbatch);
							log.info("======>t_mc_clinic_order 药品:"+a);
							listbatch.clear();
						} 
						
//						if(a-50000>=0){
//							b=b+50000;
//							a=a-50000;
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_clinic_lab总数："+count*anlilist.size()+"-->有效数据："+b);
//							listbatch.clear();
//						}
//						if((i+1)==count){
//							batchInsertRows(sql, listbatch);
//							System.out.println("t_mc_clinic_lab总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			log.info("======>t_mc_clinic_order 药品总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("调试==>t_mc_clinic_order 制造数据异常："+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) {
				Map map=(Map)listbatch.get(i);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient"));
				String HospID=map.get("HospID").toString();
				int iid=Integer.parseInt(map.get("iid").toString());
				String cid=map.get("cid").toString();
				String caseid=map.get("caseid").toString();
				String costtime1=map.get("costtime1").toString();
				String prescnostr=map.get("prescnostr").toString();
				String itemcode=strisnull.isnull(map.get("itemcode"));
				String itemname=strisnull.isnull(map.get("itemname"));
				
				try{
					pst.setString(1,"");//grouptag
					pst.setInt(2,0);//orderstatus
					pst.setString(3,Patient.getString("DoctorName"));//doctorname
					pst.setString(4,"");//remark
					pst.setString(5,Patient.getString("InHospNo"));//clinicno
					pst.setString(6,cid);//orderno
					pst.setInt(7,0);//purpose //用药目的，如果接口不传，那么PASS自动生成，根据抗菌药品是否抗感染
					pst.setString(8,"");//singledose
					pst.setString(9,"");//frequency
					pst.setString(10,"");//drugform
					pst.setString(11,"");//routecode
					pst.setString(12,Patient.getString("DeptCode"));//deptcode
					pst.setString(13,itemcode);//ordercode
					pst.setString(14,Patient.getString("DeptName"));//deptname
					pst.setString(15,caseid);//caseid
					pst.setString(16,"合理越权描述");//reasonable_desc
					pst.setString(17,Patient.getString("DoctorCode"));//doctorcode
					pst.setString(18,itemcode);//drug_unique_code
					pst.setString(19,costtime1);//startdatetime
					pst.setInt(20,3);//ordertype
					pst.setInt(21,0);//presctype
					pst.setString(22,"");//routename
					pst.setInt(23,100);//cost
					pst.setString(24,cid);//cid
					pst.setString(25,"");//drugspec
					pst.setInt(26,1);//num
					pst.setInt(27,0);//days
					pst.setString(28,Patient.getString("PatCode"));//patientid
					if("".equals(strisnull.isnull(prescnostr))){
						pst.setInt(29,iid);//prescno
					}else{
						pst.setString(29,prescnostr);//prescno
					}
					pst.setString(30,HospID);//hiscode
					pst.setString(31,itemname);//ordername
					pst.setInt(32,1);//groupstate
					pst.setString(33,"");//doseunit
					pst.setString(34,"");//numunit
					pst.setInt(35,0);//is_allow
				}catch (Exception e){
					log.debug("调试==>t_mc_clinic_order 插表异常："+map);
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
			public void setValues(PreparedStatement pst, int i) {
				Map map=(Map)listbatch.get(i);
				String drugspec=map.get("drugspec").toString();
				String drugform=map.get("drugform").toString();
				String comp_name=map.get("comp_name").toString();
				String doseunit=map.get("doseunit").toString();
				String drugcode=map.get("drugcode").toString();
				int drugtype=Integer.parseInt(map.get("drugtype").toString());
				JSONObject ScreenDrug=JSONObject.fromObject(map.get("ScreenDrug").toString());
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String caseid=map.get("caseid").toString();
				int iid=Integer.parseInt(map.get("iid").toString());
				String cid=map.get("cid").toString();
				
				try{
					pst.setString(1,ScreenDrug.getString("GroupTag"));//grouptag
					pst.setString(2,ScreenDrug.getString("OrderType"));//orderstatus
					pst.setString(3,ScreenDrug.getString("DoctorName"));//doctorname
					pst.setString(4,"");//remark
					pst.setString(5,Patient.getString("InHospNo"));//clinicno
					pst.setString(6,ScreenDrug.getString("OrderNo"));//orderno
					pst.setInt(7,Integer.parseInt(ScreenDrug.getString("Purpose")));//purpose
					pst.setString(8,ScreenDrug.getString("DosePerTime"));//singledose
					pst.setString(9,ScreenDrug.getString("Frequency"));//frequency
					pst.setString(10,drugform);//drugform
					pst.setString(11,ScreenDrug.getString("RouteCode"));//routecode
					pst.setString(12,ScreenDrug.getString("DeptCode"));//deptcode
					pst.setString(13,drugcode);//ordercode
					pst.setString(14,ScreenDrug.getString("DeptName"));//deptname
					pst.setString(15,caseid);//caseid
					pst.setString(16,"合理越权描述");//reasonable_desc
					pst.setString(17,ScreenDrug.getString("DoctorCode"));//doctorcode
					pst.setString(18,ScreenDrug.getString("DrugUniqueCode"));//drug_unique_code
					pst.setString(19,ScreenDrug.getString("StartTime"));//startdatetime
					pst.setInt(20,1);//ordertype
					if(drugtype==2){
						pst.setInt(21,3);//presctype
					}else{
						pst.setInt(21,1);//presctype
					}
					
					pst.setString(22,ScreenDrug.getString("RouteName"));//routename
					pst.setString(23,ScreenDrug.getString("Cost"));//cost
					pst.setString(24,cid);//cid
					pst.setString(25,drugspec);//drugspec
					pst.setString(26,ScreenDrug.getString("Num"));//num
					pst.setInt(27,10);//days
					pst.setString(28,Patient.getString("PatCode"));//patientid
					if("".equals(strisnull.isnull(ScreenDrug.getString("RecipNo")))){
						pst.setInt(29,iid);//prescno
					}else{
						pst.setString(29,ScreenDrug.getString("RecipNo"));//prescno
					}
					pst.setString(30,PassClient.getString("HospID"));//hiscode
					pst.setString(31,ScreenDrug.getString("DrugName"));//ordername
					pst.setInt(32,1);//groupstate
					pst.setString(33,doseunit);//doseunit
					pst.setString(34,ScreenDrug.getString("NumUnit"));//numunit
					pst.setInt(35,0);//is_allow
				}catch (Exception e){
					log.debug("调试==>t_mc_clinic_order 插表异常："+map);
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
