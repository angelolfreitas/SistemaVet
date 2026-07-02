package com.uema.vet.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface HistoricoClinicoProjection {
    Long getIdAtendimento();
    LocalDate getData();
    LocalTime getHora();
    Integer getStatus();
    String getMotivoConsulta();
    BigDecimal getPesoAtendimento();
    String getAnamnese();
    String getNomePet();
    String getNomeVeterinario();
    String getNomeTutor();
}
