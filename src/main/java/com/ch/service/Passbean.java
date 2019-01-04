package com.ch.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Executable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ch.dao.DataBaseType;
import com.ch.dao.SpringJdbc_sqlserver_his;
import com.ch.dao.SpringJdbc_sqlserver_pass;
import com.ch.passjson.Detailresule;
import com.ch.passjson.Moduleresule;
import com.ch.passjson.Passs_json;
import com.ch.passjson.Queryresule;
import com.ch.passjson.Screenresule_1609;
import com.ch.passjson.Screenresule_1712;
import com.ch.passjson.Xmltojson;
import com.ch.sys.JsontoModules;
import com.ch.sys.Passservice;
import com.ch.sys.Springbatch;
import com.ch.sys.Updatejson;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.User;
import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
public class Passbean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	Passs_json passs_json;
	@Autowired
	Updatejson updatejson;
	@Autowired
	Springbatch springbatch;	
	@Autowired
	JsontoModules jsontoModules;
	@Autowired
	Passservice passservice;
	@Autowired
	User user;
	JdbcTemplate jdbcTemplate_database=null;
	@Autowired
	DataBaseType dataBaseType;
	
	//Team查询
	public DataGrid team_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int limit=0;
		//当前页编号,已乘以当前页总数
		int offset=0; 
		String search="";
		
		String sql=null;
		String sql1=null;
		String teamname="";
		if(StringUtil.isNotBlank(req.getParameter("limit"))){
			limit=Integer.parseInt(req.getParameter("limit"));
		} 
		if(StringUtil.isNotBlank(req.getParameter("offset"))){
			offset=Integer.parseInt(req.getParameter("offset"));
		} 
		if(StringUtil.isNotBlank(req.getParameter("search"))){
			search=req.getParameter("search");
		} 
		if(StringUtil.isNotBlank(req.getParameter("teamname"))){
			teamname=req.getParameter("teamname");
		} 
		
		sql="select * from pass_team  where 1=1 ";
		sql1="select count(*) from pass_team  where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(StringUtils.isNotBlank(search)){
			sql=sql+" and teamname like ? ";
			sql1=sql1+" and teamname like ? ";
			wherelist.add(teamname);
		}
		
		//拼接order
		
		//拼接limit
		if(limit>0){
			sql=sql+" limit "+offset+","+limit;
		}
		
//		sql="select * from pass_team where teamname like ? order by teamname limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+teamname+"%"});
//		
//		sql="select count(*) from pass_team where teamname like ? ";
//		int count =jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+teamname+"%"});
			
		lstRes=jdbcTemplate.queryForList(sql, wherelist.toArray());
		int count =jdbcTemplate.queryForObject(sql1,int.class,wherelist.toArray());
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public String team_add(HttpServletRequest req){
		String teamname="";
		String remark="";
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("teamname"))){
			return "团队名称不能为空";
		}else{
			teamname=req.getParameter("teamname");
		}
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		} 
		
		int rsl=0;
		sql="select count(1) from pass_team where teamname=? ";
		rsl=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname});
		if(rsl>0){
			return "团队名称不能重复";
		}
		
		sql="insert into pass_team(teamname,remark) values(?,?)";
		jdbcTemplate.update(sql,new Object[]{teamname,remark});
		return "ok";
	}
	
	public String team_del(HttpServletRequest req,Integer[]  teamids){
		int teamid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(teamids);
		
//		HttpSession session=req.getSession();
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "非管理员权限";
//		}
		
		if(list.size()==0){
			return "teamid不能为空";
		}
		
		for(int i=0;i<list.size();i++){
			teamid=list.get(i);
			
			sql="delete a from pass_files a "
					+ "inner join pass_script b on a.linkid=b.scriptid "  
					+ "inner join pass_testmng c on b.testid=c.testid "
					+ "inner join pass_project d on c.projectid=d.projectid "
					+ "inner join pass_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? and a.linktype=2 ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete b from pass_script b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid "
					+ "inner join pass_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete c from pass_testmng c "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "inner join pass_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete b from pass_anli_err b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid "
					+ "inner join pass_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete from pass_project where teamid=?";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete from pass_team where teamid=?";
			jdbcTemplate.update(sql,new Object[]{teamid});
		}
		
		return "ok";
	}
	
	public String team_update(HttpServletRequest req){
		String teamname="";
		String remark="";
		int teamid=0;
		String sql="";
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			return "teamid不能为空";
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		if(StringUtils.isBlank(req.getParameter("teamname"))){
			return "teamname不能为空";
		}else{
			teamname=req.getParameter("teamname");
		}
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		} 
		
		sql="select count(*) from pass_team where teamname=? and teamid<>?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname,teamid});
		if(sum>0){
			return "团队名称不能重复";
		}
		
		sql="update pass_team set teamname=?,remark=? where teamid=?";
		jdbcTemplate.update(sql,new Object[]{teamname,remark,teamid});
		return "ok";
	}
	
	//
	//project查询
	public DataGrid project_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int limit=0;
		//当前页编号,已乘以当前页总数
		int offset=0; 
		String search="";
		
		String sql=null;
		String sql1=null;
		String projectname="";
		int teamid=0;
		
		if(StringUtils.isNotBlank(req.getParameter("limit"))){
			limit=Integer.parseInt(req.getParameter("limit"));
		} 
		if(StringUtils.isNotBlank(req.getParameter("offset"))){
			offset=Integer.parseInt(req.getParameter("offset"));
		} 
		if(StringUtils.isNotBlank(req.getParameter("search"))){
			search=req.getParameter("search");
		} 
		if(StringUtils.isNotBlank(req.getParameter("projectname"))){
			projectname=req.getParameter("projectname");
		} 
		if(StringUtils.isNotBlank(req.getParameter("teamid"))){
			teamid=Integer.parseInt(req.getParameter("teamid"));
		} 
		
		sql="select a.*,b.teamname,b.teamid from pass_project a inner join pass_team b on a.teamid=b.teamid where 1=1 " ;
		sql1="select count(1) from pass_project a inner join pass_team b on a.teamid=b.teamid where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(teamid>0){
			sql=sql+" and a.projectname like ? and a.teamid=? ";
			sql1=sql1+" and a.projectname like ? and a.teamid=? ";
			wherelist.add(projectname);
			wherelist.add(teamid);
		}
		//拼接order
		sql=sql+" order by b.teamname,a.projectname asc ";
		
		//拼接limit
		sql=sql+" limit "+offset+","+limit;
		
