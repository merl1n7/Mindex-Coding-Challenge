package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure create(String id){
        LOG.debug("Retrieving direct reports for emplyee [{}]", id);
        Employee boss = employeeRepository.findByEmployeeId(id);

        ReportingStructure reportingStructure = new ReportingStructure();
        List <Employee> remainingEmployees = boss.getDirectReports();
        int numOfReports = remainingEmployees.size();

        while (remainingEmployees.size() > 0){
            Employee currentEmployee =remainingEmployees.get(0);
            List <Employee> directReports = currentEmployee.getDirectReports();
            if(directReports != null){
                numOfReports += directReports.size();
                remainingEmployees.addAll(directReports);
            }
            remainingEmployees.remove(0);
        }
        reportingStructure.setEmployee(boss);
        reportingStructure.setNumberOfReports(numOfReports);

        return reportingStructure;
    }
}
