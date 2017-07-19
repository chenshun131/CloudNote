package com.chenshun.component.service;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/7/14 12:41  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class CustomerRedisService {

    private RedisTemplate<String, Object> redisTemplate;

    /** REDIS对象统一前缀 */
    private static final String REDIS_KEY_PREFIX = "cloudnote_";

    public void put(String key, Object value, int expire) {
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + key, value);
        redisTemplate.expire(REDIS_KEY_PREFIX + key, expire, TimeUnit.SECONDS);
    }

    public void persist(String key, Object value) {
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + key);
    }

    public void createOrUpdateRedisStringWithTTL(String key, String value, int expire) {
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + key, value);
        redisTemplate.expire(REDIS_KEY_PREFIX + key, expire, TimeUnit.SECONDS);
    }

    public void createOrUpdateRedisString(String key, String value) {
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(REDIS_KEY_PREFIX + key);
    }

    public boolean keyExists(String key) {
        return redisTemplate.hasKey(REDIS_KEY_PREFIX + key);
    }

    public long stringIncr(String key, long incrNum) {
        return redisTemplate.opsForValue().increment(REDIS_KEY_PREFIX + key, incrNum);
    }

    public long createOrUpdateRedisListByLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(REDIS_KEY_PREFIX + key, value);
    }

    public long getRedisListLength(String key) {
        return redisTemplate.opsForList().size(REDIS_KEY_PREFIX + key);
    }

    public boolean setTTL(String key, int expire) {
        return redisTemplate.expire(REDIS_KEY_PREFIX + key, expire, TimeUnit.SECONDS);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}

