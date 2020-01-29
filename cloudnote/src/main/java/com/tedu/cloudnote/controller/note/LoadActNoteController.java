package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class LoadActNoteController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/action_note.do")
	@ResponseBody
	public NoteResult execute(String userId){
		String type = "4";//4为参加活动的笔记
		NoteResult result = noteService.findTypeNote(userId, type);
		return result;
	}
}
