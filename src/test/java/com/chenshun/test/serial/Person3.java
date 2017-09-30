package com.chenshun.test.serial;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/9/30 08:18  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Person3 implements Serializable {

    private static final long serialVersionUID = -6943380636216410024L;

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

    private void writeObject(ObjectOutputStream os) {
        try {
            os.defaultWriteObject();
            os.writeObject(this.age);
            System.out.println(this);
            System.out.println("序列化成功!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream is) {
        try {
            is.defaultReadObject();
            this.setAge((Integer) is.readObject() - 1);
            System.out.println("反序列化成功!");
            System.out.println(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
