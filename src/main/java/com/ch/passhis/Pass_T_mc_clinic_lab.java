package com.ch.passhis;

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

import com.ch.pahis.Sys_pa;
import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class Pass_T_mc_clinic_lab {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	@Autowired
	Sys_pa sys_pa;
	
	@Autowired
	Strisnull strisnull;
	
	public void clinic_lab(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String startdate){
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_clinic_lab (ienddate, doctorname, samplingtime, doctorcode, clinicno, labcode, "
					+ "resultflag, instrument, itemcode, requestno, dd, unit, sampletype, range_0, patientid, "
					+ "reporttime, itemname, labresult, hiscode, labname, caseid) values(?,?,?,?,?,?,?,?,?,?,?,?"
					+ ",?,?,?,?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			String startdate1=startdate;
			Calendar cal=null;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
			        ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
			        startdate1=sys_pa.date2(startdate, "yyyy-MM-dd HH:mm:ss",i,sum_date);
				}
				for(int j=0;j<anlilist.size();j++){
					
					JSONObject json=JSONObject.fromObject(anlilist.get(j));
					JSONObject PassClient=json.getJSONObject("PassClient");
					JSONObject Patient=json.getJSONObject("Patient");
					JSONObject InputJsonInfoList=json.getJSONObject("InputJsonInfoList");
					JSONArray InputJsonInfos=InputJsonInfoList.getJSONArray("InputJsonInfos");
					JSONObject InputJsonInfo=null;
					for(int fos=0;fos<InputJsonInfos.size();fos++){
						//只取labinfo节点
						if("labinfo".equals(InputJsonInfos.getJSONObject(fos).get("type"))){
							a=a+1;
							if(a%2000==0){
								System.out.println("t_mc_clinic_lab --"+a);
							}
							InputJsonInfo=InputJsonInfos.getJSONObject(fos);
							
							Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_mz");
//							Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
							Patient.put("InHospNo",hiscode+"_门诊_"+Patient.getString("InHospNo"));
							//门诊caseid：Mz门诊号+“＿”＋病人编号
							String caseid="Mz"+Patient.getString("PatCode");

							Map map=new HashMap();
							map.put("ienddate1", ienddate1);
							map.put("Patient", Patient);
							map.put("startdate1", startdate1);
							map.put("PassClient", PassClient);
							map.put("caseid", caseid);
							map.put("InputJsonInfo", InputJsonInfo);
							
							listbatch.add(map);
							
							if(a%500==0){
								batchInsertRows(sql,listbatch);
								listbatch.clear();
							} 
							
							json.clear();
							json = null;
						}
					}
				}
			}
			if(listbatch.size()>0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}
			System.out.println("t_mc_clinic_lab总数："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("t_mc_clinic_lab制造数据异常");
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
				JSONObject InputJsonInfo=JSONObject.fromObject(map.get("InputJsonInfo"));
				
				try{
					pst.setInt(1,Integer.parseInt(ienddate1));
					pst.setString(2,Patient.getString("DoctorName"));
					pst.setString(3,startdate1);//samplingtime
					pst.setString(4,Patient.getString("DoctorCode"));
					pst.setString(5,Patient.getString("InHospNo"));//clinicno
					pst.setString(6,"");//labcode
					pst.setString(7,InputJsonInfo.get("ch_resultflag").toString());//resultflag
					pst.setString(8,"");//instrument
					pst.setString(9,InputJsonInfo.get("labexamcode").toString());//itemcode
					pst.setString(10,strisnull.isnulltostr(InputJsonInfo.get("requestno"), "20140401000001"));//requestno
					pst.setString(11,"");//dd
					pst.setString(12,InputJsonInfo.get("ch_unit").toString());//unit
					pst.setString(13,"");//sampletype
					pst.setString(14,InputJsonInfo.get("ch_range").toString());//range_0
					pst.setString(15,Patient.getString("PatCode"));//patientid
					pst.setString(16,strisnull.isnulltostr(InputJsonInfo.get("reporttime"), startdate1));//reporttime
					pst.setString(17,InputJsonInfo.get("labexamname").toString());//itemname
					pst.setString(18,InputJsonInfo.get("ch_labresult").toString());//labresult
					pst.setString(19,PassClient.getString("HospID"));//hiscode
					pst.setString(20,"");//labname
					pst.setString(21,caseid);//caseid
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