//		if(teamid>0){
//			sql="select a.*,b.teamname,b.teamid from pass_project a inner join pass_team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? order by b.teamname,a.projectname asc limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%",teamid});
//			
//			sql="select count(1) from pass_project a inner join pass_team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? ";
//			int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%",teamid});
//			
//			dataGrid.setTotal(count+0L);
//			dataGrid.setRows(lstRes);
//			
//			return dataGrid;
//		}
//		
//		sql="select a.*,b.teamname,b.teamid from pass_project a inner join pass_team b on a.teamid=b.teamid where a.projectname like ? order by b.teamname,a.projectname asc limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%"});
//		
//		sql="select count(1) from pass_project a inner join pass_team b on a.teamid=b.teamid where a.projectname like ? ";
//		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%"});
		
		lstRes=jdbcTemplate.queryForList(sql, wherelist.toArray());
		int count=jdbcTemplate.queryForObject(sql1, Integer.class,wherelist.toArray());
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
//	public List teamgroup(HttpServletRequest req){
//		String sql=null;
//		sql="select teamid as id,teamname as text from pass_team";
//		List list=jdbcTemplate.queryForList(sql);
//
//		return list;
//	}
	
	public String project_add(HttpServletRequest req){
		int teamid=0;
		String projectname="";
		String remark="";
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			return "teamid不能为空";
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		if(StringUtils.isBlank(req.getParameter("projectname"))){
			return "项目名称不能为空";
		}else{
			projectname=req.getParameter("projectname").trim();
		}
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		} 
		
		sql="select count(*) from pass_project where projectname=? and teamid=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid});
		if(sum>0){
			return "项目名称重复";
		}
		
		sql="insert into pass_project(teamid,projectname,remark) values(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{teamid,projectname,remark});
		return "ok";
	}
	
	public String project_del(HttpServletRequest req,Integer[]  projectids){
		int projectid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(projectids);
		
//			HttpSession session=req.getSession();
//			if(!"admin".equals(session.getAttribute("loginname"))){
//				return "非管理员权限";
//			}
		
		if(list.size()==0){
			return "teamid不能为空";
		}
		
		for(int i=0;i<list.size();i++){
			projectid=list.get(i);
			
			sql="delete from pass_project where projectid=?";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete a from pass_files a "
					+ "inner join pass_script b on a.linkid=b.scriptid "  
					+ "inner join pass_testmng c on b.testid=c.testid inner join project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and a.linktype=2 ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete b from pass_script b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete b from pass_anli_err b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete from pass_testmng where projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
		}
		
		return "ok";
	}
	
	public String project_update(HttpServletRequest req){
		int teamid=0;
		String projectname="";
		String remark="";
		int projectid=0;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "未收到项目ID";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			return "未收到团队ID";
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		if(StringUtils.isBlank(req.getParameter("projectname"))){
			return "项目名称不能为空";
		}else{
			projectname=req.getParameter("projectname");
		}
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		}
		
		sql="select count(*) from pass_project where projectname=? and teamid=? and projectid<>? ";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid,projectid});
		if(sum>0){
			return "项目名称不能重复";
		}
		
		sql="update pass_project set teamid=?,remark=?,projectname=? where projectid=?";
		jdbcTemplate.update(sql,new Object[]{teamid,remark,projectname,projectid});
		return "ok";
	}
	
	//
	//testmng查询
	public DataGrid testmng_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int limit=0;
		//当前页编号,已乘以当前页总数
		int offset=0; 
		String sort="";
		String order="";
		
		String testname="";
		String testno="";
		int projectid=0;
		int anlitype=0;
		String moduleid=null;
		String sql1=null;
		String sql2=null;
		
		if(StringUtils.isNotBlank(req.getParameter("limit"))){
			limit=Integer.parseInt(req.getParameter("limit"));
		}
		if(StringUtils.isNotBlank(req.getParameter("offset"))){
			offset=Integer.parseInt(req.getParameter("offset"));
		}
		if(StringUtils.isNotBlank(req.getParameter("sort"))){
			sort=req.getParameter("sort");
		}
		if(StringUtils.isNotBlank(req.getParameter("order"))){
			order=req.getParameter("order");
		}
		if(StringUtils.isNotBlank(req.getParameter("anlitype"))){
			anlitype=Integer.parseInt(req.getParameter("anlitype"));
		}
		if(StringUtils.isNotBlank(req.getParameter("searchdata"))){
			testname=req.getParameter("searchdata");
			testno=req.getParameter("searchdata");
		}
		if(StringUtils.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(StringUtils.isNotBlank(req.getParameter("moduleid"))){
			moduleid=req.getParameter("moduleid");
		}
		
		sql1="select a.*,b.projectname,c.username,d.teamid,d.teamname from pass_testmng a inner join pass_project b on a.projectid=b.projectid "
				+ "left join sys_users c on a.userid=c.userid "
				+ "left join pass_team d on b.teamid=d.teamid "
				+ "where 1=1 ";
		sql2="select count(*) from pass_testmng a inner join pass_project b on a.projectid=b.projectid where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(projectid>0){
			sql1=sql1+" and (a.testname like ? or a.testno like ?) and a.projectid=? ";
			sql2=sql2+" and (a.testname like ? or a.testno like ?) and a.projectid=? ";
			wherelist.add("%"+testname+"%");
			wherelist.add("%"+testno+"%");
			wherelist.add(projectid);
		}else{
			sql1=sql1+" and (a.testname like ? or a.testno like ?) ";
			sql2=sql2+" and (a.testname like ? or a.testno like ?) ";
			wherelist.add("%"+testname+"%");
			wherelist.add("%"+testno+"%");
		}
		if(anlitype>0){
			sql1=sql1+" and a.anlitype=? ";
			sql2=sql2+" and a.anlitype=? ";
			wherelist.add(anlitype);
		}
		if(StringUtils.isNotBlank(moduleid)){
			sql1=sql1+" and a.moduleid = ? ";
			sql2=sql2+" and a.moduleid = ? ";
			wherelist.add(moduleid);
		}
		//拼接order
		if(StringUtils.isBlank(sort)){
//			sql1=sql1+ "order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED), CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc ";
			sql1=sql1+" order by b.projectid , a.testno , a.orderbyno asc ";
		}else{
			sql1=sql1+ "order by b.projectid , a.moduleid , "+sort+" "+order;
		}
		
		//拼接limit
		if(limit>0){
			sql1=sql1+ " limit "+offset+","+limit;
		}
		
