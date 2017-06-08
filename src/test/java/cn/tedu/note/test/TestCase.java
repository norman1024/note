package cn.tedu.note.test;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.blog.dao.PostDao;
import cn.tedu.blog.entity.Post;
import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.StudentDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Student;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.DemoService;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.Md5;

public class TestCase {
	
	ApplicationContext ctx;
	@Before
	public void init(){
		ctx = 
		new ClassPathXmlApplicationContext(
			"spring-mybatis.xml",
			"spring-service.xml");
	}
	//@Test //测试MyBatis配置
	public void testMapperScanner(){
		Object obj = 
			ctx.getBean("mapperScanner");
		System.out.println(obj); 
	}
	//@Test//测试UserDAO的Save方法
	public void testSaveUser(){
		UserDao dao = 
			ctx.getBean(
			"userDao", UserDao.class);
		System.out.println(dao);
		String id=UUID.randomUUID().toString();
		System.out.println(id);
		User user=new User(
			id,"Tom","123","","Tomcat");
		dao.saveUser(user); 
	}
	
	//@Test
	public void testFindUserById(){
		String id="8a9875ab-0af9-4a0b-a605-e3b30c29f3c4";
		UserDao dao = 
			ctx.getBean("userDao", UserDao.class);
		User user=dao.findUserById(id);
		System.out.println(user); 
	}
	//@Test
	public void testFindUserByName(){
		String name = "Tom";
		UserDao dao = ctx.getBean(
			"userDao", UserDao.class);
		User user=dao.findUserByName(name);
		System.out.println(user); 
	}
	//@Test
	public void testLogin(){
		String name="Tom";
		String password = "123";
		UserService service =
			ctx.getBean("userService",
			UserService.class);
		User user = 
			service.login(name, password);
		System.out.println(user); 
		user = service.login(name, "1234");
	}
	
