package com.ch.sysuntils;

import org.springframework.stereotype.Service;

@Service
public class Strisnull {
	public String isnull(Object obj){
		if(obj==null || "".equals(obj)){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	public String isnulltostr(Object obj,String str){
		if(obj==null || "".equals(obj)){
			return str;
		}else{
			return obj.toString();
		}
	}
	
	public int isnulltoint_0(Object obj,String str){
		if(obj==null || "".equals(obj)){
			return 0;
		}else{
			return Integer.parseInt(obj.toString());
		}
	}
}
