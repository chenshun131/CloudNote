package com.chenshun.test.streamapi.exe;

/**
 * User: chenshun131 <p />
 * Time: 17/9/25 23:18  <p />
 * Version: V1.0  <p />
 * Description: 交易员类 <p />
 */
public class Trader {

    private String name;

    private String city;

    public Trader() {
    }

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader [name=" + name + ", city=" + city + "]";
    }

}
