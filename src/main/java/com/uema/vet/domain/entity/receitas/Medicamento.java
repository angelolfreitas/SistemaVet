package com.uema.vet.domain.entity.receitas;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medicamento")
@Data
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Long idMedicamento;

    @Column(name = "nome_comercial")
    private String nomeComercial;
    @Column(name = "principio_ativo")
    private String principioAtivo;
    private String concentracao;
    @Column(name = "quantidade_estoque")
    private Long quantidadeEstoque;
}
