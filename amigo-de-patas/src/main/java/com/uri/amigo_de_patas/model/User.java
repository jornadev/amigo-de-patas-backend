package com.uri.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    private UUID id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
