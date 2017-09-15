package com.chenshun.test.concurrency.collect.threadsafe;

/**
 * User: chenshun131 <p />
 * Time: 17/9/15 20:33  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Contact {

    private String name;

    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}
