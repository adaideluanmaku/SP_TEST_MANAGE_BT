package com.ch.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.ch.easyui.DataGrid;
import com.ch.sysuntils.Message;
import com.ch.sysuntils.User;
import com.ch.websocket.MyWebSocketHandler;

import net.sf.json.JSONObject;

@Service
public class SocketBean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	MyWebSocketHandler myWebSocketHandler;
	
	//保存聊天记录messagestate:0未读，1已读
	public void addmessage(Message message){
		String sql=null;
		sql="insert into user_message(loginname,message,userid,inserttime,touserid,messagestate) values(?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{message.getFromName(),message.getText(),
				message.getUid(),message.getDate(),message.getTouid(),0});
	}

	//获取某个用户聊天记录
	public List getmessage(int userid,int touserid){
		String sql=null;
		List list=null;
		sql="update user_message set messagestate=1 where (userid=? and touserid=?) or (userid=? and touserid=?)";
		jdbcTemplate.update(sql,new Object[]{userid,touserid,touserid,userid});
		
		sql="select * from user_message where (userid=? and touserid=?) or (userid=? and touserid=?) order by inserttime asc";
		list=jdbcTemplate.queryForList(sql,new Object[]{userid,touserid,touserid,userid});
		return list;
	}
	
	//给某用户发送消息
	public void touser(HttpServletRequest req){
		Message msg = new Message();
		msg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		msg.setUid(Integer.parseInt(req.getParameter("userid"))+0L);
		msg.setFromName(req.getParameter("fromName"));
		msg.setTouid(Integer.parseInt(req.getParameter("touid"))+0L);
		msg.setText(req.getParameter("text"));

		//将信息保存到数据库
		addmessage(msg);
		
		try {
			myWebSocketHandler.sendMessageToUser(Integer.parseInt(req.getParameter("touid"))+0L,new TextMessage(JSONObject.fromObject(msg).toString()));
		} catch (NumberFormatException | IOException e) {
			System.out.println("消息推送异常");
		}
	}
	
	//给自己传递数据
	public void tostr(HttpServletRequest req){
		Message msg = new Message();
		msg.setDatatype(7);
		msg.setUid(Integer.parseInt(req.getSession().getAttribute("userid").toString())+0L);
		msg.setText(req.getParameter("text"));

		try {
			myWebSocketHandler.sendMessageToUser(msg.getUid(),new TextMessage(JSONObject.fromObject(msg).toString()));
		} catch (NumberFormatException | IOException e) {
			System.out.println("消息推送异常");
		}
	}
	
	//保存系统通知记录messagestate:0未读，1已读
	public void sys_addmessage(Message message) throws DataAccessException, Exception{
		String sql=null;
		List list=null;
//		sql="select userid from sys_users where userid<>?";
//		final List list=jdbcTemplate.queryForList(sql,new Object[]{message.getUid()});
		if(message.getTouid()>0){
			//系统通知某个用户消息
		}else{
			//系统通知所有用户消息
			sql="select userid from sys_users";
			list=jdbcTemplate.queryForList(sql);
		}
		
		//SPRING批处理
		sql="insert into sys_message(loginname,message,userid,inserttime,messagestate,touserid) values(?,?,?,?,?,?)";
		batchInsertRows(sql,list,message);
		
//		jdbcTemplate.update(sql,new Object[]{message.getFromName(),message.getText(),
//				message.getUid(),message.getDate(),0});
	}

	public void batchInsertRows(String sql, final List list,final Message message) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map map=(Map)list.get(i);
				ps.setString(1, message.getFromName());
				ps.setString(2, message.getText());
				ps.setInt(3, Integer.parseInt(message.getUid().toString()));
				ps.setString(4, message.getDate());
				ps.setInt(5, 0);
				ps.setInt(6, Integer.parseInt(map.get("userid").toString()));
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return list.size();
			}
		};
		jdbcTemplate.batchUpdate(sql, setter);
	}

	//获取系统通知记录
	public List sys_getmessage(HttpServletRequest req){
		String sql=null;
		List list=null;
		sql="update sys_message set messagestate=1 where touserid=?";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("userid")});
		
		sql="select userid, '系统通知' as loginname,inserttime,messagestate,message,touserid from sys_message order by inserttime asc";
		list=jdbcTemplate.queryForList(sql);
		return list;
	}
	
	// 发布系统广播（群发）
	public void sysmessage(HttpServletRequest req) {
		Message msg = new Message();
		msg.setDatatype(3);
		msg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		msg.setUid(Integer.parseInt(req.getParameter("userid"))+0L);
		msg.setFromName(req.getParameter("loginname"));
		msg.setTouid(0L);
		msg.setText(req.getParameter("text"));
		
		//将信息保存到数据库
		try {
			sys_addmessage(msg);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		try {
			myWebSocketHandler.sys_message(new TextMessage(JSONObject.fromObject(msg).toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 发布系统广播（群发）强刷页面
	public void sysreload() {
		Message msg = new Message();
		msg.setDatatype(4);
		msg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
		try {
			myWebSocketHandler.sys_message(new TextMessage(JSONObject.fromObject(msg).toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//获取所有用户upanddown:1上线，0下线
	public void getusers(int userid,int upanddown){
		String sql=null;
		List list=null;
		sql="update sys_users set upanddown=? where userid=?";
		jdbcTemplate.update(sql,new Object[]{upanddown,userid});
		sql="select a.userid,a.loginname,a.upanddown from sys_users a group by a.loginname order by a.upanddown desc ";
		list=jdbcTemplate.queryForList(sql);
		
		Message msg=new Message();
		msg.setDatatype(2);
		msg.setUsers(list);
		
		final TextMessage message=new TextMessage(JSONObject.fromObject(msg).toString());
		try {
			myWebSocketHandler.sys_message(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("用户上下线后台通知失败");
		}
	}
	
	//给未读信息做标记
	public Map messagesstate(int userid){
		String sql=null;
		List list=null;
		Map map=new HashMap();
		//用户未读信息
		sql="select userid ,loginname from user_message where messagestate=0 and touserid=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{userid});
		map.put("user_message", list);
		//系统未读信息
		sql="select count(id) from sys_message where messagestate=0 and touserid=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{userid});
		map.put("sys_message", sum);
		return map;
	}
		
	//系统开启后初始化所有用户下线
	public void setusers(){
		String sql=null;
		List list=null;
		sql="update sys_users set upanddown=0";
		jdbcTemplate.update(sql);
	}
	
	// 发布系统广播（群发）
	public void logreload(int userid) {
		Message msg = new Message();
		msg.setDatatype(5);
		msg.setUid(userid+0L);
		msg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
		try {
			myWebSocketHandler.sys_message(new TextMessage(JSONObject.fromObject(msg).toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 发布系统广播（群发）
	public void workreload(int userid) {
		Message msg = new Message();
		msg.setDatatype(6);
		msg.setUid(userid+0L);
		msg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
		try {
			myWebSocketHandler.sys_message(new TextMessage(JSONObject.fromObject(msg).toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
