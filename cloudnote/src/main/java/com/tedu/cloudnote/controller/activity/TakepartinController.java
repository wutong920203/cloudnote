package com.tedu.cloudnote.controller.activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.ActivityService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class TakepartinController {
	@Resource
	private ActivityService activityService;
	
	@RequestMapping("/activity/take_part_in.do")
	@ResponseBody
	public NoteResult execute(String noteId,String actId){
		NoteResult result = activityService.addNew(noteId, actId);
		return result;
	}
}
