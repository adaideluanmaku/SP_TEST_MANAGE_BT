package com.ch.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.easyui.DataGrid;
import com.ch.pahis.Date_to_Oracle;
import com.ch.progress.Progress;
import com.ch.selenium.Selenium;
import com.ch.service.Pabean;
import com.ch.service.SocketBean;
import com.ch.service.Testmngbean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/testmng")
public class TestmngAction {
	@Autowired
	Testmngbean testmngbean;
	
	@Autowired
	Pabean pabean;
	
	@Autowired
	Date_to_Oracle date_to_Oracle;
	
	@Autowired
	private Selenium selenium;
	
	@RequestMapping("/testmanage")
	//获得整个页面所有列表数据
	public ModelAndView testmng(){
		return new ModelAndView("/testmng/testmngs");
	}
	
	//获得team列表数据
	@RequestMapping("/team")
	@ResponseBody
	public DataGrid team(HttpServletRequest req){
		DataGrid datagrid =testmngbean.Team(req);
		return datagrid;
	}
	
	//获得project列表数据
	@RequestMapping("/project")
	@ResponseBody
	public DataGrid project(HttpServletRequest req){
		DataGrid datagrid =testmngbean.Project(req);
		return datagrid;
	}
	
	//获得testmng列表数据
	@RequestMapping("/testmng")
	@ResponseBody
	public DataGrid testmng(HttpServletRequest req){
		DataGrid datagrid =testmngbean.Testmng(req);
		return datagrid;
	}

	//获得testmng列表数据
	@RequestMapping("/testmng_share")
	@ResponseBody
	public DataGrid testmng_share(HttpServletRequest req){
		DataGrid datagrid =testmngbean.testmng_share(req);
		return datagrid;
	}
	
	//获得team列表数据
	@RequestMapping("/script")
	@ResponseBody
	public DataGrid script(HttpServletRequest req){
		DataGrid datagrid =testmngbean.script(req);
		return datagrid;
	}
	
	//新增team列表数据
	@RequestMapping(value="/teamadd" , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String teamgadd(HttpServletRequest req){
		return testmngbean.teamadd(req);
		
	}
	
	//新增team列表数据
	@RequestMapping(value="/teamdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String teamdel(HttpServletRequest req){
		return testmngbean.teamdel(req);
	}
	
	//新增team列表数据
	@RequestMapping(value="/teamupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String teamupdate(HttpServletRequest req){
		return testmngbean.teamupdate(req);
	}
		
	//获取team下拉单
	@RequestMapping("/teamgroup")
	@ResponseBody
	public JSONArray teamgroup(HttpServletRequest req){
		List list=testmngbean.teamgroup(req);
		JSONArray json=new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json1=JSONObject.fromObject(list.get(i));
			json.add(json1);
		}
		return json;
	}
		
	//新增project列表数据
	@RequestMapping(value="/projectadd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String projectadd(HttpServletRequest req){
		return testmngbean.projectadd(req);
	}
	
	//新增project列表数据
	@RequestMapping(value="/projectdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String projectdel(HttpServletRequest req){
		return testmngbean.projectdel(req);
	}
	
	//新增project列表数据
	@RequestMapping(value="/projectupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String projectupdate(HttpServletRequest req){
		return testmngbean.projectupdate(req);
	}
	
