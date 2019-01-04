package com.ch.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ch.pahis.Mc_dict_allergen;
import com.ch.sysuntils.User;

/**
 * Servlet Filter implementation class Sysfilter
 */
@Service
public class Sysfilter implements Filter {
	private static Logger log = Logger.getLogger(Sysfilter.class);
	@Autowired
	Sys_url sys_url;
    /**
     * Default constructor. 
     */
    public Sysfilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req=(HttpServletRequest) request;//把ServletRequest强转成HttpServletRequest
		HttpServletResponse res=(HttpServletResponse) response;//把ServletResponse强转成HttpServletResponse
		
		String ip=null;
		if(StringUtils.isNotBlank(req.getHeader("X-Real-IP"))){
			System.out.println("获取真实IP："+req.getHeader("X-Real-IP"));
			ip=req.getHeader("X-Real-IP");
		}else{
			System.out.println("获取真实IP："+req.getRemoteAddr());
			ip=req.getRemoteAddr();
		}
		
		System.out.println("用户请求地址："+req.getRequestURI());
		log.debug("用户请求地址："+req.getRequestURI());
		
//		if(req.getRequestURI().contains("ws") || req.getRequestURI().contains("/websocket")){
//			chain.doFilter(req, res);
//			return;
//		}
		
		if(req.getRequestURI().contains(".jpg") || req.getRequestURI().contains(".gif") || req.getRequestURI().contains(".js") || req.getRequestURI().contains(".css")){
			chain.doFilter(req, res);
			return;
		}
		if(req.getRequestURI().contains("login.jsp") || req.getRequestURI().contains("login/zhuce") 
				|| req.getRequestURI().contains("/login/login") || req.getRequestURI().contains("pass_demo.jsp")){
			chain.doFilter(req, res);
			return;
		}
		
		//过滤session，处理sessionid是否变更，如果变更就退出登录
		HttpSession session=req.getSession();
		String new_sessionid=session.getId();
		
		String old_sesseionid="";
		User user=new User();
		if(session.getAttribute("user") !=null){
			user=null;
			user=(User)session.getAttribute("user");
			old_sesseionid=user.getSessionid();
		}
		if(new_sessionid.equals(old_sesseionid)){
			//判断IP是否相同
			if(!ip.equals(user.getIp())){
				res.sendRedirect("/SP_TEST_MANAGE_BT/login.jsp");
		    	return;
			}
			
			//跨站请求伪造
			String referer=req.getHeader("Referer");  
		    if(referer ==null || !referer.trim().contains("SP_TEST_MANAGE_BT")){  
		    	res.sendRedirect("/SP_TEST_MANAGE_BT/login.jsp");
		    	return;
		    }
		    
		    //增加URL权限判断，如果没有权限就不继续执行
		    if(sys_url.user_url(req)){
				chain.doFilter(req, res);
				return;
			}else{
//				res.sendRedirect("/SP_TEST_MANAGE_BT/500.jsp");
				return;
			}
		    
		}
		
		res.sendRedirect("/SP_TEST_MANAGE_BT/login.jsp");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