//		if(projectid>0){
//			sql1=sql1+" and (a.testname like ? or a.testno like ?) and a.projectid=? "
//					+ "order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED),a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED),"+orderby+" limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql1, new Object[]{"%"+testname+"%","%"+testno+"%", projectid});
//			
//			sql2=sql2+" and (a.testname like ? or a.testno like ?) and a.projectid=? ";
//			int count=jdbcTemplate.queryForObject(sql2, Integer.class, new Object[]{"%"+testname+"%","%"+testno+"%",projectid});
//			
//			dataGrid.setTotal(count+0L);
//			dataGrid.setRows(lstRes);
//			return dataGrid;
//		}
//		
//		sql1=sql1+" and a.testname like ? or a.testno like ? "
//				+ "order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED),a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED), "+orderby+" limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql1, new Object[]{"%"+testname+"%","%"+testno+"%"});
//		
//		sql2=sql2+" and a.testname like ? or a.testno like ? ";
//		int count=jdbcTemplate.queryForObject(sql2, Integer.class, new Object[]{"%"+testname+"%","%"+testno+"%"});
		
		lstRes=jdbcTemplate.queryForList(sql1, wherelist.toArray());
		int count=jdbcTemplate.queryForObject(sql2, Integer.class,wherelist.toArray());
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
//	public List projectgroup(HttpServletRequest req){
//		String sql=null;
//		int teamid=0;
//		
//		if(StringUtils.isNotBlank(req.getParameter("teamid"))){
//			teamid=Integer.parseInt(req.getParameter("teamid"));
//		}
//		
//		List list=null;
//		if(teamid>0){
//			sql="select a.projectid as id , a.projectname as text from pass_project a, pass_team b where "
//					+ " a.teamid=b.teamid and b.teamid=? order by a.projectname asc";
//			list=jdbcTemplate.queryForList(sql,new Object[]{teamid});
//		}else{
//			sql="select projectid as id ,projectname as text from pass_project order by projectname asc";
//			list=jdbcTemplate.queryForList(sql);
//		}
//		
////		Map map=new HashMap();
////		map.put("projectid", 0);
////		map.put("projectname","全选");
////		list.add(map);
////		//按字段重新排序
////		Collections.sort(list, new Comparator<Map<String,Object>>() {
////			//@Override
////			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
////				//进行判断
////				return String.valueOf(o1.get("projectid").toString()).compareTo(String.valueOf(o2.get("projectid").toString()));
////			}
////		});
//
//		return list;
//	}
	
	public String testmng_add(HttpServletRequest req){
		int projectid=0;
		String testname="";
		String testno="";
		int orderbyno=0;
		String testtext="";
		String testin="";
		String testout="";
		String remark="";
		String sql=null;
		int status=1;
		int moduleid=0;
		String modulename="";
		int anlitype=0;
		
		
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "projectid不能为空";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(StringUtils.isBlank(req.getParameter("testname"))){
			return "案例名称不能为空";
		}else{
			testname=req.getParameter("testname").trim();
		}
		if(StringUtils.isBlank(req.getParameter("testno")) || 
				StringUtils.isBlank(req.getParameter("orderbyno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno").trim();
		}
		if(StringUtils.isNotBlank(req.getParameter("orderbyno"))){
			orderbyno=Integer.parseInt(req.getParameter("orderbyno"));
		}
		if(StringUtils.isNotBlank(req.getParameter("testtext"))){
			testtext=req.getParameter("testtext");
		} 
		if(StringUtils.isNotBlank(req.getParameter("testin"))){
			testin=req.getParameter("testin");
		} 
		if(StringUtils.isNotBlank(req.getParameter("testout"))){
			testout=req.getParameter("testout");
		} 
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		} 
		if(StringUtils.isNotBlank(req.getParameter("status"))){
			status=Integer.parseInt(req.getParameter("status"));
		} 
		if(StringUtils.isNotBlank(req.getParameter("moduleid")) && !"null".equals(req.getParameter("moduleid"))){
			moduleid=Integer.parseInt(req.getParameter("moduleid"));
			modulename=req.getParameter("modulename");
		} 
		if(StringUtils.isNotBlank(req.getParameter("anlitype")) && !"null".equals(req.getParameter("anlitype"))){
			anlitype=Integer.parseInt(req.getParameter("anlitype"));
		} 
//		sql="select count(1) from pass_testmng where projectid=? and testno=?";
//		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno});
//		if(sum>0){
//			return "案例编号重复";
//		}
		
		//号码重复往后+1
		sql="select count(1) from pass_testmng where projectid=? and anlitype=? and moduleid=? and testno=? and orderbyno=? ";
		int countnum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{projectid,anlitype,moduleid,testno,orderbyno});
		if(countnum>0){
			sql="update pass_testmng set orderbyno=orderbyno+1 where projectid=? and anlitype=? and moduleid=? and testno=? and orderbyno>=?";
			jdbcTemplate.update(sql,new Object[]{projectid,anlitype,moduleid,testno,orderbyno});
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		sql="insert into pass_testmng(projectid,testname,testno,testtext,testin,testout,remark,inserttime,userid,status,moduleid,modulename,anlitype,orderbyno) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
				user.getUserid(),status,moduleid,modulename,anlitype,orderbyno});
		
		
		
