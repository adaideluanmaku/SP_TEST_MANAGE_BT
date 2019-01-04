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
import com.ch.sysuntils.User;

@Service
public class Worksbean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//查询
	public DataGrid zhouhuibao_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int limit=0;
		//当前页编号,已乘以当前页总数
		int offset=0; 
		
		String sql=null;
		String sql1=null;
		String StartDate="";
		String EndDate="";
		
		if(StringUtil.isNotBlank(req.getParameter("limit"))){
			limit=Integer.parseInt(req.getParameter("limit"));
		} 
		if(StringUtil.isNotBlank(req.getParameter("offset"))){
			offset=Integer.parseInt(req.getParameter("offset"));
		} 
		if(StringUtil.isNotBlank(req.getParameter("StartDate"))){
			StartDate=req.getParameter("StartDate");
		} 
		if(StringUtil.isNotBlank(req.getParameter("EndDate"))){
			EndDate=req.getParameter("EndDate");
		} 
		sql="select a.*,b.username from works_zhouhuibao a inner join sys_users b on a.userid=b.userid where 1=1 ";
		sql1="select count(*) from works_zhouhuibao a inner join sys_users b on a.userid=b.userid where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(StringUtils.isNotBlank(StartDate)){
			sql=sql+" and a.submittime >= ? ";
			sql1=sql1+" and a.submittime >= ? ";
			wherelist.add(StartDate);
		}
		
		if(StringUtils.isNotBlank(EndDate)){
			sql=sql+" and a.submittime <= ? ";
			sql1=sql1+" and a.submittime <= ? ";
			wherelist.add(EndDate);
		}
		
		//拼接order
		sql=sql+" order by submittime desc ";
		
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
	
	public String zhouhuibao_add(HttpServletRequest req){
		String submittime="";
		String worktext="";
		String sql=null;
		if(StringUtils.isNotBlank(req.getParameter("submittime"))){
			submittime=req.getParameter("submittime");
		}
		if(StringUtils.isNotBlank(req.getParameter("worktext"))){
			worktext=req.getParameter("worktext");
		}
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into works_zhouhuibao(submittime,worktext,userid,inserttime) values(?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{submittime,worktext,user.getUserid(),sdf.format(date)});
		
		return "ok";
	}
	
	public String zhouhuibao_del(HttpServletRequest req,Integer[]  workzhouhuibaoids){
		int workzhouhuibaoid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(workzhouhuibaoids);
		
		if(list.size()==0){
			return "workzhouhuibaoid不能为空";
		}
		
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		sql="select level from sys_users where userid=? ";
		int level=jdbcTemplate.queryForObject(sql, int.class,new Object[]{user.getUserid()});
		
		for(int i=0;i<list.size();i++){
			workzhouhuibaoid=list.get(i);
			if(level ==1){
				sql="delete from works_zhouhuibao where workzhouhuibaoid=? ";
				jdbcTemplate.update(sql,new Object[]{workzhouhuibaoid});
			}else{
				sql="delete from works_zhouhuibao where workzhouhuibaoid=? and userid=? ";
				jdbcTemplate.update(sql,new Object[]{workzhouhuibaoid,user.getUserid()});
			}
		}
		
		return "ok";
	}
	
	public String zhouhuibao_update(HttpServletRequest req){
		String worktext="";
		int workzhouhuibaoid=0;
		String sql="";
		if(StringUtils.isNotBlank(req.getParameter("workzhouhuibaoid"))){
			workzhouhuibaoid=Integer.parseInt(req.getParameter("workzhouhuibaoid"));
		}
		if(StringUtils.isNotBlank(req.getParameter("worktext"))){
			worktext=req.getParameter("worktext");
		}
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="select level from sys_users where userid=? ";
		int level=jdbcTemplate.queryForObject(sql, int.class,new Object[]{user.getUserid()});
		
		if(level==1){
			sql="update works_zhouhuibao set worktext=?,inserttime=? where workzhouhuibaoid=? ";
			jdbcTemplate.update(sql,new Object[]{worktext,sdf.format(date)+"_"+user.getUsername(),workzhouhuibaoid });
		}else{
			sql="update works_zhouhuibao set worktext=?,inserttime=? where workzhouhuibaoid=? and userid=?";
			jdbcTemplate.update(sql,new Object[]{worktext,sdf.format(date),workzhouhuibaoid,user.getUserid()});
		}
		
		return "ok";
	}
	
	
	//查询
	public DataGrid yuehuibao_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		List lstRes = new ArrayList();
		//单页条数
		int limit=0;
		//当前页编号,已乘以当前页总数
		int offset=0; 
		
		String sql=null;
		String sql1=null;
		String StartDate="";
		String EndDate="";
		
		if(StringUtil.isNotBlank(req.getParameter("limit"))){
			limit=Integer.parseInt(req.getParameter("limit"));
		} 
		if(StringUtil.isNotBlank(req.getParameter("offset"))){
			offset=Integer.parseInt(req.getParameter("offset"));
		} 
		if(StringUtil.isNotBlank(req.getParameter("StartDate"))){
			StartDate=req.getParameter("StartDate");
		} 
		if(StringUtil.isNotBlank(req.getParameter("EndDate"))){
			EndDate=req.getParameter("EndDate");
		} 
		sql="select a.*,b.username from works_yuehuibao a inner join sys_users b on a.userid=b.userid where 1=1 ";
		sql1="select count(*) from works_yuehuibao a inner join sys_users b on a.userid=b.userid where 1=1 ";
		
		//拼接where
		List wherelist=new ArrayList();
		if(StringUtils.isNotBlank(StartDate)){
			sql=sql+" and a.submittime >= ? ";
			sql1=sql1+" and a.submittime >= ? ";
			wherelist.add(StartDate);
		}
		
		if(StringUtils.isNotBlank(EndDate)){
			sql=sql+" and a.submittime <= ? ";
			sql1=sql1+" and a.submittime <= ? ";
			wherelist.add(EndDate);
		}
		
		//拼接order
		sql=sql+" order by submittime desc ";
		
		//拼接limit
		if(limit>0){
			sql=sql+" limit "+offset+","+limit;
		}
		
