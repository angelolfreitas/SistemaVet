package com.uema.vet.domain.dto.veterinario;

import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.superclasses.Endereco;
import com.uema.vet.domain.entity.superclasses.role.Role;

import java.util.List;

public record VeterinarioRequest(String username,
                                 String cpf,
                                 String email,
                                 String telefone,
                                 Endereco endereco,
                                 String password,
                                 Role role,
                                 String crmv,
                                 String especialidade) {
}
