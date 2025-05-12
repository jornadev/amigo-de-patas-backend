package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.dto.LoginResponse;
import com.uri.amigo_de_patas.dto.UserDTO;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.security.JwtUtil;
import com.uri.amigo_de_patas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.HttpStatus;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(
                java.util.Collections.singletonMap("message", "Usuário registrado com sucesso!")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.authenticateUser(userDTO.getEmail(), userDTO.getSenha());

            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/check-role")
    public ResponseEntity<?> checkRole(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não autenticado");
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.equals("ROLE_ADMIN"))
                .findFirst()
                .orElse("ROLE_USER");

        return ResponseEntity.ok(Map.of("role", role));
    }
}
