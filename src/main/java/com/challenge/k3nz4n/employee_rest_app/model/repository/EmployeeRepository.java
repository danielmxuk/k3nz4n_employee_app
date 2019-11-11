package com.challenge.k3nz4n.employee_rest_app.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.challenge.k3nz4n.employee_rest_app.model.entity.EmployeeEntity;

/**
 * <pre>
 * This class is a repository (model layer) which provides the methods to communicate with the employee entity ({@link EmployeeEntity}
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 5, 2019
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID>{

	@Query(value = "SELECT \r\n" + 
			"	id, first_name, middle_initial, last_name, date_of_birth, date_of_employment, is_user_active \r\n" + 
			"FROM \r\n" + 
			"	Employee\r\n" + 
			"WHERE\r\n" + 
			"	is_user_active = :isUserActive", 
			nativeQuery = true)
	public List<EmployeeEntity> getAllEmployeeList(@Param("isUserActive") boolean isUserActive);

	
	@Query(value = "SELECT \r\n" + 
			"	id, first_name, middle_initial, last_name, date_of_birth, date_of_employment, is_user_active \r\n" + 
			"FROM \r\n" + 
			"	Employee\r\n" + 
			"WHERE\r\n" + 
			"	is_user_active = TRUE "
			+ " AND id = :employeeId", 
			nativeQuery = true)
	public Optional<EmployeeEntity> findActiveEmployeeById(@Param("employeeId") UUID employeeId);
}
