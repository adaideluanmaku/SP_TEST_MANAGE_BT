package com.ch.pahis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class AnliCount {
	@Autowired
	JdbcTemplate jdbcTemplate_anli;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List getAnli(int anlisum,String hiscode){
		List list=null;
		List anlilist=new ArrayList();
		if(anlisum==1){
			//案例医嘱1:1.25
//			String[] anliname={"不良反应015","体外配伍068","儿童用药027","剂量范围014","哺乳用药046","围手术期020",
//					"妊娠用药012","性别用药031","成人用药018","相互作用022","细菌耐药率015","给药途径036","老人用药021",
//					"肝损害剂量027","肾损害剂量066","药物禁忌症032","药物过敏028","超适应症023",
//					"越权用药019","配伍浓度020","重复用药031","钾离子浓度069","超多日用量025"};
			//案例医嘱1:4
			String[] anliname={"不良反应030","体外配伍065","儿童用药055","剂量范围405","哺乳用药046","围手术期037",
					"妊娠用药043","性别用药012","成人用药029","相互作用063","细菌耐药率021","给药途径022","老人用药054",
					"肝损害剂量156","肾损害剂量156","药物禁忌症021","药物过敏022","超适应症010",
					"越权用药019","越权用药027","重复用药041","钾离子浓度095","超多日用量090"};
//			String[] anliname={"不良反应015"};
					
			//获取案例数据
			String sql=null;
//			System.out.println(anliname.length);
			for(int i=0;i<anliname.length;i++){
				sql="select gatherbaseinfo from sa_gather_log where anliname=? and version='1609'";
				String jsonstr=jdbcTemplate_anli.queryForObject(sql, String.class,new Object[]{anliname[i]});
				
				//变更HISCODE
				try{
					JSONObject json=JSONObject.fromObject(jsonstr);
					JSONObject PassClient=json.getJSONObject("PassClient");
					PassClient.put("HospID", hiscode);
					json.put("PassClient", PassClient);
					anlilist.add(json.toString());
				}catch(Exception e){
					System.out.println("有一条案例无法JSON解析"+e);
					continue;
				}
				
//				不变更HISCODE
//				anlilist.add(rs.getObject(1).toString());
			}
			System.out.println("1次循环数据集条数：PASS各模块案例--"+anlilist.size());
			return anlilist;
		}
		if(anlisum==2){
			//获取案例数据
			String sql=null;
			sql="select gatherbaseinfo from sa_gather_log where anlitype=1 and version='1609' and anliname not in(select anliname from log where anlistatus='1')";
			list=jdbcTemplate_anli.queryForList(sql);
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				//变更HISCODE
				try{
					JSONObject json=JSONObject.fromObject(map.get("gatherbaseinfo"));
					JSONObject PassClient=json.getJSONObject("PassClient");
					PassClient.put("HospID", hiscode);
					json.put("PassClient", PassClient);
					anlilist.add(json.toString());
				}catch(Exception e){
					System.out.println("有一条案例无法JSON解析"+e);
					continue;
				}
			}
			System.out.println("1次循环数据集条数：PASS全案例--"+anlilist.size());
			return anlilist;	
		}
		if(anlisum==3){
			//获取案例数据
			String sql=null;
			sql="select testin from testmng where projectid=20 and status =1";//PA案例对应的项目编号
//			sql="select testin from testmng where projectid=20 and status =1 and testno='23-1'";
			list=jdbcTemplate.queryForList(sql);
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				//变更HISCODE
				try{
					JSONObject json=JSONObject.fromObject(map.get("testin"));
					JSONObject PassClient=json.getJSONObject("PassClient");
					PassClient.put("HospID", hiscode);
					json.put("PassClient", PassClient);
					anlilist.add(json.toString());
				}catch(Exception e){
					System.out.println("有一条案例无法JSON解析"+e);
					continue;
				}
			}
			System.out.println("1次循环数据集条数：PA全案例--"+anlilist.size());
			return anlilist;
		}
		
		return list;
	}
}
