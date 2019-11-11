package com.challenge.k3nz4n.employee_rest_app.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.challenge.k3nz4n.employee_rest_app.enums.AuthorizationServerClientDetails;
import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;

import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * The purpose of this class is create the valid users (Called clients) that could access the endpoints from the resource server.
 * 
 * Besides that, this class provide valid JSON Web Tokens (JWTs), which are consumed by the resource server {@link ResourceServerConfig}
 * 
 * For this test there were created (on the fly in memory) two clients:
 *       - kenzanAdmin
 *       - kenzanUser
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 7, 2019
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(AuthorizationServerConfig.class);
	
	private static final String  PRIVATE_KEY          = "private key";
	private static final boolean USE_JWT_TOKEN_FORMAT = true;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	AppUtils appUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomTokenEnhancer customTokenEnhancer;
	
	@Autowired
	Environment environment;
	
	@Autowired
	DataSource dataSource;
	
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		
		LOGGER.info("\n\n  Accesing the jwtAccessTokenConverter \n\n");
		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		
		converter.setSigningKey(PRIVATE_KEY);
		
		return converter;
	}
	
	@Bean
	public JwtTokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients
		.inMemory()
		.withClient(AuthorizationServerClientDetails.CLIENT_ADMIN_ID.getValue())
		.secret(passwordEncoder.encode(AuthorizationServerClientDetails.CLIENT_ADMIN_SECRET.getValue()))
		.authorities(AuthorizationServerClientDetails.CLIENT_ADMIN_AUTHORITIES.getValue().split(","))
		.authorizedGrantTypes(AuthorizationServerClientDetails.CLIENT_BASE_GRANT_TYPES.getValue().split(","))
		.redirectUris(appUtils.getAppURI())
		.resourceIds(AuthorizationServerClientDetails.CLIENT_BASE_RESOURCE_IDS.getValue().split(","))
		.scopes(AuthorizationServerClientDetails.CLIENT_BASE_SCOPES.getValue().split(","))
		.and()
		.withClient(AuthorizationServerClientDetails.CLIENT_USER_ID.getValue())
		.secret(passwordEncoder.encode(AuthorizationServerClientDetails.CLIENT_USER_SECRET.getValue()))
		.authorities(AuthorizationServerClientDetails.CLIENT_USER_AUTHORITIES.getValue().split(","))
		.authorizedGrantTypes(AuthorizationServerClientDetails.CLIENT_BASE_GRANT_TYPES.getValue().split(","))
		.redirectUris(appUtils.getAppURI())
		.resourceIds(AuthorizationServerClientDetails.CLIENT_BASE_RESOURCE_IDS.getValue().split(","))
		.scopes(AuthorizationServerClientDetails.CLIENT_BASE_SCOPES.getValue().split(","))
		;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		if(USE_JWT_TOKEN_FORMAT) {
			
			LOGGER.info("\n\n  Using JSON Web Token format \n\n");
			
			TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
			
			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, jwtAccessTokenConverter() ));
			endpoints.tokenStore(jwtTokenStore())
				.authenticationManager(authenticationManager)
				.tokenEnhancer(tokenEnhancerChain)
				.accessTokenConverter(jwtAccessTokenConverter());
				
		}else {
			endpoints.tokenStore(new InMemoryTokenStore()).authenticationManager(authenticationManager);
		}
	}
}
