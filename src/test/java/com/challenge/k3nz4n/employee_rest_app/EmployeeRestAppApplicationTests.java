package com.challenge.k3nz4n.employee_rest_app;

import static org.junit.Assert.assertNotNull;

import java.text.MessageFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;

/**
 * <pre>
 * This test class is used to test that main application is executed properly and test one the defined beans.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 10, 2019
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRestAppApplicationTests {

	@Autowired
	AppUtils appUtils;
	
	@Test
	public void contextLoads() {
		
		String appURI = appUtils.getAppURI();
		
		System.out.println(MessageFormat.format("\n\nThe Employee app is running in: [{0}]\n\n", appURI));
		
		assertNotNull(appURI);
	}

}
