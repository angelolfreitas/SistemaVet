package com.uema.vet.domain.dto.tutor;

import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.superclasses.Endereco;
import com.uema.vet.domain.entity.superclasses.role.Role;

import java.util.List;
import java.util.Set;

public record TutorResponse(String username,
                            Role role,
                            Set<Pet> pets,
                            String token,
                            Long id) {
    public static TutorResponse create(Tutor tutor, String token) {
        return new TutorResponse(
                tutor.getUsername(),
                tutor.getRole(),
                tutor.getPets(),
                token,
                tutor.getIdUsuario()
        );
    }
    public static TutorResponse create(Tutor tutor) {
        return new TutorResponse(
                tutor.getUsername(),
                tutor.getRole(),
                tutor.getPets(),
                "token",
                tutor.getIdUsuario()
        );
    }
}
