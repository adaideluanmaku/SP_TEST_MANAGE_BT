package com.ch.service;

import java.io.IOException;
import java.security.spec.EncodedKeySpec;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.Mysqljdbc;
import com.ch.sysuntils.AesUtils;
import com.ch.sysuntils.DesUtils;
import com.ch.sysuntils.User;

@Service
public class Loginbean {
	@Autowired
	private Mysqljdbc jdbc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	AesUtils aesUtils;
	
	@Autowired
	DesUtils desUtils;
	
	private static Logger log = Logger.getLogger(Loginbean.class);
	
	Connection conn=null;
	PreparedStatement pst=null;
	Statement st=null;
	ResultSet rs=null;
	String rsmessage=null;
	
	public boolean Denglu(User user,String loginname,String password){
		log.debug("登录用户："+user);
		log.debug("密码："+password);
//		String password_aes=aesUtils.encrypt(password);
		String password_des="";
		try {
			password_des = desUtils.encrypt(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("密码加密："+password_des);
		String sql="select count(userid) from sys_users where loginname=? and password=? and state=1";
		int countnum=jdbcTemplate.queryForObject(sql,int.class, new Object[]{loginname,password_des});
		log.debug("用户名数据库查找结果："+countnum);
		if(countnum>0){
			System.out.println("登录成功:"+loginname);
			log.debug("登录成功:"+loginname);
			
			sql="select userid from sys_users where loginname=? and password=?";
			int userid=jdbcTemplate.queryForObject(sql,int.class, new Object[]{loginname,password_des});
			user.setUserid(userid+0L);
			return true;
		} 
		return false;
	}
	
	public String Zhuce(String loginname,String username,String password){
		String sql=null;
		
		sql="select count(*) from sys_users where loginname=?";
		int rscount=jdbcTemplate.queryForObject(sql,int.class, new Object[]{loginname});
		if(rscount>0){
			System.out.println("登录名重复:"+loginname);
			log.debug("登录名重复:"+loginname);
			return rsmessage="登录名重复";
		}
		
		try {
//			password=aesUtils.encrypt(password);
			password = desUtils.encrypt(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("解密失败");
		}
		sql="insert into sys_users(loginname,username,password,state,level) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[]{loginname,username,password,1,2});
		System.out.println(sql);
		System.out.println("注册成功:"+loginname);
		log.debug("注册成功:"+loginname);
		return rsmessage="注册成功";
	}
	
	public boolean checkloginname(HttpServletRequest req){
		String sql=null;
		sql="select count(*) from sys_users where loginname=? and state=1";
		int sum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{req.getParameter("loginname")});
		if(sum>0){
			return false;
		}else{
			return true;
		}
		
	}
}
