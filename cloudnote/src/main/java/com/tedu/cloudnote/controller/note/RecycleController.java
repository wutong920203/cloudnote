package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class RecycleController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/recycle.do")
	@ResponseBody
	public NoteResult execute(String userId){
		NoteResult result = noteService.findRecycle(userId);
		return result;
	}
	
	@RequestMapping("/note/recycle_replay.do")
	@ResponseBody
	public NoteResult replay(String bookId,String noteId){
		NoteResult result = noteService.updateReplay(bookId, noteId);
		return result;
	}
	
	@RequestMapping("/note/recycle_delete.do")
	@ResponseBody
	public NoteResult completelyDelete(String noteId){
		NoteResult result = noteService.completelyDelete(noteId);
		return result;
	}
	
}
