package com.ch.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ch.service.SocketBean;
import com.ch.sysuntils.Message;
import com.ch.websocket.MyWebSocketHandler;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/websocket")
public class SocketAciton {

	@Autowired
	SocketBean socketBean;
	
	@Resource
	MyWebSocketHandler myWebSocketHandler;

	//给未读信息做标记
	@RequestMapping("/messagesstate")
	@ResponseBody
	public Map messagesstate(HttpServletRequest req){
		Map map=null;
		map=socketBean.messagesstate(Integer.parseInt(req.getParameter("userid")));
		return map;
	}
	
	//获取聊天记录
	@RequestMapping("/messages")
	@ResponseBody
	public List getmessage(HttpServletRequest req){
		List list=null;
		list=socketBean.getmessage(Integer.parseInt(req.getParameter("userid")), Integer.parseInt(req.getParameter("touserid")));
		return list;
	}
	
	//给某用户发送消息
	@RequestMapping("/touser")
	@ResponseBody
	public void touser(HttpServletRequest req){
		socketBean.touser(req);
	}
	
	//获取系统通知记录
	@RequestMapping("/sysmessages")
	@ResponseBody
	public List sysmessages(HttpServletRequest req){
		List list=null;
		list=socketBean.sys_getmessage(req);
		return list;
	}
	
	// 发布系统广播（群发）
	@ResponseBody
	@RequestMapping("/broadcasts")
	public String sysmessage(HttpServletRequest req) throws IOException {
		socketBean.sysmessage(req);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	// 发布系统广播（群发）刷新用户页面
	@ResponseBody
	@RequestMapping("/sysreload")
	public void sysreload() throws IOException {
		socketBean.sysreload();
	}
}