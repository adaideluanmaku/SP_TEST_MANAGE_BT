package com.ch.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Dictbean;
import com.ch.sysuntils.DataGrid;

@Controller
@RequestMapping("/dict")
public class DictAciton {
	@Autowired
	DataGrid dataGrid;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Dictbean dict;
	
	@ResponseBody
	@RequestMapping("/drug")
	public DataGrid dict_drug(HttpServletRequest req) {
		dataGrid=dict.dict_drug(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/route")
	public DataGrid dict_route(HttpServletRequest req) {
		dataGrid=dict.dict_route(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/fre")
	public DataGrid dict_fre(HttpServletRequest req) {
		dataGrid=dict.dict_fre(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/dept")
	public DataGrid dict_dept(HttpServletRequest req) {
		dataGrid=dict.dict_dept(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/doctor")
	public DataGrid dict_doctor(HttpServletRequest req) {
		dataGrid=dict.dict_doctor(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/allergen")
	public DataGrid dict_allergen(HttpServletRequest req) {
		dataGrid=dict.dict_allergen(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/disease")
	public DataGrid dict_disease(HttpServletRequest req) {
		dataGrid=dict.dict_disease(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/operation")
	public DataGrid dict_operation(HttpServletRequest req) {
		dataGrid=dict.dict_operation(req);
		return dataGrid;
	}
}
