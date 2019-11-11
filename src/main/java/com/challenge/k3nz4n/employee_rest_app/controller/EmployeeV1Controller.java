package com.challenge.k3nz4n.employee_rest_app.controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.k3nz4n.employee_rest_app.controller.dto.request.EmployeeCreateRequest;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.request.EmployeeUpdateRequest;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.response.EmployeeBaseResponse;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.response.EmployeeDataResponse;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.response.EmployeeMessageResponse;
import com.challenge.k3nz4n.employee_rest_app.model.service.EmployeeService;

import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * This Controller handles the REST API requests which are protected, mainly this is the entry point of the endspoints version 1 under /rest-api/v1/**
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 4, 2019
 */

@RestController
@RequestMapping(value = "/rest-api/v1")
public class EmployeeV1Controller {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(EmployeeV1Controller.class);
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping(value = "/get-test-string", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTest() {
        
		LOGGER.trace("   ---->  Test TEST profile scope");
		LOGGER.debug("   ---->  Test DEV  profile scope");
		LOGGER.info( "   ---->  Test PROD profile scope");
		
		return ResponseEntity.ok("{ \"key\": \"This is an simple test EndPoint :P\" }");
    }
	
	
	@GetMapping(value = "/employee/list-active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeDataResponse>> getAllActiveEmployeesList(@RequestParam(value = "_", required = false) String uniqueRef) {
		
		return ResponseEntity.ok( employeeService.getAllEmployeesList(true) );
	}
	
	
	@GetMapping(value = "/employee/list-inactive", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeDataResponse>> getAllInactiveEmployeesList(@RequestParam(value = "_", required = false) String uniqueRef) {
		
		return ResponseEntity.ok( employeeService.getAllEmployeesList(false) );
	}
	
	
	
	@GetMapping(value = "/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeBaseResponse> getEmployeeData(@PathVariable(value = "employeeId") UUID employeeId, @RequestParam(value = "_", required = false) String uniqueRef) {
		
		Optional<EmployeeDataResponse> optionalEmployeeDataResponse = Optional.ofNullable(employeeService.getEmployeeData(employeeId));
		
		if(optionalEmployeeDataResponse.isPresent()) {
			return new ResponseEntity<>( optionalEmployeeDataResponse.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>( new EmployeeMessageResponse("Could not find employee with the data provided"), HttpStatus.BAD_REQUEST);
		}
    }
	
	
	@PostMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeMessageResponse> createEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest, @RequestParam(value = "_", required = false) String uniqueRef) {
		
		Optional<String> optionalMessage = Optional.of(employeeService.createEmployee(employeeCreateRequest));
		
		if(optionalMessage.isPresent()) {
			return new ResponseEntity<>( new EmployeeMessageResponse("New employee Id: " + optionalMessage.get()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>( new EmployeeMessageResponse("Could not create employee with the data provided"), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeMessageResponse> updateEmployee(@RequestBody EmployeeUpdateRequest employeeUpdateRequest, @RequestParam(value = "_", required = false) String uniqueRef) {
		
		Optional<String> optionalMessage = Optional.ofNullable(employeeService.updateEmployee(employeeUpdateRequest));
		
		if(optionalMessage.isPresent()) {
			return new ResponseEntity<>( new EmployeeMessageResponse("Updated employee Id: " + optionalMessage.get()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>( new EmployeeMessageResponse("Could not update employee with the data provided"), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PatchMapping(value = "/employee/{employeeId}/{activeStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeMessageResponse> updateEmployeeActiveStatus(@PathVariable(value = "employeeId") UUID employeeId, @PathVariable(value = "activeStatus") boolean activeStatus, @RequestParam(value = "_", required = false) String uniqueRef) {
		
		boolean wasActiveStatusUpdated = employeeService.setEmployeeActiveStatus(employeeId, activeStatus);
		
		if(wasActiveStatusUpdated) {
			return new ResponseEntity<>( new EmployeeMessageResponse(MessageFormat.format("Employee Id: {0} was set user_active as: {1}", employeeId, activeStatus)), HttpStatus.OK);
		}else {
			return new ResponseEntity<>( new EmployeeMessageResponse("Could not update employee activeStatus with the data provided"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeMessageResponse> deleteEmployee(@PathVariable(value = "employeeId") UUID employeeId, @RequestParam(value = "_", required = false) String uniqueRef) {

		boolean wasEmployeeDeleted = employeeService.deleteEmployee(employeeId);
		
		if(wasEmployeeDeleted) {
			return new ResponseEntity<>( new EmployeeMessageResponse(MessageFormat.format("Employee Id: {0} deleted", employeeId)), HttpStatus.OK);
		}else {
			return new ResponseEntity<>( new EmployeeMessageResponse("Could not delete employee with the data provided"), HttpStatus.BAD_REQUEST);
		}
    }
}
