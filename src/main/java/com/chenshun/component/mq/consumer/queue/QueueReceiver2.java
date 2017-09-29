package com.chenshun.component.mq.consumer.queue;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * User: mew <p />
 * Time: 17/9/29 16:19  <p />
 * Version: V1.0  <p />
 * Description: 队列消息监听器 <p />
 */
@Component("queueReceiver2")
public class QueueReceiver2 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("QueueReceiver2接收到消息:" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}