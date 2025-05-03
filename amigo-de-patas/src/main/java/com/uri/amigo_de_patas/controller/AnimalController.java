package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping("/create")
    public Animal cadastrarAnimal(@RequestBody Animal animal) {
        return animalService.cadastrar(animal);
    }

    @GetMapping("/list")
    public List<Animal> listarAnimais() {
        return animalService.listarTodos();
    }

    @PutMapping("/update/{id}")
    public Animal atualizarAnimal(@PathVariable UUID id, @RequestBody Animal animal) {
        return animalService.atualizar(id, animal);
    }

    @DeleteMapping("/delete/{id}")
    public void deletarAnimal(@PathVariable UUID id) {
        animalService.deletar(id);
    }
}