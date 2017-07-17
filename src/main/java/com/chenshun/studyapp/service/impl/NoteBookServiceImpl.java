package com.chenshun.studyapp.service.impl;

import com.chenshun.component.exception.ServiceException;
import com.chenshun.component.exception.UnloginException;
import com.chenshun.studyapp.dao.NoteBookMapper;
import com.chenshun.studyapp.domain.NoteBook;
import com.chenshun.studyapp.kit.NoteKit;
import com.chenshun.studyapp.service.NoteBookService;
import com.chenshun.utils.validate.CommonValidateUtil;
import com.chenshun.utils.web.rest.RestResultDTO;
import com.chenshun.utils.web.rest.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: chenshun131 <p />
 * Time: 17/7/12 22:33  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Service
@Transactional
public class NoteBookServiceImpl implements NoteBookService {

    @Resource
    private NoteBookMapper noteBookMapper;

    @Override
    @Transactional(readOnly = true)
    public RestResultDTO loadBooks(String userId) {
        if (userId == null || "".equals(userId)) {
            throw new ServiceException("未查询到数据");
        }

        // 根据用户ID提取笔记本
        List<NoteBook> list = noteBookMapper.findBooksByUser(userId);
        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setMessage("查询成功");
        restResultDTO.setBody(list);
        return restResultDTO;
    }

    @Override
    public RestResultDTO addBook(String userId, String bookName) {
        // 参数数据检测
        if (CommonValidateUtil.isEmpty(userId)) {
            throw new UnloginException();
        }
        if (CommonValidateUtil.isEmpty(bookName)) {
            throw new ServiceException("笔记本名为空");
        }

        // 检测是否重名
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bookName", bookName);
        NoteBook has_book = noteBookMapper.findByNameAndUser(params);
        if (has_book != null) {
            throw new ServiceException("笔记本重名");
        }

        // 正常添加逻辑
        NoteBook book = new NoteBook();
        book.setCnUserId(userId);// 设置用户ID
        book.setCnNotebookName(bookName);// 设置笔记本名
        book.setCnNotebookId(NoteKit.createId());// 设置笔记本ID
        book.setCnNotebookTypeId("5");// 设置笔记本类型
        book.setCnNotebookDesc("");// 设置笔记本描述
        Timestamp time = new Timestamp(System.currentTimeMillis());
        book.setCnNotebookCreatetime(time);// 设置创建时间
        noteBookMapper.save(book);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);
        restResultDTO.setBody(book.getCnNotebookId());
        return restResultDTO;
    }

}
