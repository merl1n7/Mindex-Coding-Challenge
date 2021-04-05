package com.mindex.challenge.service.impl;

import java.time.LocalDate;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(String id){
        LOG.debug("Creating compensation with employee id [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setEffectiveDate(LocalDate.now());
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Getting compensation with employee id [{}]", id);
        Compensation compensation = compensationRepository.findByEmployeeId(id);
        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        return compensation;
    }
}
