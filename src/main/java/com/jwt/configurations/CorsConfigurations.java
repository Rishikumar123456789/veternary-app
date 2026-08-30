package com.jwt.configurations;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
@EnableWebSecurity
public class CorsConfigurations {
@Bean
CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration corsConfiguration=new CorsConfiguration();

		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://52.66.239.38:8080"));

		corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;

	}


}
