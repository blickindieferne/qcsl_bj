package com.xzy.qcsl.jp.bj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xzy.qcsl.jp.bj.crawler.Client;
import com.xzy.qcsl.jp.bj.model.User;

@Controller
@RequestMapping("/*")
public class loginController {
	
	@RequestMapping("/login.do")
	public String login(){
		return "/login";  
	}
	
	@RequestMapping("/doLogin.do")
	public ModelAndView doLogin(HttpServletRequest request,String cookie,String domain){
		

		HttpSession session = request.getSession();
		
		User u = Client.getUserInfo(cookie,domain);
		if(u==null){
			u = new User();
			u.setUsername("test");
			u.setRealName("用户");
		}
		session.setAttribute("user", u);
		
		session.setAttribute("cookie", cookie);
		session.setAttribute("domain", domain);
		
		return new ModelAndView("redirect:/policy/list.do");
		
		
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		session.invalidate();
		return "/login";  
	}
}