package com.stockvision.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseAuthFilter firebaseAuthFilter;

    // Inject FirebaseAuthFilter via constructor
    public SecurityConfig(FirebaseAuthFilter firebaseAuthFilter) {
        this.firebaseAuthFilter = firebaseAuthFilter;
    }

    @Bean
    public org.springframework.security.web.SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)  // Updated syntax for Spring Security 6.1+
                .addFilterBefore(firebaseAuthFilter, BasicAuthenticationFilter.class)  // Injected Bean
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/stockvision/api/stripe/webhook").permitAll()
                        .requestMatchers("**").permitAll()
                        .anyRequest().authenticated());              // Protect all other routes

        return http.build();
    }

}
