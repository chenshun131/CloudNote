package com.chenshun.test.mybatis;

import com.chenshun.studyapp.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * User: mew <p />
 * Time: 17/7/14 13:48  <p />
 * Version: V1.0  <p />
 * Description: RunWith:基于 JUnit4 的Spring测试框架 <p />
 * ContextConfiguration:启动 Spring 容器，由 Spring 提供 <p />
 * 使用 Redis 作为二级缓存框架
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml", "/spring/spring-mvc.xml"})
public class CacheTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void findByNameTest() {// 验证 flushCache和useCache
        System.out.println("++++++++++++++++++++++++  第一次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");

        System.out.println("++++++++++++++++++++++++  第二次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");

        System.out.println("++++++++++++++++++++++++  第三次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");

        System.out.println("++++++++++++++++++++++++  第四次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");

        userMapper.delete("pc");

        System.out.println("++++++++++++++++++++++++  第五次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");

        System.out.println("++++++++++++++++++++++++  第六次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");

        System.out.println("++++++++++++++++++++++++  第七次查询数据 +++++++++++++++++++++++++++++");
        userMapper.findByName("demo");
    }

}
