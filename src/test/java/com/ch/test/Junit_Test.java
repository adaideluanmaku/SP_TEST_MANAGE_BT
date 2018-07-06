package com.ch.test;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ch.pahis.Clear_mc;
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

import jdk.nashorn.internal.ir.annotations.Ignore;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:/Spring-mvc-servlet.xml"}) //加载配置文件 
public class Junit_Test{
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
	Clear_mc1 clear_mc;
	
	@Test
	public void testch() throws Exception{
		System.out.println("======================");
		clear_mc.cleardict();
		
//		mc_dict_allergen.dict_allergen(6, "2018-01-01 11:11:11");
//		mc_dict_costitem.dict_costitem(6, "2018-01-01 11:11:11");
//		mc_dict_dept.dict_dept(6, "2018-01-01 11:11:11");
//		mc_dict_disease.dict_disease(6, "2018-01-01 11:11:11");
//		mc_dict_doctor.dict_doctor(6, "2018-01-01 11:11:11");
		mc_dict_drug_pass.dict_drug_pass(6, "2018-01-01 11:11:11");
		mc_dict_drug_sub.dict_drug_sub(6, "2018-01-01 11:11:11");
		mc_dict_drug.dict_drug(6, "2018-01-01 11:11:11");
//		mc_dict_exam.dict_exam(6, "2018-01-01 11:11:11");
//		mc_dict_frequency.dict_frequency(6, "2018-01-01 11:11:11");
//		mc_dict_lab.dict_lab(6, "2018-01-01 11:11:11");
//		mc_dict_labsub.dict_labsub(6, "2018-01-01 11:11:11");
//		mc_dict_operation.dict_operation(6, "2018-01-01 11:11:11");
//		mc_dict_route.dict_route(6, "2018-01-01 11:11:11");
		mdc2_dict_drug.dict_drug(6, "2018-01-01 11:11:11");
	}
}
