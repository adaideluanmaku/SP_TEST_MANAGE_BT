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

//PASS��ѯ����Ա�
@Service
public class Queryresule {
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
	
	public List query(String jsonwin,String jsonjava) throws DocumentException{
		json=jsonwin.trim();
		json1=jsonjava.trim();
		List listerr=new ArrayList();
		if("".equals(json)||"".equals(json1)){
			listerrsum=1;
			System.out.println("json is not null");
			listerr.add("json is not null");
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
		
		try{
			JSONObject jsonin=JSONObject.fromObject(json);
			JSONObject jsonin1=JSONObject.fromObject(json1);
			System.out.println(jsonin);
			System.out.println(jsonin1);
			
			if(!jsonin.getString("Delimiter").equals(jsonin1.getString("Delimiter"))){
				System.out.println("--Delimiter"+":  "+jsonin.getString("Delimiter"));
				System.out.println("--Delimiter"+":  "+jsonin1.getString("Delimiter"));
				listerr.add("--Delimiter"+":  "+jsonin.getString("Delimiter"));
				listerr.add("--Delimiter"+":  "+jsonin1.getString("Delimiter"));
			}
			
			if(!jsonin.getString("SubDelimiter").equals(jsonin1.getString("SubDelimiter"))){
				System.out.println("--SubDelimiter"+":  "+jsonin.getString("SubDelimiter"));
				System.out.println("--SubDelimiter"+":  "+jsonin1.getString("SubDelimiter"));
				listerr.add("--SubDelimiter"+":  "+jsonin.getString("SubDelimiter"));
				listerr.add("--SubDelimiter"+":  "+jsonin1.getString("SubDelimiter"));
			}
			
			if(!jsonin.getString("Slcode").equals(jsonin1.getString("Slcode"))){
				System.out.println("--Slcode"+":  "+jsonin.getString("Slcode"));
				System.out.println("--Slcode"+":  "+jsonin1.getString("Slcode"));
				listerr.add("--Slcode"+":  "+jsonin.getString("Slcode"));
				listerr.add("--Slcode"+":  "+jsonin1.getString("Slcode"));
			}
			
			if(!jsonin.getString("DisplayMode").equals(jsonin1.getString("DisplayMode"))){
				System.out.println("--DisplayMode"+":  "+jsonin.getString("DisplayMode"));
				System.out.println("--DisplayMode"+":  "+jsonin1.getString("DisplayMode"));
				listerr.add("--DisplayMode"+":  "+jsonin.getString("DisplayMode"));
				listerr.add("--DisplayMode"+":  "+jsonin1.getString("DisplayMode"));
			}
			
			if(!jsonin.getString("ReferenceType").equals(jsonin1.getString("ReferenceType"))){
				System.out.println("--ReferenceType"+":  "+jsonin.getString("ReferenceType"));
				System.out.println("--ReferenceType"+":  "+jsonin1.getString("ReferenceType"));
				listerr.add("--ReferenceType"+":  "+jsonin.getString("ReferenceType"));
				listerr.add("--ReferenceType"+":  "+jsonin1.getString("ReferenceType"));
			}
			
			if(!jsonin.getString("ReferenceTip").equals(jsonin1.getString("ReferenceTip"))){
				System.out.println("--ReferenceTip"+":  "+jsonin.getString("ReferenceTip"));
				System.out.println("--ReferenceTip"+":  "+jsonin1.getString("ReferenceTip"));
				listerr.add("--ReferenceTip"+":  "+jsonin.getString("ReferenceTip"));
				listerr.add("--ReferenceTip"+":  "+jsonin1.getString("ReferenceTip"));
			}
			
			if(!jsonin.getString("ResultMode").equals(jsonin1.getString("ResultMode"))){
				System.out.println("--ResultMode"+":  "+jsonin.getString("ResultMode"));
				System.out.println("--ResultMode"+":  "+jsonin1.getString("ResultMode"));
				listerr.add("--ResultMode"+":  "+jsonin.getString("ResultMode"));
				listerr.add("--ResultMode"+":  "+jsonin1.getString("ResultMode"));
			}
			
			if(!jsonin.getString("Title").equals(jsonin1.getString("Title"))){
				System.out.println("--Title"+":  "+jsonin.getString("Title"));
				System.out.println("--Title"+":  "+jsonin1.getString("Title"));
				listerr.add("--Title"+":  "+jsonin.getString("Title"));
				listerr.add("--Title"+":  "+jsonin1.getString("Title"));
			}
			
//			JSONArray names=jsonin.names();
//			for(int i=0;i<names.size();i++){
//				String name=names.getString(i);
//				if(!jsonin.getString(name).equals(jsonin1.getString(name))&&!"DisplayMode".equals(name)&&!"ReferenceResults".equals(name)){
//					System.out.println("���ԣ�--"+name+":  "+jsonin.getString(name));
//					System.out.println("��Ӧ��--"+name+":  "+jsonin1.getString(name));
//					listerr.add("���ԣ�--"+name+":  "+jsonin.getString(name));
//					listerr.add("��Ӧ��--"+name+":  "+jsonin1.getString(name));
//				}
//			}
			
			JSONArray ReferenceResults=jsonin.getJSONArray("ReferenceResults");
			JSONArray ReferenceResults1=jsonin1.getJSONArray("ReferenceResults");
			
			if((ReferenceResults.size()==0||ReferenceResults1.size()==0)&&ReferenceResults.size()!=ReferenceResults1.size()){
				System.out.println("--ReferenceResults:  "+ReferenceResults.size());
				System.out.println("--ReferenceResults:  "+ReferenceResults1.size());
				listerr.add("--ReferenceResults:  "+ReferenceResults.size());
				listerr.add("--ReferenceResults:  "+ReferenceResults1.size());
				listerrsum=1;
				setListerr1(listerr);
				return listerr1;
			}
			
			if(ReferenceResults.size()!=0&&ReferenceResults1.size()!=0&&ReferenceResults.size()==ReferenceResults1.size()){
				for(int i=0;i<ReferenceResults.size();i++){
					JSONObject ReferenceResultsn=ReferenceResults.getJSONObject(i);
					JSONObject ReferenceResultsn1=ReferenceResults1.getJSONObject(i);
					
					JSONArray ReferenceResultsnnames=ReferenceResultsn.names();
					for(int j=0;j<ReferenceResultsnnames.size();j++){
						String ReferenceResultsnname=ReferenceResultsnnames.getString(j);
						if(!ReferenceResultsn.get(ReferenceResultsnname).equals(ReferenceResultsn1.get(ReferenceResultsnname))&&!ReferenceResultsnname.equals("BriefItems")){
							System.out.println("--"+ReferenceResultsnname+":  "+ReferenceResultsn.getString(ReferenceResultsnname));
							System.out.println("--"+ReferenceResultsnname+":  "+ReferenceResultsn1.getString(ReferenceResultsnname));
							listerr.add("--"+ReferenceResultsnname+":  "+ReferenceResultsn.getString(ReferenceResultsnname));
							listerr.add("--"+ReferenceResultsnname+":  "+ReferenceResultsn1.getString(ReferenceResultsnname));
						}
					}
					
					if(!ReferenceResultsn.get("BriefItems").equals(ReferenceResultsn1.get("BriefItems"))){
						System.out.println("--BriefItems:  "+ReferenceResultsn.getString("BriefItems"));
						System.out.println("--BriefItems:  "+ReferenceResultsn1.getString("BriefItems"));
						listerr.add("--BriefItems:  "+ReferenceResultsn.getString("BriefItems"));
						listerr.add("--BriefItems:  "+ReferenceResultsn1.getString("BriefItems"));
					}
				}
			}
			
			if(listerr.size()!=0){
				listerrsum=1;
			}
			setListerr1(listerr);
			return listerr1;
		}catch(Exception ex){
			listerrsum=1;
			System.out.println("json error");
			listerr.add("json error");
			setListerr1(listerr);
			return listerr1;
		}
	}
}
