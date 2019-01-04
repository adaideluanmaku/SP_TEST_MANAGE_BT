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
import com.ch.moredata.pass.v53.Pass_clear_data;
import com.ch.moredata.thread.Data_FileCutters_v52;
import com.ch.moredata.thread.Data_FileCutters_v53;
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

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:/Spring-mvc-servlet.xml"}) //加载配置文件 
public class Pass_moredata_v53_run{
	@Autowired
    private Data_FileCutters_v53 data_FileCutters;
	@Autowired
	Pass_clear_data clear_data;
	@Autowired 
	Updatatest updatatest;
	@Test
	public void testch() throws Exception{
		//基础库数据：
		//1.在同库中制作一套COPY表数据，然后通过COPY表数据循环制作数据到原表中
		System.out.println("===========开始启动数据制作功能===========");
		String startdate="2018-12-01";//造数据起始时间,包括当天
		int datecount=1;//循环天数
		
		clear_data.clear();
		
		data_FileCutters.filesMng("Sacl_pat_allergen",startdate,datecount);
		data_FileCutters.filesMng("Sacl_pat_disease",startdate,datecount);
		data_FileCutters.filesMng("Sacl_pat_drugorder",startdate,datecount);
		data_FileCutters.filesMng("Sacl_pat_exam",startdate,datecount);
		data_FileCutters.filesMng("Sacl_pat_info",startdate,datecount);
		data_FileCutters.filesMng("Sacl_pat_lab",startdate,datecount);
		data_FileCutters.filesMng("Sacl_pat_operation",startdate,datecount);
		data_FileCutters.filesMng("Sacl_screenresult",startdate,datecount);
		
		//模拟总线程，通过map控制器控制所有子线程是否执行结束，flase结束，true运行
		boolean threadbl=true;
		while(threadbl){
			Map<String, Integer> map=Data_FileCutters_v53.threadMap;
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
