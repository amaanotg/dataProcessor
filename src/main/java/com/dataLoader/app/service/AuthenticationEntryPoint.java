package com.dataLoader.app.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
			throws IOException {
		Gson gson = new Gson();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Map<String, String> msg = new HashMap<>();
		msg.put("message", authEx.getMessage());
		response.getWriter().println(gson.toJson(msg));
	}

	@Override
	public void afterPropertiesSet() {
		setRealmName("SpringSecurity");
		super.afterPropertiesSet();
	}

}