package com.chenshun.studyapp.service.impl;

import com.chenshun.component.exception.ServiceException;
import com.chenshun.studyapp.dao.NoteMapper;
import com.chenshun.studyapp.dao.ShareMapper;
import com.chenshun.studyapp.domain.Note;
import com.chenshun.studyapp.domain.Share;
import com.chenshun.studyapp.entity.SearchNote;
import com.chenshun.studyapp.kit.NoteKit;
import com.chenshun.studyapp.service.NoteService;
import com.chenshun.utils.validate.CommonValidateUtil;
import com.chenshun.utils.web.rest.RestResultDTO;
import com.chenshun.utils.web.rest.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/15 12:24  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Resource
    private NoteMapper noteMapper;

    @Resource
    private ShareMapper shareMapper;

    @Override
    @Transactional(readOnly = true)// 只读事务
    public RestResultDTO loadNotes(String bookId) {
        if (CommonValidateUtil.isEmpty(bookId)) {
            throw new ServiceException("笔记数据查询失败");
        }

        RestResultDTO restResultDTO = new RestResultDTO();
        List<Map<String, Object>> list = noteMapper.findNotesByBookId(bookId);
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setBody(list);
        return restResultDTO;
    }

    @Override
    public RestResultDTO addNote(Note note) {
        // 笔记名,用户ID,笔记本ID请求传递过来
        // 笔记名称是否为空,是否重名

        String noteId = NoteKit.createId();
        note.setCnNoteId(noteId);// 设置笔记ID
        note.setCnNoteStatusId("1");// 设置normal状态
        note.setCnNoteTypeId("1");// 设置normal类型
        note.setCnNoteBody("");// 设置内容为空
        long time = System.currentTimeMillis();
        note.setCnNoteCreateTime(time);// 设置创建时间
        note.setCnNoteLastModifyTime(time);// 设置最后修改时间
        noteMapper.save(note);// 保存操作

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setBody(note.getCnNoteId());
        return restResultDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public RestResultDTO loadNote(String noteId) {
        if (CommonValidateUtil.isEmpty(noteId)) {
            throw new ServiceException("获取笔记失败");
        }

        Note note = noteMapper.findById(noteId);
        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setBody(note);
        return restResultDTO;
    }

    @Override
    public RestResultDTO updateNote(Note note) {
        // 传入笔记ID,笔记标题,笔记内容
        // 检查数据格式，笔记标题是否重名
        note.setCnNoteLastModifyTime(System.currentTimeMillis());
        noteMapper.updateNote(note);// 更新笔记

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setMessage("保存笔记成功");
        return restResultDTO;
    }

    @Override
    public RestResultDTO deleteNote(String noteId) {
        // 将笔记状态设置为删除
        Note note = new Note();
        note.setCnNoteId(noteId);
        note.setCnNoteStatusId("2");
        noteMapper.updateNote(note);

        // 如果该笔记分享过，那取消分享
        shareMapper.deleteByNoteId(noteId);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setMessage("删除成功");
        return restResultDTO;
    }

    @Override
    public RestResultDTO shareNote(String noteId) {
        // 检测是否被分享过
        Share has_share = shareMapper.findByNoteId(noteId);
        if (has_share != null) {
            throw new ServiceException("该笔记已被分享过");
        }

        // 提取要分享笔记的信息
        Note note = noteMapper.findById(noteId);
        Share share = new Share();
        String shareId = NoteKit.createId();
        share.setCnShareId(shareId);
        share.setCnNoteId(note.getCnNoteId());
        share.setCnShareTitle(note.getCnNoteTitle());
        share.setCnShareBody(note.getCnNoteBody());
        shareMapper.save(share);// 保存到cn_share表

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setMessage("笔记分享成功");
        return restResultDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public RestResultDTO searchShareNotes(String keyword) {
        String title = "";
        if (CommonValidateUtil.isEmpty(keyword)) {
            title = "%";// 如果关键词为空默认取所有
        } else {
            title = "%" + keyword.trim() + "%";
        }
        List<Share> list = shareMapper.findLikeTitle(title);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setBody(list);
        return restResultDTO;
    }

    @Override
    public RestResultDTO searchNotes(SearchNote search) {
        // 设置标题参数
        if (!CommonValidateUtil.isEmpty(search.getTitle())) {
            search.setTitle("%" + search.getTitle() + "%");
        }
        // 设置状态
        if ("-1".equals(search.getStatus())) {
            search.setStatus(null);
        }
        // 传入SQL查询
        List<Note> list = noteMapper.searchNotes(search);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        return restResultDTO;
    }

    @Override
    public RestResultDTO batchDelete(String[] ids) {
        if(ids != null && ids.length>0){
            noteMapper.batchDeleteNotes(ids);
        }

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        return restResultDTO;
    }

}
