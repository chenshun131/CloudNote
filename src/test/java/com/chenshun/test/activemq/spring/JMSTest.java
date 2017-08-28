package com.chenshun.test.activemq.spring;

import com.chenshun.studyapp.jms.client.JMSSender;
import com.chenshun.studyapp.jms.client.JMSSenderReply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: mew <p />
 * Time: 17/8/25 14:40  <p />
 * Version: V1.0  <p />
 * Description: 测试 ActiveMQ 和 Spring 整合的 JMS <p />
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml", "/spring/spring-mvc.xml"})
public class JMSTest {

    @Autowired
    private JMSSender jmsSender;

    @Autowired
    private JMSSenderReply jmsSenderReply;

    @Test
    public void sendMessage1() throws Exception {
        jmsSender.sendMessage();
    }

    @Test
    public void sendMessage2() throws Exception {
        jmsSenderReply.sendMessage();
    }

}
