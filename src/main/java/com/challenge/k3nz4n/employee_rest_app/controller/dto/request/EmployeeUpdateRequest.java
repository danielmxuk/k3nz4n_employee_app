package com.challenge.k3nz4n.employee_rest_app.controller.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * This class is a Data Transfer Object (DTO) which is used for the controller to received the data from the endpoints requests, specific to update employee entries in DB.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 10, 2019
 */

@Getter
@Setter
public class EmployeeUpdateRequest extends EmployeeCreateRequest implements EmployeeBaseRequest {

	private UUID id;
}
