package com.ch.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Logmessagebean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> testmessage(){
		List list=null;
		String sql=null;
		Map<String, Object> map=new HashMap();
		
		sql="select a.testname,a.testno,a.testtext,b.projectname,a.inserttime from testmng a inner join project b on a.projectid=b.projectid order by a.inserttime desc limit 0,100";
		list=jdbcTemplate.queryForList(sql);
		map.put("testmng", list);
		map.put("num", list.size());
		return map;
	}
	
	public Map<String, Object> learnmessage(){
		List list=null;
		String sql=null;
		Map<String, Object> map=new HashMap();
		
		sql="select b.learngroup,a.learnname,a.learntext,a.inserttime from learn a inner join learn_type b on a.learngroupid=b.learngroupid order by a.inserttime desc limit 0,100";
		list=jdbcTemplate.queryForList(sql);
		map.put("learn", list);
		map.put("num", list.size());
		return map;
	}
}
