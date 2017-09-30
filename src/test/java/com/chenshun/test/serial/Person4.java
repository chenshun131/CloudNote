package com.chenshun.test.serial;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/9/30 08:37  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Person4 implements Externalizable {

    private static final long serialVersionUID = -943254404728505536L;

    private String name;

    private transient int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.name);
        out.writeObject(this.age);
        out.writeObject(new Date());
        System.out.println(this);
        System.out.println("序列化成功!");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // 注意序列化的先后顺序
        this.name = (String) in.readObject();
        this.age = (Integer) in.readObject() - 2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) in.readObject();
        System.out.println("反序列化后的日期为:" + sdf.format(date));
        System.out.println("反序列化成功!");
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Person4{name='" + name + "\', age=" + age + '}';
    }

}
