package com.ch.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.service.Logmessagebean;

@Controller
@RequestMapping("/log")
public class LogmessageAction {
	
	@Autowired
	Logmessagebean logmessagebean;
	
	@RequestMapping("/log")
	public ModelAndView news(HttpServletRequest req){
		ModelAndView mv=new ModelAndView("/news/news");
		mv.addObject("userid", req.getParameter("userid"));
		return mv;
	}
	
	@RequestMapping("/testmessage")
	@ResponseBody
	public Map testmessage(){
		Map map=null;
		map=logmessagebean.testmessage();
		return map;
	}
	
	@RequestMapping("/learnmessage")
	@ResponseBody
	public Map learnmessage(){
		Map map=null;
		map=logmessagebean.learnmessage();
		return map;
	}
	
}
