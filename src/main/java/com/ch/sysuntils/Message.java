package com.ch.sysuntils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 消息类
 * @author Goofy
 * @Date 2015年6月12日 下午7:32:39
 */
public class Message {

	//数据类型，区分操作，1：用户发送消息，2：用户上线通知，3：系统通知,4：强刷用户界面，5：首页消息概览通知，6：工作计划更新通知,7:医嘱数据
	private int datatype=1;
	//在线用户
	private List users;
	//发送者
	private Long uid;
	//发送者名称
	private String fromName;
	//接收者
	private Long touid;
	//发送的文本
	private String text;
	//发送日期
	private String date;

	public int getDatatype() {
		return datatype;
	}

	public void setDatatype(int datatype) {
		this.datatype = datatype;
	}

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getTouid() {
		return touid;
	}

	public void setTouid(Long touid) {
		this.touid = touid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
