package com.berriesoft.social.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfiguration
{
	
	// setting up a SecurityFilterChain to override the default
		@Bean
		SecurityFilterChain filterChain(HttpSecurity http) throws Exception
		{
			// All URL must be protected
			http.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
			// A pop up login (basic auth) is shown for unauthorised requests
			//http.formLogin(withDefaults());
			http.httpBasic(withDefaults());
			
			// Disable cross site request forgery (CSRF) to enable POST and PUT
			http.csrf(csrf -> csrf.disable());
	        
			return http.build();
		}
		
		// create an InMemoryUserDetailsManager

		@Bean
		InMemoryUserDetailsManager configureUserDetailsManager()
		{
			UserDetails userDetails1 = configureUser("Prateek", "Pwd1", new String[] {"User", "Admin"});
			UserDetails userDetails2 = configureUser("Berriesoft", "Pwd2", new String[] {"User", "Admin"});

			return new InMemoryUserDetailsManager(userDetails1, userDetails2);
		}

		// create user
		private UserDetails configureUser(String username, String password, String[] roles)
		{
			Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
			
			UserDetails userDetails = User.builder().passwordEncoder(passwordEncoder).username(username).password(password)
					.roles(roles).build();
			return userDetails;
		}
		
		@Bean
		PasswordEncoder passwordEncoder()
		{
			return new BCryptPasswordEncoder();
		}
}
