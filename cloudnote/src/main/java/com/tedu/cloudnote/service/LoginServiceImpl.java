package com.tedu.cloudnote.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.NotebookDAO;
import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.entity.Admin;
import com.tedu.cloudnote.entity.Notebook;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserDAO userDao;
	@Resource
	private NotebookDAO notebookDao;
	
	//登录
	public NoteResult checkLogin(String user_name,String user_password) {
		//创建返回值
		NoteResult result = new NoteResult();
		Admin admin = userDao.findByName(user_name);
		if(admin==null){
			result.setStatus(1);
			result.setMsg("用户名或密码错误");
			return result;
		}
		try {
			String md5_pwd = NoteUtil.md5(user_password);
			if(!md5_pwd.equals(admin.getCn_user_password())){
				//密码错误
				result.setStatus(1);
				result.setMsg("用户名或密码错误");
				return result;
			}else {
				//登录成功
				result.setStatus(0);
				result.setMsg("登录成功");
				admin.setCn_user_password("");
				result.setData(admin);
				return result;
			}
		} catch (Exception e) {
			throw new NoteException("密码加密异常",e);
		}
		
		
	}
	
	//修改密码
	public NoteResult changepwd(String userId, String lastPwd, String finalPwd, HttpSession session) {
		Admin sUser = (Admin) session.getAttribute("user");
		String sessionId = sUser.getCn_user_id();
		NoteResult result = new NoteResult();
		if(!sessionId.equals(userId)){
			result.setStatus(1);
			result.setMsg("登录信息异常，请重新登录");
			return result;
		}
		Admin user = userDao.findById(userId);
		if(user == null){
			result.setStatus(1);
			result.setMsg("登录信息异常，请重新登录");
			return result;
		}
		try {
			String md5_pwd = NoteUtil.md5(lastPwd);
			//验证原密码
			if(!md5_pwd.equals(user.getCn_user_password())){
				result.setStatus(1);
				result.setMsg("原密码输入错误");
				return result;
			}else{
				String password = NoteUtil.md5(finalPwd);
				user.setCn_user_password(password);
				int rows = userDao.changePwd(user);
				if(rows != 1){
					result.setStatus(1);
					result.setMsg("修改密码失败");
					return result;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new NoteException("密码加密异常",e);
		}
		
		result.setStatus(0);
		result.setMsg("修改密码成功！");
		return result;
	}
	
	//检测用户名是否被占用
	public Admin checkUsername(String username) {
		Admin admin = userDao.findByName(username);
		if(admin == null){	
			return null;
		}else{
			return admin;
		}
	}
	
	@Transactional
	//注册新用户
	public NoteResult addUser(String name,String nickname,String password){
		NoteResult result = new NoteResult();
		try {
			//检测是否重名
			Admin has_user = userDao.findByName(name);
			if(has_user != null){
				result.setStatus(1);
				result.setMsg("用户名已被占用");
				return result;	
			}
			//执行用户注册
			Admin admin = new Admin();
			admin.setCn_user_name(name);//设置用户名
			admin.setCn_user_nick(nickname);//设置昵称
			String md5_pwd = NoteUtil.md5(password);
			admin.setCn_user_password(md5_pwd);//设置密码
			String userId = NoteUtil.createId();
			admin.setCn_user_id(userId);
			int rows = userDao.save(admin);
			//注册成功后，为它自动创造一个收藏笔记本
			int bookrows = 0;
			if(rows == 1){
				Notebook book = new Notebook();
				book.setCn_user_id(userId);
				book.setCn_notebook_name("收藏笔记本");
				String bookId = NoteUtil.createId();
				book.setCn_notebook_id(bookId);//设置笔记本ID
				Timestamp time = new Timestamp(System.currentTimeMillis());
				book.setCn_notebook_createtime(time);//设置创建时间
				book.setCn_notebook_type_id("1");//设置type为1，收藏笔记本
				//创建笔记本
				bookrows = notebookDao.createNewBook(book);
			}
			if(bookrows != 1){
				result.setStatus(1);
				result.setMsg("注册失败");
				return result;
			}
			System.out.println("这里是测试！！！"+rows);
			//创建返回结果
			result.setStatus(0);
			result.setMsg("注册成功");
			return result;
			
		} catch (Exception e) {
			throw new NoteException("用户注册异常",e);
		}
	}
	
	
	
}
