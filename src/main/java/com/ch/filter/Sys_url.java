package com.ch.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.Strisnull;
import com.ch.sysuntils.User;

@Service
public class Sys_url {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Strisnull strisnull;
	
	public boolean user_url(HttpServletRequest req){
//		System.out.println("用户请求地址："+req.getRequestURI());
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		String sql=null;
		sql="select loginname,urlid from sys_users where userid=?";
		List usermnglist=jdbcTemplate.queryForList(sql,new Object[]{user.getUserid()});
		
		Map usermng=null;
		for(int i=0;i<usermnglist.size();i++){
			usermng=(Map)usermnglist.get(i);
		}
		
		//反向控制，urlid为空权限未被限制
		if(StringUtils.isBlank(strisnull.isnull(usermng.get("urlid")))){
			return true;
		}
		
		
		String[] urlids=usermng.get("urlid").toString().split(",");
		int sum=0;
		for(int i=0;i<urlids.length;i++){
			String urlid=urlids[i];
			
			sql="select url from sys_url where urlid=?";
			List urlList=jdbcTemplate.queryForList(sql,urlid);
			if(urlList.size()>0){
				Map urlmap=(Map)urlList.get(0);
				String[] requrl=req.getRequestURI().split("/");
				String reqrul1="";
				for(int i1=0;i1<requrl.length;i1++){
					if(i1<=1){
						continue;
					}
					reqrul1=reqrul1+"/"+requrl[i1];
				}
				
				if(reqrul1.equals(urlmap.get("url"))){
					sum=sum+1;
				}
			}
		}
		
		//反向处理权限，如果数据库中存在的，表示不能访问的页面
		if(sum>0){
			return false;
		}
		return true;
	}
}
