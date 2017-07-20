package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.Share;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShareMapper {

    int deleteByNoteId(String noteId);

    Share findByShareId(String shareId);

    Share findByNoteId(String noteId);

    int save(Share share);

    List<Share> findLikeTitle(String title);

    /**
     * 搜索分享笔记
     *
     * @param map
     *         key1:title,key2:次数
     * @return 满足要求的分享笔记
     */
    List<Share> findLikeTitle2(Map map);

}
