package com.ch.sys;

import org.springframework.stereotype.Service;

@Service
public class Kettle_config {
	//数据库配置名称
	private String database_one;
	//数据库类型
	private String database_one_type;
	//数据库IP
	private String database_one_ip; 
	//数据库名称
	private String database_one_name; 
	//数据库用户名
	private String database_one_username; 
	//数据库密码
	private String database_one_password;
	
	String database_two;
	String database_two_type;
	String database_two_ip; 
	String database_two_name; 
	String database_two_username; 
	String database_two_password;
	public String getDatabase_one() {
		return database_one;
	}
	public void setDatabase_one(String database_one) {
		this.database_one = database_one;
	}
	public String getDatabase_one_ip() {
		return database_one_ip;
	}
	public void setDatabase_one_ip(String database_one_ip) {
		this.database_one_ip = database_one_ip;
	}
	public String getDatabase_one_name() {
		return database_one_name;
	}
	public void setDatabase_one_name(String database_one_name) {
		this.database_one_name = database_one_name;
	}
	public String getDatabase_one_username() {
		return database_one_username;
	}
	public void setDatabase_one_username(String database_one_username) {
		this.database_one_username = database_one_username;
	}
	public String getDatabase_one_password() {
		return database_one_password;
	}
	public void setDatabase_one_password(String database_one_password) {
		this.database_one_password = database_one_password;
	}
	public String getDatabase_two() {
		return database_two;
	}
	public void setDatabase_two(String database_two) {
		this.database_two = database_two;
	}
	public String getDatabase_two_ip() {
		return database_two_ip;
	}
	public void setDatabase_two_ip(String database_two_ip) {
		this.database_two_ip = database_two_ip;
	}
	public String getDatabase_two_name() {
		return database_two_name;
	}
	public void setDatabase_two_name(String database_two_name) {
		this.database_two_name = database_two_name;
	}
	public String getDatabase_two_username() {
		return database_two_username;
	}
	public void setDatabase_two_username(String database_two_username) {
		this.database_two_username = database_two_username;
	}
	public String getDatabase_two_password() {
		return database_two_password;
	}
	public void setDatabase_two_password(String database_two_password) {
		this.database_two_password = database_two_password;
	}
	public String getDatabase_one_type() {
		return database_one_type;
	}
	public void setDatabase_one_type(String database_one_type) {
		this.database_one_type = database_one_type;
	}
	public String getDatabase_two_type() {
		return database_two_type;
	}
	public void setDatabase_two_type(String database_two_type) {
		this.database_two_type = database_two_type;
	}
	
}
