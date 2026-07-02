package com.uema.vet.domain.dto.pet;

import java.math.BigDecimal;

public record PetResponse(String nome,
                          String especie,
                          BigDecimal pesoAtual) {
    public static PetResponse create(com.uema.vet.domain.entity.Pet pet) {
        return new PetResponse(
                pet.getNome(),
                pet.getEspecie(),
                pet.getPesoAtual()
        );
    }
}
