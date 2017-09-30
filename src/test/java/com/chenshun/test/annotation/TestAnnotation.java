package com.chenshun.test.annotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * User: chenshun131 <p />
 * Time: 17/9/27 22:03  <p />
 * Version: V1.0  <p />
 * Description: 重复注解与类型注解 <p />
 */
public class TestAnnotation {

    // checker framework
    private /* @NonNull */ Object object = null;

    @MyAnnotation("AAAA")
    @MyAnnotation("BBBB")
    public void show(@MyAnnotation("CCCCC") String str) {
    }

    @Test
    public void test1() throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method m1 = clazz.getMethod("show");

        MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation myAnnotation : mas) {
            System.out.println(myAnnotation.value());
        }
    }

}
