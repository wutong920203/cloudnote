package com.tedu.cloudnote.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tedu.cloudnote.entity.Admin;
import com.tedu.cloudnote.util.NoteResult;


/**
 * 业务接口
 */
public interface LoginService {
	public NoteResult changepwd(String userId,String lastPwd,String finalPwd,HttpSession session);
	public NoteResult checkLogin(String user_name,String user_password);
	//注册的两个方法
	public Admin checkUsername(String username);
	public NoteResult addUser(String name,String nickname,String password);
}
