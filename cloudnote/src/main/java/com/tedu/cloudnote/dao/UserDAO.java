package com.tedu.cloudnote.dao;

import com.tedu.cloudnote.entity.Admin;

/**
 * DAO接口
 * 注意：接口方法不要涉及任何具体的技术。
 */

public interface UserDAO {
	//注册新用户
	public int save(Admin user);
	public int changePwd(Admin user);
	public Admin findById(String userId);
	public Admin findByName(String user_name);
	
}