//		//广播通知
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		
		
		return "ok";
	}
	
	public String testmng_del(HttpServletRequest req,Integer[]  testids){
		int testid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(testids);
		
		HttpSession session=req.getSession();
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "非管理员权限";
//		}
		if(list.size()==0){
			return "后台未收到数据ID";
		}
		
		for(int i=0;i<list.size();i++){
			testid=list.get(i);
			
			sql="delete from pass_testmng where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete a from pass_files a , pass_script b where a.linkid=b.scriptid and a.linktype=2 and b.testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete from pass_script where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete from pass_anli_err where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
		}
		
		return "ok";
	}
	
	public String testmng_update(HttpServletRequest req){
		int anlitype=0;
		int projectid=0;
		String testname="";
		String testno="";
		int orderbyno=0;
		String testtext="";
		String testin="";
		String testout="";
		String remark="";
		String sql=null;
		int testid=0;
		String moduleid=null;
		String modulename="";
		 
		int status=1;
		
		if(StringUtils.isBlank(req.getParameter("testid"))){
			return "未收到案例ID";
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "未收到项目ID";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(StringUtils.isBlank(req.getParameter("testname"))){
			return "案例名称不能为空";
		}else{
			testname=req.getParameter("testname");
		}
		if(StringUtils.isBlank(req.getParameter("testno")) || 
				StringUtils.isBlank(req.getParameter("orderbyno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno");
		}
		if(StringUtils.isNotBlank(req.getParameter("orderbyno"))){
			orderbyno=Integer.parseInt(req.getParameter("orderbyno"));
		}
		if(StringUtils.isNotBlank(req.getParameter("testtext"))){
			testtext=req.getParameter("testtext");
		} 
		if(StringUtils.isNotBlank(req.getParameter("testin"))){
			testin=req.getParameter("testin");
		} 
		if(StringUtils.isNotBlank(req.getParameter("testout"))){
			testout=req.getParameter("testout");
		} 
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		} 
		if(StringUtils.isNotBlank(req.getParameter("anlitype")) && !"null".equals(req.getParameter("anlitype"))){
			anlitype=Integer.parseInt(req.getParameter("anlitype"));
		} 
		if(StringUtils.isNotBlank(req.getParameter("moduleid"))){
			moduleid=req.getParameter("moduleid");
		} 
		
		if(StringUtils.isNotBlank(req.getParameter("status")) && !"null".equals(req.getParameter("status"))){
			status=Integer.parseInt(req.getParameter("status"));
		} 
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		sql="select count(*) from pass_testmng where projectid=? and testno=? and testid<>?";
//		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno,testid});
//		if(sum>0){
//			return "案例编号不能重复";
//		}
		
		//号码重复往后+1
		sql="select count(1) from pass_testmng where projectid=? and anlitype=? and moduleid=? and testno=? and orderbyno=? ";
		int countnum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{projectid,anlitype,moduleid,testno,orderbyno});
		if(countnum>0){
			sql="update pass_testmng set orderbyno=orderbyno+1 where projectid=? and anlitype=? and moduleid=? and testno=? and orderbyno>=?";
			jdbcTemplate.update(sql,new Object[]{projectid,anlitype,moduleid,testno,orderbyno});
		}
		
		sql="select text from (select CONCAT(moduletype,'-',moduleid) as id ,CONCAT(CASE  moduletype when 1 then '审查_' when 2 then '查询_' else '其他_' end, modulename ) as text from mc_modulename order by moduleid asc " + 
				") x where x.id=?";
		modulename=jdbcTemplate.queryForObject(sql, String.class,new Object[]{moduleid});
				
//		sql="select modulename from mc_modulename where moduleid=? ";
//		modulename =jdbcTemplate.queryForObject(sql, String.class,new Object[]{moduleid});
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		sql="select testresult from pass_testmng where testid=? ";
		String testresult =jdbcTemplate.queryForObject(sql, String.class,new Object[]{testid});
		if("新".equals(testresult)){
			sql="update pass_testmng set anlitype=?,projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,moduleid=?,modulename=?,testresult='',orderbyno=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{anlitype,projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					user.getUserid(),status,
					moduleid,modulename,orderbyno,testid});
		}else{
			sql="update pass_testmng set anlitype=?,projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,moduleid=?,modulename=?,orderbyno=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{anlitype,projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					user.getUserid(),status,
					moduleid,modulename,orderbyno,testid});
		}
		
		//广播通知
