package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.NoteActivity;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteActivityMapper {

    int deleteByPrimaryKey(String cnNoteActivityId);

    int insert(NoteActivity record);

    int insertSelective(NoteActivity record);

    NoteActivity selectByPrimaryKey(String cnNoteActivityId);

    int updateByPrimaryKeySelective(NoteActivity record);

    int updateByPrimaryKeyWithBLOBs(NoteActivity record);

    int updateByPrimaryKey(NoteActivity record);
}