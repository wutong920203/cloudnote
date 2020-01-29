package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Notebook;

public interface NotebookDAO {
	public String findCollectBook(String userId);
	public int deleteBook (String bookId);
	public List<Notebook> findNotebookById(String user_id);
	public int createNewBook(Notebook book);
	public Notebook findNote(String bookId);
	public int updateName(Notebook book);
}
