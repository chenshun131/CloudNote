package com.chenshun.test.def;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 23:11  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TestDefaultInterface {

    public static void main(String[] args) {
        SubClass sc = new SubClass();
        System.out.println(sc.getName());

        MyInterface.show();
    }

}
