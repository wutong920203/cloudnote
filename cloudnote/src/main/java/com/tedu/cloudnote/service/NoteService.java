package com.tedu.cloudnote.service;


import javax.servlet.http.HttpServletRequest;

import com.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult findTypeNote(String userId,String type);
	public NoteResult searchNotes(String title,String status,String beginStr,String endStr);
	public NoteResult findContent(String noteId);
	public NoteResult loadCollectedNote(String userId);
	public NoteResult collectNote(String userId,String shareId);
	public NoteResult completelyDelete(String noteId);
	public NoteResult updateReplay(String bookId,String noteId);
	public NoteResult findRecycle(String userId);
	public NoteResult findNotes(HttpServletRequest req,String notebookId);
	public NoteResult update(String cn_note_id,String cn_note_body,String cn_note_title);
	public NoteResult createNewNote(String userId,String notebookId,String title);
	public NoteResult deleteNote(String noteId);
	public NoteResult shareNote(String noteId);
	public NoteResult moveNote(String noteId, String bookId);
	public NoteResult searchShareNote(String keyword,int page);
	public NoteResult findByShareId(String shareId);
}
