package com.uema.vet.service;

import com.uema.vet.domain.dto.pet.PetRequest;
import com.uema.vet.domain.dto.pet.PetResponse;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.repository.PetRepository;
import com.uema.vet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public List<PetResponse> listarTodos() {
        return petRepository.findAll()
                .stream()
                .map(PetResponse::create)
                .toList();
    }

    public Pet buscarPorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com o ID: " + id));
    }

    @Transactional
    public Optional<PetResponse> criar(PetRequest request) {
        try {
            Tutor tutor = tutorRepository.findByEmail(request.tutorEmail())
                    .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

            Pet pet = new Pet();
            pet.setNome(request.nome());
            pet.setEspecie(request.especie());
            pet.setRaca(request.raca());
            pet.setPesoAtual(request.pesoAtual());
            pet.setTutores(List.of(tutor));

            petRepository.save(pet);
            return Optional.of(PetResponse.create(pet));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Transactional
    public Optional<PetResponse> updatePet(Long id, PetRequest request) {
        try {
            Pet pet = buscarPorId(id);
            pet.setNome(request.nome());
            pet.setEspecie(request.especie());
            pet.setRaca(request.raca());
            pet.setPesoAtual(request.pesoAtual());

            petRepository.save(pet);
            return Optional.of(PetResponse.create(pet));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void patchPet(Long id, Map<String, Object> updates) {
        Pet pet = buscarPorId(id);
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Pet.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, pet, value);
            }
        });
        petRepository.save(pet);
    }

    @Transactional
    public void deletar(Long id) {
        Pet pet = buscarPorId(id);
        petRepository.delete(pet);
    }
}