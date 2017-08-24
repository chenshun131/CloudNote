package com.chenshun.test.activemq.transact;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * User: mew <p />
 * Time: 17/8/24 20:27  <p />
 * Version: V1.0  <p />
 * Description: 事务消息 消费者 <p />
 */
public class JMSReceiverTransacted implements MessageListener, ExceptionListener {

    private QueueSession session = null;

    private String queuecf;

    private String requestQueue;

    public JMSReceiverTransacted(String queuecf, String requestQueue) {
        this.queuecf = queuecf;
        this.requestQueue = requestQueue;

        establishConnection();
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println("Message: " + msg.getText());
            System.out.println("Waiting for messages...");
        } catch (Exception up) {
            up.printStackTrace();
        }
    }

    @Override
    public void onException(javax.jms.JMSException jmse) {
        // 告诉用户存在一个问题
        System.err.println("\n\nThere is a problem with the connection.");
        System.err.println("JMSException: " + jmse.getMessage());
        System.err.println("Please wait while the application tries to reestablish the connection...");

        establishConnection();
    }

    private void establishConnection() {
        try {
            Context ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup(queuecf);
            QueueConnection connection = factory.createQueueConnection();
            connection.start();
            connection.setExceptionListener(this); // 创建丢失连接异常 监听器

            Queue queue = (Queue) ctx.lookup(requestQueue);
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver receiver = session.createReceiver(queue);
            receiver.setMessageListener(this);

            System.out.println("Waiting for messages...");
        } catch (Exception up) {
            up.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new JMSReceiverTransacted("queueConnectionFactory", "queue1");
        } catch (Exception up) {
            up.printStackTrace();
        }
    }

}
