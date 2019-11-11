package com.challenge.k3nz4n.employee_rest_app.config;

import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.k3nz4n.employee_rest_app.model.service.EmployeeService;

/**
 * <pre>
 * This test class is used to test that the employee dummy data was generated properly in the H2 DB from the {@link StartupLoadDataConfig}
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 10, 2019
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartupLoadDataConfigTest {

	@Autowired
	EmployeeService employeeService;
	
	@Test
	public void testPreloadedEmployeesInDB() {
		long totalOfLoadedEmployees = employeeService.getTotalNumberOfEmployeesInDB();
		
		System.out.println(MessageFormat.format("\n\nThe Number of Employees loaded is: [{0}]\n\n", totalOfLoadedEmployees));
		
		assertTrue(50 <= totalOfLoadedEmployees && totalOfLoadedEmployees <= 300);
	}
}
