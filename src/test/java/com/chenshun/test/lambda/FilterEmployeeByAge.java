package com.chenshun.test.lambda;

/**
 * User: chenshun131 <p />
 * Time: 17/9/12 22:13  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class FilterEmployeeByAge implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee employee) {
        return employee.getAge() >= 35;
    }

}
