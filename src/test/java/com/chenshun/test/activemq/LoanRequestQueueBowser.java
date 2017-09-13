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
 * Description: 查看队列中的所有数据 <p />
 * <pre>
 *                              _______________________________________
 *                             /  ___________________________________  \
 *      _--""""--_            /  /_/_/_/_/_|_|_|_|_|_|_|_|_|_\_\_\_\_\  \
 *     /          \          /  /_/_/_/_J__L_L_L_|_|_|_J_J_J__L_\_\_\_\  \
 *    /\          /\        /  /_/_/_J__L_J__L_L_|_|_|_J_J__L_J__L_\_\_\  \
 *    L ""-____-"" J       /  /_/_J__L_J__L_J_J__L_|_J__L_L_J__L_J__L_\_\  \
 *    \            /      /  /_/__L_/__L_J__L_J__L_|_J__L_J__L_J__\_J__\_\  \
 *     \_        _/      /  /_J__/_J__/__L_J__|__L_|_J__|__L_J__\__L_\__L_\  \
 *   _--"""""--_"       /  /  F /  F J  J  |  F J  |  F J  |  F  F J  \ J  \  \
 *  /           \      /  /--/-J--/--L--|--L-J--J--|--L--L-J--|--J--\--L-\--\  \
 * /\           /\    /  /__/__L_J__J___L_J__J__|__|__|__L__L_J___L__L_J__\__\  \
 * L ""-_____-"" J   /  /  /  /  F  F  J  J  |  |  |  |  |  F  F  J  J  \  \  \  \
 * \             /  /  /--/--/--/--J---L--|--|--|--o--|--|--|--J---L--\--\--\--\  \
 *  \_         _/  /  /__/__J__J___L__J___L__L__L__|__J__J__J___L__J___L__L__\__\  \
 *    "--___--"   /  /  /   F  F  J   F  J  J   F  |  J   F  F  J   F  J  J   \  \  \
 *               /  /--/---/--J---L--J---L--|--J---|---L--|--J---L--J---L--\---\--\  \
 *              /  /__J___/___L__/___L__J___L__J___|___L__J___L__J___\__J___\___L__\  \
 *             /  /   F  J   /  J   J   |  J   J   |   F   F  |   F   F  \   F  J   \  \
 *            /  /---/---L--J---L---L---L--|---|---|---|---|--J---J---J---L--J---\---\  \
 *           /  /___/___/___L__J___J___J___|___|___|___|___|___L___L___L__J___\___\___\  \
 *          /  /   /   /   /   F   F   F   F   F   |   J   J   J   J   J   \   \   \   \  \
 *         /  /___/___J___J___J___J___J____L___L___|___J___J____L___L___L___L___L___\___\  \
 *        /  /   /    F   F   F   |   |   J    F   |   J    F   |   |   J   J   J    \   \  \
 *       /  /___J____/___/___J____L___L___|___J____|____L___|___J___J____L___\___\____L___\  \
 *      /  /    F   /   J    F   J   J    |   J    |    F   |    F   F   J    F   \   J    \  \
 *     /  /____/___J____L___/____L___|____L___|____|____|___J____|___J____\___J____L___\____\  \
 *    /  /    /    F   /   J    J    F   J    F    |    J    F   J    F    F   \   J    \    \  \
 *   /  /____/____/___J____L____|____L___J____L____|____J____L___J____|____J____L___\____\____\  \
 *  /                                                                                             \
 * /_______________________________________________________________________________________________\
 * |                                                                                               |
 * | hs                                                                                            |
 * |_______________________________________________________________________________________________|
 * </pre>
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
