package com.mindex.challenge.data;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import java.time.LocalDate;

public class Compensation {
    private Employee employee;
    private int salary;
    private LocalDate effectiveDate;


    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return this.salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate(){
        return this.effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate){
        this.effectiveDate = effectiveDate;
    }
}
