package com.uri.amigo_de_patas.service;

import com.uri.amigo_de_patas.dto.UserDTO;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso!");
        }

        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setSenha(userDTO.getSenha());

        return userRepository.save(user);
    }

    public User authenticateUser(String email, String senha) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Email ou senha incorretos!");
        }

        return user;
    }
}
