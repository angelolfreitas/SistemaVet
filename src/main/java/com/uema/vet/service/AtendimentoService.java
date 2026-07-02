package com.uema.vet.service;

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

import java.util.List;

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

    public List<Atendimento> listarTodos() {
        return atendimentoRepository.findAll();
    }

    public Atendimento buscarPorId(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado com o ID: " + id));
    }

    @Transactional
    public Atendimento criar(Atendimento atendimento) {
        // Valida se o Pet existe
        Pet pet = petRepository.findById(atendimento.getPet().getIdPet())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        // Valida se o Tutor existe
        Tutor tutor = tutorRepository.findById(atendimento.getTutor().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        // Valida se o Veterinário existe
        Veterinario vet = veterinarioRepository.findById(atendimento.getVeterinario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado"));

        atendimento.setPet(pet);
        atendimento.setTutor(tutor);
        atendimento.setVeterinario(vet);

        return atendimentoRepository.save(atendimento);
    }

    @Transactional
    public void deletar(Long id) {
        Atendimento atendimento = buscarPorId(id);
        atendimentoRepository.delete(atendimento);
    }
}