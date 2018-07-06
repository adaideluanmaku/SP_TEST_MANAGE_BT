package com.ch.pahis;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sysuntils.Strisnull;

import net.sf.json.JSONObject;
@Service
public class Mc_dict_drug_pass {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Strisnull strisnull;
	
	public void dict_drug_pass(int match_scheme,String startdate) throws Exception{
		List listbatch=new ArrayList();
		List list=null;
		String sql=null;
		
		sql="select * from mc_dict_drug_pass where match_scheme=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{match_scheme});
		
//		sql="delete from mc_dict_drug_pass where match_scheme=?";
//		jdbcTemplate_oracle.update(sql,new Object[]{match_scheme});
		
		//1609版
//		sql="insert into mc_dict_drug_pass(searchcode, oprpi_time, match_scheme, pass_drugname, "
//				+ "comp_name, menulabel, drugname, drugform, drugcode, pass_drugcode, pass_upstate, proid, "
//				+ "pass_approvalcode, pass_form_name, match_time, unable_match_desc, unable_match, pass_st_strength,"
//				+ " match_user, drug_unique_code, approvalcode, pass_dividend, pass_divisor, pass_st_comp_name, "
//				+ "drugspec, oprpi_user, pass_doseunit, pass_nametype, doseunit) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
//				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//1712版
		sql="insert into mc_dict_drug_pass(searchcode, oprpi_time, match_scheme, pass_drugname, "
				+ "comp_name, menulabel, drugname, drugform, drugcode, pass_drugcode, pass_upstate, proid, "
				+ "pass_approvalcode, pass_form_name, match_time, unable_match_desc, unable_match, pass_st_strength,"
				+ " match_user, drug_unique_code, approvalcode, pass_dividend, pass_divisor, pass_st_comp_name, "
				+ "drugspec, oprpi_user, pass_doseunit, pass_nametype, doseunit,updatedate) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
		
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			map.put("updatedate", startdate);
			listbatch.add(map);
			
			if((i+1)%500==0){
				batchInsertRows(sql,listbatch);
				listbatch.clear();
			}else{
				if(i+1==list.size()){
					batchInsertRows(sql,listbatch);
					listbatch.clear();
				}
			}
		}
	}
	
	public void batchInsertRows(String sql, final List listbatch) throws Exception {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				Map map=(Map)listbatch.get(i);
				String startdate=map.get("updatedate").toString();
				try{
					pst.setString(1,strisnull.isnull(map.get("searchcode")).toString());//searchcode
					pst.setString(2,strisnull.isnull(map.get("oprpi_time")).toString());//oprpi_time
					pst.setString(3,strisnull.isnull(map.get("match_scheme")).toString());//match_scheme
					pst.setString(4,strisnull.isnull(map.get("pass_drugname")).toString());//pass_drugname
					pst.setString(5,strisnull.isnull(map.get("comp_name")).toString());//comp_name
					pst.setString(6,strisnull.isnull(map.get("menulabel")).toString());//menulabel
					pst.setString(7,strisnull.isnull(map.get("drugname")).toString());//drugname
					pst.setString(8,strisnull.isnull(map.get("drugform")).toString());//drugform
					pst.setString(9,strisnull.isnull(map.get("drugcode")).toString());//drugcode
					pst.setString(10,strisnull.isnull(map.get("pass_drugcode")).toString());//pass_drugcode
					if("".equals(strisnull.isnull(map.get("pass_upstate")))){
						pst.setInt(11,0);//pass_upstate
					}else{
						pst.setInt(11,Integer.parseInt(strisnull.isnull(map.get("pass_upstate")).toString()));//pass_upstate
					}
					pst.setString(12,strisnull.isnull(map.get("proid")).toString());//proid
					pst.setString(13,strisnull.isnull(map.get("pass_approvalcode")).toString());//pass_approvalcode
					pst.setString(14,strisnull.isnull(map.get("pass_form_name")).toString());//pass_form_name
					pst.setString(15,strisnull.isnull(map.get("match_time")).toString());//match_time
					pst.setString(16,strisnull.isnull(map.get("unable_match_desc")).toString());//unable_match_desc
					pst.setString(17,strisnull.isnull(map.get("unable_match")).toString());//unable_match
					pst.setString(18,strisnull.isnull(map.get("pass_st_strength")).toString());//pass_st_strength
					pst.setString(19,strisnull.isnull(map.get("match_user")).toString());//match_user
					pst.setString(20,strisnull.isnull(map.get("drug_unique_code")).toString());//drug_unique_code
					pst.setString(21,strisnull.isnull(map.get("approvalcode")).toString());//approvalcode
					pst.setString(22,strisnull.isnull(map.get("pass_dividend")).toString());//pass_dividend
					pst.setString(23,strisnull.isnull(map.get("pass_divisor")).toString());//pass_divisor
					pst.setString(24,strisnull.isnull(map.get("pass_st_comp_name")).toString());//pass_st_comp_name
					pst.setString(25,strisnull.isnull(map.get("drugspec")).toString());//drugspec
					pst.setString(26,strisnull.isnull(map.get("oprpi_user")).toString());//oprpi_user
					pst.setString(27,strisnull.isnull(map.get("pass_doseunit")).toString());//pass_doseunit
					pst.setString(28,strisnull.isnull(map.get("pass_nametype")).toString());//pass_nametype
					pst.setString(29,strisnull.isnull(map.get("doseunit")).toString());//doseunit
					pst.setString(30,strisnull.isnull(startdate));//updatedate
				}catch(Exception e){
					System.out.println("mc_dict_drug_pass出现异常的数据:"+map);
					System.out.println(e);
				}
			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return listbatch.size();
			}
		};
		jdbcTemplate_oracle.batchUpdate(sql, setter);
	}
}
