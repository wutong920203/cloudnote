package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class SearchNoteController {
	
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/height_search.do")
	@ResponseBody
	public NoteResult execute(String title, String status,String beginStr,String endStr){
		NoteResult result = noteService.searchNotes(title, status, beginStr, endStr);
		return result;
	}
}
