package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.NoteStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteStatusMapper {

    int deleteByPrimaryKey(String cnNoteStatusId);

    int insert(NoteStatus record);

    int insertSelective(NoteStatus record);

    NoteStatus selectByPrimaryKey(String cnNoteStatusId);

    int updateByPrimaryKeySelective(NoteStatus record);

    int updateByPrimaryKey(NoteStatus record);
}
