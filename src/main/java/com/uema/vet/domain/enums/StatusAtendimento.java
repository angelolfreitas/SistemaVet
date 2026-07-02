package com.uema.vet.domain.enums;

import lombok.Getter;

public enum StatusAtendimento {
    RUNNING("em andamento"),
    FINISHED("terminou"),
    RETURN_REQUIRED("precisa de retorno");
    @Getter
    private final String name;

    StatusAtendimento(String name) {
        this.name = name;
    }
}