//		System.out.println(req.getSession().getAttribute("userid"));
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		return "ok";
	}
	
	public List getJson(HttpServletRequest req){
		List list=null;
		
		String sql=null;
		int testid=0;
		
		if(StringUtils.isNotBlank(req.getParameter("testid"))){
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
		sql="select * from pass_testmng where testid=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{testid});
		
		return list;
	}
	
	public String testmng_screen_all(HttpServletRequest req) throws ClassNotFoundException, TimeoutException, IOException, SQLException, DocumentException{
		List lstRes = new ArrayList();
		String sql=null;
		String testname="";
		String testno="";
		int projectid=0;
		String jsonversion=null;//区分1609或者1712版json
		int jsontype=0;//区分审查或者查询json
		String serveraddress=null;
		int anlitype=0;
		
		if(StringUtils.isBlank(req.getParameter("anlitype"))){
			return "请搜索案例类型再审查";
		}else{
			anlitype=Integer.parseInt(req.getParameter("anlitype").toString());
		}
		
		if(StringUtils.isNotBlank(req.getParameter("searchdata"))){
			testname=req.getParameter("searchdata");
			testno=req.getParameter("searchdata");
		}
		if(StringUtils.isNotBlank(req.getParameter("jsonversion"))){
			jsonversion=req.getParameter("jsonversion");
		}
		if(StringUtils.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(StringUtils.isNotBlank(req.getParameter("jsontype"))){
			jsontype=Integer.parseInt(req.getParameter("jsontype"));
		}
		if(StringUtils.isNotBlank(req.getParameter("serveraddress"))){
			serveraddress=req.getParameter("serveraddress");
		}
		
		sql="update pass_testmng set testresult='' where projectid=? ";
		jdbcTemplate.update(sql,new Object[]{projectid});
		
		if(projectid>0){
			sql="select a.* from pass_testmng a "
					+ " where (a.testname like ? or a.testno like ?) and a.projectid=? and anlitype=? and status=1";
			lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+testname+"%","%"+testno+"%", projectid,anlitype});

		}else{
			sql="select a.* from pass_testmng a "
					+ " where (a.testname like ? or a.testno like ?) and anlitype=?  and status=1";
			lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+testname+"%","%"+testno+"%",anlitype});
		}
		
		String gatherbaseinfo = null;
		String gatherresult=null;
		String testname1="";
		int testid=0;
		
		for(int i=0;i<lstRes.size();i++){
			Map map=(Map)lstRes.get(i);
			if(map.get("status")!=null){
				if(Integer.parseInt(map.get("status").toString())==0){
					sql="delete from pass_anli_err where testid=?";
					jdbcTemplate.update(sql, new Object[]{map.get("testid")});
					continue;
				}
			}
			
			gatherbaseinfo=map.get("testin").toString();
			gatherresult=map.get("testout").toString();
			testid=Integer.parseInt(map.get("testid").toString());//sa_gather_log
			testname1=map.get("testname").toString();//pass_anli_err
			System.out.println("自动审查案例数："+(i+1));
			
			//json
			List json_err = new ArrayList();
			if(StringUtils.isBlank(serveraddress)){
				json_err.add("url is null");
			}else{
				String gatherresult_java=passservice.getPassResult(gatherbaseinfo, serveraddress);
				if(StringUtils.isBlank(gatherresult_java)){
					json_err.add("pass-java is null");
				}else{
					json_err = passs_json.getResult( gatherresult,gatherresult_java,jsonversion,jsontype,testid);
				}
			}
			
			if(json_err.size()>0){
				//ListJSON
				JSONObject obj=new JSONObject();
				obj.element("tojson", json_err);
				sql="select count(*) from pass_anli_err where testid=?";
				int sum=jdbcTemplate.queryForObject(sql, int.class, new Object[]{testid});
				if(sum>0){
					sql="update pass_anli_err set json_err=?, testname=? where testid=?";
					jdbcTemplate.update(sql,new Object[]{obj.toString(),testname1,testid});
				}else{
					sql="insert into pass_anli_err(testid,json_err,testname) values(?,?,?)";
					jdbcTemplate.update(sql,new Object[]{testid,obj.toString(),testname1});
				}
				
				sql="update pass_testmng set testresult='不通过' where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
			}else{
				sql="delete from pass_anli_err where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
				
				sql="update pass_testmng set testresult='通过' where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
			}
		}
		
		//
		sql="delete from pass_anli_err where testid not in(select testid from pass_testmng)";
		jdbcTemplate.update(sql);
		
		//
