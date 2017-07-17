package com.chenshun.studyapp.controller.user;

import com.chenshun.studyapp.domain.User;
import com.chenshun.studyapp.service.UserService;
import com.chenshun.utils.web.rest.RestResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: mew <p />
 * Time: 17/7/12 08:35  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public RestResultDTO execute(HttpServletRequest request) {
        logger.info("进入LoginController");

        String author = request.getHeader("Authorization");
        logger.info("author:" + author);

        return userService.checkLogin(author);
    }

    @RequestMapping("/regist")
    @ResponseBody
    public RestResultDTO execute(User user) {
        return userService.addUser(user);
    }

}
