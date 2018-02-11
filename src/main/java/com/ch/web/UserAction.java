package com.ch.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.easyui.DataGrid;
import com.ch.service.Userbean;

@Controller
@RequestMapping("/users")
public class UserAction {
	@Autowired
	Userbean userbean;
	@RequestMapping("/users")
	public ModelAndView users(){
		return new ModelAndView("/users/users");
	}
	
	@RequestMapping("/usersquery")
	@ResponseBody
	public DataGrid usersquery(HttpServletRequest req){
		DataGrid dataGrid=userbean.usersquery(req);
		return dataGrid;
	}

	@RequestMapping(value="/usersadd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usersadd(HttpServletRequest req){
		return userbean.usersadd(req);
	}
	
	@RequestMapping(value="/usersdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usersdel(HttpServletRequest req){
		return userbean.usersdel(req);
	}
	
	@RequestMapping(value="/usersupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usersupdate(HttpServletRequest req){
		return userbean.usersupdate(req);
	}
}
