package com.chenshun.test.lambda;

/**
 * User: chenshun131 <p />
 * Time: 17/9/12 22:11  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@FunctionalInterface
public interface MyPredicate<T> {

    boolean test(T t);

}
