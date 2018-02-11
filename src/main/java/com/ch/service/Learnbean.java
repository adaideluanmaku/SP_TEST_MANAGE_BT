package com.ch.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ch.easyui.DataGrid;
import com.ch.progress.Progress;

import net.sf.json.JSONObject;

@Service
public class Learnbean {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static Logger log=Logger.getLogger(Learnbean.class);
	
	public DataGrid learnlist(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid dataGrid = new DataGrid();//返回easyui-web对象
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String learnname="";
		int learngroupid=0;
		
		if(StringUtils.isBlank(req.getParameter("learnname"))){
			learnname="";
		}else{
			learnname=req.getParameter("learnname");
		}
		if(StringUtils.isBlank(req.getParameter("learngroupid"))){
			learngroupid=0;
		}else{
			learngroupid=Integer.parseInt(req.getParameter("learngroupid"));
		}
		if(learngroupid>0){
			learngroupid=Integer.parseInt(req.getParameter("learngroupid"));
			sql="select a.*,b.learngroup from learn a inner join learn_type b on a.learngroupid=b.learngroupid where a.learnname like ? and a.learngroupid=? "+limit;
			rslist=jdbcTemplate.queryForList(sql,new Object[]{"%"+learnname+"%",learngroupid});
			dataGrid.setRows(rslist);
			
			sql="select count(*) from learn a inner join learn_type b on a.learngroupid=b.learngroupid where a.learnname like ? and a.learngroupid=?";
			total=jdbcTemplate.queryForObject(sql, int.class,new Object[]{"%"+learnname+"%",learngroupid});
			dataGrid.setTotal(total+0L);
			
			return dataGrid;
		}
		
		sql="select a.*,b.learngroup from learn a inner join learn_type b on a.learngroupid=b.learngroupid where a.learnname like ? "+limit;
		rslist=jdbcTemplate.queryForList(sql,new Object[]{"%"+learnname+"%"});
		dataGrid.setRows(rslist);
		
		sql="select count(*) from learn a inner join learn_type b on a.learngroupid=b.learngroupid where a.learnname like ? ";
		total=jdbcTemplate.queryForObject(sql, int.class,new Object[]{"%"+learnname+"%"});
		dataGrid.setTotal(total+0L);
		return dataGrid;
	}
	
