package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.Note;
import com.chenshun.studyapp.entity.SearchNote;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NoteMapper {

    List<Map<String, Object>> findNotesByBookId(String bookId);

    int save(Note note);

    Note findById(String id);

    int updateNote(Note note);

    int updateBookId(Note note);

    /**
     * 查询删除笔记
     *
     * @param userId
     * @return
     */
    List<Note> findByStatus(String userId);

    /**
     * 查询收藏笔记
     *
     * @param userId
     * @return
     */
    List<Note> findByStatus2(String userId);

    int deleteByNoteId(String noteId);

    int updateStatusToStore(String noteId);

    List<Note> findNotes(Map params);

    List<Note> searchNotes(SearchNote search);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int batchDeleteNotes(String[] ids);

}
