package com.tedu.cloudnote.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.NotebookDAO;
import com.tedu.cloudnote.entity.Admin;
import com.tedu.cloudnote.entity.Notebook;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("notebookService")
public class NotebookServiceImpl implements NotebookService {
	
	@Resource(name="notebookDAO")
	private NotebookDAO dao;
	public NoteResult findNotebooks(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("user");
		String userid = admin.getCn_user_id();
		//得到该用户的所有笔记本信息
		List<Notebook> list = dao.findNotebookById(userid);
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("查询完毕");
		result.setData(list);
		//
		session.setAttribute("notebooks", list);
		return result;
	}
	
	public NoteResult createBook(String userId, String name) {
		
		Notebook book = new Notebook();
		book.setCn_user_id(userId);//设置用户ID
		book.setCn_notebook_name(name);//设置笔记本名
		String bookId = NoteUtil.createId();
		book.setCn_notebook_id(bookId);//设置笔记本ID
		Timestamp time = new Timestamp(System.currentTimeMillis());
		book.setCn_notebook_createtime(time);//设置创建时间
		book.setCn_notebook_type_id("5");//
		dao.createNewBook(book);//保存笔记本
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("创建笔记本成功");
		result.setData(book);//返回添加的笔记本信息
		return result;
		
	}

	public NoteResult rename(String bookId, String bookName) {
		Notebook book = dao.findNote(bookId);
		book.setCn_notebook_name(bookName);
		int rows = dao.updateName(book);
		//创建返回结果
		NoteResult result = new NoteResult();
		if(rows>=1){
			result.setStatus(0);
			result.setMsg("重命名成功");
		}else{
			result.setStatus(1);
			result.setMsg("重命名失败");
		}
		return result;
	}

	
	public NoteResult deleteBook(String bookId) {
		NoteResult result = new NoteResult();
		int rows = dao.deleteBook(bookId);
		if(rows!=1){
			result.setStatus(1);
			result.setMsg("删除失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("删除成功");
		return result;
	}
	
}
