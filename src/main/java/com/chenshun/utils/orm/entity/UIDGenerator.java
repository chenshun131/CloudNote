package com.chenshun.utils.orm.entity;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * User: mew <p />
 * Time: 17/6/16 17:36  <p />
 * Version: V1.0  <p />
 * Description: 继承Hibernate的UUIDGenerator, 将GUID长度缩短到15位. <br/>
 * UUID主要用于同时使用多个数据库,不能依赖单个数据库自增生成主键的情形. <br/>
 * 1. AppId - 使用2位的自定义值代替原UUID中的IP(8位) + 同一IP上的JVM(8位),节约14位 <br/>
 * 2. 时间戳 - 沿用原算法 <br/>
 * 3. Count - 相同JVM同一毫秒内的计数器,长度减为2 <br/>
 * <p>
 * 全部使用Hex编码, 2位AppId + 12位时间戳   + 2位微秒内计数器. <p />
 */
public class UIDGenerator extends UUIDHexGenerator {

    /**
     * 重载方法
     */
    @Override
    public Serializable generate(SessionImplementor session, Object object) {
        return new StringBuilder(16).append(formatShort(getAppId())).append(format(getHiTime())).
                append(format(getLoTime())).append(formatShort(getCount())).toString();
    }

    /**
     * 可重载子类实现从System Properties, Spring ApplicationContext等地方获得值.
     */
    protected short getAppId() {
        return 0;
    }

    /**
     * 格式化最大值为255的数值成长度为2的字符串.
     */
    protected String formatShort(short value) {
        String formatted = Integer.toHexString(value);
        if (formatted.length() < 2) {
            formatted = "0" + formatted;
        }
        return formatted;
    }

    /**
     * 获得普通带"-"连接的UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取不带"-"连接的UUID
     */
    public static String getSimpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
