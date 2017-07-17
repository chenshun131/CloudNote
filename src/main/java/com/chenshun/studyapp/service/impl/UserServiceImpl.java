package com.chenshun.studyapp.service.impl;

import com.chenshun.component.exception.ServiceException;
import com.chenshun.studyapp.dao.UserMapper;
import com.chenshun.studyapp.domain.User;
import com.chenshun.studyapp.kit.NoteKit;
import com.chenshun.studyapp.service.UserService;
import com.chenshun.utils.validate.CommonValidateUtil;
import com.chenshun.utils.web.rest.RestResultDTO;
import com.chenshun.utils.web.rest.StatusCode;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/12 08:38  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public RestResultDTO checkLogin(String username, String password) {
        // 判断用户名
        User user = userMapper.findByName(username);
        if (user == null) {
            throw new ServiceException("未查询到该用户信息");
        }
        // 判断密码
        String md5_password = NoteKit.md5(password);
        if (!user.getCnUserPassword().equals(md5_password)) {
            throw new ServiceException("密码不正确");
        }
        // 成功，更新用户令牌号
        String token = NoteKit.createToken();
        Map<String, Object> params = new HashMap<>();
        params.put("userToken", token);
        params.put("userId", user.getCnUserId());
        userMapper.updateToken(params);

        RestResultDTO restResultDTO = new RestResultDTO();
        restResultDTO.initStatus(StatusCode.OK);// 0正确
        restResultDTO.setMessage("登录成功");
        restResultDTO.setBody(params);// 传递到客户端
        return restResultDTO;
    }

    @Override
    @Transactional
    public RestResultDTO checkLogin(String author) {
        String base64_msg = author.substring(author.indexOf(' '));
        logger.info("base64_msg:" + base64_msg);

        byte[] output = Base64.decodeBase64(base64_msg.getBytes());
        String msg = "";
        try {
            msg = new String(output, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("checkLogin -> 登录信息解析失败", e);
            throw new ServiceException("登录信息异常");
        }
        logger.info("msg:" + msg);

        String[] msg_arr = msg.split(":");
        String username = msg_arr[0];
        String password = msg_arr[1];
        logger.info("username:" + username + "    " + "password:" + password);

        return checkLogin(username, password);
    }

    /**
     * addUser处理是一个整体,没异常提交;有异常回滚
     *
     * @param user
     * @return
     */
    public RestResultDTO addUser(User user) {
        RestResultDTO restResultDTO = new RestResultDTO();
        // 用户名、密码 从，请求接收数据检查(格式检查,唯一性检查)
        if (CommonValidateUtil.isEmpty(user.getCnUserName())) {
            restResultDTO.setStatusCode("1");// 1:用户名错误
            restResultDTO.setMessage("用户名不能为空");
            return restResultDTO;
        }
        if (CommonValidateUtil.isEmpty(user.getCnUserPassword())) {
            restResultDTO.setStatusCode("2");// 2:密码错误
            restResultDTO.setMessage("用密码不能为空");
            return restResultDTO;
        }

        // 用户名唯一性检查
        User usr = userMapper.findByName(user.getCnUserName());
        if (usr != null) {
            restResultDTO.setStatusCode("1");// 1:用户名错误
            restResultDTO.setMessage("用户名已被占用");
            return restResultDTO;
        }

        // 生成ID
        String userId = NoteKit.createId();
        user.setCnUserId(userId);

        // 密码加密
        String md5_pwd = NoteKit.md5(user.getCnUserPassword());
        user.setCnUserPassword(md5_pwd);

        // 执行cn_user添加操作
        userMapper.save(user);

        restResultDTO.initStatus(StatusCode.OK);// 0正确
        restResultDTO.setMessage("注册成功");
        return restResultDTO;
    }

}
