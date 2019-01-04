package com.ch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/*
 * 非Spring配置文件，动态Spring数据库连接方式
 * 从.net PASS数据库
 */

@Service
public class SpringJdbc_sqlserver_pass {
//	@Autowired
	static JdbcTemplate jdbcTemplate=null;

	@Value("${sqlserverdriver_pass}")
    private String driver;
	@Value("${sqlserverurl_pass}")
    private String url;
	@Value("${sqlservername_pass}")
    private String username;
	@Value("${sqlserverpassword_pass}")
    private String password;
	
	public JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate==null){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			// 创建JDBC模板
			jdbcTemplate=new JdbcTemplate();
			// 这里也可以使用构造方法
			jdbcTemplate.setDataSource(dataSource);
			
			String sql="SELECT 9527";
			try{
				jdbcTemplate.queryForObject(sql,int.class);
			}catch(Exception e){
				jdbcTemplate=null;
				System.out.println("数据库连接驱动报错");
			}
		}
		
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
