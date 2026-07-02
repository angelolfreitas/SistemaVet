package com.uema.vet.domain.entity;

import com.uema.vet.domain.entity.subclasses.Tutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pets")
@Data
@Builder
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
    private List<Tutor> tutores;
}
