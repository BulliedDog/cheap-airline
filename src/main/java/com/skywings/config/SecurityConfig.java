package com.skywings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Lasciamo passare tutto a livello di Spring Security
                        // Il vero controllo lo farà il nostro AdminInterceptor
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        return http.build();
    }
}