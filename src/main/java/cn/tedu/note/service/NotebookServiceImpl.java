package cn.tedu.note.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.entity.Note;

@Service("notebookService")
public class NotebookServiceImpl
	implements NotebookService{
	
	@Autowired
	private NotebookDao notebookDao;
	
	@Autowired
	private NoteDao noteDao;
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> 
		listNotebooks(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException(
					"userId不能为空");
		}
		return notebookDao
			.findNotebookByUserId(userId);
	}
	
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> 
		listNotes(String notebookId) {
		if(notebookId==null){
			throw new ServiceException("笔记本ID不能为空");
		}
		return noteDao.findNoteByNotebookId(notebookId);
	}
	
	@Transactional(readOnly=true)
	public Note loadNote(String noteId) {
		if(noteId==null){
			throw new ServiceException("笔记id不能为空");
		}
		return noteDao.findNoteById(noteId);
	}
	
	@Transactional
	public Note updateNoteBody(
		String noteId, String title, String body) {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new ServiceException("ID不能为空");
		}
		if(title==null){
			title = "";
		}
		if(body==null){
			body = "";
		}
		//找到Note信息
		Note note=noteDao.findNoteById(noteId);
		if(note == null){
			throw new ServiceException("没有笔记信息");
		}
		if(! title.trim().isEmpty()){
			note.setTitle(title); 
		}
		note.setBody(body); 
		long now=System.currentTimeMillis();
		note.setLastModifyTime(now);
		noteDao.updateNote(note); 
		return note;
	}
	
/*
 //删除
 insert into cn_note_type 
 values ('2', 'delete', 'delete', null);
 
 //不能用，彻底删除
 insert into cn_note_type 
 values ('3', 'disabled', 'disabled', null);
 
 //收藏
 insert into cn_note_status 
 values ('2', 'favorite', 'favorite');
 *
 */
	
	@Transactional
	public Note addNote(String title, 
		String notebookId, String userId){
		if(title==null || 
				title.trim().isEmpty()){
			title="未命名笔记";
		}
		if(notebookId==null || 
			notebookId.trim().isEmpty()){
			throw new ServiceException("必须选择笔记本"); 
		}
		if(userId==null||
			userId.trim().isEmpty()){
			throw new ServiceException("必须登录！");
		}
		String id = UUID.randomUUID().toString();
		long now = System.currentTimeMillis();
		Note note = new Note();
		note.setId(id);
		note.setBody("");
		note.setCreateTime(now);
		note.setLastModifyTime(now);
		note.setNotebookId(notebookId);
		note.setTitle(title);
		note.setUserId(userId);
		//是否被收藏的笔记
		note.setStatusId(NORMAL_STATUS);
		//是否是删除的笔记
		note.setTypeId(NORMAL_TYPE);
		noteDao.addNote(note); 
		return note;
	}
	
	@Transactional
	public Note deleteNote(String noteId) {
		if(noteId==null ||
			noteId.trim().isEmpty()){
			throw new ServiceException("笔记ID不能为空");
		}
		Note note=noteDao.findNoteById(noteId);
		if(note == null){
			throw new ServiceException("ID不存在");
		}
		if(NORMAL_TYPE.equals(note.getTypeId())){
			note.setTypeId(DELETE_TYPE);
			long now = System.currentTimeMillis();
			note.setLastModifyTime(now);
			noteDao.updateNote(note); 
			return note;
		}
		throw new ServiceException("只能删除正常笔记！");
	}
	
	//NotebookServiceImpl中实现业务方法
	@Transactional
	public void deleteNotes(String... ids) {
		//检验被更新的数据是否存在
		//拼凑查询条件
		Map<String, Object> map=
			new HashMap<String, Object>();
		map.put("typeId", NORMAL_TYPE);
		map.put("ids", ids);
		//检查正常笔记数量
		Integer n=noteDao.countNotes(map);
		if(n!=ids.length){
			throw new ServiceException("被删除的笔记不存在");
		}
		map.put("typeId", DELETE_TYPE);
		noteDao.updateNotes(map);
	}
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> searchNotes(String key, 
		int pageNum, int pageSize) {
		int start = pageSize*pageNum;
		Map<String, Object> paramter=
			new HashMap<String, Object>();
		paramter.put("start", start);
		paramter.put("length", pageSize);
		if(key!=null){
			key = key.trim();
			if(! key.isEmpty()){
				key = "%"+key+"%";
				paramter.put("title", key);
				paramter.put("body", key);
			}
		}
		List<Map<String, Object>> list=
			noteDao.findAllByKeys(paramter);
		return list;
	}
}












