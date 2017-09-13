package com.chenshun.test.activemq;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * User: mew <p />
 * Time: 17/8/23 14:02  <p />
 * Version: V1.0  <p />
 * Description: 聊天应用使用订阅方式 <p />
 * <pre>
 *                 .-~~~~~~~~~-._       _.-~~~~~~~~~-.
 *             __.'              ~.   .~              `.__
 *           .'//                  \./                  \\`.
 *         .'//                     |                     \\`.
 *       .'// .-~"""""""~~~~-._     |     _,-~~~~"""""""~-. \\`.
 *     .'//.-"                 `-.  |  .-'                 "-.\\`.
 *   .'//______.============-..   \ | /   ..-============.______\\`.
 * .'______________________________\|/______________________________`.
 * </pre>
 */
public class Chat implements javax.jms.MessageListener {

    private TopicSession pubSession;

    private TopicPublisher publisher;

    private TopicConnection connection;

    private String username;

    /* Constructor used to Initialize Chat */
    public Chat(String topicFactory, String topicName, String username) throws Exception {
        // Obtain a JNDI connection using the jndi.properties file
        InitialContext ctx = new InitialContext();

        // Look up a JMS connection factory
        TopicConnectionFactory conFactory = (TopicConnectionFactory) ctx.lookup(topicFactory);

        // Create a JMS connection
        TopicConnection connection = conFactory.createTopicConnection();

        // 展示连接元数据
        ConnectionMetaData metadata = connection.getMetaData();
        System.out.println("JMS Version:  " + metadata.getJMSMajorVersion() + "." + metadata.getJMSMinorVersion());
        System.out.println("JMS Provider: " + metadata.getJMSProviderName());
        System.out.println("JMS Provider Version: " + metadata.getProviderMajorVersion());
        System.out.println("JMSX Properties Supported: ");
        Enumeration e = metadata.getJMSXPropertyNames();
        while (e.hasMoreElements()) {
            System.out.println("   " + e.nextElement());
        }

        // Create two JMS session objects
        // 一般认为从同一个连接中创建多个 session 对象效率更高，因为会话共享对统一连接的访问
        TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        // Look up a JMS topic
        Topic chatTopic = (Topic) ctx.lookup(topicName);

        // Create a JMS publisher and subscriber
        TopicPublisher publisher = pubSession.createPublisher(chatTopic);
        publisher.setDeliveryMode(DeliveryMode.PERSISTENT);
        TopicSubscriber subscriber = subSession.createSubscriber(chatTopic, null, true);

        // Set a JMS message listener
        // 一个 TopicSubscriber 只能有一个 MessageListener
        subscriber.setMessageListener(this);

        // Intialize the Chat application variables
        this.connection = connection;
        this.pubSession = pubSession;
        this.publisher = publisher;
        this.username = username;

        // Start the JMS connection; allows messages to be delivered
        connection.start();
    }

    /* Receive Messages From Topic Subscriber */
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);

            Enumeration<String> propertyNames = message.getPropertyNames();
            while (propertyNames.hasMoreElements()) {
                String name = propertyNames.nextElement();
                String value = message.getStringProperty(name);
                System.out.println(name + " = " + value);
            }
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    /* Create and Send Message Using Publisher */
    protected void writeMessage(String text) throws JMSException {
        TextMessage message = pubSession.createTextMessage();
        message.setText(username + ": " + text);
        message.setStringProperty("username", text);
        publisher.publish(message);
    }

    /* Close the JMS Connection */
    public void close() throws JMSException {
        connection.close();
    }

    /* Run the Chat Client */
    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                System.out.println("Factory, Topic, or username missing");
            }

            args = new String[]{"topicConnectionFactry", "Topic1", "username"};
            // args[0]=topicFactory; args[1]=topicName; args[2]=username
            Chat chat = new Chat(args[0], args[1], args[2]);

            // Read from command line
            BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));

            // Loop until the word "exit" is typed
            while (true) {
                String s = commandLine.readLine();
                if (s.equalsIgnoreCase("exit")) {
                    chat.close(); // close down connection
                    System.exit(0);// exit program
                } else {
                    chat.writeMessage(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
