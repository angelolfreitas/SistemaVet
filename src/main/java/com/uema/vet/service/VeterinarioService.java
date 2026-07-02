package com.uema.vet.service;

import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Veterinario> listarTodos() {
        return veterinarioRepository.findAll();
    }

    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado com o ID: " + id));
    }

    @Transactional
    public Veterinario criar(Veterinario veterinario) {
        // Criptografa a senha antes de salvar
        veterinario.setPassword(passwordEncoder.encode(veterinario.getPassword()));
        return veterinarioRepository.save(veterinario);
    }

    @Transactional
    public void deletar(Long id) {
        Veterinario veterinario = buscarPorId(id);
        veterinarioRepository.delete(veterinario);
    }
}