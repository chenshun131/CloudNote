package com.chenshun.test.redis;

import redis.clients.jedis.Jedis;

/**
 * User: mew <p />
 * Time: 17/9/12 09:40  <p />
 * Version: V1.0  <p />
 * Description:
 * <pre>
 *                     ,----------------,              ,---------,
 *                ,-----------------------,          ,"        ,"|
 *              ,"                      ,"|        ,"        ,"  |
 *             +-----------------------+  |      ,"        ,"    |
 *             |  .-----------------.  |  |     +---------+      |
 *             |  |                 |  |  |     | -==----'|      |
 *             |  |  I LOVE DOS!    |  |  |     |         |      |
 *             |  |  Bad command or |  |  |/----|`---=    |      |
 *             |  | [root@ha ~]#_   |  |  |   ,/|==== ooo |      ;
 *             |  |                 |  |  |  // |(((( [33]|    ,"
 *             |  `-----------------'  |," .;'| |((((     |  ,"
 *             +-----------------------+  ;;  | |         |,"
 *                /_)______________(_/  //'   | +---------+
 *           ___________________________/___  `,
 *          /  oooooooooooooooo  .o.  oooo /,   \,"-----------
 *         / ==ooooooooooooooo==.o.  ooo= //   ,`\--{)B     ,"
 *        /_==__==========__==_ooo__ooo=_/'   /___________,"
 * </pre>
 */
public class Test1 {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop-senior01", 6379); // 192.168.242.129 hadoop-senior01

        // 判断是否连接上 Redis
//        System.out.println("Connection is OK ===> Ping:" + jedis.ping());

        // keys
//        Set<String> keys = jedis.keys("*");
//        System.out.print("keys : ");
//        for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
//            System.out.print(iterator.next().toString() + " ");
//        }
//        System.out.println("\njexits.exists ===> " + jedis.exists("k5"));
//        System.out.println(jedis.ttl("k1"));

        // String
//        jedis.append("k1", "=>k1 append");
//        System.out.println("k1 = " + jedis.get("k1"));
//        jedis.set("k6", "k6_redis");
//        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3", "str4", "v4", "str5", "v5");
//        System.out.println(jedis.mget("str1", "str2", "str3", "str4", "str5"));

        // list
//        System.out.println("-----------------------");
//        jedis.lpush("list1", "v1", "v2", "v3", "v4", "v5");
//        List<String> list = jedis.lrange("list1", 0, -1);
//        list.forEach(System.out::println);

        // hash
//        System.out.println("-----------------------");
//        jedis.hset("hash1", "hashK1", "hashV1");
//        jedis.hset("hash2", "hashK2", "hashV2");
//        System.out.println(jedis.hget("hash1", "hashK1"));
//        Map<String, String> map = new HashMap<>();
//        map.put("telephone", "15623459526");
//        map.put("address", "三泰魔方");
//        map.put("email", "123456@qq.com");
//        map.put("name", "ABC");
//        jedis.hmset("hash3", map);
//        jedis.hmget("hash3", "telephone", "address", "email").forEach(System.out::println);

        // set
//        System.out.println("-----------------------");
//        jedis.sadd("set1", "sv1", "sv2", "sv3", "sv4", "sv5");
//        jedis.smembers("set1").forEach(System.out::println);
//        jedis.srem("set1", "sv5");
//        System.out.println(jedis.smembers("set1").size());

        // zset
        jedis.zadd("zset1", 10, "zsetv1");
        jedis.zadd("zset1", 20, "zsetv2");
        jedis.zadd("zset1", 30, "zsetv3");
        jedis.zadd("zset1", 40, "zsetv4");
        jedis.zadd("zset1", 50, "zsetv5");
        jedis.zrange("zset1", 0, -1).forEach(System.out::println);

        jedis.close();
    }

}
