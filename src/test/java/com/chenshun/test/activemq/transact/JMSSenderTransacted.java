package com.chenshun.test.activemq.transact;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * User: mew <p />
 * Time: 17/8/24 20:26  <p />
 * Version: V1.0  <p />
 * Description: 事务消息 生产者 <p />
 */
public class JMSSenderTransacted {

    private QueueConnection connection = null;

    private QueueSession session = null;

    private QueueSender sender = null;

    // create the connection, session, and sender
    public JMSSenderTransacted(String queuecf, String requestQueue) {
        try {
            Context ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup(queuecf);
            connection = factory.createQueueConnection();
            connection.start();

            session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE); // 指定是一个事务性会话
            Queue queue = (Queue) ctx.lookup(requestQueue);
            sender = session.createSender(queue);
        } catch (Exception jmse) {
            jmse.printStackTrace();
            System.exit(0);
        }
    }

    // initialize jms and send messages
    public void sendMessages() {
        try {
            //send the messages in a transaction
            System.out.println("Session Transacted: " + session.getTransacted());

            sendMessage("First Message");
            sendMessage("Second Message");
            //if (true) throw new Exception("rolling back");
            sendMessage("Third Message");

            session.commit(); //no send if not committed - default is NOT o send messages - they just disappear

            connection.close();
        } catch (Exception ex) {
            try {
                System.out.println("Exception caught, rolling back session");
                session.rollback();
            } catch (JMSException jmse) {
                jmse.printStackTrace();
            }
        }
    }

    // send a simple text message within the group of messages
    private void sendMessage(String text) throws Exception {
        TextMessage msg = session.createTextMessage(text);
        sender.send(msg);
    }

    public static void main(String[] args) {
        try {
            JMSSenderTransacted app = new JMSSenderTransacted("queueConnectionFactory", "queue1");
            app.sendMessages();
            System.exit(0);
        } catch (Exception up) {
            up.printStackTrace();
        }
    }

}
