package com.uema.vet.domain.entity.subclasses;

import com.uema.vet.domain.entity.superclasses.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "veterinario")
@PrimaryKeyJoinColumn(name = "id_veterinario")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Veterinario extends Usuario {
    private String crmv;
    private String especialidade;
}
