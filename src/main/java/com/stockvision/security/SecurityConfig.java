package com.stockvision.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
<<<<<<< HEAD
=======
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc

@Configuration
@EnableWebSecurity
public class SecurityConfig {

<<<<<<< HEAD
    @Bean
    public org.springframework.security.web.SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Updated syntax for Spring Security 6.1+
                .addFilterBefore(new FirebaseAuthFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Allow unauthenticated access to auth endpoints
=======
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
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
                        .anyRequest().authenticated());              // Protect all other routes

        return http.build();
    }
<<<<<<< HEAD
=======

>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
}
