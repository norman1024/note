package cn.tedu.note.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.Note;
import cn.tedu.note.service.NotebookService;

@Controller
@RequestMapping("/notebook")
public class NotebookController 
	implements Serializable{
	private static final long serialVersionUID = 7459588323545888557L;
	
	@Autowired
	private NotebookService notebookService;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult<List<Map<String, Object>>>
		list(String userId){
			List<Map<String, Object>> list=
					notebookService.listNotebooks(userId);
			return new JsonResult<List<Map<String,Object>>>(list);
	
	}
	
	
	@RequestMapping("/notes.do")
	@ResponseBody
	public JsonResult<List<Map<String, Object>>>
	  	listNotes(String notebookId){
		
			List<Map<String, Object>> list =
				notebookService.listNotes(notebookId);
			return new JsonResult<List<Map<String, Object>>>(list);
		
	}
	
	@RequestMapping("/load.do")
	@ResponseBody
	public JsonResult<Note> loadNote(String noteId){
		try {
			Note note= notebookService.loadNote(noteId);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	
	@RequestMapping("/updateNote.do")
	@ResponseBody
	public JsonResult<Note> updateNote(
			String noteId, 
			String title,
			String body){
		try {
			Note note=notebookService.updateNoteBody(
				noteId, title, body);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	
	@RequestMapping("/addNote.do")
	@ResponseBody
	public JsonResult<Note> addNote(
		String title, String notebookId, 
		String userId){
		try {
			Note note=notebookService.addNote(
					title, notebookId, userId);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	
	@RequestMapping("/deleteNote.do")
	@ResponseBody
	public JsonResult<Note> deleteNote(
			String noteId){
		try {
			Note note = notebookService.deleteNote(noteId);
			return new JsonResult<Note>(note);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Note>(e);
		}
	}
	
	@RequestMapping("/search.do")
	@ResponseBody
	public JsonResult<List<Map<String, Object>>> 
		search(String key, int num, int size){
		List<Map<String, Object>> list=
			notebookService.searchNotes(key, num, size);
		return new JsonResult<List<Map<String,Object>>>(list);
	}
	
}





