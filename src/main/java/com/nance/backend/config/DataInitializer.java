package com.nance.backend.config;

import com.nance.backend.model.User;
import com.nance.backend.model.Role; // Asegúrate de tener tu Enum Role
import com.nance.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            // Crear ADMIN si no existe
            if (userRepository.findByEmail("admin@nance.cl").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@nance.cl");
                admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña encriptada
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Usuario ADMIN creado: admin@nance.cl / admin123");
            }

            // Crear CLIENTE si no existe
            if (userRepository.findByEmail("cliente@nance.cl").isEmpty()) {
                User client = new User();
                client.setEmail("cliente@nance.cl");
                client.setPassword(passwordEncoder.encode("cliente123"));
                client.setRole(Role.CLIENT);
                userRepository.save(client);
                System.out.println("Usuario CLIENTE creado: cliente@nance.cl / cliente123");
            }
        };
    }
}