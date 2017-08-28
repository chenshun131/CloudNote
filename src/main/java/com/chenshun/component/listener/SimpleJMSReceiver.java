package com.chenshun.component.listener;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/8/25 09:57  <p />
 * Version: V1.0  <p />
 * Description: 当前类不会引用在任何 JMS API接口，所有消息传送基础设施都由 MessageListenerAdapter 和
 * DefaultMessageListenerContainer处理  <p />
 */
public class SimpleJMSReceiver {

    /**
     * 接收转换 TextMessage
     *
     * @param text
     */
    public void handleMessage(String text) {
    }

    /**
     * 接收转换 MapMessage
     *
     * @param map
     */
    public void handleMessage(Map map) {
    }

    /**
     * 接收转换 BytesMessage
     *
     * @param bytes
     */
    public void handleMessage(byte[] bytes) {
    }

    /**
     * 接收转换 ObjectMessage
     *
     * @param obj
     */
    public void handleMessage(Object obj) {
    }

    /**
     * 接收转换 StreamMessage
     *
     * @param ser
     */
    public void handleMessage(Serializable ser) {
    }

    /******************* 禁用消息转换特性时，MessageListenerAdapter 会寻求下面的handleMessage方法 *******************/
    /**
     * 接收 JMS TextMessage
     *
     * @param message
     */
    public void handleMessage(TextMessage message) {
    }

    /**
     * 接收 JMS MapMessage
     *
     * @param message
     */
    public void handleMessage(MapMessage message) {
    }

    /**
     * 接收 JMS BytesMessage
     *
     * @param message
     */
    public void handleMessage(BytesMessage message) {
    }

    /**
     * 接收 JMS ObjectMessage
     *
     * @param message
     */
    public void handleMessage(ObjectMessage message) {
    }

    /**
     * 接收 JMS StreamMessage
     *
     * @param message
     */
    public void handleMessage(StreamMessage message) {
    }

}
