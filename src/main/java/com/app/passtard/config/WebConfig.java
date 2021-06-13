package com.app.passtard.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bns.web.service.UserService;
@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer 
{
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment environment;
	@Autowired
	  public WebConfig(UserService userService,BCryptPasswordEncoder bCryptPasswordEncoder,Environment environment) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
	}

//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
//    }
	
	  @Override
	protected void configure(HttpSecurity http) throws Exception {
		  http.csrf().disable();
		  http.authorizeRequests().antMatchers("/**").permitAll()
			.and().cors().and().addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
private AuthenticationFilter getAuthenticationFilter() throws Exception
	{
	AuthenticationFilter authenticationfilter = new AuthenticationFilter(userService,environment, bCryptPasswordEncoder, authenticationManager());
	authenticationfilter.setFilterProcessesUrl("/users/login");
	return authenticationfilter;
	}

//@Bean
//public CorsConfigurationSource corsConfigurationSource() {
//  CorsConfiguration configuration = new CorsConfiguration();
//  configuration.setAllowedOrigins(Arrays.asList("*"));
//  configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//  configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//  configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//  source.registerCorsConfiguration("/**", configuration);
//  return source;
//} 