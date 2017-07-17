package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.NoteBookType;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteBookTypeMapper {

    int deleteByPrimaryKey(String cnNotebookTypeId);

    int insert(NoteBookType record);

    int insertSelective(NoteBookType record);

    NoteBookType selectByPrimaryKey(String cnNotebookTypeId);

    int updateByPrimaryKeySelective(NoteBookType record);

    int updateByPrimaryKeyWithBLOBs(NoteBookType record);

    int updateByPrimaryKey(NoteBookType record);
}
