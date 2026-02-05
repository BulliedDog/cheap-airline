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
                // 1. Disattiviamo il CSRF per ora (altrimenti blocca i tuoi POST di login/register)
                .csrf(csrf -> csrf.disable())

                // 2. Autorizziamo l'accesso a tutte le rotte senza login obbligatorio
                // (Visto che gestisci tu la sessione manualmente con GestoreSessione)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 3. Disattiviamo il form di login predefinito di Spring
                .formLogin(form -> form.disable())

                // 4. Disattiviamo il logout predefinito
                .logout(logout -> logout.disable());

        return http.build();
    }
}