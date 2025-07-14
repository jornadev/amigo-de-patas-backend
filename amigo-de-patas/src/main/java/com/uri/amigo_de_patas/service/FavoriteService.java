package com.uri.amigo_de_patas.service;

import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.model.User;
import com.uri.amigo_de_patas.repository.AnimalRepository;
import com.uri.amigo_de_patas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class FavoriteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public void favoritarAnimal(UUID userId, UUID animalId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado"));

        user.getFavoritos().add(animal);
        userRepository.save(user);
    }

    public void desfavoritarAnimal(UUID userId, UUID animalId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado"));

        user.getFavoritos().remove(animal);
        userRepository.save(user);
    }

    public Set<Animal> listarFavoritos(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return user.getFavoritos();
    }
}
