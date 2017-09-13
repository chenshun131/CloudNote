package com.chenshun.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * User: mew <p />
 * Time: 17/9/13 09:31  <p />
 * Version: V1.0  <p />
 * Description: 事物处理操作 <br/>
 * <pre>
 *          ┌─┐       ┌─┐
 *       ┌──┘ ┴───────┘ ┴──┐
 *       │                 │
 *       │       ───       │
 *       │  ─┬┘       └┬─  │
 *       │                 │
 *       │       ─┴─       │
 *       │                 │
 *       └───┐         ┌───┘
 *           │         │
 *           │         │
 *           │         │
 *           │         └──────────────┐
 *           │                        │
 *           │                        ├─┐
 *           │                        ┌─┘
 *           │                        │
 *           └─┐  ┐  ┌───────┬──┐  ┌──┘
 *             │ ─┤ ─┤       │ ─┤ ─┤
 *             └──┴──┘       └──┴──┘
 *                 神兽保佑
 *                 代码无BUG!
 * </pre>
 */
public class TransactionTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop-senior01", 6379);

//        jedis.watch("serialNum");
//        jedis.set("serialNum", "s##################");
//        jedis.unwatch();

        Transaction transaction = jedis.multi(); // 被当作一个命令执行
        transaction.set("serialNum", "s0002");
        Response<String> response = transaction.get("serialNum");
        transaction.lpush("listKKK", "a1");
        transaction.lpush("listKKK", "a2");
        transaction.lpush("listKKK", "a3");
        transaction.lpush("listKKK", "a4");
        transaction.lpush("listKKK", "a5");
        transaction.exec();

//        transaction.discard();
        System.out.println("serialNum response => " + response.get());

        jedis.close();
    }

}
