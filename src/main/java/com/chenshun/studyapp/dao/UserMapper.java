package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository(value = "userMapper")
public interface UserMapper {

    User findByName(String name);

    int updateToken(Map<String, Object> params);

    int save(User user);

    int delete(String name);

}
