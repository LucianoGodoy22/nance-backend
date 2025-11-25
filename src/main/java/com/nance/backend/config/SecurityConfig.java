package com.nance.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// Importante para la consola H2
import org.springframework.boot.autoconfigure.security.servlet.PathRequest; 
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                // IMPORTANTE: Ignorar CSRF en la consola de H2 y en rutas públicas
                .ignoringRequestMatchers(PathRequest.toH2Console()) 
                .disable() 
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/products").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // --- AQUÍ ESTABA EL FALTANTE ---
                // Permitimos entrar a la consola H2
                .requestMatchers(PathRequest.toH2Console()).permitAll() 
                // Alternativa manual si PathRequest falla: .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            // --- ESTO TAMBIÉN ES OBLIGATORIO PARA H2 ---
            // Permite que la consola se muestre dentro de un frame (marco)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}