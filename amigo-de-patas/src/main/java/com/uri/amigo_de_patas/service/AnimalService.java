package com.uri.amigo_de_patas.service;

import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public Animal cadastrar(Animal animal) {
        return animalRepository.save(animal);
    }

    public List<Animal> listarTodos() {
        return animalRepository.findAll();
    }

    public Animal atualizar(UUID id, Animal novoAnimal) {
        return animalRepository.findById(id).map(animalExistente -> {
            animalExistente.setNome(novoAnimal.getNome());
            animalExistente.setEspecie(novoAnimal.getEspecie());
            animalExistente.setPorte(novoAnimal.getPorte());
            animalExistente.setSexo(novoAnimal.getSexo());
            animalExistente.setDescricao(novoAnimal.getDescricao());
            animalExistente.setImagemUrl(novoAnimal.getImagemUrl());
            animalExistente.setVacinado(novoAnimal.isVacinado());
            animalExistente.setCastrado(novoAnimal.isCastrado());
            animalExistente.setLar_temporario(novoAnimal.isLar_temporario());
            animalExistente.setAdotado(novoAnimal.isAdotado());
            return animalRepository.save(animalExistente);
        }).orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public void deletar(UUID id) {
        if (!animalRepository.existsById(id)) {
            throw new RuntimeException("Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }
}
