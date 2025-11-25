// LoginRequest.java
package com.nance.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;    // El usuario envía esto
    private String password; // Y esto
}