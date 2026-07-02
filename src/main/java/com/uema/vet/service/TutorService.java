package com.uema.vet.service;

import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    public Tutor buscarPorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado com o ID: " + id));
    }

    @Transactional
    public Tutor criar(Tutor tutor) {
        // Criptografa a senha vinda do front antes de salvar no banco
        tutor.setPassword(passwordEncoder.encode(tutor.getPassword()));

        if (tutor.getDataCadastro() == null) {
            tutor.setDataCadastro(LocalDate.now());
        }
        return tutorRepository.save(tutor);
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = buscarPorId(id);
        tutorRepository.delete(tutor);
    }
}