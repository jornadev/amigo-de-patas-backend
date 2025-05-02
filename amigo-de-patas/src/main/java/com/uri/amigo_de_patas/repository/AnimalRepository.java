package com.uri.amigo_de_patas.repository;

import com.uri.amigo_de_patas.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
}
