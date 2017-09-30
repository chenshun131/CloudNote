package com.chenshun.test.serial;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * User: mew <p />
 * Time: 17/9/30 07:55  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SerialTest {

    /**
     * 序列化对象
     *
     * @param obj
     * @param filePath
     */
    private static void writeObject(Object obj, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.flush();
            fos.flush();
            os.close();
            fos.close();
            System.out.println("序列化成功!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 反序列化对象
     *
     * @param filePath
     * @return
     */
    private static Object readObject(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object temp = is.readObject();
            fis.close();
            is.close();
            if (temp != null) {
                System.out.println("反序列化成功!");
                return temp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static void serializeTest1() {
        Person person = new Person();
        person.setName("Zhang San");
        person.setAge(30);
        System.out.println(person);
        writeObject(person, "/Users/mew/Desktop/person.obj");
    }

    private static void deserializeTest1() {
        Person temp = (Person) readObject("/Users/mew/Desktop/person.obj");
        if (temp != null) {
            System.out.println(temp);
        }
    }

    private static void serializeTest2() {
        Person2 person = new Person2();
        person.setName("Zhang San");
        person.setAge(30);
        System.out.println(person);
        writeObject(person, "/Users/mew/Desktop/person2.obj");
    }

    private static void deserializeTest2() {
        Person2 temp = (Person2) readObject("/Users/mew/Desktop/person2.obj");
        if (temp != null) {
            System.out.println(temp);
        }
    }

    private static void serializeTest3() {
        Person3 person = new Person3();
        person.setName("Zhang San");
        person.setAge(30);
        try {
            FileOutputStream fos = new FileOutputStream("/Users/mew/Desktop/person3.obj");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(person);
            fos.close();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void deserializeTest3() {
        try {
            FileInputStream fis = new FileInputStream("/Users/mew/Desktop/person3.obj");
            ObjectInputStream is = new ObjectInputStream(fis);
            is.readObject();
            fis.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void serializeTest4() {
        Person4 person = new Person4();
        person.setName("Zhang San");
        person.setAge(30);
        try {
            FileOutputStream fos = new FileOutputStream("/Users/mew/Desktop/person4.obj");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(person);
            fos.close();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void deserializeTest4() {
        try {
            FileInputStream fis = new FileInputStream("/Users/mew/Desktop/person4.obj");
            ObjectInputStream is = new ObjectInputStream(fis);
            is.readObject();
            fis.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test1() {
//        serializeTest1();
        deserializeTest1();
    }

    @Test
    public void test2() {
        serializeTest2();
        deserializeTest2();
    }

    @Test
    public void test3() {
        serializeTest3();
        deserializeTest3();
    }

    @Test
    public void test4() {
        serializeTest4();
        deserializeTest4();
    }

}
