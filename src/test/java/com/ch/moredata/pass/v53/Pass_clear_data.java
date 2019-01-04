package com.ch.moredata.pass.v53;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.dao.SpringJdbc_mysql_pass;
import com.ch.pahis.Sys_pa;

@Service
public class Pass_clear_data {
	private static Logger log = Logger.getLogger(Pass_clear_data.class);
	
	JdbcTemplate jdbcTemplate_mysql;
	@Autowired
	SpringJdbc_mysql_pass springJdbc_mysql;
	@Autowired
	Sys_pa sys_pa;
	
	public void clear(){
		jdbcTemplate_mysql=springJdbc_mysql.getJdbcTemplate();
		if(jdbcTemplate_mysql==null){
			log.info("数据库连接失败");
			return;
		}
		
		String sql="";
		sql="truncate Sacl_pat_allergen";
		jdbcTemplate_mysql.update(sql);
		
		sql="truncate Sacl_pat_disease ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Sacl_pat_drugorder ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Sacl_pat_exam ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Sacl_pat_info ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Sacl_pat_lab ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Sacl_pat_operation ";
		jdbcTemplate_mysql.update(sql );
		
		sql="truncate Sacl_screenresult ";
		jdbcTemplate_mysql.update(sql );
		
		System.out.println("数据清理结束");
	}
}
