package com.chenshun.test.lambda.exer;

import com.chenshun.test.lambda.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: chenshun131 <p />
 * Time: 17/9/13 22:10  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TestLambda {

    List<Employee> employees = Arrays.asList(
            new Employee("A1", 18, 1111),
            new Employee("A2", 38, 2222),
            new Employee("A3", 50, 3333),
            new Employee("A4", 16, 4444),
            new Employee("A5", 20, 5555),
            new Employee("A6", 32, 6666),
            new Employee("A7", 40, 7777),
            new Employee("A8", 51, 8888),
            new Employee("A9", 33, 9999)
    );

    @Test
    public void test1() {
        Collections.sort(employees, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });

//        employees.forEach(System.out::println);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void test2() {
        String trimStr = strHandler("\t\t\t 我大尚硅谷威武   ", (str) -> str.trim());
        System.out.println(trimStr);

        String upper = strHandler("abcdef", (str) -> str.toUpperCase());
        System.out.println(upper);

        String newStr = strHandler("我大尚硅谷威武", (str) -> str.substring(2, 5));
        System.out.println(newStr);
    }

    // 需求：用于处理字符串
    public String strHandler(String str, MyFunction mf) {
        return mf.getValue(str);
    }

    @Test
    public void test3() {
        op(100L, 200L, (x, y) -> x + y);

        op(100L, 200L, (x, y) -> x * y);
    }

    // 需求：对于两个 Long 型数据进行处理
    public void op(Long l1, Long l2, MyFunction2<Long, Long> mf) {
        System.out.println(mf.getValue(l1, l2));
    }

}
