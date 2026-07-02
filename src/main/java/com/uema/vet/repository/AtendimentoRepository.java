package com.uema.vet.repository;

import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.repository.projection.HistoricoClinicoProjection;
import com.uema.vet.repository.projection.MultiplosMedicamentosProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    @Query(value = """
        SELECT
            a.id_atendimento AS idAtendimento,
            a.data,
            a.hora,
            a.status,
            a.motivo_consulta AS motivoConsulta,
            a.peso_atendimento AS pesoAtendimento,
            a.anamnese,
            p.nome AS nomePet,
            uv.nome AS nomeVeterinario,
            ut.nome AS nomeTutor
        FROM atendimento a
        JOIN pets p        ON p.id_pet = a.id_pet
        JOIN veterinario v ON v.id_veterinario = a.id_veterinario
        JOIN usuario uv     ON uv.id_usuario = v.id_veterinario
        JOIN tutor t        ON t.id_tutor = a.id_tutor
        JOIN usuario ut     ON ut.id_usuario = t.id_tutor
        WHERE a.id_pet = :idPet
        ORDER BY a.data DESC, a.hora DESC
        """, nativeQuery = true)
    List<HistoricoClinicoProjection> buscarHistoricoClinico(@Param("idPet") Long idPet);


    @Query(value = """
    WITH medicamentos_por_atendimento AS (
        SELECT r.id_atendimento, COUNT(ir.id_medicamento) AS qtd_medicamentos
        FROM receita r
        JOIN item_receita ir ON ir.id_receita = r.id_receita
        GROUP BY r.id_atendimento
    )
    SELECT 
        a.id_atendimento AS idAtendimento, 
        a.data, 
        p.nome AS pet, 
        m.qtd_medicamentos AS qtdMedicamentos
    FROM atendimento a
    JOIN medicamentos_por_atendimento m ON m.id_atendimento = a.id_atendimento
    JOIN pets p ON p.id_pet = a.id_pet
    WHERE m.qtd_medicamentos > 1
    ORDER BY m.qtd_medicamentos DESC
    """, nativeQuery = true)
    List<MultiplosMedicamentosProjection> buscarAtendimentosComMultiplosMedicamentos();

    void deleteByTutor(Tutor tutor);
    void deleteByVeterinario(Veterinario veterinario);
    void deleteByPet(Pet pet);
}