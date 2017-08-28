package com.chenshun.component.listener;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.HashMap;

/**
 * User: mew <p />
 * Time: 17/8/25 10:35  <p />
 * Version: V1.0  <p />
 * Description: 自定义消息处理器 <p />
 */
public class TradeorderManager {

    public void createTradeOrder(TextMessage msg) {
        try {
            String xml = msg.getText();
            int priority = msg.getJMSPriority();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void createTradeOrder(HashMap map) {
    }

    public void createTradeOrder(String xml) {
        // 处理交易订单 xml 消息
    }

}
