package com.uema.vet.domain.dto.atendimento;

import com.uema.vet.domain.enums.StatusAtendimento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AtendimentoRequest(LocalDate data,
                                 LocalTime hora,
                                 StatusAtendimento status,
                                 String motivoConsulta,
                                 BigDecimal pesoAtendimento,
                                 String anamnese,
                                 Long petid,
                                 Long tutorId,
                                 Long veterinarioId) {


}
