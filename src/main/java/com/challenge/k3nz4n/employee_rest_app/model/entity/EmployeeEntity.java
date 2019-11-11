package com.challenge.k3nz4n.employee_rest_app.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * This class represent an Entity model, which is used to access the employee entries from the DB.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 4, 2019
 */

@Entity
@Table(name = "Employee")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeEntity {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GenericGenerator(   name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@ColumnDefault("RANDOM_UUID()")
	@Type(type = "uuid-char")
	private UUID id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "middle_initial")
	private String middleInitial;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "date_of_employment", nullable = false)
	private LocalDate dateOfEmployment;
	
	@Column(name = "is_user_active", nullable = false)
	private boolean isUserActive;
	
}
