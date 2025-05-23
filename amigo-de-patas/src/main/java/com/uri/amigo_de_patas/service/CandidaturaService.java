package com.uri.amigo_de_patas.service;

import com.uri.amigo_de_patas.model.*;
import com.uri.amigo_de_patas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public Candidatura candidatar(UUID userId, UUID animalId, String tipo, String telefone) {
        User user = userRepository.findById(userId).orElseThrow();
        Animal animal = animalRepository.findById(animalId).orElseThrow();

        Candidatura candidatura = new Candidatura();
        candidatura.setUsuario(user);
        candidatura.setAnimal(animal);
        candidatura.setTipo(tipo);
        candidatura.setTelefone(telefone);
        candidatura.setAprovado(false);

        return candidaturaRepository.save(candidatura);
    }

    public Candidatura aprovarCandidatura(UUID candidaturaId) {
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        candidatura.setAprovado(true);

        // Se for adoção, marcar o animal como adotado
        if ("adocao".equalsIgnoreCase(candidatura.getTipo())) {
            Animal animal = candidatura.getAnimal();
            animal.setAdotado(true);
            animalRepository.save(animal);
        }

        return candidaturaRepository.save(candidatura);
    }

    public List<Candidatura> listarPorAnimal(UUID animalId, String tipo) {
        return candidaturaRepository.findByAnimalIdAndTipo(animalId, tipo);
    }
}
