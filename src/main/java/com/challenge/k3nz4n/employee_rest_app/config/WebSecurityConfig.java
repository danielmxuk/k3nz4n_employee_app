package com.challenge.k3nz4n.employee_rest_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.challenge.k3nz4n.employee_rest_app.model.service.WebSecurityUserDetailsService;
import com.challenge.k3nz4n.employee_rest_app.utility.AppUtils;


/**
 * <pre>
 * This class is used to configure the Web security access, the application users are created by the service {@link WebSecurityUserDetailsService}
 * 
 * Also, this class indicate the flow process from login, and which elements are open (public resources)
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Nov 7, 2019
 */

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AppUtils appUtils;
	
	@Autowired
	private WebSecurityUserDetailsService webSecurityUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(webSecurityUserDetailsService).passwordEncoder(passwordEncoder);
	}	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	  web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/h2-console/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http
	  	.csrf()
	  	.and()
	    .authorizeRequests()
	      .antMatchers("/","/login","/logout.do").permitAll()
	      .antMatchers("/**").authenticated()
	    .and()
	      .formLogin()
	        .loginProcessingUrl("/login.do")
	          .usernameParameter("username")
	          .passwordParameter("password")
	        .loginPage("/login")
	    .and()
	      .logout()
	      .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"));
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
