package com.chenshun.test.lambda;

/**
 * User: chenshun131 <p />
 * Time: 17/9/12 22:21  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class FilterEmployeeBySalary implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary() >= 4000;
    }

}
