package com.ch.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Executable;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ch.dao.DataBaseType;
import com.ch.sysuntils.AesUtils;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.DesUtils;
import com.ch.sysuntils.Select2;
import com.ch.sysuntils.User;
import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
public class Sysmanagebean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	AesUtils aesUtils;
	
	@Autowired
	DesUtils desUtils;
	
	public DataGrid server_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		String servername="";
		if(StringUtil.isNotBlank(req.getParameter("searchdata"))){
			servername=req.getParameter("searchdata");
		} 
		
		sql="select * from serverip where servername like ? order by servername asc limit "+offset+","+limit;
//		log.debug(sql);
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+servername+"%"});
		
		sql="select count(*) from serverip where servername like ? ";
		int count =jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+servername+"%"});
				
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public String server_add(HttpServletRequest req){
		String servername="";
		String serveraddress="";
		
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("servername"))){
			return "团队名称不能为空";
		}else{
			servername=req.getParameter("servername");
		}
		if(StringUtils.isNotBlank(req.getParameter("serveraddress"))){
			serveraddress=req.getParameter("serveraddress");
		} 
		
		int rsl=0;
		sql="select count(1) from serverip where servername=? ";
		rsl=jdbcTemplate.queryForObject(sql,int.class,new Object[]{servername});
		if(rsl>0){
			return "团队名称不能重复";
		}
		
		sql="insert into serverip(servername,serveraddress,iptype) values(?,?,3)";
		jdbcTemplate.update(sql,new Object[]{servername,serveraddress});
		return "ok";
	}
	
	public String server_del(HttpServletRequest req,Integer[]  serverids){
		int serverid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(serverids);
		
		if(list.size()==0){
			return "teamid不能为空";
		}
		
		for(int i=0;i<list.size();i++){
			serverid=list.get(i);
			
			sql="delete from serverip where serverid=? ";
			jdbcTemplate.update(sql,new Object[]{serverid});
		}
		
		return "ok";
	}
	
	public String server_update(HttpServletRequest req){
		String servername="";
		int serverid=0;
		String serveraddress="";
		String sql="";
		if(StringUtils.isBlank(req.getParameter("serverid"))){
			return "serverid不能为空";
		}else{
			serverid=Integer.parseInt(req.getParameter("serverid"));
		}
		if(StringUtils.isBlank(req.getParameter("servername"))){
			return "servername不能为空";
		}else{
			servername=req.getParameter("servername");
		}
		if(StringUtils.isNotBlank(req.getParameter("serveraddress"))){
			serveraddress=req.getParameter("serveraddress");
		} 
		
		sql="select count(*) from serverip where serverid<>? and servername=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{serverid,servername});
		if(sum>0){
			return "团队名称不能重复";
		}
		
		sql="update serverip set servername=?,serveraddress=? where serverid=?";
		jdbcTemplate.update(sql,new Object[]{servername,serveraddress,serverid});
		
		DataBaseType.databaseidbak=0;
		return "ok";
	}
	
	public DataGrid usersquery(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int count=0;
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		String loginname="";
		HttpSession session=req.getSession();
		
		if(StringUtils.isNotBlank(req.getParameter("loginname"))){
			loginname=req.getParameter("loginname");
		}
		
		//查询权限控制
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		sql="select level from sys_users where userid=?";
		int level=jdbcTemplate.queryForObject(sql,int.class,new Object[]{user.getUserid()});
		
		if(level==1){
			sql="select count(*) from sys_users where loginname like ? ";
			count=jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+loginname+"%"});
			
//			Date time=new Date();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			sql="select * from sys_users where loginname like ? limit "+offset+","+limit;
			lstRes=jdbcTemplate.queryForList(sql,new Object[]{"%"+loginname+"%"});
		}else{
			sql="select count(*) from sys_users where loginname like ? and userid=?";
			count=jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+loginname+"%",user.getUserid()});
			
			sql="select * from sys_users where loginname like ? and userid=? limit "+offset+","+limit;
			lstRes=jdbcTemplate.queryForList(sql,new Object[]{"%"+loginname+"%",user.getUserid()});
		}
		
		List lstRes1 = new ArrayList(); 
		for(int i=0;i<lstRes.size();i++){
			Map map=(Map)lstRes.get(i);
//			String password_aes=new String(aesUtils.decrypt(map.get("password").toString()));
			String password_des="";
			try {
				password_des=new String(desUtils.decrypt(map.get("password").toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("password", password_des);
			lstRes1.add(map);
		}
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes1);
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
		sql="insert into sys_users(loginname,username,password,remark,state,level) values(?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{loginname,username,password_des,
				req.getParameter("remark"),req.getParameter("state"),2});
		return "ok";
	}
	
	public String usersdel(HttpServletRequest req,Integer[]  userids){
		int userid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(userids);
		if(list.size()==0){
			return "userids不能为空";
		}
		User user= new User();
		user=(User)req.getSession().getAttribute("user");
		
		sql="delete from sys_users where userid=? ";
		for(int i=0;i<list.size();i++){
			userid=list.get(i);
			if(user.getUserid().equals(userid)){
				continue;
			}
			jdbcTemplate.update(sql,new Object[]{userid});
		}
		
		return "ok";
	}
	
	public String usersupdate(HttpServletRequest req){
		int userid=0;
		String loginname="";
		String username="";
		String password="";
		String sql=null;
		int state=0;
		
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
		if(StringUtils.isNotBlank(req.getParameter("username"))){
			username=req.getParameter("username");
		}
		if(StringUtils.isBlank(req.getParameter("password"))){
			return "密码不能为空";
		}else{
			password=req.getParameter("password");
		}
		if(StringUtils.isNotBlank(req.getParameter("state"))){
			state=Integer.parseInt(req.getParameter("state"));
		}
		String password_des="";
		try {
			password_des = desUtils.encrypt(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql="update sys_users set username=?,password=?,remark=?,state=? where userid=?";
		jdbcTemplate.update(sql,new Object[]{username,password_des,
				req.getParameter("remark"),state,userid});
		return "ok";
	}
	
}
