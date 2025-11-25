// LoginResponse.java
package com.nance.backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token; // Nosotros devolvemos esto

    public LoginResponse(String token) {
        this.token = token;
    }
}