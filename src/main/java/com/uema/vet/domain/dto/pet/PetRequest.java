package com.uema.vet.domain.dto.pet;

import java.math.BigDecimal;

public record PetRequest(String nome,
                         String especie,
                         String raca,
                         String dataNascimento,
                         BigDecimal pesoAtual,
                         String tutorEmail) {
}
