package com.ch.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Executable;
import java.sql.Blob;
import java.sql.SQLException;
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
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ch.pajson.Pa_json;
import com.ch.sys.Paservice;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;
import com.ch.sysuntils.User;
import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
public class Pabean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static Jedis jedis;
	public static final String PA_SCREENRESULTS = "PA_SCREENRESULT_LIST";
	
	@Value("${redis.host}")
	private String redisip;
	@Value("${redis.port}")
	private int redisport;
	@Value("${redis.pass}")
	private String redisauth;
	@Value("${redis.default.db}")
	private int redisselect;
	
	@Autowired
	Paservice paservice;
	@Autowired
	Pa_json pa_json;
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
		
		sql="select * from pa_team  where 1=1 ";
		sql1="select count(*) from pa_team  where 1=1 ";
		
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
		
//		sql="select * from pa_team where teamname like ? order by teamname limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+teamname+"%"});
//		
//		sql="select count(*) from pa_team where teamname like ? ";
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
		sql="select count(1) from pa_team where teamname=? ";
		rsl=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname});
		if(rsl>0){
			return "团队名称不能重复";
		}
		
		sql="insert into pa_team(teamname,remark) values(?,?)";
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
		
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		for(int i=0;i<list.size();i++){
			teamid=list.get(i);
			
			sql="delete a from pa_files a "
					+ "inner join pa_script b on a.linkid=b.scriptid "  
					+ "inner join pa_testmng c on b.testid=c.testid "
					+ "inner join pa_project d on c.projectid=d.projectid "
					+ "inner join pa_team e on d.teamid=e.teamid "
					+ "inser join sys_users f on c.userid=f.userid " 
					+ "where e.teamid=? and a.linktype=2 and f.userid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid,user.getUserid()});
			
			sql="delete b from pa_script b " 
					+ "inner join pa_testmng c on b.testid=c.testid "  
					+ "inner join pa_project d on c.projectid=d.projectid "
					+ "inner join pa_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete c from pa_testmng c "  
					+ "inner join pa_project d on c.projectid=d.projectid " 
					+ "inner join pa_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete from pa_project where teamid=?";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete from pa_team where teamid=?";
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
		
		sql="select count(*) from pa_team where teamname=? and teamid<>?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname,teamid});
		if(sum>0){
			return "团队名称不能重复";
		}
		
		sql="update pa_team set teamname=?,remark=? where teamid=?";
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
		
		sql="select a.*,b.teamname,b.teamid from pa_project a inner join pa_team b on a.teamid=b.teamid where 1=1 " ;
		sql1="select count(1) from pa_project a inner join pa_team b on a.teamid=b.teamid where 1=1 ";
		
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
//			sql="select a.*,b.teamname,b.teamid from pa_project a inner join pa_team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? order by b.teamname,a.projectname asc limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%",teamid});
//			
//			sql="select count(1) from pa_project a inner join pa_team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? ";
//			int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%",teamid});
//			
//			dataGrid.setTotal(count+0L);
//			dataGrid.setRows(lstRes);
//			
//			return dataGrid;
//		}
//		
//		sql="select a.*,b.teamname,b.teamid from pa_project a inner join pa_team b on a.teamid=b.teamid where a.projectname like ? order by b.teamname,a.projectname asc limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%"});
//		
//		sql="select count(1) from pa_project a inner join pa_team b on a.teamid=b.teamid where a.projectname like ? ";
//		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%"});
		
		lstRes=jdbcTemplate.queryForList(sql, wherelist.toArray());
		int count=jdbcTemplate.queryForObject(sql1, Integer.class,wherelist.toArray());
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
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
		
		sql="select count(*) from pa_project where projectname=? and teamid=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid});
		if(sum>0){
			return "项目名称重复";
		}
		
		sql="insert into pa_project(teamid,projectname,remark) values(?,?,?)";
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
			
			sql="delete from pa_project where projectid=?";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete a from pa_files a "
					+ "inner join pa_script b on a.linkid=b.scriptid "  
					+ "inner join pa_testmng c on b.testid=c.testid inner join project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and a.linktype=2 ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete b from pa_script b " 
					+ "inner join pa_testmng c on b.testid=c.testid "  
					+ "inner join pa_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete from pa_testmng where projectid=? ";
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
		if(StringUtils.isNotBlank(req.getParameter("remark"))){
			remark=req.getParameter("remark");
		}
		
		sql="select count(*) from pa_project where projectname=? and teamid=? and projectid<>?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid,projectid});
		if(sum>0){
			return "团队名称重复";
		}
		
		sql="update pa_project set teamid=?,remark=?,projectname=? where projectid=?";
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
		String testin="";
		String testtext="";
		String testout="";
		int projectid=0;
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
		if(StringUtils.isNotBlank(req.getParameter("searchdata"))){
			testname=req.getParameter("searchdata");
			testno=req.getParameter("searchdata");
			testin=req.getParameter("searchdata");
			testtext=req.getParameter("searchdata");
			testout=req.getParameter("searchdata");
		}
		if(StringUtils.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		
		sql1="select a.*,b.projectname,c.username,d.teamid,d.teamname  from pa_testmng a inner join pa_project b on a.projectid=b.projectid "
				+ "left join sys_users c on a.userid=c.userid "
				+ "left join pa_team d on b.teamid=d.teamid "
				+ "where 1=1 ";
		sql2="select count(*) from pa_testmng a inner join pa_project b on a.projectid=b.projectid where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(projectid>0){
			sql1=sql1+" and (a.testname like ? or a.testno like ? or a.testin like ? or a.testtext like ? or a.testout like ?) and a.projectid=? ";
			sql2=sql2+" and (a.testname like ? or a.testno like ? or a.testin like ? or a.testtext like ? or a.testout like ?) and a.projectid=? ";
			wherelist.add("%"+testname+"%");
			wherelist.add("%"+testno+"%");
			wherelist.add("%"+testin+"%");
			wherelist.add("%"+testtext+"%");
			wherelist.add("%"+testout+"%");
			wherelist.add(projectid);
		}else{
			sql1=sql1+" and (a.testname like ? or a.testno like ?  or a.testin like ? or a.testtext like ? or a.testout like ?) ";
			sql2=sql2+" and (a.testname like ? or a.testno like ?  or a.testin like ? or a.testtext like ? or a.testout like ?) ";
			wherelist.add("%"+testname+"%");
			wherelist.add("%"+testno+"%");
			wherelist.add("%"+testin+"%");
			wherelist.add("%"+testtext+"%");
			wherelist.add("%"+testout+"%");
		}
		
		//拼接order
		if(StringUtils.isBlank(sort)){
//			sql1=sql1+ "order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED), CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc ";
			sql1=sql1+" order by b.projectid , a.testno , a.orderbyno asc ";
		}else{
			sql1=sql1+ "order by "+sort+" "+order;
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
		int selenium_share_status=0;
		
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
		if(StringUtils.isBlank(req.getParameter("testno")) || StringUtils.isBlank(req.getParameter("orderbyno"))){
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
		if(StringUtils.isNotBlank(req.getParameter("selenium_share_status"))){
			selenium_share_status=Integer.parseInt(req.getParameter("selenium_share_status"));
		} 
//		sql="select count(1) from pa_testmng where projectid=? and testno=?";
//		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno});
//		if(sum>0){
//			return "案例编号重复";
//		}
		
		//号码重复往后+1
		sql="select count(1) from pa_testmng where projectid=? and testno=? and orderbyno=? ";
		int countnum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{projectid,testno,orderbyno});
		if(countnum>0){
			sql="update pa_testmng set orderbyno=orderbyno+1 where projectid=? and testno=? and orderbyno>=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testno,orderbyno});
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		System.out.println(sdf.format(time));
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		sql="insert into pa_testmng(projectid,testname,testno,testtext,testin,testout,remark,inserttime,userid,status,selenium_share_status,orderbyno) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
				user.getUserid(),Integer.parseInt(req.getParameter("status")),selenium_share_status,orderbyno});
		
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
			
			sql="delete from pa_testmng where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete a from pa_files a , pa_script b where a.linkid=b.scriptid and a.linktype=2 and b.testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete from pa_script where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
		}
		
		return "ok";
	}
	
	public String testmng_update(HttpServletRequest req){
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
		int selenium_share_status=0;
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
		
		if(StringUtils.isBlank(req.getParameter("selenium_share_status")) || "null".equals(req.getParameter("selenium_share_status"))){
			selenium_share_status=0;
		}else{
			selenium_share_status=Integer.parseInt(req.getParameter("selenium_share_status"));
		}
		
		if(StringUtils.isNotBlank(req.getParameter("status")) && !"null".equals(req.getParameter("status"))){
			status=Integer.parseInt(req.getParameter("status"));
		} 
		
		
		
//		sql="select count(*) from pa_testmng where projectid=? and testno=? and testid<>?";
//		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno,testid});
//		if(sum>0){
//			return "案例编号不能重复";
//		}
		
		//号码重复往后+1
		sql="select count(1) from pa_testmng where projectid=? and testno=? and orderbyno=? ";
		int countnum=jdbcTemplate.queryForObject(sql, int.class,new Object[]{projectid,testno,orderbyno});
		if(countnum>0){
			sql="update pa_testmng set orderbyno=orderbyno+1 where projectid=? and testno=? and orderbyno>=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testno,orderbyno});
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		sql="select testresult from pa_testmng where testid=? ";
		String testresult =jdbcTemplate.queryForObject(sql, String.class,new Object[]{testid});
		if("新".equals(testresult)){
			sql="update pa_testmng set projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,selenium_share_status=?,testresult='',orderbyno=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					user.getUserid(),status,
					selenium_share_status,orderbyno,testid});
		}else{
			sql="update pa_testmng set projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,selenium_share_status=?,orderbyno=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					user.getUserid(),status,
					selenium_share_status,orderbyno,testid});
		}
		
		//广播通知
