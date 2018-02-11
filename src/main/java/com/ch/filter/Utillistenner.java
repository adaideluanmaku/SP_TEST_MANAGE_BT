package com.ch.filter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ch.service.SocketBean;

/**
 * 
 * @author 陈辉
 *系统运行开始时初始化执行
 *
 */
//Spring扫描此文件注释
public class Utillistenner{
	private static Logger log=Logger.getLogger(Utillistenner.class);
	
	@Autowired
	SocketBean socketBean;
	
	@PostConstruct
	public void sys_init(){
		socketBean.setusers();
		System.out.println("系统初始化==================");
		log.info("系统初始化==================");
		
	}
	
    public void  dostory(){  
		System.out.println("系统注销==================");
		log.info("系统注销==================");
    }
}
