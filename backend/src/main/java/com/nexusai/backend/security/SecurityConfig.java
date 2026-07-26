package com.nexusai.backend.security;

import com.nexusai.backend.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> {})
        .headers(headers
                -> headers.frameOptions(frame -> frame.disable())
        )
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                // Public APIs
                .requestMatchers("/api/auth/**").permitAll()
                // H2 Console
                .requestMatchers("/h2-console/**").permitAll()
                // Swagger
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                // Employee APIs
                .requestMatchers(HttpMethod.GET, "/api/employees/**")
                .hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/employees/**")
.hasAnyRole("ADMIN", "EMPLOYEE")

.requestMatchers(HttpMethod.PUT, "/api/employees/**")
.hasAnyRole("ADMIN", "EMPLOYEE")

.requestMatchers(HttpMethod.DELETE, "/api/employees/**")
.hasAnyRole("ADMIN", "EMPLOYEE")
                // All other APIs
                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, ex)
                        -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .accessDeniedHandler((request, response, ex)
                        -> response.sendError(HttpServletResponse.SC_FORBIDDEN))
                );

        return http.build();
    }
}
