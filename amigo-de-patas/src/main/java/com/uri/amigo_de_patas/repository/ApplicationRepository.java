package com.uri.amigo_de_patas.repository;

import com.uri.amigo_de_patas.model.Application;
import com.uri.amigo_de_patas.model.Animal;
import com.uri.amigo_de_patas.model.ApplicationType;
import com.uri.amigo_de_patas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByUser(User user);
    boolean existsByUserAndAnimalAndType(User user, Animal animal, ApplicationType type);
}
