package com.ch.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ch.easyui.DataGrid;
import com.ch.progress.Progress;
import com.ch.service.Learnbean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/learn")
public class LearnAction {
	@Autowired
	Learnbean learnbean;
	
	@RequestMapping("/learn")
	public ModelAndView learn(HttpServletRequest req){
		return new ModelAndView("/learn/learn");
	}
	
	@RequestMapping("/learnlist")
	@ResponseBody
	public DataGrid learnlist(HttpServletRequest req){
		DataGrid datagrid=learnbean.learnlist(req);
		return datagrid;
	}
	
	@RequestMapping(value="/learnlistadd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String learnlistadd(HttpServletRequest req){
		return learnbean.learnlistadd(req);
	}
	
	@RequestMapping(value="/learnlistdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String learnlistdel(HttpServletRequest req){
		return learnbean.learnlistdel(req);
	}
	
	@RequestMapping(value="/learnlistupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String learnlistupdate(HttpServletRequest req){
		return learnbean.learnlistupdate(req);
	}
	
	@RequestMapping("/learngrouplist")
	@ResponseBody
	public DataGrid learngourplist(HttpServletRequest req){
		DataGrid datagrid=learnbean.learngrouplist(req);
		return datagrid;
	}
	
	@RequestMapping("/learngroupbox")
	@ResponseBody
	public JSONArray learngroupbox(HttpServletRequest req){
		List list=learnbean.learngroupbox(req);
		JSONArray json=new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json1=JSONObject.fromObject(list.get(i));
			json.add(json1);
		}
		return json;
	}
	
	@RequestMapping(value="/learngroupadd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String learngroupadd(HttpServletRequest req){
		return learnbean.learngroupadd(req);
	}
	
	@RequestMapping(value="/learngroupdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String learngroupdel(HttpServletRequest req){
		return learnbean.learngroupdel(req);
	}
	
	//上传file文件
	@RequestMapping("/learnuploadfile")
	@ResponseBody
	public void learnuploadfile(HttpServletRequest req) throws IOException{
		learnbean.learnuploadfile(req);
	}
	
	/**
	 * 获取文件上传下载进度条进度
	 * @param req
	 * @return
	 */
	@RequestMapping("/progress")  
    @ResponseBody
    public String progress(HttpServletRequest req) { 
//        if(status==null){  
//            return status;  
//        } 
		
		Progress status = (Progress) req.getSession().getAttribute("status");
//		if(status.getState()==0 || StringUtil.isBlank(status.toString())){
//			status.setBytesRead(0);
//			status.setContentLength(0);
//			JSONObject json=JSONObject.fromObject(status);
//			return json.toString();
//		}
		JSONObject json=JSONObject.fromObject(status);
//		System.out.println("bbbb:"+json);
//		req.getSession().setAttribute("status",null);
		
        return json.toString();
    } 
	
	//获取file文件
	@RequestMapping("/learnreadfile")
	@ResponseBody
	public Map learnreadfile(HttpServletRequest req) throws IOException{
		byte[] file=null;
		Map map=null;
		
		//返回MAP时，JS无法解析base64编码成图片，所以MAP里面的文件不能用base64编码
		map=learnbean.learnreadfile(req);
		
		//单独返回文件时，JS可以解析base64编码成图片
//		file=Base64.encodeBase64((byte[]) map.get("learnfile"));
		
		//base64编码
//		Base64.encodeBase64(file);
		//base64解码
//		Base64.decodeBase64(file);
		return map;
	}
	
	//获取上一个或者下一个file文件
	@RequestMapping("/learnreadfile_upandow")
	@ResponseBody
	public Map learnreadfile_upandown(HttpServletRequest req) throws IOException{
		Map map=null;
		map=learnbean.learnreadfile_upandown(req);
		return map;
	}
	
	//删除file文件
	@RequestMapping("/learnreadfile_del")
	@ResponseBody
	public Map learnreadfile_del(HttpServletRequest req) throws IOException{
		Map map=null;
		map=learnbean.learnreadfile_del(req);
		return map;
	}
}
