package com.chenshun.test.lambda;

import org.junit.Test;

import java.util.*;

/**
 * User: chenshun131 <p />
 * Time: 17/9/12 21:46  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TestLambda2 {

    // 原来的匿名内部类
    @Test
    public void test1() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    // Lambda 表达式
    @Test
    public void test2() {
        Comparator<Integer> comparator = (o1, o2) -> Integer.compare(o1, o2);
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

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
    public void test3() {
        List<Employee> emps = filterEmployees(employees);
        for (Employee employee : emps) {
            System.out.println(employee.toString());
        }
    }

    // 需求 : 获取当前公司中员工年龄大于 35 的员工信息
    private List<Employee> filterEmployees(List<Employee> employees) {
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= 35) {
                emps.add(employee);
            }
        }
        return emps;
    }

    @Test
    public void test4() {
        List<Employee> emps = filterEmployees2(employees);
        for (Employee employee : emps) {
            System.out.println(employee.toString());
        }
    }

    // 需求 : 获取当前公司中员工工资大于 4000 的员工信息
    private List<Employee> filterEmployees2(List<Employee> employees) {
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() >= 4000) {
                emps.add(employee);
            }
        }
        return emps;
    }

    // 优化方式一 : 策略设计模式
    public List<Employee> filterEmployees3(List<Employee> employees, MyPredicate<Employee> myPredicate) {
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employees) {
            if (myPredicate.test(employee)) {
                emps.add(employee);
            }
        }
        return emps;
    }

    @Test
    public void test5() {
        List<Employee> emps = filterEmployees3(employees, new FilterEmployeeByAge());
        for (Employee employee : emps) {
            System.out.println(employee.toString());
        }
    }

    @Test
    public void test6() {
        List<Employee> emps = filterEmployees3(employees, new FilterEmployeeBySalary());
        for (Employee employee : emps) {
            System.out.println(employee.toString());
        }
    }

    // 优化方式二 : 匿名内部类
    @Test
    public void test7() {
        List<Employee> emps = filterEmployees3(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() >= 35;
            }
        });
        for (Employee employee : emps) {
            System.out.println(employee.toString());
        }
    }

    @Test
    public void test8() {
        List<Employee> emps = filterEmployees3(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() >= 4000;
            }
        });
        for (Employee employee : emps) {
            System.out.println(employee.toString());
        }
    }

    // 优化方式三 : Lambda
    @Test
    public void test9() {
        List<Employee> emps = filterEmployees3(employees, (employee -> (employee.getAge() >= 35)));
        emps.forEach(System.out::println);
    }

    @Test
    public void test10() {
        List<Employee> emps = filterEmployees3(employees, (employee -> (employee.getSalary() >= 4000)));
        emps.forEach(System.out::println);
    }

    // 优化方式四 : Stream API
    @Test
    public void test11() {
        employees.stream()
                .filter((employee) -> employee.getAge() >= 35)
                .limit(5)
                .forEach(System.out::println);
    }

    @Test
    public void test12() {
        employees.stream()
                .filter((employee) -> employee.getSalary() >= 4000)
                .limit(5)
                .forEach(System.out::println);
    }

}
