package com.uema.vet.service;

import com.uema.vet.domain.dto.recepcionista.RecepcionistaRequestDTO;
import com.uema.vet.domain.dto.recepcionista.RecepcionistaResponseDTO;
import com.uema.vet.domain.entity.subclasses.Recepcionista;
import com.uema.vet.domain.entity.subclasses.Recepcionista;
import com.uema.vet.infra.security.TokenService;
import com.uema.vet.repository.AtendimentoRepository;
import com.uema.vet.repository.RecepcionistaRepository;
import com.uema.vet.repository.RecepcionistaRepository;
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
public class RecepcionistaService {

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private TokenService tokenService;

    public List<RecepcionistaResponseDTO> listarTodos() {
        return recepcionistaRepository.findAll()
                .stream()
                .map(RecepcionistaResponseDTO::create)
                .toList();
    }

    public Recepcionista buscarPorId(Long id) {
        return recepcionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado com o ID: " + id));
    }

    @Transactional
    public Optional<RecepcionistaResponseDTO> criar(RecepcionistaRequestDTO request) {
            Recepcionista recepcionista = new Recepcionista();
            recepcionista.setUsername(request.username());
            recepcionista.setEmail(request.email());
            recepcionista.setPassword(passwordEncoder.encode(request.password()));
            recepcionista.setCpf(request.cpf());
            recepcionista.setTelefone(request.telefone());
            recepcionista.setDataAdmissao(LocalDate.now());
            recepcionista.setTurno(request.turno());
            recepcionista.setRole(request.role());
            recepcionista.setEndereco(request.endereco());

            recepcionistaRepository.save(recepcionista);
            String token = this.tokenService.generateToken(recepcionista);
            return Optional.of(RecepcionistaResponseDTO.create(recepcionista, token));

    }
    @Transactional
    public Optional<RecepcionistaResponseDTO> updateRecepcionista(Recepcionista recepcionista, RecepcionistaRequestDTO request) {
            recepcionista.setUsername(request.username());
            recepcionista.setUsername(request.username());
            recepcionista.setEmail(request.email());
            recepcionista.setPassword(passwordEncoder.encode(request.password()));
            recepcionista.setCpf(request.cpf());
            recepcionista.setTelefone(request.telefone());
            recepcionista.setDataAdmissao(LocalDate.now());
            recepcionista.setTurno(request.turno());
            recepcionista.setRole(request.role());
            recepcionista.setEndereco(request.endereco());

            recepcionistaRepository.save(recepcionista);
            return Optional.of(RecepcionistaResponseDTO.create(recepcionista));
    }

    @Transactional
    public void patchRecepcionista(Recepcionista recepcionista, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Recepcionista.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, recepcionista, value);
            }
        });
        recepcionistaRepository.save(recepcionista);
    }

    public void deletar(Long id) {
        Recepcionista recepcionista = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado"));

        recepcionistaRepository.delete(recepcionista);
    }
}
