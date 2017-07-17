package com.chenshun.studyapp.service;

import com.chenshun.studyapp.domain.User;
import com.chenshun.utils.web.rest.RestResultDTO;

/**
 * User: mew <p />
 * Time: 17/7/12 08:37  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public interface UserService {

    RestResultDTO checkLogin(String username, String password);

    RestResultDTO checkLogin(String author);

    RestResultDTO addUser(User user);

}
