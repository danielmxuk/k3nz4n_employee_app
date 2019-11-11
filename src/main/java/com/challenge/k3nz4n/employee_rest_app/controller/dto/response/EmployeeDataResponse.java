package com.challenge.k3nz4n.employee_rest_app.controller.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * This class is a Data Transfer Object (DTO) which is used for the controller to return employee info to the endpoints as response.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 6, 2019
 */

@Getter
@Setter
public class EmployeeDataResponse implements EmployeeBaseResponse {

	private UUID id;
	
	private String firstName;
	
	private String middleInitial;
	
	private String lastName;
	
	private LocalDate dateOfBirth;
	
	private LocalDate dateOfEmployment;
}
