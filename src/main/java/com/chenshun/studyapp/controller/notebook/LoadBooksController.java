package com.chenshun.studyapp.controller.notebook;

import com.chenshun.studyapp.service.NoteBookService;
import com.chenshun.utils.web.rest.RestResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: chenshun131 <p />
 * Time: 17/7/12 22:31  <p />
 * Version: V1.0  <p />
 * Description: 笔记本 <p />
 */
@Controller
@RequestMapping("/notebook")
public class LoadBooksController {

    @Resource
    private NoteBookService bookService;

    @RequestMapping("/loadbooks")
    @ResponseBody
    public RestResultDTO execute(String userId) {
        return bookService.loadBooks(userId);
    }

    @RequestMapping("/add")
    @ResponseBody
    public RestResultDTO execute(String userId, String bookName) {
        return bookService.addBook(userId, bookName);
    }

    @RequestMapping("/rename")
    @ResponseBody
    public RestResultDTO execute2(String bookId, String bookName) {
        //调用UserService处理登录
        return bookService.rename(bookId, bookName);
    }

}
