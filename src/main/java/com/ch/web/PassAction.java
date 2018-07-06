package com.ch.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Passbean;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pass")
public class PassAction {
	@Autowired
	Passbean passbean;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@ResponseBody
	@RequestMapping("/team_query")
	public DataGrid team_query(HttpServletRequest req) {
		DataGrid datagrid =passbean.team_query(req);
		return datagrid;
	}
	
	@ResponseBody
	@RequestMapping(value="/team_add",produces = "text/html;charset=UTF-8")
	public String team_add(HttpServletRequest req) {
		return passbean.team_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/team_del",produces = "text/html;charset=UTF-8")
	public String team_del(HttpServletRequest req,@RequestParam(value = "teamids[]")  Integer[]  teamids) {
		return passbean.team_del(req,teamids);
	}
	
	@ResponseBody
	@RequestMapping(value="/team_update",produces = "text/html;charset=UTF-8")
	public String team_update(HttpServletRequest req) {
		return passbean.team_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/project_query")
	public DataGrid project_query(HttpServletRequest req) {
		DataGrid datagrid =passbean.project_query(req);
		return datagrid;
	}
	
	//获取team下拉单
	@RequestMapping("/teamgroup")
	@ResponseBody
	public List teamgroup(HttpServletRequest req){
		return passbean.teamgroup(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_add",produces = "text/html;charset=UTF-8")
	public String project_add(HttpServletRequest req) {
		return passbean.project_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_del",produces = "text/html;charset=UTF-8")
	public String project_del(HttpServletRequest req,@RequestParam(value = "projectids[]")  Integer[]  projectids) {
		return passbean.project_del(req,projectids);
	}
	
	@ResponseBody
	@RequestMapping(value="/project_update",produces = "text/html;charset=UTF-8")
	public String project_update(HttpServletRequest req) {
		return passbean.project_update(req);
	}
	
	@ResponseBody
	@RequestMapping("/testmng_query")
	public DataGrid testmng_query(HttpServletRequest req) {
		DataGrid datagrid =passbean.testmng_query(req);
		return datagrid;
	}
	
	//获取project下拉单
	@RequestMapping("/projectgroup")
	@ResponseBody
	public List projectgroup(HttpServletRequest req){
		return passbean.projectgroup(req);
	}

	//获取team下拉单
	@RequestMapping("/modulenamegroup")
	@ResponseBody
	public List modulenamegroup(HttpServletRequest req){
		return passbean.modulenamegroup(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_add",produces = "text/html;charset=UTF-8")
	public String testmng_add(HttpServletRequest req) {
		return passbean.testmng_add(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_del",produces = "text/html;charset=UTF-8")
	public String testmng_del(HttpServletRequest req,@RequestParam(value = "testids[]")  Integer[]  testids) {
		return passbean.testmng_del(req,testids);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_update",produces = "text/html;charset=UTF-8")
	public String testmng_update(HttpServletRequest req) {
		return passbean.testmng_update(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/pass_json",produces = "text/html;charset=UTF-8")
	public ModelAndView pass_json(HttpServletRequest req) {
		List list=null;
		ModelAndView mav=new ModelAndView("pass/pass_json");
		list=passbean.getJson(req);
		Map map=(Map)list.get(0);
		mav.addObject("testid", map.get("testid"));
		mav.addObject("testname", map.get("testname"));
		mav.addObject("testin", map.get("testin"));
		mav.addObject("testout", map.get("testout"));
		mav.addObject("testout_response", map.get("testout_response"));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_screen_all",produces = "text/html;charset=UTF-8")
	public String testmng_screen_all(HttpServletRequest req) throws ClassNotFoundException, TimeoutException, IOException, SQLException, DocumentException {
		return passbean.testmng_screen_all(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/testmng_screen_one",produces = "text/html;charset=UTF-8")
	public String testmng_screen_one(HttpServletRequest req) throws ClassNotFoundException, TimeoutException, IOException, SQLException, DocumentException {
		return passbean.testmng_screen_one(req);
	}
	
	@ResponseBody
	@RequestMapping("/json_duibi")
	public List json_duibi(HttpServletRequest req) throws ClassNotFoundException, TimeoutException, IOException, SQLException, DocumentException{
		return passbean.json_duibi(req);
	}
	
	//获取team下拉单
	@RequestMapping("/_select2")
	@ResponseBody
	public List _select2(HttpServletRequest req){
		List list=new ArrayList();
		
		String[] jsonversions={"1609","1712"};
		List jsonversionslist=new ArrayList();
		for(int i=0;i<jsonversions.length;i++){
			Map map= new HashMap();
			map.put("id", jsonversions[i]);
			map.put("text", jsonversions[i]);
			jsonversionslist.add(map);
		}
		
		String[][] jsontypes={{"1","Screen"},{"2","Detail"},{"3","Query"},{"4","Module"},{"5","其他"}};
		List jsontypeslist=new ArrayList();
		for(int i=0;i<jsontypes.length;i++){
			String[] jsontype=jsontypes[i];
			Map map= new HashMap();
			map.put("id", jsontype[0]);
			map.put("text", jsontype[1]);
			jsontypeslist.add(map);
		}
	
		String[][] anlitypes={{"1","自动案例"},{"2","手动案例"},{"3","说明书"},{"4","浮动窗口"},{"5","详细信息"},
				{"6","用药理由"},{"7","右键菜单"},{"8","模块菜单"},{"9","自由自定义"},{"10","中药材专论"},{"11","用药指导单"},{"999","未定位"}};
		List anlitypeslist=new ArrayList();
		for(int i=0;i<anlitypes.length;i++){
			String[] anlitype=anlitypes[i];
			Map map= new HashMap();
			map.put("id", anlitype[0]);
			map.put("text", anlitype[1]);
			anlitypeslist.add(map);
		}
		
//		String[][] daoshujus={{"100","不导"},{"112","模糊导"},{"111","全导"},{"1","自动案例"},{"2","手动案例"},{"3","说明书"},{"4","浮动窗口"},{"5","详细信息"},
//				{"6","用药理由"},{"7","右键菜单"},{"8","模块菜单"},{"10","自由自定义"}};
		String[][] daoshujus={{"100","不导"},{"111","全导"},{"1","自动案例"},{"2","手动案例"},{"3","说明书"},{"4","浮动窗口"},{"5","详细信息"},
				{"6","用药理由"},{"7","右键菜单"},{"8","模块列表"},{"9","自由自定义"},{"10","中药材专论"},{"11","用药指导单"}};
		
		List daoshujuslist=new ArrayList();
		for(int i=0;i<daoshujus.length;i++){
			String[] daoshuju=daoshujus[i];
			Map map= new HashMap();
			map.put("id", daoshuju[0]);
			map.put("text", daoshuju[1]);
			daoshujuslist.add(map);
		}
		
		String sql = "select serverid as id,servername as text from serverip order by servername asc";
		List serveriplist=jdbcTemplate.queryForList(sql);
		
		
		Map map= new HashMap();
		map.put("jsontyles", jsontypeslist);
		map.put("jsonversions", jsonversionslist);
		map.put("anlitypes", anlitypeslist);
		map.put("daoshujus", daoshujuslist);
		map.put("serverips", serveriplist);
		list.add(map);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/sqlserver_data",produces = "text/html;charset=UTF-8")
	public String sqlserver_data(HttpServletRequest req) throws DocumentException, ClassNotFoundException, IOException, SQLException {
		String requesterr="";
		requesterr=passbean.sqlserver_data(req);
		return requesterr;
	}
	
	@ResponseBody
	@RequestMapping(value="/screen_java",produces = "text/html;charset=UTF-8")
	public String screen_java(HttpServletRequest req) throws UnsupportedEncodingException, TimeoutException {
		return passbean.screen_java(req);
	}
	
	@ResponseBody
	@RequestMapping(value="/screen_win",produces = "text/html;charset=UTF-8")
	public String screen_win(HttpServletRequest req) throws UnsupportedEncodingException, TimeoutException  {
		return passbean.screen_win(req);
	}
	
	//复制team列表数据
	@RequestMapping(value="/testmng_copy", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmng_copy(HttpServletRequest req){
		return passbean.testmng_copy(req);
	}
}
