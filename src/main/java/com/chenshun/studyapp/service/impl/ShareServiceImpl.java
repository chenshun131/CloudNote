package com.chenshun.studyapp.service.impl;

import com.chenshun.studyapp.dao.NoteMapper;
import com.chenshun.studyapp.dao.ShareMapper;
import com.chenshun.studyapp.domain.Note;
import com.chenshun.studyapp.domain.Share;
import com.chenshun.studyapp.kit.NoteKit;
import com.chenshun.studyapp.service.ShareService;
import com.chenshun.utils.web.rest.RestResultDTO;
import com.chenshun.utils.web.rest.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/20 11:10  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService {

    @Resource
    private ShareMapper shareMapper;

    @Resource
    private NoteMapper noteMapper;

    @Override
    public RestResultDTO shareNote(String noteId) {
        // 根据笔记ID查找标题和内容
        Note note = noteMapper.findById(noteId);

        Share share = new Share();
        share.setCnShareId(NoteKit.createId());
        share.setCnNoteId(noteId);
        share.setCnShareTitle(note.getCnNoteTitle());
        share.setCnShareBody(note.getCnNoteBody());
        // 修改cn_note表type_id值 设置成分享类型
        shareMapper.save(share);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setMessage("分享成功");
        return restResultDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public RestResultDTO searchNote(String shareTitle, Integer page) {
        shareTitle = "%" + shareTitle + "%";
        Map map = new HashMap();
        map.put("shareTitle", shareTitle);
        map.put("begin", (page - 1) * 5);
        List<Share> list = shareMapper.findLikeTitle2(map);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setBody(list);
        if (list.size() > 0) {
            restResultDTO.setMessage("查找成功");
        } else {
            restResultDTO.setMessage("没有搜索结果");
        }
        return restResultDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public RestResultDTO searchShare(String shareId) {
        Share share = shareMapper.findByShareId(shareId);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setMessage("查询成功");
        restResultDTO.setBody(share);
        return restResultDTO;
    }

}
