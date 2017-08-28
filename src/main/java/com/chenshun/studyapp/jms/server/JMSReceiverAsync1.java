package com.chenshun.studyapp.jms.server;

import javax.jms.IllegalStateException;
import javax.jms.*;

/**
 * User: mew <p />
 * Time: 17/8/25 13:51  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class JMSReceiverAsync1 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println(((TextMessage) message).getText());
            } else if (message instanceof MapMessage) {
                MapMessage mmsg = (MapMessage) message;
                System.out.println(mmsg.getLong("acctId") + ", " +
                        mmsg.getString("side") + ", " +
                        mmsg.getString("symbol") + ", " +
                        mmsg.getDouble("shares"));
            } else {
                throw new IllegalStateException("Message Type Not Supported");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
