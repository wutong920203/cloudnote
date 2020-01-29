package test;

import java.sql.Date;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.ActivityDAO;
import com.tedu.cloudnote.dao.AdminDAO;
import com.tedu.cloudnote.dao.AssociationDAO;
import com.tedu.cloudnote.dao.CollectionDAO;
import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.dao.NotebookDAO;
import com.tedu.cloudnote.dao.ShareDAO;
import com.tedu.cloudnote.dao.SigninDAO;
import com.tedu.cloudnote.entity.Activity;
import com.tedu.cloudnote.entity.Admin;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.Notebook;
import com.tedu.cloudnote.entity.Share;
import com.tedu.cloudnote.util.NoteUtil;


public class TestCase {
	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mvc.xml");
		AdminDAO dao = ac.getBean("adminDAO",AdminDAO.class);
		Admin admin = dao.findByName("zhouj");
		System.out.println(admin);
	
	}
	
	@Test
	public void test2(){
		//创建Spring容器
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		AdminDAO dao = ac.getBean("adminDAO",AdminDAO.class);
		Admin admin = dao.findByName("zhouj");
		System.out.println(admin);
	}
	
	@Test
	public void test3(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		SigninDAO dao = ac.getBean("signinDAO",SigninDAO.class);
 		Admin admin = new Admin();
 		admin.setCn_user_id("10");
 		admin.setCn_user_name("tongwu");
 		admin.setCn_user_password("123");
 		//admin.setCn_user_nick(null);
 		//admin.setCn_user_token(null);
 		dao.save(admin);
	}
	
	@Test
	public void test4(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		NotebookDAO dao = ac.getBean("notebookDAO", NotebookDAO.class);
 		List<Notebook> list = dao.findNotebookById("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
 		
 		System.out.println(list);
 		
	}
	
	@Test
	public void test5(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		NoteDAO dao = ac.getBean("noteDAO", NoteDAO.class);
 		List<Map> list = dao.findNoteById("6d763ac9-dca3-42d7-a2a7-a08053095c08");
// 		for(Map m:list){
// 			System.out.println(m.get("cn_note_id"));
// 		}
 		System.out.println(list);
	}
	
	@Test
	public void test6(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		NoteDAO dao = ac.getBean("noteDAO", NoteDAO.class);
 		Note note = new Note();
 		note.setCn_note_id("6cc250ae-f167-4a6d-92a7-c00d7d1e96f8");
 		note.setCn_note_body("Finally, I did it!");
 		dao.updateNote(note);
 		
	}
	
	@Test
	public void test7(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		NotebookDAO dao = ac.getBean("notebookDAO", NotebookDAO.class);
 		Notebook book = new Notebook();
 		book.setCn_user_id("48595f52-b22c-4485-9244-f4004255b972");
 		book.setCn_notebook_name("fuckyou");
 		book.setCn_notebook_id(NoteUtil.createId());
 		dao.createNewBook(book);
	}
	/*
	@Test
	public void test8(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		ShareDAO dao = ac.getBean("shareDAO",ShareDAO.class);
 		List<Share> list = dao.findLikeTitle("2%");
 		System.out.println(list);
	}
	*/
	@Test
	public void test9(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		NoteDAO dao = ac.getBean("noteDAO", NoteDAO.class);
 		List<Note> list = dao.findRecycle("48595f52-b22c-4485-9244-f4004255b972");
 		System.out.println(list);
	}
	
	@Test
	public void test10(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		NoteDAO dao = ac.getBean("noteDAO", NoteDAO.class);
 		//创建查询条件params
 		Map<String,Object> params = new HashMap<String,Object>();
 		//params.put("title", "%测试%");
 		//params.put("status", 2);
 		String s1 = "2016-07-01";
 		Date beginDate = Date.valueOf(s1);
 		Long begin = beginDate.getTime();
 		params.put("begin", begin);
 		//执行组合查询
 		List<Note> list = dao.findNotes(params);
 		for(Note note:list){
 			System.out.println(note.getCn_note_title());
 		}
 		System.out.println("记录数："+list.size());
	}
	
	@Test
	public void test11(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		ActivityDAO dao = ac.getBean("activityDAO", ActivityDAO.class);
 		List<Activity> list = dao.findAll();
 		System.out.println(list);
 		
	}
	
	@Test
	public void test12(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		//AssociationDAO dao = ac.getBean("associationDAO", AssociationDAO.class);
 		AssociationDAO dao = ac.getBean("associationDAO",AssociationDAO.class);
 		Notebook book = dao.findById("0037215c-09fe-4eaa-aeb5-25a340c6b39b");
 		System.out.println("笔记本名："+book.getCn_notebook_name());
 		System.out.println("创建时间"+book.getCn_notebook_createtime());
 		System.out.println("所属用户名"+book.getUser().getCn_user_name());
	}
	
	@Test
	public void test13(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		//AssociationDAO dao = ac.getBean("associationDAO", AssociationDAO.class);
 		AssociationDAO dao = ac.getBean("associationDAO",AssociationDAO.class);
 		List<Notebook> list = dao.findAllBook();
 		for(Notebook book : list){
 			System.out.println(book.getCn_notebook_name()+" "+book.getCn_notebook_createtime()+" "+book.getUser().getCn_user_name());
 		}
	}
	
	@Test
	public void test14(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		CollectionDAO dao = ac.getBean("collectionDAO",CollectionDAO.class);
 		Admin user = dao.findById("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
 		System.out.println("用户名："+user.getCn_user_name());
 		System.out.println("拥有的笔记本");
 		for(Notebook book:user.getBooks()){
 			System.out.println("=="+book.getCn_notebook_name());
 		}
	}
	
	@Test
	public void test15(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
 		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
 		CollectionDAO dao = ac.getBean("collectionDAO",CollectionDAO.class);
 		List<Admin> list = dao.findAllUser();
 		for(Admin user:list){
 			//将用户笔记本名拼一个字符串
 			String bookNames = "";
 			for(Notebook book:user.getBooks()){
 				bookNames += book.getCn_notebook_name();
 				bookNames += ",";
 			}
 			//打印用户名，笔记本数量，笔记本名称
 			System.out.println(user.getCn_user_name()+" "+user.getBooks().size()+" "+bookNames);
 		}
	}
	

}




