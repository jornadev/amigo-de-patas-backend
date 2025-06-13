package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.dto.LoginResponse;
import com.uri.amigo_de_patas.dto.UserDTO;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.security.JwtUtil;
import com.uri.amigo_de_patas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
@Tag(name = "Autenticação", description = "Gerenciamento de login, registro e informações do usuário")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para registro")
    })
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(
                Map.of("message", "Usuário registrado com sucesso!")
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza autenticação e retorna o token JWT se válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
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
    @Operation(summary = "Verificar papel do usuário", description = "Verifica se o usuário autenticado é ADMIN ou USER.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Papel do usuário retornado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
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

    @GetMapping("/me")
    @Operation(summary = "Obter informações do usuário autenticado", description = "Retorna os dados do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        UserDTO dto = new UserDTO();
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setTelefone(user.getTelefone());
        dto.setEndereco(user.getEndereco());
        dto.setUserImg(user.getUserImg());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update")
    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza os dados do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> updateUser(@RequestBody UserDTO dto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        String email = authentication.getName();
        try {
            User updatedUser = userService.updateUserByEmail(email, dto);
            UserDTO responseDTO = new UserDTO();
            responseDTO.setNome(updatedUser.getNome());
            responseDTO.setEmail(updatedUser.getEmail());
            responseDTO.setTelefone(updatedUser.getTelefone());
            responseDTO.setEndereco(updatedUser.getEndereco());
            responseDTO.setUserImg(updatedUser.getUserImg());

            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
