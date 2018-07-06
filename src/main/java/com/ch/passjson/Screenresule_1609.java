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
public class Screenresule_1609 {
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
		try{//��ֹ�����json�����쳣
			JSONObject jsonin=JSONObject.fromObject(json);//��ȷ��json
			JSONObject jsonin1=JSONObject.fromObject(json1);//��Ҫ��֤��json
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
//				System.out.println("���ԣ�һ��Ŀ¼��ȷ�Ľڵ���ScreenResultDrugs��"+ScreenResultDrugs.size()+"��,ҩƷ����ǣ�"+drugnames);
//				System.out.println("��Ӧ��һ��Ŀ¼����Ľڵ���ScreenResultDrugs��"+ScreenResultDrugs1.size()+"��,ҩƷ����ǣ�"+drugnames1);
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
			
			//A��ѭ��ȡScreenResultDrugs�ڵ��ҩƷ
			for(int i=0;i<ScreenResultDrugs.size();i++){
				JSONObject ScreenResultDrugsn=ScreenResultDrugs.getJSONObject(i);//Ajson��һ��ScreenResultDrugs
				
				JSONArray ScreenResultsdan=ScreenResultDrugsn.getJSONArray("ScreenResults");//ȡ��A��ҩƷ�����������
//				int Slcodesum=100;
//				for(int i1=0;i1<ScreenResultsdan.size();i1++){//����ͬģ��ScreenResults����С��Slcode
//					JSONObject obj=ScreenResultsdan.getJSONObject(i1);
//					if(obj.getInt("Slcode")<Slcodesum&&(moduleidstr==obj.getInt("ModuleID"))){
//						Slcodesum=obj.getInt("Slcode");
//					}
//				}
				
				int DrugIndexstate=0;//��ʼ��״̬��Ĭ��B���е�ҩƷδ�ҵ�
				//B��ѭ��ȡScreenResultDrugs�ڵ��ҩƷ
				for(int j=0;j<ScreenResultDrugs1.size();j++){
					JSONObject ScreenResultDrugsn1=ScreenResultDrugs1.getJSONObject(j);//Bjson��һ��ScreenResultDrugs
					if(ScreenResultDrugsn.get("DrugIndex").equals(ScreenResultDrugsn1.get("DrugIndex"))){
						DrugIndexstate=1;
						
						//�Ա�DrugName�ڵ�
						if(!ScreenResultDrugsn.get("DrugName").equals(ScreenResultDrugsn1.get("DrugName"))){
//							System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--DrugName��"+ScreenResultDrugsn.get("DrugName"));
//							System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--DrugName��"+ScreenResultDrugsn1.get("DrugName"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugName:"+ScreenResultDrugsn.get("DrugName"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--DrugName:"+ScreenResultDrugsn1.get("DrugName"));
						}
						
						JSONArray ScreenResultsdan1=ScreenResultDrugsn1.getJSONArray("ScreenResults");//ȡ��B��ҩƷ�����������
//							int Slcodesum1=100;
//							for(int i1=0;i1<ScreenResultsdan1.size();i1++){//����ͬģ��ScreenResults����С��Slcode
//								JSONObject obj1=ScreenResultsdan1.getJSONObject(i1);
//								if(obj1.getInt("Slcode")<Slcodesum1&&(moduleidstr==obj1.getInt("ModuleID"))){
//									Slcodesum1=obj1.getInt("Slcode");
//								}
//							}
//							if(moduleidstr!=-1){//1
//								if(Slcodesum!=Slcodesum1&&Slcodesum!=100&&Slcodesum1!=100){//ʹ��AND�Ĺ�ϵ��Ϊ��Slcode��ͬʱ������ͺ����ظ���ʾ
//									System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+Slcodesum);
//									System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+Slcodesum1);
//									listerr.add("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+Slcodesum);
//									listerr.add("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+Slcodesum1);
//								}
//								if(!ScreenResultDrugsn.get("Slcode").equals(ScreenResultDrugsn1.get("Slcode"))&&ScreenResultsdan.size()==0&&ScreenResultsdan1.size()==0&&Slcodesum==100&&Slcodesum1==100){
//									System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn.get("Slcode"));
//									System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn1.get("Slcode"));
//									listerr.add("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn.get("Slcode"));
//									listerr.add("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn1.get("Slcode"));
//								}
//							}else{
//								if(!ScreenResultDrugsn.get("Slcode").equals(ScreenResultDrugsn1.get("Slcode"))){
//									System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn.get("Slcode"));
//									System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn1.get("Slcode"));
//									listerr.add("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn.get("Slcode"));
//									listerr.add("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn1.get("Slcode"));
//								}
//							}
						
						//�Ա�Slcode�ڵ�
						if(moduleidstr==-1||(ScreenResultsdan.size()==0&&ScreenResultsdan1.size()==0)){//���������Ϊ�յ�ʱ��ŶԱ�DrugIndex�µ�Slcode
							if(!ScreenResultDrugsn.get("Slcode").equals(ScreenResultDrugsn1.get("Slcode"))){
//								System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn.get("Slcode"));
//								System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode��"+ScreenResultDrugsn1.get("Slcode"));
								listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode:"+ScreenResultDrugsn.get("Slcode"));
								listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--Slcode:"+ScreenResultDrugsn1.get("Slcode"));
							}
						}
						
						//�Ա�MenuLabel�ڵ�
						if(!ScreenResultDrugsn.get("MenuLabel").equals(ScreenResultDrugsn1.get("MenuLabel"))){
//							System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--MenuLabel��"+ScreenResultDrugsn.get("MenuLabel"));
//							System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--MenuLabel��"+ScreenResultDrugsn1.get("MenuLabel"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--MenuLabel:"+ScreenResultDrugsn.get("MenuLabel"));
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--MenuLabel:"+ScreenResultDrugsn1.get("MenuLabel"));
						}
						
						//�Ա�AB��ScreenResults�ڵ���������ģ�������Ƿ���ͬ
						JSONArray ScreenResults=ScreenResultDrugsn.getJSONArray("ScreenResults");
						JSONArray ScreenResults1=ScreenResultDrugsn1.getJSONArray("ScreenResults");
						
						//����A�����������
						List modulnames=new ArrayList();						
						for(int namesum=0;namesum<ScreenResults.size();namesum++){
							JSONObject obj=ScreenResults.getJSONObject(namesum);
							String name=null;
							if(moduleidstr==Integer.parseInt(obj.get("ModuleID").toString())||moduleidstr==-1){
								name=obj.getString("ModuleID")+":"+obj.getString("ModuleName");
								modulnames.add(name);
							}
						}
						//����B�����������
						List modulnames1=new ArrayList();
						for(int namesum1=0;namesum1<ScreenResults1.size();namesum1++){
							JSONObject obj1=ScreenResults1.getJSONObject(namesum1);
							String name1=null;
							if(moduleidstr==obj1.getInt("ModuleID")||moduleidstr==-1){
								name1=obj1.getString("ModuleID")+":"+obj1.getString("ModuleName");
								modulnames1.add(name1);
							}
						}
						//�Ա�AB�����������
						if(modulnames.size()!=modulnames1.size()){
//							System.out.println("���ԣ�DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--ScreenResults��"+modulnames.size()+"��,ģ��ID�ǣ�"+modulnames);
//							System.out.println("��Ӧ��DrugIndexΪ"+ScreenResultDrugsn.get("DrugIndex")+"--ScreenResults��"+modulnames1.size()+"��,ģ��ID�ǣ�"+modulnames1);
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--ScreenResults:"+modulnames.size()+":"+modulnames);
							listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex")+"--ScreenResults:"+modulnames1.size()+":"+modulnames1);
							
							//����Jsonshuchu1�����ж������������ͬ�ľ��������
							if(modulnames.size()!=0&&modulnames1.size()!=0){
								Screenresule_1609_two jsonshuchu1=new Screenresule_1609_two();
								jsonshuchu1.setJson(ScreenResults);
								jsonshuchu1.setJson1(ScreenResults1);
								jsonshuchu1.setModuleidstr(moduleidstr);
								jsonshuchu1.setDrugname(ScreenResultDrugsn.get("DrugIndex").toString());
								jsonshuchu1.Jsonrequest();
								List listerr1=jsonshuchu1.getListerr1();
								for(int i1=0;i1<listerr1.size();i1++){
									listerr.add(listerr1.get(i1));
								}
							}
							break;//���AB���������Ϊ�գ����ٽ������ڵ�Ա�
						}
						
						//ȡB��������ڵ�
						List klist=new ArrayList();//��¼Bjson�Ѿ��Աȳɹ����λ��
						for(int z=0;z<ScreenResults.size();z++){
							JSONObject ScreenResult=ScreenResults.getJSONObject(z);//Ajson��һ��ScreenResults
							
							//ȫ������ָ����ģ��Ա�ModuleID�ڵ�
							if(moduleidstr==Integer.parseInt(ScreenResult.get("ModuleID").toString())||moduleidstr==-1){
								int sum=0;//��ͬ�̶�ƥ������ʼֵΪ0
								String err=null;
								String err1=null;
								int ksum=0;//B����ʼλ��Ϊ0
								for(int k=0;k<ScreenResults1.size();k++){
									//��ѯB���Ƿ��Ѿ�ƥ���
									int kstate=0;//Ĭ��Bjsonδ�Աȹ�
									for(int k1=0;k1<klist.size();k1++){
										if(k==Integer.parseInt(klist.get(k1).toString())){
											kstate=1;//Bjson�ѶԱȹ�
										}
									}
									
									//B��δ�Աȹ�
									if(kstate==0){
										int sum1=0;
										JSONObject ScreenResult1=ScreenResults1.getJSONObject(k);//Bjson��һ��ScreenResults
										
										//ȫ������ָ����ģ��Ա�ModuleID�ڵ�
										if(moduleidstr==Integer.parseInt(ScreenResult.get("ModuleID").toString())||moduleidstr==-1){
											
											//ȷ���Ƿ���ͬģ����
											if(ScreenResult.get("ModuleID").equals(ScreenResult1.get("ModuleID"))){
												sum1=sum1+1;
											}
											
											//ȷ���Ƿ���ͬ������
											if(ScreenResult.get("Slcode").equals(ScreenResult1.get("Slcode"))){
												sum1=sum1+1;
											}
											
											//ȷ���Ƿ���ͬ��ʾ���
											if(ScreenResult.get("Warning").equals(ScreenResult1.get("Warning"))){
												sum1=sum1+1;
											}
											
											//�Ա����нڵ�
											for(int o=0;o<ScreenResult.size();o++){
												String name=ScreenResult.names().getString(o);
												if(ScreenResult.get(name).equals(ScreenResult1.get(name))){
													sum1=sum1+1;
												}
											}
											
											//��¼ƥ�����ʹ�����ʾ
											if(sum1>sum){
												sum=sum1;
												ksum=k;//��Bjson�Աȹ���λ�ü�¼��
//												System.out.println(ScreenResult.toString());
//												System.out.println(ScreenResult1.toString());
												err=ScreenResult.toString();
												err1=ScreenResult1.toString();
											}
										}
									}
								}
								
								//��¼Bjson�����ƥ������ߵ�λ��
								klist.add(ksum);
								
								//���ȫ���ڵ���ڶԱȴ���ģ���¼����
								if(sum<18&&sum>0){
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
					
					//���B���Ҳ���A��ҩƷ����ʾ����
					if(DrugIndexstate==0&&(j+1)==ScreenResultDrugs1.size()){
//						System.out.println("���ԣ�DrugIndexΪ��"+ScreenResultDrugsn.get("DrugIndex"));
//						System.out.println("��Ӧ��DrugIndexΪ����");
						listerr.add("DrugIndex=====>"+ScreenResultDrugsn.get("DrugIndex"));
						listerr.add("DrugIndex=====>");
					}
				}
			}
			
			//����д��󣬴���״̬�ĳ�1
			if(listerr.size()!=0){
				listerrsum=1;
			}
			
			//������д���list
			setListerr1(listerr);
			Date data=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println("IP:"+ip+"��"+sf.format(data));
			return listerr1;
		}catch(Exception ex){
			listerrsum=1;
			System.out.println("json-err");
			listerr.add("json-err");
			setListerr1(listerr);
			Date data=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println("IP:"+ip+"��"+sf.format(data));
			return listerr1;
		}
	}
}
