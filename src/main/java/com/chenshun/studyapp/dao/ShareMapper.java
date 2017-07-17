package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.Share;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareMapper {

    int deleteByNoteId(String noteId);

    Share findByNoteId(String noteId);

    int save(Share share);

    List<Share> findLikeTitle(String title);

}
