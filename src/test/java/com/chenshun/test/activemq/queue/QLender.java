package com.chenshun.test.activemq.queue;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * User: mew <p />
 * Time: 17/8/23 18:43  <p />
 * Version: V1.0  <p />
 * Description: 点对点 消息侦听 <p />
 */
public class QLender implements MessageListener {

    private QueueConnection qConnect = null;

    private QueueSession qSession = null;

    private Queue requestQ = null;

    public QLender(String queuecf, String requestQueue) {
        try {
            // Connect to the provider and get the JMS connection
            Context ctx = new InitialContext();
            QueueConnectionFactory qFactory = (QueueConnectionFactory) ctx.lookup(queuecf);
            qConnect = qFactory.createQueueConnection();

            // 展示连接元数据
            ConnectionMetaData metadata = qConnect.getMetaData();
            System.out.println("JMS Version:  " + metadata.getJMSMajorVersion() + "." + metadata.getJMSMinorVersion());
            System.out.println("JMS Provider: " + metadata.getJMSProviderName());
            System.out.println("JMS Provider Version: " + metadata.getProviderMajorVersion());
            System.out.println("JMSX Properties Supported: ");
            Enumeration e = metadata.getJMSXPropertyNames();
            while (e.hasMoreElements()) {
                System.out.println("   " + e.nextElement());
            }

            // Create the JMS Session
            qSession = qConnect.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // Lookup the request queue
            requestQ = (Queue) ctx.lookup(requestQueue);

            // Now that setup is complete, start the Connection
            qConnect.start();

            // Create the message listener
            QueueReceiver qReceiver = qSession.createReceiver(requestQ);
            qReceiver.setMessageListener(this);

            System.out.println("Waiting for loan requests...");
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
            boolean accepted = false;

            if (message instanceof ActiveMQMapMessage) {
                // Get the data from the message
                ActiveMQMapMessage msg = (ActiveMQMapMessage) message;
                String filter = "JMSCorrelationID = '" + msg.getJMSMessageID() + "'";
                System.out.println(filter);

                double salary = msg.getDouble("Salary");
                double loanAmt = msg.getDouble("LoanAmount");

                // Determine whether to accept or decline the loan
                if (loanAmt < 200000) {
                    accepted = (salary / loanAmt) > .25;
                } else {
                    accepted = (salary / loanAmt) > .33;
                }
                System.out.println("" + "Percent = " + (salary / loanAmt) + ", loan is " +
                        (accepted ? "Accepted!" : "Declined"));

                // Send the results back to the borrower
                TextMessage tmsg = qSession.createTextMessage();
                tmsg.setText(accepted ? "Accepted!" : "Declined");
                tmsg.setJMSCorrelationID(message.getJMSMessageID());

                // Create the sender and send the message
                QueueSender qSender = qSession.createSender((Queue) message.getJMSReplyTo());
                qSender.send(tmsg);

                System.out.println("\nWaiting for loan requests...");
            } else if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
                String text = textMessage.getText();
                System.out.println(text);

                // Send the results back to the borrower
                TextMessage tmsg = qSession.createTextMessage();
                tmsg.setText("This message can ignore!");
                tmsg.setJMSCorrelationID(message.getJMSMessageID());

                // Create the sender and send the message
                QueueSender qSender = qSession.createSender((Queue) message.getJMSReplyTo());
                qSender.send(tmsg);
            } else {
                throw new IllegalArgumentException("unsupported message type");
            }

            System.out.println("");
            System.out.println("All Property : " + message.getClass().getSimpleName());
            Enumeration<String> propertyNames = message.getPropertyNames();
            while (propertyNames.hasMoreElements()) {
                String name = propertyNames.nextElement();
                String value = message.getStringProperty(name);
                System.out.println(name + " = " + value);
            }
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
            qConnect.close();
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String args[]) {
        String queuecf = null;
        String requestq = null;

        args = new String[]{"queueConnectionFactory", "queue1"};
        if (args.length == 2) {
            queuecf = args[0];
            requestq = args[1];
        } else {
            System.out.println("Invalid arguments. Should be: ");
            System.out.println("java QLender factory request_queue");
            System.exit(0);
        }

        QLender lender = new QLender(queuecf, requestq);

        try {
            // Run until enter is pressed
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("QLender application started");
            System.out.println("Press enter to quit application");
            stdin.readLine();
            lender.exit();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
