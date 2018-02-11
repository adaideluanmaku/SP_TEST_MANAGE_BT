package com.ch.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.easyui.DataGrid;

@Service
public class Worksbean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	SocketBean socketBean;
	
	public DataGrid workslist(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		String workname=null;
		String username=null;
		String starttime=null;
		
		if(StringUtils.isBlank(req.getParameter("workname"))){
			workname="";
		}else{
			workname=req.getParameter("workname");
		}
		if(StringUtils.isBlank(req.getParameter("starttime"))){
			starttime="";
		}else{
			starttime=req.getParameter("starttime");
		}
		if(StringUtils.isBlank(req.getParameter("username"))){
			username="";
		}else{
			username=req.getParameter("username");
		}
		DataGrid dataGrid = new DataGrid();//返回easyui-web对象
		String limit=null;
		int page=0;
		int total=0;
		
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		sql="select count(a.workid) from works a "
				+ "inner join sys_users b on a.userid=b.userid where a.workname like ? and a.starttime like ? "
				+ "and b.username like ? ";
		total=jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+workname+"%","%"+starttime+"%","%"+username+"%"});
		dataGrid.setTotal(total+0L);
		
//		Date time=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		sql="select a.workid,a.workname,a.worktext,a.inserttime,a.starttime,a.endtime,b.username,b.userid from works a "
				+ "left join sys_users b on a.userid=b.userid where a.workname like ? and a.starttime like ? "
				+ "and b.username like ? order by left(a.starttime,7) asc, b.username desc , a.starttime asc  "+limit;
		rslist=jdbcTemplate.queryForList(sql,new Object[]{"%"+workname+"%","%"+starttime+"%","%"+username+"%"});
		dataGrid.setRows(rslist);
		
		return dataGrid;
	}
	
	public void worksadd(HttpServletRequest req){
		String sql=null;
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into works(workname,worktext,userid,inserttime,starttime,endtime) values(?,?,?,?,?,?) ";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("workname"),req.getParameter("worktext")
				,req.getParameter("userid"),sdf.format(time),req.getParameter("starttime")
				,req.getParameter("endtime")});
		
		//广播通知
		socketBean.workreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
//		socketBean.workreload();
	}
	
	public List users(HttpServletRequest req){
		String sql=null;
		List list=null;
		
		sql="select * from sys_users";
		list=jdbcTemplate.queryForList(sql);
		
		return list;
	}
	
	public void worksdel(HttpServletRequest req){
		String sql=null;
		
		sql="delete from works where workid=? ";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("workid")});
		
	}
	
	public void worksupdate(HttpServletRequest req){
		String sql=null;
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="update works set workname=?,worktext=?,userid=?,inserttime=?,starttime=?,endtime=? where workid=? ";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("workname"),req.getParameter("worktext"),
				req.getParameter("userid"),sdf.format(time),req.getParameter("starttime"),
				req.getParameter("endtime"),req.getParameter("workid")});
		//广播通知
		socketBean.workreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
//		socketBean.workreload();
	}
}
