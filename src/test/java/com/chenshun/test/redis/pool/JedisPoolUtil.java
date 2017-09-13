package com.chenshun.test.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * User: mew <p />
 * Time: 17/9/13 10:04  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class JedisPoolUtil {

    private static volatile JedisPool jedisPool = null; // 被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存

    private JedisPoolUtil() {
    }

    public static JedisPool getJedisPoolInstance() {
        if (null == jedisPool) {
            synchronized (JedisPoolUtil.class) {
                if (null == jedisPool) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxIdle(32);
                    poolConfig.setTestOnBorrow(true);

                    jedisPool = new JedisPool(poolConfig, "hadoop-senior01");
                }
            }
        }
        return jedisPool;
    }

    public static void release(JedisPool jedisPool, Jedis jedis) {
        if (null != jedis) {
            jedisPool.returnResourceObject(jedis);
        }
    }

    public static void main(String[] args) {
        JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.set("k18", "v183");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisPoolUtil.release(jedisPool, jedis);
        }
    }

}
