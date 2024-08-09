package com.skillstorm.misc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService()
//	{
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("itsme")
//				.password("password")
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		
		http.httpBasic(Customizer.withDefaults());
		http.authorizeHttpRequests((reqs) -> 
		{
			

			reqs.requestMatchers(HttpMethod.POST, "/**").authenticated();
			reqs.requestMatchers(HttpMethod.GET, "/**").authenticated();
			reqs.requestMatchers(HttpMethod.PUT, "/**").authenticated(); 
			reqs.requestMatchers(HttpMethod.DELETE, "/**").authenticated();

		
		}).cors(Customizer.withDefaults())
		.csrf(csrf -> csrf.disable());
		//http.cors(cors -> cors.disable());
		//http.cors(cors -> cors.disable());
		return http.build();
	}
	
}
