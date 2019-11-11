package com.challenge.k3nz4n.employee_rest_app.controller;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.k3nz4n.employee_rest_app.model.dto.WebSecurityUser;
import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;

import ch.qos.logback.classic.Logger;

/**
 * <pre>
 * This Controller handles the login and GUI requests, it indicates what need to be done once the user is logged, also provide the required info to the View (GUI page) 
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 7, 2019
 */

@RestController
public class LoginController {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	AppUtils appUtils;
	
	@RequestMapping("/login")
	public ModelAndView loginPage() {

		ModelAndView mod = new ModelAndView ("login_page");
		
		LOGGER.info("\n\nRequesting loging page: [{}] \n\n", mod.getViewName() );
		
		return mod;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logoutPage (HttpServletRequest request, HttpServletResponse response) {
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (auth != null){
	    new SecurityContextLogoutHandler().logout(request, response, auth);
	  }
	  return new ModelAndView ("redirect:/login?logout");
	}
	
	
	@RequestMapping("/")
	  public ModelAndView root(Map<String,Object> model, Authentication auth){
		
		if(Objects.isNull(auth)) {
			
			LOGGER.info("\n\n Invalid Authentication, redirecting to login page \n\n");
			
			return new ModelAndView ("redirect:/login");
		}
		else {
			WebSecurityUser webSecurityUser = (WebSecurityUser) auth.getPrincipal();
			
			LOGGER.info("\n\n User logged as: [{}] , access roles: [{}] \n\n", webSecurityUser.getUsername(), webSecurityUser.getAuthorities() );
			
			model.put("clientId", webSecurityUser.getUsername());
		    model.put("clientSecret", webSecurityUser.getClientSecret());
		    model.put("clientAuthorities", webSecurityUser.getAuthorities().toString());
		    model.put("appURI", appUtils.getAppURI() );
		    
		    return new ModelAndView ("gui_page", model);
		}
	 }
}
