package com.ch.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Passbean;
import com.ch.service.Sysmanagebean;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sysmanage")
public class SysmanageAction {
	@Autowired
	Sysmanagebean sysmanagebean;
	
	@ResponseBody
	@RequestMapping("/server_query")
	public DataGrid server_query(HttpServletRequest req) {
		DataGrid datagrid =sysmanagebean.server_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/server_add",produces = "text/html;charset=UTF-8")
	public String server_add(HttpServletRequest req) {
		return sysmanagebean.server_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/server_del",produces = "text/html;charset=UTF-8")
	public String server_del(HttpServletRequest req,@RequestParam(value = "serverids[]")  Integer[]  serverids) {
		return sysmanagebean.server_del(req,serverids);
	}
	
	@ResponseBody
	@RequestMapping(value="/server_update",produces = "text/html;charset=UTF-8")
	public String server_update(HttpServletRequest req) {
		return sysmanagebean.server_update(req);
	}
	
	@RequestMapping("/usersquery")
	@ResponseBody
	public DataGrid usersquery(HttpServletRequest req){
		DataGrid dataGrid=sysmanagebean.usersquery(req);
		return dataGrid;
	}

	@RequestMapping(value="/usersadd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usersadd(HttpServletRequest req){
		return sysmanagebean.usersadd(req);
	}
	
	@RequestMapping(value="/usersdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usersdel(HttpServletRequest req,@RequestParam(value = "userids[]")  Integer[]  userids){
		return sysmanagebean.usersdel(req,userids);
	}
	
	@RequestMapping(value="/usersupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String usersupdate(HttpServletRequest req){
		return sysmanagebean.usersupdate(req);
	}
	
}
