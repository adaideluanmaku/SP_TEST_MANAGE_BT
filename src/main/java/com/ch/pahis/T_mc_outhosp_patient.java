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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class T_mc_outhosp_patient {
	private static Logger log = Logger.getLogger(T_mc_outhosp_patient.class);
	JdbcTemplate jdbcTemplate_dataBase=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Sys_pa sys_pa;
	@Value("${data.insertdatacount}")
    private String insertdatacount;
	public void outhosp_patient(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String enddate,String startdate,int database1){
		jdbcTemplate_dataBase=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_dataBase==null){
			log.info("数据库连接失败");
			return;
		}
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_outhosp_patient (sex, bedno, doctorname, in_diagnosis, weight, firstdeptname, ren_damage, "
					+ "iage, wardcode, i_in, height, hospitalno, payclass, visitid, deptcode, medgroupname, age, is_lact, "
					+ "birthdate, identitycard, deptname, caseid, incisiontypes, allergen, doctorcode, hep_damage, "
					+ "medgroupcode, is_preg, cost, preg_starttime, nursingclass, wardname, startdate, standby, "
					+ "patientname, patientid, hiscode, operations, enddate, accountdate, telephone) values(?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			String enddate1=enddate;
			String startdate1=startdate;
			JSONObject json=null;
			JSONObject PassClient=null;
			JSONObject Patient=null;
			String caseid=null;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
				    enddate1=sys_pa.date1(enddate1, "yyyy-MM-dd HH:mm:ss");
//				    startdate1=sys_pa.date2(startdate1, "yyyy-MM-dd HH:mm:ss",i,sum_date);
				    startdate1=sys_pa.date1(startdate1, "yyyy-MM-dd HH:mm:ss");
				}
				for(int j=0;j<anlilist.size();j++){
					a=a+1;
					json=JSONObject.fromObject(anlilist.get(j));
					PassClient=json.getJSONObject("PassClient");
					Patient=json.getJSONObject("Patient");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					caseid="Cy"+Patient.getString("PatCode");

					Map map=new HashMap();
					map.put("Patient", Patient);
					map.put("caseid", caseid);
					map.put("PassClient", PassClient);
					map.put("ienddate1", ienddate1);
					map.put("enddate1", enddate1);
					map.put("startdate1", startdate1);//将出院病人的住院天数设置成15天
					listbatch.add(map);
					
					if(a%Integer.parseInt(insertdatacount)==0){
						batchInsertRows(sql,listbatch);
						log.info("======>t_mc_outhosp_patient :"+a);
						listbatch.clear();
					} 
//					if(a-50000>=0){
//						b=b+50000;
//						a=a-50000;
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_outhosp_patient总数："+count*anlilist.size()+"-->有效数据："+b);
//						listbatch.clear();
//					}
//					if((i+1)==count){
//						batchInsertRows(sql, listbatch);
//						System.out.println("t_mc_outhosp_patient总数："+count*anlilist.size()+"-->有效数据："+(b+a));
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
			log.info("======>t_mc_outhosp_patient 总数  ："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("调试==>t_mc_outhosp_patient 制造数据异常 ："+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String caseid=map.get("caseid").toString();
				String ienddate1=map.get("ienddate1").toString();
				String enddate1=map.get("enddate1").toString();
				String startdate1=map.get("startdate1").toString();
				
				try{
					if(StringUtils.isBlank(Patient.getString("Sex"))){
						pst.setString(1,"");//sex
					}else{
						pst.setString(1,Patient.getString("Sex"));//sex
					}
					pst.setInt(2,1);//bedno
					pst.setString(3,Patient.getString("DoctorName"));//doctorname
					pst.setString(4,"");//in_diagnosis
					pst.setString(5,Patient.getString("WeighKG"));//weight
					pst.setString(6,Patient.getString("DeptName"));//firstdeptname
					pst.setString(7,Patient.getString("RenDamageDegree"));//ren_damage
					pst.setInt(8,0);//iage
					pst.setString(9,Patient.getString("DeptCode"));//wardcode
					pst.setInt(10,0);//i_in
					pst.setString(11,Patient.getString("HeightCM"));//height
					pst.setString(12,Patient.getString("InHospNo"));//hospitalno
					pst.setString(13,Patient.getString("PayClass"));//payclass
					pst.setString(14,Patient.getString("VisitCode"));//visitid
					pst.setString(15,Patient.getString("DeptCode"));//deptcode
					pst.setString(16,"门诊心电图_医疗组");//medgroupname
//					if(StringUtils.isBlank(Patient.getString("Age"))){
//						pst.setInt(17,0);//age
//					}else{
//						pst.setString(17,Patient.getString("Age"));//age
//					}
					pst.setString(17,Patient.getString("Age"));//age
					pst.setString(18,Patient.getString("IsLactation"));//is_lact
					pst.setString(19,Patient.getString("Birthday"));//birthdate
					pst.setString(20,Patient.getString("IDCard"));//identitycard
					pst.setString(21,Patient.getString("DeptName"));//deptname
					pst.setString(22,caseid);//caseid
					pst.setString(23,"切口类型串");//incisiontypes
					pst.setString(24,"");//allergen
					pst.setString(25,Patient.getString("DoctorCode"));//doctorcode
					pst.setString(26,Patient.getString("HepDamageDegree"));//hep_damage
					pst.setInt(27,3207);//medgroupcode
					pst.setString(28,Patient.getString("IsPregnancy"));//is_preg
					pst.setInt(29,100);//cost
					pst.setString(30,Patient.getString("PregStartDate"));//preg_starttime
					pst.setInt(31,1);//nursingclass
					pst.setString(32,Patient.getString("DeptName"));//wardname
					pst.setString(33,startdate1);//startdate
					pst.setString(34,"");//standby
					pst.setString(35,Patient.getString("Name"));//patientname
					pst.setString(36,Patient.getString("PatCode"));//patientid
					pst.setString(37,PassClient.getString("HospID"));//hiscode
					pst.setString(38,"手术列表串");//operations
					pst.setString(39,enddate1);//enddate
					pst.setString(40,"");//accountdate
					pst.setString(41,Patient.getString("Telephone"));//telephone]
				}catch (Exception e){
					log.debug("调试==>t_mc_outhosp_patient 插表异常 ："+map);
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
