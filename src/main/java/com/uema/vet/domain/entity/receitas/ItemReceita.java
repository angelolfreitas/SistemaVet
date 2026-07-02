package com.uema.vet.domain.entity.receitas;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_receita")
@Data
public class ItemReceita {

    @EmbeddedId
    private ItemReceitaId id = new ItemReceitaId();

    @ManyToOne
    @JoinColumn(name = "id_receita")
    @MapsId("idReceita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    @MapsId("idMedicamento")
    private Medicamento medicamento;

    @Column(name = "dosagem")
    private String dosagem;
    @Column(name = "frequencia_tempo")
    private String frequenciaTempo;
}
