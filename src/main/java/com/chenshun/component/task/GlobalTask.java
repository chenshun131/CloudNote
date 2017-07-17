package com.chenshun.component.task;

import com.chenshun.utils.date.DateUtil;

/**
 * User: mew <p />
 * Time: 17/7/11 15:24  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class GlobalTask implements Runnable {

    @Override
    public void run() {
        System.out.println("任务调度执行:" + DateUtil.getTime());
    }

}
