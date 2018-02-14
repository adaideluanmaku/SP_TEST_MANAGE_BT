package com.ch.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.sysuntils.DataGrid;

@Controller
@RequestMapping("/pass")
public class PassAction {
	@Autowired
	DataGrid dataGrid;
	
	@ResponseBody
	@RequestMapping("/pass_team")
	public DataGrid bootstrap_table_test(HttpServletRequest req) {
		System.out.println("bootstrap-table");
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String departmentname=req.getParameter("departmentname"); 
		String statu=req.getParameter("statu");
		
		List lstRes = new ArrayList();
		for (int i = 0; i < 30; i++){
		    Map map = new HashMap();
		    map.put("ID", i);
		    map.put("Name", "销售部" + i);
		    map.put("Level", i);
		    map.put("Desc", "暂无描述信息");
		    lstRes.add(map);
		}
		
		dataGrid.setTotal(lstRes.size()+0L);
		dataGrid.setRows(lstRes);
		return dataGrid;
	}
}
