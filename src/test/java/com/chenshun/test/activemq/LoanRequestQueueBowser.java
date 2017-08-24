package com.chenshun.test.activemq;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * User: mew <p />
 * Time: 17/8/24 11:01  <p />
 * Version: V1.0  <p />
 * Description: 查看队列中的所有数据　 <p />
 */
public class LoanRequestQueueBowser {

    public static void main(String[] args) {
        try {
            // Obtain a JNDI connection using the jndi.properties file
            InitialContext ctx = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("queueConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            // 建立会话
            Queue queue = (Queue) ctx.lookup("queue1");
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueBrowser browser = session.createBrowser(queue);

            Enumeration<Message> e = browser.getEnumeration();
            while (e.hasMoreElements()) {
                Message message = e.nextElement();
                if (message instanceof ActiveMQMapMessage) {
                    ActiveMQMapMessage mapMessage = (ActiveMQMapMessage) message;
                    Map<String, Object> map = mapMessage.getContentMap();
                    Set<Map.Entry<String, Object>> set = map.entrySet();
                    for (Map.Entry<String, Object> entry : set) {
                        System.out.println(entry.getKey() + ":" + entry.getValue());
                    }
                } else if (message instanceof ActiveMQTextMessage) {
                    ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
                    System.out.println("Browsing : " + textMessage.getText());
                }
            }
            browser.close();
            connection.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