//			sql="select * from zfxm_team where teamname like ? order by teamname limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+teamname+"%"});
//			
//			sql="select count(*) from zfxm_team where teamname like ? ";
//			int count =jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+teamname+"%"});
			
		lstRes=jdbcTemplate.queryForList(sql, wherelist.toArray());
		int count =jdbcTemplate.queryForObject(sql1,int.class,wherelist.toArray());
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public String yuehuibao_add(HttpServletRequest req){
		String submittime="";
		String worktext="";
		String sql=null;
		if(StringUtils.isNotBlank(req.getParameter("submittime"))){
			submittime=req.getParameter("submittime");
		}
		if(StringUtils.isNotBlank(req.getParameter("worktext"))){
			worktext=req.getParameter("worktext");
		}
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into works_yuehuibao(submittime,worktext,userid,inserttime) values(?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{submittime,worktext,user.getUserid(),sdf.format(date)});
		
		return "ok";
	}
	
	public String yuehuibao_del(HttpServletRequest req,Integer[]  workyuehuibaoids){
		int workyuehuibaoid=0;
		String sql=null;
		List<Integer> list= Arrays.asList(workyuehuibaoids);
		
		if(list.size()==0){
			return "workzhouhuibaoid不能为空";
		}
		
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		sql="select level from sys_users where userid=? ";
		int level=jdbcTemplate.queryForObject(sql, int.class,new Object[]{user.getUserid()});
		
		for(int i=0;i<list.size();i++){
			workyuehuibaoid=list.get(i);
			if(level ==1){
				sql="delete from works_yuehuibao where workyuehuibaoid=? ";
				jdbcTemplate.update(sql,new Object[]{workyuehuibaoid});
			}else{
				sql="delete from works_yuehuibao where workyuehuibaoid=? and userid=? ";
				jdbcTemplate.update(sql,new Object[]{workyuehuibaoid,user.getUserid()});
			}
		}
		
		return "ok";
	}
	
	public String yuehuibao_update(HttpServletRequest req){
		String worktext="";
		int workyuehuibaoid=0;
		String sql="";
		if(StringUtils.isNotBlank(req.getParameter("workyuehuibaoid"))){
			workyuehuibaoid=Integer.parseInt(req.getParameter("workyuehuibaoid"));
		}
		if(StringUtils.isNotBlank(req.getParameter("worktext"))){
			worktext=req.getParameter("worktext");
		}
		User user=new User();
		user=(User)req.getSession().getAttribute("user");
		
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="select level from sys_users where userid=? ";
		int level=jdbcTemplate.queryForObject(sql, int.class,new Object[]{user.getUserid()});
		
		if(level==1){
			sql="update works_yuehuibao set worktext=?,inserttime=? where workyuehuibaoid=? ";
			jdbcTemplate.update(sql,new Object[]{worktext,sdf.format(date)+"_"+user.getUsername(),workyuehuibaoid });
		}else{
			sql="update works_yuehuibao set worktext=?,inserttime=? where workyuehuibaoid=? and userid=?";
			jdbcTemplate.update(sql,new Object[]{worktext,sdf.format(date),workyuehuibaoid,user.getUserid()});
		}
		
		return "ok";
	}
}
