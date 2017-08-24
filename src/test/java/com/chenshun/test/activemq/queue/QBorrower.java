package com.chenshun.test.activemq.queue;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * User: mew <p />
 * Time: 17/8/23 18:40  <p />
 * Version: V1.0  <p />
 * Description: 点对点 消息发送 <p />
 */
public class QBorrower {

    private QueueConnection qConnect = null;

    private QueueSession qSession = null;

    private Queue responseQ = null;

    private Queue requestQ = null;

    public QBorrower(String queuecf, String requestQueue, String responseQueue) {
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
            qSession = qConnect.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); // 第一个参数:是否为事务性 第二个参数:确认订阅模式

            // Lookup the request and response queues
            requestQ = (Queue) ctx.lookup(requestQueue); // 贷款请求队列
            responseQ = (Queue) ctx.lookup(responseQueue); // 贷款响应队列

            // Now that setup is complete, start the Connection
            qConnect.start();
        } catch (NamingException jne) {
            jne.printStackTrace();
            System.exit(1);
        } catch (JMSException jmse) {
            jmse.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 发送消息
     *
     * @param salary
     * @param loanAmt
     */
    private void sendLoanRequest(double salary, double loanAmt) {
        try {
            // Create JMS message
            MapMessage msg = qSession.createMapMessage();
            msg.setDouble("Salary", salary);
            msg.setDouble("LoanAmount", loanAmt);
            msg.setJMSReplyTo(responseQ);

            //set the message expiration to 30 seconds
            msg.setJMSExpiration(new Date().getTime() + 30000); // 有效时间为0则永远不过期

            // Create the sender and send the message
            QueueSender qSender = qSession.createSender(requestQ);
            qSender.send(msg);

            // Wait to see if the loan request was accepted or declined
            String filter = "JMSCorrelationID = '" + msg.getJMSMessageID() + "'";
            System.out.println(filter);

            QueueReceiver qReceiver = qSession.createReceiver(responseQ, filter);
            TextMessage tmsg = (TextMessage) qReceiver.receive(30000);
            if (tmsg == null) {
                System.out.println("Lender not responding");
            } else {
                System.out.println("Loan request was " + tmsg.getText());
            }
        } catch (JMSException jmse) {
            jmse.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 发送消息组
     */
    private void publishManyMessages() {
        try {
            QueueSender sender = qSession.createSender(requestQ);

            // 发送一条空有效负载消息启动消息组
            BytesMessage startMsg = qSession.createBytesMessage();
            startMsg.setStringProperty("SequenceMarker", "START_SEQUENCE");
            sender.send(startMsg);

            // 发送消息
            TextMessage msg1 = qSession.createTextMessage();
            msg1.setText("Message 1");
            sender.send(msg1);

            TextMessage msg2 = qSession.createTextMessage();
            msg2.setText("Message 2");
            sender.send(msg2);

            TextMessage msg3 = qSession.createTextMessage();
            msg3.setText("Message 3");
            sender.send(msg3);

            // 发送一条空有效负载消息启动消息组
            BytesMessage endMsg = qSession.createBytesMessage();
            endMsg.setStringProperty("SequenceMarker", "END_SEQUENCE");
            sender.send(endMsg);
        } catch (JMSException e) {
            e.printStackTrace();
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
        String responseq = null;

        args = new String[]{"queueConnectionFactory", "queue1", "queue1"};
        if (args.length == 3) {
            queuecf = args[0];
            requestq = args[1];
            responseq = args[2];
        } else {
            System.out.println("Invalid arguments. Should be: ");
            System.out.println("java QBorrower factory requestQueue responseQueue");
            System.exit(0);
        }

        QBorrower borrower = new QBorrower(queuecf, requestq, responseq);

        try {
            // Read all standard input and send it as a message
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("QBorrower Application Started");
            System.out.println("Press enter to quit application");
            System.out.println("Enter: Salary, Loan_Amount");
            System.out.println("\ne.g. 50000,120000");

            while (true) {
                System.out.print("> ");

                String loanRequest = stdin.readLine();
                if (loanRequest == null || loanRequest.trim().length() <= 0) {
                    borrower.exit();
                }

                // Parse the deal description
                StringTokenizer st = new StringTokenizer(loanRequest, ",");
                double salary = Double.valueOf(st.nextToken().trim()).doubleValue();
                double loanAmt = Double.valueOf(st.nextToken().trim()).doubleValue();

                borrower.sendLoanRequest(salary, loanAmt);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
