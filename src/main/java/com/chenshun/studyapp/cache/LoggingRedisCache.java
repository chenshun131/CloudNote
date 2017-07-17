package com.chenshun.studyapp.cache;

import org.apache.ibatis.cache.decorators.LoggingCache;

/**
 * User: mew <p />
 * Time: 17/7/14 14:57  <p />
 * Version: V1.0  <p />
 * Description: 自定义缓存的入口 <p />
 */
public class LoggingRedisCache extends LoggingCache {

    public LoggingRedisCache(String id) {
        super(new MyCache(id));
    }

}
