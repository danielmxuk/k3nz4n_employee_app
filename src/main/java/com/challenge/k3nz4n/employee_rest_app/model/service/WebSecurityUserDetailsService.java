package com.challenge.k3nz4n.employee_rest_app.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.k3nz4n.employee_rest_app.config.WebSecurityConfig;
import com.challenge.k3nz4n.employee_rest_app.enums.AuthorizationServerClientDetails;
import com.challenge.k3nz4n.employee_rest_app.enums.WebSecurityUserDetails;
import com.challenge.k3nz4n.employee_rest_app.model.dto.WebSecurityUser;

import ch.qos.logback.classic.Logger;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * <pre>
 * This service class is used to generate the application users for the {@link WebSecurityConfig} class.
 * 
 * For demo purpose it generates two users on the fly.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 8, 2019
 */

@Service
public class WebSecurityUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public WebSecurityUser loadUserByUsername(String userName) {

		WebSecurityUser webSecurityUser = findUserbyUsername(userName);
		
		if( Objects.nonNull(webSecurityUser) ) {
			
			return webSecurityUser;
			
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}

	/**
	 * This could be improved later by using a Datasource in place, this is just to load basic users on the fly for demo purpose.
	 * 
	 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
	 * @since Nov 8, 2019
	 * @param userName
	 * @return WebSecurityUser
	 */
	private WebSecurityUser findUserbyUsername(String userName) {
		
		if( userName.contentEquals(WebSecurityUserDetails.ACCESS_ADMIN_ID.getValue()) ) {

			return new WebSecurityUser(userName, 
										passwordEncoder.encode(WebSecurityUserDetails.ACCESS_ADMIN_PASS.getValue()), 
										AuthorizationServerClientDetails.CLIENT_ADMIN_SECRET.getValue(),
										getGrantedAuthorityList(AuthorizationServerClientDetails.CLIENT_ADMIN_AUTHORITIES.getValue().split(",")) );
		}
		else if(userName.contentEquals(WebSecurityUserDetails.ACCESS_USER_ID.getValue())) {
			
			return new WebSecurityUser(userName, 
										passwordEncoder.encode(WebSecurityUserDetails.ACCESS_USER_PASS.getValue()), 
										AuthorizationServerClientDetails.CLIENT_USER_SECRET.getValue(),
										getGrantedAuthorityList(AuthorizationServerClientDetails.CLIENT_USER_AUTHORITIES.getValue().split(",")) );

		}
		else {
			LOGGER.info("\n\nInvalid user tried to login: [{}] \n\n", userName);
			
			return null;
		}
	}
	
	private List<GrantedAuthority> getGrantedAuthorityList(String... authArray){
		
		Set<GrantedAuthority> setAuths = new HashSet<>();
		
		Arrays.stream(authArray).map( a -> new SimpleGrantedAuthority(a.replace("ROLE_",""))).forEach(setAuths::add);
		
		return new ArrayList<>(setAuths);
	}
}
