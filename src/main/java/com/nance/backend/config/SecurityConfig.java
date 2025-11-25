package com.nance.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF: Solo lo deshabilitamos (ya no hace falta ignorar la consola H2)
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Rutas: Quitamos las referencias a H2
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Login/Registro
                .requestMatchers("/api/products").permitAll() // Catálogo público
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Documentación
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Solo admins
                .anyRequest().authenticated() // El resto protegido
            );
            
            // 3. Headers: Eliminamos la configuración de 'frameOptions' 
            // porque MariaDB no tiene interfaz web embebida.

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}