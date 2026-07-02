package com.uema.vet.domain.dto.veterinario;

import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.domain.entity.superclasses.Endereco;
import com.uema.vet.domain.entity.superclasses.role.Role;

public record Veterinarioresponse(String username,
                                  Role role,
                                  String crmv,
                                  String especialidade) {
    public static Veterinarioresponse create(Veterinario vet) {
        return new Veterinarioresponse(
                vet.getUsername(),
                vet.getRole(),
                vet.getCrmv(),
                vet.getEspecialidade()
        );
    }
}
