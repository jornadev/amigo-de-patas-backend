//package com.uri.amigo_de_patas.controller;
//
//import com.uri.amigo_de_patas.dto.LoginResponse;
//import com.uri.amigo_de_patas.dto.UserDTO;
//import com.uri.amigo_de_patas.model.User;
//import com.uri.amigo_de_patas.security.JwtUtil;
//import com.uri.amigo_de_patas.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
//        userService.registerUser(userDTO);
//        return ResponseEntity.status(201).body("Usuário registrado com sucesso!");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
//        try {
//            User user = userService.authenticateUser(userDTO.getEmail(), userDTO.getSenha());
//            String token = jwtUtil.generateToken(user.getEmail());
//
//            return ResponseEntity.ok(new LoginResponse(token));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }
//
//}
