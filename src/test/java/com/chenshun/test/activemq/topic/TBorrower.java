package com.chenshun.test.activemq.topic;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: mew <p />
 * Time: 17/8/24 13:41  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TBorrower implements MessageListener {

    private TopicConnection tConnect = null;

    private TopicSession tSession = null;

    private Topic topic = null;

    TopicSubscriber subscriber = null;

    private double currentRate;

    /** 是否是持久订阅者 */
    private boolean isDurable;

    public TBorrower(String topiccf, String topicName, String rate, boolean isDurable) {
        try {
            currentRate = Double.valueOf(rate);
            this.isDurable = isDurable;

            // Connect to the provider and get the JMS connection
            Context ctx = new InitialContext();
            TopicConnectionFactory qFactory = (TopicConnectionFactory) ctx.lookup(topiccf);
            tConnect = qFactory.createTopicConnection();
            if (isDurable) { // 如果是持久订阅者，就需要设置用户 ID
                tConnect.setClientID("123456789ABC");
            }

            // Create the JMS Session
            tSession = tConnect.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            // Lookup the request and response queues
            topic = (Topic) ctx.lookup(topicName);

            // Create the message listener
            if (isDurable) { // 创建持久订阅者
                subscriber = tSession.createDurableSubscriber(topic, "Borrower1");
            } else { // 创建非持久订阅者
                subscriber = tSession.createSubscriber(topic);

//                // 带过滤器的的订阅，之后的消息监听器只会收到满足条件消息
//                subscriber = tSession.createSubscriber(topic, "(currentRate - newRate) >= 1.0", true);
//                subscriber = tSession.createSubscriber(topic, "type = 1", true);
            }
            subscriber.setMessageListener(this);

            // Now that setup is complete, start the Connection
            tConnect.start();

            System.out.println("Waiting for loan requests...");

            String selector1 = "PhysicianType IN('Chiropractor','Psychologist','Dermatolgist') AND " +
                    "PatientGroupID LIKE 'ACME%' AND (Shares * Price) >= 10000";
            String selector2 = "InventoryID='ISO8859-1' AND Quantity BETWEEN 100 AND 13000";
            String selector3 = "CustomerType='GOLD' OR JMSPriority BETWEEN 5 AND 9"; // 通过 JMSPriority 设置优先级
        } catch (JMSException jmse) {
            jmse.printStackTrace();
            System.exit(1);
        } catch (NamingException jne) {
            jne.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            // Get the data from the message
            if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
                System.out.println(textMessage.getText());
            } else if (message instanceof ActiveMQBytesMessage) {
                ActiveMQBytesMessage bytesMessage = (ActiveMQBytesMessage) message;
                double newRate = bytesMessage.readDouble();

                // If the rate is at least 1 point lower then the current rate then recommend refinancing
                if ((currentRate - newRate) >= 1.0) {
                    System.out.println("New rate = " + newRate + " - Consider refinancing loan");
                } else {
                    System.out.println("New rate = " + newRate + " - Keep existing loan");
                }
            } else {
                System.out.println("unknow type message!");
            }
            System.out.println("\nWaiting for rate updates...");
            System.out.println("---------------------------------------------------");
        } catch (JMSException jmse) {
            jmse.printStackTrace();
            System.exit(1);
        } catch (Exception jmse) {
            jmse.printStackTrace();
            System.exit(1);
        }
    }

    private void exit() {
        try {
            if (isDurable) { // 取消订阅状态持久订阅者
                subscriber.close();
                tSession.unsubscribe("Borrower1");
            }
            tConnect.close();
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String args[]) {
        String topiccf = null;
        String topicName = null;
        String rate = null;

        args = new String[]{"queueConnectionFactory", "Topic2", "5.6"};
        if (args.length == 3) {
            topiccf = args[0];
            topicName = args[1];
            rate = args[2];
        } else {
            System.out.println("Invalid arguments. Should be: ");
            System.out.println("java TBorrower factory topic rate");
            System.exit(0);
        }

        TBorrower borrower = new TBorrower(topiccf, topicName, rate, true);

        try {
            // Run until enter is pressed
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("TBorrower application started");
            System.out.println("Press enter to quit application");
            stdin.readLine();
            borrower.exit();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
