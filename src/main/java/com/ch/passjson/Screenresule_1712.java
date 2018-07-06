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

@Service
public class Screenresule_1712 {
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
	
	//�����������Ϊxml��ʽ��ȡ�����CDATA���ݳ���
	public String Xmltojson(String jsoncdata) throws DocumentException{
		SAXReader reader=new SAXReader();
		Document document=reader.read(new StringReader(jsoncdata));
		Element root=document.getRootElement();
//		System.out.println(root.getText());
		String jsoncdata1=root.getText();
		return jsoncdata1;
	}

	public List screenres(String jsonwin,String jsonjava) throws DocumentException{
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
			JSONObject jsonin=JSONObject.fromObject(json);//
			JSONObject jsonin1=JSONObject.fromObject(json1);//
//			System.out.println(jsonin);
//			System.out.println(jsonin1);
			
			//�Ա�HighestSlcode�ڵ�
			if(!jsonin.get("HighestSlcode").equals(jsonin1.get("HighestSlcode"))&&moduleidstr==-1){
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���HighestSlcode��"+jsonin.get("HighestSlcode"));
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���HighestSlcode��"+jsonin1.get("HighestSlcode"));
				listerr.add("HighestSlcode=====>"+jsonin.get("HighestSlcode"));
				listerr.add("HighestSlcode=====>"+jsonin1.get("HighestSlcode"));
//				return "success";
			}
			
			//�Ա�InUseModules�ڵ�
			String win_InUseModule=jsonin.get("InUseModules").toString();
			String java_InUseModule=jsonin1.get("InUseModules").toString();
			
			if(!win_InUseModule.equals(java_InUseModule)){
				String[] win_InUseModules=win_InUseModule.split(";");
				String[] java_InUseModules=java_InUseModule.split(";");
				String[] notin_InUseModules={"22","23","24","25","26","27","28","29","30","31","32"};
				int win_inusesum=win_InUseModules.length;
				int java_inusesum=0;
				for(int i=0;i<win_InUseModules.length;i++){
					for(int i1=0;i1<notin_InUseModules.length;i1++){
						if(notin_InUseModules[i1].equals(win_InUseModules[i].substring(0, 2))){
							win_inusesum=win_inusesum-1;
							break;
						}
					}
					
					for(int i1=0;i1<java_InUseModules.length;i1++){
						if(win_InUseModules[i].substring(0, win_InUseModules[i].length()-1).equals(java_InUseModules[i1])){
							java_inusesum=java_inusesum+1;
							break;
						}
					}
				}
				
				if(win_inusesum!=java_inusesum){
//					System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���InUseModules��"+jsonin.get("InUseModules"));
//					System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���InUseModules��"+jsonin1.get("InUseModules"));
					listerr.add("InUseModules=====>"+jsonin.get("InUseModules"));
					listerr.add("InUseModules=====>"+jsonin1.get("InUseModules"));
//					return "success";
				}
			}
			
			//Delimiter
			if(!jsonin.get("Delimiter").equals(jsonin1.get("Delimiter"))&&moduleidstr==-1){
				listerr.add("Delimiter=====>"+jsonin.get("Delimiter"));
				listerr.add("Delimiter=====>"+jsonin1.get("Delimiter"));
//				return "success";
			}
			
			//MustUseReason
			if(!jsonin.get("MustUseReason").equals(jsonin1.get("MustUseReason"))&&moduleidstr==-1){
				listerr.add("MustUseReason=====>"+jsonin.get("MustUseReason"));
				listerr.add("MustUseReason=====>"+jsonin1.get("MustUseReason"));
//				return "success";
			}
			
			//IsNewWarning
			if(!jsonin.get("IsNewWarning").equals(jsonin1.get("IsNewWarning"))&&moduleidstr==-1){
				listerr.add("IsNewWarning=====>"+jsonin.get("IsNewWarning"));
				listerr.add("IsNewWarning=====>"+jsonin1.get("IsNewWarning"));
//				return "success";
			}
			
			//�Ա�ScreenResultDrugs�ڵ㣬���ҩƷ������ͬ���˳�
			JSONArray ScreenResultDrugs=jsonin.getJSONArray("ScreenResultDrugs");
			JSONArray ScreenResultDrugs1=jsonin1.getJSONArray("ScreenResultDrugs");
			if(ScreenResultDrugs.size()!=ScreenResultDrugs1.size()){
				List drugnames=new ArrayList();
				for(int namesum=0;namesum<ScreenResultDrugs.size();namesum++){
					JSONObject obj=ScreenResultDrugs.getJSONObject(namesum);
					String name=obj.getString("DrugIndex");
					drugnames.add(name);
				}
				List drugnames1=new ArrayList();
				for(int namesum1=0;namesum1<ScreenResultDrugs1.size();namesum1++){
					JSONObject obj1=ScreenResultDrugs1.getJSONObject(namesum1);
					String name1=obj1.getString("DrugIndex");
					drugnames1.add(name1);
				}
				listerr.add("ScreenResultDrugs=====>"+ScreenResultDrugs.size()+":"+drugnames);
				listerr.add("ScreenResultDrugs=====>"+ScreenResultDrugs1.size()+":"+drugnames1);
				if(listerr.size()!=0){
					listerrsum=1;
				}
				setListerr1(listerr);
				Date data=new Date();
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				System.out.println("IP:"+ip+"��"+sf.format(data));
				return listerr1;
			}
			
			//ScreenResults
			for(int i=0;i<ScreenResultDrugs.size();i++){
				JSONObject ScreenResultDrugsn=ScreenResultDrugs.getJSONObject(i);//Ajson,ScreenResultDrugs
				JSONArray ScreenResultsdan=ScreenResultDrugsn.getJSONArray("ScreenResults");//
				
				int DrugIndexstate=0;//
				for(int j=0;j<ScreenResultDrugs1.size();j++){
					JSONObject ScreenResultDrugsn1=ScreenResultDrugs1.getJSONObject(j);//Bjson,ScreenResultDrugs
					if(ScreenResultDrugsn.get("DrugIndex").equals(ScreenResultDrugsn1.get("DrugIndex"))
							 && ScreenResultDrugsn.get("DrugName").equals(ScreenResultDrugsn1.get("DrugName"))){
						DrugIndexstate=1;
						
						//
//						if(!ScreenResultDrugsn.get("DrugName").equals(ScreenResultDrugsn1.get("DrugName"))){
//							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugName:"+ScreenResultDrugsn.get("DrugName"));
//							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugName:"+ScreenResultDrugsn1.get("DrugName"));
//						}
						
						JSONArray ScreenResultsdan1=ScreenResultDrugsn1.getJSONArray("ScreenResults");//					
						//Slcode
						if(moduleidstr==-1||(ScreenResultsdan.size()==0&&ScreenResultsdan1.size()==0)){//
							if(!ScreenResultDrugsn.get("Slcode").equals(ScreenResultDrugsn1.get("Slcode"))){
								listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode:"+ScreenResultDrugsn.get("Slcode"));
								listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode:"+ScreenResultDrugsn1.get("Slcode"));
							}
						}
						
						//MenuLabel
						if(!ScreenResultDrugsn.get("MenuLabel").equals(ScreenResultDrugsn1.get("MenuLabel"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--MenuLabel:"+ScreenResultDrugsn.get("MenuLabel"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--MenuLabel:"+ScreenResultDrugsn1.get("MenuLabel"));
						}
						
						//WarnType
						if(!ScreenResultDrugsn.get("WarnType").equals(ScreenResultDrugsn1.get("WarnType"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--WarnType:"+ScreenResultDrugsn.get("WarnType"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--WarnType:"+ScreenResultDrugsn1.get("WarnType"));
						}
						
						//SubWarnType
						if(!ScreenResultDrugsn.get("SubWarnType").equals(ScreenResultDrugsn1.get("SubWarnType"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--SubWarnType:"+ScreenResultDrugsn.get("SubWarnType"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--SubWarnType:"+ScreenResultDrugsn1.get("SubWarnType"));
						}
						
						//IsNewWarning
						if(!ScreenResultDrugsn.get("IsNewWarning").equals(ScreenResultDrugsn1.get("IsNewWarning"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--IsNewWarning:"+ScreenResultDrugsn.get("IsNewWarning"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--IsNewWarning:"+ScreenResultDrugsn1.get("IsNewWarning"));
						}
						
						//DrugUniqueCode
						if(!ScreenResultDrugsn.get("DrugUniqueCode").equals(ScreenResultDrugsn1.get("DrugUniqueCode"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugUniqueCode:"+ScreenResultDrugsn.get("DrugUniqueCode"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugUniqueCode:"+ScreenResultDrugsn1.get("DrugUniqueCode"));
						}
						
						//DrugSource
						if(!ScreenResultDrugsn.get("DrugSource").equals(ScreenResultDrugsn1.get("DrugSource"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugSource:"+ScreenResultDrugsn.get("DrugSource"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugSource:"+ScreenResultDrugsn1.get("DrugSource"));
						}
						
						//RouteCode
						if(!ScreenResultDrugsn.get("RouteCode").equals(ScreenResultDrugsn1.get("RouteCode"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RouteCode:"+ScreenResultDrugsn.get("RouteCode"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RouteCode:"+ScreenResultDrugsn1.get("RouteCode"));
						}
						
						//RouteName
						if(!ScreenResultDrugsn.get("RouteName").equals(ScreenResultDrugsn1.get("RouteName"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RouteName:"+ScreenResultDrugsn.get("RouteName"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RouteName:"+ScreenResultDrugsn1.get("RouteName"));
						}
						
						//RecomUsage
						if(!ScreenResultDrugsn.get("RecomUsage").equals(ScreenResultDrugsn1.get("RecomUsage"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RecomUsage:"+ScreenResultDrugsn.get("RecomUsage"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RecomUsage:"+ScreenResultDrugsn1.get("RecomUsage"));
						}
						
						//RecomReason
						if(!ScreenResultDrugsn.get("RecomReason").equals(ScreenResultDrugsn1.get("RecomReason"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RecomReason:"+ScreenResultDrugsn.get("RecomReason"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--RecomReason:"+ScreenResultDrugsn1.get("RecomReason"));
						}
						
						//ImportantLabItems
						if(!ScreenResultDrugsn.get("ImportantLabItems").equals(ScreenResultDrugsn1.get("ImportantLabItems"))){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--ImportantLabItems:"+ScreenResultDrugsn.get("ImportantLabItems"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--ImportantLabItems:"+ScreenResultDrugsn1.get("ImportantLabItems"));
						}
						
						//ScreenResults
//						JSONArray ScreenResults=ScreenResultDrugsn.getJSONArray("ScreenResults");
//						JSONArray ScreenResults1=ScreenResultDrugsn1.getJSONArray("ScreenResults");
						
						//
						List modulnames=new ArrayList();						
						for(int namesum=0;namesum<ScreenResultsdan.size();namesum++){
							JSONObject obj=ScreenResultsdan.getJSONObject(namesum);
							String name=null;
							if(moduleidstr==Integer.parseInt(obj.get("ModuleID").toString())||moduleidstr==-1){
								name=obj.getString("ModuleID")+":"+obj.getString("ModuleName");
								modulnames.add(name);
							}
						}
						//
						List modulnames1=new ArrayList();
						for(int namesum1=0;namesum1<ScreenResultsdan1.size();namesum1++){
							JSONObject obj1=ScreenResultsdan1.getJSONObject(namesum1);
							String name1=null;
							if(moduleidstr==obj1.getInt("ModuleID")||moduleidstr==-1){
								name1=obj1.getString("ModuleID")+":"+obj1.getString("ModuleName");
								modulnames1.add(name1);
							}
						}
						
						//
						if(modulnames.size()!=modulnames1.size()){
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--ScreenResults:"+modulnames.size()+":"+modulnames);
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--ScreenResults:"+modulnames1.size()+":"+modulnames1);
							
							//
							if(modulnames.size()!=0&&modulnames1.size()!=0){
								Screenresule_1712_two jsonshuchu1=new Screenresule_1712_two();
								jsonshuchu1.setJson(ScreenResultsdan);
								jsonshuchu1.setJson1(ScreenResultsdan1);
								jsonshuchu1.setModuleidstr(moduleidstr);
								jsonshuchu1.setDrugname(ScreenResultDrugsn.get("DrugIndex").toString());
								jsonshuchu1.Jsonrequest();
								List listerr1=jsonshuchu1.getListerr1();
								for(int i1=0;i1<listerr1.size();i1++){
									listerr.add(listerr1.get(i1));
								}
							}
							break;//
						}
						
						//
						List klist=new ArrayList();//
						for(int z=0;z<ScreenResultsdan.size();z++){
							JSONObject ScreenResult=ScreenResultsdan.getJSONObject(z);//Ajson,ScreenResults
							
							//
							if(moduleidstr==Integer.parseInt(ScreenResult.get("ModuleID").toString())||moduleidstr==-1){
								int sum=0;//
								String err=null;
								String err1=null;
								int ksum=0;//
								for(int k=0;k<ScreenResultsdan1.size();k++){
									//
									int kstate=0;//
									for(int k1=0;k1<klist.size();k1++){
										if(k==Integer.parseInt(klist.get(k1).toString())){
											kstate=1;//
										}
									}
									
									//
									if(kstate==0){
										int sum1=0;
										JSONObject ScreenResult1=ScreenResultsdan1.getJSONObject(k);//Bjson,ScreenResults
										
										//
										if(moduleidstr==Integer.parseInt(ScreenResult.get("ModuleID").toString())||moduleidstr==-1){
											
											//
											if(ScreenResult.get("ModuleID").equals(ScreenResult1.get("ModuleID"))){
												sum1=sum1+1;
											}
											
											//
											if(ScreenResult.get("Slcode").equals(ScreenResult1.get("Slcode"))){
												sum1=sum1+1;
											}
											
											//
											if(ScreenResult.get("Warning").equals(ScreenResult1.get("Warning"))){
												sum1=sum1+1;
											}
											
											//
											for(int o=0;o<ScreenResult.size();o++){
												String name=ScreenResult.names().getString(o);
												if(ScreenResult.get(name).equals(ScreenResult1.get(name))){
													sum1=sum1+1;
												}
											}
											
											//
											if(sum1>sum){
												sum=sum1;
												ksum=k;//
												err=ScreenResult.toString();
												err1=ScreenResult1.toString();
											}
										}
									}
								}
								
								//
								klist.add(ksum);
								
								//
								if(sum<(ScreenResult.names().size()+3)&&sum>0){
									JSONObject errobj=JSONObject.fromObject(err);
									JSONObject errobj1=JSONObject.fromObject(err1);
									List errnamelist=new ArrayList();
									List errnamelist1=new ArrayList();
									//排除正确的节点，保留断言里面不同的节点
									for(int errn=0;errn<errobj.size();errn++){
										String errname=errobj.names().getString(errn);
										if(!errobj.get(errname).equals(errobj1.get(errname))){
											errnamelist.add(errname);
										}else{
											errobj1.remove(errname);
										}
									}
									//排除正确的节点，保留响应里面不同的节点
									for(int errn=0;errn<errobj1.size();errn++){
										String errname=errobj1.names().getString(errn);
										errnamelist1.add(errname);
									}
									
									listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+":"+ScreenResultDrugsn.get("DrugName")+",ScreenResults :"+errnamelist+"   ===Look Down===");
									listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+":"+ScreenResultDrugsn.get("DrugName")+",ScreenResults-error :"+errnamelist1);
									listerr.add("----error:"+err);
									listerr.add("----error:"+err1);
								}
							}
						}
					}
					
					//
					if(DrugIndexstate==0&&(j+1)==ScreenResultDrugs1.size()){
						listerr.add("DrugIndex,DrugName=====>"+ScreenResultDrugsn.get("DrugIndex")+","+ScreenResultDrugsn.get("DrugName"));
						listerr.add("DrugIndex,DrugName=====>缺少节点数据");
					}
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
			System.out.println("json-err");
			listerr.add("json-err");
			setListerr1(listerr);
			Date data=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return listerr1;
		}
	}
}
