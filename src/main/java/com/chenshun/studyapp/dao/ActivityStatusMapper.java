package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.ActivityStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityStatusMapper {

    int deleteByPrimaryKey(String cnActivityStatusId);

    int insert(ActivityStatus record);

    int insertSelective(ActivityStatus record);

    ActivityStatus selectByPrimaryKey(String cnActivityStatusId);

    int updateByPrimaryKeySelective(ActivityStatus record);

    int updateByPrimaryKey(ActivityStatus record);
}