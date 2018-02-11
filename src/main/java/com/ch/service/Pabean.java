package com.ch.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.easyui.DataGrid;
import com.ch.http.Paservice;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
public class Pabean {
	public static Jedis jedis;
	public static final String PA_SCREENRESULTS = "PA_SCREENRESULT_LIST";
	
	@Value("${redis.ip}")
	private String redisip;
	@Value("${redis.port}")
	private int redisport;
	@Value("${redis.auth}")
	private String redisauth;
	@Value("${redis.select}")
	private int redisselect;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	JdbcTemplate jdbcTemplate_passpa2db;
	
	@Autowired
	Paservice paservice;
	
	public String pa_screen(HttpServletRequest req){
		int testid=0;
		String sql=null;
		String duibiresult=null;
		String VisitCode=req.getParameter("VisitCode");
		
		if(StringUtils.isBlank(req.getParameter("testid"))){
			return "未收到案例ID";
		}else{
			testid=Integer.parseInt(req.getParameter("testid"));
		}
		
//		Date time=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//1java案例，2win案例
		if(Integer.parseInt(req.getParameter("state"))==1){
			//清理单个案例缓存
			pa_redis_clear(VisitCode);
		}
		
		//测试案例
		sql="select testin from testmng where testid=?";
		String testin=jdbcTemplate.queryForObject(sql,String.class,new Object[]{testid});
		
		//取url
		String url=null;
		if(Integer.parseInt(req.getParameter("state"))==1){
			sql="select pa_screen from sys_users where userid=?";
//			System.out.println(req.getSession().getAttribute("userid"));
			url=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getSession().getAttribute("userid")});
		}else{
			sql="select pa_screen_win from sys_users where userid=?";
//			System.out.println(req.getSession().getAttribute("userid"));
			url=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getSession().getAttribute("userid")});
		}
		
		if("".equals(url) || url==null){
			return "screen url is null";
		}
		//调审查接口,服务器将结果存在redis中
		boolean result=false;
		try {
			result=paservice.getPaResult(testin, url);
			
			if(!result){
				return "PASS CORE服务连接失败";
			}
			
			if(Integer.parseInt(req.getParameter("state"))==2){
				
				return "审查结束,请到PA-WIN的sqlserver数据库找结果";
			}
			//redis连接
			jedis = new Jedis(redisip, redisport);
			jedis.auth(redisauth);
			jedis.select(redisselect);

//			System.out.println("redis连接："+jedis.ping());
			if(!"PONG".equals(jedis.ping())){
				return "redis连接失败";
			}
			
			//获取结果
			List<String> values = jedis.lrange("PA_SCREENRESULT_LIST", 0, -1);
			
			//和数据库预期结果做对比
			sql="select testout from testmng where testid=?";
			String testout=jdbcTemplate.queryForObject(sql,String.class,new Object[]{testid});
			
//			int num1=0;
//			int num2=0;
//			for(int i=0;i<values.size();i++){
//				System.out.println(values.get(i));
//				JSONObject json=JSONObject.fromObject(values.get(i));
//				if(VisitCode.equals(json.get("visitcode"))){
//					num1=num1+1;
//					if(testout.equals(json.get("warning"))){
//						num2=num2+1;
//					}
//				}
//			}
//			if(num1<=0 && "无审查结果".equals(testout)){
//				sql="update testmng set testresult=? where testid=?";
//				jdbcTemplate.update(sql,new Object[]{"通过",testid});
//			}else if(num1<=0 && !"无审查结果".equals(testout)){
//				sql="update testmng set testresult=? where testid=?";
//				jdbcTemplate.update(sql,new Object[]{"不通过",testid});
//			}else{
//				if(num2==num1){
//					sql="update testmng set testresult=? where testid=?";
//					jdbcTemplate.update(sql,new Object[]{"通过",testid});
//				} else if(num2==0 && "无审查结果".equals(testout)){//num1>0:有数据,num2=0:没有对比结果,"无审查结果"的结论为不能找到任何数据
//					sql="update testmng set testresult=? where testid=?";
//					jdbcTemplate.update(sql,new Object[]{"不通过",testid});
//				} else if(num2==0 && !"无审查结果".equals(testout)){
//					sql="update testmng set testresult=? where testid=?";
//					jdbcTemplate.update(sql,new Object[]{"不通过",testid});
//				}else if(num2>0 && num2<num1){
//					sql="update testmng set testresult=? where testid=?";
//					jdbcTemplate.update(sql,new Object[]{"通过,有多余",testid});
//				}
//			}
			
			JSONArray json_java=new JSONArray();
			for(int i=0;i<values.size();i++){
				JSONObject json=JSONObject.fromObject(values.get(i));
				if(VisitCode.equals(json.get("visitcode"))){
					json_java.add(json);
				}
			}
			duibiresult=Jsonduibi(json_java.toString(),testout);
			if(StringUtils.isNotBlank(duibiresult)){
				sql="update testmng set testresult=? where testid=?";
				jdbcTemplate.update(sql,new Object[]{"不通过",testid});
			}else{
				sql="update testmng set testresult=? where testid=?";
				jdbcTemplate.update(sql,new Object[]{"通过",testid});
			}
			
		} catch (UnsupportedEncodingException | TimeoutException e) {
			// TODO Auto-generated catch block
			System.out.println("pa审查结果异常");
			return "pa审查结果异常";
		}
		
		return duibiresult;
		//广播通知
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
	}
	
	public String pa_screen_all(HttpServletRequest req){
		String sql=null;
		int projectid=0;
		String duibiresult=null;
		String search_data="";
		if(StringUtils.isBlank(req.getParameter("projectid"))){
			return "未收到案例ID";
		}else{
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}
		
		if(StringUtils.isNotBlank(req.getParameter("search_data"))){
			search_data=req.getParameter("search_data");
		}
//		Date time=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//清理所有案例缓存
		pa_redis_clear_all();
		
		//测试案例
		//获取测试案例，testin发送请求，testout对比结果
		sql="select testid,testin,testout,testno,testname from testmng where projectid=? and (testname like ? or testno like ?) and status=1";
		List testlist=jdbcTemplate.queryForList(sql,new Object[]{projectid,"%"+search_data+"%","%"+search_data+"%"});
		
		sql="update testmng set testresult='' where projectid=? ";
		jdbcTemplate.update(sql,new Object[]{projectid});
		
		//取url
		sql="select pa_screen from sys_users where userid=?";
//		System.out.println(req.getSession().getAttribute("userid"));
		String url=jdbcTemplate.queryForObject(sql,String.class,new Object[]{req.getSession().getAttribute("userid")});
		if("".equals(url)){
			return "screen url is null";
		}
		
		//调审查接口,服务器将结果存在redis中
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);
//		System.out.println("redis连接："+jedis.ping());
		if(!"PONG".equals(jedis.ping())){
			return "redis连接失败";
		}
		
		try {
			for(int i=0;i<testlist.size();i++){
				Map map=(Map)testlist.get(i);
				String testin=map.get("testin").toString();
				System.out.println("调用pa_screen_java接口审查案例-案例编号是："+map.get("testno"));
				paservice.getPaResult(testin, url);
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//获取结果
			List<String> values = jedis.lrange("PA_SCREENRESULT_LIST", 0, -1);
			
			for(int i=0;i<testlist.size();i++){
				Map map=(Map)testlist.get(i);
				String VisitCode=null;
				try{
					JSONObject testinjson=JSONObject.fromObject(map.get("testin"));
					JSONObject Patient=testinjson.getJSONObject("Patient");
					VisitCode=Patient.getString("VisitCode");
				}catch(Exception e){
					System.out.println("审查案例JSON解析错误："+map.get("testno"));
					continue;
				}
				
				JSONArray json_java=new JSONArray();
				for(int i1=0;i1<values.size();i1++){
					JSONObject json=JSONObject.fromObject(values.get(i1));
					if(VisitCode.equals(json.get("visitcode"))){
						json_java.add(json);
					}
				}
				System.out.println("开始对比pa_screen_java和案例结果-案例编号是："+map.get("testno"));
				duibiresult=Jsonduibi(json_java.toString(),map.get("testout").toString());
				if(StringUtils.isNotBlank(duibiresult)){
					sql="update testmng set testresult=? where testid=?";
					jdbcTemplate.update(sql,new Object[]{"不通过",map.get("testid")});
				}else{
					sql="update testmng set testresult=? where testid=?";
					jdbcTemplate.update(sql,new Object[]{"通过",map.get("testid")});
				}
				
				
//				int num1=0;
//				int num2=0;
//				for(int i1=0;i1<values.size();i1++){
////					System.out.println(values.get(i));
//					JSONObject redisjson=JSONObject.fromObject(values.get(i1));
//					if(VisitCode.equals(redisjson.get("visitcode"))){
//						num1=num1+1;
//						if(map.get("testout").equals(redisjson.get("warning"))){
//							num2=num2+1;
//						}
//					}
//				}
//				
//				if(num1==0 && "无审查结果".equals(map.get("testout"))){
//					sql="update testmng set testresult=? where testid=?";
//					jdbcTemplate.update(sql,new Object[]{"通过",map.get("testid")});
//				}else if(num1<=0 && !"无审查结果".equals(map.get("testout"))){
//					sql="update testmng set testresult=? where testid=?";
//					jdbcTemplate.update(sql,new Object[]{"不通过",map.get("testid")});
//				}else{
//					if(num2==num1){
//						sql="update testmng set testresult=? where testid=?";
//						jdbcTemplate.update(sql,new Object[]{"通过",map.get("testid")});
//					} else if(num2==0 && "无审查结果".equals(map.get("testout"))){//num1>0:有数据,num2=0:没有对比结果,"无审查结果"的结论为不能找到任何数据
//						sql="update testmng set testresult=? where testid=?";
//						jdbcTemplate.update(sql,new Object[]{"不通过",map.get("testid")});
//					} else if(num2==0 && !"无审查结果".equals(map.get("testout"))){
//						sql="update testmng set testresult=? where testid=?";
//						jdbcTemplate.update(sql,new Object[]{"不通过",map.get("testid")});
//					}else if(num2>0 && num2<num1){
//						sql="update testmng set testresult=? where testid=?";
//						jdbcTemplate.update(sql,new Object[]{"通过,有多余",map.get("testid")});
//					}
//				}
			}
		} catch (UnsupportedEncodingException | TimeoutException e) {
			// TODO Auto-generated catch block
			System.out.println("pa审查结果异常");
			return "pa审查结果异常";
		}
		
		//清理所有案例缓存
		pa_redis_clear_all();
		return "全部测试结束";
		//广播通知
//		socketBean.logreload(Integer.parseInt(req.getSession().getAttribute("userid").toString()));
	}
	
	public String Jsonduibi(String json_java1,String json_win1){
		List errlist=new ArrayList();
		String errstr="";
		String result="";
		try{
			JSONArray json_wins =JSONArray.fromObject(json_win1);
			JSONArray json_javas =JSONArray.fromObject(json_java1);
			
			for(int i=0;i<json_wins.size();i++){
				JSONObject json_win=json_wins.getJSONObject(i);
				for(int i1=0;i1<json_javas.size();i1++){
					JSONObject json_java=json_javas.getJSONObject(i1);
					errstr="";
					int jilu=0;
					if(json_win.get("moduleid").equals(json_java.get("moduleid")) &&
							json_win.get("moduleitem").equals(json_java.get("moduleitem")) &&
							json_win.get("drug_unique_code").equals(json_java.get("drug_unique_code")) &&
							json_win.get("recipeno").equals(json_java.get("recipeno"))){
						jilu=1;
						if(!json_win.get("severity").equals(json_java.get("severity"))){
							errstr=errstr+"severity,";
						}
						if(!json_win.get("orderno").equals(json_java.get("orderno"))){
							errstr=errstr+"orderno,";
						}
						if(!json_win.get("drugmaxwarn").equals(json_java.get("drugmaxwarn"))){
							errstr=errstr+"drugmaxwarn,";
						}
//						if(!json_win.get("recipeno").equals(json_java.get("recipeno"))){
//							errstr=errstr+"recipeno,";
//						}
						if(!json_win.get("drugname").equals(json_java.get("drugname"))){
							errstr=errstr+"drugname,";
						}
//						if(!json_win.get("drug_unique_code").equals(json_java.get("drug_unique_code"))){
//							errstr=errstr+"drug_unique_code,";
//						}
						if(!json_win.get("usetime").equals(json_java.get("usetime"))){
							errstr=errstr+"usetime,";
						}
						if(!json_win.get("costunit").equals(json_java.get("costunit"))){
							errstr=errstr+"costunit,";
						}
//						if(!json_win.get("moduleitem").equals(json_java.get("moduleitem"))){
//							errstr=errstr+"moduleitem,";
//						}
						if(!json_win.get("patstatus").equals(json_java.get("patstatus"))){
							errstr=errstr+"patstatus,";
						}
						if(!json_win.get("modulename").equals(json_java.get("modulename"))){
							errstr=errstr+"modulename,";
						}
						if(!json_win.get("warning").equals(json_java.get("warning"))){
							errstr=errstr+"warning,";
						}
						if(!json_win.get("slcode").equals(json_java.get("slcode"))){
							errstr=errstr+"slcode,";
						}
						if(!json_win.get("cid").equals(json_java.get("cid"))){
							errstr=errstr+"cid,";
						}
						if(!json_win.get("drugspec").equals(json_java.get("drugspec"))){
							errstr=errstr+"drugspec,";
						}
						
						json_wins.remove(i);
						json_javas.remove(i1);
						i=i-1;
						i1=i1-1;
					}
					if(jilu==1){
						if(errstr.length()>0){
							result=result+"<div style='color:red'>存在问题的节点有："+errstr+"</div><br>"
							+"<div style='color:blue'>断言moduleid-"+json_win.get("moduleid")+"-->"+json_win+"</div><br>"
							+"<div style='color:blue'>响应moduleid-"+json_win.get("moduleid")+"-->"+json_java+"</div><br>";
							break;
						}else{
							break;
						}
					}
				}
			}
			if(json_wins.size()>0){
				for(int i=0;i<json_wins.size();i++){
					JSONObject json_win=json_wins.getJSONObject(i);
					result=result+"<div style='color:red'>断言：-->"+json_win+"</div><br>"
							+"<div style='color:blue'>响应：缺少断言对应的结果</div><br>";
				}
			}
			if(json_javas.size()>0){
				for(int i=0;i<json_javas.size();i++){
					JSONObject json_java=json_javas.getJSONObject(i);
					result=result+"<div style='color:red'>断言：不存在该结果</div><br>"
					+"<div style='color:blue'>响应：-->"+json_java+"</div><br>";
							
				}
			}
		}catch(Exception e){
			System.out.println(e);
			result="json串解析失败";
		}
		
		
		return result;
	}
	
	public String pa_screen_redis(HttpServletRequest req){
		String VisitCode=req.getParameter("VisitCode");
		String result="";
		
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);

