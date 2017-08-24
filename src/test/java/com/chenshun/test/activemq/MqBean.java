package com.chenshun.test.activemq;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/8/22 18:08  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MqBean implements Serializable {

    private static final long serialVersionUID = -7581115306298030641L;

    private Integer age;

    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
