package com.ch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/*
 * 非Spring配置文件，动态Spring数据库连接方式
 */
@Service
public class DataBaseType {
	@Autowired
	JdbcTemplate jdbcTemplate;
	JdbcTemplate databasejdbcTemplate=null;
	
	//数据库变更后，需要重新加载数据库
	public static int databaseidbak=0;
	
	public JdbcTemplate getJdbcTemplate(int databaseid) {
		if(databaseidbak != databaseid){
			databaseidbak=databaseid;
			databasejdbcTemplate=null;
		}
		if(databasejdbcTemplate==null){
			Map databasemap= new HashMap();
			String sql=null;
			
			String driver=null;
			String url=null;
			String username=null;
			String password=null;
			
			sql="select * from sys_database where databaseid=? ";
			List list=jdbcTemplate.queryForList(sql,new Object[]{databaseid});
			if(list.size()>0){
				databasemap=(Map)list.get(0);
				if("MYSQL".equals(databasemap.get("databasetype"))){
					driver="com.mysql.jdbc.Driver";
					url="jdbc:mysql://"+databasemap.get("ip")+":3306/"+databasemap.get("databasename")+"?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
					username=databasemap.get("username").toString();
					password=databasemap.get("password").toString();
					sql="SELECT 9527";
				}else if("MSSQL".equals(databasemap.get("databasetype"))){
					driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
					url="jdbc:sqlserver://"+databasemap.get("ip")+";databaseName="+databasemap.get("databasename");
					username=databasemap.get("username").toString();
					password=databasemap.get("password").toString();
					sql="SELECT 9527";
				}else if("ORACLE".equals(databasemap.get("databasetype"))){
					driver="oracle.jdbc.driver.OracleDriver";
					url="jdbc:oracle:thin:@"+databasemap.get("ip")+":1521:"+databasemap.get("databasename");
					username=databasemap.get("username").toString();
					password=databasemap.get("password").toString();
					sql="SELECT 9527 from dual";
				}
			}
			
			databasejdbcTemplate=Database(driver, url, username, password);
			try{
				databasejdbcTemplate.queryForObject(sql,int.class);
			}catch(Exception e){
				databasejdbcTemplate=null;
				System.out.println("数据库连接驱动报错");
			}
		}
		
		
		return databasejdbcTemplate;
	}
	
	public JdbcTemplate Database(String driver,String url,String username,String password){
		JdbcTemplate databasejdbcTemplate=null;
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		// 创建JDBC模板
		databasejdbcTemplate=new JdbcTemplate();
		// 这里也可以使用构造方法
		databasejdbcTemplate.setDataSource(dataSource);
		
		
		return databasejdbcTemplate;
	}
}
