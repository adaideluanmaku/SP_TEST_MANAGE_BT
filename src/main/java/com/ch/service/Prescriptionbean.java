package com.ch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ch.sysuntils.DataGrid;

@Service
public class Prescriptionbean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public DataGrid prescription_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		
		List lstRes = new ArrayList();
//		System.out.println("home 用药研究 处方管理");
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String patientname=req.getParameter("patientname"); 
		if(StringUtils.isBlank(patientname)){
			patientname="";
		}
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
	
	public String prescription_add(HttpServletRequest req){
		String patientname=req.getParameter("patientname"); 
		String prescription_json=req.getParameter("prescription_json"); 
		String sql=null;
		
		if(StringUtils.isBlank(req.getParameter("patientname"))){
			return "病人名称不能为空";
		}else{
			patientname=req.getParameter("patientname");
		}
		
		int rsl=0;
		sql="select count(1) from prescription where patientname=? ";
		rsl=jdbcTemplate.queryForObject(sql,int.class,new Object[]{patientname});
		
		if(rsl>0){
			return "病人名称不能重复";
		}
		
		sql="insert into prescription(patientname,prescription_json) values(?, ?) ";
		jdbcTemplate.update(sql,new Object[]{patientname,prescription_json});

		return "ok";
	}
	
	public String prescription_del(HttpServletRequest req,Integer[]  pre_id){
		System.out.println(pre_id);
		List<Integer> list= Arrays.asList(pre_id);
		
		if(list.size()>0){
			String sql="delete from prescription where ";
			
			for(int i=0;i<list.size();i++){
				if(i==0){
					sql=sql+" pre_id = ? ";
				}else{
					sql=sql+" or pre_id = ? ";
				}
				
			}
			jdbcTemplate.update(sql,list.toArray());
		}
		
		return "ok";
	}
	
	public String prescription_update(HttpServletRequest req){
		int prescriptiontype=0;
		if(StringUtils.isNoneBlank(req.getParameter("prescriptiontype"))){
			prescriptiontype=Integer.parseInt(req.getParameter("prescriptiontype").toString());
		}
		
		if(prescriptiontype==1){
			String pre_id="";
//			String patientname=""; 
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "pre_id不能为空";
			}else{
				pre_id=req.getParameter("pre_id");
			}
//			if(StringUtils.isNotBlank(req.getParameter("patientname"))){
//				patientname=req.getParameter("patientname"); 
//			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update prescription set prescription_json=? where pre_id=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,pre_id});
		}
		
		if(prescriptiontype==2){
			int testid=0;
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "testid不能为空";
			}else{
				testid=Integer.parseInt(req.getParameter("pre_id").toString());
			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update pa_testmng set testin=? where testid=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,testid});
		}
		
		if(prescriptiontype==3){
			int testid=0;
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "testid不能为空";
			}else{
				testid=Integer.parseInt(req.getParameter("pre_id").toString());
			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update pass_testmng set testin=? where testid=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,testid});
		}
		
		return "ok";
	}
}
