package com.uri.amigo_de_patas.dto;

import com.uri.amigo_de_patas.model.ApplicationType;
import com.uri.amigo_de_patas.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ApplicationDTO {
    private UUID animalId;
    private ApplicationType type;
    private String message;
}


