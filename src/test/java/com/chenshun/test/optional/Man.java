package com.chenshun.test.optional;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 22:19  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Man {

    private Godness god;

    public Man() {
    }

    public Man(Godness god) {
        this.god = god;
    }

    public Godness getGod() {
        return god;
    }

    public void setGod(Godness god) {
        this.god = god;
    }

    @Override
    public String toString() {
        return "Man [god=" + god + "]";
    }

}

