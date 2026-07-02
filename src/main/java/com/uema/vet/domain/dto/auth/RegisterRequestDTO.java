package com.uema.vet.domain.dto.auth;

import com.uema.vet.domain.entity.superclasses.role.Role;

public record RegisterRequestDTO(String username, String email, String password, String cpf, Role role) {
}
