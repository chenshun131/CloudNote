package com.chenshun.studyapp.jms.client;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * User: mew <p />
 * Time: 17/8/25 13:41  <p />
 * Version: V1.0  <p />
 * Description: 发送消息 <p />
 */
public class JMSSender {

    public JmsTemplate jmsTemplate = null;

    /** 目标发送消息队列名称 */
    public String queueName = null;

    public void sendMessage() throws Exception {
        MessageCreator simpleTextMessage = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage msg = session.createTextMessage("TEST 1");
                return msg;
            }
        };

        MessageCreator textMessage = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                StringBuffer xml = new StringBuffer("\n");
                xml.append("<?xml version=\"1.0\"?>" + "\n");
                xml.append("<trade>" + "\n");
                xml.append("  <acctId>998</acctId>" + "\n");
                xml.append("  <side>BUY</side>" + "\n");
                xml.append("  <symbol>AAPL</symbol>" + "\n");
                xml.append("  <shares>600</shares>" + "\n");
                xml.append("</trade>" + "\n");
                return session.createTextMessage(xml.toString());
            }
        };

        MessageCreator mapMessage = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage msg = session.createMapMessage();
                msg.setLong("acctId", 12345);
                msg.setString("side", "SELL");
                msg.setString("symbol", "AAPL");
                msg.setDouble("shares", 250.0);
                return msg;
            }
        };
        jmsTemplate.send(queueName, simpleTextMessage);
        jmsTemplate.send(queueName, textMessage);
        jmsTemplate.send(queueName, mapMessage);
        System.out.println("Message Sent...");
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

}
