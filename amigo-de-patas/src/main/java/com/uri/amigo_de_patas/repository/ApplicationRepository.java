package com.uri.amigo_de_patas.repository;

import com.uri.amigo_de_patas.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}

