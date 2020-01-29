package com.tedu.cloudnote.service;

import javax.servlet.http.HttpServletRequest;

import com.tedu.cloudnote.util.NoteResult;

public interface NotebookService {
	
	public NoteResult deleteBook(String bookId);
	public NoteResult findNotebooks(HttpServletRequest req);
	public NoteResult createBook(String userId, String name);
	public NoteResult rename(String bookId,String bookName);
	
}