//		int errcount=0;
//		if(projectid==0){
//			sql="select count(1) from pass_testmng a , pass_anli_err b where a.testid=b.testid and  "
//					+ " (a.testno like ? or a.testname like ?) ";
//			errcount=jdbcTemplate.queryForObject(sql, int.class, new Object[]{"%"+testno+"%",
//					"%"+testname+"%"});
//		}else{
//			sql="select count(1) from pass_testmng a , pass_anli_err b where a.testid=b.testid and a.projectid=? and "
//					+ " (a.testno like ? or a.testname like ?) ";
//			errcount=jdbcTemplate.queryForObject(sql, int.class, new Object[]{projectid,"%"+testno+"%",
//					"%"+testname+"%"});
//		}
		
		return "ok";
	}
	
	public String testmng_screen_one(HttpServletRequest req) throws ClassNotFoundException, TimeoutException, IOException, SQLException, DocumentException{
		List lstRes = new ArrayList();
		String sql=null;
		String jsonversion=null;//区分1609或者1712版json
		int jsontype=0;//区分审查或者查询json
		int testid=0;
		String serveraddress=null;
		
		if(StringUtils.isNotBlank(req.getParameter("testid"))){
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		if(StringUtils.isNotBlank(req.getParameter("jsonversion"))){
			jsonversion=req.getParameter("jsonversion");
		}
		if(StringUtils.isNotBlank(req.getParameter("jsontype"))){
			jsontype=Integer.parseInt(req.getParameter("jsontype"));
		}
		if(StringUtils.isNotBlank(req.getParameter("serveraddress"))){
			serveraddress=req.getParameter("serveraddress");
		}
		
		sql="select a.* from pass_testmng a where testid=?";
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{testid});
		
		String gatherbaseinfo = null;
		String gatherresult=null;
		String testname1="";
		
		for(int i=0;i<lstRes.size();i++){
			Map map=(Map)lstRes.get(i);
//			if(map.get("status")!=null){
//				if(Integer.parseInt(map.get("status").toString())==0){
//					sql="delete from pass_anli_err where testid=?";
//					jdbcTemplate.update(sql, new Object[]{map.get("testid")});
//					continue;
//				}
//			}
			
			sql="delete from pass_anli_err where testid=?";
			jdbcTemplate.update(sql, new Object[]{map.get("testid")});
			
			gatherbaseinfo=map.get("testin").toString();
			gatherresult=map.get("testout").toString();
			testid=Integer.parseInt(map.get("testid").toString());//sa_gather_log
			testname1=map.get("testname").toString();//pass_anli_err
			
			List json_err = new ArrayList();
			if(StringUtils.isBlank(serveraddress)){
				json_err.add("url is null");
			}else{
				String gatherresult_java=passservice.getPassResult(gatherbaseinfo, serveraddress);
				if(StringUtils.isBlank(gatherresult_java)){
					json_err.add("pass-java is null");
				}else{
					json_err = passs_json.getResult( gatherresult,gatherresult_java,jsonversion,jsontype,testid);
				}
			}
			
			if(json_err.size()>0){
				//ListJSON
				JSONObject obj=new JSONObject();
				obj.element("tojson", json_err);
				sql="select count(*) from pass_anli_err where testid=?";
				int sum=jdbcTemplate.queryForObject(sql, int.class, new Object[]{testid});
				if(sum>0){
					sql="update pass_anli_err set json_err=?, testname=? where testid=?";
					jdbcTemplate.update(sql,new Object[]{obj.toString(),testname1,testid});
				}else{
					sql="insert into pass_anli_err(testid,json_err,testname) values(?,?,?)";
					jdbcTemplate.update(sql,new Object[]{testid,obj.toString(),testname1});
				}
				
				sql="update pass_testmng set testresult='不通过' where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
			}else{
				sql="delete from pass_anli_err where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
				
				sql="update pass_testmng set testresult='通过' where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
			}
		}
		
		//清空冗余数据
		sql="delete from pass_anli_err where testid not in(select testid from pass_testmng)";
		jdbcTemplate.update(sql);
		
		return "ok";
	}
	
	public List json_duibi(HttpServletRequest req) throws ClassNotFoundException, TimeoutException, IOException, SQLException, DocumentException {
		String gatherresult="";
		String gatherresult_java="";
		int testid=0;
		String testname="";
		int jsontype=0;
		String jsonversion="1609";
		List json_err=new ArrayList();
		
		if(StringUtils.isNotBlank(req.getParameter("testout"))){
			gatherresult=req.getParameter("testout");
		}
		if(StringUtils.isNotBlank(req.getParameter("testout_response"))){
			gatherresult_java=req.getParameter("testout_response");
		}
		if(StringUtils.isNotBlank(req.getParameter("jsontype"))){
			jsontype=Integer.parseInt(req.getParameter("jsontype"));
		}
		if(StringUtils.isNotBlank(req.getParameter("jsonversion"))){
			jsonversion=req.getParameter("jsonversion");
		}
		if(StringUtils.isNotBlank(req.getParameter("testid"))){
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		if(StringUtils.isNotBlank(req.getParameter("testname"))){
			testname=req.getParameter("testname");
		}
		
		if("".equals(gatherresult)){
			json_err.add("pass-win not null");
			return json_err;
		}
		if("".equals(gatherresult_java)){
			json_err.add("pass-java not null");
			return json_err;
		}
		
		if(gatherresult_java.equals(gatherresult)){
			String sql="delete from pass_anli_err where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			json_err.add("ok");
			return json_err;
		}
		
		json_err = passs_json.getResult( gatherresult,gatherresult_java,jsonversion,jsontype,testid);
		
		String sql="";
		if(json_err.size()>0){
			//ListJSON
			JSONObject obj=new JSONObject();
			obj.element("tojson", json_err);
			sql="select count(*) from pass_anli_err where testid=?";
			int sum=jdbcTemplate.queryForObject(sql, int.class, new Object[]{testid});
			if(sum>0){
				sql="update pass_anli_err set json_err=? where testid=?";
				jdbcTemplate.update(sql,new Object[]{obj.toString(),testid});
			}else{
				sql="insert into pass_anli_err(testid,json_err,testname) values(?,?,?)";
				jdbcTemplate.update(sql,new Object[]{testid,obj.toString(),testname});
			}
			
			return json_err;
		}else{
			sql="delete from pass_anli_err where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			json_err.add("ok");
			return json_err;
		}
		
	}
	
	public String sqlserver_data(HttpServletRequest req) throws IOException, ClassNotFoundException, SQLException{
//		JdbcTemplate_sqlserver_passanli=springJdbc_sqlserver_passanli.getJdbcTemplate(Integer.parseInt(req.getParameter("databaseid")));
		jdbcTemplate_database=dataBaseType.getJdbcTemplate(Integer.parseInt(req.getParameter("databaseid")));
		if(jdbcTemplate_database==null){
			System.out.println("数据库连接失败");
			return "数据库连接失败" ;
		}
		String dao="100";
		int projectid=0;
		
		if(StringUtils.isNotBlank(req.getParameter("dao"))){
			dao=req.getParameter("dao");
		}
		if(StringUtils.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(dao.equals("100")){
			return "ok";
		}
		
		String sql;
		String mhiscode_user=req.getParameter("mhiscode_user");
		//获取sqlserver数据
		sql="select json as gatherbaseinfo, warning as gatherresult from clinic_passresult_copy "
				+ "UNION ALL "
				+ "select json as gatherbaseinfo, warning as gatherresult from inhosp_passresult_copy ";
//		sql="select gatherbaseinfo,gatherresult from sa_gather_info";
		List sqlserverlist=jdbcTemplate_database.queryForList(sql);
		System.out.println("获取SQLSERVER数据成功");
		if(sqlserverlist.size()==0){
			return "ok";
		}
		
		//导入相同数据前，先清空表里面的数据
		if(dao.equals("111")){
			sql="delete a from pass_files a "
					+ "inner join pass_script b on a.linkid=b.scriptid "  
					+ "inner join pass_testmng c on b.testid=c.testid inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and a.linktype=2 ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete b from pass_script b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete b from pass_anli_err b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete from pass_testmng where projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
		}else{
			sql="delete a from pass_files a "
					+ "inner join pass_script b on a.linkid=b.scriptid "  
					+ "inner join pass_testmng c on b.testid=c.testid inner join project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and a.linktype=2 and c.moduleid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,dao});
			
			sql="delete b from pass_script b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and c.moduleid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,dao});
			
			sql="delete b from pass_anli_err b " 
					+ "inner join pass_testmng c on b.testid=c.testid "  
					+ "inner join pass_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and c.moduleid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,dao});
			
			sql="delete from pass_testmng where projectid=? and moduleid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid,dao});
		}
		System.out.println("目标数据库清理结束");
		//开始导入新的数据
		List listchangejson = new ArrayList();
		int a=0;
		int b=0;
		int c=0;
		int d=0;
		int e=0;
		int f=0;
		int g=0;
		
		for(int i=0;i<sqlserverlist.size();i++){
			Map map = (Map)sqlserverlist.get(i);
			JSONObject json =  JSONObject.fromObject(map.get("gatherbaseinfo").toString());
			JSONObject Patient = json.getJSONObject("Patient");
			JSONObject ReferenceParam = json.getJSONObject("ReferenceParam");
			JSONObject DetailParams = json.getJSONObject("DetailParams");
			JSONObject ExecuteDrugUseReason = json.getJSONObject("ExecuteDrugUseReason");
			JSONObject ModuleParam = json.getJSONObject("ModuleParam");
			
//			System.out.println("aaaaa"+map);
			//判断JSON串是否为screen、query等等
			if(!"null".equals(Patient.toString())){
				Map modulemap=jsontoModules.setPassAnli(Patient.getString("Name"));
				map.put("testname", Patient.getString("Name"));
				map.put("testno", modulemap.get("modulename"));
				map.put("anlitype", modulemap.get("anlitype"));
				map.put("moduleid", modulemap.get("moduleid"));
				map.put("modulename",modulemap.get("modulename"));
			}else if(!"null".equals(ReferenceParam.toString()) ){
				if(Integer.parseInt(ReferenceParam.getString("ReferenceType"))==0){
					a=a+1;
					Map modulemap=jsontoModules.setPassAnli("模块列表");
					map.put("testname", "模块列表"+a);
					map.put("testno", "模块列表");
					map.put("anlitype", 12);
					map.put("moduleid", modulemap.get("moduleid"));
					map.put("modulename",modulemap.get("modulename"));
				}
				if(Integer.parseInt(ReferenceParam.getString("ReferenceType"))==51){
					b=b+1;
					Map modulemap=jsontoModules.setPassAnli("浮动窗口");
					map.put("testname", "浮动窗口"+b);
					map.put("testno", "浮动窗口");
					map.put("anlitype", 12);
					map.put("moduleid", modulemap.get("moduleid"));
					map.put("modulename",modulemap.get("modulename"));
				}
				if(Integer.parseInt(ReferenceParam.getString("ReferenceType"))==11){
					c=c+1;
					Map modulemap=jsontoModules.setPassAnli("说明书");
					map.put("testname", "说明书"+c);
					map.put("testno", "说明书");
					map.put("anlitype", 12);
					map.put("moduleid", modulemap.get("moduleid"));
					map.put("modulename",modulemap.get("modulename"));
				}
			}else if(!"null".equals(DetailParams.toString())){
				d=d+1;
				Map modulemap=jsontoModules.setPassAnli("详细信息");
				map.put("testname", "详细信息"+d);
				map.put("testno", "详细信息");
				map.put("anlitype", 5);
				map.put("moduleid", modulemap.get("moduleid"));
				map.put("modulename",modulemap.get("modulename"));
			}else if(!"null".equals(ExecuteDrugUseReason.toString())){
				e=e+1;
				Map modulemap=jsontoModules.setPassAnli("用药理由");
				map.put("testname", "用药理由"+e);
				map.put("testno", "用药理由");
				map.put("anlitype", 6);
				map.put("moduleid", modulemap.get("moduleid"));
				map.put("modulename",modulemap.get("modulename"));
			}else if(!"".equals(ModuleParam.toString())){
				f=f+1;
				Map modulemap=jsontoModules.setPassAnli("右键菜单");
				map.put("testname", "右键菜单"+f);
				map.put("testno", "右键菜单");
				map.put("anlitype", 7);
				map.put("moduleid", modulemap.get("moduleid"));
				map.put("modulename",modulemap.get("modulename"));
			}else{
				g=g+1;
				map.put("testname", "未知案例"+g);
				map.put("testno", "未知案例");
				map.put("anlitype", 999);
				map.put("moduleid", 0);
				map.put("modulename", "未定位");
			}
			
			//替换JSON中的HISCODE编号
			if(!"null".equals(ExecuteDrugUseReason.toString())){
//				String hiscode=prop.getProperty("hiscode");
				JSONArray ExecuteDrugUseReason1 = ExecuteDrugUseReason.getJSONArray("ExecuteDrugUseReason");
				JSONObject EReason = ExecuteDrugUseReason1.getJSONObject(0);
				ExecuteDrugUseReason1.remove(0);
				EReason.put("Hiscode", mhiscode_user);
				ExecuteDrugUseReason1.add(EReason);
				ExecuteDrugUseReason.put("ExecuteDrugUseReason",ExecuteDrugUseReason1);
				json.put("ExecuteDrugUseReason",ExecuteDrugUseReason);
				map.put("gatherbaseinfo",json);
			}else{
//				String hiscode=prop.getProperty("hiscode");
				JSONObject PassClient = json.getJSONObject("PassClient");
				PassClient.put("HospID", mhiscode_user);
				json.put("PassClient", PassClient);
				map.put("gatherbaseinfo",json);
			}
			
			
			listchangejson.add(map);
		}
		sqlserverlist.clear();
		System.out.println("SQLSERVER案例数据整理成功");
		
		sql="truncate sa_gather_log_ch";
		jdbcTemplate.update(sql);
		System.out.println("临时表清理成功");
		
		sql="insert into sa_gather_log_ch(gatherbaseinfo,gatherresult,testname,anlitype,moduleid,modulename,testno) values(?,?,?,?,?,?,?)";
		try {
			springbatch.sa_gather_log(listchangejson,sql);
			listchangejson.clear();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		System.out.println("数据导入mysql临时表成功");
		
		
		List sqlserlistclear=null;
		
		//去重处理
		sql="select anlitype,moduleid,modulename,testname,gatherbaseinfo,gatherresult,testno from sa_gather_log_ch order by moduleid,testno asc ";
		listchangejson = jdbcTemplate.queryForList(sql);
		System.out.println("数据整理和排序成功");
		
		user=(User)req.getSession().getAttribute("user");
		int userid=Integer.parseInt(user.getUserid().toString());
		String testnobak="";
		int aaa=0;
		for(int i=0;i<listchangejson.size();i++){
			Map map = (Map)listchangejson.get(i);
			if(!testnobak.equals(map.get("testno").toString())){
				testnobak=map.get("testno").toString();
				aaa=0;
			}
			aaa=aaa+1;
			map.put("status", "1");
			map.put("projectid", projectid);
			map.put("testno",map.get("testno"));
			map.put("testin",map.get("gatherbaseinfo"));
			map.put("testout",map.get("gatherresult"));
			map.put("userid",userid);
			map.put("orderbyno",aaa);
			
			sqlserverlist.add(map);
		}
		System.out.println("MYSQL案例数据整理成功");
		
		sql="insert into pass_testmng(status,projectid,testname,testno,testin,testout,moduleid,modulename,anlitype,userid,orderbyno) values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			springbatch.pass_testmng(sqlserverlist,sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		System.out.println("数据导入案例表成功");
		
//		rs.close();
//		st.close();
//		sqlserconn.close();
		
		System.out.println("finish to do from sqlserver to mysql");
		return "ok";
	}
	
	public String screen_java(HttpServletRequest req) throws TimeoutException, UnsupportedEncodingException{
		String gatherbaseinfo=req.getParameter("testin");
		int testid=0;
		if(StringUtils.isNotBlank(req.getParameter("testid"))){
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		String sql="select serveraddress from serverip where serverid=?";
		String serveraddress=jdbcTemplate.queryForObject(sql, String.class,new Object[]{req.getParameter("serverid")});
		
		String gatherresult_java="";
		try{
			gatherresult_java = passservice.getPassResult(gatherbaseinfo, serveraddress);
		}catch (Exception e){
			gatherresult_java = "服务器连接失败";
		}
		
		
		sql="update pass_testmng set testout_response=? where testid=?";
		jdbcTemplate.update(sql, new Object[]{gatherresult_java,testid});
		
		return gatherresult_java;
	}
	
	public String screen_win(HttpServletRequest req) throws UnsupportedEncodingException, TimeoutException{
		String gatherbaseinfo=req.getParameter("testin");
		int testid=0;
		if(StringUtils.isNotBlank(req.getParameter("testid"))){
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		String sql="select serveraddress from serverip where serverid=?";
		String serveraddress=jdbcTemplate.queryForObject(sql, String.class,new Object[]{req.getParameter("serverid")});
		
		String gatherresult_win="";
		gatherresult_win = passservice.getPassResult(gatherbaseinfo, serveraddress);
		
		sql="update pass_testmng set testout=? where testid=?";
		jdbcTemplate.update(sql,new Object[]{gatherresult_win,testid});
		
		return gatherresult_win;
	}
	
	public String testmng_copy(HttpServletRequest req){
		int testid=0;
		String sql=null;
		
		if(StringUtils.isBlank(req.getParameter("testid"))){
			return "未收到案例ID";
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into pass_testmng(status,moduleid,modulename,projectid,testname,testno,testtext,testin,testout,remark,userid,inserttime,testresult) " + 
				" select 1 as status,moduleid,modulename,projectid,testname, testno,testtext,testin,testout,remark,userid,? as inserttime,'新' as testresult  " + 
				" from pass_testmng where  testid=?";
		jdbcTemplate.update(sql,new Object[]{sdf.format(time),testid});
		
		return "ok";
	}
}
