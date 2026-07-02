package com.uema.vet.repository;

import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}