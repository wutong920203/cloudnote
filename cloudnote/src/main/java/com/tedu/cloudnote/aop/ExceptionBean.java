package com.tedu.cloudnote.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 切面：将异常信息写入文件
 * @author soft01
 *
 */
@Component//扫描
@Aspect//定义为切面
public class ExceptionBean {
	//指定异常通知和切入点表达式
	@AfterThrowing(throwing="e",pointcut="within(com.tedu.cloudnote.controller..*)")
	public void execute(Exception e){//e就是目标方法抛出的异常对象
//		System.out.println("将异常信息写入文件"+e);
		try {
			//将e对象信息写入note_error.log文件
			//FileWriter fw = new FileWriter("/home/soft01/note_error.log",true);
			FileWriter fw = new FileWriter("/Users/WuTong/Documents/error/note_error.log",true);
			PrintWriter pw = new PrintWriter(fw);
			//获取异常发生时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			pw.println("********************************************");
			pw.println("*发生时间："+time);
			pw.println("*异常类型："+e);
			pw.println("***************异常详情**********************");
			//将异常栈信息打印
			e.printStackTrace(pw);
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