//		System.out.println("redis连接："+jedis.ping());
		if(!"PONG".equals(jedis.ping())){
			return "redis连接失败";
		}
		
		//PASS redis查询
//				Set<String> keys1 = jedis.keys(PA_SCREENRESULTS);
//				System.out.println("redis-key PA总数:"+keys1.size());
		
		//redis数据类型为list时
//		System.out.println("list内部数据总数："+jedis.llen(PA_SCREENRESULTS));
//				System.out.println(jedis.lrange(PA_SCREENRESULTS, 0, 10));
		List<String> values = jedis.lrange(PA_SCREENRESULTS, 0, -1);
		
		int num=0;
		for(int i=0;i<values.size();i++){
//			System.out.println(values.get(i));
			JSONObject json=JSONObject.fromObject(values.get(i));
			if(VisitCode.equals(json.get("visitcode"))){
				num=num+1;
				if(i%2==0){
					result=result+"<div style='color:red'>第"+(num)+"条结果：<br>"+values.get(i)+"</div><br>";
				}else{
					result=result+"<div style='color:blue'>第"+(num)+"条结果：<br>"+values.get(i)+"</div><br>";
				}
			}
		}
		
		return result;
	}
	
	//清空redis
	public void pa_redis_clear(String VisitCode){
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);

		System.out.println("redis连接："+jedis.ping());
