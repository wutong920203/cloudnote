package com.tedu.cloudnote.controller.note;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class NoteController {
	
	@Resource(name="noteService")
	private NoteService service;
	
	@RequestMapping("/note/loadnotes.do")
	@ResponseBody
	public NoteResult findNotes(HttpServletRequest req,String bookId){
		NoteResult notes= service.findNotes(req, bookId);
		return notes;
	}
	
	@RequestMapping("/note/content.do")
	@ResponseBody
	public NoteResult note(String noteId){
		
		//NoteResult result = new NoteResult();
		NoteResult result = service.findContent(noteId);
		//从session中得到笔记的map表
//		List<Map> note = (List<Map>) session.getAttribute("notes");
//		
//		Map map = null;
//		for(Map m:note){
//			String mapid = (String) m.get("cn_note_id");
//			if(noteId.equals(mapid)){
//				//得到内容
//				map = m;
//				break;
//			}
//		}
//		//存入result
//		System.out.println(map);
//		result.setData(map);
		
		return result;
	}
	
	@RequestMapping("/note/update.do")
	@ResponseBody
	public NoteResult update(String content,String noteId,String title,HttpSession session){
		//运行保存
		NoteResult result = service.update(noteId,content,title);
		//改变session中的内容
//		List<Map> note = (List<Map>) session.getAttribute("notes");
//		for(Map m:note){
//			String mapId = (String) m.get("cn_note_id");
//			if(noteId.equals(mapId)){
//				//更新map里的内容
//				m.remove("cn_note_title");
//				m.remove("cn_note_body");
//				m.put("cn_note_title", title);
//				m.put("cn_note_body", content);
//				break;
//			}
//		}
//		session.setAttribute("notes", note);
		return result;
	}
	
	@RequestMapping("/note/add.do")
	@ResponseBody
	public NoteResult createNew(String userId,String notebookId,String title){
		NoteResult result = service.createNewNote(userId, notebookId, title);
		return result;
	}
	
	@RequestMapping("/note/delete.do")
	@ResponseBody
	public NoteResult deleteNote(String noteId){
		NoteResult result = service.deleteNote(noteId);
		return result;
		
	}
	@RequestMapping("/note/move.do")
	@ResponseBody
	public NoteResult moveNote(String noteId,String bookId){
		NoteResult result = service.moveNote(noteId, bookId);
		return result;
	}
	
	
}
