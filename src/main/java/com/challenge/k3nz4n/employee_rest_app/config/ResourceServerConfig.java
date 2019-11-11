package com.challenge.k3nz4n.employee_rest_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * <pre>
 * This class configures the resource server, which handle who can access the REST API endpoints.
 * 
 * The rules are as follow:
 *      - All the endpoints under /rest-api/** are secured, and only can be reached by a JWT
 *      - There are three rules that restrict the access to specific endpoints just for the user who as ADMIN role
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 7, 2019
 */

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String ADMIN_ACCESS_RULES = "#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception{
		resources.resourceId("resource_employee_app");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception{

		http
		.requestMatchers()
		.antMatchers("/rest-api/**")
		.and()
	      .authorizeRequests()
	        .antMatchers(HttpMethod.GET, "/rest-api/v1/employee/list-inactive").access(ADMIN_ACCESS_RULES)
	        .antMatchers(HttpMethod.PATCH, "/**").access(ADMIN_ACCESS_RULES)
	        .antMatchers(HttpMethod.DELETE, "/**").access(ADMIN_ACCESS_RULES)
	        .anyRequest().authenticated()
	    .and()
	      .exceptionHandling()
	        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
