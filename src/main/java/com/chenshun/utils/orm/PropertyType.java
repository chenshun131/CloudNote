package com.chenshun.utils.orm;

import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/6/16 17:19  <p />
 * Version: V1.0  <p />
 * Description: 属性数据类型 <p />
 */
public enum PropertyType {

    S(String.class), I(Integer.class), L(Long.class), DB(Double.class), D(Date.class), B(Boolean.class), F(Float.class);

    private Class<?> clazz;

    PropertyType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getValue() {
        return this.clazz;
    }

}
