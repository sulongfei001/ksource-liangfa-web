package com.ksource.liangfa.web.maintain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/maintain")
public class MaintainController {

	//后台登陆页
	@RequestMapping(method=RequestMethod.GET)
	public String maintainLogin(){
		return "maintain/maintain_login";
	}
	
	//登录到后台维护首页
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(){
		//TODO:后台授权登录
		return "maintain/maintain";
	}
}
