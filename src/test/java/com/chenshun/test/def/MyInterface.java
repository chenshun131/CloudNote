package com.chenshun.test.def;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 23:09  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public interface MyInterface {

    default String getName() {
        return "呵呵呵";
    }

    static void show() {
        System.out.println("接口中的静态方法");
    }

}
