package com.ch.sqlserverpasshis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.passhis.Pass_T_mc_clinic_lab;
import com.ch.passhis.Pass_T_mc_inhosp_lab;
import com.ch.passhis.Pass_T_mc_outhosp_lab;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class Date_to_Sqlserver1 {
	@Autowired
	Createtable1 createtable;
	@Autowired
	AnliCount1 anliCount;
	@Autowired
	CreateView1 createView;
	@Autowired
	Clear_mc1 clear_mc;
	@Autowired
	Mc_dict_allergen1 mc_dict_allergen;
	@Autowired
	Mc_dict_costitem1 mc_dict_costitem;
	@Autowired
	Mc_dict_dept1 mc_dict_dept;
	@Autowired
	Mc_dict_disease1 mc_dict_disease;
	@Autowired
	Mc_dict_doctor1 mc_dict_doctor;
	@Autowired
	Mc_dict_drug_pass1 mc_dict_drug_pass;
	@Autowired
	Mc_dict_drug_sub1 mc_dict_drug_sub;
	@Autowired
	Mc_dict_drug1 mc_dict_drug;
	@Autowired
	Mc_dict_exam1 mc_dict_exam;
	@Autowired
	Mc_dict_frequency1 mc_dict_frequency;
	@Autowired
	Mc_dict_lab1 mc_dict_lab;
	@Autowired
	Mc_dict_labsub1 mc_dict_labsub;
	@Autowired
	Mc_dict_operation1 mc_dict_operation;
	@Autowired
	Mc_dict_route1 mc_dict_route;
	@Autowired
	Mdc2_dict_drug1 mdc2_dict_drug;
	
	@Autowired
	T_mc_clinic_allergen1 t_mc_clinic_allergen;
	@Autowired
	T_mc_clinic_cost1 t_mc_clinic_cost;
	@Autowired
	T_mc_clinic_disease1 t_mc_clinic_disease;
	@Autowired
	T_mc_clinic_exam1 t_mc_clinic_exam;
	@Autowired
	T_mc_clinic_lab1 t_mc_clinic_lab;
	@Autowired
	T_mc_clinic_order1 t_mc_clinic_order;
	@Autowired
	T_mc_clinic_patient1 t_mc_clinic_patient;
	
	@Autowired
	T_mc_inhosp_allergen1 t_mc_inhosp_allergen;
	@Autowired
	T_mc_inhosp_cost1 t_mc_inhosp_cost;
	@Autowired
	T_mc_inhosp_disease1 t_mc_inhosp_disease;
	@Autowired
	T_mc_inhosp_exam1 t_mc_inhosp_exam;
	@Autowired
	T_mc_inhosp_lab1 t_mc_inhosp_lab;
	@Autowired
	T_mc_inhosp_operation1 t_mc_inhosp_operation;
	@Autowired
	T_mc_inhosp_order1 t_mc_inhosp_order;
	@Autowired
	T_mc_inhosp_patient1 T_mc_inhosp_patient;
	@Autowired
	T_mc_inhosp_temperature1 t_mc_inhosp_temperature;
	
	@Autowired
	T_mc_outhosp_allergen1 t_mc_outhosp_allergen;
	@Autowired
	T_mc_outhosp_cost1 t_mc_outhosp_cost;
	@Autowired
	T_mc_outhosp_disease1 t_mc_outhosp_disease;
	@Autowired
	T_mc_outhosp_exam1 t_mc_outhosp_exam;
	@Autowired
	T_mc_outhosp_lab1 t_mc_outhosp_lab;
	@Autowired
	T_mc_outhosp_operation1 t_mc_outhosp_operation;
	@Autowired
	T_mc_outhosp_order1 t_mc_outhosp_order;
	@Autowired
	T_mc_outhosp_patient1 t_mc_outhosp_patient;
	@Autowired
	T_mc_outhosp_temperature1 t_mc_outhosp_temperature;
	
	@Autowired
	Pass_T_mc_clinic_lab pass_t_mc_clinic_lab;
	@Autowired
	Pass_T_mc_inhosp_lab pass_t_mc_inhosp_lab;
	@Autowired
	Pass_T_mc_outhosp_lab pass_t_mc_outhosp_lab;
	
	public void Rundate(String hiscodes1,String datetime1,int sum_date1,int count1,int mz1,int zy1,int cy1,
			int dict1,int createview1,int trunca1,int projectid1,String match_scheme1,int createTB1,int passorpa_hisdata1,
			int cleardict1) throws ClassNotFoundException, SQLException, IOException{
		//制作数据参数设置
		String[] hiscodes=hiscodes1.split(",");//{"HISCODE001","HISCODE002","HISCODE003","HISCODE004"}--其实是用户的HISCODE_USER
		
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
		
		int sum_date=sum_date1;//10---表示N天，每分割一次，起始日期增加一天，不能设置0，N表示循环N次分割
		int count=count1*sum_date;//单天循环次数*天数----一天数据总量，一批案例的循环次数,数据集22条
		int countzy=count1*sum_date;//单天循环次数*天数
		int countcy=count1*sum_date;//单天循环次数*天数，如果要削减出院数据量，可在方法里面削减
//		if(count1>=6){
//			countcy=count1*sum_date/6;
//		}
		
		int mz=mz1;//控制数据制造开关 0关，1开
		int zy=zy1;
		int cy=cy1;
		int dict=dict1;
		
		//是否重建创建所有表
		final int createTB=createTB1;
		//是否清空表业务表
		final int trunca=trunca1;//0关1开
		
		//是否创建视图
		final int createview=createview1;//0关1开
				
		//1,导字典表方案数据,2.查找字典表时也要使用
		String[] match_scheme=match_scheme1.split(",");
//		final int match_scheme=match_scheme1;
				
		//手动开启刷redis功能
		int red=0;
		String rhmmurl="http://172.18.7.160:8088/passrhmm";
		
		//开始工作啦
		//创建所有表
		if(createTB==1){
//			clear_mc.creattable();
			createtable.ceatetable();
		}
		
		//清理业务表
		if(trunca==1){
			clear_mc.cleardate();
		}
		
		//创建视图
		if(createview==1){
			if(StringUtils.isNotBlank(hiscodes[0])){
//				createView.createV(hiscodes[0]);
				//创建业务视图
				createtable.ceateview(hiscodes);
				//创建字典表视图
//				createtable.ceatedictview(hiscodes);
			}
		}
		
		for(int k=0;k<hiscodes.length;k++){
			String hiscode=hiscodes[k];
			if(StringUtils.isBlank(hiscode)){
				continue;
			}
			System.out.println("=================目前运行的机构是第："+(k+1)+"个"+hiscode+"=================");
			
			//获取循环1次数据集合
			List anlilist=anliCount.getAnli(projectid1, hiscode);
			if(anlilist.size()==0){
				System.out.println( "未找到单次案例数据集");
				break;
			}
			
			int cid = 0;//单机构全局药品唯一编号
			if(mz==1){
				System.out.println("开始导门诊数据");
//				new Thread(new Runnable(){
//					public void run(){
						t_mc_clinic_allergen.clinic_allergen(trunca,count, sum_date,anlilist,hiscode, ienddate);
						t_mc_clinic_cost.clinic_cost(trunca,count, sum_date,anlilist,hiscode,ienddate,costtime);
						t_mc_clinic_disease.clinic_disease(trunca, count, sum_date, anlilist, hiscode, ienddate);
						t_mc_clinic_exam.clinic_exam(trunca, count, sum_date, anlilist, hiscode, ienddate, startdate);
//					}
//				}).start();
				
//				new Thread(new Runnable(){
//					public void run(){
						if(passorpa_hisdata1==0){
							t_mc_clinic_lab.clinic_lab(trunca, count, sum_date, anlilist, hiscode, ienddate, startdate);
						}else{
//							pass_t_mc_clinic_lab.clinic_lab(trunca, count, sum_date, anlilist, hiscode, ienddate, startdate);
						}
						cid=t_mc_clinic_order.clinic_order(cid,trunca, count, sum_date, anlilist, hiscode, ienddate);
						t_mc_clinic_patient.clinic_patient(trunca, count, sum_date, anlilist, hiscode, ienddate, enddate);
//					}
//				}).start();
				System.out.println("导门诊数据结束");
			}
			
			if(zy==1){
				System.out.println("开始导住院数据");
//				new Thread(new Runnable(){
//					public void run(){
						t_mc_inhosp_allergen.inhosp_allergen(trunca, countzy, sum_date, anlilist, hiscode, ienddate);
						t_mc_inhosp_cost.inhosp_cost(trunca, countzy, sum_date, anlilist, hiscode, ienddate, costtime);
						t_mc_inhosp_disease.inhosp_disease(trunca, countzy, sum_date, anlilist, hiscode, ienddate, costtime);
						t_mc_inhosp_exam.inhosp_exam(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate);
//					}
//				}).start();
				
//				new Thread(new Runnable(){
//					public void run(){
						if(passorpa_hisdata1==0){
							t_mc_inhosp_lab.inhosp_lab(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate);	
						}else{
//							pass_t_mc_inhosp_lab.inhosp_lab(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate);
						}
						t_mc_inhosp_operation.inhosp_operation(trunca, countzy, sum_date, anlilist, hiscode, ienddate, enddate);
						cid=t_mc_inhosp_order.inhosp_order(cid,trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate);
						T_mc_inhosp_patient.inhosp_patient(trunca, countzy, sum_date, anlilist, hiscode, ienddate, enddate, startdate);
						t_mc_inhosp_temperature.inhosp_temperature(trunca, countzy, sum_date, anlilist, hiscode, ienddate, startdate);
//					}
//				}).start();
				
				System.out.println("导住院数据结束");
			}
			
			if(cy==1){
				System.out.println("开始导出院数据");
//				new Thread(new Runnable(){
//					public void run(){
						t_mc_outhosp_allergen.outhosp_allergen(trunca, countcy, sum_date, anlilist, hiscode, ienddate);
						t_mc_outhosp_cost.outhosp_cost(trunca, countcy, sum_date, anlilist, hiscode, ienddate, costtime);
						t_mc_outhosp_disease.outhosp_disease(trunca, countcy, sum_date, anlilist, hiscode, ienddate);
						t_mc_outhosp_exam.outhosp_exam(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate);
//					}
//				}).start();
				
//				new Thread(new Runnable(){
//					public void run(){
						if(passorpa_hisdata1==0){
							t_mc_outhosp_lab.outhosp_lab(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate);	
						}else{
//							pass_t_mc_outhosp_lab.outhosp_lab(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate);
						}
						t_mc_outhosp_operation.outhosp_operation(trunca, countcy, sum_date, anlilist, hiscode, ienddate,enddate);
						cid=t_mc_outhosp_order.outhosp_order(cid,trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate);
						t_mc_outhosp_patient.outhosp_patient(trunca, countcy, sum_date, anlilist, hiscode, ienddate, enddate, startdate);
						t_mc_outhosp_temperature.outhosp_temperature(trunca, countcy, sum_date, anlilist, hiscode, ienddate, startdate);
//					}
//				}).start();
				
				System.out.println("导出院数据结束");
			}
		}
		
		if(cleardict1==1){
			clear_mc.cleardict();
		}
		if(dict==1){
//			if(match_scheme.length>0){
//				clear_mc.cleardict();
//			}
			System.out.println("开始导字典数据");
			try {
				for(int i=0;i<match_scheme.length;i++){
					mc_dict_allergen.dict_allergen(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_costitem.dict_costitem(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_dept.dict_dept(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_disease.dict_disease(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_doctor.dict_doctor(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_drug_pass.dict_drug_pass(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_drug_sub.dict_drug_sub(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_drug.dict_drug(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_exam.dict_exam(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_frequency.dict_frequency(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_lab.dict_lab(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_labsub.dict_labsub(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_operation.dict_operation(Integer.parseInt(match_scheme[i]), startdate);
					mc_dict_route.dict_route(Integer.parseInt(match_scheme[i]), startdate);
					mdc2_dict_drug.dict_drug(Integer.parseInt(match_scheme[i]), startdate);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("字典表异常"+e);
			}
			System.out.println("导字典数据结束");
			
//			if(hiscodes.length>0){
//				try {
//					createtable.ceatedictview(hiscodes);
//				} catch (ClassNotFoundException | SQLException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}
	}
}
