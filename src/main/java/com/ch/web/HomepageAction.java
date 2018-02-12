package com.ch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/homepage")
public class HomepageAction {
	@RequestMapping("/graph")
	public ModelAndView graph(){
		return new ModelAndView("statistics/graph");
	}
}
