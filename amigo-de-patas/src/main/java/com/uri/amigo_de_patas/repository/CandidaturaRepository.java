package com.uri.amigo_de_patas.repository;

import com.uri.amigo_de_patas.model.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidaturaRepository extends JpaRepository<Candidatura, UUID> {
    List<Candidatura> findByAnimalIdAndTipo(UUID animalId, String tipo);
}
