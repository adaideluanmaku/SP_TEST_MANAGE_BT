package com.ch.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.easyui.DataGrid;
import com.ch.service.Worksbean;

@Controller
@RequestMapping("/works")
public class WorksAction {
	
	@Autowired
	Worksbean worksbean;
	
	@RequestMapping("/works")
	public ModelAndView works(){
		return new ModelAndView("/works/works");
	}
	
	@RequestMapping("/workslist")
	@ResponseBody
	public DataGrid learnlist(HttpServletRequest req){
		DataGrid datagrid=worksbean.workslist(req);
		return datagrid;
	}
	
	@RequestMapping("/worksadd")
	@ResponseBody
	public void worksadd(HttpServletRequest req){
		worksbean.worksadd(req);
	}
	
	@RequestMapping("/users")
	@ResponseBody
	public List users(HttpServletRequest req){
		List userlist=null;
		userlist=worksbean.users(req);
		return userlist;
	}
	
	@RequestMapping("/worksdel")
	@ResponseBody
	public void worksdel(HttpServletRequest req){
		worksbean.worksdel(req);
	}
	
	@RequestMapping("/worksupdate")
	@ResponseBody
	public void worksupdate(HttpServletRequest req){
		worksbean.worksupdate(req);
	}
}
