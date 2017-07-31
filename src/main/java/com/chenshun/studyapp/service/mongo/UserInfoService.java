package com.chenshun.studyapp.service.mongo;

import com.chenshun.studyapp.dao.mongo.IBaseDao;
import com.chenshun.studyapp.dao.mongo.IUserInfoDao;
import com.chenshun.studyapp.entity.mongo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: mew <p />
 * Time: 17/7/31 14:21  <p />
 * Version: V1.0  <p />
 * Description: 用户service类 <p />
 */
@Service
public class UserInfoService extends BaseService<UserInfo> {

    @Autowired
    private IUserInfoDao userInfoDao;

    @Override
    protected IBaseDao<UserInfo> getDao() {
        return userInfoDao;
    }

}
