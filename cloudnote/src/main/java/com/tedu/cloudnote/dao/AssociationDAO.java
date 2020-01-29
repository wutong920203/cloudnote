package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Notebook;

public interface AssociationDAO {
	public List<Notebook> findAllBook();
	public Notebook findById(String bookId);
}
