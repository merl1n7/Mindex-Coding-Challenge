package com.mindex.challenge.data;

import com.mindex.challenge.data.Employee;

public class Compensation {
    private Employee employee;
    private int salary;
    //private idk data type, effectiveDate;


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
