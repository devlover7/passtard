package com.app.passtard.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.passtard.data.dto.Userdto;
import com.app.passtard.data.model.Login;
import com.app.passtard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private UserService userService;
	BCryptPasswordEncoder bCryptPasswordEncoder; 
	Environment environment;

	public AuthenticationFilter(UserService userService,Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder,AuthenticationManager authenticationManager ) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment= environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Login login;
		try {
			login = new ObjectMapper().readValue(request.getInputStream(), Login.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword(),new ArrayList<>()));
		}

		catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException 
	{
		Map<String,String> mapp = new HashMap<String,String>();
		String username=	((User)authResult.getPrincipal()).getUsername();
		Userdto userdetails = userService.getUserDetailByEmail(username);
		String userid = userdetails.getUserId();
		String token = Jwts.builder().setSubject(userdetails.getUserId()).
				setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(environment.getProperty("token.expiration_time")))).
				signWith(SignatureAlgorithm.HS512,environment.getProperty("passtard.token.secret")).compact();
		mapp.put("token", token);
		mapp.put("user", userid);
	//	 String json = new ObjectMapper().writeValueAsString(cr);
	//	response.getWriter().write(json);
	    SecurityContextHolder.getContext().setAuthentication(authResult);
	    chain.doFilter(request, response);
	}
	
}