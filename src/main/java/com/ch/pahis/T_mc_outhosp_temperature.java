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
public class T_mc_outhosp_temperature {
	private static Logger log = Logger.getLogger(T_mc_outhosp_temperature.class);
	JdbcTemplate jdbcTemplate_dataBase=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Autowired
	Sys_pa sys_pa;
	@Value("${data.insertdatacount}")
    private String insertdatacount;
	public void outhosp_temperature(int trunca, int count, int sum_date,List anlilist,String hiscode,String ienddate,
			String startdate,int database1,int outhosptiwen){
		jdbcTemplate_dataBase=dataBaseType.getJdbcTemplate(database1);
		if(jdbcTemplate_dataBase==null){
			log.info("数据库连接失败");
			return;
		}
		try {
			String sql=null;
			List listbatch=new ArrayList();
			
			sql="insert into t_mc_outhosp_temperature (patientid, visitid, hiscode, taketime, temperature, caseid) "
					+ "values(?,?,?,?,?,?)";
			int a=0;
//			int b=0;
			String ienddate1=ienddate;
			String startdate1=startdate;
			String startdate2=null;
			JSONObject json=null;
			JSONObject PassClient=null;
			JSONObject Patient=null;
			for(int i=0;i<count;i++){
				//数据分割，增加时间
				if(i%(count/sum_date)==0 && i>0){
					 ienddate1=sys_pa.date1(ienddate1, "yyyyMMdd");
//					 startdate1=sys_pa.date2(startdate, "yyyy-MM-dd HH:mm:ss",i,sum_date);
					 startdate1=sys_pa.date1(startdate1, "yyyy-MM-dd HH:mm:ss");
				}
				for(int j=0;j<anlilist.size();j++){
//					a=a+1;
					json=JSONObject.fromObject(anlilist.get(j));
					PassClient=json.getJSONObject("PassClient");
					Patient=json.getJSONObject("Patient");
					Patient.put("PatCode", hiscode+ienddate1+i+"_"+j+"_cy");
//					Patient.put("InHospNo",hiscode+ienddate1+i+"_"+j);
					Patient.put("InHospNo",hiscode+"_出院_"+Patient.getString("InHospNo"));
					//门诊caseid：Mz门诊号+“＿”＋病人编号
					String caseid="Cy"+Patient.getString("PatCode");

					startdate2=startdate1;
					for(int k=0;k<outhosptiwen;k++){
						if(k>0){
							startdate2=sys_pa.date1(startdate2, "yyyy-MM-dd HH:mm:ss");
						}
						//同一天制造3条体温数据
						for(int k1=0;k1<3;k1++){
							a=a+1;
							if(k1==0){
								startdate2=startdate2.substring(0,10)+" 00:00:01";
							}
							if(k1==1){
								startdate2=startdate2.substring(0,10)+" 08:00:01";
							}
							if(k1==2){
								startdate2=startdate2.substring(0,10)+" 13:00:01";
							}
							Map map=new HashMap();
							map.put("Patient", Patient);
							map.put("caseid", caseid);
							map.put("PassClient", PassClient);
							map.put("startdate1", startdate2);
							listbatch.add(map);
							
							if(a%Integer.parseInt(insertdatacount)==0){
								batchInsertRows(sql,listbatch);
								log.info("======>t_mc_outhosp_temperature :"+a);
								listbatch.clear();
							} 
						}
						
					}
					
					json.clear();
					json = null;
				}
			}
			if(listbatch.size()>0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}
			log.info("======>t_mc_outhosp_temperature 总数  ："+a+"-->有效数据："+a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("调试==>t_mc_outhosp_temperature 制造数据异常 ："+e);
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				JSONObject Patient=JSONObject.fromObject(map.get("Patient").toString());
				JSONObject PassClient=JSONObject.fromObject(map.get("PassClient").toString());
				String caseid=map.get("caseid").toString();
				String startdate1=map.get("startdate1").toString();
				double [] temperature = {36.1,36.3,36.5,36.7,37.1,37.3,37.5,37.7,38.1,38.3,38.5,38.7};
				double temperature1 = temperature[new Random().nextInt(12)];
				
				try{
					pst.setString(1,Patient.getString("PatCode"));//[patientid
					pst.setString(2,Patient.getString("VisitCode"));//visitid
					pst.setString(3,PassClient.getString("HospID"));//hiscode
					pst.setString(4,startdate1);//taketime
					pst.setDouble(5,temperature1);//temperature
					pst.setString(6,caseid);//caseid]
				}catch (Exception e){
					log.debug("调试==>t_mc_outhosp_temperature 插表异常 ："+map);
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