//		System.out.println(req.getSession().getAttribute("userid"));
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		return "ok";
	}
	
	public String pa_screen(HttpServletRequest req){
		JSONArray json_java=new JSONArray();
		String testout=null;
		int testid=0;
		String sql=null;
		String duibiresult=null;
		String VisitCode=req.getParameter("VisitCode");
		
		if(StringUtils.isBlank(req.getParameter("testid"))){
			return "未收到案例ID";
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
		//1java案例，2win案例
		if(Integer.parseInt(req.getParameter("state"))==1){
			//清理单个案例缓存
			pa_redis_clear(VisitCode);
		}
		
		//测试案例
		sql="select testin from pa_testmng where testid=?";
		String testin=jdbcTemplate.queryForObject(sql,String.class,new Object[]{testid});
		
		//取url
		String url=req.getParameter("serveraddress");
		
		if("".equals(url) || url==null){
			return "screen url is null";
		}
		//调审查接口,服务器将结果存在redis中
		boolean result=false;
		try {
			result=paservice.getPaResult(testin, url);
			
			if(!result){
				return "PASS CORE服务连接失败";
			}
			
			if(Integer.parseInt(req.getParameter("state"))==2){
				
				return "审查结束,请到PA-WIN的sqlserver数据库找结果";
			}
			//redis连接
			jedis = new Jedis(redisip, redisport);
			jedis.auth(redisauth);
			jedis.select(redisselect);

//			System.out.println("redis连接："+jedis.ping());
			if(!"PONG".equals(jedis.ping())){
				return "redis连接失败";
			}
			
			//获取结果
			List<String> values = jedis.lrange("PA_SCREENRESULT_LIST", 0, -1);
			
			//和数据库预期结果做对比
			sql="select testout from pa_testmng where testid=?";
			testout=jdbcTemplate.queryForObject(sql,String.class,new Object[]{testid});
			
			for(int i=0;i<values.size();i++){
				JSONObject json=JSONObject.fromObject(values.get(i));
				if(VisitCode.equals(json.get("visitcode"))){
					json_java.add(json);
				}
			}
			
			duibiresult=pa_json.Jsonduibi(json_java.toString(),testout);
			if(StringUtils.isNotBlank(duibiresult)){
				sql="update pa_testmng set testout_response=?,testresult=? where testid=?";
				jdbcTemplate.update(sql,new Object[]{json_java.toString(),"不通过",testid});
			}else{
				sql="update pa_testmng set testout_response=?, testresult=? where testid=?";
				jdbcTemplate.update(sql,new Object[]{json_java.toString(),"通过",testid});
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("pa审查结果异常");
			return "pa审查结果异常";
		}
		
		if(StringUtils.isBlank(duibiresult)){
			duibiresult=duibiresult
					+"<div style='color: black'>审查结果</div><br>"
					+"<div style='color:red'>断言： "+testout+"</div><br>"
					+"<div style='color:blue'>响应： "+json_java.toString()+"</div><br>"
					+ "<hr style=' height:2px;border:none;border-top:2px dotted #185598;' /><br>"
					+"<div style='color:blue'>OK</div><br>";
		}else{
			duibiresult="<div style='color: black'>审查结果</div><br>"
					+ "<div style='color:red'>断言： "+testout+"</div><br>"
					+"<div style='color:blue'>响应： "+json_java.toString()+"</div><br>"
					+"<hr style=' height:2px;border:none;border-top:2px dotted #185598;' /><br>"
					+duibiresult;
		}
		return duibiresult;
		//广播通知
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
	}
	
	//清空redis某条数据
	public void pa_redis_clear(String VisitCode){
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);

		System.out.println("redis连接："+jedis.ping());
//			if(!"PONG".equals(jedis.ping())){
//				return "redis连接失败";
//			}
		
		//redis数据类型为list时,用lrange取数据
		List<String> values = jedis.lrange(PA_SCREENRESULTS, 0, -1);
		for(int i=0;i<values.size();i++){
//				System.out.println(values.get(i));
			JSONObject json=JSONObject.fromObject(values.get(i));
			
			if(VisitCode.equals(json.get("visitcode"))){
				values.remove(i);
				i=i-1;
			}
		}
		
		jedis.del(PA_SCREENRESULTS);
		
		//将list数据存回redis
		if(values.size()!=0){
			for(int i=0;i<values.size();i++){
				jedis.lpush(PA_SCREENRESULTS, values.get(i));
			}
		}
	}
	
	public String pa_screen_all(HttpServletRequest req){
		String sql=null;
		int projectid=0;
		String duibiresult=null;
		String search_data="";
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "未收到案例ID";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		
		if(StringUtils.isNotBlank(req.getParameter("search_data"))){
			search_data=req.getParameter("search_data");
		}
		
		//清理所有案例缓存
		pa_redis_clear_all();
		
		//测试案例
		//获取测试案例，testin发送请求，testout对比结果
		sql="select testid,testin,testout,testno,testname from pa_testmng where projectid=? and (testname like ? or testno like ?) and status=1";
		List testlist=jdbcTemplate.queryForList(sql,new Object[]{projectid,"%"+search_data+"%","%"+search_data+"%"});
		
		sql="update pa_testmng set testresult='' where projectid=? ";
		jdbcTemplate.update(sql,new Object[]{projectid});
		
		//取url
		String url=req.getParameter("serveraddress");
		if("".equals(url) || url==null){
			return "screen url is null";
		}
		
		//调审查接口,服务器将结果存在redis中
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);
//		System.out.println("redis连接："+jedis.ping());
		if(!"PONG".equals(jedis.ping())){
			return "redis连接失败";
		}
		
		try {
			for(int i=0;i<testlist.size();i++){
				System.out.println("自动审查案例数："+(i+1));
				
				Map map=(Map)testlist.get(i);
				String testin=map.get("testin").toString();
//				System.out.println("调用pa_screen_java接口审查案例-案例编号是："+map.get("testno"));
				paservice.getPaResult(testin, url);
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//获取结果
			List<String> values = jedis.lrange("PA_SCREENRESULT_LIST", 0, -1);
			
			for(int i=0;i<testlist.size();i++){
				Map map=(Map)testlist.get(i);
				String VisitCode=null;
				try{
					JSONObject testinjson=JSONObject.fromObject(map.get("testin"));
					JSONObject Patient=testinjson.getJSONObject("Patient");
					VisitCode=Patient.getString("VisitCode");
				}catch(Exception e){
					System.out.println("审查案例JSON解析错误："+map.get("testno"));
					continue;
				}
				
				JSONArray json_java=new JSONArray();
				for(int i1=0;i1<values.size();i1++){
					JSONObject json=JSONObject.fromObject(values.get(i1));
					if(VisitCode.equals(json.get("visitcode"))){
						json_java.add(json);
					}
				}
				System.out.println("开始对比pa_screen_java和案例结果-案例编号是："+map.get("testno"));
				
				duibiresult=pa_json.Jsonduibi(json_java.toString(),map.get("testout").toString());
				if(StringUtils.isNotBlank(duibiresult)){
					sql="update pa_testmng set testout_response=?,testresult=? where testid=?";
					jdbcTemplate.update(sql,new Object[]{json_java.toString(),"不通过",map.get("testid")});
				}else{
					sql="update pa_testmng set  testout_response=?,testresult=? where testid=?";
					jdbcTemplate.update(sql,new Object[]{json_java.toString(),"通过",map.get("testid")});
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("pa审查结果异常");
			return "pa审查结果异常";
		}
		
		//清理所有案例缓存
		pa_redis_clear_all();
		return "全部测试结束";
		//广播通知
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
	}
	
	//清空redis
	public void pa_redis_clear_all(){
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);
		
		jedis.del(PA_SCREENRESULTS);
		
	}
	
	//清空redis
	public String pa_redis_clear_sd(HttpServletRequest req){
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);

		if(!"PONG".equals(jedis.ping())){
			return "redis连接失败";
		}
		
		//删除
		jedis.del(PA_SCREENRESULTS);
		
		return "redis审查结果清空成功";
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
		
		sql="insert into pa_testmng(projectid,testname,testno,testtext,testin,testout,remark,userid,inserttime,selenium_share_status,testresult) "
				+ "select projectid,testname, testno,testtext,testin,testout,remark,userid,? as inserttime, selenium_share_status,'新' as testresult  from pa_testmng where  testid=?";
		jdbcTemplate.update(sql,new Object[]{sdf.format(time),testid});
		
		return "ok";
	}
}
