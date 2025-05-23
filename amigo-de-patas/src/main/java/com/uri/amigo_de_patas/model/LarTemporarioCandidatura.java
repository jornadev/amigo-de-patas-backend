package com.uri.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "lar_temporario_candidaturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LarTemporarioCandidatura {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private Animal animal;

    private String telefone;
}
