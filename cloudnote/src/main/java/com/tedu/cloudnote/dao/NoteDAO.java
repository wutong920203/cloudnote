package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Note;

public interface NoteDAO {
	public List<Note> findTypeNote(Map<String,Object> params);
	public int batchDelete(String[] ids);
	public int dynamicUpdate(Note note);
	public List<Note> findNotes(Map<String,Object> params);
	public int completelyDelete(String noteId);
	public int updateReplay(Note note);
	public List<Note> findRecycle(String userId);
	public List<Map<String,Object>> findNoteById(String notebookId);
	public Note findById(String noteId);
	public int updateNote(Note note);
	public int createNewNote(Note note);
	public int updateStatus(String noteId);
	public int updateBookId(Note note);
}
