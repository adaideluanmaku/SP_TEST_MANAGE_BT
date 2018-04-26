package com.ch.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.DataGrid;


@Service
public class Dictbean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	DataGrid dataGrid;
	
	public DataGrid dict_drug(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.drug_unique_code,a.drugname,a.drugform,a.drugspec,a.comp_name,a.doseunit "
				+ "from mc_dict_drug_pass a,mc_hospital_match_relation b where "
				+ "a.match_scheme=b.drugmatch_scheme and a.drug_unique_code like ? order by a.drug_unique_code asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.drug_unique_code) from mc_dict_drug_pass a,mc_hospital_match_relation b where "
				+ "a.match_scheme=b.drugmatch_scheme and a.drug_unique_code like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_route(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.routecode,a.routename from mc_dict_route a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.routematch_scheme and a.routecode like ? order by a.routecode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.routecode) from mc_dict_route a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.routematch_scheme and a.routecode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_fre(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.frequency from mc_dict_frequency a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.freqmatch_scheme and a.frequency like ? order by a.frequency asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.frequency) from mc_dict_frequency a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.freqmatch_scheme and a.frequency like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_dept(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.deptcode,a.deptname from mc_dict_dept a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.deptmatch_scheme and a.deptcode<>'-1' and a.deptcode like ? order by a.deptcode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.deptcode) from mc_dict_dept a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.deptmatch_scheme and a.deptcode<>'-1' and a.deptcode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_doctor(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.doctorcode,a.doctorname from mc_dict_doctor a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.doctormatch_scheme and a.doctorcode <> '-1' "
				+ "and a.doctorcode like ? order by a.doctorcode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.doctorcode) from mc_dict_doctor a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.doctormatch_scheme and a.doctorcode <> '-1' and a.doctorcode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_allergen(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.allercode,a.allername from mc_dict_allergen a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.allermatch_scheme and a.allercode like ? order by a.allercode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.allercode) from mc_dict_allergen a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.allermatch_scheme  and a.allercode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_disease(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.discode,a.disname from mc_dict_disease a,mc_hospital_match_relation b " + 
				"where a.match_scheme=b.dismatch_scheme and a.discode like ? order by a.discode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.discode) from mc_dict_disease a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.dismatch_scheme  and a.discode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_operation(HttpServletRequest req){
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String sql=null;
		List lstRes=null;
		
		if(StringUtils.isBlank(search)){
			search="";
		}
		
		sql="select b.hisname,a.operationcode,a.operationname from mc_dict_operation a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme and a.operationcode like ? order by a.operationcode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.operationcode) from mc_dict_operation a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme  and a.operationcode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
}

