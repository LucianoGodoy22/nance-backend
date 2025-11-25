package com.nance.backend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens (En producción debería estar en variables de entorno)
    // Spring Security requiere una clave segura de al menos 256 bits
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Método para crear el token (El "Pase")
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // A quién pertenece el token
                .claim("role", role) // Guardamos el rol (ADMIN o CLIENT) dentro del token
                .setIssuedAt(new Date()) // Cuándo se creó
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira en 10 horas
                .signWith(SECRET_KEY) // Firmamos digitalmente
                .compact();
    }
    
    // Aquí podrías agregar métodos para validar el token si los necesitas después
}