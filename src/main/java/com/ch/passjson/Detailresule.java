package com.ch.passjson;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//PASS
@Service
public class Detailresule {
	private String json=null;
	private String json1=null;
	private List listerr1;
	private int listerrsum=0;
	private int moduleidstr=-1;
	private int duibistate=1;
	private String ip;
	
	public int getDuibistate() {
		return duibistate;
	}

	public void setDuibistate(int duibistate) {
		this.duibistate = duibistate;
	}

	public int getModuleidstr() {
		return moduleidstr;
	}

	public void setModuleidstr(int moduleidstr) {
		this.moduleidstr = moduleidstr;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getJson1() {
		return json1;
	}

	public void setJson1(String json1) {
		this.json1 = json1;
	}

	public List getListerr1() {
		return listerr1;
	}

	public void setListerr1(List listerr) {
		this.listerr1 = listerr;
	}

	public int getListerrsum() {
		return listerrsum;
	}

	public void setListerrsum(int listerrsum) {
		this.listerrsum = listerrsum;
	}
	
	public List detail(String jsonwin,String jsonjava) throws DocumentException{
		json=jsonwin;
		json1=jsonjava;
		List listerr=new ArrayList();
		if("".equals(json)||"".equals(json1)){
			listerrsum=1;
//			System.out.println("��ϸ��Ϣ�������벻����");
			listerr.add("json is null");
			setListerr1(listerr);
			return listerr1;
		}
		
		Xmltojson Xmltojson=new Xmltojson();
		if(json.contains("<?xml")){
			json=Xmltojson.getjson(json);
		}
		if(json1.contains("<?xml")){
			json1=Xmltojson.getjson(json1);
		}
		
		try{//json
			JSONObject jsonin=JSONObject.fromObject(json);
			JSONObject jsonin1=JSONObject.fromObject(json1);
//			System.out.println(jsonin);
//			System.out.println(jsonin1);
			
			if(!jsonin.getString("Delimiter").equals(jsonin1.getString("Delimiter"))){
//				System.out.println("Delimiter"+":"+jsonin.getString("Delimiter"));
//				System.out.println("Delimiter"+":"+jsonin1.getString("Delimiter"));
				listerr.add("Assertion: Delimiter==>"+jsonin.getString("Delimiter"));
				listerr.add("Response: Delimiter==>"+jsonin1.getString("Delimiter"));
			}
			
			if(!jsonin.getString("SubDelimiter").equals(jsonin1.getString("SubDelimiter"))){
//				System.out.println("SubDelimiter"+":"+jsonin.getString("SubDelimiter"));
//				System.out.println("SubDelimiter"+":"+jsonin1.getString("SubDelimiter"));
				listerr.add("Assertion: SubDelimiter==>"+jsonin.getString("SubDelimiter"));
				listerr.add("Response: SubDelimiter==>"+jsonin1.getString("SubDelimiter"));
			}
			
			if(!jsonin.getString("DetailType").equals(jsonin1.getString("DetailType"))){
//				System.out.println("DetailType"+":"+jsonin.getString("DetailType"));
//				System.out.println("DetailType"+":"+jsonin1.getString("DetailType"));
				listerr.add("Assertion: DetailType==>"+jsonin.getString("DetailType"));
				listerr.add("Response: DetailType==>"+jsonin1.getString("DetailType"));
			}
			
			if(!jsonin.getString("Articletitle").equals(jsonin1.getString("Articletitle"))){
//				System.out.println("Articletitle"+":"+jsonin.getString("Articletitle"));
//				System.out.println("Articletitle"+":"+jsonin1.getString("Articletitle"));
				listerr.add("Assertion: Articletitle==>"+jsonin.getString("Articletitle"));
				listerr.add("Response: Articletitle==>"+jsonin1.getString("Articletitle"));
			}
			
			if(!jsonin.getString("Abstract").equals(jsonin1.getString("Abstract"))){
//				System.out.println("Abstract:"+jsonin.getString("Abstract"));
//				System.out.println("Abstract:"+jsonin1.getString("Abstract"));
				listerr.add("Assertion: Abstract==>"+jsonin.getString("Abstract"));
				listerr.add("Response: Abstract==>"+jsonin1.getString("Abstract"));
			}
			
//			JSONArray names=jsonin.names();
//			for(int i=0;i<names.size();i++){
//				String name=names.getString(i);
//				if(!jsonin.getString(name).equals(jsonin1.getString(name))&&!"DetailType".equals(name)){
//					System.out.println("���ԣ�"+name+":"+jsonin.getString(name));
//					System.out.println("��Ӧ��"+name+":"+jsonin1.getString(name));
//					listerr.add("���ԣ�"+name+":"+jsonin.getString(name));
//					listerr.add("��Ӧ��"+name+":"+jsonin1.getString(name));
//				}
//			}
			
			JSONArray DetailResults=jsonin.getJSONArray("DetailResults");
			JSONArray DetailResults1=jsonin1.getJSONArray("DetailResults");
			
			if(DetailResults.size()==0 || DetailResults1.size()==0){
				setListerr1(listerr);
				return  listerr1;
			}
			
			if(DetailResults.size()!=0 && DetailResults1.size()!=0){
				if(DetailResults.size()!=DetailResults1.size()){
					listerr.add("Assertion: DetailResults==>"+DetailResults.size());
					listerr.add("Response: DetailResults==>"+DetailResults1.size());
				}
				
				for(int i=0;i<DetailResults.size();i++){
					int sum=0;
					String DetailResult=DetailResults.getString(i);
					String DetailResult1;
					for(int i1=0;i1<DetailResults1.size();i1++){
						DetailResult1=DetailResults1.getString(i1);
						if(DetailResult.equals(DetailResult1)){
							DetailResults.remove(i);
							DetailResults1.remove(i1);
							i=i-1;
							break;
						}
					}
				}
				
				if(DetailResults.size()>0 && DetailResults1.size()==0){
					listerr.add("Assertion: DetailResults==>"+DetailResults);
					listerr.add("Response: DetailResults==>"+DetailResults1);
				}
				if(DetailResults.size()==0 && DetailResults1.size()>0){
					listerr.add("Assertion: DetailResults==>"+DetailResults);
					listerr.add("Response: DetailResults==>"+DetailResults1);
				}
				if(DetailResults.size()>0 && DetailResults1.size()>0){
					listerr.add("Assertion: DetailResults==>"+DetailResults);
					listerr.add("Response: DetailResults==>"+DetailResults1);
				}
			}
			
			if(listerr.size()!=0){
				listerrsum=1;
			}
			setListerr1(listerr);
			return  listerr1;
		}catch(Exception ex){
			listerrsum=1;
//			System.out.println("json error");
			listerr.add("json error");
			setListerr1(listerr);
			return listerr1;
		}
		
	}
}
