package com.ch.moredata.run;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.moredata.pa.v52.Pa_clear_data;
import com.ch.moredata.pa.v52.Updatatest;
import com.ch.moredata.thread.Data_FileCutters_v52;
import com.ch.pahis.Clear_mc;
import com.ch.pahisthread.FileCutter;
import com.ch.sqlserverpasshis.Clear_mc1;
import com.ch.sqlserverpasshis.Mc_dict_allergen1;
import com.ch.sqlserverpasshis.Mc_dict_costitem1;
import com.ch.sqlserverpasshis.Mc_dict_dept1;
import com.ch.sqlserverpasshis.Mc_dict_disease1;
import com.ch.sqlserverpasshis.Mc_dict_doctor1;
import com.ch.sqlserverpasshis.Mc_dict_drug1;
import com.ch.sqlserverpasshis.Mc_dict_drug_pass1;
import com.ch.sqlserverpasshis.Mc_dict_drug_sub1;
import com.ch.sqlserverpasshis.Mc_dict_exam1;
import com.ch.sqlserverpasshis.Mc_dict_frequency1;
import com.ch.sqlserverpasshis.Mc_dict_lab1;
import com.ch.sqlserverpasshis.Mc_dict_labsub1;
import com.ch.sqlserverpasshis.Mc_dict_operation1;
import com.ch.sqlserverpasshis.Mc_dict_route1;
import com.ch.sqlserverpasshis.Mdc2_dict_drug1;
import com.ch.sys.JsontoModules;

import jdk.nashorn.internal.ir.annotations.Ignore;

/*
 * 制作PA v52业务数据
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:/Spring-mvc-servlet.xml"}) //加载配置文件 
public class Pa_moredata_v52_run{
	@Autowired
    private Data_FileCutters_v52 data_FileCutters;
	@Autowired
	Pa_clear_data clear_data;
	@Autowired 
	Updatatest updatatest;
	@Test
	public void testch() throws Exception{
		System.out.println("======================");
		String startdate="2018-09-01";//造数据起始时间,包括当天
		int datecount=11;//循环天数
		
		//基础库数据：
		//passrh_v52_big_big_bak库一天的数据量是346*7次*3家机构=7266，20180101-20180330
		//passrh_v52_big_big_bak_1x库一天的数据量是346*1次*3家机构=7266，20180401-20180930
		String databasename="passrh_v52_big_big_bak_1x";
		String yuan_date="20180701";//COPY的数据源的时间
//		clear_data.clear();
		
//		data_FileCutters.filesMng("Mc_clinic_allergen",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_caseid_ienddate",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_cost",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_disease",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_drugcost_caseid",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_drugcost_costtime",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_drugorder_detail",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_drugorder_main",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_exam",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_lab",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_operation",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_patient_baseinfo",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_patient_medinfo",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_clinic_prescription",startdate,datecount,yuan_date,databasename);
//		
//		data_FileCutters.filesMng("Mc_indtmp_syqd",startdate,datecount,yuan_date,databasename);
//		
//		data_FileCutters.filesMng("Mc_outhosp_allergen",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_caseid_ienddate",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_cost",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_disease",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_drugcost_caseid",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_drugcost_costtime",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_drugcostdistinct",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_drugorder_detail",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_drugorder_main",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_exam",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_lab",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_operation",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_order",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_patient_baseinfo",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_patient_medinfo",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Mc_outhosp_temperature",startdate,datecount,yuan_date,databasename);
//		
//		data_FileCutters.filesMng("Tmp_drugcasid",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_indtmp_clinicpres",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_indtmp_clinicpt",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_indtmp_cost",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_indtmp_costpt",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_indtmp_num",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_indtmp_numpt",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_outtmp_hosppt",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_outtmp_hospptcost",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_outtmp_hospptnum",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_report_drugnum",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Tmp_report_hosp_drug_cost",startdate,datecount,yuan_date,databasename);
//		
//		data_FileCutters.filesMng("Mc_hosp_dept_hosday",startdate,datecount,yuan_date,databasename);
//		data_FileCutters.filesMng("Pharm_screenresults",startdate,datecount,yuan_date,databasename);
		
		//模拟总线程，通过map控制器控制所有子线程是否执行结束，flase结束，true运行
//		boolean threadbl=false;
//		do{
//			Map<String, Integer> map=Data_FileCutters.threadMap;
//			if(map.size()==0){
//				threadbl=true;
//			}else{
//				threadbl=false;
//			}
//			for (Map.Entry<String, Integer> entry : map.entrySet()) {
//				if(entry.getValue()==0){
//					threadbl=true;
//				}
//			}
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}while(threadbl);
		boolean threadbl=true;
		Map<String, Integer> map=null;
		while(threadbl){
			map=Data_FileCutters_v52.threadMap;
			if(map.size()==0){
				threadbl=false;
			}else{
				threadbl=true;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//当时字段数据制造错误，用这个方法更新了下字段，以后都不需要了
//	@Test
//	public void testupdate(){
//		updatatest.clinic_allergen("20180121", 51);
//	}
}
