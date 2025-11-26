package com.nance.backend.controller;

import com.nance.backend.config.JwtUtil;
import com.nance.backend.dto.LoginRequest;
import com.nance.backend.model.User;
import com.nance.backend.model.Role; 
import com.nance.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Verificar contraseña
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                
                response.put("name", user.getEmail()); 
                
                response.put("email", user.getEmail());
                response.put("role", user.getRole().name());
                
                response.put("id", user.getId());

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        if (user.getRole() == null) {
            user.setRole(Role.CLIENT); 
        }

        return ResponseEntity.ok(userRepository.save(user));
    }
}