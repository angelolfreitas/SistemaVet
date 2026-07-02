package com.uema.vet.service;

import com.uema.vet.domain.dto.veterinario.VeterinarioRequest;
import com.uema.vet.domain.dto.veterinario.Veterinarioresponse;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Veterinarioresponse> listarTodos() {
        return veterinarioRepository.findAll()
                .stream()
                .map(Veterinarioresponse::create)
                .toList();
    }

    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado com o ID: " + id));
    }

    @Transactional
    public Optional<Veterinarioresponse> criar(VeterinarioRequest request) {
        try {
            Veterinario veterinario = new Veterinario();
            veterinario.setUsername(request.username());
            veterinario.setEmail(request.email());
            veterinario.setPassword(passwordEncoder.encode(request.password()));
            veterinario.setCpf(request.cpf());
            veterinario.setCrmv(request.crmv());
            veterinario.setTelefone(request.telefone());
            veterinario.setEspecialidade(request.especialidade());
            veterinario.setRole(request.role());
            veterinario.setEndereco(request.endereco());

            veterinarioRepository.save(veterinario);
            return Optional.of(Veterinarioresponse.create(veterinario));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Veterinarioresponse> updateVeterinario(Veterinario veterinario, VeterinarioRequest request) {
        try {
            veterinario.setUsername(request.username());
            veterinario.setEmail(request.email());
            veterinario.setPassword(passwordEncoder.encode(request.password()));
            veterinario.setCpf(request.cpf());
            veterinario.setCrmv(request.crmv());
            veterinario.setTelefone(request.telefone());
            veterinario.setEspecialidade(request.especialidade());
            veterinario.setRole(request.role());
            veterinario.setEndereco(request.endereco());

            veterinarioRepository.save(veterinario);
            return Optional.of(Veterinarioresponse.create(veterinario));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void patchVeterinario(Veterinario veterinario, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Veterinario.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, veterinario, value);
            }
        });
        veterinarioRepository.save(veterinario);
    }
    @Transactional
    public void deletar(Long id) {
        Veterinario veterinario = buscarPorId(id);
        veterinarioRepository.delete(veterinario);
    }
}