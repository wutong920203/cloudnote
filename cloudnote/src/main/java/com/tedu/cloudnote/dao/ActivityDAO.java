package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.ActNote;
import com.tedu.cloudnote.entity.Activity;

public interface ActivityDAO {
	public ActNote findById(String noteActId);
	public int addNew(ActNote note);
	public ActNote getNote(String noteId);
	public List<ActNote> findNotes(String actId);
	public List<Activity> findAll();
}
