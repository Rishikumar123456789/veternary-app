package com.jwt.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfigurations {
@Bean
SecurityFilterChain configureSecurityFilterChain(HttpSecurity httpSecurity,JwtAuthenticationFilter jwtAuthenticationFilter) {
	httpSecurity
			.cors(Customizer.withDefaults())
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(http->http.requestMatchers("/getRegister","/login").permitAll()
					.anyRequest()
					.authenticated())
			        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			        .addFilterBefore(
	                        jwtAuthenticationFilter,
	                        UsernamePasswordAuthenticationFilter.class
	                )
			        .formLogin(formLogin->formLogin.disable())
			        .httpBasic(httpBasic->httpBasic.disable());
	return httpSecurity.build();
			        
			
}}
