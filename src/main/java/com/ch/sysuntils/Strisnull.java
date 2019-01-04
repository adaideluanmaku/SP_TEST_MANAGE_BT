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
	
	public Object isnulltoobj(Object obj,String str){
		if(obj==null || "".equals(obj)){
			if(obj instanceof Integer){
				return Integer.parseInt(str);
			}else if(obj instanceof Double){
				return Double.parseDouble(str);
			}else{
				return str;
			}
		}else{
			if(obj instanceof Integer){
				return Integer.parseInt(obj.toString());
			}else if(obj instanceof Double){
				return Double.parseDouble(obj.toString());
			}else{
				return obj.toString();
			}
		}
	}
	
	public int isnulltoint_0(Object obj,int a){
		if(obj==null || "".equals(obj)){
			return a;
		}else{
			return Integer.parseInt(obj.toString());
		}
	}
	
	public Double isnulltodouble_00(Object obj,Double da){
		if(obj==null || "".equals(obj)){
			return da;
			
		}else{
			return Double.parseDouble(obj.toString());
		}
	}
}
