package com.uema.vet.domain.entity.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.superclasses.Usuario;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tutor")
@PrimaryKeyJoinColumn(name = "id_tutor")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tutor extends Usuario {
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;


    @ManyToMany
    @JoinTable(
            name = "tutor_has_pets",
            joinColumns = @JoinColumn(name = "id_tutor"),
            inverseJoinColumns = @JoinColumn(name = "id_pet")
    )
    @JsonIgnoreProperties("tutores")
    private Set<Pet> pets;
}
