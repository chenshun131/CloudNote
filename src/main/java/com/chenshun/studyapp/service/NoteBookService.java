package com.chenshun.studyapp.service;

import com.chenshun.utils.web.rest.RestResultDTO;

/**
 * User: chenshun131 <p />
 * Time: 17/7/12 22:32  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public interface NoteBookService {

    RestResultDTO loadBooks(String userId);

    RestResultDTO addBook(String userId, String bookName);
}
