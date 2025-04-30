package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    public Animal cadastrarAnimal(@RequestBody Animal animal) {
        return animalService.cadastrar(animal);
    }

    @GetMapping
    public List<Animal> listarAnimais() {
        return animalService.listarTodos();
    }
}
