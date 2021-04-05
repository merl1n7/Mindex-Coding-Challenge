package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportingStructureUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testReportingStructure() {
        // Create the test employee
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testEmployee.setDirectReports(new ArrayList<>());

        // Get the full employee ID from this post
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        // Create the boss for the above employee and set the direct reports
        Employee testBoss = new Employee();
        testBoss.setFirstName("John");
        testBoss.setLastName("Boss");
        testBoss.setDepartment("Engineering");
        testBoss.setPosition("Sr. Developer");
        List<Employee> directReports = new ArrayList<>();
        directReports.add(createdEmployee);

        testBoss.setDirectReports(directReports);

        Employee createdBoss = restTemplate.postForEntity(employeeUrl, testBoss, Employee.class).getBody();

        // Get the amount of direct reports
        assert createdBoss != null;
        ReportingStructure reportStruct = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createdBoss.getEmployeeId()).getBody();
        assertEquals(1, reportStruct.getNumberOfReports());

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}

