package com.chenshun.test.concurrency.collect.delay;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/14 15:33  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Event implements Delayed {

    private Date startDate;

    public Event(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        Date now = new Date();
        long diff = startDate.getTime() - now.getTime();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long result = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        if (result < 0) { // 当前对象的延迟值小于参数对象值
            return -1;
        } else if (result > 0) { // 当前对象的延迟值大于参数对象值
            return 1;
        }
        return 0; // 两者的延迟值相等
    }

}
