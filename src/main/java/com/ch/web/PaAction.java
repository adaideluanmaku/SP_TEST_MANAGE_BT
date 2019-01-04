package com.ch.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.pahisthread.Date_to_Oracle_Thread;
import com.ch.service.Pabean;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;
import com.ch.sysuntils.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pa")
public class PaAction {
	@Autowired
	Pabean pabean;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Date_to_Oracle_Thread date_to_Oracle_Thread;
	private static Logger log = Logger.getLogger(PaAction.class);
	
	@ResponseBody
	@RequestMapping("/team_query")
	public DataGrid team_query(HttpServletRequest req) {
		DataGrid datagrid =pabean.team_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/team_add",produces = "text/html;charset=UTF-8")
	public String team_add(HttpServletRequest req) {
		return pabean.team_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/team_del",produces = "text/html;charset=UTF-8")
	public String team_del(HttpServletRequest req,@RequestParam(value = "teamids[]")  Integer[]  teamids) {
		return pabean.team_del(req,teamids);
	}
	
	@ResponseBody
	@RequestMapping(value="/team_update",produces = "text/html;charset=UTF-8")
	public String team_update(HttpServletRequest req) {
		return pabean.team_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/project_query")
	public DataGrid project_query(HttpServletRequest req) {
		DataGrid datagrid =pabean.project_query(req);
		return datagrid;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/project_add",produces = "text/html;charset=UTF-8")
	public String project_add(HttpServletRequest req) {
		return pabean.project_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_del",produces = "text/html;charset=UTF-8")
	public String project_del(HttpServletRequest req,@RequestParam(value = "projectids[]")  Integer[]  projectids) {
		return pabean.project_del(req,projectids);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_update",produces = "text/html;charset=UTF-8")
	public String project_update(HttpServletRequest req) {
		return pabean.project_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/testmng_query")
	public DataGrid testmng_query(HttpServletRequest req) {
		DataGrid datagrid =pabean.testmng_query(req);
		return datagrid;
	}
	

	@ResponseBody
	@RequestMapping(value="/testmng_add",produces = "text/html;charset=UTF-8")
	public String testmng_add(HttpServletRequest req) {
		return pabean.testmng_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_del",produces = "text/html;charset=UTF-8")
	public String testmng_del(HttpServletRequest req,@RequestParam(value = "testids[]")  Integer[]  testids) {
		return pabean.testmng_del(req,testids);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_update",produces = "text/html;charset=UTF-8")
	public String testmng_update(HttpServletRequest req) {
		return pabean.testmng_update(req);
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
		
		sql="select teamid as id,teamname as text from pa_team where teamname like ? ";
		List teamlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		
		if(StringUtils.isNotBlank(req.getParameter("teamid"))){
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		
		List projectlist=null;
		if(teamid>0){
			sql="select a.projectid as id , a.projectname as text from pa_project a, pa_team b where "
					+ " a.teamid=b.teamid and b.teamid=? and projectname like ? order by a.projectname asc";
			projectlist=jdbcTemplate.queryForList(sql,new Object[]{teamid,"%"+searchstr+"%"});
		}else{
			sql="select projectid as id ,projectname as text from pa_project where projectname like ? order by projectname asc";
			projectlist=jdbcTemplate.queryForList(sql,new Object[]{"%"+searchstr+"%"});
		}
		
		Map map= new HashMap();
		map.put("projectlist", projectlist);
		map.put("teamlist", teamlist);
		list.add(map);
		return list;
	}
	
	@RequestMapping(value="/date_to_oracle",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String date_to_oracle(HttpServletRequest req) throws ClassNotFoundException, SQLException, IOException{
//		if(StringUtils.isBlank(req.getParameter("hiscodes1")) || StringUtils.isBlank(req.getParameter("datetime1"))
//				 || StringUtils.isBlank(req.getParameter("sum_date1")) || StringUtils.isBlank(req.getParameter("count1"))){
//			return "输入数据不完整";
//		}
		String qianfanggaoneng="";
		log.info("======>开始制作HIS数据");
		String hiscodes1=req.getParameter("hiscodes1");
		String datetime1=req.getParameter("datetime1");
		int mz_sum_date1=0;
		if(StringUtils.isNotBlank(req.getParameter("mz_sum_date1"))){
			mz_sum_date1=Integer.parseInt(req.getParameter("mz_sum_date1"));
		};
//		int zy_sum_date1=0;
//		if(StringUtils.isNotBlank(req.getParameter("zy_sum_date1"))){
//			zy_sum_date1=Integer.parseInt(req.getParameter("zy_sum_date1"));
//		};
		int cy_sum_date1=0;
		if(StringUtils.isNotBlank(req.getParameter("cy_sum_date1"))){
			cy_sum_date1=Integer.parseInt(req.getParameter("cy_sum_date1"));
		};
		int countmz=0;
		if(StringUtils.isNotBlank(req.getParameter("countmz"))){
			countmz=Integer.parseInt(req.getParameter("countmz"));
		};
		int countzy=0;
		if(StringUtils.isNotBlank(req.getParameter("countzy"))){
			countzy=Integer.parseInt(req.getParameter("countzy"));
		};
		int countcy=0;
		if(StringUtils.isNotBlank(req.getParameter("countcy"))){
			countcy=Integer.parseInt(req.getParameter("countcy"));
		};
		int mz1=Integer.parseInt(req.getParameter("mz1"));
		int zy1=Integer.parseInt(req.getParameter("zy1"));
		int cy1=Integer.parseInt(req.getParameter("cy1"));
		int dict1=Integer.parseInt(req.getParameter("dict1"));
		int createview1=Integer.parseInt(req.getParameter("createview1"));
		int trunca1=Integer.parseInt(req.getParameter("trunca1"));
		int clear_radio=Integer.parseInt(req.getParameter("clear_radio"));
//		int anlisum=Integer.parseInt(req.getParameter("anlisum"));
		int projectid1=0;
		if(StringUtils.isNotBlank(req.getParameter("projectid1"))){
			projectid1=Integer.parseInt(req.getParameter("projectid1"));
		};
		long startTime = System.currentTimeMillis();
//		int match_scheme1=0;
//		if(StringUtils.isNotBlank(req.getParameter("match_scheme1"))){
//			match_scheme1=Integer.parseInt(req.getParameter("match_scheme1"));
//		};
		String match_scheme1=req.getParameter("match_scheme1");
		int createTB1=Integer.parseInt(req.getParameter("createTB1"));
		//默认=0制作PA数据，=1制作PASS数据(应该是制作肝肾检验值数据使用)
		int passorpa_hisdata1=Integer.parseInt(req.getParameter("passorpa_hisdata1"));
		int cleardict1=Integer.parseInt(req.getParameter("cleardict1"));
		int database=Integer.parseInt(req.getParameter("database"));
//		int mzfenmu=Integer.parseInt(req.getParameter("mzfenmu"));
//		int zyfenmu=Integer.parseInt(req.getParameter("zyfenmu"));
//		int cyfenmu=Integer.parseInt(req.getParameter("cyfenmu"));
		
		User user= new User();
		user = (User)req.getSession().getAttribute("user");
		
		//单线程
//		date_to_Oracle.Rundate(user.getUserid(),hiscodes1, datetime1, mz_sum_date1, countmz,countzy,countcy, mz1, zy1, cy1, dict1, createview1, 
//		trunca1,projectid1,match_scheme1,createTB1,passorpa_hisdata1,cleardict1,clear_radio,database,cy_sum_date1);
		//多线程
		qianfanggaoneng=date_to_Oracle_Thread.Rundate(user.getUserid(),hiscodes1, datetime1, mz_sum_date1, countmz,countzy,countcy, mz1, zy1, cy1, dict1, createview1, 
				trunca1,projectid1,match_scheme1,createTB1,passorpa_hisdata1,cleardict1,clear_radio,database,cy_sum_date1);
		long endTime = System.currentTimeMillis();
//		log.info("======>总耗时："+(endTime-startTime)+"毫秒");
//		return "=========数据制作结束=======总耗时："+(endTime-startTime)+"毫秒";
		return "前方高能！反正很快。。-_-！("+qianfanggaoneng+")";
	}
	
	//pa审查-单个案例
	@RequestMapping(value="/pa_screen", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_screen(HttpServletRequest req){
		return pabean.pa_screen(req);
	}
	
	//pa审查-所有案例
	@RequestMapping(value="/pa_screen_all", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_screen_all(HttpServletRequest req){
		return pabean.pa_screen_all(req);
	}
	
	//pa-redis手动清理
	@RequestMapping(value="/pa_redis_clear_sd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_redis_clear_sd(HttpServletRequest req){
		return pabean.pa_redis_clear_sd(req);
	}
	
	//复制team列表数据
	@RequestMapping(value="/testmng_copy", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmng_copy(HttpServletRequest req){
		return pabean.testmng_copy(req);
	}
}