	public String learnlistadd(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		String learnname="";
		String learntext="";
		int learngroupid=0;
		
		if(StringUtils.isBlank(req.getParameter("learngroupid"))){
			return "未找到分类ID";
		}else{
			learngroupid=Integer.parseInt(req.getParameter("learngroupid"));
		}
		if(StringUtils.isBlank(req.getParameter("learnname"))){
			return "学习标题不能为空";
		}else{
			learnname=req.getParameter("learnname");
		}
		if(StringUtils.isBlank(req.getParameter("learntext"))){
			learntext="";
		}else{
			learntext=req.getParameter("learntext");
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="insert into learn(learnname,learntext,learngroupid,inserttime) value(?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{learnname,learntext,learngroupid,sdf.format(time)});
		log.debug(sql);
		
		return "ok";
	}
	
	public String learnlistdel(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		int learnid=0;
		
		if(StringUtils.isBlank(req.getParameter("learnid"))){
			return "未找到学习ID";
		}else{
			learnid=Integer.parseInt(req.getParameter("learnid"));
		}
		
		sql="delete from learn where learnid = ? ";
		jdbcTemplate.update(sql,new Object[]{learnid});
		
		sql="delete from files where learnid = ? ";
		jdbcTemplate.update(sql,new Object[]{learnid});
		log.debug(sql);
		return "ok";
	}
	
	public String learnlistupdate(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		int learnid=0;
		int learngroupid=0;
		String learnname=null;
		String learntext=null;
		
		if(StringUtils.isBlank(req.getParameter("learnid"))){
			return "未找到学习ID";
		}else{
			learnid=Integer.parseInt(req.getParameter("learnid"));
		}
		if(StringUtils.isBlank(req.getParameter("learngroupid"))){
			return "未找到学习分类ID";
		}else{
			learngroupid=Integer.parseInt(req.getParameter("learngroupid"));
		}
		if(StringUtils.isBlank(req.getParameter("learnname"))){
			return "标题不能为空";
		}else{
			learnname=req.getParameter("learnname");
		}
		if(StringUtils.isBlank(req.getParameter("learntext"))){
			learntext="";
		}else{
			learntext=req.getParameter("learntext");
		}
		
		Date time=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sql="update learn set learngroupid=?,learnname=?,learntext=?,inserttime=? where learnid = ? ";
		jdbcTemplate.update(sql,new Object[]{learngroupid,learnname,learntext,sdf.format(time),learnid});
		log.debug(sql);
		return "ok";
	}
	
	public DataGrid learngrouplist(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		String limit=null;
		int page=0;
		int rows=0;
		String learngroup="";
		
		DataGrid dataGrid = new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		rows=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*rows-rows)+","+rows;
		
		if(StringUtils.isNotBlank(req.getParameter("learngroup"))){
			learngroup=req.getParameter("learngroup");
		}
		
		sql="select * from learn_type where learngroup like ? "+limit;
		rslist=jdbcTemplate.queryForList(sql,new Object[]{"%"+learngroup+"%"});
		log.debug(sql);
		dataGrid.setRows(rslist);
		
		sql="select count(*) from learn_type where learngroup like ?";
		rows=jdbcTemplate.queryForObject(sql, int.class,new Object[]{"%"+learngroup+"%"});
		log.debug(sql);
		dataGrid.setTotal(rows+0L);
		return dataGrid;
	}
	
	public List learngroupbox(HttpServletRequest req){
		String sql=null;
		List list=null;
		
		sql="select * from learn_type ";
		list=jdbcTemplate.queryForList(sql);
		Map map=new HashMap();
		map.put("learngroupid", 0);
		map.put("learngroup","全选");
		list.add(map);
		//按字段重新排序
		Collections.sort(list, new Comparator<Map<String,Object>>() {
			//@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				//进行判断
				return String.valueOf(o1.get("learngroupid").toString()).compareTo(String.valueOf(o2.get("learngroupid").toString()));
			}
		});

