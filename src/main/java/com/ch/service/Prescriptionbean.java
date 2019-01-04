package com.ch.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ch.dao.SpringJdbc_sqlserver_pass;
import com.ch.sys.AlterKettle;
import com.ch.sysuntils.DataGrid;
import com.ch.sysuntils.KettleUtils;

@Service
public class Prescriptionbean {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	KettleUtils kettleUtils;
	@Autowired
	AlterKettle alterKettle;
	private static Logger log = Logger.getLogger(Prescriptionbean.class);
	public DataGrid prescription_query(HttpServletRequest req){
		DataGrid dataGrid=new DataGrid();
		
		List lstRes = new ArrayList();
//		System.out.println("home 用药研究 处方管理");
		//单页条数
		int limit=Integer.parseInt(req.getParameter("limit").toString());
		//当前页编号,已乘以当前页总数
		int offset=Integer.parseInt(req.getParameter("offset")); 
		String search=req.getParameter("search");
		
		String patientname=req.getParameter("patientname"); 
		if(StringUtils.isBlank(patientname)){
			patientname="";
		}
		String sql="select * from prescription where patientname like ? limit "+offset+","+limit;
		lstRes=jdbcTemplate.queryForList(sql,new Object[]{"%"+patientname+"%"});
		
//		if(lstRes.size()==0 && offset/limit>0){
//			offset=offset-limit;
//			sql="select * from prescription where patientname like ? limit "+offset+","+limit;
//			lstRes=jdbcTemplate.queryForList(sql,new Object[]{"%"+patientname+"%"});
//		}
		
		sql="select count(1) from prescription where patientname like ? ";
		int count =jdbcTemplate.queryForObject(sql,int.class,new Object[]{"%"+patientname+"%"});
		
		dataGrid.setTotal(count+0L);
		dataGrid.setRows(lstRes);
		
		return dataGrid;
	}
	
	public String prescription_add(HttpServletRequest req){
		String patientname=req.getParameter("patientname"); 
		String prescription_json=req.getParameter("prescription_json"); 
		String sql=null;
		
		if(StringUtils.isBlank(req.getParameter("patientname"))){
			return "病人名称不能为空";
		}else{
			patientname=req.getParameter("patientname");
		}
		
		int rsl=0;
		sql="select count(1) from prescription where patientname=? ";
		rsl=jdbcTemplate.queryForObject(sql,int.class,new Object[]{patientname});
		
		if(rsl>0){
			return "病人名称不能重复";
		}
		
		sql="insert into prescription(patientname,prescription_json) values(?, ?) ";
		jdbcTemplate.update(sql,new Object[]{patientname,prescription_json});

		return "ok";
	}
	
	public String prescription_del(HttpServletRequest req,Integer[]  pre_id){
		System.out.println(pre_id);
		List<Integer> list= Arrays.asList(pre_id);
		
		if(list.size()>0){
			String sql="delete from prescription where ";
			
			for(int i=0;i<list.size();i++){
				if(i==0){
					sql=sql+" pre_id = ? ";
				}else{
					sql=sql+" or pre_id = ? ";
				}
				
			}
			jdbcTemplate.update(sql,list.toArray());
		}
		
		return "ok";
	}
	
	public String prescription_update(HttpServletRequest req){
		int prescriptiontype=0;
		if(StringUtils.isNotBlank(req.getParameter("prescriptiontype"))){
			prescriptiontype=Integer.parseInt(req.getParameter("prescriptiontype").toString());
		}
		
		if(prescriptiontype==1){
			String pre_id="";
//			String patientname=""; 
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "pre_id不能为空";
			}else{
				pre_id=req.getParameter("pre_id");
			}
//			if(StringUtils.isNotBlank(req.getParameter("patientname"))){
//				patientname=req.getParameter("patientname"); 
//			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update prescription set prescription_json=? where pre_id=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,pre_id});
		}
		
