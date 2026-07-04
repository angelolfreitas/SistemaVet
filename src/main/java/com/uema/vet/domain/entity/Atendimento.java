package com.uema.vet.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uema.vet.domain.entity.receitas.Receita;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.domain.enums.StatusAtendimento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "atendimento")
@Builder
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendimento")
    private Long idAtendimento;

    private LocalDate data;
    private LocalTime hora;

    @Enumerated(EnumType.ORDINAL)
    private StatusAtendimento status;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;
    @Column(name = "peso_atendimento")
    private BigDecimal pesoAtendimento;

    @Column(columnDefinition = "TEXT")
    private String anamnese;

    @ManyToOne
    @JoinColumn(name = "id_pet")
    @JsonIgnoreProperties({"tutores", "atendimentos"})
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    @JsonIgnoreProperties({"pets", "atendimentos"})
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "id_veterinario")
    @JsonIgnoreProperties({"atendimentos"})
    private Veterinario veterinario;

    @OneToMany(mappedBy = "atendimento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"atendimento"}) // Para evitar recursão no JSON
    @ToString.Exclude // Evita loop infinito no log
    @EqualsAndHashCode.Exclude // Evita loop infinito no equals/hashcode
    private List<Receita> receitas;


}
