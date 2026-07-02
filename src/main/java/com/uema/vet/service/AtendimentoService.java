package com.uema.vet.service;

import com.uema.vet.domain.dto.atendimento.AtendimentoRequest;
import com.uema.vet.domain.dto.atendimento.AtendimentoResponse;
import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.repository.AtendimentoRepository;
import com.uema.vet.repository.PetRepository;
import com.uema.vet.repository.TutorRepository;
import com.uema.vet.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    public List<AtendimentoResponse> listarTodos() {
        return atendimentoRepository.findAll()
                .stream()
                .map(AtendimentoResponse::create)
                .toList();
    }

    public Atendimento buscarPorId(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado com o ID: " + id));
    }

    @Transactional
    public Optional<AtendimentoResponse> criar(AtendimentoRequest atendimento) {
        try{
            // Valida se o Pet existe
            Pet pet = petRepository.findById(atendimento.petid())
                    .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

            // Valida se o Tutor existe
            Tutor tutor = tutorRepository.findById(atendimento.tutorId())
                    .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

            // Valida se o Veterinário existe
            Veterinario vet = veterinarioRepository.findById(atendimento.veterinarioId())
                    .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));
            Atendimento atendimentoValue = Atendimento.builder()
                    .data(atendimento.data())
                    .pet(pet)
                    .pesoAtendimento(atendimento.pesoAtendimento())
                    .hora(atendimento.hora())
                    .anamnese(atendimento.anamnese())
                    .tutor(tutor)
                    .status(atendimento.status())
                    .motivoConsulta(atendimento.motivoConsulta())
                    .veterinario(vet)
                    .build();
            atendimentoRepository.save(atendimentoValue);
            return Optional.of(AtendimentoResponse.create(atendimentoValue));
        }catch (Exception e){
            return Optional.empty();
        }

    }


    @Transactional
    public Optional<AtendimentoResponse> updateAtendimento(Long id, AtendimentoRequest request) {
        try {
            Atendimento atendimento = buscarPorId(id);

            Pet pet = petRepository.findById(request.petid()).orElseThrow();
            Tutor tutor = tutorRepository.findById(request.tutorId()).orElseThrow();
            Veterinario vet = veterinarioRepository.findById(request.veterinarioId()).orElseThrow();

            atendimento.setData(request.data());
            atendimento.setHora(request.hora());
            atendimento.setStatus(request.status());
            atendimento.setMotivoConsulta(request.motivoConsulta());
            atendimento.setPesoAtendimento(request.pesoAtendimento());
            atendimento.setAnamnese(request.anamnese());
            atendimento.setPet(pet);
            atendimento.setTutor(tutor);
            atendimento.setVeterinario(vet);

            atendimentoRepository.save(atendimento);
            return Optional.of(AtendimentoResponse.create(atendimento));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void patchAtendimento(Long id, Map<String, Object> updates) {
        Atendimento atendimento = buscarPorId(id);
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Atendimento.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, atendimento, value);
            }
        });
        atendimentoRepository.save(atendimento);
    }

    @Transactional
    public void deletar(Long id) {
        Atendimento atendimento = buscarPorId(id);
        atendimentoRepository.delete(atendimento);
    }
}