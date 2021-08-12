package com.akiraz.bookstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.akiraz.bookstore.security.JWTAuthorizationFilter;

	@EnableWebSecurity
	@Configuration
	public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/swagger-ui.html",
            "/user"
    };
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().httpBasic().disable()
			.addFilterAfter(new JWTAuthorizationFilter(), BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated();
		}
	}

