package com.ch.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ch.easyui.DataGrid;
import com.ch.service.Ordersbean;
import com.ch.service.Userbean;


@Controller
@RequestMapping("/orders")
public class OrdersAction {
	@Autowired
	Ordersbean ordersbean;
	
//	@RequestMapping("/order")
//	@ResponseBody
//	public void order(HttpServletRequest req){
//		ordersbean.order(req); 
//	}
	
	@RequestMapping("/testmng_json_update")
	@ResponseBody
	public String json_update(HttpServletRequest req){
		ordersbean.json_update(req); 
		return "ok";
	}
}
