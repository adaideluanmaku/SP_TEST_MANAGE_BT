package com.ch.redis.v53.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ch.dao.DataBaseType;
import com.ch.redis.v53.PRTask;
import com.ch.redis.v53.Redis_machine;
import com.medicom.pojo.redis.McSysHospital;

import net.sf.json.JSONObject;

/*
 * redis测试
 * 制作数据等功能
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:/Spring-mvc-servlet.xml"}) //加载配置文件 
public class Passyuchuli{
	@Autowired
	Redis_machine redis_machine;
	@Autowired
	DataBaseType dataBaseType;
	JdbcTemplate jdbcTemplate= null;
	
	private String url="jdbc:mysql://172.18.7.160:3306/sp_test_manage_bt?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&autoReconnect=true";
	private String username="root";
	private String password="zfxmz123";
	private String driver="com.mysql.jdbc.Driver";
	
	//将PASS审查结果输入插入到redis中
	@Ignore
	public void passscreendata() throws Exception{
		jdbcTemplate=dataBaseType.Database(driver, url, username, password);
		if(jdbcTemplate==null){
			System.out.println("数据库连接失败");
			return;
		}
		
		String sql="select * from pass_testmng where anlitype=1 and projectid=31 order by testid asc limit 10";
		List list=jdbcTemplate.queryForList(sql);
		
		PRTask prOverTask = null;
		Date time=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		String datetime=sf.format(time);
		Long autoevid=0L;
		for(int i=0;i<list.size();i++){
			Map testmng=(Map)list.get(i);
			prOverTask = new PRTask();
			
			JSONObject jsonin=JSONObject.fromObject(testmng.get("testin"));
			JSONObject Patient=jsonin.getJSONObject("Patient");
			
			String caseid=datetime+"_"+Patient.getString("Name");//当天日期加病人名称
			System.out.println(caseid);
			
			if(String.valueOf(i).length()==1){
				autoevid=Long.valueOf(datetime+"000000000"+String.valueOf(i+1));
			}else if(String.valueOf(i).length()==2){
				autoevid=Long.valueOf(datetime+"00000000"+String.valueOf(i+1));
			}else if(String.valueOf(i).length()==3){
				autoevid=Long.valueOf(datetime+"0000000"+String.valueOf(i+1));
			}else if(String.valueOf(i).length()==4){
				autoevid=Long.valueOf(datetime+"000000"+String.valueOf(i+1));
			}else if(String.valueOf(i).length()==5){
				autoevid=Long.valueOf(datetime+"00000"+String.valueOf(i+1));
			}
			prOverTask.setAutoevid(autoevid);
			prOverTask.setCaseid(caseid);
			prOverTask.setMhiscode(199010L);
			prOverTask.setPatStatus(1);
			prOverTask.setCheckDatetime(datetime);
			prOverTask.setStatus(0);
			prOverTask.setStatusDesc("");
			prOverTask.setAfterStatue(0);
			prOverTask.setScreenStr(testmng.get("testin").toString());
			prOverTask.setScreenResult(testmng.get("testout").toString());
			redis_machine._set("PRWATINGTASK", caseid, prOverTask);
		}
	}
	public void passscreendata_delete(){
		redis_machine.removerTable("PRWATINGTASK");
	}
	//将PASS审查结果取出
	@Ignore
	public void passscreen_result() throws Exception{
		PRTask prOverTask = new PRTask();
		
		Date time=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		String datetime=sf.format(time);
		
		String caseid=datetime+"_"+"剂量范围113";//当天日期加病人名称
		prOverTask=(PRTask)redis_machine._get("PRWATINGTASK",caseid);
		System.out.println(JSONObject.fromObject(prOverTask).toString());
	}
	
	public void passtooldata(){
		//mc_hospital
		McSysHospital mcSysHospital = new McSysHospital();
		String key="MC_HOSPITAL-1609PA";
		System.out.println(key);
		
		mcSysHospital=(McSysHospital)redis_machine._get("MC_HOSPITAL",key);
		System.out.println(JSONObject.fromObject(mcSysHospital).toString());
		
	}
	@Test
	public void Runrun() throws Exception{
		//将PASS审查数据-写
//		passscreendata();
		//将PASS审查数据-删除
//		passscreendata_delete();
		//将PASS审查数据-读
//		passscreen_result();
		//字典表数据-读
//		passtooldata();
	}
}
