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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.service.Zfxmbean;
import com.ch.sqlserverpasshis.Date_to_Sqlserver1;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zfxm")
public class ZfxmAction {
	@Autowired
	Zfxmbean zfxmbean;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ResponseBody
	@RequestMapping("/team_query")
	public DataGrid team_query(HttpServletRequest req) {
		DataGrid datagrid =zfxmbean.team_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/team_add",produces = "text/html;charset=UTF-8")
	public String team_add(HttpServletRequest req) {
		return zfxmbean.team_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/team_del",produces = "text/html;charset=UTF-8")
	public String team_del(HttpServletRequest req,@RequestParam(value = "teamids[]")  Integer[]  teamids) {
		return zfxmbean.team_del(req,teamids);
	}
	
	@ResponseBody
	@RequestMapping(value="/team_update",produces = "text/html;charset=UTF-8")
	public String team_update(HttpServletRequest req) {
		return zfxmbean.team_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/project_query")
	public DataGrid project_query(HttpServletRequest req) {
		DataGrid datagrid =zfxmbean.project_query(req);
		return datagrid;
	}
	
	//下拉单
	@RequestMapping("/_select2")
	@ResponseBody
	public List _select2(HttpServletRequest req){
		List list=new ArrayList();
		String sql =null; 
		String searchstr="";
		int teamid=0;
		if(StringUtils.isNotBlank(req.getParameter("searchstr"))){
			searchstr=req.getParameter("searchstr");
		}
		
		sql="select teamid as id,teamname as text from zfxm_team where teamname like ? ";
		List teamlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		
		if(StringUtils.isNotBlank(req.getParameter("teamid"))){
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		
		List projectlist=null;
		if(teamid>0){
			sql="select a.projectid as id , a.projectname as text from zfxm_project a, zfxm_team b where "
					+ " a.teamid=b.teamid and b.teamid=? and projectname like ? order by a.projectname asc";
			projectlist=jdbcTemplate.queryForList(sql,new Object[]{teamid,"%"+searchstr+"%"});
		}else{
			sql="select projectid as id ,projectname as text from zfxm_project where projectname like ? order by projectname asc";
			projectlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		}
		
		Map map= new HashMap();
		map.put("projectlist", projectlist);
		map.put("teamlist", teamlist);
		list.add(map);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/project_add",produces = "text/html;charset=UTF-8")
	public String project_add(HttpServletRequest req) {
		return zfxmbean.project_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_del",produces = "text/html;charset=UTF-8")
	public String project_del(HttpServletRequest req,@RequestParam(value = "projectids[]")  Integer[]  projectids) {
		return zfxmbean.project_del(req,projectids);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_update",produces = "text/html;charset=UTF-8")
	public String project_update(HttpServletRequest req) {
		return zfxmbean.project_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/testmng_query")
	public DataGrid testmng_query(HttpServletRequest req) {
		DataGrid datagrid =zfxmbean.testmng_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_add",produces = "text/html;charset=UTF-8")
	public String testmng_add(HttpServletRequest req) {
		return zfxmbean.testmng_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_del",produces = "text/html;charset=UTF-8")
	public String testmng_del(HttpServletRequest req,@RequestParam(value = "testids[]")  Integer[]  testids) {
		return zfxmbean.testmng_del(req,testids);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_update",produces = "text/html;charset=UTF-8")
	public String testmng_update(HttpServletRequest req) {
		return zfxmbean.testmng_update(req);
	}
	
	//复制team列表数据
	@RequestMapping(value="/testmng_copy", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmng_copy(HttpServletRequest req){
		return zfxmbean.testmng_copy(req);
	}
}
