package com.chenshun.test.optional;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 22:19  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Godness {

    private String name;

    public Godness() {
    }

    public Godness(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Godness [name=" + name + "]";
    }

}
