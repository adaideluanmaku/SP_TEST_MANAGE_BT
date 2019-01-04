package com.ch.moredata.run;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
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

import com.ch.dao.DataBaseType;
import com.ch.dao.SpringJdbc_oracle_his;
import com.ch.moredata.pa.v52.Pa_clear_data;
import com.ch.moredata.pa.v52.Updatatest;
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
public class Table_column{
	JdbcTemplate jdbcTemplate_database=null;
	@Autowired
	DataBaseType dataBaseType;
	
	@Test
	public void testch() throws Exception{
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://172.18.7.160:3306/passrh_v53?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
		String username="root";
		String password="zfxmz123";
		String sql=null;
		
		jdbcTemplate_database=dataBaseType.Database(driver, url, username, password);
		System.out.println();
		if(jdbcTemplate_database==null){
			return;
		}
		List listbatch=new ArrayList();
		List list=null;
		
		//打印字段
//		sql="select COLUMN_NAME,DATA_TYPE,DATA_LENGTH from user_tab_cols where table_name='sacl_pat_allergen_copy'";//oracle
//		sql="select a.name as [column],b.name as type from syscolumns a,systypes b where a.id=object_id('mc_dict_operation') and a.xtype=b.xtype and b.name<>'sysname'";//sqlserver
		sql="select COLUMN_NAME  as 'column' , DATA_TYPE as type from information_schema.COLUMNS where table_name = 'sacl_pat_lab_copy' and table_schema = 'passrh_v53' ";//mysql
		list=jdbcTemplate_database.queryForList(sql);
		String c="";//?
		String e="";//名称
		int d=0;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(list.size()-1-i);
			//去除没用字段
			if("sysname".equals(map.get("type"))){
				continue;
			}
			if("inserttime1111".equals(map.get("column").toString())){
				continue;
			}
			
			d=d+1;
			
			c=c+"?,";
			e=e+map.get("column").toString()+",";
			if("decimal".equals(map.get("type")) || "NUMBER".equals(map.get("type")) ){
				System.out.println("pst.setInt("+(d)+",strisnull.isnulltoint_0(map.get(\""+map.get("column")+"\"),\"-1\"));//"+map.get("column").toString());
			}else{
				System.out.println("pst.setString("+(d)+",strisnull.isnull(map.get(\""+map.get("column")+"\")));//"+map.get("column").toString());
			}
		}
		System.out.println(c);
		System.out.println(e);
	}
	
}
