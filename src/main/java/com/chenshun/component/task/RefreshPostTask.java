package com.chenshun.component.task;

import com.chenshun.utils.date.DateUtil;

/**
 * User: mew <p />
 * Time: 17/8/13 15:25  <p />
 * Version: V1.0  <p />
 * Description: 定时刷新帖子 <p />
 */
public class RefreshPostTask implements Runnable {

    @Override
    public void run() {
        System.out.println("任务调度执行 定时刷新帖子 ＝> :" + DateUtil.getTime());
    }

}
