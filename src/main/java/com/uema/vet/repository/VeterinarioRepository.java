package com.uema.vet.repository;

import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.repository.projection.TopMedicamentosProjection;
import com.uema.vet.repository.projection.TotalAtendimentosProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    @Query(value = """
        SELECT
            uv.nome AS veterinario,
            v.especialidade,
            COUNT(a.id_atendimento) AS totalAtendimentos
        FROM veterinario v
        JOIN usuario uv ON uv.id_usuario = v.id_veterinario
        LEFT JOIN atendimento a ON a.id_veterinario = v.id_veterinario
        GROUP BY v.id_veterinario, uv.nome, v.especialidade
        HAVING COUNT(a.id_atendimento) > 0
        ORDER BY totalAtendimentos DESC
        """, nativeQuery = true)
    List<TotalAtendimentosProjection> buscarTotalAtendimentos();
    @Query(value = """
    WITH prescricoes AS (
        SELECT
            a.id_veterinario,
            ir.id_medicamento,
            COUNT(*) AS total_prescricoes
        FROM item_receita ir
        JOIN receita r      ON r.id_receita = ir.id_receita
        JOIN atendimento a  ON a.id_atendimento = r.id_atendimento
        GROUP BY a.id_veterinario, ir.id_medicamento
    ),
    ranking AS (
        SELECT
            p.id_veterinario,
            p.id_medicamento,
            p.total_prescricoes,
            RANK() OVER (PARTITION BY p.id_veterinario ORDER BY p.total_prescricoes DESC) AS posicao
        FROM prescricoes p
    )
    SELECT
        uv.nome AS veterinario,
        m.nome_comercial AS nomeComercial,
        r.total_prescricoes AS totalPrescricoes,
        r.posicao
    FROM ranking r
    JOIN veterinario v ON v.id_veterinario = r.id_veterinario
    JOIN usuario uv     ON uv.id_usuario = v.id_veterinario
    JOIN medicamento m  ON m.id_medicamento = r.id_medicamento
    WHERE r.posicao <= 3
    ORDER BY uv.nome, r.posicao
    """, nativeQuery = true)
    List<TopMedicamentosProjection> buscarTopMedicamentosPorVeterinario();
}