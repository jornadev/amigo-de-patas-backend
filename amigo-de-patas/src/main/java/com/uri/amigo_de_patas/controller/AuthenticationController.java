package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.dto.AuthenticationData;
import com.uri.amigo_de_patas.dto.UserDTO;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.security.TokenService;
import com.uri.amigo_de_patas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.uri.amigo_de_patas.security.DadosTokenJWT;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthenticationData dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(201).body("Usuário registrado com sucesso!");
    }
}
