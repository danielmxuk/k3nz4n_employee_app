package com.challenge.k3nz4n.employee_rest_app.controller.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * This class is a Data Transfer Object (DTO) which is used for the controller to received the data from the endpoints requests, specific to create employee entries in DB.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 9, 2019
 */

@Getter
@Setter
public class EmployeeCreateRequest implements EmployeeBaseRequest {
	
	private String firstName;
	
	private String middleInitial;
	
	private String lastName;
	
	private LocalDate dateOfBirth;
	
	private LocalDate dateOfEmployment;
}
