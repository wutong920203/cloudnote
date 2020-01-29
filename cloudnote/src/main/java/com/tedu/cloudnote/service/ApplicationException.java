package com.tedu.cloudnote.service;
/**
 * 应用异常
 * 	不是程序的问题，是不满足业务规则引起的错误，比如
 * 用户输入了不正确的密码。
 * @author soft01
 *
 */
public class ApplicationException extends RuntimeException {

	public ApplicationException() {
		
	}

	public ApplicationException(String message) {
		super(message);
	}
	
}
