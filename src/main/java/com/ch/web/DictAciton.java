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

import com.ch.service.Dict;
import com.ch.sysuntils.DataGrid;

@Controller
@RequestMapping("/dict")
public class DictAciton {
	@Autowired
	DataGrid dataGrid;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Dict dict;
	
	@ResponseBody
	@RequestMapping("/drug")
	public DataGrid dict_drug(HttpServletRequest req) {
		dataGrid=dict.dict_drug(req);
		return dataGrid;
	}
	
	
}
