package com.uema.vet.domain.entity.receitas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uema.vet.domain.entity.Atendimento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "receita")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receita")
    @EqualsAndHashCode.Include
    private Long idReceita;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(name = "orientacoes_gerais", columnDefinition = "TEXT")
    private String orientacoesGerais;

    @ManyToOne
    @JoinColumn(name = "id_atendimento")
    @JsonIgnoreProperties({"receitas", "pet", "tutor", "veterinario"})
    @ToString.Exclude
    private Atendimento atendimento;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemReceita> itens = new java.util.ArrayList<>();
}
