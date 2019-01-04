package com.ch.sys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ch.sys.Kettle_config;

/**
 * 脚本批量维护
*java 修改kettle 文件
*/
@Service
public class AlterKettle {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Kettle_config kettle_config ;
	
	private static final int RADIX = 16;
	private static final String SEED = "0933910847463829827159347601486730416058";
	public static final String PASSWORD_ENCRYPTED_PREFIX = "Encrypted ";

	//加密
	public static final String encryptPassword(String password) {
		if (password == null)
			return "";
		if (password.length() == 0)
			return "";

		BigInteger bi_passwd = new BigInteger(password.getBytes());

		BigInteger bi_r0 = new BigInteger(SEED);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);

		return bi_r1.toString(RADIX);
	}

	//解密
	public static final String decryptPassword(String encrypted) {
		if (encrypted == null)
			return "";
		if (encrypted.length() == 0)
			return "";

		BigInteger bi_confuse = new BigInteger(SEED);

		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);

			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}
	
	public void kettlexml(String filepath, String fileoutpath){
//		String server1=kettle_config.getDatabase_one_ip();
//		String type1=kettle_config.getDatabase_one_type();
//		String access1="Native";
//		String database1=kettle_config.getDatabase_one_name();
//		String port1="";
//		if("MYSQL".equals(kettle_config.getDatabase_one_type())){
//			port1="3306";
//		}else if("SQLSERVER".equals(kettle_config.getDatabase_one_type())){
//			port1="1433";
//		}else if("SQLSERVER".equals(kettle_config.getDatabase_one_type())){
//			port1="1521";
//		}
//		String username1=kettle_config.getDatabase_one_username();
//		String password1=PASSWORD_ENCRYPTED_PREFIX+encryptPassword(kettle_config.getDatabase_one_password().trim());//zfxmz
		
		String server2=kettle_config.getDatabase_two_ip();
		String type2=kettle_config.getDatabase_two_type();
		String access2="Native";
		String database2=kettle_config.getDatabase_two_name();
		String port2="";
		if("MYSQL".equals(kettle_config.getDatabase_two_type())){
			port2="3306";
		}else if("MSSQL".equals(kettle_config.getDatabase_two_type())){
			port2="1433";
		}else if("ORACLE".equals(kettle_config.getDatabase_two_type())){
			port2="1521";
		}
		String username2=kettle_config.getDatabase_two_username();
		String password2=PASSWORD_ENCRYPTED_PREFIX+encryptPassword(kettle_config.getDatabase_two_password().trim());//123456
		
		Crypt_Kettyle password=new Crypt_Kettyle();
		
		SAXReader reader=new SAXReader();
		Document document = null;
		try {
			File fl=new File(filepath);
			document = reader.read(fl);
			
			//文件读到内存后删除原文件，重新生成文件时，直接替换原文件使用
			fl.delete();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		System.out.println(document);
		
		//读取xml文件
		Element root = document.getRootElement();
//		System.out.println(root.asXML());
		
		int a=0;
		int b=0;
		for ( Iterator i = root.elementIterator("connection"); i.hasNext();) {
			Element connection = (Element) i.next();//�߳�������
//			System.out.println(connection.asXML());
			
			Element name =connection.element("name");
//			System.out.println(name.getText());
//			if(kettle_config.getDatabase_one().equals(name.getText())){
//				a=a+1;
//				connection.element("server").setText(server1);
//				connection.element("type").setText(type1);
//				connection.element("access").setText(access1);
//				connection.element("database").setText(database1);
//				connection.element("port").setText(port1);
//				connection.element("username").setText(username1);
//				connection.element("password").setText(password1);
//			}
			if(kettle_config.getDatabase_two().equals(name.getText())){
				b=b+1;
				connection.element("server").setText(server2);
				connection.element("type").setText(type2);
				connection.element("access").setText(access2);
				connection.element("database").setText(database2);
				connection.element("port").setText(port2);
				connection.element("username").setText(username2);
				connection.element("password").setText(password2);
				
				Element attributes=connection.element("attributes");
				if("MYSQL".equals(type2)){
					for ( Iterator i1 = attributes.elementIterator("attribute"); i1.hasNext();) {
						Element attribute = (Element) i1.next();
						attributes.remove(attribute);
					}
					attributes.setText("");//清楚前面删除后文档存在换行内容
					
					Element attribute=attributes.addElement("attribute");
					attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_LOWERCASE");
					attribute.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_UPPERCASE");
					attribute.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("IS_CLUSTERED");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("PORT_NUMBER");
					attributes.addElement("attribute").addText("3306");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("PRESERVE_RESERVED_WORD_CASE");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("QUOTE_ALL_FIELDS");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("STREAM_RESULTS");
					attributes.addElement("attribute").addText("Y");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("SUPPORTS_BOOLEAN_DATA_TYPE");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("SUPPORTS_TIMESTAMP_DATA_TYPE");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("USE_POOLING");
					attributes.addElement("attribute").addText("N");
				}else if("MSSQL".equals(type2)){
					for ( Iterator i1 = attributes.elementIterator("attribute"); i1.hasNext();) {
						Element attribute = (Element) i1.next();
						attributes.remove(attribute);
					}
					attributes.setText("");//清楚前面删除后文档存在换行内容
					
					Element attribute=attributes.addElement("attribute");
					attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_LOWERCASE");
					attribute.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_UPPERCASE");
					attribute.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("IS_CLUSTERED");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("MSSQL_DOUBLE_DECIMAL_SEPARATOR");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("PORT_NUMBER");
					attributes.addElement("attribute").addText("1433");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("PRESERVE_RESERVED_WORD_CASE");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("QUOTE_ALL_FIELDS");
					attributes.addElement("attribute").addText("N");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("SUPPORTS_BOOLEAN_DATA_TYPE");
					attributes.addElement("attribute").addText("Y");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("SUPPORTS_TIMESTAMP_DATA_TYPE");
					attributes.addElement("attribute").addText("Y");
					
					attribute=attributes.addElement("attribute");
					attributes.addElement("code").addText("USE_POOLING");
					attributes.addElement("attribute").addText("N");
					
				}else if("ORACLE".equals(type2)){
					
				}
			}
	    }
		
		//如果缺少数据库连接
//		if(a==0){
//			root.addElement("connection").addElement("name").setText("mysql");
//			for ( Iterator i = root.elementIterator("connection"); i.hasNext();) {
//				Element connection = (Element) i.next();//�߳�������
////				System.out.println(connection.asXML());
//				
//				Element name =connection.element("name");
////				System.out.println(name.getText());
//				if(kettle_config.getDatabase_one().equals(name.getText())){
//					connection.addElement("server").setText(server1);
//					connection.addElement("type").setText(type1);
//					connection.addElement("access").setText(access1);
//					connection.addElement("database").setText(database1);
//					connection.addElement("port").setText(port1);
//					connection.addElement("username").setText(username1);
//					connection.addElement("password").setText(password1);
//				}
//		    }
//		}
		if(b==0){
			root.addElement("connection").addElement("name").setText("oracle");
			for ( Iterator i = root.elementIterator("connection"); i.hasNext();) {
				Element connection = (Element) i.next();//�߳�������
//				System.out.println(connection.asXML());
				
				Element name =connection.element("name");
//				System.out.println(name.getText());
				if(kettle_config.getDatabase_two().equals(name.getText())){
					connection.addElement("server").setText(server2);
					connection.addElement("type").setText(type2);
					connection.addElement("access").setText(access2);
					connection.addElement("database").setText(database2);
					connection.addElement("port").setText(port2);
					connection.addElement("username").setText(username2);
					connection.addElement("password").setText(password2);
					
					Element attributes=connection.element("attributes");
					if("MYSQL".equals(type2)){
						for ( Iterator i1 = attributes.elementIterator("attribute"); i1.hasNext();) {
							Element attribute = (Element) i1.next();
							attributes.remove(attribute);
						}
						attributes.setText("");//清楚前面删除后文档存在换行内容
						
						Element attribute=attributes.addElement("attribute");
						attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_LOWERCASE");
						attribute.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_UPPERCASE");
						attribute.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("IS_CLUSTERED");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("PORT_NUMBER");
						attributes.addElement("attribute").addText("3306");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("PRESERVE_RESERVED_WORD_CASE");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("QUOTE_ALL_FIELDS");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("STREAM_RESULTS");
						attributes.addElement("attribute").addText("Y");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("SUPPORTS_BOOLEAN_DATA_TYPE");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("SUPPORTS_TIMESTAMP_DATA_TYPE");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("USE_POOLING");
						attributes.addElement("attribute").addText("N");
					}else if("MSSQL".equals(type2)){
						for ( Iterator i1 = attributes.elementIterator("attribute"); i1.hasNext();) {
							Element attribute = (Element) i1.next();
							attributes.remove(attribute);
						}
						attributes.setText("");//清楚前面删除后文档存在换行内容
						
						Element attribute=attributes.addElement("attribute");
						attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_LOWERCASE");
						attribute.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attribute.addElement("code").addText("FORCE_IDENTIFIERS_TO_UPPERCASE");
						attribute.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("IS_CLUSTERED");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("MSSQL_DOUBLE_DECIMAL_SEPARATOR");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("PORT_NUMBER");
						attributes.addElement("attribute").addText("1433");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("PRESERVE_RESERVED_WORD_CASE");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("QUOTE_ALL_FIELDS");
						attributes.addElement("attribute").addText("N");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("SUPPORTS_BOOLEAN_DATA_TYPE");
						attributes.addElement("attribute").addText("Y");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("SUPPORTS_TIMESTAMP_DATA_TYPE");
						attributes.addElement("attribute").addText("Y");
						
						attribute=attributes.addElement("attribute");
						attributes.addElement("code").addText("USE_POOLING");
						attributes.addElement("attribute").addText("N");
						
					}else if("ORACLE".equals(type2)){
						
					}
				}
		    }
		}
		
		//因为java表结构和。net有区别，所以这里增加代码修改脚本对应的type字段
		if(filepath.contains("mc_dict_disease.ktr")){
			for ( Iterator i = root.elementIterator("step"); i.hasNext();) {
				Element step = (Element) i.next();
				//导入案例库
				if("其他库表输入".equals(step.element("name").getText())){
					Element sql = (Element)step.element("sql");
					String sqls="";
					if("MYSQL".equals(type2)){
						sqls="SELECT match_scheme, discode, disname, searchcode, typecode, typename, "
								+ "dis_type, is_mxb, is_save, pass_icd_code, pass_icd_name, unable_match, "
								+ "unable_match_desc FROM mc_dict_disease where  match_scheme=?";
					}else{
						sqls="SELECT match_scheme, discode, disname, searchcode, typecode, typename, "
								+ "type, is_mxb, is_save, pass_icd_code, pass_icd_name, unable_match, "
								+ "unable_match_desc FROM mc_dict_disease where  match_scheme=?";
					}
					sql.setText(sqls);
				}
				if("mysql更新".equals(step.element("name").getText())){
					Element lookup = (Element)step.element("lookup");
					for ( Iterator i1 = lookup.elementIterator("value"); i1.hasNext();) {
						Element value = (Element) i1.next();
						if("dis_type".equals(value.element("name").getText()) || "type".equals(value.element("name").getText())){
							if("MYSQL".equals(type2)){
								value.element("name").setText("dis_type");
								value.element("rename").setText("dis_type");
							}else{
								value.element("name").setText("dis_type");
								value.element("rename").setText("type");
							}
							
						}
					}
				}
				//导入目标库
				if("其他库表插入 / 更新".equals(step.element("name").getText())){
					Element lookup = (Element)step.element("lookup");
					for ( Iterator i1 = lookup.elementIterator("value"); i1.hasNext();) {
						Element value = (Element) i1.next();
						if("dis_type".equals(value.element("name").getText()) || "type".equals(value.element("name").getText())){
							if("MYSQL".equals(type2)){
								value.element("name").setText("dis_type");
								value.element("rename").setText("dis_type");
							}else{
								value.element("name").setText("type");
								value.element("rename").setText("dis_type");
							}
							
						}
					}
				}
			}
		}
		if(filepath.contains("mc_dict_route.ktr")){
			for ( Iterator i = root.elementIterator("step"); i.hasNext();) {
				Element step = (Element) i.next();
				//导入案例库
				if("其他库表输入".equals(step.element("name").getText())){
					Element sql = (Element)step.element("sql");
					String sqls="";
					if("MYSQL".equals(type2)){
						sqls="SELECT match_scheme, discode, disname, searchcode, typecode, typename, "
								+ "route_type, is_mxb, is_save, pass_icd_code, pass_icd_name, unable_match, "
								+ "unable_match_desc FROM mc_dict_disease where  match_scheme=?";
					}else{
						sqls="SELECT match_scheme, discode, disname, searchcode, typecode, typename, "
								+ "type, is_mxb, is_save, pass_icd_code, pass_icd_name, unable_match, "
								+ "unable_match_desc FROM mc_dict_disease where  match_scheme=?";
					}
					sql.setText(sqls);
				}
				if("mysql更新".equals(step.element("name").getText())){
					Element lookup = (Element)step.element("lookup");
					for ( Iterator i1 = lookup.elementIterator("value"); i1.hasNext();) {
						Element value = (Element) i1.next();
						if("dis_type".equals(value.element("name").getText()) || "type".equals(value.element("name").getText())){
							if("MYSQL".equals(type2)){
								value.element("name").setText("route_type");
								value.element("rename").setText("route_type");
							}else{
								value.element("name").setText("route_type");
								value.element("rename").setText("type");
							}
							
						}
					}
				}
				//导入目标库
				if("其他库表插入 / 更新".equals(step.element("name").getText())){
					Element lookup = (Element)step.element("lookup");
					for ( Iterator i1 = lookup.elementIterator("value"); i1.hasNext();) {
						Element value = (Element) i1.next();
						if("dis_type".equals(value.element("name").getText()) || "type".equals(value.element("name").getText())){
							if("MYSQL".equals(type2)){
								value.element("name").setText("route_type");
								value.element("rename").setText("route_type");
							}else{
								value.element("name").setText("type");
								value.element("rename").setText("route_type");
							}
							
						}
					}
				}
			}
		}
		
		OutputFormat format = new OutputFormat();
        format.setEncoding("UTF-8");
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileoutpath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        XMLWriter output = null;
		try {
			output = new XMLWriter(fos, format);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			output.write(document);
			output.flush();
			output.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	public void kettlerun(int tongbuDatabaseid,String filepath){
		String sql=null;
		sql="select * from sys_database where databaseid=?";
		List list=jdbcTemplate.queryForList(sql,new Object[]{tongbuDatabaseid});
		Map map=(Map)list.get(0);
		kettle_config.setDatabase_two(map.get("kettle_Database_two").toString());
		kettle_config.setDatabase_two_ip(map.get("ip").toString());
		kettle_config.setDatabase_two_name(map.get("databasename").toString());
		kettle_config.setDatabase_two_type(map.get("databasetype").toString());
		kettle_config.setDatabase_two_username(map.get("username").toString());
		kettle_config.setDatabase_two_password(map.get("password").toString());
		
		//修改kettle文件
		if(filepath.contains(".ktr")){
			kettlexml(filepath,filepath);
		}else{
			//read all kettle files
			if(!filepath.substring(filepath.length()-1,filepath.length()).equals("\\".substring(0, 1))){
				filepath=filepath+"\\";
			}
			File file = new File(filepath);
			File[] tempList = file.listFiles();
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					System.out.println("filepath is:" + tempList[i]);
					
					//alter kettle file
					kettlexml(tempList[i].toString(),tempList[i].toString());
				}
			}
		}
	}
}
