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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Prescriptionbean;
import com.ch.sysuntils.DataGrid;

@Controller
@RequestMapping("/prescription")
public class PrescriptionAction {
	@Autowired
	DataGrid dataGrid;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Prescriptionbean prescription;
	
	@ResponseBody
	@RequestMapping("/query")
	public DataGrid prescription_query(HttpServletRequest req) {
		dataGrid=prescription.prescription_query(req);
		return dataGrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/add",produces = "text/html;charset=UTF-8")
	public String prescription_add(HttpServletRequest req) {
		return prescription.prescription_add(req);
	}
	
	@ResponseBody
	@RequestMapping("/del")
	public String prescription_del(HttpServletRequest req,@RequestParam(value = "pre_id[]")  Integer[]  pre_id) {
		return prescription.prescription_del(req,pre_id);
	}
	
	@ResponseBody
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	public String prescription_update(HttpServletRequest req) {
		return prescription.prescription_update(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/prescription_edit",produces = "text/html;charset=UTF-8")
	public ModelAndView prescription_edit(HttpServletRequest req) {
		ModelAndView mav=new ModelAndView("prescription/prescription_edit");
		int prescriptiontype=0;
		if(StringUtils.isNoneBlank(req.getParameter("prescriptiontype"))){
			prescriptiontype=Integer.parseInt(req.getParameter("prescriptiontype").toString());
		}
		if(req.getSession().getAttribute("loginname")==null || prescriptiontype==0){
			return mav;
		}

		if(prescriptiontype==1){
			String sql="select prescription_json from  prescription where pre_id=? ";
			String prescription_json=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getParameter("pre_id")});
			mav.addObject("prescription_json",prescription_json);
			mav.addObject("pre_id",req.getParameter("pre_id"));
			mav.addObject("patientname",req.getParameter("patientname"));
		}
		
		if(prescriptiontype==2){
			String sql="select testin from  pa_testmng where testid=? ";
			String prescription_json=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getParameter("testid")});
			mav.addObject("prescription_json",prescription_json);
			mav.addObject("pre_id",req.getParameter("testid"));
			mav.addObject("patientname",req.getParameter("testname"));
		}
		
		if(prescriptiontype==3){
			String sql="select testin from  pass_testmng where testid=? ";
			String prescription_json=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getParameter("testid")});
			mav.addObject("prescription_json",prescription_json);
			mav.addObject("pre_id",req.getParameter("testid"));
			mav.addObject("patientname",req.getParameter("testname"));
		}
		
		if(prescriptiontype==4){
			String sql="select testin from  zfxm_testmng where testid=? ";
			String prescription_json=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getParameter("testid")});
			mav.addObject("prescription_json",prescription_json);
			mav.addObject("pre_id",req.getParameter("testid"));
			mav.addObject("patientname",req.getParameter("testname"));
		}
		
		mav.addObject("prescriptiontype",prescriptiontype);
		return mav;
	}
	
}
