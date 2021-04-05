package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String employeeUrl;
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationService compensationService;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        employeeUrl = "http://localhost:" + port + "/employee";

    }

    @Test
    public void testCreateRead(){
        // Create an employee
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee);
        // Set the date and salary for compensation
        LocalDate localDate = LocalDate.now();
        int salary = 80000;

        // Create the compensation
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(createdEmployee);
        testCompensation.setEffectiveDate(localDate);
        testCompensation.setSalary(salary);

        // Put the value into the database and make sure it is the same value coming out
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation);
        assertEquals(createdEmployee.getEmployeeId(), createdCompensation.getEmployee().getEmployeeId());
        assertEquals(localDate, createdCompensation.getEffectiveDate());
        assertEquals(salary, createdCompensation.getSalary());

        // Test read
        Compensation testReadCompensation = restTemplate.getForEntity(compensationIdUrl,Compensation.class, createdEmployee.getEmployeeId()).getBody();
        assertNotNull(createdCompensation);
        assertEquals(createdEmployee.getEmployeeId(), testReadCompensation.getEmployee().getEmployeeId());
        assertEquals(localDate, testReadCompensation.getEffectiveDate());
        assertEquals(salary, testReadCompensation.getSalary());

    }
}
