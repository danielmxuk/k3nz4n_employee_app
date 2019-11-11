package com.challenge.k3nz4n.employee_rest_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 * This class is the main entry point of the application, since this is a Spring boot app, it is just required run this class to start the full app.
 * 
 * Besides that, this class loads a couple beans which used by other classes in the app.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 4, 2019
 */

@EnableSwagger2
@EnableEncryptableProperties
@SpringBootApplication
public class EmployeeRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRestAppApplication.class, args);
	}

	@Bean
	public AppUtils appUtils() {
		return new AppUtils();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
