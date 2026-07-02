package com.uema.vet.repository;

import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.repository.projection.EvolucaoPesoResponse;
import com.uema.vet.repository.projection.PetSemAtendimentoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(value = """
        SELECT p.id_pet AS idPet, p.nome, p.especie, p.raça AS raca
        FROM pets p
        WHERE NOT EXISTS (
            SELECT 1 FROM atendimento a WHERE a.id_pet = p.id_pet
        )
        """, nativeQuery = true)
    List<PetSemAtendimentoProjection> buscarPetsSemAtendimento();

    @Query(value = """
        SELECT
            a.data,
            a.motivo_consulta AS motivoConsulta,
            a.peso_atendimento AS pesoAtendimento,
            LAG(a.peso_atendimento) OVER (PARTITION BY a.id_pet ORDER BY a.data, a.hora) AS pesoAnterior,
            ROUND(
                a.peso_atendimento - LAG(a.peso_atendimento) OVER (PARTITION BY a.id_pet ORDER BY a.data, a.hora),
                2
            ) AS variacaoPeso
        FROM atendimento a
        WHERE a.id_pet = :idPet
        ORDER BY a.data, a.hora
        """, nativeQuery = true)
    List<EvolucaoPesoResponse> buscarEvolucaoPesoNative(@Param("idPet") Long idPet);
}