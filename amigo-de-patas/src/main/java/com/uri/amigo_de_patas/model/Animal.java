package com.uri.amigo_de_patas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "animais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String especie;
    private String porte;
    private String sexo;
    private String descricao;
    private String imagemUrl;
    private boolean vacinado;
    private boolean castrado;
    private boolean adotado;
    private boolean lar_temporario;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<Candidatura> candidaturas = new ArrayList<>();

}
