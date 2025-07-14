package com.uri.amigo_de_patas.dto;

import com.uri.amigo_de_patas.model.Application;
import com.uri.amigo_de_patas.model.ApplicationStatus;
import com.uri.amigo_de_patas.model.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ApplicationResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID animalId;
    private ApplicationType type;
    private ApplicationStatus status;
    private String message;
    private LocalDateTime applicationDate;

    public ApplicationResponseDTO(Application app) {
        this.id = app.getId();
        this.userId = app.getUser().getId();
        this.animalId = app.getAnimal().getId();
        this.type = app.getType();
        this.status = app.getStatus();
        this.message = app.getMessage();
        this.applicationDate = app.getApplicationDate();
    }
}
