package com.ch.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.DataGrid;


@Service
public class Dict {
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
}