		if(prescriptiontype==2){
			int testid=0;
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "testid不能为空";
			}else{
				testid=Integer.parseInt(req.getParameter("pre_id").toString());
			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update pa_testmng set testin=? where testid=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,testid});
		}
		
		if(prescriptiontype==3){
			int testid=0;
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "testid不能为空";
			}else{
				testid=Integer.parseInt(req.getParameter("pre_id").toString());
			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update pass_testmng set testin=? where testid=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,testid});
		}
		
		if(prescriptiontype==4){
			int testid=0;
			String prescription_json=""; 
			if(StringUtils.isBlank(req.getParameter("pre_id"))){
				return "testid不能为空";
			}else{
				testid=Integer.parseInt(req.getParameter("pre_id").toString());
			}
			if(StringUtils.isNotBlank(req.getParameter("prescription_json"))){
				prescription_json=req.getParameter("prescription_json"); 
			}
			
			String sql="update zfxm_testmng set testin=? where testid=? ";
			jdbcTemplate.update(sql,new Object[]{prescription_json,testid});
		}
		return "ok";
	}

	public String yijiandaorumubiao(int tongbuDatabaseid,String tongbuTable,String mhiscode,String path) {
		String[] mhiscode1= new String[1];
		mhiscode1[0]=mhiscode;
		Map<String,String> map=new HashMap();
		map.put("mhiscode", mhiscode);
		
		//读取目录下的所有文件
//		File file = new File(path);
//		File[] tempList = file.listFiles();
//		if(tempList!=null){
//			for (int i = 0; i < tempList.length; i++) {
//				if (tempList[i].isFile()) {
//					kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//					log.info("finish :" + tempList[i]);
//				}
//			}
//		}
		
		if("-1".equals(tongbuTable)){
			path=path.split("webapp")[0]+"webapp\\kettle\\out\\";
			alterKettle.kettlerun(tongbuDatabaseid, path);
			
			File file = new File(path);
			File[] tempList = file.listFiles();
			if(tempList!=null){
				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].isFile()) {
						kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
						log.info("执行脚本结束 :" + tempList[i]);
					}
				}
			}
		}else{
			path=path.split("webapp")[0]+"webapp\\kettle\\out\\"+tongbuTable+".ktr";
			alterKettle.kettlerun(tongbuDatabaseid, path);
			
			kettleUtils.runTransfer(mhiscode1, path);
			log.info("执行脚本结束 :" + path);
		}
		
//		if(tongbuDatabaseid == 1){
//			if("-1".equals(tongbuTable)){
//				path=path.split("webapp")[0]+"webapp\\kettle_mysql\\out\\";
//				alterKettle.kettlerun(tongbuDatabaseid, path);
//				
//				File file = new File(path);
//				File[] tempList = file.listFiles();
//				if(tempList!=null){
//					for (int i = 0; i < tempList.length; i++) {
//						if (tempList[i].isFile()) {
//							kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//							log.info("执行脚本结束 :" + tempList[i]);
//						}
//					}
//				}
//			}else{
//				path=path.split("webapp")[0]+"webapp\\kettle_mysql\\out\\"+tongbuTable+".ktr";
//				alterKettle.kettlerun(tongbuDatabaseid, path);
//				
//				kettleUtils.runTransfer(mhiscode1, path);
//				log.info("执行脚本结束 :" + path);
//			}
//		}
//		if (tongbuDatabaseid == 2) {
//			if("-1".equals(tongbuTable)){
//				path=path.split("webapp")[0]+"webapp\\kettle_sqlserver\\out\\";
//				File file = new File(path);
//				File[] tempList = file.listFiles();
//				if(tempList!=null){
//					for (int i = 0; i < tempList.length; i++) {
//						if (tempList[i].isFile()) {
//							kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//							log.info("执行脚本结束 :" + tempList[i]);
//						}
//					}
//				}
//			}else{
//				path=path.split("webapp")[0]+"webapp\\kettle_sqlserver\\out\\"+tongbuTable+".ktr";
//				kettleUtils.runTransfer(mhiscode1, path);
//				log.info("执行脚本结束 :" + path);
//			}
//			
//		}
//		if (tongbuDatabaseid == 3) {
//			if("-1".equals(tongbuTable)){
//				path=path.split("webapp")[0]+"webapp\\kettle_oracle\\out\\";
//				File file = new File(path);
//				File[] tempList = file.listFiles();
//				if(tempList!=null){
//					for (int i = 0; i < tempList.length; i++) {
//						if (tempList[i].isFile()) {
//							kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//							log.info("执行脚本结束 :" + tempList[i]);
//						}
//					}
//				}
//			}else{
//				path=path.split("webapp")[0]+"webapp\\kettle_oracle\\out\\"+tongbuTable+".ktr";
//				kettleUtils.runTransfer(mhiscode1, path);
//				log.info("执行脚本结束 :" + path);
//			}
//			
//		}
		
		return "ok";
	}
	
	public String yijiandaoruanli(int tongbuDatabaseid,String tongbuTable,String mhiscode,String path) {
		String[] mhiscode1= new String[1];
		mhiscode1[0]=mhiscode;
		Map<String,String> map=new HashMap();
		map.put("mhiscode", mhiscode);
		
		//读取目录下的所有文件
//		File file = new File(path);
//		File[] tempList = file.listFiles();
//		if(tempList!=null){
//			for (int i = 0; i < tempList.length; i++) {
//				if (tempList[i].isFile()) {
//					kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//					log.info("finish :" + tempList[i]);
//				}
//			}
//		}
		
		if("-1".equals(tongbuTable)){
			path=path.split("webapp")[0]+"webapp\\kettle\\in\\";
			alterKettle.kettlerun(tongbuDatabaseid, path);
			
			File file = new File(path);
			File[] tempList = file.listFiles();
			if(tempList!=null){
				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].isFile()) {
						kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
						log.info("执行脚本结束 :" + tempList[i]);
					}
				}
			}
		}else{
			path=path.split("webapp")[0]+"webapp\\kettle\\in\\"+tongbuTable+".ktr";
			alterKettle.kettlerun(tongbuDatabaseid, path);
			
			kettleUtils.runTransfer(mhiscode1, path);
			log.info("执行脚本结束 :" + path);
		}
		
