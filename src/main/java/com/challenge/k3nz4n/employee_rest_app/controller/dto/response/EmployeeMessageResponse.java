package com.challenge.k3nz4n.employee_rest_app.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * This class is a Data Transfer Object (DTO) which is used for the controller to return an specific message to the endpoints as response.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 9, 2019
 */

@Getter
@Setter
@AllArgsConstructor
public class EmployeeMessageResponse implements EmployeeBaseResponse{

	private String message;
}
