package com.chenshun.studyapp.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: mew <p />
 * Time: 17/7/14 12:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final Jedis redisClient = createClient();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    private String host;

    public MyCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {// 获取缓存编号
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {// 保存key值缓存对象
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
        redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
    }

    @Override
    public Object getObject(Object key) {// 获取key值缓存对象
        Object value = SerializeUtil.unserialize(redisClient.get(SerializeUtil.serialize(key.toString())));
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key + "=" + value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {// 删除key值缓存对象
        return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
    }

    @Override
    public void clear() {// 清空缓存
        redisClient.flushDB();
    }

    @Override
    public synchronized int getSize() {// 获取缓存对象大小
        return Integer.valueOf(redisClient.dbSize().toString());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {// 获取缓存的读写锁
        return readWriteLock;
    }

    public void setHost(String host) {
        this.host = host;
    }

    protected static Jedis createClient() {
        try {
            JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
            Jedis jedis = pool.getResource();
//            jedis.auth("jintoufs");
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("初始化连接池错误");
    }

}
