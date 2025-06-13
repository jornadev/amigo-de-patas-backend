package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animais")
@Tag(name = "Animais", description = "Gerenciamento de animais disponíveis para adoção")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping("/create")
    @Operation(summary = "Cadastrar novo animal", description = "Adiciona um novo animal ao sistema (apenas para administradores).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public Animal cadastrarAnimal(@RequestBody Animal animal) {
        return animalService.cadastrar(animal);
    }

    @GetMapping("/list")
    @Operation(summary = "Listar todos os animais", description = "Retorna uma lista com todos os animais cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de animais retornada")
    })
    public List<Animal> listarAnimais() {
        return animalService.listarTodos();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar animal", description = "Atualiza os dados de um animal existente (apenas para administradores).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Animal não encontrado")
    })
    public Animal atualizarAnimal(@PathVariable UUID id, @RequestBody Animal animal) {
        return animalService.atualizar(id, animal);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deletar animal", description = "Remove um animal do sistema (apenas para administradores).")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Animal deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Animal não encontrado")
    })
    public void deletarAnimal(@PathVariable UUID id) {
        animalService.deletar(id);
    }
}
