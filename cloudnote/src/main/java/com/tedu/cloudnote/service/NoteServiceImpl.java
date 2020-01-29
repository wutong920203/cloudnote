package com.tedu.cloudnote.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.dao.NotebookDAO;
import com.tedu.cloudnote.dao.ShareDAO;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.Notebook;
import com.tedu.cloudnote.entity.Share;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;
@Service("noteService")
public class NoteServiceImpl implements NoteService {
	
	@Resource(name="noteDAO")
	private NoteDAO dao;
	
	@Resource(name="shareDAO")
	private ShareDAO shareDao;
	
	@Resource
	private NotebookDAO notebookDao;
	
	public NoteResult findNotes(HttpServletRequest req,String notebookId) {

		HttpSession session = req.getSession();
		//按笔记本ID查询笔记信息,返回的是noteId和noteTiele
		List<Map<String,Object>> list = dao.findNoteById(notebookId);
		//System.out.println(list);
		//
		session.setAttribute("notes", list);
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("查询完毕");
		result.setData(list);
		return result;
		
	}
	
	public NoteResult update(String cn_note_id,String cn_note_body,String cn_note_title){
		
		Note note = new Note();
		note.setCn_note_id(cn_note_id);//设置笔记ID
		note.setCn_note_title(cn_note_title);//设置标题
		note.setCn_note_body(cn_note_body);//设置内容
		Long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);//设置修改时间
		System.out.println(note);
		int rows = dao.updateNote(note);//更新
		//创建返回结果
		NoteResult result = new NoteResult();
		if(rows==1){//成功
			result.setStatus(0);
			result.setMsg("保存笔记成功");
		}else{//失败
			result.setStatus(1);
			result.setMsg("保存笔记失败");
		}
		return result;
	}
	
	public NoteResult createNewNote(String userId,String notebookId,String title){
		//创建note对象
		Note note = new Note();
		note.setCn_user_id(userId);
		note.setCn_notebook_id(notebookId);
		note.setCn_note_title(title);
		//设置一个空body
		note.setCn_note_body("");
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);
		Long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);
		note.setCn_note_last_modify_time(time);
		int rows = dao.createNewNote(note);
		//创建返回结果
		NoteResult result = new NoteResult();
		if(rows != 1){
			result.setStatus(1);
			result.setMsg("创建笔记本失败");
		}
		result.setStatus(0);
		result.setMsg("创建笔记本成功");
		result.setData(note);//返回添加的笔记信息
		return result;
		
	}
	
	public NoteResult deleteNote(String noteId){
		
		int rows = dao.updateStatus(noteId);
		NoteResult result = new NoteResult();
		if(rows >= 1){
			result.setStatus(0);
			result.setMsg("删除笔记成功");
		}else{
			result.setStatus(1);
			result.setMsg("删除笔记失败");
		}
		return result;
		
	}
	
	public NoteResult moveNote(String noteId, String bookId) {
			Note note = new Note();
			note.setCn_note_id(noteId);//设置笔记ID
			note.setCn_notebook_id(bookId);//设置笔记本ID
			int rows = dao.updateBookId(note);//更新
			//创建返回结果
			NoteResult result = new NoteResult();
			if(rows>=1){
				result.setStatus(0);
				result.setMsg("转移笔记成功");
			}else{
				result.setStatus(1);
				result.setMsg("转移笔记失败");
			}
			return result;
		}
	
	public NoteResult shareNote(String noteId){
		//创建返回结果
		NoteResult result = new NoteResult();
		//获取笔记信息
		Note note = dao.findById(noteId);
		//
		String typeId = note.getCn_note_type_id();
		//“3”代表已分享的笔记，这里检查它是否为已分享的笔记
		//如果已分享不执行下面逻辑
		if("3".equals(typeId)){
			result.setStatus(1);
			result.setMsg("该笔记已分享过");
			return result;
		}
		//更新笔记的cn_note_type_id为分享状态
		note.setCn_note_type_id("3");
		int rows = dao.updateNote(note);
		//做个判断
		if(rows != 1){
			result.setStatus(1);
			result.setMsg("修改分享状态失败");
			return result;
		}
		//添加到分享表
		Share share = new Share();
		share.setCn_note_id(noteId);
		share.setCn_share_id(NoteUtil.createId());
		share.setCn_share_title(note.getCn_note_title());//分享标题
		share.setCn_share_body(note.getCn_note_body());//分享内容
		rows = shareDao.save(share);
		if(rows != 1){
			result.setStatus(1);
			result.setMsg("存储分享笔记失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("分享笔记成功");
		return result;	
	}

	
	public NoteResult searchShareNote(String keyword,int page) {
		//处理查询条件值
		String title = "%";
		if(keyword != null && !"".equals(keyword)){
			title = "%"+keyword+"%";
		}
		//计算抓取记录的起点
		if(page < 1){
			page = 1;
		}
		int begin = (page - 1) * 5;
		//封装成Map参数
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("keyword", title);
		List<Share> list = shareDao.findLikeTitle(params);
		//封装NoteResult结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("搜索完毕");
		result.setData(list);
		return result;
	}
	
	public NoteResult findByShareId(String shareId){
		Share share = shareDao.findById(shareId);
		NoteResult result = new NoteResult();
		if(share == null){
			result.setStatus(1);
			result.setMsg("加载内容失败");
		}
		result.setStatus(0);
		result.setMsg("内容加载成功");
		result.setData(share);
		return result;
	}

	public NoteResult findRecycle(String userId) {
		NoteResult result = new NoteResult();
		if(userId == null){
			result.setStatus(1);
			result.setMsg("用户ID获取失败");
			return result;
		}
		//note_status为2时，是回收站的笔记
		List<Note> list = dao.findRecycle(userId);
		result.setStatus(0);
		result.setMsg("用户ID获取成功");
		result.setData(list);
		return result;
	}

	public NoteResult updateReplay(String bookId, String noteId) {
		//封装Note
		Note note = new Note();
		note.setCn_notebook_id(bookId);
		note.setCn_note_id(noteId);
		int rows = dao.updateReplay(note);
		//
		NoteResult result = new NoteResult();
		if(rows != 1){
			result.setStatus(1);
			result.setMsg("还原失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("还原成功");
		return result;
	}

	public NoteResult completelyDelete(String noteId) {
		int rows = dao.completelyDelete(noteId);
		//
		NoteResult result = new NoteResult();
		if(rows != 1){
			result.setStatus(1);
			result.setMsg("删除失败");
			return result;
		}
		result.setStatus(0);
		result.setMsg("删除成功");
		return result;
	}

	public NoteResult collectNote(String userId, String shareId) {
		//得到Share对象
		Share share = shareDao.findById(shareId);
				String titlt = share.getCn_share_title();
				String body = share.getCn_share_body();
				//得到收藏笔记本的ID
				String notebookId = notebookDao.findCollectBook(userId);
				//创建返回结果
				NoteResult result = new NoteResult();
				if(notebookId == null){
					result.setStatus(1);
					result.setMsg("收藏失败");
					return result;
				}
				//创建Note对象
				Note note = new Note();
				note.setCn_user_id(userId);
				note.setCn_notebook_id(notebookId);
				note.setCn_note_title(titlt);
				note.setCn_note_body(body);
				String noteId = NoteUtil.createId();
				note.setCn_note_id(noteId);
				Long time = System.currentTimeMillis();
				note.setCn_note_create_time(time);
				note.setCn_note_last_modify_time(time);
				int rows = dao.createNewNote(note);
				if(rows != 1){
					result.setStatus(1);
					result.setMsg("收藏失败");
					return result;
				}
				result.setStatus(0);
				result.setMsg("收藏成功");
				return result;
	}

	public NoteResult loadCollectedNote(String userId) {
		//得到收藏笔记本的ID
		String bookId = notebookDao.findCollectBook(userId);
		//得到笔记信息
		List<Map<String,Object>> list = dao.findNoteById(bookId);
		//创建返回结果
		NoteResult result = new NoteResult();
		if(list==null){
			result.setStatus(1);
			result.setMsg("加载列表失败!");
			return result;
		}
		result.setStatus(0);
		result.setMsg("加载列表成功!");
		result.setData(list);
		return result;
	}

	public NoteResult findContent(String noteId) {
		Note note = dao.findById(noteId);
		NoteResult result = new NoteResult();
		if(note == null){
			result.setStatus(1);
			result.setMsg("加载内容失败!");
			return result;
		}
		result.setStatus(0);
		result.setMsg("加载内容成功!");
		result.setData(note);
		return result;
	}
	
	//不在功能范围之内
	public NoteResult searchNotes(String title, String status, String beginStr, String endStr) {
		//创建查询参数
		Map<String,Object> params = new HashMap<String,Object>();
		//标题
		if(title!=null&&!"".equals(title)){
			//对应SQL中的#{title}
			params.put("title", "%"+ title +"%");
		}
		//状态，如果不是全部选项“0”
		if(!"0".equals(status)){
			//对应SQL中的#{status}
			params.put("status", status);
		}
		//开始日期
		if(beginStr!=null&&!"".equals(beginStr)){
			Date beginDate = Date.valueOf(beginStr);
			//对应SQL中的#{begin}
			params.put("begin", beginDate.getTime());
		}
		//结束日期
		if(endStr!=null&&!"".equals(endStr)){
			Date endDate = Date.valueOf(endStr);
			//对应SQL中的#{end}
			params.put("end", endDate.getTime());
		}
		//根据参数查询笔记信息
		List<Note> list = dao.findNotes(params);
		//
		NoteResult result = new NoteResult();
		result.setMsg("搜索完毕");
		result.setStatus(0);
		result.setData(list);
		return result;
	}

	public NoteResult findTypeNote(String userId, String type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cn_user_id", userId);
		params.put("type_id", type);
		List<Note> note = dao.findTypeNote(params);
		NoteResult result = new NoteResult();
		if(note == null){
			result.setStatus(1);
			result.setMsg("加载活动笔记失败!");
			return result;
		}
		result.setStatus(0);
		result.setMsg("加载活动笔记成功!");
		result.setData(note);
		return result;
	}

}
