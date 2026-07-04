package com.uema.vet.domain.entity.receitas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_receita")
@Data
public class ItemReceita {

    @EmbeddedId
    private ItemReceitaId id = new ItemReceitaId();

    @ManyToOne
    @JoinColumn(name = "id_receita", insertable = false, updatable = false)
    @MapsId("idReceita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", insertable = false, updatable = false)
    @MapsId("idMedicamento")
    private Medicamento medicamento;

    @Column(name = "dosagem")
    private String dosagem;
    @Column(name = "frequencia_tempo")
    private String frequenciaTempo;
}
