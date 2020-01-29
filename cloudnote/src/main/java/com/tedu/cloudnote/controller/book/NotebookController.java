package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NotebookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class NotebookController {
	
	@Resource(name="notebookService")
	private NotebookService service;
	
	@RequestMapping("/book/notebooks.do")
	@ResponseBody
	public NoteResult findNotebooks(HttpServletRequest req){
		NoteResult result = service.findNotebooks(req);
		return result;
	}
	
	@RequestMapping("/book/add.do")
	@ResponseBody
	public NoteResult execute(String userId,String name){
		NoteResult result = service.createBook(userId, name);		
		return result;
	}
	
	@RequestMapping("/book/rename.do")
	@ResponseBody
	public NoteResult rename(String bookId,String bookName){
		NoteResult result = service.rename(bookId, bookName);
		return result;
	}
}
