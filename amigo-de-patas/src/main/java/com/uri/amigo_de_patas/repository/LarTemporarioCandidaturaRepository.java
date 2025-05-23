package com.uri.amigo_de_patas.repository;

import com.uri.amigo_de_patas.model.LarTemporarioCandidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LarTemporarioCandidaturaRepository extends JpaRepository<LarTemporarioCandidatura, UUID> {
    List<LarTemporarioCandidatura> findByAnimalId(UUID animalId);
    List<LarTemporarioCandidatura> findByUsuarioId(UUID userId);
}
