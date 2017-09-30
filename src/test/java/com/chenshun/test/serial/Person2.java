package com.chenshun.test.serial;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/9/30 08:08  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Person2 implements Serializable {

    private static final long serialVersionUID = 5871783399896345071L;

    private String name;

    private transient int age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return "Name:" + name + "; Age:" + age;
    }

}
