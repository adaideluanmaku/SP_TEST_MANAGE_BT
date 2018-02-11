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
	
	public String isnullorstr(Object obj,String str){
		if(obj==null || "".equals(obj)){
			return str;
		}else{
			return obj.toString();
		}
	}
}
