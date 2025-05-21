package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/{userId}/{animalId}")
    public void favoritar(@PathVariable UUID userId, @PathVariable UUID animalId) {
        favoriteService.favoritarAnimal(userId, animalId);
    }

    @DeleteMapping("/{userId}/{animalId}")
    public void desfavoritar(@PathVariable UUID userId, @PathVariable UUID animalId) {
        favoriteService.desfavoritarAnimal(userId, animalId);
    }

    @GetMapping("/{userId}")
    public Set<Animal> listarFavoritos(@PathVariable UUID userId) {
        return favoriteService.listarFavoritos(userId);
    }
}
