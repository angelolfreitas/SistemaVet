package com.uema.vet.service;

import com.uema.vet.domain.dto.tutor.TutorRequest;
import com.uema.vet.domain.dto.tutor.TutorResponse;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<TutorResponse> listarTodos() {
        return tutorRepository.findAll()
                .stream()
                .map(TutorResponse::create)
                .toList();
    }

    public Tutor buscarPorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado com o ID: " + id));
    }

    @Transactional
    public Optional<TutorResponse> criar(TutorRequest request) {
        try {
            Tutor tutor = new Tutor();
            tutor.setUsername(request.username());
            tutor.setEmail(request.email());
            tutor.setPassword(passwordEncoder.encode(request.password()));
            tutor.setCpf(request.cpf());
            tutor.setTelefone(request.telefone());
            tutor.setDataCadastro(LocalDate.now());
            tutor.setPets(request.pets());
            tutor.setRole(request.role());
            tutor.setEndereco(request.endereco());

            tutorRepository.save(tutor);
            return Optional.of(TutorResponse.create(tutor));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Transactional
    public Optional<TutorResponse> updateTutor(Tutor tutor, TutorRequest request) {
        try {
            tutor.setUsername(request.username());
            tutor.setEmail(request.email());
            tutor.setPassword(passwordEncoder.encode(request.password()));
            tutor.setCpf(request.cpf());
            tutor.setTelefone(request.telefone());
            tutor.setPets(request.pets());
            tutor.setRole(request.role());
            tutor.setEndereco(request.endereco());

            tutorRepository.save(tutor);
            return Optional.of(TutorResponse.create(tutor));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void patchTutor(Tutor tutor, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Tutor.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, tutor, value);
            }
        });
        tutorRepository.save(tutor);
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = buscarPorId(id);
        tutorRepository.delete(tutor);
    }
}