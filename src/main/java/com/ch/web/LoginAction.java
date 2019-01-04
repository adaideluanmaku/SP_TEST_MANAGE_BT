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
	public ModelAndView Denglu(User user1,HttpServletRequest req,HttpServletResponse res){
		log.debug("调试==>旧的sessionid："+req.getSession().getId());
		//销毁旧session
		req.getSession().invalidate();
		
		HttpSession session=req.getSession();
		log.debug("调试==>新的sessionid："+req.getSession().getId());
		String sessionid=session.getId();
		
		//防止页面退出后，通过浏览器后退到登录状态
//		res.setHeader("Cache-Control","no-cache");//不对页面进行缓存，再次访问时将从服务器重新获取最新版本
		res.setHeader("Cache-Control","no-store");//任何情况下都不缓存页面
		res.setHeader("Pragma","no-cache");//HTTP 1.0 向后兼容
		res.setDateHeader("Expires", 0);//使缓存过期
		
		//获取IP
		String ip=null;
		if(StringUtils.isNotBlank(req.getHeader("X-Real-IP"))){
			System.out.println("获取真实IP："+req.getHeader("X-Real-IP"));
			ip=req.getHeader("X-Real-IP");
		}else{
			System.out.println("获取真实IP："+req.getRemoteAddr());
			ip=req.getRemoteAddr();
		}
		
		if(StringUtils.isBlank(user1.getLoginname())){
			mverr= new ModelAndView("forward:/login.jsp");
			mverr.addObject("loginerr", "请输入用户名登录");
			log.debug("调试==>请输入用户名登录");
		}else{
			List userlist=loginbean.Denglu(user1.getLoginname(),user1.getPassword());
			if(userlist.size()==1){
				Map usermap=(Map)userlist.get(0);
				User user=new User();
				user.setIp(ip);
				user.setLoginname(usermap.get("loginname").toString());
				user.setUserid(Integer.parseInt(usermap.get("userid").toString())+0L);
				user.setPassword(usermap.get("password").toString());
				user.setSessionid(sessionid);
				user.setUsername(usermap.get("username").toString());
				user.setLevel(Integer.parseInt(usermap.get("level").toString()));
				session.setAttribute("user", user);
				mverr= new ModelAndView("homepage");
				mverr.addObject("loginname", usermap.get("loginname"));
				log.info("======>用户登录:"+usermap.get("loginname"));
			}else{
				mverr= new ModelAndView("forward:/login.jsp");
				mverr.addObject("loginerr", "不存在的用户名");
				log.debug("调试==>不存在的用户名");
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
		session.removeAttribute("user");
		session.invalidate();//清空session所有属性，类似销毁
		return mv;
	}
	
}
