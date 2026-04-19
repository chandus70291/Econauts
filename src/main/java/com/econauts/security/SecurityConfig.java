package com.econauts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.econauts.Service.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    try
    {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // PUBLIC
                .requestMatchers("/auth/**").permitAll()

                // PRODUCTS (PUBLIC VIEW)
                .requestMatchers("/api/products/**").permitAll()

                // USER APIs
                .requestMatchers("/api/cart/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/orders/**").hasAnyRole("USER", "ADMIN")

                // ADMIN ONLY
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/orders/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/upload/**").authenticated()

                // ANY OTHER
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
	    }
	    catch(Exception ex)
	    {
	    	throw new Exception("Authentication issue"+ ex.getMessage() + ex);
	    }
    }
}