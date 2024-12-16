package com.shopme.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
//	@Autowired private CustomerOAuth2UserService oauth2userService;
//	@Autowired private OAuth2LoginSuccessHandler oauth2LoginHandler;
//	@Autowired private DatabaseLoginSuccessHandler databaseLoginHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain configureHttp(HttpSecurity http) throws Exception {
		
//		.csrf(csrf -> {
//			csrf.disable();
//		})
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/account_details", "/update_account_details", 
							"/cart", "/address_book/**").authenticated()
				.requestMatchers("/images/**", "/js/**", "/webjars/**").permitAll()
				.anyRequest().permitAll()
			)
//			.csrf(AbstractHttpConfigurer::disable)
			
			.formLogin(form -> form
				.loginPage("/login")
				.usernameParameter("email")
				
				.permitAll() //.successHandler(databaseLoginHandler)
			)
			
			.logout(logout -> logout.permitAll()		
			)
			.rememberMe(rem -> rem
					.key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
					.tokenValiditySeconds(14 * 24 * 60 * 60)
			)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			;
//			.oauth2Login(oauth2 -> oauth2
//				.loginPage("/login")
//			.userInfoEndpoint(u -> u.userService(oauth2userService))
//			.successHandler(oauth2LoginHandler)
//		)	
			return http.build();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
}
