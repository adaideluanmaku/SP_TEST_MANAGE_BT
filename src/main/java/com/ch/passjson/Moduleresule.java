package com.ch.passjson;

import java.io.StringReader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


//pass
@Service
public class Moduleresule {
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
	
	public List module(String jsonwin,String jsonjava) throws DocumentException{
		List listerr=new ArrayList();
		json=jsonwin;
		json1=jsonjava;
		
		Xmltojson Xmltojson=new Xmltojson();
		if(json.contains("<?xml")){
			json=Xmltojson.getjson(json);
		}
		if(json1.contains("<?xml")){
			json1=Xmltojson.getjson(json1);
		}
//		System.out.println(json);
//		System.out.println(json1);
		try{//
			JSONObject jsonin=JSONObject.fromObject(json);//��ȷ��json
			JSONObject jsonin1=JSONObject.fromObject(json1);//��Ҫ��֤��json
//			System.out.println(jsonin);
//			System.out.println(jsonin1);
			
			//ProductCode
			if(!jsonin.get("ProductCode").equals(jsonin1.get("ProductCode"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: ProductCode==>"+jsonin.get("ProductCode"));
				listerr.add("Response: ProductCode==>"+jsonin1.get("ProductCode"));
//				return "success";
			}
			
			if(!jsonin.get("DataVersionReg").equals(jsonin1.get("DataVersionReg"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: DataVersionReg==>"+jsonin.get("DataVersionReg"));
				listerr.add("Response: DataVersionReg==>"+jsonin1.get("DataVersionReg"));
//				return "success";
			}
			
			if(!jsonin.get("UserName").equals(jsonin1.get("UserName"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: UserName==>"+jsonin.get("UserName"));
				listerr.add("Response: UserName==>"+jsonin1.get("UserName"));
//				return "success";
			}
			
			if(!jsonin.get("ProjectVersion").equals(jsonin1.get("ProjectVersion"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: ProjectVersion==>"+jsonin.get("ProjectVersion"));
				listerr.add("Response: ProjectVersion==>"+jsonin1.get("ProjectVersion"));
//				return "success";
			}
			
			if(!jsonin.get("DataVersion").equals(jsonin1.get("DataVersion"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: DataVersion==>"+jsonin.get("DataVersion"));
				listerr.add("Response: DataVersion==>"+jsonin1.get("DataVersion"));
//				return "success";
			}
				
			if(!jsonin.get("HasIM").equals(jsonin1.get("HasIM"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: HasIM==>"+jsonin.get("HasIM"));
				listerr.add("Response: HasIM==>"+jsonin1.get("HasIM"));
//				return "success";
			}
			
			if(!jsonin.get("HasPASS").equals(jsonin1.get("HasPASS"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: HasPASS==>"+jsonin.get("HasPASS"));
				listerr.add("Response: HasPASS==>"+jsonin1.get("HasPASS"));
//				return "success";
			}
			
			if(!jsonin.get("HasPharm").equals(jsonin1.get("HasPharm"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: HasPharm==>"+jsonin.get("HasPharm"));
				listerr.add("Response: HasPharm==>"+jsonin1.get("HasPharm"));
//				return "success";
			}	
			
			if(!jsonin.get("HasRHC").equals(jsonin1.get("HasRHC"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: HasRHC==>"+jsonin.get("HasRHC"));
				listerr.add("Response: HasRHC==>"+jsonin1.get("HasRHC"));
//				return "success";
			}
			
			if(!jsonin.get("RHCName").equals(jsonin1.get("RHCName"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: RHCName==>"+jsonin.get("RHCName"));
				listerr.add("Response: RHCName==>"+jsonin1.get("RHCName"));
//				return "success";
			}
			
			if(!jsonin.get("RHC").equals(jsonin1.get("RHC"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: RHC==>"+jsonin.get("RHC"));
				listerr.add("Response: RHC==>"+jsonin1.get("RHC"));
//				return "success";
			}
			
			if(!jsonin.get("CanGetScreenDtlRes").equals(jsonin1.get("CanGetScreenDtlRes"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: CanGetScreenDtlRes==>"+jsonin.get("CanGetScreenDtlRes"));
				listerr.add("Response: CanGetScreenDtlRes==>"+jsonin1.get("CanGetScreenDtlRes"));
//				return "success";
			}
			
			if(!jsonin.get("ForCloud").equals(jsonin1.get("ForCloud"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: ForCloud==>"+jsonin.get("ForCloud"));
				listerr.add("Response: ForCloud==>"+jsonin1.get("ForCloud"));
//				return "success";
			}
			
			
			if(!jsonin.get("ReceptClients").equals(jsonin1.get("ReceptClients"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: ReceptClients==>"+jsonin.get("ReceptClients"));
				listerr.add("Response: ReceptClients==>"+jsonin1.get("ReceptClients"));
//				return "success";
			}
			
			//PASS-JAVA��һ���ڵ�
//			if(!jsonin.get("hiscode_invoke").equals(jsonin1.get("hiscode_invoke"))){
////				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
////				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
//				listerr.add("���ԣ�һ��Ŀ¼��ȷ�Ľڵ��ǣ�hiscode_invoke��"+jsonin.get("hiscode_invoke"));
//				listerr.add("��Ӧ��һ��Ŀ¼����Ľڵ��ǣ�hiscode_invoke��"+jsonin1.get("hiscode_invoke"));
////				return "success";
//			}
			
			if(!jsonin.get("mhiscode").equals(jsonin1.get("mhiscode"))){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("Assertion: mhiscode==>"+jsonin.get("mhiscode"));
				listerr.add("Response: mhiscode==>"+jsonin1.get("mhiscode"));
//				return "success";
			}
			
			JSONArray ScreenModuleList=jsonin.getJSONArray("ScreenModuleList");
			JSONArray ScreenModuleList1=jsonin1.getJSONArray("ScreenModuleList");
			
			if(ScreenModuleList.size()==0 || ScreenModuleList1.size()==0){
				setListerr1(listerr);
				return  listerr1;
			}
			
			if(ScreenModuleList.size()!=0 && ScreenModuleList1.size()!=0){
				if(ScreenModuleList.size()!=ScreenModuleList1.size()){
					listerr.add("Assertion: ScreenModuleList==>"+ScreenModuleList.size());
					listerr.add("Response: ScreenModuleList==>"+ScreenModuleList1.size());
				}
				
				for(int i=0;i<ScreenModuleList.size();i++){
					int sum=0;
					String DetailResult=ScreenModuleList.getString(i);
					String DetailResult1;
					for(int i1=0;i1<ScreenModuleList1.size();i1++){
						DetailResult1=ScreenModuleList1.getString(i1);
						if(DetailResult.equals(DetailResult1)){
							ScreenModuleList.remove(i);
							ScreenModuleList1.remove(i1);
							i=i-1;
							break;
						}
					}
				}
				
				if(ScreenModuleList.size()>0 && ScreenModuleList1.size()==0){
					listerr.add("Assertion: ScreenModuleList==>"+ScreenModuleList);
					listerr.add("Response: ScreenModuleList==>"+ScreenModuleList1);
				}
				if(ScreenModuleList.size()==0 && ScreenModuleList1.size()>0){
					listerr.add("Assertion: ScreenModuleList==>"+ScreenModuleList);
					listerr.add("Response: ScreenModuleList==>"+ScreenModuleList1);
				}
				if(ScreenModuleList.size()>0 && ScreenModuleList1.size()>0){
					for(int i=0;i<ScreenModuleList.size();i++){
						JSONObject ScreenModule=ScreenModuleList.getJSONObject(i);
						JSONObject ScreenModule1;
						for(int i1=0;i1<ScreenModuleList1.size();i1++){
							ScreenModule1=ScreenModuleList1.getJSONObject(i1);
							if(ScreenModule.get("ModuleID")==ScreenModule1.get("ModuleID")){
								listerr.add("Assertion: ModuleID==>"+ScreenModule);
								listerr.add("Response: ModuleID==>"+ScreenModule1);
							}
						}
					}
				}
			}
//			
			JSONArray ReferenceModuleList=jsonin.getJSONArray("ReferenceModuleList");
			JSONArray ReferenceModuleList1=jsonin1.getJSONArray("ReferenceModuleList");
			
			if(ReferenceModuleList.size()==0 || ReferenceModuleList1.size()==0){
				setListerr1(listerr);
				return  listerr1;
			}
			
			if(ReferenceModuleList.size()!=0 && ReferenceModuleList1.size()!=0){
				if(ReferenceModuleList.size()!=ReferenceModuleList1.size()){
					listerr.add("Assertion: ReferenceModuleList==>"+ReferenceModuleList.size());
					listerr.add("Response: ReferenceModuleList==>"+ReferenceModuleList1.size());
				}
				
				for(int i=0;i<ReferenceModuleList.size();i++){
					int sum=0;
					String DetailResult=ReferenceModuleList.getString(i);
					String DetailResult1;
					for(int i1=0;i1<ReferenceModuleList1.size();i1++){
						DetailResult1=ReferenceModuleList1.getString(i1);
						if(DetailResult.equals(DetailResult1)){
							ReferenceModuleList.remove(i);
							ReferenceModuleList1.remove(i1);
							i=i-1;
							break;
						}
					}
				}
				
				if(ReferenceModuleList.size()>0 && ReferenceModuleList1.size()==0){
					listerr.add("Assertion: ReferenceModuleList==>"+ReferenceModuleList);
					listerr.add("Response: ReferenceModuleList==>"+ReferenceModuleList1);
				}
				if(ReferenceModuleList.size()==0 && ReferenceModuleList1.size()>0){
					listerr.add("Assertion: ReferenceModuleList==>"+ReferenceModuleList);
					listerr.add("Response: ReferenceModuleList==>"+ReferenceModuleList1);
				}
				if(ReferenceModuleList.size()>0 && ReferenceModuleList1.size()>0){
					listerr.add("Assertion: ReferenceModuleList==>"+ReferenceModuleList);
					listerr.add("Response: ReferenceModuleList==>"+ReferenceModuleList1);
				}
			}
			
			//
			if(listerr.size()!=0){
				listerrsum=1;
			}
			
			//
			setListerr1(listerr);
			Date data=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return listerr1;
		}catch(Exception ex){
			listerrsum=1;
//			System.out.println("json���ڵ����ƿ��ܴ�������");
			listerr.add("json error");
			setListerr1(listerr);
			Date data=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println("IP:"+ip+"��"+sf.format(data));
			return listerr1;
		}
	}
}
