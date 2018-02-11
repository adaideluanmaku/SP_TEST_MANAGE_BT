package com.ch.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Executable;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ch.easyui.DataGrid;
import com.ch.http.Paservice;
import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
public class Testmngbean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	SocketBean socketBean;
	
	private static Logger log = Logger.getLogger(Testmngbean.class);
	
	//Team查询
	public DataGrid Team(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String teamname=null;
		if(StringUtils.isBlank(req.getParameter("teamname"))){
			teamname="";
		}else{
			teamname=req.getParameter("teamname");
		}
		
		sql="select * from team where teamname like ? order by teamname "+limit;
		log.debug(sql);
		rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+teamname+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(*) from team where teamname like ? ";
		total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+teamname+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//project查询
	public DataGrid Project(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String projectname=null;
		int teamid=0;
		if(StringUtils.isBlank(req.getParameter("projectname"))){
			projectname="";
		}else{
			projectname=req.getParameter("projectname");
		}
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			teamid=0;
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		if(teamid>0){
			sql="select a.*,b.teamname from project a inner join team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? order by b.teamname,a.projectname asc "+limit;
			log.debug(sql);
			rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%",teamid});
			datagrid.setRows(rslist);
			
			sql="select count(*) from project a inner join team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? ";
			total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%",teamid});
			datagrid.setTotal(total+0L);
			
			return datagrid;
		}
		
		sql="select a.*,b.teamname from project a inner join team b on a.teamid=b.teamid where a.projectname like ? order by b.teamname,a.projectname asc "+limit;
		log.debug(sql);
		rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(*) from project a inner join team b on a.teamid=b.teamid where a.projectname like ? ";
		total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//testmng查询
	public DataGrid Testmng(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String testname=null;
		String testno=null;
		int projectid=0;
		if(StringUtils.isBlank(req.getParameter("searchdate"))){
			testname="";
			testno="";
		}else{
			testname=req.getParameter("searchdate");
			testno=req.getParameter("searchdate");
		}
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			projectid=0;
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(projectid>0){
			sql="select a.*,b.projectname,c.username from testmng a inner join project b on a.projectid=b.projectid "
					+ "inner join sys_users c on a.userid=c.userid "
					+ "where (a.testname like ? or a.testno like ?) and a.projectid=? order by "
					+ "b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED),a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc "+limit;
			log.debug(sql);
			rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+testname+"%","%"+testno+"%", projectid});
			datagrid.setRows(rslist);
			
			sql="select count(*) from testmng a inner join project b on a.projectid=b.projectid where "
					+ "(a.testname like ? or a.testno like ?) and a.projectid=? ";
			total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+testname+"%","%"+testno+"%",projectid});
			datagrid.setTotal(total+0L);
			
			return datagrid;
		}
		sql="select a.*,b.projectname, c.username from testmng a inner join project b on a.projectid=b.projectid "
				+ "inner join sys_users c on a.userid=c.userid "
				+ "where a.testname like ? or a.testno like ? order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED), "
				+ "a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc "+limit;
		log.debug(sql);
		rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+testname+"%","%"+testno+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(*) from testmng a inner join project b on a.projectid=b.projectid where a.testname like ? or a.testno like ? ";
		total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+testname+"%","%"+testno+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//testmng查询
	public DataGrid testmng_share(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String testname=null;
		String testno=null;
		int projectid=0;
		if(StringUtils.isBlank(req.getParameter("searchdate"))){
			testname="";
			testno="";
		}else{
			testname=req.getParameter("searchdate");
			testno=req.getParameter("searchdate");
		}
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			projectid=0;
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(projectid>0){
			sql="select a.*,b.projectname,c.username from testmng a inner join project b on a.projectid=b.projectid "
					+ "inner join sys_users c on a.userid=c.userid "
					+ "where (a.testname like ? or a.testno like ?) and a.projectid=? and a.selenium_share_status=1 order by "
					+ "b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED),a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc "+limit;
			log.debug(sql);
			rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+testname+"%","%"+testno+"%", projectid});
			datagrid.setRows(rslist);
			
			sql="select count(*) from testmng a inner join project b on a.projectid=b.projectid where "
					+ "(a.testname like ? or a.testno like ?) and a.projectid=? and a.selenium_share_status=1 ";
			total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+testname+"%","%"+testno+"%",projectid});
			datagrid.setTotal(total+0L);
			
			return datagrid;
		}
		sql="select a.*,b.projectname, c.username from testmng a inner join project b on a.projectid=b.projectid "
				+ "inner join sys_users c on a.userid=c.userid "
				+ "where (a.testname like ? or a.testno like ?) and a.selenium_share_status=1 order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED), "
				+ "a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc "+limit;
		log.debug(sql);
		rslist=jdbcTemplate.queryForList(sql, new Object[]{"%"+testname+"%","%"+testno+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(*) from testmng a inner join project b on a.projectid=b.projectid where (a.testname like ? or a.testno like ?) "
				+ "and a.selenium_share_status=1 ";
		total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+testname+"%","%"+testno+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//script查询
	public DataGrid script(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		int testid=0;
		if(StringUtils.isBlank(req.getParameter("testid"))){
			testid=0;
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
		sql="select a.*,b.testno from script a, testmng b where a.testid=b.testid and a.testid = ? "
				+ "and a.scriptname like ? order by a.step asc "+limit;
		log.debug(sql);
		rslist=jdbcTemplate.queryForList(sql, new Object[]{testid,"%"+req.getParameter("scriptname")+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(*) from script a, testmng b where a.testid=b.testid and a.testid = ? "
				+ "and a.scriptname like ? ";
		total=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{testid,"%"+req.getParameter("scriptname")+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	public String teamadd(HttpServletRequest req){
		String teamname=null;
		String remark=null;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("teamname"))){
			return "团队名称不能为空";
		}else{
			teamname=req.getParameter("teamname").trim();
		}
		if(StringUtils.isBlank(req.getParameter("remark"))){
			remark="";
		}else{
			remark=req.getParameter("remark");
		}
		
		sql="select count(*) from team where teamname=? ";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname});
		if(sum>0){
			return "团队名称重复";
		}
		sql="insert into team(teamname,remark) values(?,?)";
		jdbcTemplate.update(sql,new Object[]{teamname,remark});
		return "ok";
	}
	
	public String teamupdate(HttpServletRequest req){
		String teamname=null;
		String remark=null;
		int teamid=0;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			return "团队ID不能为空";
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		if(StringUtils.isBlank(req.getParameter("teamname"))){
			return "团队名称不能为空";
		}else{
			teamname=req.getParameter("teamname");
		}
		if(StringUtils.isBlank(req.getParameter("remark"))){
			remark="";
		}else{
			remark=req.getParameter("remark");
		}
		
		sql="update team set remark=? where teamid=?";
		jdbcTemplate.update(sql,new Object[]{remark,teamid});
		return "ok";
	}
	
	public String teamdel(HttpServletRequest req){
		int teamid=0;
		String sql=null;
		HttpSession session=req.getSession();
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "非管理员权限";
//		}
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			return "后台未收到数据ID";
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		
		sql="delete from team where teamid=?";
		jdbcTemplate.update(sql,new Object[]{teamid});
		
		sql="select projectid from project where teamid=?";
		List list=jdbcTemplate.queryForList(sql,new Object[]{teamid});
		
		Map map=null;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			
			sql="select testid from testmng where projectid=?";
			List list1=jdbcTemplate.queryForList(sql,new Object[]{map.get("projectid")});
			
			for(int i1=0;i1<list1.size();i1++){
				int testid=Integer.parseInt(list1.get(i1).toString());
				sql="delete from script where testid=?";
				jdbcTemplate.update(sql,new Object[]{testid});
			}
			
			sql="delete from testmng where projectid=?";
			jdbcTemplate.update(sql,new Object[]{map.get("projectid")});
			
		}
		
		sql="delete from project where teamid=?";
		jdbcTemplate.update(sql,new Object[]{teamid});
		
		return "ok";
	}
	
	public List teamgroup(HttpServletRequest req){
		String sql=null;
		DataGrid datagrid=new DataGrid();
		sql="select teamid,teamname from team";
		List list=jdbcTemplate.queryForList(sql);
		Map map=new HashMap();
		map.put("teamid", 0);
		map.put("teamname","全选");
		list.add(map);
		//按字段重新排序
		Collections.sort(list, new Comparator<Map<String,Object>>() {
			//@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				//进行判断
				return String.valueOf(o1.get("teamid").toString()).compareTo(String.valueOf(o2.get("teamid").toString()));
			}
		});

		return list;
	}
	
	public String projectadd(HttpServletRequest req){
		int teamid=0;
		String projectname=null;
		String remark=null;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("teamid"))){
			return "未收到团队ID";
		}else{
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		if(StringUtils.isBlank(req.getParameter("projectname"))){
			return "项目名称不能为空";
		}else{
			projectname=req.getParameter("projectname").trim();
		}
		if(StringUtils.isBlank(req.getParameter("remark"))){
			remark="";
		}else{
			remark=req.getParameter("remark");
		}
		
		sql="select count(*) from project where projectname=? and teamid=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid});
		if(sum>0){
			return "项目名称重复";
		}
		
		sql="insert into project(teamid,projectname,remark) values(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{teamid,projectname,remark});
		return "ok";
	}
	
	public String projectupdate(HttpServletRequest req){
		int teamid=0;
		String projectname=null;
		String remark=null;
		int projectid=0;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "未收到团队ID";
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
		if(StringUtils.isBlank(req.getParameter("remark"))){
			remark="";
		}else{
			remark=req.getParameter("remark");
		}
		
		sql="update project set teamid=?,remark=?,projectname=? where projectid=?";
		jdbcTemplate.update(sql,new Object[]{teamid,remark,projectname,projectid});
		return "ok";
	}
	
	public String projectdel(HttpServletRequest req){
		int projectid=0;
		String sql=null;
		HttpSession session=req.getSession();
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "非管理员权限";
//		}
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "后台未收到数据ID";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		
		sql="delete from project where projectid=?";
		jdbcTemplate.update(sql,new Object[]{projectid});
		
		sql="select testid from testmng where projectid=?";
		List list=jdbcTemplate.queryForList(sql,new Object[]{projectid});
		
		for(int i=0;i<list.size();i++){
			int testid=Integer.parseInt(list.get(i).toString());
			sql="delete from script where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
		}
		
		sql="delete from testmng where projectid=?";
		jdbcTemplate.update(sql,new Object[]{projectid});
		return "ok";
	}
	
	public List projectgroup(HttpServletRequest req){
		String sql=null;
		sql="select projectid,projectname from project order by projectname asc";
		List list=jdbcTemplate.queryForList(sql);
		Map map=new HashMap();
		map.put("projectid", 0);
		map.put("projectname","全选");
		list.add(map);
		//按字段重新排序
		Collections.sort(list, new Comparator<Map<String,Object>>() {
			//@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				//进行判断
				return String.valueOf(o1.get("projectid").toString()).compareTo(String.valueOf(o2.get("projectid").toString()));
			}
		});

		return list;
	}
	
	public String testmngadd(HttpServletRequest req){
		int projectid=0;
		String testname=null;
		String testno=null;
		String testtext=null;
		String testin=null;
		String testout=null;
		String remark=null;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "未收到项目ID";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		if(StringUtils.isBlank(req.getParameter("testname"))){
			return "案例名称不能为空";
		}else{
			testname=req.getParameter("testname").trim();
		}
		if(StringUtils.isBlank(req.getParameter("testno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno").trim();
		}
		if(StringUtils.isBlank(req.getParameter("testtext"))){
			testtext="";
		}else{
			testtext=req.getParameter("testtext");
		}
		if(StringUtils.isBlank(req.getParameter("testin"))){
			testin="";
		}else{
			testin=req.getParameter("testin");
		}
		if(StringUtils.isBlank(req.getParameter("testout"))){
			testout="";
		}else{
			testout=req.getParameter("testout");
		}
		if(StringUtils.isBlank(req.getParameter("remark"))){
			remark="";
		}else{
			remark=req.getParameter("remark");
		}
		
		sql="select count(*) from testmng where projectid=? and testno=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno});
		if(sum>0){
			return "案例编号重复";
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into testmng(projectid,testname,testno,testtext,testin,testout,remark,inserttime,userid,status) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
				req.getSession().getAttribute("userid"),Integer.parseInt(req.getParameter("status"))});
		
		//广播通知
		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		
		
		return "ok";
	}
	
	public String testmngdel(HttpServletRequest req){
		int testid=0;
		String sql=null;
		HttpSession session=req.getSession();
//		if(!"admin".equals(session.getAttribute("loginname"))){
//			return "非管理员权限";
//		}
		if(StringUtils.isBlank(req.getParameter("testid"))){
			return "后台未收到数据ID";
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
		sql="delete from testmng where testid=?";
		jdbcTemplate.update(sql,new Object[]{testid});
		
		sql="delete from script where testid=?";
		jdbcTemplate.update(sql,new Object[]{testid});
		return "ok";
	}
	
	public String testmngupdate(HttpServletRequest req){
		int projectid=0;
		String testname=null;
		String testno=null;
		String testtext=null;
		String testin=null;
		String testout=null;
		String remark=null;
		String sql=null;
		int testid=0;
		int selenium_share_status=0;
		
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
		if(StringUtils.isBlank(req.getParameter("testno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno");
		}
		if(StringUtils.isBlank(req.getParameter("testtext"))){
			testtext="";
		}else{
			testtext=req.getParameter("testtext");
		}
		if(StringUtils.isBlank(req.getParameter("testin"))){
			testin="";
		}else{
			testin=req.getParameter("testin");
		}
		if(StringUtils.isBlank(req.getParameter("testout"))){
			testout="";
		}else{
			testout=req.getParameter("testout");
		}
		if(StringUtils.isBlank(req.getParameter("remark"))){
			remark="";
		}else{
			remark=req.getParameter("remark");
		}
		if(StringUtils.isBlank(req.getParameter("selenium_share_status"))){
			selenium_share_status=0;
		}else{
			selenium_share_status=Integer.parseInt(req.getParameter("selenium_share_status"));
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="select testresult from testmng where testid=? ";
		String testresult =jdbcTemplate.queryForObject(sql, String.class,new Object[]{testid});
		if("新".equals(testresult)){
			sql="update testmng set projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,selenium_share_status=?,testresult='' where testid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					req.getSession().getAttribute("userid"),Integer.parseInt(req.getParameter("status")),
					selenium_share_status,testid});
		}else{
			sql="update testmng set projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,selenium_share_status=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					req.getSession().getAttribute("userid"),Integer.parseInt(req.getParameter("status")),
					selenium_share_status,testid});
		}
		
		//广播通知