//		if(tongbuStatus == 1){
//			if("-1".equals(tongbuTable)){
//				path=path.split("webapp")[0]+"webapp\\kettle_mysql\\in\\";
//				File file = new File(path);
//				File[] tempList = file.listFiles();
//				if(tempList!=null){
//					for (int i = 0; i < tempList.length; i++) {
//						if (tempList[i].isFile()) {
//							kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//							log.info("执行脚本结束 :" + tempList[i]);
//						}
//					}
//				}
//			}else{
//				path=path.split("webapp")[0]+"webapp\\kettle_mysql\\in\\"+tongbuTable+".ktr";
//				kettleUtils.runTransfer(mhiscode1, path);
//				log.info("执行脚本结束 :" + path);
//			}
//			
//		}
//		if (tongbuStatus == 2) {
//			if("-1".equals(tongbuTable)){
//				path=path.split("webapp")[0]+"webapp\\kettle_sqlserver\\in\\";
//				File file = new File(path);
//				File[] tempList = file.listFiles();
//				if(tempList!=null){
//					for (int i = 0; i < tempList.length; i++) {
//						if (tempList[i].isFile()) {
//							kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//							log.info("执行脚本结束 :" + tempList[i]);
//						}
//					}
//				}
//			}else{
//				path=path.split("webapp")[0]+"webapp\\kettle_sqlserver\\in\\"+tongbuTable+".ktr";
//				kettleUtils.runTransfer(mhiscode1, path);
//				log.info("执行脚本结束 :" + path);
//			}
//			
//		}
//		if (tongbuStatus == 3) {
//			if("-1".equals(tongbuTable)){
//				path=path.split("webapp")[0]+"webapp\\kettle_oracle\\in\\";
//				File file = new File(path);
//				File[] tempList = file.listFiles();
//				if(tempList!=null){
//					for (int i = 0; i < tempList.length; i++) {
//						if (tempList[i].isFile()) {
//							kettleUtils.runTransfer(mhiscode1, tempList[i].toString());
//							log.info("执行脚本结束 :" + tempList[i]);
//						}
//					}
//				}
//			}else{
//				path=path.split("webapp")[0]+"webapp\\kettle_oracle\\in\\"+tongbuTable+".ktr";
//				kettleUtils.runTransfer(mhiscode1, path);
//				log.info("执行脚本结束 :" + path);
//			}
//		}
		
		return "ok";
	}
	
}
