package com.tedu.cloudnote.controller.activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.ActivityService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class SaveNoteController {
	@Resource
	private ActivityService activityService;
	
	@RequestMapping("/activity/save_note.do")
	@ResponseBody
	public NoteResult execute(String userId,String noteActId){
		NoteResult result = activityService.saveNote(userId, noteActId);
		return result;
	}
}
