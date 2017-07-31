package com.chenshun.studyapp.dao.mongo.impl;

import com.chenshun.studyapp.dao.mongo.IUserInfoDao;
import com.chenshun.studyapp.entity.mongo.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * User: mew <p />
 * Time: 17/7/31 14:16  <p />
 * Version: V1.0  <p />
 * Description: 用户接口实现类 <p />
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements IUserInfoDao {

    @Override
    protected Class<UserInfo> getEntityClass() {
        return UserInfo.class;
    }

}
