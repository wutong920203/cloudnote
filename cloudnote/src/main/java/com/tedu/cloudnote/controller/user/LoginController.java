package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.entity.Admin;
import com.tedu.cloudnote.service.LoginService;
import com.tedu.cloudnote.util.NoteResult;


@Controller
public class LoginController {
	@Resource(name="loginService")
	LoginService service;
	
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		return "redirect:log_in.html";
	}
	
	
	@RequestMapping("/user/login.do")
	@ResponseBody
	public NoteResult execute(String username,String password,HttpSession session){
		//测试异常，故意产生一个异常
		//String str = null;
		//str.toString();
		//调用业务层代码来处理
		NoteResult result = service.checkLogin(username, password);
		//登录成功后，绑定一些数据到session对象上。
		Admin user = (Admin) result.getData();
		session.setAttribute("user", user);
		System.out.println(user);
		return result;
	}
}
