package com.ch.pahis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Createtable {
	@Autowired
	JdbcTemplate jdbcTemplate_oracle;
	
	public void ceatetable() throws SQLException, ClassNotFoundException, IOException{
		String encoding = "UTF-8";
		byte[] filecontent=null;
		System.out.println("开始创建表结构");
		//read file context，读取文件内容
        try {  
            InputStream in = Createtable.class.getClassLoader().getResourceAsStream("his_oracle_table_1712.sql");
            filecontent = new byte[in.available()];  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  catch (IOException e) {  
            e.printStackTrace();  
        }  
        String obj[]=new String(filecontent, encoding).split("-- ----------------------------");

        
        for(int i1=0;i1<obj.length;i1++){
        	if(obj[i1].contains("DROP") ){
        		String[] obj1=obj[i1].split(";");
            	
            	try{
//            		System.out.println(obj1[0]);
            		jdbcTemplate_oracle.update(obj1[0]);
            	}catch(Exception e){
            		System.out.println("未找到表，执行失败"+obj1[0]);
            	}
//        		System.out.println(obj1[1]);
        		jdbcTemplate_oracle.update(obj1[1]);
        	}
        	
        	if(obj[i1].contains("ALTER TABLE")){
        		String[] obj1=obj[i1].split(";");
        		for(int i=0; i<obj1.length; i++){
        			try{
//                		System.out.println(obj1[0]);
                		jdbcTemplate_oracle.update(obj1[i]);
                	}catch(Exception e){
                		System.out.println("未找到表，执行失败"+obj1[0]);
                	}
        		}
        	}
        }
        
        System.out.println("创建表结构结束");
	}
	
	//制作业务表接口
	public void ceateview(String[] hiscodes) throws SQLException, ClassNotFoundException, IOException{
		String sql=null;
		
		String encoding = "UTF-8";
		//read all kettle files 读取所有文件
		byte[] filecontent=null;
		List createsql=new ArrayList();
		System.out.println("开始创建业务表视图结构");
		
		//read file context，读取文件内容
        try {  
        	InputStream in = Createtable.class.getClassLoader().getResourceAsStream("his_oracle_view.sql");
            filecontent = new byte[in.available()];  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        String obj[]=new String(filecontent, encoding).split("-- ----------------------------");
        
        for(int i1=0;i1<obj.length;i1++){
        	String[] obj1=obj[i1].split(";");
        	for(int i2=0;i2<obj1.length;i2++){
        		if(obj1[i2].contains("CREATE")){
            		if(obj1[i2].contains("MDC2_DICT")){
            			//执行建表脚本操作
    	    			String[] sqlstr=obj1[i2].split("SELECT");
    	    			sql=sqlstr[0];
            			for(int j=0;j<hiscodes.length;j++){
            				if(j>0){
        	    				sql+=" UNION ALL ";
        	    			}
            				
            				String hiscode=hiscodes[j];
        		    		sql+="SELECT '"+hiscode+"' "+sqlstr[1].split("'chview'")[1];
//        	    			sql+=sql.substring(0,sql.indexOf("SELECT"))+" SELECT '"+hiscode+"' "+sql.substring(sql.indexOf("AS hiscode"));
            			}
            			try{
        	    			jdbcTemplate_oracle.update(sql);
        	    		}catch(Exception e){
        	    			createsql.add(sql);
        	    			System.out.println("sql执行失败，将sql保护");
        	    		}
            		}else{
            			try{
        		    		//执行建表脚本操作
        	    			sql=obj1[i2];
        	    			jdbcTemplate_oracle.update(sql);
        	    		}catch(Exception e){
        	    			createsql.add(sql);
        	    			System.out.println("sql执行失败，将sql保护");
        	    		}
            		}
            	}
        	}
        }
        
        System.out.println("创建视图结构结束");
		
		int status = 0;
		while (createsql.size() != 0) {
			for (int i1 = 0; i1 < createsql.size(); i1++) {
				sql=createsql.get(i1).toString();
//				System.out.println(createsql.get(i1));
				try{
					jdbcTemplate_oracle.update(sql);
	    		}catch(Exception e){
//	    			System.out.println("视图执行失败保护"+sql);
	    			continue;
	    		}
				
				createsql.remove(i1);
				i1=i1-1;
			}
			status++;
			if(status>100){
				System.out.println("存在视图未创建成功，强制退出");
				System.out.println(createsql);
				return;
			}
		}
	}
	
	//制作多个机构的字典表接口
	public void ceatedictview(String[] hiscodes) throws SQLException, ClassNotFoundException, IOException{
		String sql="";
		
		String encoding = "UTF-8";
		//read all kettle files 读取所有文件
		byte[] filecontent=null;
		List createsql=new ArrayList();
		System.out.println("开始创建字典表视图结构");
		
		//read file context，读取文件内容
        try {  
        	InputStream in = Createtable.class.getClassLoader().getResourceAsStream("his_oracle_dict_view.sql");
            filecontent = new byte[in.available()];  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        String obj[]=new String(filecontent, encoding).split("-- ----------------------------");
        
        for(int i1=0;i1<obj.length;i1++){
        	if(obj[i1].contains("CREATE")){
        		if(obj[i1].contains("MDC2_DICT")){
        			//执行建表脚本操作
	    			sql=obj[i1].split(";")[0];
	    			String[] sqlstr=obj[i1].split(";")[0].split("SELECT");
	    			sql=sqlstr[0];
        			for(int j=0;j<hiscodes.length;j++){
        				if(j>0){
    	    				sql+=" UNION ALL ";
    	    			}
        				
        				String hiscode=hiscodes[j];
    		    		sql+="SELECT '"+hiscode+"' "+sqlstr[1].split("'chview'")[1];
//    	    			sql+=sql.substring(0,sql.indexOf("SELECT"))+" SELECT '"+hiscode+"' "+sql.substring(sql.indexOf("AS hiscode"));
        			}
        			try{
    	    			jdbcTemplate_oracle.update(sql);
    	    		}catch(Exception e){
    	    			createsql.add(sql);
    	    			System.out.println("sql执行失败，将sql保护");
    	    		}
        		}
        	}
        }
        
        System.out.println("创建视图结构结束");
		
		int status = 0;
		while (createsql.size() != 0) {
			for (int i1 = 0; i1 < createsql.size(); i1++) {
				sql=createsql.get(i1).toString();
//				System.out.println(createsql.get(i1));
				try{
					jdbcTemplate_oracle.update(sql);
	    		}catch(Exception e){
//	    			System.out.println("视图执行失败保护"+sql);
	    			continue;
	    		}
				
				createsql.remove(i1);
				i1=i1-1;
			}
			status++;
			if(status>100){
				System.out.println("存在视图未创建成功，强制退出");
				System.out.println(createsql);
				return;
			}
		}
	}
}
