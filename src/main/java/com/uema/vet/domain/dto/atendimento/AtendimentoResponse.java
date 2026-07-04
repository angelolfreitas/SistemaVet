package com.uema.vet.domain.dto.atendimento;

import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.enums.StatusAtendimento;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
@Builder
public record AtendimentoResponse(LocalDate data,
                                  LocalTime hora,
                                  StatusAtendimento status,
                                  String motivoConsulta,
                                  BigDecimal pesoAtendimento,
                                  String anamnese,
                                  String petName,
                                  String tutorName,
                                  String veterinarioName,
                                  Long id,
                                  String creatorName) {
    public static AtendimentoResponse create(Atendimento atendimento) {
        return create(atendimento, null);
    }
    public static AtendimentoResponse create(Atendimento atendimento, String creatorName) {
        return AtendimentoResponse.builder()
                .data(atendimento.getData())
                .hora(atendimento.getHora())
                .status(atendimento.getStatus())
                .motivoConsulta(atendimento.getMotivoConsulta())
                .pesoAtendimento(atendimento.getPesoAtendimento())
                .anamnese(atendimento.getAnamnese())
                .petName(atendimento.getPet().getNome())
                .tutorName(atendimento.getTutor().getUsername())
                .veterinarioName(atendimento.getVeterinario().getUsername())
                .id(atendimento.getIdAtendimento())
                .creatorName(creatorName)
                .build();
    }
}
