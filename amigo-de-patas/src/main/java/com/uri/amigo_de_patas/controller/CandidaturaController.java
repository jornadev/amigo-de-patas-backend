package com.uri.amigo_de_patas.controller;

import com.uri.amigo_de_patas.model.Candidatura;
import com.uri.amigo_de_patas.service.CandidaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @PostMapping("/candidatar")
    public Candidatura candidatar(
            @RequestParam UUID userId,
            @RequestParam UUID animalId,
            @RequestParam String tipo,
            @RequestParam String telefone) {
        return candidaturaService.candidatar(userId, animalId, tipo, telefone);
    }

    @PostMapping("/aprovar/{id}")
    public Candidatura aprovar(@PathVariable UUID id) {
        return candidaturaService.aprovarCandidatura(id);
    }

    @GetMapping("/por-animal/{animalId}")
    public List<Candidatura> listarPorAnimal(@PathVariable UUID animalId,
                                             @RequestParam String tipo) {
        return candidaturaService.listarPorAnimal(animalId, tipo);
    }
}
