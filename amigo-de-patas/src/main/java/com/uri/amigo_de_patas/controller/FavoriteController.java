package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
@Tag(name = "Favoritos", description = "Operações relacionadas aos favoritos dos usuários")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Operation(summary = "Favoritar um animal para o usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal favoritado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou animal não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @PostMapping("/{userId}/{animalId}")
    public void favoritar(@PathVariable UUID userId, @PathVariable UUID animalId) {
        favoriteService.favoritarAnimal(userId, animalId);
    }

    @Operation(summary = "Remover um animal dos favoritos do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal removido dos favoritos"),
            @ApiResponse(responseCode = "404", description = "Usuário ou animal não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @DeleteMapping("/{userId}/{animalId}")
    public void desfavoritar(@PathVariable UUID userId, @PathVariable UUID animalId) {
        favoriteService.desfavoritarAnimal(userId, animalId);
    }
}
