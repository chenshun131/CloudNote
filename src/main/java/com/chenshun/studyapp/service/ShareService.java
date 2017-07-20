package com.chenshun.studyapp.service;

import com.chenshun.utils.web.rest.RestResultDTO;

/**
 * User: mew <p />
 * Time: 17/7/20 11:08  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public interface ShareService {

    RestResultDTO shareNote(String noteId);

    RestResultDTO searchNote(String shareTitle, Integer page);

    RestResultDTO searchShare(String shareId);

}
