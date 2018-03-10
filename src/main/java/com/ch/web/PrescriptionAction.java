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

import com.ch.service.Prescription;
import com.ch.sysuntils.DataGrid;

@Controller
@RequestMapping("/prescription")
public class PrescriptionAction {
	@Autowired
	DataGrid dataGrid;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Prescription prescription;
	
	@ResponseBody
	@RequestMapping("/query")
	public DataGrid prescription_query(HttpServletRequest req) {
		dataGrid=prescription.prescription_query(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public int prescription_add(HttpServletRequest req) {
		return prescription.prescription_add(req);
	}
	
	@ResponseBody
	@RequestMapping("/del")
	public int prescription_del(HttpServletRequest req) {
		return prescription.prescription_del(req);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public int prescription_update(HttpServletRequest req) {
		
		return prescription.prescription_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/prescription_edit")
	public ModelAndView prescription_edit(HttpServletRequest req) {
		ModelAndView mav=new ModelAndView("prescription/prescription_edit");
		System.out.println(req.getParameter("pre_id"));
		String sql="select prescription_json from  prescription where pre_id=? ";
		String prescription_json=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getParameter("pre_id")});
		mav.addObject("prescription_json",prescription_json);
		mav.addObject("pre_id",req.getParameter("pre_id"));
		return mav;
	}
	
}
