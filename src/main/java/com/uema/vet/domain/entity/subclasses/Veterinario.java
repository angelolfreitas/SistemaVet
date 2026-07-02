package com.uema.vet.domain.entity.subclasses;

import com.uema.vet.domain.entity.superclasses.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "veterinario")
@PrimaryKeyJoinColumn(name = "id_veterinario")
@Data
public class Veterinario extends Usuario {
    private String crmv;
    private String especialidade;
}
