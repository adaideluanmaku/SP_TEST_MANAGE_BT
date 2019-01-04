package com.ch.test;

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
import com.ch.sys.JsontoModules;

import jdk.nashorn.internal.ir.annotations.Ignore;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:/Spring-mvc-servlet.xml"}) //加载配置文件 
public class Junit_Test{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void testch() throws Exception{
		String sql="select url from sys_url where urlid=?";
		
		List urlList=jdbcTemplate.queryForList(sql,1);
		if(urlList.size()>0){
			Map urlmap=(Map)urlList.get(0);
		}
	}
}
