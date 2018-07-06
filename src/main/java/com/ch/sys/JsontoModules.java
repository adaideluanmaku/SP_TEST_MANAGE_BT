package com.ch.sys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JsontoModules {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	StrLevenshtein strLevenshtein;
	
	public int anlitype=0;
	public int moduleid=0;
	public String modulename;
	List listtype1 = null;
	List listtype2 = null;
	public Map setModuletype1(String modulename1) throws ClassNotFoundException, SQLException, IOException{
		String sql=null;
		Map modulemap = new HashMap();
		if(listtype1==null){
			sql="select moduleid,modulename from  mc_modulename where moduletype=1";
			listtype1=jdbcTemplate.queryForList(sql);
		}
		int maxint=0;
		for(int i=0; i<listtype1.size();i++){
			Map map = (Map)listtype1.get(i);
//			float strint=strLevenshtein.getSimilarityRatio(map.get("modulename").toString(),Name);
			int strint=strLevenshtein.str2tosame(map.get("modulename").toString(),modulename1);
			if(strint>maxint){
				maxint=strint;
				modulemap.put("modulename", map.get("modulename").toString());
				modulemap.put("moduleid", Integer.parseInt(map.get("moduleid").toString()));
//				if(strint==1){
//					break;
//				}
			}
		}
		
		if(maxint==0){
			modulemap.put("moduleid", 0);
			modulemap.put("modulename", "未定位");
		}
		
		return modulemap;
	}
	
	public Map setModuletype2(int moduleid) throws ClassNotFoundException, SQLException, IOException{
		String sql=null;
		Map modulemap = new HashMap();
		if(listtype2==null){
			sql="select moduleid ,CONCAT(CASE  moduletype when 1 then '审查_' when 2 then '查询_' else '其他_' end, modulename ) as modulename from mc_modulename where moduletype=2 order by moduleid asc";
			listtype2=jdbcTemplate.queryForList(sql);
		}
		for(int i=0; i<listtype2.size();i++){
			Map map = (Map)listtype2.get(i);
			if(Integer.parseInt(map.get("moduleid").toString())==moduleid){
				modulemap.put("moduleid", map.get("moduleid"));
				modulemap.put("modulename", map.get("modulename"));
				break;
			}
		}
		
		return modulemap;
	}
	
	public Map setPassAnli(String patientname) throws ClassNotFoundException, SQLException, IOException{
		Map map = new HashMap();
		if(patientname.contains("说明书")){//shuomingshu
			Map modulemap = setModuletype2(11);
			map.put("anlitype", 3);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
		}else if(patientname.contains("浮动")){//fudong
			Map modulemap = setModuletype2(51);
			map.put("anlitype", 4);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
		}else if(patientname.contains("详细信息")){//fudong
			map.put("anlitype", 5);
			map.put("moduleid", 0);
			map.put("modulename", "详细信息");
		}else if(patientname.contains("理由")){//ziyouzidingyi
			map.put("anlitype", 6);
			map.put("moduleid", 0);
			map.put("modulename", "用药理由");
		}else if(patientname.contains("右键菜单")){//ziyouzidingyi
			map.put("anlitype", 7);
			map.put("moduleid", 0);
			map.put("modulename", "右键菜单");
		}else if(patientname.contains("模块列表")){//ziyouzidingyi
			map.put("anlitype", 8);
			map.put("moduleid", 0);
			map.put("modulename", "模块列表");
		}else if(patientname.contains("ZYZDY")){//ziyouzidingyi
			Map modulemap = setModuletype1("自由自定义");
			map.put("anlitype", 9);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
		}else if(patientname.contains("中药材专论")){//liyou
			Map modulemap = setModuletype2(24);
			map.put("anlitype", 10);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
		}else if(patientname.contains("用药指导单")){//liyou
			Map modulemap = setModuletype2(34);
			map.put("anlitype", 11);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
		}else if(patientname.contains("手动") || patientname.contains("手自动")){//shoudong
			Map modulemap = setModuletype1(patientname);
			map.put("anlitype", 2);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
		}else {//
			Map modulemap = setModuletype1(patientname);
			map.put("moduleid", modulemap.get("moduleid"));
			map.put("modulename", modulemap.get("modulename"));
			if(Integer.parseInt(modulemap.get("moduleid").toString())==0){
				map.put("anlitype", 999);
			}else{
				map.put("anlitype", 1);
			}
		}
		
		return map;
	}
}
