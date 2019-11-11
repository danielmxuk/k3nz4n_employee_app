package com.challenge.k3nz4n.employee_rest_app.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;

import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * This class is used to customize the generated JSON Web Token (JWT), it adds extra information once the token is created.
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 7, 2019
 */

@Component
public class CustomTokenEnhancer implements TokenEnhancer  {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomTokenEnhancer.class);
	
	@Autowired
	AppUtils appUtils;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		LOGGER.info("Adding more details to the Employee token");
		
		Map<String, Object> additionalInfo = new HashMap<>();
        
		additionalInfo.put("employee_app_uri", appUtils.getAppURI() );
		additionalInfo.put("auth_username", authentication.getName() );
		additionalInfo.put("authority_roles", authentication.getAuthorities().toString() );
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        
		return accessToken;
	}

	
}
