package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class CollectController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/collect_note.do")
	@ResponseBody
	public NoteResult execute(String userId,String shareId){
		NoteResult result = noteService.collectNote(userId, shareId);
		return result;
	}
}
