package com.ch.selenium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.util.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class Selenium {
	@Autowired
	JdbcTemplate jdbcTemplate;
	public WebDriver driver=null;
	
	public String autoone(int testid , int userid){
//		WebDriver driver=null;
		List list=null;
		Map resultmap=new HashMap();
		Map scriptmap=null;
		String sql=null;
		String result=null;
		
		// 启动浏览器
		sql="select browserpath from sys_users where userid=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{userid});
		
		scriptmap=(Map)list.get(0);
		if("".equals(scriptmap.get("browserpath"))){
			return "本地浏览器地址为空";
		}
		
		try{
			if(scriptmap.get("browserpath").toString().contains("IEDriverServer")){
				System.setProperty("webdriver.ie.driver", scriptmap.get("browserpath").toString());
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(ieCapabilities);
			}
			if(scriptmap.get("browserpath").toString().contains("firefox")){
				System.setProperty("webdriver.firefox.bin", scriptmap.get("browserpath").toString());
				driver = new FirefoxDriver();
				
			}
			if(scriptmap.get("browserpath").toString().contains("chrome")){
				System.setProperty("webdriver.chrome.driver", scriptmap.get("browserpath").toString());
				driver = new ChromeDriver();
			}
		}catch(Exception e){
			return "浏览器调用失败";
		}
		
		// 全局设置延迟，如果操作无响应，则等待最多10S
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		// 浏览器最大化
		 driver.manage().window().maximize();

		//开始测试
		//清空测试结果 
		sql="update testmng set testresult='' where testid=?";
		jdbcTemplate.update(sql,new Object[]{testid});
			
		//获取案例脚本
		sql="select a.*,b.testno,b.testname from script a, testmng b where a.testid=b.testid and a.testid=? order by a.step asc";
		list=jdbcTemplate.queryForList(sql,new Object[]{testid});
		if(list.size()==0){
			sql="update testmng set testresult=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{"未找到测试脚本",testid});
			driver.close();
			return "未找到测试脚本";
		}
		
		//客户端从这里开始复制代码
		try{
			//循环脚本
			int status=0;
			for(int i=0;i<list.size();i++){
				scriptmap=(Map)list.get(i);
				System.out.println("开始执行selenium脚本第"+scriptmap.get("step")+"步");
				
				//每个脚本的公共方法
				String result1="";
				if(Integer.parseInt(scriptmap.get("scripttype").toString())==70 || 
						Integer.parseInt(scriptmap.get("scripttype").toString())==71){
					result1=selenium_obj_70(driver,scriptmap,0);
				}else{
					result1=selenium_obj(driver,scriptmap,0);
				}
				if(!"".equals(result1)){
					if(result1.contains("存在BUG") && status==0){
						status=1;
						result=result1;
//						break;
					}
				}
			}
		}catch (Exception e){
			result="selenium脚本第"+scriptmap.get("step")+"步，测试异常失败";
			System.out.println(result);
			System.out.println(e);
		}
		//客户端从这里结束复制代码
		
		sql="update testmng set testresult=? where testid=?";
		jdbcTemplate.update(sql,new Object[]{result,testid});
		
		return "ok";
	}
	
	public String autoall(int projectid, int userid,String search_data){
//		WebDriver driver=null;
		List list=null;
		Map resultmap=new HashMap();
		Map scriptmap=null;
		String sql=null;
		String result=null;
		
		// 启动浏览器
		sql="select browserpath from sys_users where userid=?";
		list=jdbcTemplate.queryForList(sql,new Object[]{userid});
		scriptmap=(Map)list.get(0);
		if("".equals(scriptmap.get("browserpath"))){
			return "本地浏览器地址为空";
		}
		
		try{
			if(scriptmap.get("browserpath").toString().contains("IEDriverServer")){
				System.setProperty("webdriver.ie.driver", scriptmap.get("browserpath").toString());
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(ieCapabilities);
			}
			if(scriptmap.get("browserpath").toString().contains("firefox")){
				System.setProperty("webdriver.firefox.bin", scriptmap.get("browserpath").toString());
				driver = new FirefoxDriver();
				
			}
			if(scriptmap.get("browserpath").toString().contains("chrome")){
				System.setProperty("webdriver.chrome.driver", scriptmap.get("browserpath").toString());
				driver = new ChromeDriver();
			}
		}catch(Exception e){
			return "浏览器调用失败";
		}
		
		// 全局设置延迟，如果操作无响应，则等待最多10S
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		// 浏览器最大化
		 driver.manage().window().maximize();

		//开始测试
		sql="update testmng set testresult='' where projectid=? and (testname like ? or testno like ?)";
		jdbcTemplate.update(sql,new Object[]{projectid,"%"+search_data+"%","%"+search_data+"%"});
			
		//获取所有案例
		sql="select CAST(SUBSTRING_INDEX(a.testno, '-', 1) as SIGNED) as testno1, a.* from testmng a where a.projectid=? and (a.testname like ? or a.testno like ?) and a.status=1 and a.selenium_share_status=0 "
				+ "order by CAST(SUBSTRING_INDEX(a.testno, '-', 1) as SIGNED),a.testname, CAST(SUBSTRING_INDEX(a.testno, '-', -1) as SIGNED) asc";
		List listgroup=jdbcTemplate.queryForList(sql,new Object[]{projectid,"%"+search_data+"%","%"+search_data+"%"});
		
		//循环案例
		int testno1=0;
		for(int j=0;j<listgroup.size();j++){
			Map testmap=(Map)listgroup.get(j);
			
			System.out.println("======开始案例:"+testmap.get("testname")+"||"+testmap.get("testno")+"======");
			result="";
			
			//获取案例脚本
			sql="select a.*,b.testno,b.testname from script a, testmng b where a.testid=b.testid and a.testid=?  order by a.step asc";
			list=jdbcTemplate.queryForList(sql,new Object[]{testmap.get("testid")});
			if(list.size()==0){
				sql="update testmng set testresult=? where testid=?";
				jdbcTemplate.update(sql,new Object[]{"未找到测试脚本",testmap.get("testid")});
				continue;
			}
			
			//客户端从这里开始复制代码
			try{
				//循环脚本
				int testid=0;
				for(int i=0;i<list.size();i++){
					scriptmap=(Map)list.get(i);
					System.out.println("开始执行selenium脚本第"+scriptmap.get("step")+"步");
					
					//每个脚本的公共方法
					String result1="";
					if(Integer.parseInt(scriptmap.get("scripttype").toString())==70 || 
							Integer.parseInt(scriptmap.get("scripttype").toString())==71){
						if(Integer.parseInt(scriptmap.get("scripttype").toString())==71){
							result1=selenium_obj_70(driver,scriptmap,1);
						}else{
							//1.判断是否有公共脚本的页面，如果找不到，则重新运行公共脚本。2.如果案例大编号变更，则需要执行公共脚本
							if(!switchToWindow(driver,scriptmap.get("xpath").toString()) ||
									testno1!=Integer.parseInt(testmap.get("testno1").toString())){
								//每套案例开始，都只保留一个窗口
								switchToWindowclose(driver);
								result1=selenium_obj_70(driver,scriptmap,1);
								
								//记录案例大编号
								testno1=Integer.parseInt(testmap.get("testno1").toString());
								
								//增加一个testid记录，如果同一个案例脚本调用了多个公共脚本案例时，从第二个公共脚本开始都要执行
//								testid=Integer.parseInt(scriptmap.get("testid").toString());
							}
//							else{
//								if(testid==Integer.parseInt(scriptmap.get("testid").toString())){
//									result1=selenium_obj_70(driver,scriptmap,1);
//								}
//							}
						}
					}else{
						result1=selenium_obj(driver,scriptmap,1);
					}
					if(!"".equals(result1)){
						result=result1;
						if(result.contains("存在BUG")){
							break;
						}
					}
				}
			}catch (Exception e){
				result="selenium脚本第"+scriptmap.get("step")+"步，测试异常失败";
				System.out.println(result);
				System.out.println(e);
			}
			//客户端从这里结束复制代码
			
			sql="update testmng set testresult=? where testid=?";
			jdbcTemplate.update(sql,new Object[]{result,testmap.get("testid")});
			
//			for(int i=0;i<list.size();i++){
//				scriptmap=(Map)list.get(i);
//				if(Integer.parseInt(scriptmap.get("scripttype").toString())==1){
//					driver.findElement(By.xpath(scriptmap.get("xpath").toString())).clear();	
//				}
//			}
		}
		return "ok";
	}
	
	public String selenium_obj_70(WebDriver driver,Map scriptmap,int status){
		String result="";
		String sql=null;
		List share_list=null;
		
		JSONArray selenium_share=JSONArray.fromObject(scriptmap.get("testvalue"));
		for(int i1=0;i1<selenium_share.size();i1++){
			JSONObject share_json=selenium_share.getJSONObject(i1);
			//获取案例脚本
			sql="select a.*,b.testno,b.testname from script a, testmng b where a.testid=b.testid and a.testid=? order by a.step asc";
			share_list=jdbcTemplate.queryForList(sql,new Object[]{share_json.getString("testid")});
			if(share_list.size()==0){
//				sql="update testmng set testresult=? where testid=?";
//				jdbcTemplate.update(sql,new Object[]{"公共脚本第"+scriptmap.get("step")+"步,未找到关联案例脚本",scriptmap.get("testid")});
				driver.quit();
				return "公共脚本第"+scriptmap.get("step")+"步,未找到关联案例脚本";
			}
			for(int i2=0;i2<share_list.size();i2++){
				Map share_map=(Map)share_list.get(i2);
				System.out.println("公共脚本第"+share_map.get("step")+"步");
				result=selenium_obj(driver,share_map,status);
			}
		}
		return result;
	}
	
	
	public String selenium_obj(WebDriver driver,Map scriptmap,int status){
		String result="";
		
		//是否输入新网址
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==0){
			if(StringUtil.isBlank(scriptmap.get("testurl").toString())){
				return result="第"+scriptmap.get("step")+"步，未找到测试网址";
			}
			// 浏览器输入地址
			driver.get(scriptmap.get("testurl").toString());
		}
		
		//是否为输入的脚本
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==1){
			driver.findElement(By.xpath(scriptmap.get("xpath").toString())).clear();
			driver.findElement(By.xpath(scriptmap.get("xpath").toString())).sendKeys(scriptmap.get("testvalue").toString());	
		}
		
		//是否为批量输入的脚本
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==2){
			WebElement table = driver.findElement(By.xpath(scriptmap.get("xpath").toString()));
			List<WebElement> inputs = table.findElements(By.tagName("input"));
			for(int k=0;k<inputs.size();k++){
				WebElement input=inputs.get(k);
				if(input.getAttribute("type").equals("hidden")){
					continue;
				}else if(input.getAttribute("type").equals("radio")){
					input.click();
				}else if(input.getAttribute("type").equals("checkbox")){
					input.click();
				}else if(input.getAttribute("type").equals("text")){
					input.clear();
					input.sendKeys(scriptmap.get("testvalue").toString());
				}
			}	
			
//			WebElement table = driver.findElement(By.xpath(scriptmap.get("xpath").toString()));
//			List<WebElement> rows = table.findElements(By.tagName("tr"));
//			for (WebElement row : rows) {
//				List<WebElement> cols = row.findElements(By.tagName("td"));
//				for (WebElement col : cols) {
//					System.out.println(col.getText()+"\t");
//					if(!"".equals(col.getText())){
//						continue;
//					}
//					WebElement input = col.findElement(By.tagName("input"));
//					if(input.getAttribute("type").equals("hidden")){
//						continue; 
//					}else if(input.getAttribute("type").equals("radio")){
//						input.click();
//					}else if(input.getAttribute("type").equals("text")){
//						input.clear();
//						input.sendKeys(scriptmap.get("testvalue").toString());
//					}
//				}
//			}
			
		}
		
		//是否为iframe页面
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==3){
			WebElement iframe = driver.findElement(By.xpath(scriptmap.get("xpath").toString()));
	        driver.switchTo().frame(iframe);
		}
		
		//是否为iframe切回到上一级iframe页面
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==4){
	        driver.switchTo().parentFrame();  //# 如果当前已是主文档，则无效果
		}
		
		//是否为iframe切回到主文档页面
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==5){
			driver.switchTo().defaultContent();
		}
		
		//是否为点击事件
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==88){
			driver.findElement(By.xpath(scriptmap.get("xpath").toString())).click();	
		}
		
		//是否为断言,全等比较
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==98){
			result=driver.findElement(By.xpath(scriptmap.get("xpath").toString())).getText();
			if("".equals(result)){
				result=driver.findElement(By.xpath(scriptmap.get("xpath").toString())).getAttribute("value");
			}
			System.out.println("页面获取测试对象是：" + result+",断言对象是："+scriptmap.get("testvalue").toString());
			if (result.equals(scriptmap.get("testvalue").toString())) {
				result="测试通过";
				System.out.println("测试案例："+scriptmap.get("testname")+"||"+scriptmap.get("testno")+"||"+result);
			} else {
				result="存在BUG(步："+scriptmap.get("step")+")";
				System.out.println("测试案例："+scriptmap.get("testname")+"||"+scriptmap.get("testno")+"||"+result);
			}
		}
		
		//是否为断言，包含比较
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==99){
			result=driver.findElement(By.xpath(scriptmap.get("xpath").toString())).getText();
			if("".equals(result)){
				result=driver.findElement(By.xpath(scriptmap.get("xpath").toString())).getAttribute("value");
			}
			System.out.println("页面获取测试对象是：" + result+",断言对象是："+scriptmap.get("testvalue").toString());
			if (result.contains(scriptmap.get("testvalue").toString())) {
				result="测试通过";
				System.out.println("测试案例："+scriptmap.get("testname")+"||"+scriptmap.get("testno")+"||"+result);
			} else {
				result="存在BUG(步："+scriptmap.get("step")+")";
				System.out.println("测试案例："+scriptmap.get("testname")+"||"+scriptmap.get("testno")+"||"+result);
			}
		}
		
		//0不保留窗口，1保留窗口
		if(status==0){
			//是否关闭当前窗口
			if(Integer.parseInt(scriptmap.get("scripttype").toString())==100){
				driver.close();
			}
			
//			是否关闭所有窗口
			if(Integer.parseInt(scriptmap.get("scripttype").toString())==102){
				driver.quit();
			}
		}
		
		//是否是新标签页面
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==111){
			WebElement element = null;
            element = driver.findElement(By.tagName("body"));
            element.sendKeys(Keys.CONTROL + "t");
			driver.get(scriptmap.get("xpath").toString());  
		}
		
		//是否切换窗口
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==112){
			switchToWindow(driver,scriptmap.get("xpath").toString());
		}
		
		//等待时间
		if(Integer.parseInt(scriptmap.get("scripttype").toString())==10){
			int time=Integer.parseInt(scriptmap.get("testvalue").toString().trim());
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//切换新开窗口
	public boolean switchToWindow(WebDriver driver,String windowTitle){  
	    boolean flag = false;  
	    try {  
	    	//获取当前窗口的唯一标示符
	        String currentHandle = driver.getWindowHandle(); 
	        //获取浏览器所有窗口set集合
	        Set<String> handles = driver.getWindowHandles(); 
	        
	        if(driver.getCurrentUrl().contains(windowTitle)){
//        		System.out.println("当前已在: " + windowTitle ); 
        		flag = true;
        	}else{
        		for (String s : handles) {
//	   	        	System.out.println(driver.getCurrentUrl());
	   	        	//如果窗口的唯一标示符=当前窗口，则循环下一个窗口
	   	            if (s.equals(currentHandle)){
	   	            	continue;
	   	            }else {  
	   	            	//如果窗口的唯一标示符<>当前窗口,则切换
	   	                driver.switchTo().window(s);  
//	   	                System.out.println(driver.getCurrentUrl());
	   	                //判断切换的窗口的唯一标示符是否=自己想要的窗口，如果是则返回true
	   	                if (driver.getCurrentUrl().contains(windowTitle)) { 
	   	                	//找到窗口，并返回true
	   	                    flag = true;  
	   	                    System.out.println("切换到新窗口成功: "  
	   	                            + windowTitle );  
	   	                    break;  
	   	                } else  
	   	                    continue;  
	   	            }  
        		}  
        	};
	    } catch (Exception e) {  
//	    	 System.out.println("Window: " + windowTitle  
//		                + " cound not found!", e.fillInStackTrace());
	        flag = false;  
	    }  
	    return flag;  
	}  
	
	//关闭多余窗口，保留最后一个
	public void switchToWindowclose(WebDriver driver){  
	    try {  
	        //获取浏览器所有窗口set集合
	        Set<String> handles = driver.getWindowHandles(); 
	        
	        int handlessum=handles.size();
	        for (String s : handles) {
	        	//先切换窗口
                driver.switchTo().window(s); 
                
	        	if(handlessum==1){
	        		return;
	        	}
                driver.close();
                handlessum=handlessum-1;
	        }  
	    } catch (Exception e) {  
	       System.out.println("关闭浏览器窗口异常");
	    }  
	}  
}
