package com.chenshun.test.def;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 23:08  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SubClass /*extends MyClass*/ implements MyFun, MyInterface {

    @Override
    public Object func(int a) {
        return new Object();
    }

    @Override
    public String getName() {
        return MyInterface.super.getName();
    }

}
