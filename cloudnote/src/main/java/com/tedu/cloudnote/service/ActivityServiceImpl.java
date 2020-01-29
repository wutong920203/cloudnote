package com.tedu.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.ActivityDAO;
import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.dao.NotebookDAO;
import com.tedu.cloudnote.entity.ActNote;
import com.tedu.cloudnote.entity.Activity;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService{
	
	@Resource
	private ActivityDAO activityDao;
	@Resource
	private NoteDAO noteDao;
	@Resource
	private NotebookDAO notebookDao;
	
	public NoteResult findAll() {
		List<Activity> list = activityDao.findAll();
		NoteResult result = new NoteResult();
		if(list==null){
			result.setStatus(1);
			result.setMsg("加载活动失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("加载活动成功");
		result.setData(list);
		return result;
	}
	public NoteResult findNotes(String actId) {
		List<ActNote> list = activityDao.findNotes(actId);
		NoteResult result = new NoteResult();
		if(list==null){
			result.setStatus(1);
			result.setMsg("加载笔记失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("加载笔记成功");
		result.setData(list);
		return result;
	}
	public NoteResult getNote(String noteActId) {
		ActNote note = activityDao.getNote(noteActId);
		NoteResult result = new NoteResult();
		if(note==null){
			result.setStatus(1);
			result.setMsg("加载内容失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("加载内容成功");
		result.setData(note);
		return result;
	}
	
	public NoteResult addNew(String noteId, String actId) {
		//通过noteId找到对应的对象
		Note note = noteDao.findById(noteId);
		NoteResult result = new NoteResult();
		if(note==null){
			result.setStatus(1);
			result.setMsg("获取原笔记失败");
			return result;
		}
		//创建活动笔记对象
		ActNote anote = new ActNote();
		//将正常笔记的值导入活动笔记
		anote.setCn_activity_id(actId);
		anote.setCn_note_id(noteId);
		anote.setCn_note_activity_id(NoteUtil.createId());
		anote.setCn_note_activity_title(note.getCn_note_title());
		anote.setCn_note_activity_body(note.getCn_note_body());
		//添加活动笔记
		int rows = activityDao.addNew(anote);
		if(rows!=1){
			result.setStatus(1);
			result.setMsg("参加活动失败");
			return result;
		}
		//改变原笔记的状态为4，即参加活动状态
		note.setCn_note_type_id("4");
		rows = noteDao.updateNote(note);
		if(rows!=1){
			result.setStatus(1);
			result.setMsg("改变笔记状态失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("参加活动成功");
		return result;
	}
	public NoteResult saveNote(String userId, String noteActId) {
		//创建返回对象
		NoteResult result = new NoteResult();
		//取出活动笔记
		ActNote anote = activityDao.findById(noteActId);
		if(anote == null){
			result.setStatus(1);
			result.setMsg("获取收藏笔记内容失败");
			return result;
		}
		//创建笔记
		Note note = new Note();
		
		//暂时不用(取出活动笔记的noteId值)；如果noteId值和这个用户原来的一个笔记的noteId值相同（原来参加活动的笔记）
		anote.getCn_note_id();
		
		//为Note对象赋值
		note.setCn_user_id(userId);
		note.setCn_note_title(anote.getCn_note_activity_title());
		note.setCn_note_body(anote.getCn_note_activity_body());
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);
		//得到收藏笔记本的ID
		String notebookId = notebookDao.findCollectBook(userId);
		if(notebookId == null){
			result.setStatus(1);
			result.setMsg("此用户没有收藏笔记本！");
			return result;
		}
		note.setCn_notebook_id(notebookId);
		Long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);
		note.setCn_note_last_modify_time(time);
		//创建新笔记
		int rows = noteDao.createNewNote(note);
		
		if(rows != 1){
			result.setStatus(1);
			result.setMsg("收藏笔记失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("收藏笔记成功");
		return result;
	}

}
