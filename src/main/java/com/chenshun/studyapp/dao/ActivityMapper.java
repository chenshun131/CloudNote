package com.chenshun.studyapp.dao;

import com.chenshun.studyapp.domain.Activity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityMapper {

    int deleteByPrimaryKey(String cnActivityId);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String cnActivityId);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKeyWithBLOBs(Activity record);

    int updateByPrimaryKey(Activity record);
}
