package com.uema.vet.domain.entity;

import com.uema.vet.domain.entity.subclasses.Tutor;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pets")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long idPet;

    private String nome;
    private String especie;
    private String raca;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "peso_atual")
    private BigDecimal pesoAtual;

    @ManyToMany(mappedBy = "pets")
    private List<Tutor> tutores;
}
