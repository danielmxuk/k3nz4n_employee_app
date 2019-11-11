package com.challenge.k3nz4n.employee_rest_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * This enum is used to defined the valid users (credentials) that will be used by the Web Security server {@link com.challenge.k3nz4n.employee_rest_app.model.service.WebSecurityUserDetailsService}
 * 
 * These are the user that can access and use the application.
 * 
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 9, 2019
 */

@Getter
@AllArgsConstructor
public enum WebSecurityUserDetails {
	
	ACCESS_ADMIN_ID("kenzanAdmin"),
	ACCESS_ADMIN_PASS("adminPass"),
	
	ACCESS_USER_ID("kenzanUser"),
	ACCESS_USER_PASS("userPass");
	
	private String value;
}
