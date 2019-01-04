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
	
	@RequestMapping("/pass_project")
	public ModelAndView pass_project(){
		return new ModelAndView("pass/pass_project");
	}

	@RequestMapping("/pass_testmng")
	public ModelAndView pass_testmng(){
		return new ModelAndView("pass/pass_testmng");
	}
	
	@RequestMapping("/pass_tools")
	public ModelAndView pass_tools(){
		return new ModelAndView("pass/pass_tools");
	}
	
	//PA目录
	@RequestMapping("/pa_graph")
	public ModelAndView graph_pa(){
		return new ModelAndView("pa/pa_graph");
	}
	
	@RequestMapping("/pa_team")
	public ModelAndView pa_team(){
		return new ModelAndView("pa/pa_team");
	}
	
	@RequestMapping("/pa_project")
	public ModelAndView pa_project(){
		return new ModelAndView("pa/pa_project");
	}

	@RequestMapping("/pa_testmng")
	public ModelAndView pa_testmng(){
		return new ModelAndView("pa/pa_testmng");
	}

	//用药研究
	@RequestMapping("/prescription")
	public ModelAndView prescription(){
		return new ModelAndView("prescription/prescription");
	}
	
	//政府项目目录
	@RequestMapping("/zfxm_graph")
	public ModelAndView graph_zfxm(){
		return new ModelAndView("zfxm/zfxm_graph");
	}
	
	@RequestMapping("/zfxm_team")
	public ModelAndView zfxm_team(){
		return new ModelAndView("zfxm/zfxm_team");
	}
	
	@RequestMapping("/zfxm_project")
	public ModelAndView zfxm_project(){
		return new ModelAndView("zfxm/zfxm_project");
	}

	@RequestMapping("/zfxm_testmng")
	public ModelAndView zfxm_testmng(){
		return new ModelAndView("zfxm/zfxm_testmng");
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
	
	@RequestMapping("/works_jihuaguanli")
	public ModelAndView works_jihuaguanli(){
		return new ModelAndView("works/works_jihuaguanli");
	}
	
	@RequestMapping("/works_zhouhuibao")
	public ModelAndView works_zhouhuibao(){
		return new ModelAndView("works/works_zhouhuibao");
	}
	
	@RequestMapping("/works_yuehuibao")
	public ModelAndView works_yuehuibao(){
		return new ModelAndView("works/works_yuehuibao");
	}
	
	//系统设置
	@RequestMapping("/sysmanage_serverip")
	public ModelAndView sysmanage_serverip(){
		return new ModelAndView("sysmanage/serverip");
	}
	
	@RequestMapping("/sysmanage_users")
	public ModelAndView sysmanage_users(){
		return new ModelAndView("sysmanage/users");
	}
	
	@RequestMapping("/sysmanage_database")
	public ModelAndView sysmanage_database(){
		return new ModelAndView("sysmanage/database");
	}
}
