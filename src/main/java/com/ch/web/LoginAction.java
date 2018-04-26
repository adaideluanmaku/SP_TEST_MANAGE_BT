package com.ch.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Loginbean;
import com.ch.sysuntils.User;

@Controller
@RequestMapping("/login")
public class LoginAction {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Loginbean loginbean;
	
	private static Logger log = Logger.getLogger(LoginAction.class);
	private ModelAndView mverr;
	
	@RequestMapping("/login")
	public ModelAndView Denglu(User user,HttpServletRequest req,HttpServletResponse res){
		System.out.println(req.getSession().getId());
		//销毁旧session
		req.getSession().invalidate();
		
		HttpSession session=req.getSession();
		System.out.println(req.getSession().getId());
		String sessionid=session.getId();
		
		//防止页面退出后，通过浏览器后退到登录状态
//		res.setHeader("Cache-Control","no-cache");//不对页面进行缓存，再次访问时将从服务器重新获取最新版本
		res.setHeader("Cache-Control","no-store");//任何情况下都不缓存页面
		res.setHeader("Pragma","no-cache");//HTTP 1.0 向后兼容
		res.setDateHeader("Expires", 0);//使缓存过期
		
		if(StringUtils.isBlank(user.getLoginname())){
			mverr= new ModelAndView("forward:/login.jsp");
			mverr.addObject("loginerr", "请输入用户名登录");
		}else{
			List userlist=loginbean.Denglu(user.getLoginname(),user.getPassword());
			if(userlist.size()==1){
				Map usermap=(Map)userlist.get(0);
				session.setAttribute("loginname", usermap.get("loginname"));
				session.setAttribute("userid", usermap.get("userid"));
				session.setAttribute("sessionid", sessionid);
				
				mverr= new ModelAndView("homepage");
				mverr.addObject("loginname", usermap.get("loginname"));
			}else{
				mverr= new ModelAndView("forward:/login.jsp");
				mverr.addObject("loginerr", "不存在的用户名");
			}
		}
		
		return mverr;
	}
	
	@RequestMapping(value="/register", produces = "text/html;charset=UTF-8")
	@ResponseBody//返回客户端json数据，如果不设置就是返回整个页面
	public String Zhuce(HttpServletRequest req) {
		String rsmessage=null;
		rsmessage=loginbean.Zhuce(req);
        return rsmessage;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest req){
		ModelAndView mv=new ModelAndView("forward:/login.jsp");
		HttpSession session=req.getSession(false);
		//false代表：不创建session对象，只是从request中获取。  
//		session.removeAttribute("loginname");
		session.invalidate();//清空session所有属性，类似销毁
		return mv;
	}
	
}
