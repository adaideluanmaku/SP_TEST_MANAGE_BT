package com.ch.passjson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


//��jsonshuchu��java���ý���������ͬ�ľ���Ա�
public class Screenresule_1609_two {
	private JSONArray json=null;
	private JSONArray json1=null;
	private JSONArray ScreenResults=null;
	private JSONArray ScreenResults1=null;
	private List listerr1;
	private int listerrsum=0;
	private int moduleidstr=-1;
	private int duibistate=1;
	private String ip;
	public String drugindex=null;

	public void setDrugname(String drugindex) {
		this.drugindex = drugindex;
	}

	public void setListerr1(List listerr1) {
		this.listerr1 = listerr1;
	}

	public List getListerr1() {
		return listerr1;
	}

	public void setJson(JSONArray json) {
		this.json = json;
	}

	public void setJson1(JSONArray json1) {
		this.json1 = json1;
	}

	public void setModuleidstr(int moduleidstr) {
		this.moduleidstr = moduleidstr;
	}

	public List Jsonrequest(){
		List listerr=new ArrayList();
		
		List klist=new ArrayList();//��¼Bjson�Ѿ��Աȳɹ����λ��
		if(json.size()<json1.size()){
			ScreenResults=json;
			ScreenResults1=json1;
			for(int z=0;z<ScreenResults.size();z++){
				JSONObject ScreenResult=ScreenResults.getJSONObject(z);//Ajson��һ��ScreenResults
				
				//ȫ������ָ����ģ��Ա�ModuleID�ڵ�
				int sum=0;//��ͬ�̶�ƥ������ʼֵΪ0
				String err=null;
				String err1=null;
				int ksum=0;//B����ʼλ��Ϊ0
				if(moduleidstr==Integer.parseInt(ScreenResult.get("ModuleID").toString())||moduleidstr==-1){
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
//									System.out.println(ScreenResult.toString());
//									System.out.println(ScreenResult1.toString());
									err=ScreenResult.toString();
									err1=ScreenResult1.toString();
								}
							}
						}
					}
					
					//���ȫ���ڵ���ڶԱȴ���ģ���¼����
					if(sum<18&&sum>0){
						JSONObject errobj=JSONObject.fromObject(err);
						JSONObject errobj1=JSONObject.fromObject(err1);
						List errnamelist=new ArrayList();
						List errnamelist1=new ArrayList();
						for(int errn=0;errn<errobj.size();errn++){
							String errname=errobj.names().getString(errn);
							if(!errobj.get(errname).equals(errobj1.get(errname))){
								errnamelist.add(errname);
							}
						}
						listerr.add("DrugIndex=====>"+drugindex+",ScreenResults find :"+errnamelist);
						listerr.add("DrugIndex=====>"+drugindex+",ScreenResults not find :");
						listerr.add("----error:"+err);
						listerr.add("----error:"+err1);
					}
					
					//��¼Bjson�����ƥ������ߵ�λ��
					klist.add(ksum);
				}
			}
			
			//B������������
			List ScreenResults1re=new ArrayList();
			for(int i=0;i<klist.size();i++){
				ScreenResults1re.add(ScreenResults1.get(Integer.parseInt(klist.get(i).toString())));
			}
			for(int i=0;i<ScreenResults1re.size();i++){
				ScreenResults1.remove(ScreenResults1re.get(i));
			}
			for(int i=0;i<ScreenResults1.size();i++){
				listerr.add("----DrugIndex=====>"+drugindex);
				listerr.add("----DrugIndex=====>"+drugindex+"--"+ScreenResults1.getJSONObject(i));
			}
		}else{
			ScreenResults=json1;
			ScreenResults1=json;
			for(int z=0;z<ScreenResults.size();z++){
				JSONObject ScreenResult=ScreenResults.getJSONObject(z);//Ajson��һ��ScreenResults
				
				//ȫ������ָ����ģ��Ա�ModuleID�ڵ�
				int sum=0;//��ͬ�̶�ƥ������ʼֵΪ0
				String err=null;
				String err1=null;
				int ksum=0;//B����ʼλ��Ϊ0
				if(moduleidstr==Integer.parseInt(ScreenResult.get("ModuleID").toString())||moduleidstr==-1){
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
//									System.out.println(ScreenResult.toString());
//									System.out.println(ScreenResult1.toString());
									err=ScreenResult.toString();
									err1=ScreenResult1.toString();
								}
							}
						}
					}
					
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
						
						listerr.add("DrugIndex=====>"+drugindex+",ScreenResults :"+errnamelist+"   ===Look Down===");
						listerr.add("DrugIndex=====>"+drugindex+",ScreenResults-error :"+errnamelist1);
						listerr.add("----error:"+err1);
						listerr.add("----error:"+err);
					}
					
					klist.add(ksum);
				}
			}
			
			List ScreenResults1re=new ArrayList();
			for(int i=0;i<klist.size();i++){
				ScreenResults1re.add(ScreenResults1.get(Integer.parseInt(klist.get(i).toString())));
			}
			for(int i=0;i<ScreenResults1re.size();i++){
				ScreenResults1.remove(ScreenResults1re.get(i));
			}
			for(int i=0;i<ScreenResults1.size();i++){
				listerr.add("DrugIndex=====>"+drugindex+"--"+ScreenResults1.getJSONObject(i));
				listerr.add("DrugIndex=====>"+drugindex);
			}
		}
		setListerr1(listerr);
		return listerr1;
	}
}
