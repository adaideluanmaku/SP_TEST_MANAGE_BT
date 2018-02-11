package com.ch.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ch.easyui.DataGrid;
import com.ch.http.Paservice;
import com.mysql.fabric.xmlrpc.base.Data;

//暂时作废
@Service
public class Ordersbean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	SocketBean socketBean;
	
	private static Logger log = Logger.getLogger(Ordersbean.class);
	
	public void order(HttpServletRequest req){
		//广播通知
		socketBean.tostr(req);
	}
	
	public void json_update(HttpServletRequest req){
//		System.out.println(req.getParameter("json"));
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql="update testmng set testin=?,userid=?,inserttime=? where testid=?";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("json"),req.getSession().getAttribute("userid"),sdf.format(time),req.getParameter("testid")});
	}
}
