package com.challenge.k3nz4n.employee_rest_app.model.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.k3nz4n.employee_rest_app.controller.dto.request.EmployeeBaseRequest;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.request.EmployeeCreateRequest;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.request.EmployeeUpdateRequest;
import com.challenge.k3nz4n.employee_rest_app.controller.dto.response.EmployeeDataResponse;
import com.challenge.k3nz4n.employee_rest_app.model.entity.EmployeeEntity;
import com.challenge.k3nz4n.employee_rest_app.model.repository.EmployeeRepository;
import java.util.stream.Collectors;

/**
 * <pre>
 * This service class is used as a bridge between the Controller layer and the model layer (repository).
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 5, 2019
 */

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	
	public boolean saveEmployee(EmployeeEntity employeeEntity) {
		
		employeeRepository.save(employeeEntity);
		
		return true;
	}
	
	
	public long getTotalNumberOfEmployeesInDB() {
		return employeeRepository.count();
	}
	
	
	public List<EmployeeDataResponse> getAllEmployeesList(boolean isUserActive){
		
		List<EmployeeEntity> employeeEntityList = employeeRepository.getAllEmployeeList(isUserActive);
		
		return employeeEntityList.stream().map(this::mapEmployeeEntityToEmployeeDataResponse).collect(Collectors.toList());	
	}
	
	
	public EmployeeDataResponse getEmployeeData(UUID employeeId){
		
		Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findActiveEmployeeById(employeeId);
		
		if(optionalEmployeeEntity.isPresent()) {
			
			return mapEmployeeEntityToEmployeeDataResponse(optionalEmployeeEntity.get());
		}else {
			
			return null;
		}
	}
	
	
	public String createEmployee(EmployeeCreateRequest employeeCreateRequest) {
		
		return employeeRepository.saveAndFlush(mapEmployeeBaseRequestToEmployeeEntity(employeeCreateRequest)).getId().toString();
	}
	
	
	public String updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
		
		Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeUpdateRequest.getId());
		
		if(optionalEmployeeEntity.isPresent()) {
			
			EmployeeEntity employeeEntity = mapEmployeeBaseRequestToEmployeeEntity(employeeUpdateRequest);
			employeeEntity.setUserActive(true);
			
			return employeeRepository.saveAndFlush(employeeEntity).getId().toString();
		}
		else {
			return null;
		}
	}
	
	
	public boolean setEmployeeActiveStatus(UUID employeeId, boolean activeStatus) {
		
		
		Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeId);
		
		if(optionalEmployeeEntity.isPresent()) {
			
			EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
			
			employeeEntity.setUserActive(activeStatus);
			
			return Objects.nonNull( employeeRepository.saveAndFlush(employeeEntity).getId() );
		}
		else {
			return false;
		}
	}
	
	
	public boolean deleteEmployee(UUID employeeId) {
		
		
		Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeId);
		
		if(optionalEmployeeEntity.isPresent()) {
			
			employeeRepository.deleteById(employeeId);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	private EmployeeDataResponse mapEmployeeEntityToEmployeeDataResponse(EmployeeEntity employeeEntity) {
		
		return modelMapper.map(employeeEntity, EmployeeDataResponse.class);
	}
	
	
	private EmployeeEntity mapEmployeeBaseRequestToEmployeeEntity(EmployeeBaseRequest employeeBaseRequest) {
		
		return modelMapper.map(employeeBaseRequest, EmployeeEntity.class);
	}
}
