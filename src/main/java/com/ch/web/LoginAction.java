package com.ch.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Loginbean;
import com.ch.service.SocketBean;
import com.ch.sysuntils.User;

@Controller
@RequestMapping("/login")
public class LoginAction {
	@Autowired
	private Loginbean loginbean;
	
	@Autowired
//	SocketBean socketBean;
//	Map<Long, User> sessionuser = new HashMap<Long, User>();
	
	private static Logger log = Logger.getLogger(LoginAction.class);
	private ModelAndView mverr;
//	Map<Long, User> users = new HashMap<Long, User>();//将登录的用户对象User放入到Users-Map中，后台推送使用
	
	@RequestMapping("/denglu")
	public ModelAndView Denglu(User user,HttpServletRequest req,HttpServletResponse res){
		HttpSession session=req.getSession();
		
		//防止页面退出后，通过浏览器后退到登录状态
//		res.setHeader("Cache-Control","no-cache");//不对页面进行缓存，再次访问时将从服务器重新获取最新版本
		res.setHeader("Cache-Control","no-store");//任何情况下都不缓存页面
		res.setHeader("Pragma","no-cache");//HTTP 1.0 向后兼容
		res.setDateHeader("Expires", 0);//使缓存过期
		
		//重新登录验证
		if(StringUtils.isNotBlank(req.getParameter("loginname")) && StringUtils.isNotBlank(req.getParameter("password"))){
			//登录操作
			if(loginbean.Denglu(user,req.getParameter("loginname"),req.getParameter("password"))){
				session.setAttribute("userid", user.getUserid());
				session.setAttribute("loginname", user.getLoginname());
				
				//保存用户到MAP中，websocket推送使用
//				sessionuser.put(user.getUserid(), user);
				
				return new ModelAndView("test_manage");
			}else{
				mverr= new ModelAndView("forward:/login.jsp");//转发forward,重定向redirect
				mverr.addObject("err", req.getParameter("loginname")+"账户或密码错误");
				return mverr;
			}
		}
		
		//用户已经登录，直接可进入系统
		if(session.getAttribute("loginname")!=null){
			mverr= new ModelAndView("test_manage");
			return mverr;
		}
		mverr= new ModelAndView("forward:/login.jsp");
		mverr.addObject("err", "请输入账户登录");
		return mverr;
	}
	
	@RequestMapping(value="/zhuce", produces = "text/html;charset=UTF-8")
	@ResponseBody//返回客户端json数据，如果不设置就是返回整个页面
	public String Zhuce(HttpServletRequest req) {
		String rsmessage=null;
		if("".equals(req.getParameter("loginname").toString()) || "".equals(req.getParameter("username").toString()) || "".equals(req.getParameter("password").toString())){
			System.err.println("信息填写不完整"+req.getParameter("loginname").toString());
			log.debug("信息填写不完整:"+req.getParameter("loginname").toString());
			return rsmessage="信息填写不完整";
		}
		rsmessage=loginbean.Zhuce(req.getParameter("loginname").toString(),req.getParameter("username").toString(),req.getParameter("password").toString());
        return rsmessage;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest req){
		ModelAndView mv=new ModelAndView("forward:/login.jsp");
		HttpSession session=req.getSession();
//		session.removeAttribute("loginname");
		session.invalidate();//清空session所有属性，类似销毁
		return mv;
	}
	
	@RequestMapping("/checkloginname")
	@ResponseBody
	public boolean checkloginname(HttpServletRequest req){
		boolean check=false;
		check=loginbean.checkloginname(req);
		return check;
	}
}
