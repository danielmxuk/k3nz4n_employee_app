package com.challenge.k3nz4n.employee_rest_app.model.dto;

import java.util.Collection;
import org.springframework.security.core.userdetails.User;

import com.challenge.k3nz4n.employee_rest_app.config.WebSecurityConfig;

import ch.qos.logback.classic.Logger;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

/**
 * <pre>
 * This class creates a valid user object for the {@link com.challenge.k3nz4n.employee_rest_app.model.service.WebSecurityUserDetailsService}, which in the end are the users that can access the application.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 8, 2019
 */

@Getter
@Setter
public class WebSecurityUser extends User{

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(WebSecurityConfig.class);
	
	private static final long serialVersionUID = 1L;

	private String clientSecret;
	
	private static final boolean ENABLED = true;
	private static final boolean ACCOUNTNONEXPIRED = true;
	private static final boolean CREDENTIALSNONEXPIRED = true;
	private static final boolean ACCOUNTNONLOCKED = true;
	
	public WebSecurityUser(String userName, String password, String clientSecret, Collection<? extends GrantedAuthority> authorities) {
        
		super(userName, password, ENABLED, ACCOUNTNONEXPIRED, CREDENTIALSNONEXPIRED, ACCOUNTNONLOCKED, authorities);
		
		this.clientSecret = clientSecret;
		
		LOGGER.info("\n\n Added new Web user called: [{}], roles: [{}] \n\n", userName, authorities.toArray() );
    }
}
