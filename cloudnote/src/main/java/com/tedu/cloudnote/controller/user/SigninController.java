package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.entity.Admin;
import com.tedu.cloudnote.service.LoginService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class SigninController {
	
	@Resource
	private LoginService loginService;
	
	@RequestMapping("/user/checkUsername.do")
	@ResponseBody
	public Admin checkUsername(String username){
		Admin user = loginService.checkUsername(username);
		return user;
	}
	
	@RequestMapping("/user/save.do")
	@ResponseBody
	public NoteResult save(String username,String nickname,String password){
		NoteResult result = loginService.addUser(username, nickname, password);
		return result;
	}
}