//		if(!"PONG".equals(jedis.ping())){
//			return "redis连接失败";
//		}
		
		//redis数据类型为list时,用lrange取数据
		List<String> values = jedis.lrange(PA_SCREENRESULTS, 0, -1);
		for(int i=0;i<values.size();i++){
//			System.out.println(values.get(i));
			JSONObject json=JSONObject.fromObject(values.get(i));
			
			if(VisitCode.equals(json.get("visitcode"))){
				values.remove(i);
				i=i-1;
			}
		}
		
		jedis.del(PA_SCREENRESULTS);
		
		//将list数据存回redis
		if(values.size()!=0){
			for(int i=0;i<values.size();i++){
				jedis.lpush(PA_SCREENRESULTS, values.get(i));
			}
		}
		
	}
	
	//清空redis
	public void pa_redis_clear_all(){
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);

//		System.out.println("redis连接："+jedis.ping());
//			if(!"PONG".equals(jedis.ping())){
//				return "redis连接失败";
//			}
		
		jedis.del(PA_SCREENRESULTS);
		
	}
	
	//清空redis
	public String pa_redis_clear_sd(HttpServletRequest req){
		//redis连接
		jedis = new Jedis(redisip, redisport);
		jedis.auth(redisauth);
		jedis.select(redisselect);

//		System.out.println("redis连接："+jedis.ping());
		if(!"PONG".equals(jedis.ping())){
			return "redis连接失败";
		}
		
		//删除
		jedis.del(PA_SCREENRESULTS);
		
		return "redis审查结果清空成功";
	}
	
	//过敏原字典表
	public DataGrid aller_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String allercode=null;
		if(StringUtils.isBlank(req.getParameter("allercode"))){
			allercode="";
		}else{
			allercode=req.getParameter("allercode");
		}
		
		sql="select b.hisname,a.allercode,a.allername from mc_dict_allergen a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.allermatch_scheme and a.allercode like ? order by a.allercode asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+allercode+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.allercode) from mc_dict_allergen a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.allermatch_scheme  and a.allercode like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+allercode+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//疾病字典表
	public DataGrid dis_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String discode=null;
		if(StringUtils.isBlank(req.getParameter("discode"))){
			discode="";
		}else{
			discode=req.getParameter("discode");
		}
		
		sql="select b.hisname,a.discode,a.disname from mc_dict_disease a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.dismatch_scheme and a.discode like ? order by a.discode asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+discode+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.discode) from mc_dict_disease a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.dismatch_scheme  and a.discode like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+discode+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//手术字典表
	public DataGrid opr_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String oprcode=null;
		if(StringUtils.isBlank(req.getParameter("oprcode"))){
			oprcode="";
		}else{
			oprcode=req.getParameter("oprcode");
		}
		
		sql="select b.hisname,a.operationcode,a.operationname from mc_dict_operation a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme and a.operationcode like ? order by a.operationcode asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+oprcode+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.operationcode) from mc_dict_operation a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.oprmatch_scheme  and a.operationcode like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+oprcode+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//药品字典表
	public DataGrid odr_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String drug_uni_code=null;
		if(StringUtils.isBlank(req.getParameter("odrcode"))){
			drug_uni_code="";
		}else{
			drug_uni_code=req.getParameter("odrcode");
		}
		
		sql="select b.hisname,a.drug_unique_code,a.drugname,a.drugform,a.drugspec,a.comp_name,a.doseunit "
				+ "from mc_dict_drug_pass a,mc_hospital_match_relation b where "
				+ "a.match_scheme=b.drugmatch_scheme and a.drug_unique_code like ? order by a.drug_unique_code asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+drug_uni_code+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.drug_unique_code) from mc_dict_drug_pass a,mc_hospital_match_relation b where "
				+ "a.match_scheme=b.drugmatch_scheme and a.drug_unique_code like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+drug_uni_code+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//给药途径字典表
	public DataGrid route_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String routecode=null;
		if(StringUtils.isBlank(req.getParameter("routecode"))){
			routecode="";
		}else{
			routecode=req.getParameter("routecode");
		}
		
		sql="select b.hisname,a.routecode,a.routename from mc_dict_route a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.routematch_scheme and a.routecode like ? order by a.routecode asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+routecode+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.routecode) from mc_dict_route a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.routematch_scheme and a.routecode like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+routecode+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//频次字典表
	public DataGrid fre_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String frequency=null;
		if(StringUtils.isBlank(req.getParameter("frequency"))){
			frequency="";
		}else{
			frequency=req.getParameter("frequency");
		}
		
		sql="select b.hisname,a.frequency from mc_dict_frequency a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.freqmatch_scheme and a.frequency like ? order by a.frequency asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+frequency+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.frequency) from mc_dict_frequency a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.freqmatch_scheme and a.frequency like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+frequency+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//科室字典表
	public DataGrid dept_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String deptcode=null;
		if(StringUtils.isBlank(req.getParameter("deptcode"))){
			deptcode="";
		}else{
			deptcode=req.getParameter("deptcode");
		}
		
		sql="select b.hisname,a.deptcode,a.deptname from mc_dict_dept a,mc_hospital_match_relation b "
				+ "where a.match_scheme=b.deptmatch_scheme and a.deptcode<>'-1' and a.deptcode like ? order by a.deptcode asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+deptcode+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.deptcode) from mc_dict_dept a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.deptmatch_scheme and a.deptcode<>'-1' and a.deptcode like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+deptcode+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//医生字典表
	public DataGrid doc_dict(HttpServletRequest req){
		String limit=null;
		int page=0;
		int total=0;
		DataGrid datagrid=new DataGrid();
		page=Integer.parseInt(req.getParameter("page"));
		total=Integer.parseInt(req.getParameter("rows"));
		limit="limit "+(page*total-total)+","+total;
		
		String sql=null;
		List rslist=null;
		String doctorcode=null;
		if(StringUtils.isBlank(req.getParameter("doctorcode"))){
			doctorcode="";
		}else{
			doctorcode=req.getParameter("doctorcode");
		}
		
		sql="select b.hisname,a.doctorcode,a.doctorname from mc_dict_doctor a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.doctormatch_scheme and a.doctorcode <> '-1' "
				+ "and a.doctorcode like ? order by a.doctorcode asc "+limit;
		rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+doctorcode+"%"});
		datagrid.setRows(rslist);
		
		sql="select count(a.doctorcode) from mc_dict_doctor a,mc_hospital_match_relation b "
				+ " where a.match_scheme=b.doctormatch_scheme and a.doctorcode <> '-1' and a.doctorcode like ? ";
		total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+doctorcode+"%"});
		datagrid.setTotal(total+0L);
		
		return datagrid;
	}
	
	//医生字典表
		public DataGrid user_labitem(HttpServletRequest req){
			String limit=null;
			int page=0;
			int total=0;
			DataGrid datagrid=new DataGrid();
			page=Integer.parseInt(req.getParameter("page"));
			total=Integer.parseInt(req.getParameter("rows"));
			limit="limit "+(page*total-total)+","+total;
			
			String sql=null;
			List rslist=null;
			String itemcode=null;
			if(StringUtils.isBlank(req.getParameter("itemcode"))){
				itemcode="";
			}else{
				itemcode=req.getParameter("itemcode");
			}
			
			sql="select b.hisname,a.itemcode,a.itemname,a.labeldesc,a.`range`,a.unit,a.labeltypedesc from mc_user_labitem a, "
					+ "mc_hospital_match_relation b where a.mhiscode=b.mhiscode "
					+ "and a.itemcode like ? order by a.itemcode asc "+limit;
			rslist=jdbcTemplate_passpa2db.queryForList(sql, new Object[]{"%"+itemcode+"%"});
			datagrid.setRows(rslist);
			
			sql="select count(a.itemcode) from mc_user_labitem a, "
					+ "mc_hospital_match_relation b where a.mhiscode=b.mhiscode and a.itemcode like ? ";
			total=jdbcTemplate_passpa2db.queryForObject(sql, Integer.class, new Object[]{"%"+itemcode+"%"});
			datagrid.setTotal(total+0L);
			
			return datagrid;
		}
}
