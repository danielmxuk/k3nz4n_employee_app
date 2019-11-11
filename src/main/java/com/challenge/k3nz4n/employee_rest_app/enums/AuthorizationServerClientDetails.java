package com.challenge.k3nz4n.employee_rest_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * This enum is used to defined the clients (properties and credentials) that will be used by the authorization server {@link com.challenge.k3nz4n.employee_rest_app.config.AuthorizationServerConfig}
 * 
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 9, 2019
 */

@Getter
@AllArgsConstructor
public enum AuthorizationServerClientDetails {

	CLIENT_BASE_GRANT_TYPES("client_credentials"),
	CLIENT_BASE_RESOURCE_IDS("resource_employee_app"),
	CLIENT_BASE_SCOPES("read"),
	
	CLIENT_ADMIN_ID("kenzanAdmin"),
	CLIENT_ADMIN_SECRET("adminSecret"),
	CLIENT_ADMIN_AUTHORITIES("ROLE_USER,ROLE_ADMIN"),
	
	CLIENT_USER_ID("kenzanUser"),
	CLIENT_USER_SECRET("userSecret"),
	CLIENT_USER_AUTHORITIES("ROLE_USER");
	
	private String value;
}
