package com.chenshun.component.mq.consumer.topic;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * User: mew <p />
 * Time: 17/9/29 16:21  <p />
 * Version: V1.0  <p />
 * Description: Topic消息监听器 <p />
 */
@Component("topicReceiver2")
public class TopicReceiver2 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("TopicReceiver2接收到消息:" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}