//		System.out.println(req.getSession().getAttribute("userid"));
		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		return "ok";
	}
	
	public String testmngcopy(HttpServletRequest req){
		int testid=0;
		String sql=null;
		
		if(StringUtils.isBlank(req.getParameter("testid"))){
			return "未收到案例ID";
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into testmng(projectid,testname,testno,testtext,testin,testout,remark,userid,inserttime,selenium_share_status,testresult) "
				+ "select projectid,testname, testno,testtext,testin,testout,remark,userid,? as inserttime, selenium_share_status,'新' as testresult  from testmng where  testid=?";
		jdbcTemplate.update(sql,new Object[]{sdf.format(time),testid});
		
		sql="select count(*) from script where testid=? ";
		int scriptsum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{testid});
		
		if(scriptsum>0){
			sql="select a.testid from testmng a,(select projectid,testname,testno,testtext,testin,testout,remark,userid from testmng where testid=?) b " + 
					"where a.projectid=b.projectid and a.testname=b.testname and a.testno=b.testno and a.testtext=b.testtext and a.testin=b.testin and " + 
					"a.testout=b.testout and a.remark=b.remark and a.userid=b.userid";
			List testlist=jdbcTemplate.queryForList(sql,new Object[]{testid});
			
			int testidcopy=0;
			for(int i=0;i<testlist.size();i++){
				Map map=(Map)testlist.get(i);
				if(Integer.parseInt(map.get("testid").toString())!=testid){
					testidcopy=Integer.parseInt(map.get("testid").toString());
				}
				
			}
			
			sql="insert into script(xpath,testvalue,scripttype,scriptname,step,testid,testurl) "
					+ "select xpath,testvalue,scripttype,scriptname,step,"+testidcopy+" as testid,testurl from script where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
		}
		
		//广播通知
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		return "ok";
	}
	
	@Async//异步处理
	public String scriptadd(HttpServletRequest req){
		String sql="";
		String testno="";
		String xpath="";
		String testvalue="";
		int scripttype=0;
		String scriptname="";
		int step=0;
		int testid=0;
		String testurl="";
		if(StringUtils.isBlank(req.getParameter("testno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno");
		}
		if(StringUtils.isNotBlank(req.getParameter("xpath"))){
			xpath=req.getParameter("xpath");
		}
		if(StringUtils.isNotBlank(req.getParameter("testvalue"))){
			testvalue=req.getParameter("testvalue");
		}
		if(StringUtils.isNotBlank(req.getParameter("scripttype"))){
			try{
				scripttype=Integer.parseInt(req.getParameter("scripttype"));
			}catch(Exception e){
				System.out.println(e);
			}
		}
		if(StringUtils.isNotBlank(req.getParameter("scriptname"))){
			scriptname=req.getParameter("scriptname");
		}
		if(StringUtils.isNotBlank(req.getParameter("step"))){
			try{
				step=Integer.parseInt(req.getParameter("step"));
			}catch(Exception e){
				System.out.println(e);
				return "步骤必须为数字";
			}
		}
		if(StringUtils.isNotBlank(req.getParameter("testid"))){
			try{
				testid=Integer.parseInt(req.getParameter("testid"));
			}catch(Exception e){
				System.out.println("testid转换异常："+e);
			}
		}
		if(StringUtils.isNotBlank(req.getParameter("testurl"))){
			testurl=req.getParameter("testurl");
		}
		
		//如果存在重复step，则后面的step+1
		sql="select count(*) from script where testid=? and step=?";
		int sum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{testid,step});
		if(sum>0){
			sql="update script a, (select scriptid,step+1 as step from script where testid=? and step>=?) b "
					+ "set a.step=b.step  where a.scriptid=b.scriptid";
			jdbcTemplate.update(sql,new Object[]{testid,step} );
		}
		
		sql="insert into script(xpath,testvalue,scripttype,scriptname,step,testid,testurl) values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{xpath,testvalue,scripttype,scriptname,step,testid,testurl} );
		
		return "ok";
	}
	
	public String scriptupdate(HttpServletRequest req){
		int scriptid=0;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("scriptid"))){
			return "后台未收到数据ID";
		}else{
			scriptid=Integer.parseInt(req.getParameter("scriptid"));
		}
		
		//！=0如果同一个脚本，同一个step，则不处理后面step，==0，则同一个脚本，step变更，则需要后面step+1
		sql="select count(*) from script where scriptid=? and step=?";
		int sum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{scriptid,req.getParameter("step")});
		
		if(sum==0){
			sql="select testid from script where scriptid=?";
			int testid=jdbcTemplate.queryForObject(sql, int.class,scriptid);
			
			//>0如果存在重复step，则后面的step+1,==0则没有重复的step
			sql="select count(*) from script where testid=? and step=?";
			int sum1=jdbcTemplate.queryForObject(sql, int.class,new Object[]{testid,req.getParameter("step")});
			if(sum1>0){
				sql="update script a, (select scriptid,step+1 as step from script where testid=? and step>=?) b "
						+ "set a.step=b.step  where a.scriptid=b.scriptid";
				jdbcTemplate.update(sql,new Object[]{testid,req.getParameter("step")} );
			}
		}
		
		sql="update script set xpath=?,testvalue=?,scripttype=?,scriptname=?,step=?,testurl=? where scriptid=?";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("xpath"),req.getParameter("testvalue"),
				req.getParameter("scripttype"),req.getParameter("scriptname"),
				req.getParameter("step"),req.getParameter("testurl"),scriptid});
		
		return "ok";
	}
	
	public String scriptdel(HttpServletRequest req){
		int scriptid=0;
		String sql=null;
		if(StringUtils.isBlank(req.getParameter("scriptid"))){
			return "后台未收到数据ID";
		}else{
			scriptid=Integer.parseInt(req.getParameter("scriptid"));
		}
		
		sql="delete from script where scriptid=?";
		jdbcTemplate.update(sql,new Object[]{scriptid});
		
		return "ok";
	}
	
	/**
	 * js上传图片到服务器，服务器保存二进制流到数据库
	 * @throws IOException 
	 * 
	 * */
	public void seleniumuploadfile(HttpServletRequest req) throws IOException{
		String sql=null;
		
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(  
//				req.getSession().getServletContext()); 
		
		MultipartRequest multipartRequest = (MultipartRequest) req;
		// 获得file集合
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			//循环获取file对象
			MultipartFile mf = entity.getValue();
			//输出到本地路径
			//file名称
//			String fileName = mf.getOriginalFilename();
//			FileOutputStream fos = new FileOutputStream(new File("E:/"+fileName));
//			fos.write(mf.getBytes());
//			fos.close();
			//保存在数据库
			
			sql="insert into seleniumfiles(seleniumfile,testid) values(?,?) ";
			jdbcTemplate.update(sql, new Object[]{mf.getBytes(),req.getParameter("testid")});
			
			log.debug(sql);
		}
	}
	
	/**
	 * 文件下载，从数据库取出二进制流数据
	 * 
	 * */
	public List seleniumdownfile(HttpServletRequest req){
		String sql=null;
		List list=null;
		
		sql="select filename,seleniumfile from seleniumfiles where testid=?";
		list=jdbcTemplate.queryForList(sql, new Object[]{req.getParameter("testid")});
		
		return list;
	}
	
	public void seleniumfile_del(HttpServletRequest req){
		String sql=null;
		
		sql="delete from seleniumfiles where testid=?";
		jdbcTemplate.update(sql, new Object[]{req.getParameter("testid")});
		
	}
}