		log.debug(sql);
		return list;
	}
	
	public String learngroupadd(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		String learngroup="";
		
		if(StringUtils.isBlank(req.getParameter("learngroup"))){
			return "学习分类不能为空";
		}else{
			learngroup=req.getParameter("learngroup");
		}
		
		sql="select count(*) from learn_type where learngroup = ? ";
		int rsnum=jdbcTemplate.queryForObject(sql,int.class,new Object[]{learngroup});
		log.debug(sql);
		if(rsnum>0){
			return "学习分类重复";
		}
		sql="insert into learn_type(learngroup) value(?)";
		jdbcTemplate.update(sql,new Object[]{learngroup});
		log.debug(sql);
		return "ok";
	}
	
	public String learngroupdel(HttpServletRequest req){
		String sql=null;
		List rslist=null;
		int learngroupid=0;
		
		if(StringUtils.isBlank(req.getParameter("learngroupid"))){
			return "未找到学习分类ID";
		}else{
			learngroupid=Integer.parseInt(req.getParameter("learngroupid"));
		}
		
		sql="delete from learn_type where learngroupid = ? ";
		jdbcTemplate.update(sql,new Object[]{learngroupid});
		log.debug(sql);
		
		sql="select learnid from learn where learngroupid = ? ";
		final List list=jdbcTemplate.queryForList(sql,new Object[]{learngroupid});
		
		sql="delete from learn where learngroupid = ? ";
		jdbcTemplate.update(sql,new Object[]{learngroupid});
		log.debug(sql);
		
		sql="delete from files where learnid = ? ";
		jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(list.get(i).toString()));			}
			@Override
			public int getBatchSize() {
				//这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
				return list.size();
			}
		});
		
		log.debug(sql);
		return "ok";
	}
	
	
	/**
	 * js上传图片到服务器，服务器保存二进制流到数据库
	 * @throws IOException 
	 * 
	 * */
	public void learnuploadfile(HttpServletRequest req) throws IOException{
		String sql=null;
		
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(  
//				req.getSession().getServletContext()); 
		
		MultipartRequest multipartRequest = (MultipartRequest) req;
		// 获得file集合
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			//循环获取file对象
			MultipartFile mf = entity.getValue();
			//输出到本地路径
			//file名称
//			String fileName = mf.getOriginalFilename();
//			FileOutputStream fos = new FileOutputStream(new File("E:/"+fileName));
//			fos.write(mf.getBytes());
//			fos.close();
			//保存在数据库
			sql="insert into files(learnfile,learnid) values(?,?) ";
			jdbcTemplate.update(sql, new Object[]{mf.getBytes(),req.getParameter("learnid")});
			log.debug(sql);
		}
	}
	
	/**
	 * 文件下载，从数据库取出二进制流数据
	 * 
	 * */
	public Map learnreadfile(HttpServletRequest req){
		String sql=null;
		String url=null;
		Map<String, Object> map=null;
		List list=null;
		//blob转二进制
		Blob blob=null;
		
		sql="select fileid,learnfile from files where learnid=? order by fileid asc limit 0,1 ";
		list=jdbcTemplate.queryForList(sql, new Object[]{req.getParameter("learnid")});
		if(list.size()>0){
			map=(Map)list.get(0);
		}
		
		//将Blob装换成二进制数据，再转成字节数组
//		blob=(Blob) map.get("learnfile");
//		InputStream in=null;
//		try {
//			in = blob.getBinaryStream();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		byte[] file=null;
//		try {
//			file = new byte[in.available()];
//			in.read(file);
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		map.put("learnfile", file);
		
		//将文件输出到本地磁盘上
//		if(files==null || "".equals(files)){
//			return url;
//		}
//		String fileName = files.getOriginalFilename();
//		try {
//			//数据库图片输出到本地路径
//			FileOutputStream fos = new FileOutputStream(new File("/file_cache/"+fileName));
//			fos.write(files.getBytes());
//			fos.close();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return url;
		
		return map;
	}
	
	public Map learnreadfile_upandown(HttpServletRequest req){
		String sql=null;
		String url=null;
		List list=null;
		Map<String, Object> map=null;
		
		if("down".equals(req.getParameter("button"))){
			sql="select learnfile,fileid from files where fileid>? and learnid=? order by fileid asc limit 0,1 ";
			list=jdbcTemplate.queryForList(sql,new Object[]{req.getParameter("fileid"),req.getParameter("learnid")});
		}else{
			sql="select learnfile,fileid from files where fileid<? and learnid=? order by fileid desc limit 0,1 ";
			list=jdbcTemplate.queryForList(sql, new Object[]{req.getParameter("fileid"),req.getParameter("learnid")});
		}
		if(list.size()>0){
			map=(Map)list.get(0);
		}
		return map;
	}
	
	public Map learnreadfile_del(HttpServletRequest req){
		String sql=null;
		String url=null;
		List list=null;
		Map<String, Object> map=null;
		
		sql="delete from files where fileid=? and learnid=? ";
		jdbcTemplate.update(sql,new Object[]{req.getParameter("fileid"),req.getParameter("learnid")});
		
		sql="select fileid,learnfile from files where learnid=? order by fileid asc limit 0,1 ";
		list=jdbcTemplate.queryForList(sql, new Object[]{req.getParameter("learnid")});
		
		if(list.size()>0){
			map=(Map)list.get(0);
		}
		return map;
	}
}
