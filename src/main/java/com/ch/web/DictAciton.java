package com.ch.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
	
	@ResponseBody
	@RequestMapping("/exam")
	public DataGrid dict_exam(HttpServletRequest req) {
		dataGrid=dict.dict_exam(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/lab")
	public DataGrid dict_lab(HttpServletRequest req) {
		dataGrid=dict.dict_lab(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/hospital")
	public DataGrid mc_hospital(HttpServletRequest req) {
		dataGrid=dict.mc_hospital(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/database")
	public DataGrid database(HttpServletRequest req) {
		dataGrid=dict.database(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/select2")
	public List select1(HttpServletRequest req) {
		List list=new ArrayList();
		Map map= new HashMap();
		String searchstr="";
		if(StringUtils.isNotBlank(req.getParameter("searchstr"))){
			searchstr=req.getParameter("searchstr");
		}
		
		String sql = "select mhiscode as id,hisname as text from mc_hospital_match_relation where hisname like ? order by hiscode asc";
		List resultlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		map.put("hospital", resultlist);
		
		sql = "select table_name as id, table_name  as text " + 
				"from information_schema.tables " + 
				"where table_schema='sp_test_manage_bt' " + 
				"and (table_name like '%dict%' or table_name like '%mc_user%' or table_name like '%mc_hospital%') and table_name like ? ";
		resultlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		map.put("tables_name", resultlist);
		
		sql="select databaseid as id,name as text from sys_database a where name like ?";
		resultlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		map.put("database", resultlist);
		
		list.add(map);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/insertdatabase",produces = "text/html;charset=UTF-8")
	public String insertdatabase(HttpServletRequest req) {
		return dict.insertdatabase(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatedatabase",produces = "text/html;charset=UTF-8")
	public String updatedatabase(HttpServletRequest req) {
		return dict.updatedatabase(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletedatabase",produces = "text/html;charset=UTF-8")
	public String deletedatabase(HttpServletRequest req) {
		return dict.deletedatabase(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/inserthospital",produces = "text/html;charset=UTF-8")
	public String inserthospital(HttpServletRequest req) {
		return dict.inserthospital(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatehospital",produces = "text/html;charset=UTF-8")
	public String updatehospital(HttpServletRequest req) {
		return dict.updatehospital(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletehospital",produces = "text/html;charset=UTF-8")
	public String deletehospital(HttpServletRequest req) {
		return dict.deletehospital(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertdrug",produces = "text/html;charset=UTF-8")
	public String insertdrug(HttpServletRequest req) {
		return dict.insertdrug(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatedrug",produces = "text/html;charset=UTF-8")
	public String updatedrug(HttpServletRequest req) {
		return dict.updatedrug(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletedrug",produces = "text/html;charset=UTF-8")
	public String deletedrug(HttpServletRequest req) {
		return dict.deletedrug(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertroute",produces = "text/html;charset=UTF-8")
	public String insertroute(HttpServletRequest req) {
		return dict.insertroute(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updateroute",produces = "text/html;charset=UTF-8")
	public String updateroute(HttpServletRequest req) {
		return dict.updateroute(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteroute",produces = "text/html;charset=UTF-8")
	public String deleteroute(HttpServletRequest req) {
		return dict.deleteroute(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertfre",produces = "text/html;charset=UTF-8")
	public String insertfre(HttpServletRequest req) {
		return dict.insertfre(req);
	}
	
//	@ResponseBody
//	@RequestMapping(value="/updatefre",produces = "text/html;charset=UTF-8")
//	public String updatefre(HttpServletRequest req) {
//		return dict.updatefre(req);
//	}
	
	@ResponseBody
	@RequestMapping(value="/deletefre",produces = "text/html;charset=UTF-8")
	public String deletefree(HttpServletRequest req) {
		return dict.deletefre(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertdept",produces = "text/html;charset=UTF-8")
	public String insertdept(HttpServletRequest req) {
		return dict.insertdept(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatedept",produces = "text/html;charset=UTF-8")
	public String updatedept(HttpServletRequest req) {
		return dict.updatedept(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletedept",produces = "text/html;charset=UTF-8")
	public String deletedept(HttpServletRequest req) {
		return dict.deletedept(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertdoctor",produces = "text/html;charset=UTF-8")
	public String insertdoctor(HttpServletRequest req) {
		return dict.insertdoctor(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatedoctor",produces = "text/html;charset=UTF-8")
	public String updatedoctor(HttpServletRequest req) {
		return dict.updatedoctor(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletedoctor",produces = "text/html;charset=UTF-8")
	public String deletedoctor(HttpServletRequest req) {
		return dict.deletedoctor(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertallergen",produces = "text/html;charset=UTF-8")
	public String insertallergen(HttpServletRequest req) {
		return dict.insertallergen(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updateallergen",produces = "text/html;charset=UTF-8")
	public String updateallergen(HttpServletRequest req) {
		return dict.updateallergen(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteallergen",produces = "text/html;charset=UTF-8")
	public String deleteallergen(HttpServletRequest req) {
		return dict.deleteallergen(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertdisease",produces = "text/html;charset=UTF-8")
	public String insertdisease(HttpServletRequest req) {
		return dict.insertdisease(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatedisease",produces = "text/html;charset=UTF-8")
	public String updatedisease(HttpServletRequest req) {
		return dict.updatedisease(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletedisease",produces = "text/html;charset=UTF-8")
	public String deletedisease(HttpServletRequest req) {
		return dict.deletedisease(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertoperation",produces = "text/html;charset=UTF-8")
	public String insertoperation(HttpServletRequest req) {
		return dict.insertoperation(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updateoperation",produces = "text/html;charset=UTF-8")
	public String updateoperation(HttpServletRequest req) {
		return dict.updateoperation(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteoperation",produces = "text/html;charset=UTF-8")
	public String deleteoperation(HttpServletRequest req) {
		return dict.deleteoperation(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertexam",produces = "text/html;charset=UTF-8")
	public String insertexam(HttpServletRequest req) {
		return dict.insertexam(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updateexam",produces = "text/html;charset=UTF-8")
	public String updateexam(HttpServletRequest req) {
		return dict.updateexam(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteexam",produces = "text/html;charset=UTF-8")
	public String deleteexam(HttpServletRequest req) {
		return dict.deleteexam(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/insertlab",produces = "text/html;charset=UTF-8")
	public String insertlab(HttpServletRequest req) {
		return dict.insertlab(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/updatelab",produces = "text/html;charset=UTF-8")
	public String updatelab(HttpServletRequest req) {
		return dict.updatelab(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/deletelab",produces = "text/html;charset=UTF-8")
	public String deletelab(HttpServletRequest req) {
		return dict.deletelab(req);
	}
}
