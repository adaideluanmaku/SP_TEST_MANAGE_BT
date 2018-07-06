package com.ch.passjson;

import java.io.StringReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

@Service
public class Xmltojson {
	public String getjson(String jsoncdata) throws DocumentException{
		SAXReader reader=new SAXReader();
		Document document=reader.read(new StringReader(jsoncdata));
		Element root=document.getRootElement();
//		System.out.println(root.getText());
		String jsoncdata1=root.getText();
		return jsoncdata1;
	}
}
