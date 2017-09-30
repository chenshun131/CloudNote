package com.chenshun.test.def;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 22:51  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public interface MyFun<T> {

    T func(int a);

    default String getName() {
        return "哈哈哈";
    }

}
