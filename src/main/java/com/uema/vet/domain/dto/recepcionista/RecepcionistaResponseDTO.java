package com.uema.vet.domain.dto.recepcionista;

import com.uema.vet.domain.dto.tutor.TutorResponse;
import com.uema.vet.domain.entity.subclasses.Recepcionista;
import com.uema.vet.domain.entity.subclasses.Tutor;

public record RecepcionistaResponseDTO (
        String username,
        String token,
        String turno
){
    public static RecepcionistaResponseDTO create(Recepcionista recepcionista, String token) {
        return new RecepcionistaResponseDTO(
                recepcionista.getUsername(),
                token,
                recepcionista.getTurno()
        );
    }
    public static RecepcionistaResponseDTO create(Recepcionista recepcionista) {
        return new RecepcionistaResponseDTO(
                recepcionista.getUsername(),
                "token",
                recepcionista.getTurno()
        );
    }
}
