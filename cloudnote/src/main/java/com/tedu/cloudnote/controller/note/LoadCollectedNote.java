package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class LoadCollectedNote {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/load_collect_note.do")
	@ResponseBody
	public NoteResult execute(String userId){
		NoteResult result = noteService.loadCollectedNote(userId);
		return result;
	}
}
