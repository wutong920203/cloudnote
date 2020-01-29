package com.tedu.cloudnote.util;

/**
 * 自定义异常
 * @author soft01
 *
 */
public class NoteException extends RuntimeException {
	public NoteException(String msg,Throwable t){
		super(msg,t);
	}
}
