package com.ch.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.AesUtils;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.DesUtils;


@Service
public class Loginbean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DesUtils desUtils;
	
	public List Denglu(String loginname,String password){
		String sql="select * from sys_users where loginname=? and password=? and state=1";
		List userlist=new ArrayList();
		try {
			userlist = jdbcTemplate.queryForList(sql, new Object[]{loginname,desUtils.encrypt(password)});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userlist;
	}
	
	public String Zhuce(HttpServletRequest req){
		String sql="select count(1) from sys_users where loginname=?";
		int usersum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{req.getParameter("loginname")});
		if(usersum>0){
			return "用户名重复";
		}else{
			sql="insert into sys_users(loginname,username,password,state,level) value(?,?,?,0,2)";
			try {
				jdbcTemplate.update(sql, new Object[]{req.getParameter("loginname"),req.getParameter("username"),desUtils.encrypt(req.getParameter("password"))});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "注册用户出现异常";
			}
			return "ok";
		}
		
		
	}
}

