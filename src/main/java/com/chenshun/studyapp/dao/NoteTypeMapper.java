package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.NoteType;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteTypeMapper {

    int deleteByPrimaryKey(String cnNoteTypeId);

    int insert(NoteType record);

    int insertSelective(NoteType record);

    NoteType selectByPrimaryKey(String cnNoteTypeId);

    int updateByPrimaryKeySelective(NoteType record);

    int updateByPrimaryKeyWithBLOBs(NoteType record);

    int updateByPrimaryKey(NoteType record);
}
