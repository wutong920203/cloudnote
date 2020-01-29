package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NotebookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class DeleteBookController {
	
	@Resource
	private NotebookService notebookService;
	
	@RequestMapping("/book/delete_book.do")
	@ResponseBody
	public NoteResult execute(String bookId){
		NoteResult result = notebookService.deleteBook(bookId);
		return result;
	}
}
