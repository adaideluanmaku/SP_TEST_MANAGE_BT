package com.ch.pahisthread;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.pahis.AnliCount;
import com.ch.pahis.Clear_mc;
import com.ch.pahis.CreateView;
import com.ch.pahis.Createtable;
import com.ch.pahis.Mc_dict_allergen;
import com.ch.pahis.Mc_dict_costitem;
import com.ch.pahis.Mc_dict_dept;
import com.ch.pahis.Mc_dict_disease;
import com.ch.pahis.Mc_dict_doctor;
import com.ch.pahis.Mc_dict_drug;
import com.ch.pahis.Mc_dict_drug_pass;
import com.ch.pahis.Mc_dict_drug_sub;
import com.ch.pahis.Mc_dict_exam;
import com.ch.pahis.Mc_dict_frequency;
import com.ch.pahis.Mc_dict_lab;
import com.ch.pahis.Mc_dict_labsub;
import com.ch.pahis.Mc_dict_operation;
import com.ch.pahis.Mc_dict_route;
import com.ch.pahis.Mdc2_dict_drug;
import com.ch.pahis.Sys_pa;
import com.ch.passhis.Pass_T_mc_clinic_lab;
import com.ch.passhis.Pass_T_mc_inhosp_lab;
import com.ch.passhis.Pass_T_mc_outhosp_lab;
import com.ch.sysuntils.User;
import com.ch.web.PaAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class Date_to_Oracle_Thread {
	private static Logger log = Logger.getLogger(Date_to_Oracle_Thread.class);
	@Autowired
	Createtable createtable;
	@Autowired
	AnliCount anliCount;
//	@Autowired
//	CreateView createView;
	@Autowired
	Clear_mc clear_mc;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	 @Autowired
	Mc_dict_allergen mc_dict_allergen;
	@Autowired
	Mc_dict_costitem mc_dict_costitem;
	@Autowired
	Mc_dict_dept mc_dict_dept;
	@Autowired
	Mc_dict_disease mc_dict_disease;
	@Autowired
	Mc_dict_doctor mc_dict_doctor;
	@Autowired
	Mc_dict_drug_pass mc_dict_drug_pass;
	@Autowired
	Mc_dict_drug_sub mc_dict_drug_sub;
	@Autowired
	Mc_dict_drug mc_dict_drug;
	@Autowired
	Mc_dict_exam mc_dict_exam;
	@Autowired
	Mc_dict_frequency mc_dict_frequency;
	@Autowired
	Mc_dict_lab mc_dict_lab;
	@Autowired
	Mc_dict_labsub mc_dict_labsub;
	@Autowired
	Mc_dict_operation mc_dict_operation;
	@Autowired
	Mc_dict_route mc_dict_route;
	@Autowired
	Mdc2_dict_drug mdc2_dict_drug;
	@Autowired
    private FileCutter fileCutter;
	@Autowired
	Sys_pa sys_pa;
	
	public String Rundate(Long userid,String hiscodes1,String datetime1,int mz_sum_date1,int countmz1,int countzy1,int countcy1,int mz1,int zy1,int cy1,
			int dict1,int createview1,int trunca1,int projectid1,String match_scheme1,int createTB1,final int passorpa_hisdata1,
			int cleardict1,int clear_radio1,int database1,int cy_sum_date1) throws ClassNotFoundException, SQLException, IOException{
		
		String sql="select level from sys_users where userid=?";
		int level = jdbcTemplate.queryForObject(sql, int.class,new Object[]{userid});
		if(level != 1){
			return "你无权操作!";
		}
		//制作数据参数设置
		String[] hiscodes=hiscodes1.split(",");//{"HISCODE001","HISCODE002","HISCODE003","HISCODE004"}--其实是用户的HISCODE_USER
		
		//
		String[] datetimes=null;
		String ienddate=null;
		String enddate=null;
		String costtime=null;
		String startdate=null;
		//根据设定的时间拼装时间，"2012-01-01"
		if(StringUtils.isNotBlank(datetime1)){
			datetimes=datetime1.split("-");
			ienddate=datetimes[0]+datetimes[1]+datetimes[2];//设置导入数据的起始点
			enddate=datetime1;
			costtime=datetime1+" 01:01:01";
			startdate=datetime1+" 01:01:01";//住院开始时间
		}
		int outhosptiwen=9;//shezhi 病人住院到出院的天数,用来制造体温数据
		
		//住院只产生最后一天的数据，所以要根据出院的最后日期单独制作一个时间出来
		int inhosptiwen=5;//设置病人住院的天数,用来制造体温数据
		String[] datetimes_zy=null;
		String ienddate_zy=null;
		String enddate_zy=null;
		String costtime_zy=null;
		String startdate_zy=null;
		if(StringUtils.isNotBlank(datetime1)){
			datetimes_zy=datetime1.split("-");
			ienddate_zy=datetimes_zy[0]+datetimes_zy[1]+datetimes_zy[2];//设置导入数据的起始点
			if(cy_sum_date1==0){//
				ienddate_zy=sys_pa.date7(ienddate_zy, "yyyyMMdd", 0);
			}else{
				ienddate_zy=sys_pa.date7(ienddate_zy, "yyyyMMdd", cy_sum_date1-1);
			}
			
			enddate_zy=ienddate_zy.substring(0,4)+"-"+ienddate_zy.substring(4,6)+"-"+ienddate_zy.substring(6,8);
			costtime_zy=enddate_zy+" 01:01:01";
			startdate_zy=enddate_zy+" 01:01:01";//住院开始时间
			startdate_zy=sys_pa.date7(startdate_zy, "yyyy-MM-dd HH:mm:ss", -inhosptiwen);//将住院开始时间调整到前5天开始
		}
		
		//
		int mz_sum_date=mz_sum_date1;//10---表示N天，每分割一次，起始日期增加一天，不能设置0，N表示循环N次分割
		int zy_sum_date=1;//出院循环天数赋值给住院，之后逻辑里面住院只造最后一天数据
		int cy_sum_date=cy_sum_date1;
		int count=countmz1*mz_sum_date;
		int countzy=countzy1*zy_sum_date;
		int countcy=countcy1*cy_sum_date;
		
		int mz=mz1;//控制数据制造开关 0关，1开
		int zy=zy1;
		int cy=cy1;
		int dict=dict1;
		
		//是否重建创建所有表
		int createTB=createTB1;
		//选择数据库0oracle,1sqlserver,2mysql
		int database=database1;
		sql="select databasetype from sys_database where databaseid=?";
		String databasetype=jdbcTemplate.queryForObject(sql, String.class,new Object[]{database});
		//是否清空表业务表
		int trunca=trunca1;//0关1开
		int clear_radio=clear_radio1;
		//是否创建视图
		int createview=createview1;//0关1开
				
		//1,导字典表方案数据,2.查找字典表时也要使用
		String[] match_scheme=match_scheme1.split(",");
//		final int match_scheme=match_scheme1;
				
		//手动开启刷redis功能
//		int red=0;
//		String rhmmurl="http://172.18.7.160:8088/passrhmm";
		
		//开始工作啦
		//判断机构是否都合理
		if(anliCount.HisCodes(hiscodes)==1){
			log.info("======>至少有一个机构编号未在mc_hospital_match_relation中找到");
			return "至少有一个机构编号未在mc_hospital_match_relation中找到";
		}
		
		//创建所有表
		if(createTB==1){
			if("MYSQL".equals(databasetype)){
				
			}else if("MSSQL".equals(databasetype)){
				createtable.ceatetable_sqlserver(database);
			}else if("ORACLE".equals(databasetype)){
				createtable.ceatetable_oracle(database);
			}
		}
		
		//清理业务表
		if(trunca==1){
			if(clear_radio==0){
				clear_mc.cleardatemz(database);
				clear_mc.cleardatezy(database);
				clear_mc.cleardatecy(database);
			}else{
				if(mz==1){
					clear_mc.cleardatemz(database);
				}
				if (zy == 1) {
					clear_mc.cleardatezy(database);
				}
				if (cy == 1) {
					clear_mc.cleardatecy(database);
				}
			}
		}
		//清理字典表
		if(cleardict1==1){
			clear_mc.cleardict(database);
		}
		//创建视图
		if(createview==1){
			if(StringUtils.isNotBlank(hiscodes[0])){
				if("MYSQL".equals(databasetype)){
					
				}else if("MSSQL".equals(databasetype)){
					createtable.ceateview_sqlserver(hiscodes,database);
				}else if("ORACLE".equals(databasetype)){
					createtable.ceateview_oracle(hiscodes,database);
				}
			}
		}
		log.info("======>机构："+Arrays.toString(hiscodes));
		for(int k=0;k<hiscodes.length;k++){
			final String hiscode=hiscodes[k];
			if(StringUtils.isBlank(hiscode)){
				continue;
			}
			log.info("======>目前运行的机构是第："+(k+1)+"个"+hiscode);
			//获取循环1次数据集合
			final List anlilist=anliCount.getAnli(projectid1, hiscode);
			if(anlilist.size()==0){
				log.info("======>未找到单次案例数据集");
//				return "数据集压根没数据";
				break;
			}
			
			int cid = 0;//单机构全局药品唯一编号
			if(mz==1){
				log.info("======>开始导门诊数据");
				fileCutter.filesMng("t_mc_clinic_allergen",trunca,count, mz_sum_date,anlilist,hiscode, ienddate,null,null,0,null,0,0,database,0,0);
		        
				fileCutter.filesMng("t_mc_clinic_cost",trunca,count, mz_sum_date,anlilist,hiscode, ienddate,costtime,null,0,null,0,0,database,0,0);
				
				fileCutter.filesMng("t_mc_clinic_disease",trunca,count, mz_sum_date,anlilist,hiscode, ienddate,null,null,0,null,0,0,database,0,0);
				
				fileCutter.filesMng("t_mc_clinic_exam",trunca,count, mz_sum_date,anlilist,hiscode, ienddate,null,startdate,0,null,0,0,database,0,0);
				
				fileCutter.filesMng("t_mc_clinic_lab",trunca,count, mz_sum_date,anlilist,hiscode, ienddate,null,startdate,passorpa_hisdata1,null,0,0,database,0,0);
				
				fileCutter.filesMng("t_mc_clinic_order",trunca, count, mz_sum_date, anlilist, hiscode, ienddate,null,null,0,null,0,0,database,0,0);
				
				fileCutter.filesMng("t_mc_clinic_patient",trunca, count, mz_sum_date, anlilist, hiscode, ienddate,null,null,0,enddate,0,0,database,0,0);
					
//				log.info("======>导门诊数据结束");
			}
			
			if(cy==1){
				log.info("======>开始导出院数据");
				fileCutter.filesMng("t_mc_outhosp_allergen",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,null,0,null,0,countcy,database,0,0);
				
				fileCutter.filesMng("t_mc_outhosp_cost",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,costtime,null,0,null,0,countcy,database,0,0);
				
				fileCutter.filesMng("t_mc_outhosp_disease",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,null,0,null,0,countcy,database,0,0);
				
				fileCutter.filesMng("t_mc_outhosp_exam",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,startdate,0,null,0,countcy,database,0,0);
				
				fileCutter.filesMng("t_mc_outhosp_lab",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,startdate,passorpa_hisdata1,null,0,countcy,database,0,0);
	
				fileCutter.filesMng("t_mc_outhosp_operation",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,null,0,enddate,0,countcy,database,0,0);
//				
				fileCutter.filesMng("t_mc_outhosp_order",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,startdate,0,null,0,countcy,database,0,0);
//				
				fileCutter.filesMng("t_mc_outhosp_patient",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,sys_pa.date7(startdate, "yyyy-MM-dd", -outhosptiwen),0,enddate,0,countcy,database,0,0);
				
				fileCutter.filesMng("t_mc_outhosp_temperature",trunca,0, cy_sum_date,anlilist,hiscode, ienddate,null,sys_pa.date7(startdate, "yyyy-MM-dd", -outhosptiwen),0,null,0,countcy,database,0,outhosptiwen);
				
//				log.info("======>导出院数据结束");
			}
			
			if(zy==1){
				log.info("======>开始导住院数据");
				fileCutter.filesMng("t_mc_inhosp_allergen",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,null,0,null,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_cost",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,costtime,null,0,null,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_disease",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,null,0,null,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_exam",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,startdate_zy,0,null,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_lab",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,startdate_zy,passorpa_hisdata1,null,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_operation",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,startdate_zy,0,enddate_zy,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_order",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,startdate_zy,0,enddate_zy,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_patient",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,startdate_zy,0,enddate_zy,countzy,0,database,0,0);
				
				fileCutter.filesMng("t_mc_inhosp_temperature",trunca,0, zy_sum_date,anlilist,hiscode, ienddate_zy,null,startdate_zy,0,null,countzy,0,database,inhosptiwen,0);
				
//				log.info("======>导住院数据结束");
			}
			
			//模拟总线程，通过map控制器控制所有子线程是否执行结束，flase结束，true运行
			boolean threadbl=false;
			do{
				Map<String, Integer> map=FileCutter.threadMap;
				if(map.size()==0){
					threadbl=true;
				}else{
					threadbl=false;
				}
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					if(entry.getValue()==0){
						threadbl=true;
					}
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(threadbl);
		}
		
		if(dict==1){
//			if(match_scheme.length>0){
//				clear_mc.cleardict();
//			}
			log.info("======>开始导字典数据");
			try {
				for(int i=0;i<match_scheme.length;i++){
					mc_dict_allergen.dict_allergen(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_costitem.dict_costitem(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_dept.dict_dept(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_disease.dict_disease(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_doctor.dict_doctor(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_exam.dict_exam(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_frequency.dict_frequency(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_lab.dict_lab(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_labsub.dict_labsub(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_operation.dict_operation(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_route.dict_route(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_drug_pass.dict_drug_pass(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_drug_sub.dict_drug_sub(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mc_dict_drug.dict_drug(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
					mdc2_dict_drug.dict_drug(Integer.parseInt(match_scheme[i]), startdate,database,databasetype);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.debug("调试==>字典表异常"+e);
			}
			log.info("======>导字典数据结束");
//			if(hiscodes.length>0){
//				try {
//					createtable.ceatedictview(hiscodes);
//				} catch (ClassNotFoundException | SQLException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}
		return "结束了";
	}
}
