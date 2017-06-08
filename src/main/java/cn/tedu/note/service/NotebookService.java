package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NotebookService {
	
	String NORMAL_TYPE = "1";
	String DELETE_TYPE = "2";
	String DISABLED_TYPE = "3";
	
	String NORMAL_STATUS="1";
	String FAVORITE_STATUS="2";
		
	List<Map<String, Object>> listNotebooks(
			String userId);
	
	/**
	 * 查询笔记本中的全部笔记
	 */
	List<Map<String, Object>> listNotes(
			String notebookId); 
	
	Note loadNote(String noteId);
	
	/**
	 * 更新笔记的内容
	 * @param noteId 笔记的ID
	 * @param title 标题
	 * @param body  内容
	 */
	Note updateNoteBody(
		String noteId, String title, 
		String body);
	
	Note addNote(String title, 
			String notebookId, 
			String userId);
	
	/**
	 * 删除 指定的笔记对象
	 * @param noteId 笔记ID
	 * @return 已经删除的笔记对象
	 */
	Note deleteNote(String noteId);
	
	
	/**
	 * 批量删除方法，批量"删(移)除(动)"
	 * 指定的笔记
	 * @param ids
	 */
	void deleteNotes(String... ids);
	
	/**
	 * 分页查询
	 * @param key 查找关键字
	 * @param pageNum 页号: 0 1 2 3 4 。。
	 * @param pageSize 页面大小: 10
	 * @return 
	 */
	List<Map<String, Object>> searchNotes(String key, 
		int pageNum, int pageSize);
}






