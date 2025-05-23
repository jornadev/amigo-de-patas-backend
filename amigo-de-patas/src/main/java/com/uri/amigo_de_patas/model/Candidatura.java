package com.uri.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "candidaturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidatura {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    private String tipo; // "adocao" ou "lar_temporario"
    private boolean aprovado;

    private String telefone;
}
