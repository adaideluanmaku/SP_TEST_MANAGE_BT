package com.ch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.easyui.DataGrid;
import com.ch.sysuntils.AesUtils;
import com.ch.sysuntils.DesUtils;

@Service
public class Userbean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AesUtils aesUtils;
	
	@Autowired
	DesUtils desUtils;
	
	public DataGrid usersquery(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		String loginname=null;
		HttpSession session=req.getSession();
		
		String limit=null;
		int page=0;
		int total=0;
		DataGrid dataGrid = new DataGrid();//返回easyui-web对象
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		
		if(StringUtils.isBlank(req.getParameter("loginname"))){
			loginname="";
		}else{
			loginname=req.getParameter("loginname");
		}
		
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		//查询权限控制
		if("admin".equals(session.getAttribute("loginname"))){
			sql="select count(*) from sys_users where loginname like ? ";
			total=jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+loginname+"%"});
			dataGrid.setTotal(total+0L);
			
//			Date time=new Date();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			sql="select * from sys_users where loginname like ? "+limit;
			rslist=jdbcTemplate.queryForList(sql,new Object[]{"%"+loginname+"%"});
		}else{
			sql="select count(*) from sys_users where loginname like ? ";
			total=jdbcTemplate.queryForObject(sql,int.class,new Object[]{session.getAttribute("loginname")});
			dataGrid.setTotal(total+0L);
			
			sql="select * from sys_users where loginname like ? "+limit;
			rslist=jdbcTemplate.queryForList(sql,new Object[]{session.getAttribute("loginname")});
		}

		List rslist1=new ArrayList();
		
		for(int i=0;i<rslist.size();i++){
			Map map=(Map)rslist.get(i);
//			String password_aes=new String(aesUtils.decrypt(map.get("password").toString()));
			String password_des="";
			try {
				password_des=new String(desUtils.decrypt(map.get("password").toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("password", password_des);
			rslist1.add(map);
		}
		dataGrid.setRows(rslist1);
		return dataGrid;
	}
	
	public String usersadd(HttpServletRequest req){
		String loginname=null;
		String username=null;
		String password=null;
		String sql=null;
		HttpSession session=req.getSession();
		
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "你没有权限增加数据";
//		}
		
		if(StringUtils.isBlank(req.getParameter("loginname"))){
			return "登录名不能为空";
		}else{
			loginname=req.getParameter("loginname");
		}
		if(StringUtils.isBlank(req.getParameter("username"))){
			username="";
		}else{
			username=req.getParameter("username");
		}
		if(StringUtils.isBlank(req.getParameter("password"))){
			return "密码不能为空";
		}else{
			password=req.getParameter("password");
		}
		String password_des="";
		try {
			password_des = desUtils.encrypt(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql="insert into sys_users(loginname,username,password,browserpath,remark,pa_screen,state,level,pa_screen_win) values(?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{loginname,username,password_des,req.getParameter("browserpath"),
				req.getParameter("remark"),req.getParameter("pa_screen"),1,2,req.getParameter("pa_screen_win")});
		return "ok";
	}
	
	public String usersdel(HttpServletRequest req){
		int userid=0;
		String sql=null;
		HttpSession session=req.getSession();
		
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "你没有权限删除数据";
//		}
		
		if(req.getSession().getAttribute("userid").equals(req.getParameter("userid"))){
			return "不允许删除自己的账户";
		}
		if(StringUtils.isBlank(req.getParameter("userid"))){
			return "未找到用户ID";
		}else{
			userid=Integer.parseInt(req.getParameter("userid"));
		}
		
		sql="delete from sys_users where userid=? ";
		jdbcTemplate.update(sql,new Object[]{userid});
		return "ok";
	}
	
	public String usersupdate(HttpServletRequest req){
		int userid=0;
		String loginname=null;
		String username=null;
		String password=null;
		String sql=null;
		
		if(StringUtils.isBlank(req.getParameter("userid"))){
			return "未找到用户ID";
		}else{
			userid=Integer.parseInt(req.getParameter("userid"));
		}
		if(StringUtils.isBlank(req.getParameter("loginname"))){
			return "登录名不能为空";
		}else{
			loginname=req.getParameter("loginname");
		}
		if(StringUtils.isBlank(req.getParameter("username"))){
			username="";
		}else{
			username=req.getParameter("username");
		}
		if(StringUtils.isBlank(req.getParameter("password"))){
			return "密码不能为空";
		}else{
			password=req.getParameter("password");
		}
		
		String password_des="";
		try {
			password_des = desUtils.encrypt(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql="update sys_users set username=?,password=?,browserpath=?,remark=?,pa_screen=?,pa_screen_win=? where userid=?";
		jdbcTemplate.update(sql,new Object[]{username,password_des,req.getParameter("browserpath"),
				req.getParameter("remark"),req.getParameter("pa_screen"),req.getParameter("pa_screen_win"),userid});
		return "ok";
	}
}
