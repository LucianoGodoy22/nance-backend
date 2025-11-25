package com.nance.backend.controller;

import com.nance.backend.config.JwtUtil;
import com.nance.backend.dto.LoginRequest;
import com.nance.backend.dto.LoginResponse;
import com.nance.backend.model.User;
import com.nance.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite que tu frontend se conecte sin problemas
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // Herramienta para comparar contraseñas encriptadas

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Buscamos al usuario por su correo
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // 2. Verificación simple: ¿Existe el usuario? Y ¿La contraseña coincide?
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // passwordEncoder.matches verifica la contraseña plana (request) contra la encriptada (BD)
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                
                // 3. ¡Coinciden! Generamos el token según pide la rúbrica 
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                
                // Devolvemos el token al frontend
                return ResponseEntity.ok(new LoginResponse(token));
            }
        }

        // Si no coinciden o no existe, devolvemos error 401 (No autorizado)
        return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
    }

    // Endpoint extra para crear un usuario de prueba (útil para que tengas con qué probar)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Encriptamos la contraseña antes de guardarla (Seguridad básica obligatoria)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }
}