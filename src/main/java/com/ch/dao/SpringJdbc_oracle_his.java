package com.ch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/*
 * 非Spring配置文件，动态Spring数据库连接方式
 * Oralce连接 HIS库
 */
@Service
public class SpringJdbc_oracle_his {
	static JdbcTemplate jdbcTemplate= null;
	
	@Value("${jdbc.orcldriver_his}")
    private String driver;
	@Value("${jdbc.orclurl_his}")
    private String url;
	@Value("${jdbc.orclusername_his}")
    private String username;
	@Value("${jdbc.orclpassword_his}")
    private String password;
	
	public JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate==null){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			// 创建JDBC模板
			jdbcTemplate =new JdbcTemplate();
			// 这里也可以使用构造方法
			jdbcTemplate.setDataSource(dataSource);
			
			String sql="SELECT 9527 from dual";
			try{
				jdbcTemplate.queryForObject(sql,int.class);
			}catch(Exception e){
				jdbcTemplate=null;
				System.out.println("数据库连接驱动报错");
			}
		}
		
		
		return jdbcTemplate;
	}

	public void setJdbcTemplate_oracle(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
