package com.uema.vet.repository;


import com.uema.vet.domain.entity.subclasses.Recepcionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionistaRepository extends JpaRepository<Recepcionista,Long> {
}
