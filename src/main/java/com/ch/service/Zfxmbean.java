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

import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.Select2;

@Service
public class Zfxmbean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
		
		sql="select * from zfxm_team  where 1=1 ";
		sql1="select count(*) from zfxm_team  where 1=1 ";
		
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
		
//		sql="select * from zfxm_team where teamname like ? order by teamname limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+teamname+"%"});
//		
//		sql="select count(*) from zfxm_team where teamname like ? ";
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
		sql="select count(1) from zfxm_team where teamname=? ";
		rsl=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname});
		if(rsl>0){
			return "团队名称不能重复";
		}
		
		sql="insert into zfxm_team(teamname,remark) values(?,?)";
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
			
			sql="delete a from zfxm_files a "
					+ "inner join zfxm_script b on a.linkid=b.scriptid "  
					+ "inner join zfxm_testmng c on b.testid=c.testid "
					+ "inner join zfxm_project d on c.projectid=d.projectid "
					+ "inner join zfxm_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? and a.linktype=2 ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete b from zfxm_script b " 
					+ "inner join zfxm_testmng c on b.testid=c.testid "  
					+ "inner join zfxm_project d on c.projectid=d.projectid "
					+ "inner join zfxm_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete c from zfxm_testmng c "  
					+ "inner join zfxm_project d on c.projectid=d.projectid " 
					+ "inner join zfxm_team e on d.teamid=e.teamid " 
					+ "where e.teamid=? ";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete from zfxm_project where teamid=?";
			jdbcTemplate.update(sql,new Object[]{teamid});
			
			sql="delete from zfxm_team where teamid=?";
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
		
		sql="select count(*) from zfxm_team where teamname=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{teamname});
		if(sum>0){
			return "团队名称不能重复";
		}
		
		sql="update zfxm_team set teamname=?,remark=? where teamid=?";
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
		
		sql="select a.*,b.teamname,b.teamid from zfxm_project a inner join zfxm_team b on a.teamid=b.teamid where 1=1 " ;
		sql1="select count(1) from zfxm_project a inner join zfxm_team b on a.teamid=b.teamid where 1=1 ";
		
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
//			sql="select a.*,b.teamname,b.teamid from zfxm_project a inner join zfxm_team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? order by b.teamname,a.projectname asc limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%",teamid});
//			
//			sql="select count(1) from zfxm_project a inner join zfxm_team b on a.teamid=b.teamid where a.projectname like ? and a.teamid=? ";
//			int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%",teamid});
//			
//			dataGrid.setTotal(count+0L);
//			dataGrid.setRows(lstRes);
//			
//			return dataGrid;
//		}
//		
//		sql="select a.*,b.teamname,b.teamid from zfxm_project a inner join zfxm_team b on a.teamid=b.teamid where a.projectname like ? order by b.teamname,a.projectname asc limit "+offset+","+limit;
//		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+projectname+"%"});
//		
//		sql="select count(1) from zfxm_project a inner join zfxm_team b on a.teamid=b.teamid where a.projectname like ? ";
//		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+projectname+"%"});
		
		lstRes=jdbcTemplate.queryForList(sql, wherelist.toArray());
		int count=jdbcTemplate.queryForObject(sql1, Integer.class,wherelist.toArray());
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public List teamgroup(HttpServletRequest req){
		String sql=null;
		sql="select teamid as id,teamname as text from zfxm_team";
		List list=jdbcTemplate.queryForList(sql);
//		Map map=new HashMap();
//		map.put("id", 0);
//		map.put("text","全选");
//		list.add(map);
//		//按字段重新排序
//		Collections.sort(list, new Comparator<Map<String,Object>>() {
//			//@Override
//			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//				//进行判断
//				return String.valueOf(o1.get("id").toString()).compareTo(String.valueOf(o2.get("id").toString()));
//			}
//		});

		return list;
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
		
		sql="select count(*) from zfxm_project where projectname=? and teamid=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid});
		if(sum>0){
			return "项目名称重复";
		}
		
		sql="insert into zfxm_project(teamid,projectname,remark) values(?,?,?)";
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
			
			sql="delete from zfxm_project where projectid=?";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete a from zfxm_files a "
					+ "inner join zfxm_script b on a.linkid=b.scriptid "  
					+ "inner join zfxm_testmng c on b.testid=c.testid inner join project d on c.projectid=d.projectid " 
					+ "where d.projectid=? and a.linktype=2 ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete b from zfxm_script b " 
					+ "inner join zfxm_testmng c on b.testid=c.testid "  
					+ "inner join zfxm_project d on c.projectid=d.projectid " 
					+ "where d.projectid=? ";
			jdbcTemplate.update(sql,new Object[]{projectid});
			
			sql="delete from zfxm_testmng where projectid=? ";
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
		
		sql="select count(*) from zfxm_project where projectname=? and teamid=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectname,teamid});
		if(sum>0){
			return "团队名称重复";
		}
		
		sql="update zfxm_project set teamid=?,remark=?,projectname=? where projectid=?";
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
		}
		if(StringUtils.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		
		sql1="select a.*,b.projectname,c.username from zfxm_testmng a inner join zfxm_project b on a.projectid=b.projectid "
				+ "left join sys_users c on a.userid=c.userid "
				+ "where 1=1 ";
		sql2="select count(*) from zfxm_testmng a inner join zfxm_project b on a.projectid=b.projectid where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(projectid>0){
			sql1=sql1+" and (a.testname like ? or a.testno like ?) and a.projectid=? ";
			sql2=sql2+" and (a.testname like ? or a.testno like ?) and a.projectid=? ";
			wherelist.add("%"+testname+"%");
			wherelist.add("%"+testno+"%");
			wherelist.add(projectid);
		}else{
			sql1=sql1+" and a.testname like ? or a.testno like ? ";
			sql2=sql2+" and a.testname like ? or a.testno like ? ";
			wherelist.add("%"+testname+"%");
			wherelist.add("%"+testno+"%");
		}
		
		//拼接order
		if(StringUtils.isBlank(sort)){
			sql1=sql1+ "order by b.projectid,CAST(SUBSTRING_INDEX(a.testno, \"-\", 1) as SIGNED),a.testname, CAST(SUBSTRING_INDEX(a.testno, \"-\", -1) as SIGNED) asc ";
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
	
	public List projectgroup(HttpServletRequest req){
		String sql=null;
		int teamid=0;
		
		if(StringUtils.isNotBlank(req.getParameter("teamid"))){
			teamid=Integer.parseInt(req.getParameter("teamid"));
		}
		
		List list=null;
		if(teamid>0){
			sql="select a.projectid as id , a.projectname as text from zfxm_project a, zfxm_team b where "
					+ " a.teamid=b.teamid and b.teamid=? order by a.projectname asc";
			list=jdbcTemplate.queryForList(sql,new Object[]{teamid});
		}else{
			sql="select projectid as id ,projectname as text from zfxm_project order by projectname asc";
			list=jdbcTemplate.queryForList(sql);
		}
		
		return list;
	}
	
	public String testmng_add(HttpServletRequest req){
		int projectid=0;
		String testname="";
		String testno="";
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
		if(StringUtils.isBlank(req.getParameter("testno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno").trim();
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
		sql="select count(1) from zfxm_testmng where projectid=? and testno=?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno});
		if(sum>0){
			return "案例编号重复";
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(sdf.format(time));
		
		sql="insert into zfxm_testmng(projectid,testname,testno,testtext,testin,testout,remark,inserttime,userid,status,selenium_share_status) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
				req.getSession().getAttribute("userid"),Integer.parseInt(req.getParameter("status")),selenium_share_status});
		
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
			
			sql="delete from zfxm_testmng where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete a from zfxm_files a , zfxm_script b where a.linkid=b.scriptid and a.linktype=2 and b.testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
			sql="delete from zfxm_script where testid=?";
			jdbcTemplate.update(sql,new Object[]{testid});
			
		}
		
		return "ok";
	}
	
	public String testmng_update(HttpServletRequest req){
		int projectid=0;
		String testname="";
		String testno="";
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
		if(StringUtils.isBlank(req.getParameter("testno"))){
			return "案例编号不能为空";
		}else{
			testno=req.getParameter("testno");
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
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="select count(*) from zfxm_testmng where projectid<>? and testno=? and testid<>?";
		int sum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{projectid,testno,testid});
		if(sum>0){
			return "案例编号不能重复";
		}
		
		sql="select testresult from zfxm_testmng where testid=? ";
		String testresult =jdbcTemplate.queryForObject(sql, String.class,new Object[]{testid});
		if("新".equals(testresult)){
			sql="update zfxm_testmng set projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,selenium_share_status=?,testresult='' where testid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					req.getSession().getAttribute("userid"),status,
					selenium_share_status,testid});
		}else{
			sql="update zfxm_testmng set projectid=?,testname=?,testno=?,testtext=?,testin=?,testout=?,remark=?,inserttime=?, "
					+ "userid=?,status=?,selenium_share_status=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{projectid,testname,testno,testtext,testin,testout,remark,sdf.format(time),
					req.getSession().getAttribute("userid"),status,
					selenium_share_status,testid});
		}
		
		//广播通知
//		System.out.println(req.getSession().getAttribute("userid"));
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
		return "ok";
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
		
		sql="insert into zfxm_testmng(projectid,testname,testno,testtext,testin,testout,remark,userid,inserttime,selenium_share_status,testresult) "
				+ "select projectid,testname, testno,testtext,testin,testout,remark,userid,? as inserttime, selenium_share_status,'新' as testresult  from zfxm_testmng where  testid=?";
		jdbcTemplate.update(sql,new Object[]{sdf.format(time),testid});
		
		return "ok";
	}
	
}
