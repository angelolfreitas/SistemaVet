package com.uema.vet.domain.entity.subclasses;

import com.uema.vet.domain.entity.superclasses.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "recepcionista")
@PrimaryKeyJoinColumn(name = "id_recepcionista")
@Data

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Recepcionista extends Usuario {
    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;
    private String turno;
}
