package com.chenshun.test.mongodb;

import com.chenshun.studyapp.entity.mongo.PageModel;
import com.chenshun.studyapp.entity.mongo.UserInfo;
import com.chenshun.studyapp.service.mongo.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/7/31 14:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml", "/spring/spring-mvc.xml"})
public class TestUserService {

    @Autowired
    private UserInfoService service;

    @Test
    public void save() {
        UserInfo user = new UserInfo();
        user.setName("张三");
        user.setAge(25);
        user.setBirth(Timestamp.valueOf("2017-4-12 16:52:00"));
        service.save(user);
        System.out.println("已生成ID:" + user.getId());
    }

    @Test
    public void find() {
        UserInfo user = service.find("597f03020f71200e5bf79143");
        System.out.println("姓名 : " + user.getName());
    }

    @Test
    public void update() {
        UserInfo user = service.find("58edf1b26f033406394a8a61");
        user.setAge(18);
        service.update(user);
    }

    @Test
    public void delete() {
        service.delete("58edef886f03c7b0fdba51b9");
    }

    @Test
    public void findAll() {
        List<UserInfo> list = service.findAll("age desc");
        for (UserInfo u : list) {
            System.out.println(u.getName());
        }
    }

    @Test
    public void findByProp() {
        List<UserInfo> list = service.findByProp("name", "张三");
        for (UserInfo u : list) {
            System.out.println(u.getName());
        }
    }

    @Test
    public void findByProps() {
        List<UserInfo> list = service.findByProps(new String[]{"name", "age"}, new Object[]{"张三", 18});
        for (UserInfo u : list) {
            System.out.println(u.getName());
        }
    }

    @Test
    public void pageAll() {
        PageModel<UserInfo> page = service.pageAll(1, 10);
        System.out.println("总记录：" + page.getTotalCount() + "，总页数：" + page.getTotalPage());
        for (UserInfo u : page.getList()) {
            System.out.println(u.getName());
        }
    }

    @Test
    public void pageByProp() {
        PageModel<UserInfo> page = service.pageByProp(1, 10, "name", "张三");
        System.out.println("总记录：" + page.getTotalCount() + "，总页数：" + page.getTotalPage());
        for (UserInfo u : page.getList()) {
            System.out.println(u.getName());
        }
    }

}
