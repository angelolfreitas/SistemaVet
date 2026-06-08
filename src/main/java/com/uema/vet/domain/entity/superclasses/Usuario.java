package com.uema.vet.domain.entity.superclasses;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

}
