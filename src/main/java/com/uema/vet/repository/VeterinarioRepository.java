package com.uema.vet.repository;

import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
}