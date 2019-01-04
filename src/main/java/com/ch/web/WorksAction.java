package com.ch.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.service.Worksbean;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/works")
public class WorksAction {
	@Autowired
	Worksbean worksbean;
	
	@ResponseBody
	@RequestMapping("/zhouhuibao_query")
	public DataGrid zhouhuibao_query(HttpServletRequest req) {
		DataGrid datagrid =worksbean.zhouhuibao_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/zhouhuibao_add",produces = "text/html;charset=UTF-8")
	public String zhouhuibao_add(HttpServletRequest req) {
		return worksbean.zhouhuibao_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/zhouhuibao_del",produces = "text/html;charset=UTF-8")
	public String zhouhuibao_del(HttpServletRequest req,@RequestParam(value = "workzhouhuibaoids[]")  Integer[]  workzhouhuibaoids) {
		return worksbean.zhouhuibao_del(req,workzhouhuibaoids);
	}
	
	@ResponseBody
	@RequestMapping(value="/zhouhuibao_update",produces = "text/html;charset=UTF-8")
	public String zhouhuibao_update(HttpServletRequest req) {
		return worksbean.zhouhuibao_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/yuehuibao_query")
	public DataGrid yuehuibao_query(HttpServletRequest req) {
		DataGrid datagrid =worksbean.yuehuibao_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/yuehuibao_add",produces = "text/html;charset=UTF-8")
	public String yuehuibao_add(HttpServletRequest req) {
		return worksbean.yuehuibao_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/yuehuibao_del",produces = "text/html;charset=UTF-8")
	public String yuehuibao_del(HttpServletRequest req,@RequestParam(value = "workyuehuibaoids[]")  Integer[]  workyuehuibaoids) {
		return worksbean.yuehuibao_del(req,workyuehuibaoids);
	}
	
	@ResponseBody
	@RequestMapping(value="/yuehuibao_update",produces = "text/html;charset=UTF-8")
	public String yuehuibao_update(HttpServletRequest req) {
		return worksbean.yuehuibao_update(req);
	}
}
