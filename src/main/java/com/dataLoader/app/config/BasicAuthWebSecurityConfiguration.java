package com.dataLoader.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.dataLoader.app.service.AuthenticationEntryPoint;

@Configuration
public class BasicAuthWebSecurityConfiguration {
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Value("${api.username}")
	private String username;
	
	@Value("${api.password}")
	private String password;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);

		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withUsername(username).password("{noop}"+password).roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}
}