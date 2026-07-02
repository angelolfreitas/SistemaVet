package com.uema.vet.domain.entity.receitas;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ItemReceitaId implements Serializable {
    @Column(name = "id_medicamento")
    private Long idMedicamento;
    @Column(name = "id_receita")
    private Long idReceita;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemReceitaId confident = (ItemReceitaId) o;
        return Objects.equals(idMedicamento, confident.idMedicamento) &&
                Objects.equals(idReceita, confident.idReceita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMedicamento, idReceita);
    }
}
