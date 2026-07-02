package com.uema.vet.repository.projection;


import java.math.BigDecimal;
import java.time.LocalDate;

public interface EvolucaoPesoResponse {
    LocalDate getData();
    String getMotivoConsulta();
    BigDecimal getPesoAtendimento();

    BigDecimal getPesoAnterior();
    BigDecimal getVariacaoPeso();
}
