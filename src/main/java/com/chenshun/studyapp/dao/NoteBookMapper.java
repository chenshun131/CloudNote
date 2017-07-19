package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.NoteBook;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NoteBookMapper {

    List<NoteBook> findBooksByUser(String userId);

    NoteBook findByNameAndUser(Map<String, Object> params);

    int save(NoteBook book);

    int updateName(Map map);

}
