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
		
		sql="select b.hisname,b.hiscode,a.drugcode,a.drug_unique_code,a.drugcode,a.drugname,a.drugform,a.drugspec,a.comp_name,a.doseunit "
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
		
		sql="select b.hisname,b.hiscode,a.routecode,a.routename from mc_dict_route a,mc_hospital_match_relation b "
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
		
		sql="select b.hisname,b.hiscode,a.frequency from mc_dict_frequency a,mc_hospital_match_relation b "
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
		
		sql="select b.hisname,b.hiscode,a.deptcode,a.deptname from mc_dict_dept a,mc_hospital_match_relation b "
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
		
		sql="select b.hisname,b.hiscode,a.doctorcode,a.doctorname from mc_dict_doctor a,mc_hospital_match_relation b "
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
		
		sql="select b.hisname,b.hiscode,a.allercode,a.allername from mc_dict_allergen a,mc_hospital_match_relation b "
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
		
		sql="select b.hisname,b.hiscode,a.discode,a.disname from mc_dict_disease a,mc_hospital_match_relation b " + 
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
		
		sql="select b.hisname,b.hiscode,a.operationcode,a.operationname from mc_dict_operation a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme and a.operationcode like ? order by a.operationcode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.operationcode) from mc_dict_operation a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme  and a.operationcode like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_exam(HttpServletRequest req){
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
		
		sql="select b.hisname,b.hiscode,a.examcode,a.examname from mc_dict_exam a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme and a.examname like ? order by a.examcode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.examcode) from mc_dict_exam a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme  and a.examname like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public DataGrid dict_lab(HttpServletRequest req){
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
		
		sql="select b.hisname,b.hiscode,a.labcode,a.labname from mc_dict_lab a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme and a.labname like ? order by a.labcode asc limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql, new Object[]{"%"+search+"%"});
		
		sql="select count(a.labcode) from mc_dict_lab a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme  and a.labname like ? ";
		int count=jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{"%"+search+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public String insertdrug(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String drugcode="";
		String drug_unique_code="";
		String drugname="";
		String drugform="";
		String drugspec="";
		String comp_name="";
		String doseunit="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugcode"))){
			drugcode=req.getParameter("drugcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("drug_unique_code"))){
			drug_unique_code=req.getParameter("drug_unique_code");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugname"))){
			drugname=req.getParameter("drugname");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugform"))){
			drugform=req.getParameter("drugform");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugspec"))){
			drugspec=req.getParameter("drugspec");
		}
		if(StringUtils.isNotBlank(req.getParameter("comp_name"))){
			comp_name=req.getParameter("comp_name");
		}
		if(StringUtils.isNotBlank(req.getParameter("doseunit"))){
			doseunit=req.getParameter("doseunit");
		}
		
		int match_scheme=0;
		sql="select drugmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_drug_pass where (drugcode=? or drug_unique_code=?) and doseunit=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{drugcode,drug_unique_code,doseunit,match_scheme});

		if(rscount>0){
			return "药品编码(唯一码)重复,或者相同编码下给药途径重复";
		}
		
		sql="insert into mc_dict_drug_pass(drug_unique_code,drugcode,drugname,drugform,drugspec,"
				+ "comp_name,doseunit,match_scheme) value(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{drug_unique_code,drugcode,drugname,drugform,
				drugspec,comp_name,doseunit,match_scheme});
		
		sql="select count(1) from mc_dict_drug where match_scheme=? and drugcode=? ";
		rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{match_scheme,drugcode});
		if(rscount>0){
			sql="update mc_dict_drug set  drugname=?,drugform=?,state=1 where drugcode=? and match_scheme=?";
			jdbcTemplate.update(sql,new Object[]{drugname,drugform,drugcode,match_scheme});
		}else{
			sql="insert into mc_dict_drug(drugcode,drugname,drugform,match_scheme,state) "
					+ "value(?,?,?,?,?)";
			jdbcTemplate.update(sql,new Object[]{drugcode,drugname,drugform,match_scheme,1});
		}
		
		sql="select count(1) from mc_dict_drug_sub where match_scheme=? and drugcode=? and drugspec=? ";
		rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{match_scheme,drugcode,drugspec});
		if(rscount>0){
			sql="update mc_dict_drug_sub set drugname=?,drugform=?,state=1 where drugcode=? "
					+ " and match_scheme=? and drugspec=?";
			jdbcTemplate.update(sql,new Object[]{drugname,drugform,drugcode,match_scheme,
					drugspec});
		}else{
			sql="insert into mc_dict_drug_sub(drugcode,drugname,drugform,drugspec,"
					+ "match_scheme,state) value(?,?,?,?,?,?)";
			jdbcTemplate.update(sql,new Object[]{drugcode,drugname,drugform,
					drugspec,match_scheme,1});
		}
		
		
		return "ok";
	}
	
	public String updatedrug(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String drugcode="";
		String drug_unique_code="";
		String drugname="";
		String drugform="";
		String drugspec="";
		String comp_name="";
		String doseunit="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugcode"))){
			drugcode=req.getParameter("drugcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("drug_unique_code"))){
			drug_unique_code=req.getParameter("drug_unique_code");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugname"))){
			drugname=req.getParameter("drugname");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugform"))){
			drugform=req.getParameter("drugform");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugspec"))){
			drugspec=req.getParameter("drugspec");
		}
		if(StringUtils.isNotBlank(req.getParameter("comp_name"))){
			comp_name=req.getParameter("comp_name");
		}
		if(StringUtils.isNotBlank(req.getParameter("doseunit"))){
			doseunit=req.getParameter("doseunit");
		}
		
		int match_scheme=0;
		sql="select drugmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_drug_pass set drug_unique_code=?,drugname=?,drugform=?,drugspec=?, "
				+ " comp_name=? where drugcode=? and match_scheme=? and doseunit=?";
		jdbcTemplate.update(sql,new Object[]{drug_unique_code,drugname,drugform,
				drugspec,comp_name,drugcode,match_scheme,doseunit});
		
		sql="update mc_dict_drug set  drugname=?,drugform=? where drugcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{drugname,drugform,drugcode,match_scheme});
		
		sql="update mc_dict_drug_sub set drugname=?,drugform=? where drugcode=? "
				+ " and match_scheme=? and drugspec=?";
		jdbcTemplate.update(sql,new Object[]{drugname,drugform,
				drugcode,match_scheme,drugspec});
		
		return "ok";
	}
	
	public String deletedrug(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String drugcode="";
		String drug_unique_code="";
		String drugname="";
		String drugform="";
		String drugspec="";
		String comp_name="";
		String doseunit="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugcode"))){
			drugcode=req.getParameter("drugcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("drug_unique_code"))){
			drug_unique_code=req.getParameter("drug_unique_code");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugname"))){
			drugname=req.getParameter("drugname");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugform"))){
			drugform=req.getParameter("drugform");
		}
		if(StringUtils.isNotBlank(req.getParameter("drugspec"))){
			drugspec=req.getParameter("drugspec");
		}
		if(StringUtils.isNotBlank(req.getParameter("comp_name"))){
			comp_name=req.getParameter("comp_name");
		}
		if(StringUtils.isNotBlank(req.getParameter("doseunit"))){
			doseunit=req.getParameter("doseunit");
		}
		
		int match_scheme=0;
		sql="select drugmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_drug_pass where drug_unique_code=? and drugcode=? and match_scheme=? and doseunit=?";
		jdbcTemplate.update(sql,new Object[]{drug_unique_code,drugcode,match_scheme,doseunit});
		
		sql="update mc_dict_drug set state=0 where drugcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{drugcode,match_scheme});
		
		sql="update mc_dict_drug_sub set state=0 where drugcode=? "
				+ " and match_scheme=? and drugspec=?";
		jdbcTemplate.update(sql,new Object[]{drugcode,match_scheme,drugspec});
		
		return "ok";
	}
	
	public String insertroute(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String routecode="";
		String routename="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("routecode"))){
			routecode=req.getParameter("routecode");
		}
		if(StringUtils.isNotBlank(req.getParameter("routename"))){
			routename=req.getParameter("routename");
		}
		
		int match_scheme=0;
		sql="select routematch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_route where routecode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{routecode,match_scheme});

		if(rscount>0){
			return "给药途径编码重复";
		}
		
		sql="insert into mc_dict_route(routecode,routename,match_scheme) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{routecode,routename,match_scheme});
		
		return "ok";
	}
	
	public String updateroute(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String routecode="";
		String routename="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("routecode"))){
			routecode=req.getParameter("routecode");
		}
		if(StringUtils.isNotBlank(req.getParameter("routename"))){
			routename=req.getParameter("routename");
		}
		
		int match_scheme=0;
		sql="select routematch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_route set routename=? where routecode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{routename,routecode,match_scheme});
		
		return "ok";
	}
	
	public String deleteroute(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String routecode="";
		String routename="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("routecode"))){
			routecode=req.getParameter("routecode");
		}
		if(StringUtils.isNotBlank(req.getParameter("routename"))){
			routename=req.getParameter("routename");
		}
		
		int match_scheme=0;
		sql="select routematch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_route where routecode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{routecode,match_scheme});
		
		return "ok";
	}
	
	public String insertfre(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String frequency="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("frequency"))){
			frequency=req.getParameter("frequency");
		}
		
		int match_scheme=0;
		sql="select freqmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_frequency where frequency=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{frequency,match_scheme});

		if(rscount>0){
			return "频次重复";
		}
		
		
		int frequency_int=-1;
		try{
			frequency_int=Integer.parseInt(frequency);
		}catch(Exception e){
			
		}
		
		String[] frequencys=null;
		int times=0;
		int days=0;
		if(frequency_int==-1){
			frequencys=frequency.split("/");
			if(frequencys.length==2){
				try{
					times=Integer.parseInt(frequencys[0].toString());
				}catch(Exception e){
					times=-1;
				}
				try{
					days=Integer.parseInt(frequencys[1].toString());
				}catch(Exception e){
					days=-1;
				}
			}else{
				times=-1;
				days=-1;
			}
		}else{
			times=frequency_int;
			days=frequency_int;
		}
		
		sql="insert into mc_dict_frequency(frequency,match_scheme,times,days) value(?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{frequency,match_scheme,times,days});
		
		return "ok";
	}
	
