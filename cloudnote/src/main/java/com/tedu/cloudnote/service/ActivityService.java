package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface ActivityService {
	public NoteResult saveNote(String userId,String noteActId);
	public NoteResult addNew(String noteId,String actId);
	public NoteResult getNote(String noteId);
	public NoteResult findNotes(String actId);
	public NoteResult findAll();
}
