package com.uri.amigo_de_patas.repository;

import com.uri.amigo_de_patas.model.AdocaoCandidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdocaoCandidaturaRepository extends JpaRepository<AdocaoCandidatura, UUID> {
    List<AdocaoCandidatura> findByAnimalId(UUID animalId);
    List<AdocaoCandidatura> findByUsuarioId(UUID userId);
}
