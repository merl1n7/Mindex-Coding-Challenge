package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;

public class ReportingStructureServiceImpl implements ReportingStructureService{

    @Override
    public ReportingStructure create(Employee employee){
        ReportingStructure reportingStructure = new ReportingStructure();
        List <Employee> remainingEmployees = employee.getDirectReports();
        while (remainingEmployees.size() > 0){
            Employee currentEmployee =remainingEmployees.get(0);
            remainingEmployees.addAll(currentEmployee.getDirectReports());
            remainingEmployees.remove(0);
        }
        return reportingStructure;
    }
}
