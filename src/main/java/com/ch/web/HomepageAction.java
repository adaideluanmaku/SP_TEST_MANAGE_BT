package com.ch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/homepage")
public class HomepageAction {
	//首页目录
	@RequestMapping("/home_graph")
	public ModelAndView graph(){
		return new ModelAndView("home/home_graph");
	}
	
	//PASS目录
	@RequestMapping("/pass_graph")
	public ModelAndView graph_pass(){
		return new ModelAndView("pass/pass_graph");
	}
	
	@RequestMapping("/pass_team")
	public ModelAndView pass_team(){
		return new ModelAndView("pass/pass_team");
	}

	//PA目录
	@RequestMapping("/pa_graph")
	public ModelAndView graph_pa(){
		return new ModelAndView("pa/pa_graph");
	}
	
	//政府项目目录
	@RequestMapping("/zfxm_graph")
	public ModelAndView graph_zfxm(){
		return new ModelAndView("zfxm/zfxm_graph");
	}
	
	//学习目录
	@RequestMapping("/learn_graph")
	public ModelAndView graph_learn(){
		return new ModelAndView("learn/learn_graph");
	}
	
	//工作目录
	@RequestMapping("/works_graph")
	public ModelAndView graph_works(){
		return new ModelAndView("works/works_graph");
	}
}
