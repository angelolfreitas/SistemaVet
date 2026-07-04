package com.uema.vet.domain.dto.recepcionista;

import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.superclasses.Endereco;
import com.uema.vet.domain.entity.superclasses.role.Role;

import java.time.LocalDate;
import java.util.Set;

public record RecepcionistaRequestDTO(
        String username,
        String cpf,
        String email,
        String telefone,
        Endereco endereco,
        String password,
        Role role,
        LocalDate dataAdmissao,
        String turno
) {
}
