package com.ch.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sys_url {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean user_url(HttpServletRequest req){
		String sql=null;
		sql="select loginname,urlid from sys_users where userid=?";
		List usermnglist=jdbcTemplate.queryForList(sql,new Object[]{req.getSession().getAttribute("userid")});
		
		Map usermng=null;
		for(int i=0;i<usermnglist.size();i++){
			usermng=(Map)usermnglist.get(i);
		}
		
		//反向控制，权限未被限制
		if(StringUtils.isBlank(usermng.get("urlid").toString())){
			return true;
		}
		
		
		String[] urlids=usermng.get("urlid").toString().split(",");
		int sum=0;
		for(int i=0;i<urlids.length;i++){
			String urlid=urlids[i];
			
			sql="select url from sys_url where urlid=?";
			String url=jdbcTemplate.queryForObject(sql,String.class,urlid);
//			System.out.println(req.getRequestURI());
			if(req.getRequestURI().contains(url)){
				sum=sum+1;
			}
		}
		
		//反向处理权限，如果数据库中存在的，表示不能访问的页面
		if(sum>0){
			return false;
		}
		return true;
	}
}
