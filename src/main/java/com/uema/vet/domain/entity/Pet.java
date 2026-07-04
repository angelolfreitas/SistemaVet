package com.uema.vet.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uema.vet.domain.entity.subclasses.Tutor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pets")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long idPet;

    private String nome;
    private String especie;
    @Column(name = "raça")
    private String raca;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "peso_atual")
    private BigDecimal pesoAtual;

    @ManyToMany(mappedBy = "pets")
    @JsonIgnore
    private List<Tutor> tutores;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Atendimento> atendimentos;
}
