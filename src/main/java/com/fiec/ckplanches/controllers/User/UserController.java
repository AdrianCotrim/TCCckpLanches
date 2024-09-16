package com.fiec.ckplanches.controllers;
import com.fiec.ckplanches.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(HttpServletRequest request) {
        // Recupera o token do cabeçalho Authorization
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " do token
        }

        if (token != null && !token.isEmpty()) {
            try {
                // Obtém a role do token
                String role = tokenService.getRoleFromToken(token);
                if (role != null) {
                    return ResponseEntity.ok(Map.of("role", role)); // Retorna a role do token
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } catch (Exception e) {
                // Trata exceções e retorna Unauthorized se a validação falhar
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        // Retorna Unauthorized se o token estiver ausente ou inválido
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/name")
    public ResponseEntity<?> getUserName(HttpServletRequest request) {
        // Recupera o token do cabeçalho Authorization
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " do token
        }

        if (token != null && !token.isEmpty()) {
            try {
                // Obtém a role do token
                String username = tokenService.validateToken(token);
                if (username != null) {
                    return ResponseEntity.ok(Map.of("username", username)); // Retorna a role do token
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } catch (Exception e) {
                // Trata exceções e retorna Unauthorized se a validação falhar
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        // Retorna Unauthorized se o token estiver ausente ou inválido
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}