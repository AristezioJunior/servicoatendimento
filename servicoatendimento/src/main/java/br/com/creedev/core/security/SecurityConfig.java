package br.com.creedev.core.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
		
	public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter,
		JwtAuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}


	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    .csrf(csrf -> csrf.disable())
	    .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
	    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    .authorizeHttpRequests(auth -> auth
	    	    .requestMatchers("/api/auth/login").permitAll()
	    	    .requestMatchers("/api/auth/register").authenticated()
	    	    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
	    	    .anyRequest().authenticated()
	    	)
	    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}