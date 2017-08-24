package com.chenshun.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * User: mew <p />
 * Time: 17/8/22 19:30  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ActiveMQTest {

    @Test
    public void sendMessageTest() {
        ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session; // 保存用于消息传送的事务性工作单元
        Destination destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://hadoop-senior01:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            // 第一个参数是是否是事务型消息，设置为true,第二个参数无效
            // 第二个参数是
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            // 时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            // 待测试
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-queue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 优先级不能影响先进先出。。。那这个用处究竟是什么呢呢呢呢
            MqBean bean = new MqBean();
            bean.setAge(13);
            for (int i = 0; i < 100; i++) {
                bean.setName("小黄" + i);
                producer.send(session.createObjectMessage(bean));
            }
            producer.close();
            System.out.println("消息发送完成！");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void receiveMessageTest() {
        ActiveMQConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://hadoop-senior01:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            //这个最好还是有事务
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("test-queue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        MqBean bean = (MqBean) ((ObjectMessage) message).getObject();
                        System.out.println(bean);
                        if (null != message) {
                            System.out.println("收到消息" + bean.getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void topicSendTest() {
        ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Destination destination;
        MessageProducer producer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://hadoop-senior01:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createTopic("test-topic");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 优先级不能影响先进先出。。。那这个用处究竟是什么呢呢呢呢
            MqBean bean = new MqBean();
            bean.setAge(13);
            for (int i = 0; i < 100; i++) {
                Thread.sleep(1000);
                bean.setName("小黄" + i);
                producer.send(session.createObjectMessage(bean));
            }
            producer.close();
            System.out.println("呵呵");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void topicReceiveTest() {
        ActiveMQConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://hadoop-senior01:61616");
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            //这个最好还是有事务
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("test-queue");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        MqBean bean = (MqBean) ((ObjectMessage) message).getObject();
                        System.out.println(bean);
                        if (null != message) {
                            System.out.println("收到消息" + bean.getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
