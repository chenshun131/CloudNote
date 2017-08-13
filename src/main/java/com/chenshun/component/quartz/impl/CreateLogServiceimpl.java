package com.chenshun.component.quartz.impl;

import com.chenshun.component.quartz.CreateLogService;
import com.chenshun.utils.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: mew <p />
 * Time: 17/8/13 16:02  <p />
 * Version: V1.0  <p />
 * Description: 定时创建活动日志 <p />
 */
@Service("createLogService")
@Transactional
public class CreateLogServiceimpl implements CreateLogService {

    @Override
    public void createlog() {
        System.out.println("任务调度执行 定时创建活动日志 ＝> : " + DateUtil.getTime());
    }

}