	//获取project下拉单
	@RequestMapping("/projectgroup")
	@ResponseBody
	public JSONArray projectgroup(HttpServletRequest req){
		List list=testmngbean.projectgroup(req);
		JSONArray json=new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json1=JSONObject.fromObject(list.get(i));
			json.add(json1);
		}
		return json;
	}
		
	//新增team列表数据
	@RequestMapping(value="/testmngadd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmngadd(HttpServletRequest req){
		return testmngbean.testmngadd(req);
	}
	
	//新增team列表数据
	@RequestMapping(value="/testmngdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmngdel(HttpServletRequest req){
		return testmngbean.testmngdel(req);
	}
	
	//新增team列表数据
	@RequestMapping(value="/testmngupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmngupdate(HttpServletRequest req){
		return testmngbean.testmngupdate(req);
	}
	
	//复制team列表数据
	@RequestMapping(value="/testmngcopy", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String testmngcopy(HttpServletRequest req){
		return testmngbean.testmngcopy(req);
	}
	
	//pa审查-单个案例
	@RequestMapping(value="/pa_screen", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_screen(HttpServletRequest req){
		return pabean.pa_screen(req);
	}
	
	//pa审查-所有案例
	@RequestMapping(value="/pa_screen_all", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_screen_all(HttpServletRequest req){
		return pabean.pa_screen_all(req);
	}
		
	//pa-redis查询结果
	@RequestMapping(value="/pa_screen_redis", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_screen_redis(HttpServletRequest req){
		return pabean.pa_screen_redis(req);
	}
	
	//pa-redis手动清理
	@RequestMapping(value="/pa_redis_clear_sd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String pa_redis_clear_sd(HttpServletRequest req){
		return pabean.pa_redis_clear_sd(req);
	}
		
	//获取过敏原字典表
	@RequestMapping("/aller_dict")
	@ResponseBody
	public DataGrid aller_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.aller_dict(req);
		return datagrid;
	}
	
	//获取疾病字典表
	@RequestMapping("/dis_dict")
	@ResponseBody
	public DataGrid dis_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.dis_dict(req);
		return datagrid;
	}
	
	//获取手术字典表
	@RequestMapping("/opr_dict")
	@ResponseBody
	public DataGrid opr_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.opr_dict(req);
		return datagrid;
	}
	
	//获取药品字典表
	@RequestMapping("/odr_dict")
	@ResponseBody
	public DataGrid odr_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.odr_dict(req);
		return datagrid;
	}
	
	//获取给药途径字典表
	@RequestMapping("/route_dict")
	@ResponseBody
	public DataGrid route_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.route_dict(req);
		return datagrid;
	}
	
	//获取频次字典表
	@RequestMapping("/fre_dict")
	@ResponseBody
	public DataGrid fre_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.fre_dict(req);
		return datagrid;
	}
	
	//获取科室字典表
	@RequestMapping("/dept_dict")
	@ResponseBody
	public DataGrid dept_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.dept_dict(req);
		return datagrid;
	}
	
	//获取医生字典表
	@RequestMapping("/doc_dict")
	@ResponseBody
	public DataGrid doc_dict(HttpServletRequest req){
		DataGrid datagrid =pabean.doc_dict(req);
		return datagrid;
	}
	
	//获取自定义检验检查表
	@RequestMapping("/user_labitem")
	@ResponseBody
	public DataGrid user_labitem(HttpServletRequest req){
		DataGrid datagrid =pabean.user_labitem(req);
		return datagrid;
	}
	
	@RequestMapping(value="/selenium_testone", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String autoone(HttpServletRequest req){
		int testid=0;
		int projectid=0;
		HttpSession session=req.getSession();
		
		if(StringUtil.isNotBlank(req.getParameter("testid"))){
			testid=Integer.parseInt(req.getParameter("testid"));
		}else{
			return "项目编号或者案例编号不能为空";
		}
		if(StringUtil.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}else{
			return "项目编号或者案例编号不能为空";
		}
		return selenium.autoone(testid, Integer.parseInt(session.getAttribute("userid").toString()));
	}
	
	@RequestMapping(value="/selenium_testall",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String autoall(HttpServletRequest req){
		int testid=0;
		int projectid=0;
		String search_data="";
		HttpSession session=req.getSession();
		
		if(StringUtil.isNotBlank(req.getParameter("projectid"))){
			projectid=Integer.parseInt(req.getParameter("projectid"));
		}else{
			return "项目编号或者案例编号不能为空";
		}
		
		if(StringUtil.isNotBlank(req.getParameter("search_data"))){
			search_data=req.getParameter("search_data");
		}
		return selenium.autoall(projectid, Integer.parseInt(session.getAttribute("userid").toString()),search_data);
	}
	
	//新增script列表数据
	@RequestMapping(value="/scriptadd" , produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String scriptadd(HttpServletRequest req){
		return testmngbean.scriptadd(req);
		
	}
	
	//新增script列表数据
	@RequestMapping(value="/scriptdel", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String scriptdel(HttpServletRequest req){
		return testmngbean.scriptdel(req);
	}
	
	//更新script列表数据
	@RequestMapping(value="/scriptupdate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String scriptupdate(HttpServletRequest req){
		return testmngbean.scriptupdate(req);
	}
	
	//上传file文件
	@RequestMapping("/seleniumuploadfile")
	@ResponseBody
	public void seleniumuploadfile(HttpServletRequest req) throws IOException{
		testmngbean.seleniumuploadfile(req);
	}
	
	//下载file文件
	//页面A标签直接访问这个方法
	@RequestMapping("/seleniumdownfile")
	public void seleniumdownfile(HttpServletRequest req,HttpServletResponse rsp) throws IOException{
		Map map=null;
		List list=null;
		
		list=testmngbean.seleniumdownfile(req);
		
        //zip打包下载
		//设置上下文类型
    	rsp.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
    	//设置信息头，设置下载的文件名
    	rsp.setHeader("Content-Disposition","attachment; filename=\"selenium.zip\"");
    	
    	FileInputStream inputStream=null;
		FileOutputStream fos=null;
        List<String> fileNames =new ArrayList();
        ZipOutputStream zipOutputStream=null;
        
        //服务器生成文件，以便进行压缩
        for(int i=0;i<list.size();i++){
        	map=(Map)list.get(i);
        	
        	fos = new FileOutputStream(new File("C:\\"+map.get("filename")+".java"));
			fos.write((byte[])map.get("seleniumfile"));
			fos.close();
			
            fileNames.add(map.get("filename").toString());
        }
        
        
        //创建ZIP输出流，压缩准备
        zipOutputStream = new ZipOutputStream(rsp.getOutputStream());
        
        //开始压缩原文件到ZIP
        for(String fileName : fileNames) {
        	//ZIP压缩包里面的文件：取名
            ZipEntry zipEntry = new ZipEntry(fileName+".java");
            //开始将文件增加到压缩包
            zipOutputStream.putNextEntry(zipEntry);
            //读取原文件到输入流
            inputStream = new FileInputStream("C:\\"+fileName+".java");
            //压缩开始，将输入流的原文件COPY到输出流的压缩包
            IOUtils.copy(inputStream,zipOutputStream);
            inputStream.close();
        }
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        
        //删除原文件
        for(String fileName : fileNames) {
        	new File("C:\\"+fileName+".java").delete();
        }
        
        //单个下载
//        HttpHeaders headers = new HttpHeaders();    
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "test.java"); 
//        ResponseEntity rspen=new ResponseEntity<byte[]>((byte[]) list.get(0),    
//                headers, HttpStatus.CREATED); 
         
        //JS通过A标签直接请求，或者普通form表单请求，不能用ajax请求，要不然不能下载
//		return rspen;
	}
	
	/**
	 * 获取文件上传下载进度条进度
	 * @param req
	 * @return
	 */
	@RequestMapping("/progress")  
    @ResponseBody
    public String progress(HttpServletRequest req) { 
		Progress status = (Progress) req.getSession().getAttribute("status");
		JSONObject json=JSONObject.fromObject(status);
		
        return json.toString();
    } 
	
	//删除file文件
	@RequestMapping("/seleniumfile_del")
	public void seleniumfile_del(HttpServletRequest req) throws IOException{
		testmngbean.seleniumfile_del(req);
	}
	
	@RequestMapping(value="/date_to_oracle",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String date_to_oracle(HttpServletRequest req){
//		if(StringUtils.isBlank(req.getParameter("hiscodes1")) || StringUtils.isBlank(req.getParameter("datetime1"))
//				 || StringUtils.isBlank(req.getParameter("sum_date1")) || StringUtils.isBlank(req.getParameter("count1"))){
//			return "输入数据不完整";
//		}
		System.out.println("开始制作HIS数据");
		String hiscodes1=req.getParameter("hiscodes1");
		String datetime1=req.getParameter("datetime1");
		int sum_date1=0;
		if(StringUtils.isNotBlank(req.getParameter("sum_date1"))){
			sum_date1=Integer.parseInt(req.getParameter("sum_date1"));
		};
		int count1=0;
		if(StringUtils.isNotBlank(req.getParameter("count1"))){
			count1=Integer.parseInt(req.getParameter("count1"));
		};
		int mz1=Integer.parseInt(req.getParameter("mz1"));
		int zy1=Integer.parseInt(req.getParameter("zy1"));
		int cy1=Integer.parseInt(req.getParameter("cy1"));
		int dict1=Integer.parseInt(req.getParameter("dict1"));
		int createview1=Integer.parseInt(req.getParameter("createview1"));
		int trunca1=Integer.parseInt(req.getParameter("trunca1"));
		int anlisum=Integer.parseInt(req.getParameter("anlisum"));
		long startTime = System.currentTimeMillis();
//		int match_scheme1=0;
//		if(StringUtils.isNotBlank(req.getParameter("match_scheme1"))){
//			match_scheme1=Integer.parseInt(req.getParameter("match_scheme1"));
//		};
		String match_scheme1=req.getParameter("match_scheme1");
		int createTB1=Integer.parseInt(req.getParameter("createTB1"));
		//默认=0制作PA数据，=1制作PASS数据
		int passorpa_hisdata1=Integer.parseInt(req.getParameter("passorpa_hisdata1"));
		
		date_to_Oracle.Rundate(hiscodes1, datetime1, sum_date1, count1, mz1, zy1, cy1, dict1, createview1, 
				trunca1,anlisum,match_scheme1,createTB1,passorpa_hisdata1);
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时："+(endTime-startTime)+"毫秒");
		return "=========数据制作结束=======总耗时："+(endTime-startTime)+"毫秒";
	}
}
