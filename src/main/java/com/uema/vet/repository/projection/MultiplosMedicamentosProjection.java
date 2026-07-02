package com.uema.vet.repository.projection;

import java.time.LocalDate;

public interface MultiplosMedicamentosProjection {
    Long getIdAtendimento();
    LocalDate getData();
    String getPet();
    Long getQtdMedicamentos();
}