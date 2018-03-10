package com.ch.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.DataGrid;

@Service
public class Prescription {
	@Autowired
	DataGrid dataGrid;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public DataGrid prescription_query(HttpServletRequest req){
		List lstRes = new ArrayList();
		System.out.println("home 用药研究 处方管理");
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String patientname=req.getParameter("patientname"); 
		
		String sql="select * from prescription where patientname like ? limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql,new Object[]{"%"+patientname+"%"});
		
//		if(lstRes.size()==0 && offset/limit>0){
//			offset=offset-limit;
//			sql="select * from prescription where patientname like ? limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql,new Object[]{"%"+patientname+"%"});
//		}
		
		sql="select count(1) from prescription where patientname like ? ";
		int count =jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+patientname+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public int prescription_add(HttpServletRequest req){
		String patientname=req.getParameter("patientname"); 
		String prescription_json=req.getParameter("prescription_json"); 
		
		int rsl=0;
		if(!"".equals(patientname)){
			String sql="insert into prescription(patientname,prescription_json) values(?, ?) ";
			rsl=jdbcTemplate.update(sql,new Object[]{patientname,prescription_json});
		}
		if(rsl>0){
			rsl=1;
		}
		return rsl;
	}
	
	public int prescription_del(HttpServletRequest req){
		String[] pre_id=req.getParameter("pre_id").toString().split(","); 
		
		int rsl=0;
		if(!"".equals(pre_id)){
			String sql="delete from prescription where ";
			List <Object> moreList=new  ArrayList<Object>();  
			
			for(int i=0;i<pre_id.length;i++){
				if(i==0){
					sql=sql+" pre_id = ? ";
					moreList.add(pre_id[i]);
				}else{
					sql=sql+" or pre_id = ? ";
					moreList.add(pre_id[i]);
				}
				
			}
			rsl=jdbcTemplate.update(sql,moreList.toArray());
		}
		
		if(rsl>0){
			rsl=1;
		}
		return rsl;
	}
	
	public int prescription_update(HttpServletRequest req){
		String pre_id=req.getParameter("pre_id");
		String patientname=req.getParameter("patientname"); 
		String prescription_json=req.getParameter("prescription_json"); 
		
		int rsl=0;
		if(!"".equals(pre_id)){
			String sql="update prescription set patientname=?,prescription_json=? where pre_id=? ";
			rsl=jdbcTemplate.update(sql,new Object[]{patientname,prescription_json,pre_id});
		}
		
		if(rsl>0){
			rsl=1;
		}
		return rsl;
	}
}
