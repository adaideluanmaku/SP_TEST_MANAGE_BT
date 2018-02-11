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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Servlet Filter implementation class Sysfilter
 */
@Service
public class Sysfilter implements Filter {
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
		
		//过滤session
		HttpSession session=req.getSession();
		if(req.getRequestURI().contains("ws") || req.getRequestURI().contains("/websocket")){
			chain.doFilter(req, res);
			return;
		}
		if(req.getRequestURI().contains(".js") || req.getRequestURI().contains(".css")){
			chain.doFilter(req, res);
			return;
		}
		if(req.getRequestURI().contains("login.jsp") || req.getRequestURI().contains("login/zhuce")){
			chain.doFilter(req, res);
			return;
		}
		
//		if(session.getAttribute("loginname")!=null){
//			if(sys_url.user_url(req)){
//				chain.doFilter(req, res);
//			}
//			return;
//		}else if(req.getRequestURI().contains("login/denglu")){
//			chain.doFilter(req, res);
//			return;
//		}else if(req.getRequestURI().contains("login/checkloginname")){
//			chain.doFilter(req, res);
//			return;
//		}
		if(req.getRequestURI().contains("/login/logout")){
			chain.doFilter(req, res);
			return;
		}else if(req.getRequestURI().contains("login/denglu")){
			chain.doFilter(req, res);
			return;
		}else if(req.getRequestURI().contains("login/checkloginname")){
			chain.doFilter(req, res);
			return;
		}else if(session.getAttribute("loginname")!=null){
			if(sys_url.user_url(req)){
				chain.doFilter(req, res);
			}
			return;
		}
		
		request.getRequestDispatcher("/login.jsp").forward(request, response);
//		res.sendRedirect("/SP_TEST_MANAGE_BT/login.jsp");
		
//		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
