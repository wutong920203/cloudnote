package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.LoginService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class ChangePwdController {
	
	@Resource
	private LoginService loginService;
	
	@RequestMapping("/user/change_pwd.do")
	@ResponseBody
	public NoteResult execute(String userId, String lastPwd, String finalPwd, HttpSession session){
		NoteResult result = loginService.changepwd(userId, lastPwd, finalPwd, session);
		return result;
	}
	
}
