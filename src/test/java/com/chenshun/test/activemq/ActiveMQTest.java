package com.chenshun.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
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
        // 根据用户名，密码，url创建一个连接工厂
        connectionFactory = new ActiveMQConnectionFactory("system", "manager", "tcp://hadoop-senior01:61616");
        try {
            connection = connectionFactory.createConnection();// 从工厂中获取一个连接
            connection.start();
            // 第一个参数是是否是事务型消息，设置为true,第二个参数无效
            // 第二个参数是
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            // 时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            // 待测试
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            // 创建一个到达的目的地，连接一个名为"test-queue"的队列，这个会话将会到这个队列，如果这个队列不存在，将会被创建
            destination = session.createQueue("test-queue");
            producer = session.createProducer(destination); // 从session中，获取一个消息生产者
            // 设置生产者的模式，有两种可选 :
            //      DeliveryMode.PERSISTENT:当 ActiveMQ 关闭的时候，队列数据将会被保存
            //      DeliveryMode.NON_PERSISTENT:当 ActiveMQ 关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            MqBean bean = new MqBean(); // 优先级不能影响先进先出
            bean.setAge(13);
            for (int i = 0; i < 100; i++) {
                bean.setName("小黄" + i);
                // 创建一条消息，当然，消息的类型有很多，如文字，字节，对象等,可以通过session.create..方法来创建出来
                producer.send(session.createObjectMessage(bean));
            }
            producer.close(); // 即便生产者的对象关闭了，程序还在运行
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
            // 创建一个session
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第二个参数为false时，paramB的值可为
            //     Session.AUTO_ACKNOWLEDGE,Session.CLIENT_ACKNOWLEDGE,Session.DUPS_OK_ACKNOWLEDGE其中一个
            //        AUTO_ACKNOWLEDGE : 为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功
            //        CLIENT_ACKNOWLEDGE : 为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息
            //        DUPS_OK_ACKNOWLEDGE : 允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("test-queue");
            consumer = session.createConsumer(destination); // 根据session，创建一个接收者对象
            consumer.setMessageListener(new MessageListener() { // 设置消息监听器
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
        Destination destination; // 目的地，其实就是连接到哪个队列，如果是点对点，那么它的实现是Queue，如果是订阅模式，那它的实现是Topic
        MessageProducer producer; // 生产者，就是产生数据的对象
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://hadoop-senior01:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            // 创建一个session
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个
            //    Session.AUTO_ACKNOWLEDGE : 为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功
            //    Session.CLIENT_ACKNOWLEDGE : 为客户端确认，客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法，jms服务器才会当作发送成功，并删除消息
            //    Session.DUPS_OK_ACKNOWLEDGE : 允许副本的确认模式，一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            // 创建主题，要是已存在则使用因存在的主题
            destination = session.createTopic("test-topic");
            // 从session中，获取一个消息生产者
            producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            //   DeliveryMode.PERSISTENT : 当ActiveMQ关闭的时候，队列数据将会被保存
            //   DeliveryMode.NON_PERSISTENT : 当ActiveMQ关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
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

    @Test
    public void sendTempMessageTest() {
        ActiveMQConnectionFactory connectionFactory;
        Connection connection;
        Session session; // 保存用于消息传送的事务性工作单元
        Destination destination;
        MessageProducer producer;
        // 根据用户名，密码，url创建一个连接工厂
        connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://hadoop-senior01:61616");
        // 修改消息队列连接工厂设置
        RedeliveryPolicy policy = connectionFactory.getRedeliveryPolicy();
        policy.setInitialRedeliveryDelay(500);
        policy.setBackOffMultiplier(2);
        policy.setUseExponentialBackOff(true);
        policy.setMaximumRedeliveries(2);
        try {
            connection = connectionFactory.createConnection();// 从工厂中获取一个连接
            connection.start();
            // 第一个参数是是否是事务型消息，设置为true,第二个参数无效
            // 第二个参数是
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
            // 时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
            // 待测试
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            // 创建一个到达的目的地，连接一个名为"test-queue"的队列，这个会话将会到这个队列，如果这个队列不存在，将会被创建
            destination = session.createQueue("test-queue");
            producer = session.createProducer(destination); // 从session中，获取一个消息生产者
            // 设置生产者的模式，有两种可选 :
            //      DeliveryMode.PERSISTENT:当 ActiveMQ 关闭的时候，队列数据将会被保存
            //      DeliveryMode.NON_PERSISTENT:当 ActiveMQ 关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.setTimeToLive(1000); // 设置超时时间
            MqBean bean = new MqBean(); // 优先级不能影响先进先出
            bean.setAge(13);
            for (int i = 0; i < 10; i++) {
                bean.setName("小黄" + i);
                // 创建一条消息，当然，消息的类型有很多，如文字，字节，对象等,可以通过session.create..方法来创建出来
                producer.send(session.createObjectMessage(bean));
            }
            producer.close(); // 即便生产者的对象关闭了，程序还在运行
            System.out.println("消息发送完成！");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDiscardMessageTest() {
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
            // 创建一个session
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第二个参数为false时，paramB的值可为
            //     Session.AUTO_ACKNOWLEDGE,Session.CLIENT_ACKNOWLEDGE,Session.DUPS_OK_ACKNOWLEDGE其中一个
            //        AUTO_ACKNOWLEDGE : 为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功
            //        CLIENT_ACKNOWLEDGE : 为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息
            //        DUPS_OK_ACKNOWLEDGE : 允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("ActiveMQ.DLQ");
            consumer = session.createConsumer(destination); // 根据session，创建一个接收者对象
            consumer.setMessageListener(new MessageListener() { // 设置消息监听器
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