	//@Test
	public void testMd5(){
 
		try {
			//Message:消息  Digest：摘要
			//MessageDigest封装了复制的消息摘要算法
			MessageDigest md = 
				MessageDigest.getInstance("md5");
			//"MD5" 是算法名称
			//update 提交数据，如果多次调用的
			//话，就是对一堆数据进行摘要
			//md.update(数据);
			//md.update(0~99字节);
			//md.update(100~199字节);
			//md.update(200~299字节);..
			//byte[] 摘要=md.digest(); 
			//摘要=md.digest(数据)
			
			String pwd="123456";
			byte[] data=pwd.getBytes("utf-8");
			byte[] md5=md.digest(data);
			//for (byte b : md5) {
			//	System.out.println(b);
			//}
			//commons-codec
			String hex=
				Hex.encodeHexString(md5);
			System.out.println(hex);
			//Base64 
			String base64 =
				Base64.encodeBase64String(md5);
			System.out.println(base64);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
 
	}
	
	//@Test
	public void testSaltMd5(){
		String pwd = "123";
		System.out.println(
				Md5.saltMd5(pwd));
	}
	
	@Test
	@Ignore
	public void testRegUser(){
		UserService svc  = 
			ctx.getBean("userService",
			UserService.class);
		User user = svc.regist(
			"Tom", "123", "哈哈哈哈");
		System.out.println(user);
		//svc.regist("Tom", "123", "ssss");
	}
	
	@Test
	@Ignore
	public void testFindNotebookByUserId(){
		NotebookDao dao = ctx.getBean(
			"notebookDao", NotebookDao.class);
		String id="39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		List<Map<String, Object>> list=
			dao.findNotebookByUserId(id);
		for (Map<String, Object> map : list) {
			System.out.println(map); 
		}
	}
		
	//@Test
	public void testListNotebooks(){
		NotebookService service = 
			ctx.getBean("notebookService",
			NotebookService.class);
		String id="39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		List<Map<String, Object>> list=
			service.listNotebooks(id);
		for (Map<String, Object> map : list) {
			System.out.println(map); 
		}
	}
	
	@Test
	@Ignore
	public void testFindNoteBYNotebookId(){
		String id = "fa8d3d9d-2de5-4cfe-845f-951041bcc461";
		NoteDao noteDao = 
			ctx.getBean("noteDao", NoteDao.class);
		List<Map<String, Object>> list=
			noteDao.findNoteByNotebookId(id);
		for (Map<String, Object> map : list) {
			System.out.println(map); 
		}
	}
	//@Test
	public void testListNotes(){
		String id = "fa8d3d9d-2de5-4cfe-845f-951041bcc461";
		NotebookService service=
			ctx.getBean("notebookService",
			NotebookService.class);
		List list = service.listNotes(id);
		for (Object obj : list) {
			System.out.println(obj); 
		}
	}
	
	//@Test
	public void testFindNoteById(){
		String id="1ec185d6-554a-481b-b322-b562485bb8e8";
		NoteDao dao = ctx.getBean("noteDao",
			NoteDao.class);
		Note note = dao.findNoteById(id);
		
		System.out.println(note); 
	}
	
	//@Test
	public void testUpdateNote(){
		String id="1ec185d6-554a-481b-b322-b562485bb8e8";
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		Note note=dao.findNoteById(id);
		note.setTitle("云笔记项目");
		dao.updateNote(note);
		//验证
		Note n = dao.findNoteById(id);
		System.out.println(n.getTitle()); 
	}
		
	//测试事务操作
	//@Test
	public void testBatchRegist(){
		DemoService service=
			ctx.getBean("demoService",
			DemoService.class);
		List<String> pwdList=
			service.batchRegist(
			"Liu","Lee","Tom","Mac");
		System.out.println(pwdList); 
	}
	
	//@Test
	public void testBatRegist(){
		DemoService service=
			ctx.getBean("demoService",
			DemoService.class);
		List<String> pwdList=
			service.batRegist(
			"Liu","Lee","Tom","Mac");
		System.out.println(pwdList); 
		
		//service.regist(name, password, nick)
	}
	
	/*
	 select cn_note_id, cn_note_type_id
	 from cn_note;
	 
	 246f4e4e-7cc4-4df2-956f-8860165fd3c3
630793d5-aa63-428c-967b-86d0ba8612de
6cb21f33-3b5f-4ac8-a0bd-5f664582db4e
888aab3e-a144-4a51-979b-e96712537c4c
	 */
	//@Test
	public void testUpdateNoteDelType(){
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		dao.updateNoteDelType(
			"246f4e4e-7cc4-4df2-956f-8860165fd3c3",
			"630793d5-aa63-428c-967b-86d0ba8612de",
			"6cb21f33-3b5f-4ac8-a0bd-5f664582db4e",
			"888aab3e-a144-4a51-979b-e96712537c4c");
	}
	//@Test
	public void testCountNormalNote(){
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		List<String> list=
			new ArrayList<String>();
		list.add("246f4e4e-7cc4-4df2-956f-8860165fd3c3");
		list.add("630793d5-aa63-428c-967b-86d0ba8612de");
		list.add("6cb21f33-3b5f-4ac8-a0bd-5f664582db4e");
		list.add("888aab3e-a144-4a51-979b-e96712537c4c");
		Integer n=dao.countNormalNote(list);
		System.out.println(n); 
	}
	
	//@Test
	public void testUpdateNotes(){
		String[] ids={
			"246f4e4e-7cc4-4df2-956f-8860165fd3c3",
			"630793d5-aa63-428c-967b-86d0ba8612de",
			"6cb21f33-3b5f-4ac8-a0bd-5f664582db4e",
			"888aab3e-a144-4a51-979b-e96712537c4c"};
		Map<String, Object> map = 
			new HashMap<String, Object>();
		map.put("typeId", "1");
		map.put("lastModifyTime", System.currentTimeMillis());
		map.put("ids", ids);
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		dao.updateNotes(map);
		
	}
	//@Test
	public void testCountNotes(){
		String[] ids={
			"246f4e4e-7cc4-4df2-956f-8860165fd3c3",
			"630793d5-aa63-428c-967b-86d0ba8612de",
			"6cb21f33-3b5f-4ac8-a0bd-5f664582db4e",
			"888aab3e-a144-4a51-979b-e96712537c4c"};
		Map<String, Object> map = 
			new HashMap<String, Object>();
		map.put("typeId", "2");
		//map.put("lastModifyTime", System.currentTimeMillis());
		//map.put("ids", ids);
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		Integer n=dao.countNotes(map);
		System.out.println(n);
		
	}

	//@Test
	public void testDeleteNotes(){
		NotebookService service =
			ctx.getBean("notebookService",
			NotebookService.class);
		service.deleteNotes(
			"246f4e4e-7cc4-4df2-956f-8860165fd3c3",
			"630793d5-aa63-428c-967b-86d0ba8612de",
			"6cb21f33-3b5f-4ac8-a0bd-5f664582db4e",
			"888aab3e-a144-4a51-979b-e96712537c4c");
	}
	
	//@Test
	public void testDeleteNotes2(){
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		dao.deleteNotes(
			"fsaf-as-df-asdf-as-df-dsa",
			"ss19055-30e8-4cdc-bfac-97c6bad9518f");
	}
	
	//@Test
	public void testFindAllByKeys(){
		Map<String, Object> map = 
			new HashMap<String, Object>();
		map.put("typeId", "1");
		map.put("title", "%你好%");
		map.put("body", "%你好%");
		
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		
		List<Map<String, Object>> list=
			dao.findAllByKeys(map);
		for (Map<String, Object> note : list) {
			System.out.println(note); 
		}
	}
	
	//@Test
	public void testSaveStudent(){
		StudentDao dao = ctx.getBean(
			"studentDao", StudentDao.class);
		Student andy = new Student("Andy");
		dao.saveStudent(andy);
		System.out.println(andy); 
	}
	
	//@Test
	public void testFindAllByKeysPage(){
		Map<String, Object> map = 
			new HashMap<String, Object>();
		//map.put("typeId", "1");
		//map.put("title", "%你好%");
		//map.put("body", "%你好%");
		map.put("start", 10);
		map.put("length", 10);
		
		NoteDao dao = ctx.getBean(
			"noteDao", NoteDao.class);
		
		List<Map<String, Object>> list=
			dao.findAllByKeys(map);
		for (Map<String, Object> note : list) {
			System.out.println(note); 
		}
	}
	
	//@Test
	public void testSearchNotes(){
		NotebookService service = 
			ctx.getBean("notebookService",
			NotebookService.class);
		List<Map<String, Object>> list=
			service.searchNotes("", 1, 10);
		for (Map<String, Object> note : list) {
			System.out.println(note); 
		}
	}
	
	@Test
	public void testFindPostById(){
		PostDao dao = ctx.getBean(
			"postDao",PostDao.class);
		Post post = dao.findPostById("1");
		System.out.println(post); 
	}
}










