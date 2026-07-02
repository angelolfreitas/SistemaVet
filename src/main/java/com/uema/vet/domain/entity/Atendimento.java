package com.uema.vet.domain.entity;

import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.domain.enums.StatusAtendimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "atendimento")
@Data
@Builder
@NoArgsConstructor
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
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "id_veterinario")
    private Veterinario veterinario;


}
