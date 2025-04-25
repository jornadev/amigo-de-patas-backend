package com.uri.amigo_de_patas.service;

import com.uri.amigo_de_patas.dto.UserDTO;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso!");
        }

        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());

        String encryptedPassword = passwordEncoder.encode(userDTO.getSenha());
        user.setSenha(encryptedPassword);

        return userRepository.save(user);
    }

    public User authenticateUser(String email, String senha) {
        User user = userRepository.findByEmail(email);

        if (user == null || !passwordEncoder.matches(senha, user.getSenha())) {  // Verifica se a senha fornecida é igual ao hash
            throw new IllegalArgumentException("Email ou senha incorretos!");
        }

        return user;
    }
}
