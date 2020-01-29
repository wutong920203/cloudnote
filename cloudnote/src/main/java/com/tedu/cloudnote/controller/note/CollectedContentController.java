package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class CollectedContentController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/collect_content.do")
	@ResponseBody
	public NoteResult execute(String noteId){
		NoteResult result = noteService.findContent(noteId);
		return result;
	}
}
