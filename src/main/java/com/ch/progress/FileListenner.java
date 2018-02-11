package com.ch.progress;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class FileListenner implements ProgressListener{
	private HttpSession session;

	public FileListenner() {  } 
	
	public void setSession(HttpSession session){  
        this.session=session;  
        Progress status = new Progress();  
        session.setAttribute("status", status);  
    }  
	
	/**
	 * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
	 */
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		Progress status = (Progress) session.getAttribute("status");
		status.setBytesRead(pBytesRead);
		status.setContentLength(pContentLength);
		status.setItems(pItems);
		session.setAttribute("status", status);
		
		//打印
//		JSONObject json=JSONObject.fromObject(status);
//		System.out.println("aaaa:"+json);
	}
}