//	public String updatefre(HttpServletRequest req){
//		String sql=null;
//		String hiscode="";
//		String frequency="";
//		
//		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
//			hiscode=req.getParameter("hiscode");
//		}
//		if(StringUtils.isNotBlank(req.getParameter("frequency"))){
//			frequency=req.getParameter("frequency");
//		}
//		
//		int match_scheme=0;
//		sql="select freqmatch_scheme from mc_hospital_match_relation where hiscode=?";
//		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
//		
//		sql="update mc_dict_frequency set frequency=? where routecode=? and match_scheme=?";
//		jdbcTemplate.update(sql,new Object[]{routename,routecode,match_scheme});
//		
//		return "ok";
//	}
	
	public String deletefre(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String frequency="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("frequency"))){
			frequency=req.getParameter("frequency");
		}
		
		int match_scheme=0;
		sql="select freqmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_frequency where frequency=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{frequency,match_scheme});
		
		return "ok";
	}
	
	public String insertdept(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String deptcode="";
		String deptname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("deptcode"))){
			deptcode=req.getParameter("deptcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("deptname"))){
			deptname=req.getParameter("deptname");
		}
		
		int match_scheme=0;
		sql="select deptmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_dept where deptcode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{deptcode,match_scheme});

		if(rscount>0){
			return "科室重复";
		}
		
		sql="insert into mc_dict_dept(deptcode,match_scheme,deptname) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{deptcode,match_scheme,deptname});
		
		return "ok";
	}
	
	public String updatedept(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String deptcode="";
		String deptname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("deptcode"))){
			deptcode=req.getParameter("deptcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("deptname"))){
			deptname=req.getParameter("deptname");
		}
		
		int match_scheme=0;
		sql="select deptmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_dept set deptname=? where deptcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{deptname,deptcode,match_scheme});
		
		return "ok";
	}
	
	public String deletedept(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String deptcode="";
		String deptname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("deptcode"))){
			deptcode=req.getParameter("deptcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("deptname"))){
			deptname=req.getParameter("deptname");
		}
		
		int match_scheme=0;
		sql="select deptmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_dept where deptcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{deptcode,match_scheme});
		
		return "ok";
	}
	
	public String insertdoctor(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String doctorcode="";
		String doctorname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("doctorcode"))){
			doctorcode=req.getParameter("doctorcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("doctorname"))){
			doctorname=req.getParameter("doctorname");
		}
		
		int match_scheme=0;
		sql="select doctormatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_doctor where doctorcode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{doctorcode,match_scheme});

		if(rscount>0){
			return "医生重复";
		}
		
		sql="insert into mc_dict_doctor(doctorcode,match_scheme,doctorname) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{doctorcode,match_scheme,doctorname});
		
		return "ok";
	}
	
	public String updatedoctor(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String doctorcode="";
		String doctorname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("doctorcode"))){
			doctorcode=req.getParameter("doctorcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("doctorname"))){
			doctorname=req.getParameter("doctorname");
		}
		
		int match_scheme=0;
		sql="select doctormatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_doctor set doctorname=? where doctorcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{doctorname,doctorcode,match_scheme});
		
		return "ok";
	}
	
	public String deletedoctor(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String doctorcode="";
		String doctorname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("doctorcode"))){
			doctorcode=req.getParameter("doctorcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("doctorname"))){
			doctorname=req.getParameter("doctorname");
		}
		
		int match_scheme=0;
		sql="select doctormatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_doctor where doctorcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{doctorcode,match_scheme});
		
		return "ok";
	}
	
	public String insertallergen(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String allercode="";
		String allername="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("allercode"))){
			allercode=req.getParameter("allercode");
		}
		if(StringUtils.isNotBlank(req.getParameter("allername"))){
			allername=req.getParameter("allername");
		}
		
		int match_scheme=0;
		sql="select allermatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_allergen where allercode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{allercode,match_scheme});

		if(rscount>0){
			return "过敏原重复";
		}
		
		sql="insert into mc_dict_allergen(allercode,match_scheme,allername) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{allercode,match_scheme,allername});
		
		return "ok";
	}
	
	public String updateallergen(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String allercode="";
		String allername="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("allercode"))){
			allercode=req.getParameter("allercode");
		}
		if(StringUtils.isNotBlank(req.getParameter("allername"))){
			allername=req.getParameter("allername");
		}
		
		int match_scheme=0;
		sql="select allermatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_allergen set allername=? where allercode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{allername,allercode,match_scheme});
		
		return "ok";
	}
	
	public String deleteallergen(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String allercode="";
		String allername="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("allercode"))){
			allercode=req.getParameter("allercode");
		}
		if(StringUtils.isNotBlank(req.getParameter("allername"))){
			allername=req.getParameter("allername");
		}
		
		int match_scheme=0;
		sql="select allermatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_allergen where allercode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{allercode,match_scheme});
		
		return "ok";
	}
	
	public String insertdisease(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String discode="";
		String disname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("discode"))){
			discode=req.getParameter("discode");
		}
		if(StringUtils.isNotBlank(req.getParameter("disname"))){
			disname=req.getParameter("disname");
		}
		
		int match_scheme=0;
		sql="select dismatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_disease where discode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{discode,match_scheme});

		if(rscount>0){
			return "疾病重复";
		}
		
		sql="insert into mc_dict_disease(discode,match_scheme,disname) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{discode,match_scheme,disname});
		
		return "ok";
	}
	
	public String updatedisease(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String discode="";
		String disname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("discode"))){
			discode=req.getParameter("discode");
		}
		if(StringUtils.isNotBlank(req.getParameter("disname"))){
			disname=req.getParameter("disname");
		}
		
		int match_scheme=0;
		sql="select dismatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_disease set disname=? where discode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{disname,discode,match_scheme});
		
		return "ok";
	}
	
	public String deletedisease(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String discode="";
		String disname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("discode"))){
			discode=req.getParameter("discode");
		}
		if(StringUtils.isNotBlank(req.getParameter("disname"))){
			disname=req.getParameter("disname");
		}
		
		int match_scheme=0;
		sql="select dismatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_disease where discode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{discode,match_scheme});
		
		return "ok";
	}
	
	public String insertoperation(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String operationcode="";
		String operationname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("operationcode"))){
			operationcode=req.getParameter("operationcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("operationname"))){
			operationname=req.getParameter("operationname");
		}
		
		int match_scheme=0;
		sql="select oprmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_operation where operationcode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{operationcode,match_scheme});

		if(rscount>0){
			return "手术编号重复";
		}
		
		sql="insert into mc_dict_operation(operationcode,match_scheme,operationname) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{operationcode,match_scheme,operationname});
		
		return "ok";
	}
	
	public String updateoperation(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String operationcode="";
		String operationname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("operationcode"))){
			operationcode=req.getParameter("operationcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("operationname"))){
			operationname=req.getParameter("operationname");
		}
		
		int match_scheme=0;
		sql="select oprmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_operation set operationname=? where operationcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{operationname,operationcode,match_scheme});
		
		return "ok";
	}
	
	public String deleteoperation(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String operationcode="";
		String operationname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("operationcode"))){
			operationcode=req.getParameter("operationcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("operationname"))){
			operationname=req.getParameter("operationname");
		}
		
		int match_scheme=0;
		sql="select oprmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_operation where operationcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{operationcode,match_scheme});
		
		return "ok";
	}
	
	public String insertexam(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String examcode="";
		String examname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("examcode"))){
			examcode=req.getParameter("examcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("examname"))){
			examname=req.getParameter("examname");
		}
		
		int match_scheme=0;
		sql="select exammatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_exam where examcode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{examcode,match_scheme});

		if(rscount>0){
			return "检查编号重复";
		}
		
		sql="insert into mc_dict_exam(examcode,match_scheme,examname) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{examcode,match_scheme,examname});
		
		return "ok";
	}
	
	public String updateexam(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String examcode="";
		String examname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("examcode"))){
			examcode=req.getParameter("examcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("examname"))){
			examname=req.getParameter("examname");
		}
		
		int match_scheme=0;
		sql="select exammatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_exam set examname=? where examcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{examname,examcode,match_scheme});
		
		return "ok";
	}
	
	public String deleteexam(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String examcode="";
		String examname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("examcode"))){
			examcode=req.getParameter("examcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("examname"))){
			examname=req.getParameter("examname");
		}
		
		int match_scheme=0;
		sql="select exammatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_exam where examcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{examcode,match_scheme});
		
		return "ok";
	}
	
	public String insertlab(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String labcode="";
		String labname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("labcode"))){
			labcode=req.getParameter("labcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("labname"))){
			labname=req.getParameter("labname");
		}
		
		int match_scheme=0;
		sql="select labmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="select count(1) from mc_dict_lab where labcode=? and match_scheme=?";
		int rscount=jdbcTemplate.queryForObject(sql, int.class,new Object[]{labcode,match_scheme});

		if(rscount>0){
			return "检查编号重复";
		}
		
		sql="insert into mc_dict_lab(labcode,match_scheme,labname) value(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{labcode,match_scheme,labname});
		
		return "ok";
	}
	
	public String updatelab(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String labcode="";
		String labname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("labcode"))){
			labcode=req.getParameter("labcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("labname"))){
			labname=req.getParameter("labname");
		}
		
		int match_scheme=0;
		sql="select labmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="update mc_dict_lab set labname=? where labcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{labname,labcode,match_scheme});
		
		return "ok";
	}
	
	public String deletelab(HttpServletRequest req){
		String sql=null;
		String hiscode="";
		String labcode="";
		String labname="";
		
		if(StringUtils.isNotBlank(req.getParameter("hiscode"))){
			hiscode=req.getParameter("hiscode");
		}
		if(StringUtils.isNotBlank(req.getParameter("labcode"))){
			labcode=req.getParameter("labcode");
		}
		if(StringUtils.isNotBlank(req.getParameter("labname"))){
			labname=req.getParameter("labname");
		}
		
		int match_scheme=0;
		sql="select labmatch_scheme from mc_hospital_match_relation where hiscode=?";
		match_scheme=jdbcTemplate.queryForObject(sql, int.class,new Object[]{hiscode});
		
		sql="delete from mc_dict_lab where labcode=? and match_scheme=?";
		jdbcTemplate.update(sql,new Object[]{labcode,match_scheme});
		
		return "ok";
	}
}

