package com.chenshun.test.activemq.group;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * User: mew <p />
 * Time: 17/8/24 19:29  <p />
 * Version: V1.0  <p />
 * Description: 发送组消息 <p />
 */
public class JMSSender {

    private QueueConnection connection = null;

    private QueueSession session = null;

    private QueueSender sender = null;

    public JMSSender(String queuecf, String requestQueue) {
        try {
            // connect to the jms provider and create the connection, session, and sender
            Context ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup(queuecf);
            connection = factory.createQueueConnection();
            connection.start();

            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) ctx.lookup(requestQueue);
            sender = session.createSender(queue);
        } catch (Exception jmse) {
            jmse.printStackTrace();
        }
    }

    public void sendMessageGroup() throws JMSException {
        // send the messages as a group
        sendSequenceMarker("START_SEQUENCE", "GROUP1", true);
        sendMessage("First Message", "GROUP1", 1);
        sendMessage("Second Message", "GROUP1", 2);
        sendMessage("Third Message", "GROUP1", 3);
        sendSequenceMarker("END_SEQUENCE", "GROUP1", false);
        connection.close();
    }

    // send a simple text message within the group of messages
    private void sendMessage(String text, String messageGroupId, int sequence) throws JMSException {
        TextMessage msg = session.createTextMessage(text);
        msg.setIntProperty("Sequence", sequence);
        msg.setStringProperty("JMSXGroupID", messageGroupId);
        msg.setJMSRedelivered(true);
        sender.send(msg);
    }

    // send an empty payload message containing the sequence marker
    private void sendSequenceMarker(String marker, String messageGroupId, boolean redelivered) throws JMSException {
        BytesMessage msg = session.createBytesMessage();
        // 任何数据类型都可以作为数据标记
        msg.setStringProperty("SequenceMarker", marker);
        msg.setStringProperty("JMSXGroupID", messageGroupId);
        msg.setJMSRedelivered(redelivered);
        sender.send(msg);
    }

    public static void main(String[] args) {
        try {
            JMSSender app = new JMSSender("queueConnectionFactory", "queue1");
            app.sendMessageGroup();
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
