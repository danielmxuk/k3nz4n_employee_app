package com.challenge.k3nz4n.employee_rest_app.config;

import static org.junit.Assert.assertNotNull;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.k3nz4n.employee_rest_app.enums.AuthorizationServerClientDetails;
import com.challenge.k3nz4n.employee_rest_app.enums.WebSecurityUserDetails;
import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * <pre>
 * This test class is used to test the connection and generation of the JSON Web Tokens (JWTs) with the Authorization Server {@link AuthorizationServerConfig}
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 10, 2019
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationServerConfigTest {
	
	@Autowired
	AppUtils appUtils;
	
	@Test
	public void testGetAuthorizedJWTToAccessTheRestAPIs() {
	    
		String accessToken = obtainAccessToken(AuthorizationServerClientDetails.CLIENT_USER_ID.getValue(), 
												AuthorizationServerClientDetails.CLIENT_USER_SECRET.getValue(), 
												AuthorizationServerClientDetails.CLIENT_BASE_GRANT_TYPES.getValue(), 
												WebSecurityUserDetails.ACCESS_USER_ID.getValue(), 
												WebSecurityUserDetails.ACCESS_USER_PASS.getValue());
		
	    System.out.println(MessageFormat.format("\n\nThe obtained JWT token is: [{0}]\n\n", accessToken));
		
	    
	    assertNotNull(accessToken);
	}
	
	private String obtainAccessToken(String clientId, String clientSecret, String grantType, String username, String password) {
	    
		Map<String, String> params = new HashMap<String, String>();
	    params.put("grant_type", grantType);
	    params.put("client_id", clientId);
	    params.put("username", username);
	    params.put("password", password);
	    
	    Response response = RestAssured.given().auth().preemptive()
	    							.basic(clientId, clientSecret).and().with().params(params).when()
	    							.post(appUtils.getAppURI() + "/oauth/token");
	    
	    return response.jsonPath().getString("access_token");
	}
}
