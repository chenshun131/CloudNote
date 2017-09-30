package com.chenshun.test.serial;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/9/30 07:54  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1636273937722329398L;

    private String name;

    private int age;

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
