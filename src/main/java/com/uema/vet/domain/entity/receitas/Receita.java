package com.uema.vet.domain.entity.receitas;

import com.uema.vet.domain.entity.Atendimento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "receita")
@Data
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receita")
    private Long idReceita;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @Column(name = "orientacoes_gerais", columnDefinition = "TEXT")
    private String orientacoesGerais;

    @ManyToOne
    @JoinColumn(name = "id_atendimento")
    private Atendimento atendimento;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemReceita> itens;
}
