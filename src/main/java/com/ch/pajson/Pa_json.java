package com.ch.pajson;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
public class Pa_json {
	public String Jsonduibi(String json_java1,String json_win1){
		String json_java2=json_java1.trim();
		String json_win2=json_win1.trim();
		
		List errlist=new ArrayList();
		String errstr="";
		String result="";
		
		if(json_java2.equals(json_win2)){
			return result;
		}
		
		if("[]".equals(json_java2) && !"[]".equals(json_win2)){
			result=result+"<div style='color:red'>断言：-->"+json_win2+"</div><br>"
					+"<div style='color:blue'>响应：缺少断言对应的结果</div><br>";
			return result;
		}
		
		if("[]".equals(json_win2) && !"[]".equals(json_java2)){
			result=result+"<div style='color:red'>断言：不存在该结果</div><br>"
					+"<div style='color:blue'>响应：-->"+json_java2+"</div><br>";
			return result;
		}
		
		try{
			JSONArray json_wins =JSONArray.fromObject(json_win2);
			JSONArray json_javas =JSONArray.fromObject(json_java2);
			
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
}
